package com.emobi.bjaindoc;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import database.PreferenceData;


public  class Settings_Fragment extends Fragment {
	ImageView img_edit,img_refresh;
	TextView name,email,weight,height,age,share1text,about1text,txt_descrption1;
	RelativeLayout share_App;
	LinearLayout btn_submit;
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
		View rootView = inflater.inflate(R.layout.settings, container, false);
		btn_submit=(LinearLayout)rootView.findViewById(R.id.btn_submit);
		share_App =(RelativeLayout)rootView.findViewById(R.id.rl_appointments);
		TextView share_App1=(TextView)rootView.findViewById(R.id.share1);
		TextView rate1=(TextView)rootView.findViewById(R.id.rate1);
		TextView how_1=(TextView)rootView.findViewById(R.id.how_1);
		share1text=(TextView)rootView.findViewById(R.id.share1text);
		about1text=(TextView)rootView.findViewById(R.id.about1text);
		TextView term1=(TextView)rootView.findViewById(R.id.term1);
		TextView notification_stop1=(TextView)rootView.findViewById(R.id.notification_stop1);
		TextView notification_sound1=(TextView)rootView.findViewById(R.id.notification_sound1);

		final Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
		share_App1.setTypeface(tf);
		rate1.setTypeface(tf);
		share1text.setTypeface(tf);
		about1text.setTypeface(tf);
		how_1.setTypeface(tf);
		term1.setTypeface(tf);
		notification_stop1.setTypeface(tf);
		notification_sound1.setTypeface(tf);

		btn_submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PreferenceData.setPatientLogin(getActivity(),false);
				Intent intent=new Intent(getActivity(),SIgnInOption.class);
				Toast.makeText(getActivity(),"logout successfully", Toast.LENGTH_LONG).show();
				startActivity(intent);
				getActivity().finishAffinity();
			}
		});


		term1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = "http://www.bjain.com/store/contactus";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}

		});
		how_1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog);

				//setting custom layout to dialog
				dialog.setContentView(R.layout.works);
				dialog.setTitle("How it - works");

				//adding text dynamically
				final EditText text_email_id = (EditText) dialog.findViewById(R.id.text_email_id);

				text_email_id.setTypeface(tf);

                /*ImageView image = (ImageView)dialog.findViewById(R.id.image);
                image.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.ic_dialog_email));*/
				Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);

				btn_submit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
		rate1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = "https://play.google.com/store/apps/details?id=com.emobi.bjain";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}

			});
		share_App.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				String path="http://www.bjain.com/doctor/upload/image_91016123802.png";
//
//				final ContentValues values = new ContentValues(2);
//				values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//				values.put(MediaStore.Images.Media.DATA, path);
//				final Uri contentUriFile = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//				// Add data to the intent, the receiving app will decide
//				// what to do with it.
//
//				Intent share = new Intent(android.content.Intent.ACTION_SEND);
//				share.setType("image/png");
//				share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//				share.putExtra(android.content.Intent.EXTRA_STREAM, contentUriFile);
//				share.putExtra(Intent.EXTRA_TEXT, "http://www.bjain.com");
//
//				startActivity(Intent.createChooser(share,"\"title\"" ));

				Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
						R.drawable.shareimg);

				Intent i = new Intent(Intent.ACTION_SEND);

				i.setType("image/*");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
    /*compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] bytes = stream.toByteArray();*/

				i.putExtra(Intent.EXTRA_TEXT, "http://www.bjain.com");
				i.putExtra(Intent.EXTRA_STREAM, getImageUri(getActivity().getApplicationContext(), icon));
				try {
					startActivity(Intent.createChooser(i, "My Profile ..."));
				} catch (android.content.ActivityNotFoundException ex) {

					ex.printStackTrace();
				}

			}
		});

		return rootView;
	}
	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

		String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
		return Uri.parse(path);
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


