package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.pojo.allAppointment.AllAppointmentResultPOJO;
import com.emobi.bjaindoc.pojo.appointment.PastAppointmentPOJO;
import com.emobi.bjaindoc.pojo.appointment.PastAppointmentResultPOJO;
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
public class TabDoctorPastDate extends Fragment implements WebServicesCallBack{
    CircleImageView d_photo;
    String d_name,d_department,d_address,d_time,ddate;
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
    UsersAdapterAppointmentPast mAdapter;
    LinearLayout ll_upcoming_scroll;
    List<View> ll_views=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.viewappointmentpatient, container, false);


       /* d_name= (TextView) view.findViewById(R.id.d_name);
        d_photo= (CircleImageView) view.findViewById(R.id.d_photo);
        d_department= (TextView) view.findViewById(R.id.d_department);
        d_address= (TextView) view.findViewById(R.id.d_address);
        d_time= (TextView) view.findViewById(R.id.d_time);
        ddate= (TextView) view.findViewById(R.id.d_date);*/

        mSnackBarLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        ll_upcoming_scroll = (LinearLayout) view.findViewById(R.id.ll_upcoming_scroll);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        contactDetails1=new ArrayList<InfoApps>();

//        new CallServices().execute(ApiConfig.checkdoctorappoiment);

        try{

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse("2016-10-21");
            String date2 = UtilsValidate.getCurrentDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = dateFormat.parse(date2);
            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(myDate));

            if(date1.after(myDate)){
                System.out.println("Date1 is after Date2");
            }

            if(date1.before(myDate)){
                System.out.println("Date1 is before Date2");
            }

            if(date1.equals(myDate)){
                System.out.println("Date1 is equal Date2");
            }

        }catch(ParseException ex){
            ex.printStackTrace();
        }

//        String input = UtilsValidate.getCurrentDate();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        try {
//            Date myDate = dateFormat.parse(input);
//            Calendar cal1 = Calendar.getInstance();
//            cal1.setTime(myDate);
//            cal1.add(Calendar.DAY_OF_YEAR, -1);
//            Date previousDate = cal1.getTime();
//            Log.e("previousDate", previousDate.toString());
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

        return view;
    }
