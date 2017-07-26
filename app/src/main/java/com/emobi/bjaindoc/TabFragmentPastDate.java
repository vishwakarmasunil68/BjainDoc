package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Emobi-Android-002 on 10/20/2016.
 */
public class TabFragmentPastDate extends Fragment {
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
    UsersAdapterAppoPatient mAdapter;
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
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        contactDetails1=new ArrayList<InfoApps>();

        new CallServices().execute(ApiConfig.GET_APPO_BY_PATIENT);

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
            namevaluepair.add(new BasicNameValuePair("p_id", PreferenceData.getPatientId(getActivity())));
            namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getchatDocid(getActivity())));

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
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String date=jsonObject.getString("a_date");
                    SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
                    Date ComingDate = dateForm.parse(date);
                    if (ComingDate.before(myDate)){
                String a_time=jsonObject.getString("a_time");
                String a_date=jsonObject.getString("a_date");
                String department=jsonObject.getString("department");
                String clinic_address=jsonObject.getString("clinic_address");
                String photo=jsonObject.getString("photo");
                String reg_name=jsonObject.getString("reg_name");
                String reg_mob=jsonObject.getString("reg_mob");

                        /*d_name.setText(reg_name);
                        d_department.setText(department);
                        d_address.setText(clinic_address);
                        d_time.setText(a_time);
                        ddate.setText(a_date);
                        d_name.setText(reg_name);*/

                        Detailapp=new InfoApps();
                        Detailapp.setName(reg_name);
                        Detailapp.setAppname(photo);
                        Detailapp.setInvo_date(a_date);
                        Detailapp.setN_time(a_time);
                        Detailapp.setDataAdd(clinic_address);
                        Detailapp.setDesignation(department);
                        contactDetails1.add(Detailapp);
                        mAdapter = new UsersAdapterAppoPatient(contactDetails1,getActivity(),mSnackBarLayout);
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
}
