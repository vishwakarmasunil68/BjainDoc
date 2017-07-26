package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import database.PreferenceData;
import interfaces.AdapterToActivity;

/**
 * Created by Emobi-Android-002 on 8/24/2016.
 */
public class DoctorNotes  extends AppCompatActivity implements AdapterToActivity{
    private ListView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public  static String patient_id,category,message,patient_status;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private int itemPosition;
    InfoApps Detailapp;
    ArrayList<String> stringArrayList;
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    UsersAdapter_note mAdapterbroad;
    Spinner doctorspinner;
    ImageView btn_submit,backarrow;
    ProgressDialog pd;
    ActionBar actionBar;
    ImageView text_medication;
    EditText edtxt_medication;
    TextView toolbar_title;
    String id;
    public DoctorNotes() {
        // Required empty public constructor
    }
    DoctorNotes notes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        backarrow = (ImageView) findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        actionBar=getSupportActionBar();
        notes=this;

        edtxt_medication=(EditText)findViewById(R.id.edtxt_notes);
        mRecyclerView = (ListView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        btn_submit=(ImageView)findViewById(R.id.btn_submit);
        text_medication=(ImageView)findViewById(R.id.text_medication);
        id=UsersAdapter.patient_id;


        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        toolbar_title.setTypeface(tf);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        contactDetails1=new ArrayList<InfoApps>();
        new CallServices(DoctorNotes.this).execute(ApiConfig.VIEWNOTESURL);
        text_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtxt_medication.setText("");
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtxt_medication.getText().toString().length()>0) {
                    new Call1Services().execute(ApiConfig.SEND_DOCTOR_NOTES);
                }
            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("Doctor");
        categories.add("Patient");
        doctorspinner = (Spinner) findViewById(R.id.spinner1doctor);
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.simple_dropdown_item_1line, categories);
        doctorspinner.setAdapter(spinnerArrayAdapter1);
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

    @Override
    public void refreshView() {
//        new CallServices(DoctorNotes.this).execute(ApiConfig.VIEWNOTESURL);
    }

    public class Call1Services extends AsyncTask<String, String, String> {
        String msg_type1= doctorspinner.getSelectedItem().toString();
        String contentstring = edtxt_medication.getText().toString();
        String msg_type="Doctor";

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                pd = new ProgressDialog(DoctorNotes.this);

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            }
            catch (Exception e){

            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("note_msg", contentstring));
            namevaluepair.add(new BasicNameValuePair("p_note_id", id));
            namevaluepair.add(new BasicNameValuePair("note_cat", msg_type));
            namevaluepair.add(new BasicNameValuePair("doc_note_id", PreferenceData.getId(getApplicationContext())));

            /*Log.e("LoginActivity.reg_id",LoginActivity.reg_id);
            Log.e("UtilsValidatedat",UtilsValidate.getCurrentDate());
            Log.e("id",id);
            Log.e("contentstring",LoginActivity.reg_id);*/

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
            try {
                if (pd != null) {
                    pd.dismiss();
                }
            }
            catch (Exception e){

            }


            if (result != null) {

                try {
                    edtxt_medication.setText("");
                    Toast.makeText(getApplicationContext(),"message send successfully", Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    Log.e("result", result.toString());
                    new CallServices(DoctorNotes.this).execute(ApiConfig.VIEWNOTESURL);

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }
    }
    public class CallServices extends AsyncTask<String, String, String> {
        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;
        Activity activity;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            contactDetails1.clear();
            try {
                pd = new ProgressDialog(activity);

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            }
            catch (Exception e){

            }
        }
        CallServices(Activity activity){
                this.activity=activity;
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
                        category = jsonObject2.getString("note_cat");
                        String note_id = jsonObject2.getString("note_id");
                        message = jsonObject2.getString("note_msg");
                        Detailapp = new InfoApps();
//                        Detailapp.setName(sfirst_name);
                        Detailapp.setName(message);
                        Detailapp.setStatus(category);
                        Detailapp.setId(note_id);
                        contactDetails1.add(Detailapp);
//                        Collections.reverse(contactDetails1);

                        //for testing purpose
//                        if(patient_email.equals("Testing@gmail.com")){
//                            Log.e("sunil","sending device token");
//                            Log.e("sunil","device token:-"+device_token);
//                            setNotification(device_token);
//                        }
                    }
                    mAdapterbroad = new UsersAdapter_note(DoctorNotes.this,R.layout.view_doc_adapternote,contactDetails1,notes);
                    mRecyclerView.setAdapter(mAdapterbroad);
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }


        }
    }
}
