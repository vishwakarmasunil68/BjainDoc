package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import database.PreferenceData;
import patient_side.UsersAdapter_Serach_Doc;

/**
 * Created by Emobi-Android-002 on 9/8/2016.
 */
public class AppointmentDetails extends AppCompatActivity {

    Button btn_submit;
    ActionBar actionBar;
    String appointment_date,appointment_time;
    EditText et_search;
    TextView name,email,contact,txt_patient_date,txt_patient_time,txt_d_name,txt_d_address,txt_d_designation,appo_time,appo_time1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_details);

        actionBar=getSupportActionBar();

        SpannableString s = new SpannableString("Appointment Deatils");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setLogo(R.drawable.bjainicon);
//        et_search = (EditText)findViewById(R.id.et_search);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        name= (TextView) findViewById(R.id.tv_profile_name);
        email= (TextView) findViewById(R.id.email);
        contact= (TextView) findViewById(R.id.contact);
        txt_patient_date= (TextView) findViewById(R.id.txt_patient_date);
        txt_patient_time= (TextView) findViewById(R.id.txt_patient_time);
        txt_d_name= (TextView) findViewById(R.id.txt_d_name);
        txt_d_address= (TextView) findViewById(R.id.txt_d_address);
        txt_d_designation= (TextView) findViewById(R.id.txt_d_designation);
        appo_time   = (TextView) findViewById(R.id.appo_time);
        appo_time1= (TextView) findViewById(R.id.appo_time1);


        try {
            if (MondayFragment.monday == true) {
                appointment_time = MondayFragment.appointment_time;
                appointment_date = MondayFragment.appointment_date;
                appo_time.setText(DoctorAppointment.appointment_date);
                appo_time1.setText(appointment_time);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            if (TuesdayFragment.tuesday==true){
                appointment_time= TuesdayFragment.appointment_time;
                appointment_date = TuesdayFragment.appointment_date;
                appo_time.setText(DoctorAppointment.appointment_date);
                appo_time1.setText(appointment_time);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
         if (WednesdayFragment.wednesday==true){
            appointment_time=WednesdayFragment.appointment_time;
             appointment_date = WednesdayFragment.appointment_date;
             appo_time.setText(DoctorAppointment.appointment_date);
             appo_time1.setText(appointment_time);
        }
    }
    catch (Exception e){
        e.printStackTrace();
    }

        try{
         if (ThursdayFragment.thursday==true){
            appointment_time=ThursdayFragment.appointment_time;
             appointment_date = ThursdayFragment.appointment_date;
             appo_time.setText(DoctorAppointment.appointment_date);
             appo_time1.setText(appointment_time);
        }
    }
    catch (Exception e){
        e.printStackTrace();
    }

        try{
         if (FridayFragment.Friday==true){
            appointment_time=FridayFragment.appointment_time;
             appointment_date = FridayFragment.appointment_date;
             appo_time.setText(DoctorAppointment.appointment_date);
             appo_time1.setText(appointment_time);
        }
     }
     catch (Exception e){
        e.printStackTrace();
    }

        try{
         if (SaturdayFragment.Saturday==true){
            appointment_time= SaturdayFragment.appointment_time;
             appointment_date = SaturdayFragment.appointment_date;
             appo_time.setText(DoctorAppointment.appointment_date);
             appo_time1.setText(appointment_time);
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try
        {
            if (SundayFragment.Sunday==true){
                appointment_time=SundayFragment.appointment_time;
                appointment_date = SundayFragment.appointment_date;
                appo_time.setText(DoctorAppointment.appointment_date);
                appo_time1.setText(appointment_time);
            }
    }
    catch (Exception e){
        e.printStackTrace();
    }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CallServices().execute(ApiConfig.DOCTOR_ADD_APPO_INFO);
            }
        });

        txt_d_name.setText(UsersAdapter_Serach_Doc.reg_name);
        txt_d_address.setText(UsersAdapter_Serach_Doc.reg_address);
        txt_d_designation.setText(UsersAdapter_Serach_Doc.reg_designation);
        /*txt_patient_date.setText(UsersAdapter_Serach_Doc.reg_name);
        txt_patient_time.setText(UsersAdapter_Serach_Doc.reg_name);*/

        actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Appointment Deatils");
        actionBar.setTitle(s);

        name.setText(PreferenceData.getPatientName(getApplicationContext()));
        email.setText(PreferenceData.getPatientEmail(getApplicationContext()));
        contact.setText(PreferenceData.getPatientNumber(getApplicationContext()));
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Log.e("clik", "action bar clicked");
            finish();
        }

        return super.onOptionsItemSelected(item);
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
            pd = new ProgressDialog(AppointmentDetails.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("a_p_id", PreferenceData.getPatientId(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("a_doc_id", UsersAdapter_Serach_Doc.reg_id));
            namevaluepair.add(new BasicNameValuePair("a_time", appointment_time));
            Log.d("sunil","time:-"+MondayFragment.appointment_time);
            namevaluepair.add(new BasicNameValuePair("a_date", DoctorAppointment.appointment_date));
            namevaluepair.add(new BasicNameValuePair("a_status", "22"));

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
                Log.e("result", result.toString());
                startActivityForResult(new Intent(AppointmentDetails.this, ConfirmationActivity.class), 1);
                /*try {

                    *//*JSONObject jsonObject=new JSONObject(result);
                    String msg=jsonObject.optString("a_message");
                    if(msg.equals("Appoiment book already")){
                        Toast.makeText(AppointmentDetails.this,"Appointment Book already",Toast.LENGTH_LONG).show();
                    }
                    else{
                        startActivity(new Intent(AppointmentDetails.this,ConfirmationActivity.class));
                    }*//*
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
//                try {
//
//                    /*Intent returnIntent = new Intent();
//                    returnIntent.putExtra("result1","okay");
//                    setResult(Activity.RESULT_OK,returnIntent);*/
////                    finish();
//                    /*JSONArray jsonArray = new JSONArray(result);
//                    for (int i=0; i<jsonArray.length();i++) {
//                        Log.e("jsonArray", jsonArray.toString());
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    }*/
//                } catch (Exception e) {
//
//                    e.printStackTrace();
//                }

            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
