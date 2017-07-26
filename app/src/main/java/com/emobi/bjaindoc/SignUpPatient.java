package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 9/7/2016.
 */
public class SignUpPatient extends AppCompatActivity {
    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
   public static String patient_login_id;
    public static final String REGISTER_URL="http://www.bjain.com/doctor/registration.php";
    LinearLayout btn_Submit;
    String rate;
    public static ArrayList<InfoApps> patientdetails;
    ListView lv;
    LocationAdapter contactAdapter;
    InfoApps Detailapp;
    LinearLayout linearLayout;
    TextView sign,subm;
   ActionBar actionBar;
    ImageView backarrow;
    TextView toolbar_title;
    UsersAdapter usersAdapter;
    String email_id,passwor,d_token;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpatient);


        btn_Submit = (LinearLayout) findViewById(R.id.btn_submit);
        backarrow = (ImageView) findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        sign =(TextView)findViewById(R.id.sign);
        subm =(TextView)findViewById(R.id.subm);
        ed1=(EditText)findViewById(R.id.edtxt_pname);
        linearLayout=(LinearLayout)findViewById(R.id.ln_layout);
        ed2=(EditText)findViewById(R.id.edtxt_pemail);
        lv=(ListView)findViewById(R.id.listView);
        ed3=(EditText)findViewById(R.id.edtxt_ppassword);
        ed4=(EditText)findViewById(R.id.edtxt_pmob);

        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_title.setTypeface(tf);
        subm.setTypeface(tf);
        ed1.setTypeface(tf);
        ed2.setTypeface(tf);
        ed3.setTypeface(tf);
        ed4.setTypeface(tf);
        sign.setTypeface(tf);

        patientdetails=new ArrayList<InfoApps>();
