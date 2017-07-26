package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.pojo.BroadcastPOJO;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;
import patient_side.UsersAdapter_Broadcast;

/**
 * Created by Emobi-Android-002 on 8/24/2016.
 */
public class BroadCast_Docview extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    EditText edtxt_medication;
    ImageView btn_submit;
    public  static String patient_id,patient_name,patient_email,patient_status;
    public static ArrayList<InfoApps> contactDetails1;
    UsersAdapter_Broadcast mAdapterbroad;
    InfoApps Detailapp;
    FloatingActionButton fab;
    ActionBar actionBar;
    LinearLayout ll_scroll;
    ScrollView scroll_view;
    @TargetApi(Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.broadcastdoctorside);
//         mSnackBarLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        scroll_view = (ScrollView) findViewById(R.id.scroll_view);
        ll_scroll = (LinearLayout) findViewById(R.id.ll_scroll);
        SpannableString s = new SpannableString("  Broadcast messages");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(R.drawable.bjainicon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("  Broadcast messages");
        actionBar.setTitle(s);
        btn_submit=(ImageView)findViewById(R.id.btn_submit);
        edtxt_medication=(EditText)findViewById(R.id.edtxt_medication);

        contactDetails1=new ArrayList<InfoApps>();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(BroadCast_Docview.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        new CallServices().execute(ApiConfig.VIEW_BROADCAST_MESSAGE);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtxt_medication.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Message can not be blank",Toast.LENGTH_LONG).show();
                }
                else {
                    new CallServicesMessage().execute(ApiConfig.SEND_BROADCAST_MESSAGE);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
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
    private final String TAG=getClass().getSimpleName();
    public class CallServices extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(BroadCast_Docview.this);

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

            contactDetails1.clear();
            if (pd != null) {
                pd.dismiss();
                try {
                    Log.d(TAG,"result:-"+result);
                    try{
                        Gson gson=new Gson();
                        BroadcastPOJO[] broadcastPOJOs=gson.fromJson(result,BroadcastPOJO[].class);
                        parseBroadcastMessages(broadcastPOJOs);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
//                    JSONArray jsonArray = new JSONArray(result);
//                    Log.e("Post Method", result.toString());
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                        Log.e("2", jsonObject2.toString());
//                        String med_date = jsonObject2.getString("bro_date");
//                        String med_mess = jsonObject2.getString("bro_mess");
//                        String med_time = jsonObject2.getString("bro_time");
//
//                        Detailapp = new InfoApps();
//                        Detailapp.setNumber(med_date);
//                        Detailapp.setDataAdd(med_mess);
//                        Detailapp.setName(med_time);
//						/*Detailapp.setId(patient_id);
//						Detailapp.setName(patient_name);
//						Detailapp.setEmail_id(patient_email);
//						Detailapp.setStatus(patient_status);*/
//                        contactDetails1.add(Detailapp);
//                        Collections.reverse(contactDetails1);
//                        mAdapterbroad = new UsersAdapter_Broadcast(contactDetails1, BroadCast_Docview.this);
//                        mRecyclerView.setAdapter(mAdapterbroad);
//                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }


    public void parseBroadcastMessages(BroadcastPOJO[] pojos){
        List<BroadcastPOJO> list_broad_cast=new ArrayList<>();
        for(BroadcastPOJO pojo:pojos){
            list_broad_cast.add(pojo);
        }
        Collections.reverse(list_broad_cast);

        for(BroadcastPOJO broadcastPOJO:list_broad_cast){
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.view_patientbroadcastmessage_adapter, null);
            TextView text_broad= (TextView) view.findViewById(R.id.text_broad);
            TextView tv_medication= (TextView) view.findViewById(R.id.tv_medication);
            TextView tv_medication_time= (TextView) view.findViewById(R.id.tv_medication_time);
            CircleImageView img_profile= (CircleImageView) view.findViewById(R.id.img_profile);

            text_broad.setText(broadcastPOJO.getBro_mess());
            tv_medication.setText(broadcastPOJO.getBro_time());
            tv_medication_time.setText(broadcastPOJO.getBro_date());

            Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + PreferenceData.getPhoto(getApplicationContext())).into(img_profile);
            ll_scroll.addView(view);
        }
        scroll_view.post(new Runnable() {
            @Override
            public void run() {
                scroll_view.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
    public class CallServicesMessage extends AsyncTask<String, String, String> {
        String contentstring = edtxt_medication.getText().toString();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(BroadCast_Docview.this);

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
            SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
            Date d=new Date();
            namevaluepair.add(new BasicNameValuePair("date", sdf.format(d)));
            namevaluepair.add(new BasicNameValuePair("time", UtilsValidate.getCurrentTime()));
            namevaluepair.add(new BasicNameValuePair("chat_msg", contentstring));
            namevaluepair.add(new BasicNameValuePair("chat_title", "Broadcast"));
            namevaluepair.add(new BasicNameValuePair("direction", "false"));


            //namevaluepair.add(new BasicNameValuePair("cat", "HAIR"));

            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

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
                    /*JSONObject objresponse = new JSONObject(result);
                    String message = objresponse.getString("message");
                    if (message.equalsIgnoreCase("invalid")) {
                        UtilsValidate.showError(BroadCast_Msg_Class.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    } else {
                        String reg_id = objresponse.getString("reg_id");

                        *//*Intent intent = new Intent(Medication.this, DoctorAccount.class);
                        startActivity(intent);*//*

                    }*/
                    edtxt_medication.setText("");
                    Toast.makeText(BroadCast_Docview.this,"message send successfully",Toast.LENGTH_SHORT).show();
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                    catch (Exception e){
                        e.toString();
                    }
                    new CallServices().execute(ApiConfig.VIEW_BROADCAST_MESSAGE);
                    Log.e("result", result.toString());


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }
    }


}




