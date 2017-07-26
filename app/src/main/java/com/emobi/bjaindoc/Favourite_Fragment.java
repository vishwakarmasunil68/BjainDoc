package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

public class Favourite_Fragment extends Fragment {
	public RecyclerView mRecyclerView;
	InfoApps Detailapp;
	public static ArrayList<InfoApps> contactDetails1;
	UsersAdapter1 mAdapter1;
	TextView chat_msg;
	ImageView image;
	//	Listadapterdata adapter;
	Button Off,button1,button2,button3,button5,button6,button7;

	FrameLayout print_layout;
	Activity activity;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=activity;
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_medication, container, false);
		chat_msg= (TextView) rootView.findViewById(R.id.chat_txt);
		image= (ImageView) rootView.findViewById(R.id.image);
		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
		print_layout= (FrameLayout) rootView.findViewById(R.id.print_frame);
		/*if (SignWithPatient.p_status.equalsIgnoreCase("deactive")){
			Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montez-Regular.ttf");
			chat_msg.setText("Your id is deactive, contact to your doctor");
			chat_msg.setTypeface(tf);
			chat_msg.setVisibility(View.VISIBLE);
			mRecyclerView.setVisibility(View.GONE);
		}*/
		if (PreferenceData.getPatientStatus(getActivity()).equalsIgnoreCase("deactive")){
			Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montez-Regular.ttf");
			chat_msg.setText("Your id is deactive, contact to your doctor");
			chat_msg.setTypeface(tf);
			chat_msg.setVisibility(View.VISIBLE);
			mRecyclerView.setVisibility(View.GONE);
		}
		contactDetails1=new ArrayList<InfoApps>();
		mRecyclerView.setHasFixedSize(true);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		new CallServices().execute(ApiConfig.VIEW_MEDICATION_URL);
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
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			namevaluepair.add(new BasicNameValuePair("med_p_id", PreferenceData.getPatientId(getActivity().getApplicationContext())));


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
						String med_date = jsonObject2.getString("med_date");
						String med_mess = jsonObject2.getString("med_mess");

						Detailapp = new InfoApps();
						Detailapp.setNumber(med_date);
						Detailapp.setDataAdd(med_mess);
//                        Detailapp.setName(sfirst_name);
						/*Detailapp.setId(patient_id);
						Detailapp.setName(patient_name);
						Detailapp.setEmail_id(patient_email);
						Detailapp.setStatus(patient_status);*/
						contactDetails1.add(Detailapp);

					}
					mAdapter1 = new UsersAdapter1(contactDetails1,getActivity());
					mRecyclerView.setAdapter(mAdapter1);
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
				Toast.makeText(getActivity(), "print", Toast.LENGTH_LONG).show();
//				CreatePdf();
				saveImage();
				print();
				break;
		}
		return true;

	}

	/*public void CreatePdf(){
		Bitmap bitmap = overlay(print_layout);
	}*/
	public Bitmap overlay(RecyclerView frame_layout){
		Bitmap bitmap = Bitmap.createBitmap(frame_layout.getWidth(), frame_layout.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		frame_layout.draw(canvas);
//		image.setImageBitmap(bitmap);
		frame_layout.setDrawingCacheEnabled(true);
		frame_layout.getDrawingCache();
		return frame_layout.getDrawingCache();
	}

	/*   for listview printer
	public static Bitmap getWholeListViewItemsToBitmap() {

		ListView listview    = MyActivity.mFocusedListView;
		ListAdapter adapter  = listview.getAdapter();
		int itemscount       = adapter.getCount();
		int allitemsheight   = 0;
		List<Bitmap> bmps    = new ArrayList<Bitmap>();

		for (int i = 0; i < itemscount; i++) {

			View childView      = adapter.getView(i, null, listview);
			childView.measure(View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
					View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

			childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
			childView.setDrawingCacheEnabled(true);
			childView.buildDrawingCache();
			bmps.add(childView.getDrawingCache());
			allitemsheight+=childView.getMeasuredHeight();
		}

		Bitmap bigbitmap    = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
		Canvas bigcanvas    = new Canvas(bigbitmap);

		Paint paint = new Paint();
		int iHeight = 0;

		for (int i = 0; i < bmps.size(); i++) {
			Bitmap bmp = bmps.get(i);
			bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
			iHeight+=bmp.getHeight();

			bmp.recycle();
			bmp=null;
		}*/

	public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {
		Toast.makeText(getActivity(), "4", Toast.LENGTH_LONG).show();
		Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		Drawable bgDrawable = view.getBackground();
		if (bgDrawable != null) {
			Toast.makeText(getActivity(), "print1", Toast.LENGTH_LONG).show();
			bgDrawable.draw(canvas);

			image.setImageDrawable(bgDrawable);
		}
		else{
			Toast.makeText(getActivity(), "print4" + "", Toast.LENGTH_LONG).show();
			canvas.drawColor(Color.WHITE);
			view.draw(canvas);

		}
		return returnedBitmap;
	}
	public void saveImage(){

		Bitmap bitmap=overlay(mRecyclerView);
//		getBitmapFromView(print_layout,print_layout.getWidth(), print_layout.getHeight());
		try{
			String path = Environment.getExternalStorageDirectory().toString()+ File.separator+"bitmap";
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
		Bitmap bitmap = overlay(mRecyclerView);
		// Print the bitmap.
		printHelper.printBitmap("Print Bitmap", bitmap);
	}
}
