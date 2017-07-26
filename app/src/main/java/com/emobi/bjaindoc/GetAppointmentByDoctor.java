package com.emobi.bjaindoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emobi.bjaindoc.pojo.allAppointment.AllAppointmentResultPOJO;
import com.emobi.bjaindoc.pojo.appointment.AppointmentPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.emobi.bjaindoc.utls.StringUtils;
import com.emobi.bjaindoc.utls.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 10/20/2016.
 */
public class GetAppointmentByDoctor extends AppCompatActivity implements WebServicesCallBack {

    Button btn_Submit;
    String rate1, rate2, reason;
    ImageView backarrow;
    TextView toolbar_title;
    ActionBar actionBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<AllAppointmentResultPOJO> upcoming_appointment_list = new ArrayList<>();
    List<AllAppointmentResultPOJO> past_appointment_list = new ArrayList<>();
    PagerAdapterDoctor adapter;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.ll_confirmcancel)
    LinearLayout ll_confirmcancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appoinment_bypatient);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //.setIcon(R.drawable.activation)
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        backarrow = (ImageView) findViewById(R.id.backarrow);
        tabLayout.addTab(tabLayout.newTab().setText("Past"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundResource(R.color.white);

        btn_Submit = (Button) findViewById(R.id.btn_submit);
        viewPager = (ViewPager) findViewById(R.id.pager);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }


        });


        btn_Submit.setVisibility(View.GONE);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        toolbar_title.setTypeface(tf);
