package com.emobi.bjaindoc;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public  class Home_Fragment extends Fragment {
	ImageView img_edit,img_refresh;
	public static TextView find,consullt,weight,height,age,bloodgroup,txt_codition1,txt_descrption1;
	public static String p_name,p_pass,p_weight,p_bloodgroup,p_age,p_height,condition,description,p_status;
	Activity activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {
//		isInternetOn();
		View rootView = inflater.inflate(R.layout.home_patient, container, false);

		find=(TextView)rootView.findViewById(R.id.find);
		consullt=(TextView)rootView.findViewById(R.id.consullt);

		Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
		find.setTypeface(tf);
		consullt.setTypeface(tf);

		return rootView;
	}
		/*name=(TextView)rootView.findViewById(R.id.txt_name);
		email=(TextView)rootView.findViewById(R.id.txt_email);
		weight=(TextView)rootView.findViewById(R.id.txt_wt);
		height=(TextView)rootView.findViewById(R.id.txt_ht);
		age=(TextView)rootView.findViewById(R.id.txt_age1);
		bloodgroup=(TextView)rootView.findViewById(R.id.txt_b_group);
		txt_codition1=(TextView)rootView.findViewById(R.id.txt_codition1);
		txt_descrption1=(TextView)rootView.findViewById(R.id.txt_descrption1);
		img_edit=(ImageView)rootView.findViewById(R.id.img_edit);
		img_refresh=(ImageView)rootView.findViewById(R.id.img_refresh);

//		String id= SignWithPatient.p_pass;

		Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
		name.setTypeface(tf);
		email.setTypeface(tf);
		weight.setTypeface(tf);
		height.setTypeface(tf);
		age.setTypeface(tf);
		bloodgroup.setTypeface(tf);
		txt_codition1.setTypeface(tf);
		txt_descrption1.setTypeface(tf);

//		name.setText(SignWithPatient.p_name);
//		email.setText(SignWithPatient.p_login_id);
//		weight.setText(SignWithPatient.p_weight	);
//		height.setText(SignWithPatient.p_height);
//		age.setText(SignWithPatient.p_age);
//		bloodgroup.setText(SignWithPatient.p_bloodgroup);
//		img_refresh.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {

		refresh();

			*//*}
		});*//*
		img_edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				startActivity(new Intent(getActivity(),Updatepatient_patient.class));
//				getActivity().finish();

				Intent i = new Intent(getActivity(), Updatepatient_patient.class);
				startActivityForResult(i, 1);
			}
		});

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
			namevaluepair.add(new BasicNameValuePair("p_login_id", PreferenceData.getPatientEmail(getActivity().getApplicationContext())));
			namevaluepair.add(new BasicNameValuePair("p_login_pass", PreferenceData.getPatientPassword(getActivity().getApplicationContext())));
			namevaluepair.add(new BasicNameValuePair("p_device_token", PreferenceData.getDevice_Token(getActivity())));

			//namevaluepair.add(new BasicNameValuePair("cat", "HAIR"));

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
			try {
				if (pd != null) {
					pd.dismiss();
				}
			}
			catch (Exception e){

			}


			if (result != null) {

				try {
					JSONObject objresponse = new JSONObject(result);
					Log.e("sub", objresponse.toString());
					String message = objresponse.getString("p_message");
					if (message.equalsIgnoreCase("invalid")) {
						Toast.makeText(getActivity(), "information is incorrect", Toast.LENGTH_SHORT).show();
						UtilsValidate.showError(getActivity(), getResources().getString(R.string.error), getResources().getString(R.string.err_email));
					}

					else {
						p_name = objresponse.getString("p_name");
						String p_login_id = objresponse.getString("p_login_id");
						p_status = objresponse.getString("p_status");
						p_pass = objresponse.getString("p_login_pass");
						p_weight = objresponse.getString("p_weight");
						p_bloodgroup = objresponse.getString("p_bloodgroup");
						p_age = objresponse.getString("p_age");
						p_height = objresponse.getString("p_height");
						condition = objresponse.getString("conditions");
						description = objresponse.getString("description");
						name.setText(p_name);
						email.setText(p_login_id);
						weight.setText(p_weight);
						height.setText(p_height);
						age.setText(p_age);
						bloodgroup.setText(p_bloodgroup);
						txt_codition1.setText(condition);
						txt_descrption1.setText(description);
						PreferenceData.setPatientStatus(getActivity().getApplicationContext(),objresponse.getString("p_status"));

					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
	}
	public void refresh(){
		new CallServices().execute(ApiConfig.LOGIN_PATIENT);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if(resultCode == getActivity().RESULT_OK){
				String result=data.getStringExtra("result");
				Log.e("result", result);
				refresh();
				FragmentToActivity fta= (FragmentToActivity) activity;
				fta.refreshImage();
			}
			if (resultCode == getActivity().RESULT_CANCELED) {
				//Write your code if there's no result
			}
		}
	}*/
}


