package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 7/21/2016.
 */
public class ViewPatientList_broad extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public  static String patient_id,patient_name,patient_email,patient_status;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private int itemPosition;
    InfoApps Detailapp;
ArrayList<String> stringArrayList;
    private ProgressDialog pDialog;
//    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    UsersAdapter_broad mAdapterbroad;
    FloatingActionButton fab;
    @TargetApi(Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_patient_broad);
//         mSnackBarLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        savechange=(Button)findViewById(R.id.btn_submit);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        stringArrayList=new ArrayList<String>();
        listView=(ListView)findViewById(R.id.list);
        contactDetails1=new ArrayList<InfoApps>();
        new CallServices().execute(ApiConfig.VIEW_BROADCAST_MESSAGE);
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
            pd = new ProgressDialog(ViewPatientList_broad.this);

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
                        Log.e("2", jsonObject2.toString());
                         patient_id = jsonObject2.getString("p_id");
                         patient_name = jsonObject2.getString("p_name");
                        patient_email = jsonObject2.getString("p_login_id");
                        patient_status = jsonObject2.getString("p_status");
                        String device_token=jsonObject2.getString("p_device_token");
                        Detailapp = new InfoApps();
//                        Detailapp.setName(sfirst_name);
                        Detailapp.setId(patient_id);
                        Detailapp.setName(patient_name);
                        Detailapp.setEmail_id(patient_email);
                        Detailapp.setStatus(patient_status);
                        Detailapp.setDevice_token(device_token);
                        contactDetails1.add(Detailapp);
                        mAdapterbroad = new UsersAdapter_broad(contactDetails1,getApplicationContext());
                        mRecyclerView.setAdapter(mAdapterbroad);
                        Log.e("stringArrayList", stringArrayList.toString());

                        //for testing purpose
//                        if(patient_email.equals("Testing@gmail.com")){
//                            Log.e("sunil","sending device token");
//                            Log.e("sunil","device token:-"+device_token);
//                            setNotification(device_token);
//                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }
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
                pd = new ProgressDialog(ViewPatientList_broad.this);

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