//        tabLayout.setupWithViewPager(viewPager);
        /*tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#578A48"));*/

        adapter = new PagerAdapterDoctor
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                /578A48
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#479736"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        callAppointmentAPI();
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(json.length()>0) {
                    callConfirmAppointmentAPI(json);
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(json.length()>0) {
                    callCancelAppointments(json);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.BOOK_APPOINTMENT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra("message");
            callAppointmentAPI();
        }
    };
    String json="";
    public void showconfirm(List<AllAppointmentResultPOJO> list_of_appointments){
        Log.d(TAG,"list_size:-"+list_of_appointments.size());
        if(list_of_appointments.size()>0){
            ll_confirmcancel.setVisibility(View.VISIBLE);
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("success",true);
                JSONArray array=new JSONArray();
                for(AllAppointmentResultPOJO allAppointmentResultPOJO:list_of_appointments){
                    JSONObject object=new JSONObject();
                    object.put("a_id",allAppointmentResultPOJO.getA_id());
                    object.put("p_id",allAppointmentResultPOJO.getP_id());
                    object.put("a_date",allAppointmentResultPOJO.getA_date());
                    object.put("a_time",allAppointmentResultPOJO.getA_time());
                    object.put("doc_name",PreferenceData.getName(getApplicationContext()));
                    array.put(object);
                }
                jsonObject.put("result",array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG,"json:-"+jsonObject.toString());
            json=jsonObject.toString();
        }
        else{
            ll_confirmcancel.setVisibility(View.GONE);
        }
    }

    public void callConfirmAppointmentAPI(String json){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("json_data", json));
        new WebServiceBase(nameValuePairs, this, CONFIRM_ALL_APPOINTMENTS).execute(ApiConfig.CONFIRM_ALL_APPOINTMENTS);
    }
    public void callCancelAppointments(String json){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("json_data", json));
        new WebServiceBase(nameValuePairs, this, CANCEL_APPOINTMENTS).execute(ApiConfig.CANCEL_ALL_APPOINTMENTS);
    }


    public void callAppointmentAPI() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date d=new Date();
        Log.d(TAG,"simple:-"+simpleDateFormat.format(d));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("a_doc_id", PreferenceData.getId(getApplicationContext())));
        nameValuePairs.add(new BasicNameValuePair("post_time",simpleDateFormat.format(d)));
        Log.d(TAG, "doc_id:-" + PreferenceData.getId(getApplicationContext()));
        new WebServiceBase(nameValuePairs, this, CALL_APPOINTMENT_API).execute(ApiConfig.DOCAPPOINTMENT);
    }

    private static final String CALL_APPOINTMENT_API = "call_appointment_api";
    private static final String CONFIRM_ALL_APPOINTMENTS = "confirm_all_appointments";
    private static final String CANCEL_APPOINTMENTS = "cancel_all_appointments";


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
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case CALL_APPOINTMENT_API:
                parseAppointmentAPIRESPONSE(response);
                break;
            case CONFIRM_ALL_APPOINTMENTS:
                startActivity(new Intent(GetAppointmentByDoctor.this,GetAppointmentByDoctor.class));
                finish();
                break;
            case CANCEL_APPOINTMENTS:
                startActivity(new Intent(GetAppointmentByDoctor.this,GetAppointmentByDoctor.class));
                finish();
                break;

        }
    }

    public void parseAppointmentAPIRESPONSE(String resposne) {
        Log.d(TAG, "appointment:-" + resposne);
        try {
            Gson gson = new Gson();
            AppointmentPOJO pojo = gson.fromJson(resposne, AppointmentPOJO.class);
            if (pojo.getSuccess().equals("true")) {
                Log.d(TAG, "appointments:-" + pojo.toString());
                TabDoctorUpcomingDate up_frag = (TabDoctorUpcomingDate) adapter.upcomingtab;
                up_frag.showUpcomingAppointments(pojo.getUpcomingAppointmentPOJO());

                TabDoctorPastDate past_frag = (TabDoctorPastDate) adapter.pasttab;
                past_frag.showPastAppointments(pojo.getPastAppointmentPOJO());
            } else {
                ToastClass.ShowLongToast(getApplicationContext(), "No appointments found.");
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            ToastClass.ShowLongToast(getApplicationContext(), "No appointments found.");
        }
//        upcoming_appointment_list.clear();
//        past_appointment_list.clear();
//        try {
//            Gson gson = new Gson();
//            AllAppointmentPOJO pojo = gson.fromJson(resposne, AllAppointmentPOJO.class);
//            if (pojo.getSuccess().equals("true")) {
//                for (AllAppointmentResultPOJO allAppointmentResultPOJO : pojo.getList_appointments()) {
//                    String searching_date = allAppointmentResultPOJO.getA_date() + " " + allAppointmentResultPOJO.getA_time();
//                    Date d = new Date();
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//                    try {
//                        Date d1 = sdf.parse(searching_date);
//                        if (d1.after(d)) {
//                            //after
//                            upcoming_appointment_list.add(allAppointmentResultPOJO);
//                        } else {
//                            //before
//                            past_appointment_list.add(allAppointmentResultPOJO);
//                        }
//                    } catch (Exception e) {
//                        System.out.println(e.toString());
//                    }
//                }
//
////                Log.d(TAG,"upcoming appointments:-"+upcoming_appointment_list.toString());
////                Log.d(TAG,"past appointments:-"+past_appointment_list.toString());
//                Log.d(TAG,"upcoming fragment_count:-"+upcoming_appointment_list.size());
//                Log.d(TAG,"past fragment_count:-"+past_appointment_list.size());
//                Log.d(TAG,"doc_id:-"+PreferenceData.getId(getApplicationContext()));
//                try{
//                    TabDoctorUpcomingDate frag= (TabDoctorUpcomingDate) adapter.upcomingtab;
//                    frag.showUpcomingAppointments(upcoming_appointment_list);
//                }
//                catch (Exception e){
//                    Log.d(TAG,e.toString());
//                }
//                try{
//                    TabDoctorPastDate frag= (TabDoctorPastDate) adapter.pasttab;
//                    frag.showPastAppointments(past_appointment_list);
//                }
//                catch (Exception e){
//                    Log.d(TAG,e.toString());
//                }
//
//            }
//        } catch (Exception e) {
//            Log.d(TAG, e.toString());
//        }
    }

//    public void showAppointments(AppointmentPOJO appointmentPOJO) {
//        upcoming_appointment_list.clear();
//        past_appointment_list.clear();
//        List<AppointmentResultPOJO> list_date_appointments = appointmentPOJO.getAppointmentResultPOJOList();
//        for (int i = 0; i < list_date_appointments.size(); i++) {
//            AppointmentResultPOJO appointmentResultPOJO = list_date_appointments.get(i);
//            String date = appointmentResultPOJO.getDate();
//            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//            try {
//                Date now = new Date();
//                Date date_pojo = sdf.parse(date);
//                if (now.before(date_pojo)) {
//                    SimpleDateFormat sdf_time = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//                    List<AllAppointmentResultPOJO> list_all_time_appointments = appointmentResultPOJO.getList_appointments();
//                    for (AllAppointmentResultPOJO allAppointmentResultPOJO : list_all_time_appointments) {
//                        Date time_date = sdf_time.parse(allAppointmentResultPOJO.getA_date() + " " + allAppointmentResultPOJO.getA_time());
//                        if (now.before(time_date)) {
//                            upcoming_appointment_list.add(allAppointmentResultPOJO);
//                        } else {
//                            past_appointment_list.add(allAppointmentResultPOJO);
//                        }
//                    }
//                } else {
//                    SimpleDateFormat sdf_time = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//                    List<AllAppointmentResultPOJO> list_all_time_appointments = appointmentResultPOJO.getList_appointments();
//                    for (AllAppointmentResultPOJO allAppointmentResultPOJO : list_all_time_appointments) {
//                        Date time_date = sdf_time.parse(allAppointmentResultPOJO.getA_date() + " " + allAppointmentResultPOJO.getA_time());
//                        if (now.before(time_date)) {
//                            past_appointment_list.add(allAppointmentResultPOJO);
//                        } else {
//                            past_appointment_list.add(allAppointmentResultPOJO);
//                        }
//                    }
//                }
//
////                Log.d(TAG, "upcoming appointments:-" + upcoming_appointment_list.toString());
////                Log.d(TAG, "past appointments:-" + past_appointment_list.toString());
//                Log.d(TAG, "upcoming fragment_count:-" + upcoming_appointment_list.size());
//                Log.d(TAG, "past fragment_count:-" + past_appointment_list.size());
//                Log.d(TAG, "doc_id:-" + PreferenceData.getId(getApplicationContext()));
//                try {
//                    TabDoctorUpcomingDate frag = (TabDoctorUpcomingDate) adapter.upcomingtab;
//                    frag.showUpcomingAppointments(upcoming_appointment_list);
//                } catch (Exception e) {
//                    Log.d(TAG, e.toString());
//                }
//                try {
//                    TabDoctorPastDate frag = (TabDoctorPastDate) adapter.pasttab;
//                    frag.showPastAppointments(past_appointment_list);
//                } catch (Exception e) {
//                    Log.d(TAG, e.toString());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private final String TAG = getClass().getSimpleName();
}
