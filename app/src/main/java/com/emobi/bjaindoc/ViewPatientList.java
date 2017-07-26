package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.activity.PatientProfileActivity;
import com.emobi.bjaindoc.pojo.chat.ChatResultPOJO;
import com.emobi.bjaindoc.pojo.patient.PatientPOJO;
import com.emobi.bjaindoc.pojo.patient.PatientResultPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.emobi.bjaindoc.utls.ToastClass;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.PreferenceData;
import interfaces.FragmentToActivity;

/**
 * Created by Emobi-Android-002 on 7/21/2016.
 */
public class ViewPatientList extends AppCompatActivity implements WebServicesCallBack {
    private ListView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public static String patient_id, patient_name, patient_email, patient_status, patient_pic;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private int itemPosition;
    InfoApps Detailapp;
    ArrayList<String> stringArrayList;
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    UsersAdapter mAdapter;
    ImageView backarrow, toolbar_image4, drawer_ham, toolbar_image1;
    TextView toolbar_title;
    EditText edsearch;
    CallServicesSearchal call;
    ActionBar actionBar;
    FloatingActionButton fab_journey1, fab_journey2;
    FloatingActionMenu fab;

    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    String result = "";

    @TargetApi(Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_patint);
        ButterKnife.bind(this);
//         mSnackBarLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        actionBar = getSupportActionBar();
        SpannableString s = new SpannableString("  Patient list");
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
        actionBar.setTitle("  Patient list");
        actionBar.setTitle(s);
        savechange = (Button) findViewById(R.id.btn_submit);
        edsearch = (EditText) findViewById(R.id.edsearch);
        backarrow = (ImageView) findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        fab_journey1 = (FloatingActionButton) findViewById(R.id.fab_journey1);
        fab_journey2 = (FloatingActionButton) findViewById(R.id.fab_journey2);
        fab = (FloatingActionMenu) findViewById(R.id.fab);
        mRecyclerView = (ListView) findViewById(R.id.recycler_view);

        contactDetails1 = new ArrayList<InfoApps>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        drawer_ham = (ImageView) findViewById(R.id.drawer_ham);
        toolbar_image1 = (ImageView) findViewById(R.id.toolbar_image1);
        toolbar_image4 = (ImageView) findViewById(R.id.toolbar_image4);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            result = bundle.getString("result");
        }

        toolbar_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edsearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edsearch, InputMethodManager.SHOW_FORCED);
                drawer_ham.setVisibility(View.GONE);
                toolbar_image1.setVisibility(View.GONE);
                edsearch.setVisibility(View.VISIBLE);
                toolbar_image4.setVisibility(View.VISIBLE);
                toolbar_title.setVisibility(View.GONE);

//                edsearch.requestFocus();
            }
        });

        toolbar_image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar_image1.setVisibility(View.VISIBLE);
                toolbar_image4.setVisibility(View.GONE);
                edsearch.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                drawer_ham.setVisibility(View.VISIBLE);
            }
        });
        stringArrayList = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.list);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        toolbar_title.setTypeface(tf);


        edsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                notify();
//                new CallServices().execute(ApiConfig.FIND_DOCTOR_URL);
                String s1 = edsearch.getText().toString();
                Log.d("sunil", s.toString());
                if (s.length() > 0) {
                    contactDetails1.clear();

                    try {
                        isInteger(s1);
                    } catch (Exception e) {
                        Log.d("sunil", e.toString());
                    }


                } else {
                    if (edsearch.getText().toString().equals(" ")) {
                        contactDetails1.clear();
                        try {
//                        call.cancel(true);
                            new CallServicesSearchal1(edsearch.getText().toString()).execute(ApiConfig.FIND_PATIENT_URL, edsearch.getText().toString());
                        } catch (Exception e) {
                            Log.d("sunil", e.toString());
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edsearch.getText().length() < 1) {
                    contactDetails1.clear();
                    toolbar_title.setVisibility(View.VISIBLE);
                    edsearch.setVisibility(View.GONE);
                }
            }
        });
        edsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    String s = edsearch.getText().toString();

                    Log.d("clicked", "clickd");
                    /*try{
                        Integer Char = Integer.parseInt(s);
                        return true;
                    }catch (Exception  e){
                        return false;
                    }*/
                    contactDetails1.clear();
                    try {
                        call.cancel(true);
                    } catch (Exception e) {
                        Log.d("sunil", e.toString());
                    }

                    isInteger(s);

                    //do something
                }
                return false;
            }
        });
        edsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                notify();
