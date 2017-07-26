package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 8/13/2016.
 */
public class AddExistPatientActivity extends Activity {
    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
    public static final String REGISTER_URL="http://www.bjain.com/doctor/registration.php";
    Button btn_Submit;
    String patient_id;
    public static ArrayList<InfoApps> patientdetails;
    ListView lv;
    LocationAdapter contactAdapter;
    InfoApps Detailapp;
    RelativeLayout linearLayout;
    LinearLayout linear_layout;
    ImageView right,left;
    TextView add_patine;
    UsersAdapter usersAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexispatient);
        btn_Submit = (Button) findViewById(R.id.btn_submit);
        right= (ImageView) findViewById(R.id.check);
        ed1=(EditText)findViewById(R.id.edtxt_pname);
        add_patine=(TextView)findViewById(R.id.add_patine);
        linearLayout=(RelativeLayout)findViewById(R.id.ln_layout);
        linear_layout=(LinearLayout)findViewById(R.id.linear_layout);
        ed2=(EditText)findViewById(R.id.edtxt_pemail);
        lv=(ListView)findViewById(R.id.listView);
        ed3=(EditText)findViewById(R.id.edtxt_ppassword);
        ed4=(EditText)findViewById(R.id.edtxt_pmob);
        patientdetails=new ArrayList<InfoApps>();
//        mViewTherapist = (View) findViewById(R.id.layout_therapist);
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        add_patine.setTypeface(tf);

        ed1.setFocusable(false);
        ed4.setFocusable(false);
        ed3.setFocusable(false);

        Typeface tfff= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
        ed1.setTypeface(tfff);
        ed2.setTypeface(tf);
        ed3.setTypeface(tf);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CallServices1().execute(ApiConfig.UPDATE_EXISTING_PATIENT_URL);

            }
        });
ed2.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (ed2.getText().toString().length()<0) {
            linear_layout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
});
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed2.getText().toString().length()<0){
                    Toast.makeText(getApplicationContext(),"Please enter email first",Toast.LENGTH_LONG).show();
                }
                else {
                    new CallServices().execute(ApiConfig.ADD_EXISTING_PATIENT_URL);
                }
            }
        });

    }
    public class CallServices extends AsyncTask<String, String, String> {
//        final String name =ed1.getText().toString().trim();
        final String email =ed2.getText().toString().trim();
//        final String password =ed3.getText().toString().trim();
//        final String activation="active";
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(AddExistPatientActivity.this);

            pd.setMessage("Working ..");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("p_login_id", email));

//            namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
//            namevaluepair.add(new BasicNameValuePair("p_name", name));
//            namevaluepair.add(new BasicNameValuePair("p_login_pass", password));
//            namevaluepair.add(new BasicNameValuePair("p_status", activation));


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

                    JSONArray jsonArray=new JSONArray(result);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    String message=jsonObject.getString("p_message");
                    if (message.equals("success")) {
                        String patient_name = jsonObject.getString("p_name");
                        patient_id = jsonObject.getString("p_id");
                        String patient_login_pass = jsonObject.getString("p_login_pass");
                        String p_mob = jsonObject.getString("p_mob");

                        linear_layout.setVisibility(View.VISIBLE);

                        ed1.setText(patient_name);
                        ed3.setText(patient_login_pass);
                        ed4.setText(p_mob);
                        btn_Submit.setVisibility(View.VISIBLE);
                        right.setVisibility(View.INVISIBLE);
                    }
                    else {
                        Toast.makeText(AddExistPatientActivity.this,"No record available",Toast.LENGTH_LONG).show();

                    }


          }
                catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


    }

    public class CallServices1 extends AsyncTask<String, String, String> {
        final String name =ed1.getText().toString().trim();
        final String email =ed2.getText().toString().trim();
        final String password =ed3.getText().toString().trim();
        final String mob=ed4.getText().toString().trim();;
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(AddExistPatientActivity.this);

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
            namevaluepair.add(new BasicNameValuePair("p_id", patient_id));
//            namevaluepair.add(new BasicNameValuePair("p_login_pass", password));
//            namevaluepair.add(new BasicNameValuePair("p_status", activation));


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

                    String message=jsonObject.getString("p_message");

                    if (message.equals("All ready connected")) {
                        Toast.makeText(AddExistPatientActivity.this,"Patient already connected to another Doctor",Toast.LENGTH_LONG).show();
                    }
                    else {
                        setNotification("", patient_id);
                    }
/*
                    JSONArray jsonArray=new JSONArray(result);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    String patient_name = jsonObject.getString("p_name");
                    patient_id = jsonObject.getString("p_id");
                    String patient_login_pass = jsonObject.getString("p_login_pass");
                    String p_mob = jsonObject.getString("p_mob");

                    *//*linear_layout.setVisibility(View.VISIBLE);

                    ed1.setText(patient_name);
                    ed2.setText(patient_login_pass);
                    ed4.setText(p_mob);*/


                }
                catch (Exception e) {

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
