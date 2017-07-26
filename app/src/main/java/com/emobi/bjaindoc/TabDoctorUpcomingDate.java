package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.pojo.allAppointment.AllAppointmentResultPOJO;
import com.emobi.bjaindoc.pojo.appointment.UpcomingAppointmentPOJO;
import com.emobi.bjaindoc.pojo.appointment.UpcomingAppointmentResultPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Emobi-Android-002 on 10/20/2016.
 */
public class TabDoctorUpcomingDate extends Fragment implements WebServicesCallBack {
    CircleImageView d_photo;
    String d_name, d_department, d_address, d_time, ddate;
    private RecyclerView mRecyclerView;
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
    UsersAdapterAppointment mAdapter;
    LinearLayout ll_upcoming_scroll;
    List<View> ll_views = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.viewappointmentpatient, container, false);

        mSnackBarLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        ll_upcoming_scroll = (LinearLayout) view.findViewById(R.id.ll_upcoming_scroll);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        contactDetails1 = new ArrayList<InfoApps>();

//        new CallServices().execute(ApiConfig.checkdoctorappoiment);

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse("2016-10-21");
            String date2 = UtilsValidate.getCurrentDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = dateFormat.parse(date2);
            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(myDate));

            if (date1.after(myDate)) {
                System.out.println("Date1 is after Date2");
            }

            if (date1.before(myDate)) {
                System.out.println("Date1 is before Date2");
            }

            if (date1.equals(myDate)) {
                System.out.println("Date1 is equal Date2");
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return view;
    }

    TextView confirm_tv;
    private int removeViewIndex = -1;

    private void showSnackMessage(String msg) {
        Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.nav_drawer_item_text_selected));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }

    private final static String CALL_APPOINTMENT_CANCEL_API = "call_appointment_cancel_api";
    private final static String CONFIRM_APPOINTMENT = "confirm_appointment";

    @Override
    public void onGetMsg(String[] msg) {
        String api_call = msg[0];
        String response = msg[1];
        switch (api_call) {
            case CALL_APPOINTMENT_CANCEL_API:
                parseAppointmentCancelResponse(response);
                break;
            case CONFIRM_APPOINTMENT:
                parseConfirmAppointment(response);
                break;
        }
    }

    public void parseConfirmAppointment(String response) {
        try {
            Log.d(TAG, "confirm:-" + response);
            JSONObject object = new JSONObject(response);
            String multicast = object.optString("multicast_id");
            if (multicast.length() > 0) {
                if (confirm_tv != null) {
                    confirm_tv.setText("confirmed");
                    confirm_tv = null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseAppointmentCancelResponse(String response) {
        Log.d(TAG, "cancel response:-" + response);
//        showSnackMessage("Appoint Canceled Successfully");
        try {
            JSONObject jsonObject = new JSONObject(response);

            String success = jsonObject.optString("success");
            if (success.equals("true")) {
                Toast.makeText(getActivity().getApplicationContext(), "Appointment Cancelled Successfully", Toast.LENGTH_LONG).show();
//                if (removeViewIndex != -1) {
////                    ll_upcoming_scroll.removeView(ll_views.remove(removeViewIndex));
//                    ll_upcoming_scroll.removeView(ll_appoint_view_cancel);
//                }
                if(ll_appoint_view_cancel!=null) {
                    ll_upcoming_scroll.removeView(ll_appoint_view_cancel);
                }
                Intent intent=new Intent(getActivity(),GetAppointmentByDoctor.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), "Appointment not canceled,please try again", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d(TAG, "errorin parsing:-" + e.toString());
        }
    }

    private final String TAG = getClass().getSimpleName();

    public class CallServices extends AsyncTask<String, String, String> {

        ProgressDialog pd;


        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//            Log.d("values",name+"::"+email+"::"+password+"::"+age+"::"+weigt+"::"+height+"::"+descrption);

            try {
                pd = new ProgressDialog(getActivity());

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
            namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getActivity().getApplicationContext())));

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
            //Record not available
            try {
                if (pd != null) {
                    pd.dismiss();
                }
                String date2 = UtilsValidate.getCurrentDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date myDate = dateFormat.parse(date2);
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    String date = jsonObject2.getString("a_date");
                    Log.e("date", date);
                    SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
                    Date ComingDate = dateForm.parse(date);
                    if (ComingDate.after(myDate) || ComingDate.equals(myDate)) {
                        String p_id = jsonObject2.getString("p_id");
                        String a_date = jsonObject2.getString("a_date");
                        String a_time = jsonObject2.getString("a_time");
                        String p_name = jsonObject2.getString("p_name");
                        String p_mob = jsonObject2.getString("p_mob");
                        String p_device_token = jsonObject2.getString("p_device_token");
                        String p_photo = jsonObject2.getString("p_photo");
                        Log.e("2", jsonObject2.toString());

                        Detailapp = new InfoApps();

                        Detailapp.setId(p_id);
                        Detailapp.setNumber(p_mob);
                        Detailapp.setName(p_name);
                        Detailapp.setInvo_date(a_date);
                        Detailapp.setAppotime(a_time);
                        Detailapp.setDevice_token(p_device_token);
                        Detailapp.setPass(p_photo);
                        contactDetails1.add(Detailapp);

//
// String a_time=jsonObject.getString("a_time");
                    }
                }
                mAdapter = new UsersAdapterAppointment(contactDetails1, getActivity(), mSnackBarLayout);
                mRecyclerView.setAdapter(mAdapter);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("catch", e.toString());
            }
            /*Intent returnIntent = new Intent();
            returnIntent.putExtra("result","okay");
            setResult(Activity.RESULT_OK,returnIntent);
            finish();*/
        }
    }


    public void showUpcomingAppointments(UpcomingAppointmentPOJO upcomingAppointmentResultPOJO) {
        ll_upcoming_scroll.removeAllViews();
        list_of_appointments.clear();
        List<UpcomingAppointmentResultPOJO> list_appointments = upcomingAppointmentResultPOJO.getUpcomingAppointmentResultPOJO();
        List<UpcomingAppointmentResultPOJO> list_new_appointments=new ArrayList<>();
        for(int i=0;i<list_appointments.size();i++){
            try {
                String date = list_appointments.get(i).getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date now = new Date();
                Date app_date = sdf.parse(date);
                if (now.before(app_date) || now.getDate() == app_date.getDate()) {
                    list_new_appointments.add(list_appointments.get(i));
                }
            }
            catch (Exception e){
                Log.d(TAG,e.toString());
            }
        }
        inflateList(list_new_appointments);
    }
    public void inflateList(List<UpcomingAppointmentResultPOJO> list_appointments){
        int count=0;
        for (int i = 0; i < list_appointments.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_appointments_date, null);
            CheckBox chk_date_btn= (CheckBox) view.findViewById(R.id.chk_date_btn);
            TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
            LinearLayout ll_scroll = (LinearLayout) view.findViewById(R.id.ll_scroll);

            try {
                String date = list_appointments.get(i).getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date now = new Date();
                Date app_date = sdf.parse(date);
//                Log.d(TAG,"now:-"+now.getDate());
//                Log.d(TAG,"app_date:-"+app_date.getDate());

                if (now.before(app_date) || now.getDate() == app_date.getDate()) {
                    tv_date.setText(list_appointments.get(i).getDate());

                    List<AllAppointmentResultPOJO> list_app = list_appointments.get(i).getList_upcoming_appointments();
                    showDateAppointments(i,list_app, ll_scroll, chk_date_btn);

                    ll_upcoming_scroll.addView(view);
                    count++;
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
        bol=new boolean[count];
    }

    List<AllAppointmentResultPOJO> list_of_appointments = new ArrayList<>();
    List<List<CheckBox>> list_of_checkbox = new ArrayList<>();
    boolean bol[];


    LinearLayout ll_appoint_view_cancel;
    LinearLayout ll_scroll_cancel;
    public void showDateAppointments(final int position, final List<AllAppointmentResultPOJO> list_appointments,
                                     final LinearLayout ll_scroll, final CheckBox chk_date_btn) {
        final List<CheckBox> list_check=new ArrayList<>();
        for (int i = 0; i < list_appointments.size(); i++) {
            final AllAppointmentResultPOJO allAppointmentResultPOJO = list_appointments.get(i);
//            list_appointments.add(allAppointmentResultPOJO);
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.upcomingdoc, null);
            final LinearLayout ll_appoint_view = (LinearLayout) view.findViewById(R.id.ll_appoint_view);
            LinearLayout ll_member_name = (LinearLayout) view.findViewById(R.id.ll_member_name);
            CheckBox chk_btn_appointment = (CheckBox) view.findViewById(R.id.chk_btn_appointment);
            CircleImageView d_photo = (CircleImageView) view.findViewById(R.id.d_photo);
            TextView d_name = (TextView) view.findViewById(R.id.d_name);
            TextView d_department = (TextView) view.findViewById(R.id.d_department);
            TextView d_address = (TextView) view.findViewById(R.id.d_address);
            TextView d_time = (TextView) view.findViewById(R.id.d_time);
            TextView d_date = (TextView) view.findViewById(R.id.d_date);
            final TextView call = (TextView) view.findViewById(R.id.d_call);
            TextView dire = (TextView) view.findViewById(R.id.d_dire);
            TextView tv_m_name = (TextView) view.findViewById(R.id.tv_m_name);

            if (allAppointmentResultPOJO.getM_name().length() > 0) {
                ll_member_name.setVisibility(View.VISIBLE);
                tv_m_name.setText("Booked by " + allAppointmentResultPOJO.getM_name());
            } else {
                ll_member_name.setVisibility(View.GONE);
            }

            Picasso.with(getActivity().getApplicationContext()).
                    load("http://www.bjain.com/doctor/upload/" + allAppointmentResultPOJO.getP_photo())
                    .into(d_photo);
            d_department.setText(PreferenceData.getDoctorDepartment(getApplicationContext()));
            d_name.setText(allAppointmentResultPOJO.getP_name());
            d_address.setText(PreferenceData.getDoctorclinic_address(getApplicationContext()));
            d_time.setText(allAppointmentResultPOJO.getA_time());
            d_date.setText(allAppointmentResultPOJO.getA_date());

            if (allAppointmentResultPOJO.getA_status().equals("confirm")) {
                call.setText("confirmed");
            }
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (call.getText().toString().equals("confirm")) {
                        confirm_tv = call;
//                        Log.d(TAG,"doc_name:-"+PreferenceData.getName(getApplicationContext()));
                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("a_id", allAppointmentResultPOJO.getA_id()));
                        nameValuePairs.add(new BasicNameValuePair("p_id", allAppointmentResultPOJO.getP_id()));
                        nameValuePairs.add(new BasicNameValuePair("a_date", allAppointmentResultPOJO.getA_date()));
                        nameValuePairs.add(new BasicNameValuePair("a_time", allAppointmentResultPOJO.getA_time()));
                        nameValuePairs.add(new BasicNameValuePair("doc_name", PreferenceData.getName(getApplicationContext())));
                        new WebServiceBase(nameValuePairs, getActivity(), TabDoctorUpcomingDate.this, CONFIRM_APPOINTMENT).execute(ApiConfig.CONFIRM_APPOINTMENT);
                    }
                }
            });
            final int finalI = i;
            dire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeViewIndex = finalI;
                    try {
                        ll_appoint_view_cancel=ll_appoint_view;
                        ll_scroll_cancel=ll_scroll;
                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("a_id", allAppointmentResultPOJO.getA_id()));
                        nameValuePairs.add(new BasicNameValuePair("p_id", allAppointmentResultPOJO.getP_id()));
                        nameValuePairs.add(new BasicNameValuePair("a_date", allAppointmentResultPOJO.getA_date()));
                        nameValuePairs.add(new BasicNameValuePair("a_time", allAppointmentResultPOJO.getA_time()));
                        nameValuePairs.add(new BasicNameValuePair("doc_name", PreferenceData.getName(getApplicationContext())));
                        new WebServiceBase(nameValuePairs, getActivity(), TabDoctorUpcomingDate.this, CALL_APPOINTMENT_CANCEL_API).execute(ApiConfig.CANCEL_APPOINTMENT1);
                    } catch (Exception e) {
                        Log.d(TAG, "error in cancel:-" + e.toString());
                    }
                }
            });
            list_check.add(chk_btn_appointment);
            chk_btn_appointment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        list_of_appointments.add(allAppointmentResultPOJO);
                    }
                    else{
                        list_of_appointments.remove(allAppointmentResultPOJO);
                        Log.d(TAG,"array length:-"+bol.length);
                        bol[position]=true;
                        chk_date_btn.setChecked(false);
                    }
                    showconfirmcancel();
                }
            });
//            ll_views.add(view);
            ll_scroll.addView(view);
        }
        list_of_checkbox.add(list_check);
        chk_date_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
//                        List<CheckBox> list=list_of_checkbox.get(position);
                    for(int i=0;i<list_check.size();i++){
                        CheckBox checkBox=list_check.get(i);
                        checkBox.setChecked(true);
                    }
                }
                else{
//                    List<CheckBox> list=list_of_checkbox.get(position);
                    if(!bol[position]) {
                        for (int i = 0; i < list_check.size(); i++) {
                            CheckBox checkBox = list_check.get(i);
                            checkBox.setChecked(false);
                        }
                    }
                }

                Log.d(TAG,"appointments:-"+list_of_appointments.size());
                Log.d(TAG,"position:-"+position);
               showconfirmcancel();
                bol[position]=false;
            }
        });
    }
    public void showconfirmcancel(){
        try {
            GetAppointmentByDoctor doctor = (GetAppointmentByDoctor) getActivity();
            doctor.showconfirm(list_of_appointments);
        }
        catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }
}
