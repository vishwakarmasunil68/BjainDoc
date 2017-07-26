package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
 * Created by Emobi-Android-002 on 8/13/2016.
 */
public class AddPatientActivity extends Activity {
    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
    public static final String REGISTER_URL="http://www.bjain.com/doctor/registration.php";
    LinearLayout btn_Submit;
    TextView sign;
    String rate;
    public static ArrayList<InfoApps> patientdetails;
    ListView lv;
    LocationAdapter contactAdapter;
    InfoApps Detailapp;
    LinearLayout linearLayout;
    UsersAdapter usersAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpatient);
        btn_Submit = (LinearLayout) findViewById(R.id.btn_submit);
        ed1=(EditText)findViewById(R.id.edtxt_pname);
        sign=(TextView) findViewById(R.id.sign);
        linearLayout=(LinearLayout)findViewById(R.id.ln_layout);
        ed2=(EditText)findViewById(R.id.edtxt_pemail);
        lv=(ListView)findViewById(R.id.listView);
        ed3=(EditText)findViewById(R.id.edtxt_ppassword);
        patientdetails=new ArrayList<InfoApps>();
//        mViewTherapist = (View) findViewById(R.id.layout_therapist);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new CallServices().execute(ApiConfig.ADD_PATIENT_URL);

            }
        });

        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        sign.setTypeface(tf);
        ed1.setTypeface(tf);
        ed2.setTypeface(tf);
        ed3.setTypeface(tf);
    }


    public class CallServices extends AsyncTask<String, String, String> {
        final String name =ed1.getText().toString().trim();
        final String email =ed2.getText().toString().trim();
        final String password =ed3.getText().toString().trim();
        final String activation="active";
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(AddPatientActivity.this);

            pd.setMessage("Working ..");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("p_name", name));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", password));
            namevaluepair.add(new BasicNameValuePair("p_login_id", email));
            namevaluepair.add(new BasicNameValuePair("p_status", activation));


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
                        String p_message = jsonObject.getString("p_message");
                    if (p_message.equalsIgnoreCase("success")) {
                        String patient_name = jsonObject.getString("p_name");
                        String patient_id = jsonObject.getString("p_id");
                        String patient_login_pass = jsonObject.getString("p_login_pass");
                        String p_status = jsonObject.getString("p_status");
                        Detailapp = new InfoApps();
                        Detailapp.setName(patient_name);
                        Detailapp.setId(patient_id);
                        Detailapp.setPass(patient_login_pass);
                        Detailapp.setStatus(p_status);
                        patientdetails.add(Detailapp);
                        Toast.makeText(getApplicationContext(),"Successfully patient added", Toast.LENGTH_LONG).show();
                        setNotification("",patient_id);

                    }
                    else if (p_message.equalsIgnoreCase("You did not enter a valid email.")){
                        Toast.makeText(AddPatientActivity.this,"you did not enter valid email-id", Toast.LENGTH_LONG).show();
                        ed2.setFocusable(true);

                    }





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

    public void setNotification(final String device_token, final String msg) {
        new AsyncTask<String, String, String>() {
            ProgressDialog pd;
            ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
            String result;
            String tag;

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                pd = new ProgressDialog(getApplicationContext());

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.hide();
            }

            @SuppressWarnings("deprecation")
            @Override
            protected String doInBackground(String... params) {
                //  TODO Auto-generated method stub

                namevaluepair.add(new BasicNameValuePair("token", ""));
                Log.d("sun", device_token);
                namevaluepair.add(new BasicNameValuePair("message", "doctor::"));
                namevaluepair.add(new BasicNameValuePair("purgentchat", ""));
                namevaluepair.add(new BasicNameValuePair("chat_doc_id", PreferenceData.getId(getApplicationContext())));
                namevaluepair.add(new BasicNameValuePair("chat_p_id", msg));
                namevaluepair.add(new BasicNameValuePair("title", "Bjain Doctor"));
                namevaluepair.add(new BasicNameValuePair("date", UtilsValidate.getCurrentDate()));
                namevaluepair.add(new BasicNameValuePair("time", UtilsValidate.getCurrentDateTime()));
                try {

                    result = Util.executeHttpPost(ApiConfig.SEND_MESSAGE, namevaluepair);

                    Log.e("sunil", result.toString());
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

                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

}
