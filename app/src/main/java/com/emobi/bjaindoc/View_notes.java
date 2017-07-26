package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 8/24/2016.
 */
public class View_notes extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public String id,note_title,note_message,note_date,note_time;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private int itemPosition;
    InfoApps Detailapp;
    ArrayList<String> stringArrayList;
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    TextView toolbar_title;
    ImageView backarrow;
    UsersAdapterNotifi_Doc mAdapterbroad;
    FloatingActionButton fab;
    ActionBar actionBar;
    @TargetApi(Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_patient_broad);
        /*actionBar=getSupportActionBar();
//         mSnackBarLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        SpannableString s = new SpannableString("  Notification");
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
        actionBar.setTitle("  Notification");
        actionBar.setTitle(s);*/
        mSnackBarLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        savechange=(Button)findViewById(R.id.btn_submit);
        backarrow = (ImageView)findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        stringArrayList=new ArrayList<String>();
        listView=(ListView)findViewById(R.id.list);

        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        toolbar_title.setTypeface(tf);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        contactDetails1=new ArrayList<InfoApps>();
        new CallServices().execute(ApiConfig.FCM_NOTIFICATION_DOC);
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
            try {
                pd = new ProgressDialog(View_notes.this);

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

            try {
                if (pd != null) {
                    pd.dismiss();
                }
            }
            catch (Exception e){

            }


            if (result != null) {
                Log.e("result",result.toString());
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        Log.e("2", jsonObject2.toString());
                        String msg = jsonObject2.getString("message");

                        if (msg.equals("Record not available")){
                            Toast.makeText(getApplicationContext(),"no record available",Toast.LENGTH_LONG).show();
//                            finish();
                        }
                        else {
                            id=jsonObject2.getString("note_id");
                            note_title = jsonObject2.getString("note_title");
                            note_message = jsonObject2.getString("note_msg");
                            note_date= jsonObject2.getString("date");
                            note_time= jsonObject2.getString("time");

                            Detailapp = new InfoApps();
                            Detailapp.setEmail_id(id);
//                        Detailapp.setName(sfirst_name);
                            Detailapp.setId(note_title);
                            Detailapp.setName(note_message);
                            Detailapp.setInvo_date(note_date);
                            Detailapp.setN_time(note_time);
                            contactDetails1.add(Detailapp);
                        }


                        //for testing purpose
//                        if(patient_email.equals("Testing@gmail.com")){
//                            Log.e("sunil","sending device token");
//                            Log.e("sunil","device token:-"+device_token);
//                            setNotification(device_token);
//                        }
                    }
                }

                catch (Exception e) {

                    e.printStackTrace();
                }
                mAdapterbroad = new UsersAdapterNotifi_Doc(contactDetails1,getApplicationContext(),mSnackBarLayout);
                mRecyclerView.setAdapter(mAdapterbroad);
//                Log.e("stringArrayList", stringArrayList.toString());
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
                pd = new ProgressDialog(View_notes.this);

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
}