//    public void showPastAppointments(final List<AllAppointmentResultPOJO> list_appointments){
////        if(list_appointments.size()>0){
//            ll_upcoming_scroll.removeAllViews();
////        }
//        for (int i = 0; i < list_appointments.size(); i++) {
//            final AllAppointmentResultPOJO allAppointmentResultPOJO=list_appointments.get(i);
//            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.upcomingdoc, null);
//            LinearLayout ll_member_name= (LinearLayout) view.findViewById(R.id.ll_member_name);
//            LinearLayout caal= (LinearLayout) view.findViewById(R.id.caal);
//            CircleImageView d_photo= (CircleImageView) view.findViewById(R.id.d_photo);
//            TextView d_name= (TextView) view.findViewById(R.id.d_name);
//            TextView d_department= (TextView) view.findViewById(R.id.d_department);
//            TextView d_address= (TextView) view.findViewById(R.id.d_address);
//            TextView d_time= (TextView) view.findViewById(R.id.d_time);
//            TextView d_date= (TextView) view.findViewById(R.id.d_date);
//            TextView call= (TextView) view.findViewById(R.id.d_call);
//            TextView dire= (TextView) view.findViewById(R.id.d_dire);
//            TextView tv_m_name= (TextView) view.findViewById(R.id.tv_m_name);
//
//            if(allAppointmentResultPOJO.getM_name().length()>0){
//                ll_member_name.setVisibility(View.VISIBLE);
//                tv_m_name.setText("Booked by "+allAppointmentResultPOJO.getM_name());
//            }
//            else{
//                ll_member_name.setVisibility(View.GONE);
//            }
//            caal.setVisibility(View.GONE);
//            Picasso.with(getActivity().getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + allAppointmentResultPOJO.getP_photo()).into(d_photo);
////            d_department.setText(PreferenceData.getDoctorDepartment(getApplicationContext()));
//            d_name.setText(allAppointmentResultPOJO.getP_name());
//            d_address.setText(PreferenceData.getDoctorclinic_address(getApplicationContext()));
//            d_time.setText(allAppointmentResultPOJO.getA_time());
//            d_date.setText(allAppointmentResultPOJO.getA_date());
//            call.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(!allAppointmentResultPOJO.getP_mob().isEmpty()){
//                        try {
//                            Intent my_callIntent = new Intent(Intent.ACTION_CALL);
//                            my_callIntent.setData(Uri.parse("tel:"+allAppointmentResultPOJO.getP_mob()));
//                            startActivity(my_callIntent);
//                        } catch (Exception e) {
//                            Toast.makeText(getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                    else{
//                        Toast.makeText(getActivity().getApplicationContext(),"No Number Found",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            final int finalI = i;
//            dire.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    removing_index= finalI;
//                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                    nameValuePairs.add(new BasicNameValuePair("a_p_id", allAppointmentResultPOJO.getP_id()));
//                    nameValuePairs.add(new BasicNameValuePair("a_date", allAppointmentResultPOJO.getA_date()));
//                    nameValuePairs.add(new BasicNameValuePair("a_time", allAppointmentResultPOJO.getA_time()));
//                    nameValuePairs.add(new BasicNameValuePair("title", "Cancel Appointment"));
//                    nameValuePairs.add(new BasicNameValuePair("message", "Your Appointment has been canceled,please contact to your doctor"));
//                    nameValuePairs.add(new BasicNameValuePair("token", allAppointmentResultPOJO.getP_device_token()));
//                    new WebServiceBase(nameValuePairs,getActivity(), TabDoctorPastDate.this, CALL_APPOINTMENT_CANCEL_API).execute(ApiConfig.CANCEL_APPOINTMENT);
//                }
//            });
//            ll_views.add(view);
//            ll_upcoming_scroll.addView(view);
//        }
//    }
    private int removing_index=-1;
    private void showSnackMessage(String msg) {
        Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.nav_drawer_item_text_selected));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }
    private final static String CALL_APPOINTMENT_CANCEL_API="call_appointment_cancel_api";

    @Override
    public void onGetMsg(String[] msg) {
        String api_call=msg[0];
        String response=msg[1];
        switch (api_call){
            case CALL_APPOINTMENT_CANCEL_API:
                parseAppointmentCancelResponse(response);
                break;
        }
    }
    public void parseAppointmentCancelResponse(String response){
        Log.d(TAG,"cancel response:-"+response);
        showSnackMessage("Appoint Canceled Successfully");
        try{
            JSONObject jsonObject = new JSONObject(response);

            String message=jsonObject.getString("a_message");

            if (message.equals("Appoiment cancel Successfully")){
                Toast.makeText(getActivity().getApplicationContext(),"Appointment Cancelled Successfully",Toast.LENGTH_LONG).show();
                if(removing_index!=-1) {
                    ll_upcoming_scroll.removeView(ll_views.get(removing_index));
                }
            }
            else {
                Toast.makeText(getActivity(),"Appointment  not canceled,please try again",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            Log.d(TAG,"errorin parsing:-"+e.toString());
        }
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
//            Log.d("values",name+"::"+email+"::"+password+"::"+age+"::"+weigt+"::"+height+"::"+descrption);

            try {
                pd = new ProgressDialog(getActivity());

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getActivity())));

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
                JSONArray jsonArray=new JSONArray(result);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2=jsonArray.getJSONObject(i);
                    String date=jsonObject2.getString("a_date");
                    SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
                    Date ComingDate = dateForm.parse(date);
                    if (ComingDate.before(myDate)){
                        String  p_id = jsonObject2.getString("p_id");
                        String a_date = jsonObject2.getString("a_date");
                        String a_time = jsonObject2.getString("a_time");
                        String p_name = jsonObject2.getString("p_name");
                        String p_mob = jsonObject2.getString("p_mob");
                        String p_photo = jsonObject2.getString("p_photo");
                        String p_device_token = jsonObject2.getString("p_device_token");
                        Log.e("2", jsonObject2.toString());

                        Detailapp = new InfoApps();

                        Detailapp.setId(p_id);
                        Detailapp.setNumber(p_mob);
                        Detailapp.setName(p_name);
                        Detailapp.setInvo_date(a_date);
                        Detailapp.setAppotime(a_time);
                        Detailapp.setPass(p_photo);
                        Detailapp.setDevice_token(p_device_token);
                        contactDetails1.add(Detailapp);
                        mAdapter = new UsersAdapterAppointmentPast(contactDetails1,getActivity(),mSnackBarLayout);
                        mRecyclerView.setAdapter(mAdapter);
//
// String a_time=jsonObject.getString("a_time");
                    }
                }


            }
            catch (Exception e){
                e.printStackTrace();
            }
            /*Intent returnIntent = new Intent();
            returnIntent.putExtra("result","okay");
            setResult(Activity.RESULT_OK,returnIntent);
            finish();*/
        }
    }
    public void showPastAppointments(PastAppointmentPOJO upcomingAppointmentResultPOJO) {
        ll_upcoming_scroll.removeAllViews();
        List<PastAppointmentResultPOJO> list_appointments=upcomingAppointmentResultPOJO.getPastAppointmentResultPOJO();
        for(int i=0;i<list_appointments.size();i++){
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_appointments_date, null);
            TextView tv_date= (TextView) view.findViewById(R.id.tv_date);
            LinearLayout ll_scroll= (LinearLayout) view.findViewById(R.id.ll_scroll);
            CheckBox chk_date_btn= (CheckBox) view.findViewById(R.id.chk_date_btn);
            chk_date_btn.setVisibility(View.GONE);
            try {
                String date=list_appointments.get(i).getDate();
                SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                Date now=new Date();
                Date app_date=sdf.parse(date);
//                Log.d(TAG,"now:-"+now.getDate());
//                Log.d(TAG,"app_date:-"+app_date.getDate());

                if(now.after(app_date)||now.getDate()==app_date.getDate()) {
                    tv_date.setText(list_appointments.get(i).getDate());

                    List<AllAppointmentResultPOJO> list_app = list_appointments.get(i).getList_upcoming_appointments();
                    showDateAppointments(list_app, ll_scroll);

                    ll_upcoming_scroll.addView(view);
                }
            }
            catch (Exception e){
                Log.d(TAG,e.toString());
            }
        }