//                new CallServices().execute(ApiConfig.FIND_DOCTOR_URL);

                Log.d("sunil", s.toString());
                if (s.length() < 1) {
                    contactDetails1.clear();
                    try {
                        call.cancel(true);
                    } catch (Exception e) {
                        Log.d("sunil", e.toString());
                    }
                    new CallServicesSearchal3().execute(ApiConfig.FIND_PATIENT_URL, edsearch.getText().toString());


                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edsearch.getText().length() < 1) {
                    contactDetails1.clear();
                    toolbar_title.setVisibility(View.VISIBLE);
                    edsearch.setVisibility(View.GONE);
                }
            }
        });

        Log.e("doc_id", PreferenceData.getId(getApplicationContext()));
//        new CallServices().execute(ApiConfig.VIEW_PATIENT);

        getPatientList();
//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
//                if (dy > 0 ||dy<0 && fab.isShown())
//                    fab.hide();
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
//                    fab.show();
//                }
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
//        mRecyclerView.setOn(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ViewPatientList.this,CustomViewIconTextTabsActivity.class));
//            }
//        });

        fab_journey2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPatientList.this, AddExistPatientActivity.class));
                finish();
            }
        });

        fab_journey1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPatientList.this, AddPatientActivity.class));
                finish();
            }
        });
    }

    private final String TAG = getClass().getSimpleName();
    private final static String GET_PATIENT_LIST = "get_patient_list";

    public void getPatientList() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
        Log.d(TAG,"doc_id:-"+PreferenceData.getId(getApplicationContext()));
        new WebServiceBase(nameValuePairs, this, GET_PATIENT_LIST).execute(ApiConfig.GET_PATIENTS_URL);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_PATIENT_LIST:
                parsePatientList(response);
                break;
        }
    }

    List<PatientResultPOJO> list_of_patients;

    public void parsePatientList(String response) {
        Log.d(TAG, "response:-" + response);
        try {
            Gson gson = new Gson();
            PatientPOJO patientPOJO = gson.fromJson(response, PatientPOJO.class);
            if (patientPOJO.getSuccess().equals("true")) {
                list_of_patients = patientPOJO.getList_patients();
                ShowPatients(patientPOJO.getList_patients());
            } else {
                ToastClass.ShowLongToast(getApplicationContext(), "No Patient Found");
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }


    public void ShowPatients(final List<PatientResultPOJO> list_patients) {
        for (int i = 0; i < list_patients.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_patient_list, null);
            LinearLayout ll_patient = (LinearLayout) view.findViewById(R.id.ll_patient);
            ImageView iv_profile = (ImageView) view.findViewById(R.id.iv_profile);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_email = (TextView) view.findViewById(R.id.tv_email);

            tv_name.setText(list_patients.get(i).getP_name());
            tv_email.setText(list_patients.get(i).getP_login_id());
            String image_url = "";
            if (list_patients.get(i).getP_photo().contains("upload/")) {
                image_url = "http://www.bjain.com/doctor/" + list_patients.get(i).getP_photo();
            } else {
                image_url = "http://www.bjain.com/doctor/upload/" + list_patients.get(i).getP_photo();
            }
            Picasso.with(getApplicationContext())
                    .load(image_url)
                    .error(R.drawable.ic_action_person)
                    .placeholder(R.drawable.ic_action_person)
                    .into(iv_profile);

            final int finalI = i;
            ll_patient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ViewPatientList.this, PatientProfileActivity.class);
                    intent.putExtra("patient", list_patients.get(finalI));
                    startActivity(intent);
                }
            });

            ll_scroll.addView(view);
        }

        if (result != null && list_of_patients != null) {

            try {
                Gson gson = new Gson();
                ChatResultPOJO chatResultPOJO = gson.fromJson(result, ChatResultPOJO.class);
                for (PatientResultPOJO pojo : list_of_patients) {
                    if (chatResultPOJO.getChat_p_id().equals(pojo.getP_id())) {
                        Intent intent = new Intent(ViewPatientList.this, PatientProfileActivity.class);
                        intent.putExtra("patient", pojo);
                        intent.putExtra("chat", chatResultPOJO);
                        startActivity(intent);
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        } else {
            Log.d(TAG, "result is null");
        }
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
            pd = new ProgressDialog(ViewPatientList.this);

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
            Log.d(TAG, "doc_d:-" + PreferenceData.getId(getApplicationContext()));
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
                Log.d(TAG, "result:-" + result.toString());
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        Log.e("2", jsonObject2.toString());
                        if (jsonObject2.toString().contains("chat_msg")) {
                            patient_id = jsonObject2.getString("p_id");
                            patient_name = jsonObject2.getString("p_name");
                            String p_login_id = jsonObject2.getString("p_login_id");
                            patient_status = jsonObject2.getString("p_status");
                            String p_pass = jsonObject2.getString("p_login_pass");
                            String device_token = jsonObject2.getString("p_device_token");
                            patient_pic = jsonObject2.getString("p_photo");
                            patient_email = jsonObject2.getString("chat_msg");
                            String u_msg = jsonObject2.getString("purgentchat");
                            String chat_file = jsonObject2.getString("chat_file");
                            String p_age = jsonObject2.getString("p_age");
                            String chat_lasttime = jsonObject2.getString("time");
                                /*PreferenceData.setPatient_Device_Token(getApplicationContext(), device_token);
                                PreferenceData.setchatPatient_id(getApplicationContext(), patient_id);*/

                            Detailapp = new InfoApps();

                            Detailapp.setId(patient_id);
                            Detailapp.setName(patient_name);
                            Detailapp.setN_time(chat_lasttime);
                            Detailapp.setBg(p_login_id);
                            Detailapp.setStatus(patient_status);
                            Detailapp.setAppname(patient_pic);
                            Detailapp.setPass(p_pass);
                            if (!patient_email.isEmpty()) {
                                Detailapp.setEmail_id(patient_email);
                            } else if (!u_msg.isEmpty()) {
                                Detailapp.setEmail_id(u_msg);
                            } else if (!chat_file.isEmpty()) {
                                Detailapp.setEmail_id("photo");
                            }
                            Detailapp.setEmail_id(patient_email);

                            Detailapp.setDevice_token(device_token);
                            Detailapp.setAge(p_age);
                            contactDetails1.add(Detailapp);
                        } else {
                            patient_id = jsonObject2.getString("p_id");
                            patient_name = jsonObject2.getString("p_name");
                            patient_status = jsonObject2.getString("p_status");
                            String p_login_id = jsonObject2.getString("p_login_id");
                            String p_age = jsonObject2.getString("p_age");
                            String p_pass = jsonObject2.getString("p_login_pass");
                            String device_token = jsonObject2.getString("p_device_token");
                            patient_pic = jsonObject2.getString("p_photo");
                                /*PreferenceData.setPatient_Device_Token(getApplicationContext(), device_token);
                                PreferenceData.setchatPatient_id(getApplicationContext(), patient_id);*/

                            Detailapp = new InfoApps();

                            Detailapp.setId(patient_id);
                            Detailapp.setName(patient_name);
                            Detailapp.setStatus(patient_status);
                            Detailapp.setAppname(patient_pic);
                            Detailapp.setPass(p_pass);
                            Detailapp.setEmail_id("");
                            Detailapp.setBg(p_login_id);
                            Detailapp.setDevice_token(device_token);
                            Detailapp.setAge(p_age);
                            contactDetails1.add(Detailapp);
                        }
//                        new CallServiceslastchat(Detailapp).execute(ApiConfig.VIEW_LASTCHAT);
                    }

                    mAdapter = new UsersAdapter(ViewPatientList.this, R.layout.view_patient_adapter, contactDetails1);
                    mRecyclerView.setAdapter(mAdapter);
                    Log.e("stringArrayList", contactDetails1.size() + "");
                    Log.e("stringArrayList", contactDetails1.toString());
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }
    }

    public class CallServicesSearchal extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag, s;

        CallServicesSearchal(String s) {
            this.s = s;

        }

        String text = s;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {


                pd = new ProgressDialog(ViewPatientList.this);

                pd.setMessage("Searching..");
                pd.setIndeterminate(false);
                pd.setCancelable(true);
                pd.show();
            } catch (Exception e) {
                e.toString();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (!isCancelled()) {
                namevaluepair.add(new BasicNameValuePair("p_name", s));
                namevaluepair.add(new BasicNameValuePair("p_mob", ""));
                namevaluepair.add(new BasicNameValuePair("p_login_id", ""));
                namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));

                try {

                    result = Util.executeHttpPost(params[0], namevaluepair);

                    Log.e("result", result.toString());

                } catch (Exception e) {

                    e.printStackTrace();

                }
                return result;
            } else {
                return null;
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d("sunil", "canceled");
            try {
                if (pd != null) {
                    pd.dismiss();
                }
            } catch (Exception e) {

            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
//            if (pd != null) {
//                pd.dismiss();
            if (result != null) {
                try {
                    contactDetails1.clear();
                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String dmsg = jsonObject2.getString("p_message");
                        if (dmsg.equals("No Rrecord Available")) {
                            contactDetails1.clear();
                        } else {
                            Log.e("2", jsonObject2.toString());
                            if (jsonObject2.toString().contains("chat_msg")) {
                                patient_id = jsonObject2.getString("p_id");
                                patient_name = jsonObject2.getString("p_name");
                                String p_login_id = jsonObject2.getString("p_login_id");
                                patient_status = jsonObject2.getString("p_status");
                                String p_pass = jsonObject2.getString("p_login_pass");
                                String device_token = jsonObject2.getString("p_device_token");
                                patient_pic = jsonObject2.getString("p_photo");
                                patient_email = jsonObject2.getString("chat_msg");
                                String u_msg = jsonObject2.getString("purgentchat");
                                String chat_file = jsonObject2.getString("chat_file");
                                String p_age = jsonObject2.getString("p_age");
                                /*PreferenceData.setPatient_Device_Token(getApplicationContext(), device_token);
                                PreferenceData.setchatPatient_id(getApplicationContext(), patient_id);*/

                                Detailapp = new InfoApps();

                                Detailapp.setId(patient_id);
                                Detailapp.setName(patient_name);
                                Detailapp.setBg(p_login_id);
                                Detailapp.setStatus(patient_status);
                                Detailapp.setAppname(patient_pic);
                                Detailapp.setPass(p_pass);
                                if (!patient_email.isEmpty()) {
                                    Detailapp.setEmail_id(patient_email);
                                } else if (!u_msg.isEmpty()) {
                                    Detailapp.setEmail_id(u_msg);
                                } else if (!chat_file.isEmpty()) {
                                    Detailapp.setEmail_id("photo");
                                }
                                Detailapp.setEmail_id(patient_email);

                                Detailapp.setDevice_token(device_token);
                                Detailapp.setAge(p_age);
                                contactDetails1.add(Detailapp);
                            } else {
                                patient_id = jsonObject2.getString("p_id");
                                patient_name = jsonObject2.getString("p_name");
                                patient_status = jsonObject2.getString("p_status");
                                String p_login_id = jsonObject2.getString("p_login_id");
                                String p_age = jsonObject2.getString("p_age");
                                String p_pass = jsonObject2.getString("p_login_pass");
                                String device_token = jsonObject2.getString("p_device_token");
                                patient_pic = jsonObject2.getString("p_photo");
                                /*PreferenceData.setPatient_Device_Token(getApplicationContext(), device_token);
                                PreferenceData.setchatPatient_id(getApplicationContext(), patient_id);*/

                                Detailapp = new InfoApps();

                                Detailapp.setId(patient_id);
                                Detailapp.setName(patient_name);
                                Detailapp.setStatus(patient_status);
                                Detailapp.setAppname(patient_pic);
                                Detailapp.setPass(p_pass);
                                Detailapp.setEmail_id("");
                                Detailapp.setBg(p_login_id);
                                Detailapp.setDevice_token(device_token);
                                Detailapp.setAge(p_age);
                                contactDetails1.add(Detailapp);
                            }

                        }
                        if (contactDetails1.size() > 0) {
                            mAdapter = new UsersAdapter(ViewPatientList.this, R.layout.view_patient_adapter, contactDetails1);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {

                            Toast.makeText(ViewPatientList.this, "Sorry no result found", Toast.LENGTH_LONG).show();
                            mRecyclerView.setVisibility(View.INVISIBLE);
                            mAdapter.notifyDataSetChanged();
                            contactDetails1.clear();

                        }


                    /*if(contactDetails1.size()>0) {
                        text_result.setVisibility(View.GONE);
                        mAdapterbroad = new UsersAdapter_Serach_Doc(contactDetails1, Search_Doctor.this);
                        mRecyclerView.setAdapter(mAdapterbroad);
                    }
                    else{
                        Toast.makeText(Search_Doctor.this,"Sorry no result found",Toast.LENGTH_LONG).show();
                        text_result.setVisibility(View.VISIBLE);
                        text_result.setTypeface(tf);
                    }*/
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            try {
                if (pd != null) {
                    pd.dismiss();
                }
            } catch (Exception e) {

            }
        }
    }

    public boolean isInteger(String s) {
        try {

            Integer.parseInt(s);
            mRecyclerView.setVisibility(View.VISIBLE);
            Log.e("try", "try");

            new CallServicesSearchal1(s).execute(ApiConfig.FIND_PATIENT_URL);

        } catch (NumberFormatException e) {
            if (s.contains("@")) {
                Log.e("catch", "catch@");
                mRecyclerView.setVisibility(View.VISIBLE);
                new CallServicesSearchal2(s).execute(ApiConfig.FIND_PATIENT_URL);
            } else {
                Log.e("try", "catchstri");
                mRecyclerView.setVisibility(View.VISIBLE);
                new CallServicesSearchal(s).execute(ApiConfig.FIND_PATIENT_URL);
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main1, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        int mNotifCount = 0;
//        View count = menu.findItem(R.id.action_notification).getActionView();
////        Home_Fragment.find.setText(String.valueOf(mNotifCount));
//
//        if (searchItem != null) {
//            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//
//            // use this method for search process
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    // use this method when query submitted
////                    Toast.makeText(Search_Doctor.this, query, Toast.LENGTH_SHORT).show();
//////                    Log.d("sunil",query.toString());
////                    if (query.length()>0) {
////                        contactDetails1.clear();
////                        try {
////                            call.cancel(true);
////                        } catch (Exception e) {
////                            Log.d("sunil", e.toString());
////                        }
////                        call = new CallServices();
////                        call.execute(ApiConfig.FIND_DOCTOR_URL, query.toString());
////                    }
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    // use this method for auto complete search process
//                    try {
////                            Toast.makeText(Search_Doctor.this, newText, Toast.LENGTH_SHORT).show();
////                    Log.d("sunil",query.toString());
//                        if (newText.length() > 0) {
////                                contactDetails1.clear();
//                            try {
////                                new CallServicesSearchal(newText).execute(ApiConfig.FIND_PATIENT_URL);
//                                mRecyclerView.setVisibility(View.VISIBLE);
//                                isInteger(newText);
//                            } catch (Exception e) {
//                                Log.d("sunil", e.toString());
//                            }
////                            new CallServicesSearchal1().execute(ApiConfig.FIND_PATIENT_URL, newText.toString());
//                        } else if (newText.equals("")) {
//                            mRecyclerView.setVisibility(View.VISIBLE);
//                            new CallServicesSearchal3().execute(ApiConfig.FIND_PATIENT_URL);
//                        }
//                    } catch (Exception e) {
//                        Log.d("sunil", e.toString());
//                    }
//                    return false;
//                }
//            });
//
//        } else {
//
//            try {
//                contactDetails1.clear();
//            } catch (Exception e) {
//                Log.d("sunil", e.toString());
//            }
//        }
//
//        return super.onCreateOptionsMenu(menu);
//    }

    public class CallServicesSearchal1 extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;
        String s;

        CallServicesSearchal1(String s) {
            this.s = s;

        }

        String text = s;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            contactDetails1.clear();
            pd = new ProgressDialog(ViewPatientList.this);

            pd.setMessage("Searching..");
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (!isCancelled()) {
                namevaluepair.add(new BasicNameValuePair("p_name", ""));
                namevaluepair.add(new BasicNameValuePair("p_mob", s));
                namevaluepair.add(new BasicNameValuePair("p_login_id", ""));
                namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));

                try {

                    result = Util.executeHttpPost(params[0], namevaluepair);

                    Log.e("result", result.toString());

                } catch (Exception e) {

                    e.printStackTrace();

                }
                return result;
            } else {
                return null;
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d("sunil", "canceled");
            try {
                if (pd != null) {
                    pd.dismiss();
                }
            } catch (Exception e) {

            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
//            if (pd != null) {
//                pd.dismiss();
            if (result != null) {
                try {

                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String dmsg = jsonObject2.getString("p_message");
                        if (dmsg.equals("No Rrecord Available")) {
                            contactDetails1.clear();
                        } else {
                            Log.e("2", jsonObject2.toString());
                            if (jsonObject2.toString().contains("chat_msg")) {
                                patient_id = jsonObject2.getString("p_id");
                                patient_name = jsonObject2.getString("p_name");
                                String p_login_id = jsonObject2.getString("p_login_id");
                                patient_status = jsonObject2.getString("p_status");
                                String p_pass = jsonObject2.getString("p_login_pass");
                                String device_token = jsonObject2.getString("p_device_token");
                                patient_pic = jsonObject2.getString("p_photo");
                                patient_email = jsonObject2.getString("chat_msg");
                                String u_msg = jsonObject2.getString("purgentchat");
                                String chat_file = jsonObject2.getString("chat_file");
                                String p_age = jsonObject2.getString("p_age");
                                /*PreferenceData.setPatient_Device_Token(getApplicationContext(), device_token);
                                PreferenceData.setchatPatient_id(getApplicationContext(), patient_id);*/

                                Detailapp = new InfoApps();

                                Detailapp.setId(patient_id);
                                Detailapp.setName(patient_name);
                                Detailapp.setBg(p_login_id);
                                Detailapp.setStatus(patient_status);
                                Detailapp.setAppname(patient_pic);
                                Detailapp.setPass(p_pass);
                                if (!patient_email.isEmpty()) {
                                    Detailapp.setEmail_id(patient_email);
                                } else if (!u_msg.isEmpty()) {
                                    Detailapp.setEmail_id(u_msg);
                                } else if (!chat_file.isEmpty()) {
                                    Detailapp.setEmail_id("photo");
                                }
                                Detailapp.setEmail_id(patient_email);


                                Detailapp.setDevice_token(device_token);
                                Detailapp.setAge(p_age);
                                contactDetails1.add(Detailapp);
                            } else {
                                patient_id = jsonObject2.getString("p_id");
                                patient_name = jsonObject2.getString("p_name");
                                patient_status = jsonObject2.getString("p_status");
                                String p_login_id = jsonObject2.getString("p_login_id");
                                String p_age = jsonObject2.getString("p_age");
                                String p_pass = jsonObject2.getString("p_login_pass");
                                String device_token = jsonObject2.getString("p_device_token");
                                patient_pic = jsonObject2.getString("p_photo");
                                /*PreferenceData.setPatient_Device_Token(getApplicationContext(), device_token);
                                PreferenceData.setchatPatient_id(getApplicationContext(), patient_id);*/

                                Detailapp = new InfoApps();

                                Detailapp.setId(patient_id);
                                Detailapp.setName(patient_name);
                                Detailapp.setStatus(patient_status);
                                Detailapp.setAppname(patient_pic);
                                Detailapp.setPass(p_pass);
                                Detailapp.setEmail_id("");
                                Detailapp.setBg(p_login_id);
                                Detailapp.setDevice_token(device_token);
                                Detailapp.setAge(p_age);
                                contactDetails1.add(Detailapp);
                            }

                        }
                        if (contactDetails1.size() > 0) {
                            mAdapter = new UsersAdapter(ViewPatientList.this, R.layout.view_patient_adapter, contactDetails1);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(ViewPatientList.this, "Sorry no result found", Toast.LENGTH_LONG).show();
                            mRecyclerView.setVisibility(View.INVISIBLE);
                            contactDetails1.clear();
                        }
                    }

                    /*if(contactDetails1.size()>0) {
                        text_result.setVisibility(View.GONE);
                        mAdapterbroad = new UsersAdapter_Serach_Doc(contactDetails1, Search_Doctor.this);
                        mRecyclerView.setAdapter(mAdapterbroad);
                    }
                    else{
                        Toast.makeText(Search_Doctor.this,"Sorry no result found",Toast.LENGTH_LONG).show();
                        text_result.setVisibility(View.VISIBLE);
                        text_result.setTypeface(tf);
                    }*/
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            try {
                if (pd != null) {
                    pd.dismiss();
                }
            } catch (Exception e) {

            }
        }
    }

    public class CallServicesSearchal2 extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;
        String s;

        CallServicesSearchal2(String s) {
            this.s = s;

        }

        String text = s;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            contactDetails1.clear();
            try {
                pd = new ProgressDialog(ViewPatientList.this);

                pd.setMessage("Searching..");
                pd.setIndeterminate(false);
                pd.setCancelable(true);
                pd.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (!isCancelled()) {
                namevaluepair.add(new BasicNameValuePair("p_name", ""));
                namevaluepair.add(new BasicNameValuePair("p_mob", ""));
                namevaluepair.add(new BasicNameValuePair("p_login_id", s));
                namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));

                try {

                    result = Util.executeHttpPost(params[0], namevaluepair);

                    Log.e("result", result.toString());

                } catch (Exception e) {

                    e.printStackTrace();

                }
                return result;
            } else {
                return null;
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d("sunil", "canceled");
            try {
                if (pd != null) {
                    pd.dismiss();
                }
            } catch (Exception e) {

            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
//            if (pd != null) {
//                pd.dismiss();
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String dmsg = jsonObject2.getString("p_message");
                        if (dmsg.equals("No Rrecord Available")) {
                            contactDetails1.clear();
                        } else {
                            Log.e("2", jsonObject2.toString());
                            if (jsonObject2.toString().contains("chat_msg")) {
                                patient_id = jsonObject2.getString("p_id");
                                patient_name = jsonObject2.getString("p_name");
                                String p_login_id = jsonObject2.getString("p_login_id");
                                patient_status = jsonObject2.getString("p_status");
                                String p_pass = jsonObject2.getString("p_login_pass");
                                String device_token = jsonObject2.getString("p_device_token");
                                patient_pic = jsonObject2.getString("p_photo");
                                patient_email = jsonObject2.getString("chat_msg");
                                String u_msg = jsonObject2.getString("purgentchat");
                                String chat_file = jsonObject2.getString("chat_file");
                                String p_age = jsonObject2.getString("p_age");
                                /*PreferenceData.setPatient_Device_Token(getApplicationContext(), device_token);
                                PreferenceData.setchatPatient_id(getApplicationContext(), patient_id);*/

                                Detailapp = new InfoApps();

                                Detailapp.setId(patient_id);
                                Detailapp.setName(patient_name);
                                Detailapp.setBg(p_login_id);
                                Detailapp.setStatus(patient_status);
                                Detailapp.setAppname(patient_pic);
                                Detailapp.setPass(p_pass);
                                if (!patient_email.isEmpty()) {
                                    Detailapp.setEmail_id(patient_email);
                                } else if (!u_msg.isEmpty()) {
                                    Detailapp.setEmail_id(u_msg);
                                } else if (!chat_file.isEmpty()) {
                                    Detailapp.setEmail_id("photo");
                                }
                                Detailapp.setEmail_id(patient_email);

                                Detailapp.setDevice_token(device_token);
                                Detailapp.setAge(p_age);
                                contactDetails1.add(Detailapp);
                            } else {
                                patient_id = jsonObject2.getString("p_id");
                                patient_name = jsonObject2.getString("p_name");
                                patient_status = jsonObject2.getString("p_status");
                                String p_login_id = jsonObject2.getString("p_login_id");
                                String p_age = jsonObject2.getString("p_age");
                                String p_pass = jsonObject2.getString("p_login_pass");
                                String device_token = jsonObject2.getString("p_device_token");
                                patient_pic = jsonObject2.getString("p_photo");
                                /*PreferenceData.setPatient_Device_Token(getApplicationContext(), device_token);
                                PreferenceData.setchatPatient_id(getApplicationContext(), patient_id);*/

                                Detailapp = new InfoApps();

                                Detailapp.setId(patient_id);
                                Detailapp.setName(patient_name);
                                Detailapp.setStatus(patient_status);
                                Detailapp.setAppname(patient_pic);
                                Detailapp.setPass(p_pass);
                                Detailapp.setEmail_id("");
                                Detailapp.setBg(p_login_id);
                                Detailapp.setDevice_token(device_token);
                                Detailapp.setAge(p_age);
                                contactDetails1.add(Detailapp);
                            }

                        }
                        if (contactDetails1.size() > 0) {
                            mAdapter = new UsersAdapter(ViewPatientList.this, R.layout.view_patient_adapter, contactDetails1);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(ViewPatientList.this, "Sorry no result found", Toast.LENGTH_LONG).show();
                            mRecyclerView.setVisibility(View.INVISIBLE);
                            contactDetails1.clear();
                        }

                    /*if(contactDetails1.size()>0) {
                        text_result.setVisibility(View.GONE);
                        mAdapterbroad = new UsersAdapter_Serach_Doc(contactDetails1, Search_Doctor.this);
                        mRecyclerView.setAdapter(mAdapterbroad);
                    }
                    else{
                        Toast.makeText(Search_Doctor.this,"Sorry no result found",Toast.LENGTH_LONG).show();
                        text_result.setVisibility(View.VISIBLE);
                        text_result.setTypeface(tf);
                    }*/
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            try {
                if (pd != null) {
                    pd.dismiss();
                }
            } catch (Exception e) {

            }
        }
    }

    public class CallServicesSearchal3 extends AsyncTask<String, String, String> {
        String text = edsearch.getText().toString().trim();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            contactDetails1.clear();
            try {
                pd = new ProgressDialog(ViewPatientList.this);

                pd.setMessage("Searching..");
                pd.setIndeterminate(false);
                pd.setCancelable(true);
                pd.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (!isCancelled()) {
                namevaluepair.add(new BasicNameValuePair("p_name", ""));
                namevaluepair.add(new BasicNameValuePair("p_mob", ""));
                namevaluepair.add(new BasicNameValuePair("p_login_id", ""));
                namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));

                try {

                    result = Util.executeHttpPost(params[0], namevaluepair);

                    Log.e("result", result.toString());

                } catch (Exception e) {

                    e.printStackTrace();

                }
                return result;
            } else {
                return null;
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d("sunil", "canceled");
            try {
                if (pd != null) {
                    pd.dismiss();
                }
            } catch (Exception e) {

            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
//            if (pd != null) {
//                pd.dismiss();
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        Log.e("2", jsonObject2.toString());
                        if (jsonObject2.toString().contains("chat_msg")) {
                            patient_id = jsonObject2.getString("p_id");
                            patient_name = jsonObject2.getString("p_name");
                            String p_login_id = jsonObject2.getString("p_login_id");
                            patient_status = jsonObject2.getString("p_status");
                            String p_pass = jsonObject2.getString("p_login_pass");
                            String device_token = jsonObject2.getString("p_device_token");
                            patient_pic = jsonObject2.getString("p_photo");
                            patient_email = jsonObject2.getString("chat_msg");
                            String u_msg = jsonObject2.getString("purgentchat");
                            String chat_file = jsonObject2.getString("chat_file");
                            String p_age = jsonObject2.getString("p_age");
                                /*PreferenceData.setPatient_Device_Token(getApplicationContext(), device_token);
                                PreferenceData.setchatPatient_id(getApplicationContext(), patient_id);*/

                            Detailapp = new InfoApps();

                            Detailapp.setId(patient_id);
                            Detailapp.setName(patient_name);
                            Detailapp.setBg(p_login_id);
                            Detailapp.setStatus(patient_status);
                            Detailapp.setAppname(patient_pic);
                            Detailapp.setPass(p_pass);
                            if (!patient_email.isEmpty()) {
                                Detailapp.setEmail_id(patient_email);
                            } else if (!u_msg.isEmpty()) {
                                Detailapp.setEmail_id(u_msg);
                            } else if (!chat_file.isEmpty()) {
                                Detailapp.setEmail_id("photo");
                            }
                            Detailapp.setEmail_id(patient_email);

                            Detailapp.setDevice_token(device_token);
                            Detailapp.setAge(p_age);
                            contactDetails1.add(Detailapp);
                        } else {
                            patient_id = jsonObject2.getString("p_id");
                            patient_name = jsonObject2.getString("p_name");
                            patient_status = jsonObject2.getString("p_status");
                            String p_login_id = jsonObject2.getString("p_login_id");
                            String p_age = jsonObject2.getString("p_age");
                            String p_pass = jsonObject2.getString("p_login_pass");
                            String device_token = jsonObject2.getString("p_device_token");
                            patient_pic = jsonObject2.getString("p_photo");
                                /*PreferenceData.setPatient_Device_Token(getApplicationContext(), device_token);
                                PreferenceData.setchatPatient_id(getApplicationContext(), patient_id);*/

                            Detailapp = new InfoApps();

                            Detailapp.setId(patient_id);
                            Detailapp.setName(patient_name);
                            Detailapp.setStatus(patient_status);
                            Detailapp.setAppname(patient_pic);
                            Detailapp.setPass(p_pass);
                            Detailapp.setEmail_id("");
                            Detailapp.setBg(p_login_id);
                            Detailapp.setDevice_token(device_token);
                            Detailapp.setAge(p_age);
                            contactDetails1.add(Detailapp);
                        }

                    }
                    if (contactDetails1.size() > 0) {
                        mAdapter = new UsersAdapter(ViewPatientList.this, R.layout.view_patient_adapter, contactDetails1);
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        Toast.makeText(ViewPatientList.this, "Sorry no result found", Toast.LENGTH_LONG).show();
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        contactDetails1.clear();
                    }

                    /*if(contactDetails1.size()>0) {
                        text_result.setVisibility(View.GONE);
                        mAdapterbroad = new UsersAdapter_Serach_Doc(contactDetails1, Search_Doctor.this);
                        mRecyclerView.setAdapter(mAdapterbroad);
                    }
                    else{
                        Toast.makeText(Search_Doctor.this,"Sorry no result found",Toast.LENGTH_LONG).show();
                        text_result.setVisibility(View.VISIBLE);
                        text_result.setTypeface(tf);
                    }*/
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            try {
                if (pd != null) {
                    pd.dismiss();
                }
            } catch (Exception e) {

            }
        }
    }

    public class CallServiceslastchat extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;
        InfoApps Detailapp;

        public CallServiceslastchat(InfoApps Detailapp) {
            this.Detailapp = Detailapp;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(ViewPatientList.this);

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

            if (pd != null) {
                pd.dismiss();
            }


            if (result != null) {
                Log.e("result", "chatting:-" + result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    String chat_msg = jsonObject.getString("chat_msg");
                    String purgentchat = jsonObject.getString("purgentchat");
                    String chat_file = jsonObject.getString("chat_file");


                    if (!chat_msg.isEmpty()) {
                        Detailapp.setEmail_id(chat_msg);
                    } else if (!purgentchat.isEmpty()) {
                        Detailapp.setEmail_id(purgentchat);
                    } else if (!chat_file.isEmpty()) {
                        Detailapp.setEmail_id("photo");
                    }

                    contactDetails1.add(Detailapp);


                } catch (Exception e) {
                    Log.d("result", "error:-" + e.toString());
                }

                mAdapter = new UsersAdapter(ViewPatientList.this, R.layout.view_patient_adapter, contactDetails1);
                mRecyclerView.setAdapter(mAdapter);
                Log.e("stringArrayList", contactDetails1.size() + "");
                Log.e("stringArrayList", contactDetails1.toString());
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Log.e("clik", "action bar clicked");
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    public void setNotification(final String device_token) {
        new AsyncTask<String, String, String>() {
            ProgressDialog pd;
            ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
            String result;
            String tag;

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                pd = new ProgressDialog(ViewPatientList.this);

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
                namevaluepair.add(new BasicNameValuePair("regtoken", device_token));
                namevaluepair.add(new BasicNameValuePair("message", "HEllO SHUBHAm"));
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

                if (pd != null) {
                    pd.dismiss();
                }


                if (result != null) {
                    Log.e("sunil", "sent");
                }

            }
        }.execute();
    }

    public void refresh() {
        new CallServices().execute(ApiConfig.VIEW_PATIENT);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == ViewPatientList.RESULT_OK) {
                String result = data.getStringExtra("result");
                Log.e("onActivityResult", result);
//                refresh();
                getPatientList();
                FragmentToActivity fta = (FragmentToActivity) getApplicationContext();
                fta.refreshImage();
            }
            if (resultCode == ViewPatientList.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}