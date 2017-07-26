package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 7/21/2016.
 */
public class ViewAppointmentList extends AppCompatActivity  {
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public  static String patient_id,patient_name,patient_email,patient_status,patient_pic;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private int itemPosition;
    InfoApps Detailapp;
ArrayList<String> stringArrayList;
    private ProgressDialog pDialog;
//    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    UsersAdapterAppointment mAdapter;
    ActionBar actionBar;
    FloatingActionButton fab;
    @TargetApi(Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewappointmentpatient);
        mSnackBarLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        actionBar=getSupportActionBar();
        SpannableString s = new SpannableString("Appointments");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(R.drawable.bjainicon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Appointments");
        actionBar.setTitle(s);

        savechange=(Button)findViewById(R.id.btn_submit);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        stringArrayList=new ArrayList<String>();
        listView=(ListView)findViewById(R.id.list);
        contactDetails1=new ArrayList<InfoApps>();


        Log.e("doc_id",PreferenceData.getId(getApplicationContext()));
        new CallServices().execute(ApiConfig.checkdoctorappoiment);


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
            pd = new ProgressDialog(ViewAppointmentList.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
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
                Log.e("result",result.toString());
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String message=jsonObject2.getString("p_message");
                        if (message.equals("Not Book any Appoiment")){
                            Toast.makeText(ViewAppointmentList.this,"Not Book any Appoiment",Toast.LENGTH_LONG).show();
                        }
                        else {

                            String  p_id = jsonObject2.getString("p_id");
                            String a_date = jsonObject2.getString("a_date");
                            String a_time = jsonObject2.getString("a_time");
                            String p_name = jsonObject2.getString("p_name");
                            String p_mob = jsonObject2.getString("p_mob");
                            String p_device_token = jsonObject2.getString("p_device_token");
                            Log.e("2", jsonObject2.toString());

                            Detailapp = new InfoApps();

                            Detailapp.setId(p_id);
                            Detailapp.setNumber(p_mob);
                            Detailapp.setName(p_name);
                            Detailapp.setInvo_date(a_date);
                            Detailapp.setAppotime(a_time);
                            Detailapp.setDevice_token(p_device_token);
                            contactDetails1.add(Detailapp);
                            mAdapter = new UsersAdapterAppointment(contactDetails1,getApplicationContext(),mSnackBarLayout);
                            mRecyclerView.setAdapter(mAdapter);

                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }
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

    public void setNotification(final String device_token){
        new AsyncTask<String,String,String>(){
            ProgressDialog pd;
            ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
            String result;
            String tag;

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                pd = new ProgressDialog(ViewAppointmentList.this);

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            }

            @SuppressWarnings("deprecation")
            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub
                namevaluepair.add(new BasicNameValuePair("apikey", "AIzaSyDwcmzEC5zlcz3zcBND5JvakMOtkSZY18s"));
                namevaluepair.add(new BasicNameValuePair("regtoken", device_token)    );
                namevaluepair.add(new BasicNameValuePair("message", "HEllO SHUBHAm"));
                try {

                    result = Util.executeHttpPost(ApiConfig.SEND_MESSAGE, namevaluepair);

                    Log.e("sunil",result.toString());
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
                    Log.e("sunil","sent");
                }

            }
        }.execute();
    }
}