package com.emobi.bjaindoc;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import database.PreferenceData;

public class Search_Fragment extends Fragment {
	public ListView mRecyclerView;
	InfoApps Detailapp;
	TextView chat_msg;
	public static ArrayList<InfoApps> contactDetails2=new ArrayList<InfoApps>();;
	UsersAdapter2 mAdapter2;
	FrameLayout frame_layout;
	Bitmap print_itmap;
	Activity activity;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=activity;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_prescription, container, false);
		chat_msg=(TextView)rootView.findViewById(R.id.chat_txt);

		mRecyclerView = (ListView) rootView.findViewById(R.id.recycler_view);
		frame_layout= (FrameLayout) rootView.findViewById(R.id.print_layout);

		if (PreferenceData.getPatientStatus(getActivity()).equalsIgnoreCase("deactive")){
			Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montez-Regular.ttf");
			chat_msg.setText("Your id is deactive, contact to your doctor");
			chat_msg.setTypeface(tf);
			chat_msg.setVisibility(View.VISIBLE);
			mRecyclerView.setVisibility(View.GONE);
		}

		/*if (PreferenceData.getPatientStatus(getActivity()).equalsIgnoreCase("deactive")){
			Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montez-Regular.ttf");
			chat_msg.setText("Your id is deactive, contact to your doctor");
			chat_msg.setTypeface(tf);
			chat_msg.setVisibility(View.VISIBLE);
			mRecyclerView.setVisibility(View.GONE);
		}*/

		new CallServices().execute(ApiConfig.VIEW_PRESCRIPTION_URL);
		return rootView;
	}
	public class CallServices extends AsyncTask<String, String, String> {
		ProgressDialog pd;

		ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
		String result;
		String tag;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(getActivity());

			pd.setMessage("Working ...");
			pd.setIndeterminate(false);
			pd.setCancelable(false);
			pd.show();
			contactDetails2.clear();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			namevaluepair.add(new BasicNameValuePair("pre_p_id", PreferenceData.getPatientId(getActivity().getApplicationContext())));


			try {

				result = Util.executeHttpPost(params[0], namevaluepair);

				Log.e("result", result.toString());

			} catch (Exception e) {

				e.printStackTrace();

			}

			return result;


		}


		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (pd != null) {
				pd.dismiss();
				try {
					JSONArray jsonArray = new JSONArray(result);
					Log.e("Post Method", jsonArray.toString());
					for (int i = 0; i < jsonArray.length(); i++) {

						JSONObject jsonObject2 = jsonArray.getJSONObject(i);
						Log.e("2", jsonObject2.toString());
						String pre_date = jsonObject2.getString("pre_date");
						String pre_medicine = jsonObject2.getString("pre_medicine");
						String pre_file= jsonObject2.getString("pre_file");

						Detailapp = new InfoApps();
						Detailapp.setNumber(pre_date);
						Detailapp.setDataAdd(pre_file);
						Detailapp.setName(pre_medicine);

//						Detailapp = new InfoApps();
//                        Detailapp.setName(pre_medicine);
						/*Detailapp.setId(patient_id);
						Detailapp.setName(patient_name);
						Detailapp.setEmail_id(patient_email);
						Detailapp.setStatus(patient_status);*/
						contactDetails2.add(Detailapp);

					}
					mAdapter2 = new UsersAdapter2(getActivity(),R.layout.view_patient_pre_adapter,contactDetails2);
					mRecyclerView.setAdapter(mAdapter2);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		// TODO Add your menu entries here
		inflater.inflate(R.menu.pre_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.print:
				Toast.makeText(getActivity(),"print", Toast.LENGTH_LONG).show();
//				CreatePdf();
				saveImage();
				print();
				break;
		}
		return true;

	}

	public void CreatePdf(){
		Bitmap bitmap = overlay(frame_layout);
	}
	public Bitmap overlay(FrameLayout frame_layout){
		frame_layout.setDrawingCacheEnabled(true);
		frame_layout.buildDrawingCache();
		return frame_layout.getDrawingCache();
	}

	public void saveImage(){
		Bitmap bitmap=overlay(frame_layout);
		try{
			String path = Environment.getExternalStorageDirectory().toString()+ File.separator+"Bjain";
			File path_file=new File(path);
			path_file.mkdirs();
			OutputStream fOut = null;
			File file = new File(path+ File.separator+"print.png"); // the File to save to
			fOut = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
			fOut.flush();
			fOut.close();
			Log.d("sun","saved");
		}
		catch (Exception e){
			Log.d("sun",e.toString());
		}
	}

	private void print() {
		// Get the print manager.
		PrintHelper printHelper = new PrintHelper(activity);
		// Set the desired scale mode.
		printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
		// Get the bitmap for the ImageView's drawable.
		Bitmap bitmap = overlay(frame_layout);
		// Print the bitmap.
		printHelper.printBitmap("Bjain doc", bitmap);
	}

}