//        if(list_appointments.size()>0){
//        ll_upcoming_scroll.removeAllViews();
//        }

    }

    public void showDateAppointments(List<AllAppointmentResultPOJO> list_appointments,LinearLayout ll_scroll){
        for (int i = 0; i < list_appointments.size(); i++) {
            final AllAppointmentResultPOJO allAppointmentResultPOJO=list_appointments.get(i);
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.upcomingdoc, null);
            LinearLayout ll_member_name= (LinearLayout) view.findViewById(R.id.ll_member_name);
            LinearLayout caal= (LinearLayout) view.findViewById(R.id.caal);
            CircleImageView d_photo= (CircleImageView) view.findViewById(R.id.d_photo);
            TextView d_name= (TextView) view.findViewById(R.id.d_name);
            TextView d_department= (TextView) view.findViewById(R.id.d_department);
            TextView d_address= (TextView) view.findViewById(R.id.d_address);
            TextView d_time= (TextView) view.findViewById(R.id.d_time);
            TextView d_date= (TextView) view.findViewById(R.id.d_date);
            TextView call= (TextView) view.findViewById(R.id.d_call);
            TextView dire= (TextView) view.findViewById(R.id.d_dire);
            TextView tv_m_name= (TextView) view.findViewById(R.id.tv_m_name);
            CheckBox chk_btn_appointment= (CheckBox) view.findViewById(R.id.chk_btn_appointment);

            chk_btn_appointment.setVisibility(View.GONE);
            if(allAppointmentResultPOJO.getM_name().length()>0){
                ll_member_name.setVisibility(View.VISIBLE);
                tv_m_name.setText("Booked by "+allAppointmentResultPOJO.getM_name());
            }
            else{
                ll_member_name.setVisibility(View.GONE);
            }
            caal.setVisibility(View.GONE);
            Picasso.with(getActivity().getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + allAppointmentResultPOJO.getP_photo()).into(d_photo);
//            d_department.setText(PreferenceData.getDoctorDepartment(getApplicationContext()));
            d_name.setText(allAppointmentResultPOJO.getP_name());
            d_address.setText(PreferenceData.getDoctorclinic_address(getApplicationContext()));
            d_time.setText(allAppointmentResultPOJO.getA_time());
            d_date.setText(allAppointmentResultPOJO.getA_date());
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!allAppointmentResultPOJO.getP_mob().isEmpty()){
                        try {
                            Intent my_callIntent = new Intent(Intent.ACTION_CALL);
                            my_callIntent.setData(Uri.parse("tel:"+allAppointmentResultPOJO.getP_mob()));
                            startActivity(my_callIntent);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(),"No Number Found",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            final int finalI = i;
            dire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removing_index= finalI;
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("a_p_id", allAppointmentResultPOJO.getP_id()));
                    nameValuePairs.add(new BasicNameValuePair("a_date", allAppointmentResultPOJO.getA_date()));
                    nameValuePairs.add(new BasicNameValuePair("a_time", allAppointmentResultPOJO.getA_time()));
                    nameValuePairs.add(new BasicNameValuePair("title", "Cancel Appointment"));
                    nameValuePairs.add(new BasicNameValuePair("message", "Your Appointment has been canceled,please contact to your doctor"));
                    nameValuePairs.add(new BasicNameValuePair("token", allAppointmentResultPOJO.getP_device_token()));
                    new WebServiceBase(nameValuePairs,getActivity(), TabDoctorPastDate.this, CALL_APPOINTMENT_CANCEL_API).execute(ApiConfig.CANCEL_APPOINTMENT);
                }
            });
            ll_scroll.addView(view);
        }
    }
}