//        mViewTherapist = (View) findViewById(R.id.layout_therapist);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Call1Services().execute(ApiConfig.ADD_PATIENT_URL);

            }
        });
    }
    public class Call1Services extends AsyncTask<String, String, String> {
        final String name =ed1.getText().toString().trim();
        final String email =ed2.getText().toString().trim();
        final String password =ed3.getText().toString().trim();
        final String mobile_number =ed4.getText().toString().trim();
        final String activation="deactive";
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(SignUpPatient.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("doc_id", ""));
            namevaluepair.add(new BasicNameValuePair("p_name", name));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", password));
            namevaluepair.add(new BasicNameValuePair("p_login_id", email));
            namevaluepair.add(new BasicNameValuePair("p_status", activation));
            namevaluepair.add(new BasicNameValuePair("p_mob", mobile_number));

            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

                Log.e("result",result.toString());

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
            }


            if (result != null) {

                try {

                    JSONObject jsonObject=new JSONObject(result);

//                    String patient_name = jsonObject.getString("p_name");
                    String p_message = jsonObject.getString("p_message");
                    if (p_message.equals("success")) {
                        email_id = jsonObject.getString("p_login_id");
                        passwor = jsonObject.getString("p_login_pass");
                        String p_status = jsonObject.getString("p_status");
                        Log.d("check", email_id + "" + passwor);
                        Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_LONG).show();
//                    email_id=jsonObject.getString("patient_login_id");
//                    passwor=jsonObject.getString("patient_login_pass");
//                    d_token=jsonObject.getString("device_token");
                        new CallServices().execute(ApiConfig.LOGIN_PATIENT);
                    }
                    else if (p_message.equals("Login id Already Exit!"))
                    {
                        Toast.makeText(getApplicationContext(), "This email id has already exist", Toast.LENGTH_LONG).show();
                    }
                    else {

                    }

                   /* Detailapp = new InfoApps();
                    Detailapp.setName(patient_name);
                    Detailapp.setId(patient_login_id);
//                    Detailapp.setPass(patient_login_pass);
                    Detailapp.setStatus(p_status);
                    patientdetails.add(Detailapp);
//                    usersAdapter.notifyDataSetChanged();
                    Log.e("account_balance_id", patientdetails.toString());*/
//                                    if (BalanceDetail.password.equals(pinpassword)) {
                        /*linearLayout.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);
                        contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);
                        lv.setAdapter(contactAdapter);
*/

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


    }
    public class CallServices extends AsyncTask<String, String, String> {
        String unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(SignUpPatient.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("p_login_id", email_id));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", passwor));
            namevaluepair.add(new BasicNameValuePair("p_device_token", PreferenceData.getDevice_Token(getApplicationContext())));

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

            if (pd != null) {
                pd.dismiss();
            }


            if (result != null) {

                try {
                    JSONObject objresponse = new JSONObject(result);
                    Log.e("sub", objresponse.toString());
                    String message = objresponse.getString("p_message");
                    if (message.equalsIgnoreCase("You did not enter a valid email.")) {
                        Toast.makeText(SignUpPatient.this, "Please enter valid information", Toast.LENGTH_SHORT).show();
//                        UtilsValidate.showError(SignUpPatient.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    }
                    /*else {
                        if (result.toLowerCase().contains("deactive")) {
                            UtilsValidate.showError(SignWithPatient.this, getResources().getString(R.string.error), getResources().getString(R.string.err_notify));
                        }*/
                    else {
                       String p_id = objresponse.getString("p_id");
                        String  p_name = objresponse.getString("p_name");
                        Log.d("sunil","p_name:-"+p_name);
                        String d_id = objresponse.getString("doc_id");
                        String p_login_id = objresponse.getString("p_login_id");
                        String  p_pass = objresponse.getString("p_login_pass");
                        String  p_weight = objresponse.getString("p_weight");
                        String p_bloodgroup = objresponse.getString("p_bloodgroup");
                        String  p_age = objresponse.getString("p_age");
                        String  p_height = objresponse.getString("p_height");
                        String  p_doctor_device_token = objresponse.getString("p_device_token");
                        String  p_status = objresponse.getString("p_status");
                        String  p_photo = objresponse.getString("p_photo");

                        PreferenceData.setchatDocid(getApplicationContext(),objresponse.getString("doc_id"));
                       if (d_id.equals("0")) {
                            PreferenceData.setPatientName(getApplicationContext(), p_name);
                            PreferenceData.setPatientID(getApplicationContext(), p_id);
                            PreferenceData.setPatientEmail(getApplicationContext(), p_login_id);
                            PreferenceData.setPatientPassword(getApplicationContext(), p_pass);
                            PreferenceData.setPatientStatus(getApplicationContext(), p_status);
                            PreferenceData.setPatientPhoto(getApplicationContext(), p_photo);

                            PreferenceData.setDoctor_Token(getApplicationContext(), "");
                            PreferenceData.setDoctorName(getApplicationContext(), "");
                            PreferenceData.setDoctorNumber(getApplicationContext(), "");
                            PreferenceData.setDoctorEmail(getApplicationContext(),"");
                            PreferenceData.setDoctorDepartment(getApplicationContext(),"");
                            PreferenceData.setDoctorDob(getApplicationContext(),"");
                            PreferenceData.setDoctorPhotoUrl(getApplicationContext(),"");
                            PreferenceData.setDoctorclinic_address(getApplicationContext(),"");
                            PreferenceData.setDoctorDesignation(getApplicationContext(), "");
                            PreferenceData.setDoctorSpecialist(getApplicationContext(),"");
                            PreferenceData.setDoctorDegree(getApplicationContext(),"" +
                                    "" +
                                    "");
                            PreferenceData.setPatientLogin(getApplicationContext(), true);
                        }
                        else{
                           PreferenceData.setPatientName(getApplicationContext(), p_name);
                           PreferenceData.setPatientID(getApplicationContext(), p_id);
                           PreferenceData.setPatientEmail(getApplicationContext(), p_login_id);
                           PreferenceData.setPatientPassword(getApplicationContext(), p_pass);
                           PreferenceData.setPatientStatus(getApplicationContext(), p_status);
                           PreferenceData.setPatientPhoto(getApplicationContext(), p_photo);

                           PreferenceData.setDoctor_Token(getApplicationContext(), objresponse.getString("device_token"));
                           PreferenceData.setDoctorName(getApplicationContext(), objresponse.getString("reg_name"));
                           PreferenceData.setDoctorNumber(getApplicationContext(), objresponse.getString("reg_mob"));
                           PreferenceData.setDoctorEmail(getApplicationContext(), objresponse.getString("reg_email"));
                           PreferenceData.setDoctorDepartment(getApplicationContext(), objresponse.getString("department"));
                           PreferenceData.setDoctorDob(getApplicationContext(), objresponse.getString("reg_dob"));
                           PreferenceData.setDoctorPhotoUrl(getApplicationContext(), objresponse.getString("photo"));
                           PreferenceData.setDoctorclinic_address(getApplicationContext(), objresponse.getString("clinic_address"));
                           PreferenceData.setDoctorDesignation(getApplicationContext(), objresponse.getString("designation"));
                           PreferenceData.setDoctorSpecialist(getApplicationContext(), objresponse.getString("specialist"));
                           PreferenceData.setDoctorDegree(getApplicationContext(), objresponse.getString("degree"));
                           PreferenceData.setPatientLogin(getApplicationContext(), true);
                        }
//                        editor.commit();

                        finish();
                        Intent intent = new Intent(SignUpPatient.this, PatientAccount.class);
                        startActivity(intent);
//                        Intent intent = new Intent(SignUpPatient.this, DoctorInfo.class);
//                        startActivity(intent);
//                        finish();
                    }



                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
