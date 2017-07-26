package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;
import interfaces.AdapterToActivity;


/**
 * Created by Emobi-Android-002 on 7/21/2016.
 */
public class ViewPatientListactivation extends AppCompatActivity implements AdapterToActivity {
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public  static String patient_bp,patient_id,patient_name,patient_email,patient_status,patient_pic,
            patient_height,patient_weight,patient_pass,patient_age;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private int itemPosition;
    InfoApps Detailapp;
    ArrayList<String> stringArrayList;
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    ImageView backarrow;
    TextView toolbar_title;
    UsersAdapterActivate1 mAdapter;
    FloatingActionButton fab;
    Toolbar toolbar;
    @TargetApi(Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_patient_activition);
//         mSnackBarLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        /*toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Activation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.bjainicon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SpannableString s = new SpannableString("  Activation");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
*/

        savechange=(Button)findViewById(R.id.btn_submit);
        backarrow = (ImageView)findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        fab=(FloatingActionButton)findViewById(R.id.fab);
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
        new CallServices().execute(ApiConfig.VIEW_ALL_PATIENT);
        fab.hide();
        /*mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
//        mRecyclerView.setOn(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ViewPatientList.this,CustomViewIconTextTabsActivity.class));
//            }
//        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPatientListactivation.this,AddPatientActivity.class));
            }
        });*/
    }

    @Override
    public void refreshView() {
        new CallServices().execute(ApiConfig.VIEW_ALL_PATIENT);
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
            pd = new ProgressDialog(ViewPatientListactivation.this);

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
            if(contactDetails1!=null) {
                contactDetails1.clear();
            }
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
                        patient_pic = jsonObject2.getString("p_photo");
                        patient_bp = jsonObject2.getString("p_bloodgroup");
                        patient_weight = jsonObject2.getString("p_weight");
                        patient_height = jsonObject2.getString("p_height");
                        patient_age = jsonObject2.getString("p_age");
                        patient_pass = jsonObject2.getString("p_login_pass");



                        String  p_medication = jsonObject2.getString("p_medication");
                        String p_alergi = jsonObject2.getString("p_alergi");
                        String  p_digoxin = jsonObject2.getString("p_digoxin");

                        Detailapp = new InfoApps();
//                        Detailapp.setName(sfirst_name);
                        Detailapp.setId(patient_id);
                        Detailapp.setName(patient_name);
                        Detailapp.setEmail_id(patient_email);
                        Detailapp.setStatus(patient_status);
                        Detailapp.setDevice_token(device_token);
                        Detailapp.setAppname(patient_pic);
                        Detailapp.setHeight(patient_height);
                        Detailapp.setBg(patient_bp);
                        Detailapp.setWeight(patient_weight);
                        Detailapp.setPass(patient_pass);
                        Detailapp.setP_medication(p_medication);
                        Detailapp.setAlergi(p_alergi);
                        Detailapp.setP_digoxin(p_digoxin);

                        contactDetails1.add(Detailapp);
                    }
                    mAdapter = new UsersAdapterActivate1(contactDetails1,ViewPatientListactivation.this);
                    mRecyclerView.setAdapter(mAdapter);
                    Log.e("stringArrayList", stringArrayList.toString());
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
                pd = new ProgressDialog(ViewPatientListactivation.this);

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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}