package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import patient_side.UsersAdapter_Serach_Doc;


public class WednesdayFragment extends Fragment implements View.OnClickListener{

    LinearLayout lineartop, linearmid, linearbottom;
    ImageView mimageview, aimageview, eimageview;
    ArrayList<String> arraylisttime;

    TextView sixam, sixthirtyam, sevenam, seventhirtyam, eightam, eightthirtyam, nineam, ninethirtyam, tenam, tenthirtyam, elevenam, eleventhirtyam, twelveam, twelvethirtypm,
            onepm, onethirtypm, twopm, twotpm, threepm, threetpm, fourpm, fourtpm, fivepm, fivetpm, sixpm,
            sixtpm, sevenpm, seventpm, seventhirtypm, eightpm, eightthirtypm, ninepm, ninethirtypm;

    public static String appointment_date,appointment_time;
    public static Boolean  wednesday;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_wednesday, container, false);


        lineartop = (LinearLayout) rootview.findViewById(R.id.lineartop);
        linearmid = (LinearLayout) rootview.findViewById(R.id.linearmid);
        linearbottom = (LinearLayout) rootview.findViewById(R.id.linearbottom);

        arraylisttime = new ArrayList<String>();

        sixam = (TextView) rootview.findViewById(R.id.sixam);
        sixthirtyam = (TextView) rootview.findViewById(R.id.sixthirtyam);
        sevenam = (TextView) rootview.findViewById(R.id.sevenam);
        seventhirtyam = (TextView) rootview.findViewById(R.id.seventhirtyam);
        eightam = (TextView) rootview.findViewById(R.id.eightam);
        eightthirtyam = (TextView) rootview.findViewById(R.id.eightthirtyam);

        nineam = (TextView) rootview.findViewById(R.id.nineam);
        ninethirtyam = (TextView) rootview.findViewById(R.id.ninethirtyam);
        tenam = (TextView) rootview.findViewById(R.id.tenam);
        tenthirtyam = (TextView) rootview.findViewById(R.id.tenthirtyam);
        elevenam = (TextView) rootview.findViewById(R.id.elevenam);
        eleventhirtyam = (TextView) rootview.findViewById(R.id.eleventhirtyam);
        twelveam = (TextView) rootview.findViewById(R.id.tweleveam);
        twelvethirtypm = (TextView) rootview.findViewById(R.id.twelevethirtypm);
        onepm = (TextView) rootview.findViewById(R.id.onepm);
        onethirtypm = (TextView) rootview.findViewById(R.id.onethirtypm);
        twopm = (TextView) rootview.findViewById(R.id.twopm);
        twotpm = (TextView) rootview.findViewById(R.id.twothirtypm);
        threepm = (TextView) rootview.findViewById(R.id.threepm);
        threetpm = (TextView) rootview.findViewById(R.id.teenthirtypm);
        fourpm = (TextView) rootview.findViewById(R.id.fourpm);
        fourtpm = (TextView) rootview.findViewById(R.id.fourthirtypm);
        fivepm = (TextView) rootview.findViewById(R.id.fivepm);
        fivetpm = (TextView) rootview.findViewById(R.id.fivethirtypm);
        sixpm = (TextView) rootview.findViewById(R.id.sixpm);
        sixtpm = (TextView) rootview.findViewById(R.id.sixthirtypm);
        sevenpm = (TextView) rootview.findViewById(R.id.sevenpm);
        seventpm = (TextView) rootview.findViewById(R.id.seventhirtypm);

        eightpm = (TextView) rootview.findViewById(R.id.eightpm);
        eightthirtypm = (TextView) rootview.findViewById(R.id.eightthirtypm);
        ninepm = (TextView) rootview.findViewById(R.id.ninepm);
        ninethirtypm = (TextView) rootview.findViewById(R.id.ninethirtypm);

        mimageview = (ImageView) rootview.findViewById(R.id.mimg);
        aimageview = (ImageView) rootview.findViewById(R.id.aimg);
        eimageview = (ImageView) rootview.findViewById(R.id.eimg);

        Typeface tf1= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
        sixam.setTypeface(tf1);
        sixthirtyam.setTypeface(tf1);
        sevenam.setTypeface(tf1);
        seventhirtyam.setTypeface(tf1);
        eightam.setTypeface(tf1);
        eightthirtyam.setTypeface(tf1);
        nineam.setTypeface(tf1);
        ninethirtyam.setTypeface(tf1);
        tenam.setTypeface(tf1);
        tenthirtyam.setTypeface(tf1);
        elevenam.setTypeface(tf1);
        eleventhirtyam.setTypeface(tf1);
        twelveam.setTypeface(tf1);
        twelvethirtypm.setTypeface(tf1);
        onepm.setTypeface(tf1);
        onethirtypm.setTypeface(tf1);
        twopm.setTypeface(tf1);
        twotpm.setTypeface(tf1);
        threepm.setTypeface(tf1);
        threetpm.setTypeface(tf1);
        fourpm.setTypeface(tf1);
        fourtpm.setTypeface(tf1);
        fivepm.setTypeface(tf1);
        fivetpm.setTypeface(tf1);
        sixpm.setTypeface(tf1);
        sixtpm.setTypeface(tf1);
        sevenpm.setTypeface(tf1);
        seventpm.setTypeface(tf1);
        eightpm.setTypeface(tf1);
        eightthirtypm.setTypeface(tf1);
        ninepm.setTypeface(tf1);
        ninethirtypm.setTypeface(tf1);

        sixam.setOnClickListener(this);
        sixthirtyam.setOnClickListener(this);
        sevenam.setOnClickListener(this);
        seventhirtyam.setOnClickListener(this);
        eightam.setOnClickListener(this);
        eightthirtyam.setOnClickListener(this);
        nineam.setOnClickListener(this);
        ninethirtyam.setOnClickListener(this);
        tenam.setOnClickListener(this);
        tenthirtyam.setOnClickListener(this);
        elevenam.setOnClickListener(this);
        eleventhirtyam.setOnClickListener(this);
        twelveam.setOnClickListener(this);
        twelvethirtypm.setOnClickListener(this);
        onepm.setOnClickListener(this);
        onethirtypm.setOnClickListener(this);
        twopm.setOnClickListener(this);
        twotpm.setOnClickListener(this);
        threepm.setOnClickListener(this);
        threetpm.setOnClickListener(this);
        fourpm.setOnClickListener(this);
        fourtpm.setOnClickListener(this);
        fivepm.setOnClickListener(this);
        fivetpm.setOnClickListener(this);
        sixpm.setOnClickListener(this);
        sixtpm.setOnClickListener(this);
        sevenpm.setOnClickListener(this);
        seventpm.setOnClickListener(this);
        eightpm.setOnClickListener(this);
        eightthirtypm.setOnClickListener(this);
        ninepm.setOnClickListener(this);
        ninethirtypm.setOnClickListener(this);

        try {
            SimpleDateFormat df = new SimpleDateFormat("hhaa");

            Date d1 = df.parse(UsersAdapter_Serach_Doc.avl_start_time3_morn);
            Calendar c1 = GregorianCalendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            c1.setTime(d1);
            String str1 = sdf.format(c1.getTime());

            Date d2 = df.parse(UsersAdapter_Serach_Doc.avl_end_time3_morn);
            Calendar c2 = GregorianCalendar.getInstance();
            SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss");
            c2.setTime(d2);
            String st2 = sd2.format(c2.getTime());
            getTime(UsersAdapter_Serach_Doc.avl_start_time3_morn,UsersAdapter_Serach_Doc.avl_end_time3_morn);
            Log.e("c1.getTime()",str1+st2);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            SimpleDateFormat df = new SimpleDateFormat("hhaa");
            Date d1 = df.parse(UsersAdapter_Serach_Doc.avl_start_time3_afternoon);
            Calendar c1 = GregorianCalendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            c1.setTime(d1);
            String str1 = sdf.format(c1.getTime());

            Date d2 = df.parse(UsersAdapter_Serach_Doc.avl_end_time3_afternoon);
            Calendar c2 = GregorianCalendar.getInstance();
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            c2.setTime(d2);
            String st2 = sdf2.format(c2.getTime());
            getTimeaftr(UsersAdapter_Serach_Doc.avl_start_time3_afternoon,UsersAdapter_Serach_Doc.avl_end_time3_afternoon);
            Log.e("c1.getTime()",str1+st2);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            SimpleDateFormat df = new SimpleDateFormat("hhaa");
            Date d1 = df.parse(UsersAdapter_Serach_Doc.avl_start_time3_even);
            Calendar c1 = GregorianCalendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            c1.setTime(d1);
            String str1 = sdf.format(c1.getTime());

            Date d2 = df.parse(UsersAdapter_Serach_Doc.avl_end_time3_even);
            Calendar c2 = GregorianCalendar.getInstance();
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            c2.setTime(d2);
            String st2 = sdf2.format(c2.getTime());
            getTimeeven(UsersAdapter_Serach_Doc.avl_start_time3_even,UsersAdapter_Serach_Doc.avl_end_time3_even);
            Log.e("c1.getTime()",str1+st2);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        refresh();

        mimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lineartop.getVisibility() == View.VISIBLE) {
                    lineartop.setVisibility(View.GONE);
                    mimageview.setImageDrawable(getResources().getDrawable(R.drawable.addplus));
                } else {
                    lineartop.setVisibility(View.VISIBLE);
                    mimageview.setImageDrawable(getResources().getDrawable(R.drawable.remomins));
                }
            }
        });

        aimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearmid.getVisibility() == View.VISIBLE) {
                    linearmid.setVisibility(View.GONE);
                    aimageview.setImageDrawable(getResources().getDrawable(R.drawable.addplus));
                } else {

                    linearmid.setVisibility(View.VISIBLE);
                    aimageview.setImageDrawable(getResources().getDrawable(R.drawable.remomins));
                }
            }
        });

        eimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearbottom.getVisibility() == View.VISIBLE) {
                    linearbottom.setVisibility(View.GONE);
                    eimageview.setImageDrawable(getResources().getDrawable(R.drawable.addplus));
                } else {

                    linearbottom.setVisibility(View.VISIBLE);
                    eimageview.setImageDrawable(getResources().getDrawable(R.drawable.remomins));
                }
            }
        });
        return rootview;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sixam:
                appointment_time="06:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
//                new CallServices().execute(ApiConfig.DOCTOR_ADD_APPO_INFO);
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.sixthirtyam:
                appointment_time="06:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
//                new CallServices().execute(ApiConfig.DOCTOR_ADD_APPO_INFO);
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.sevenam:
                appointment_time="07:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.seventhirtyam:
                appointment_time="07:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.eightam:
                appointment_time="08:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.eightthirtyam:
                appointment_time="08:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.nineam:
                appointment_time="09:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.ninethirtyam:
                appointment_time="09:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.tenam:
                appointment_time="10:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.tenthirtyam:
                appointment_time="10:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.elevenam:
                appointment_time="11:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.eleventhirtyam:
                appointment_time="11:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.tweleveam:
                appointment_time="12:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.twelevethirtypm:
                appointment_time="12:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.onepm:
                appointment_time="13:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.onethirtypm:
                appointment_time="13:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.twopm:
                appointment_time="14:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.twothirtypm:
                appointment_time="14:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.threepm:
                appointment_time="15:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.teenthirtypm:
                appointment_time="15:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.fourpm:
                appointment_time="16:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.fourthirtypm:
                appointment_time="16:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.fivepm:
                appointment_time="17:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                getActivity().finish();
                break;

            case R.id.fivethirtypm:
                appointment_time="17:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.sixpm:
                appointment_time="18:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.sixthirtypm:
                appointment_time="18:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.sevenpm:
                appointment_time="19:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.seventhirtypm:
                appointment_time="19:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                getActivity().finish();
                break;

            case R.id.eightpm:
                appointment_time="20:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                getActivity().finish();
                break;

            case R.id.eightthirtypm:
                appointment_time="20:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                getActivity().finish();
                break;

            case R.id.ninepm:
                appointment_time="21:00:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;

            case R.id.ninethirtypm:
                appointment_time="21:30:00";
                appointment_date=UtilsgetCurrentDate.getWedenessdayDate();
                wednesday=true;
                startActivity(new Intent(getActivity(),AppointmentDetails.class));
                break;
            default:
                return;

        }
        getActivity().finish();
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
            pd = new ProgressDialog(getActivity());

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("a_p_id", "22"));
            namevaluepair.add(new BasicNameValuePair("a_doc_id", "22"));
            namevaluepair.add(new BasicNameValuePair("a_time", appointment_time));
            namevaluepair.add(new BasicNameValuePair("a_date", DoctorAppointment.appointment_date));
            namevaluepair.add(new BasicNameValuePair("a_status", "22"));

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
                startActivity(new Intent(getActivity(),ConfirmationActivity.class));
                getActivity().finish();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.e("jsonArray", jsonArray.toString());
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }
    }

    public class CallServices1 extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.hide();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("doc_id", UsersAdapter_Serach_Doc.reg_id));

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
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.e("jsonArray", "wed:-"+jsonArray.toString());
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String cudtae = UtilsgetCurrentDate.getWedenessdayDate();
                        Log.d("sunil","current date:-"+cudtae);
                        String date = jsonObject.getString("a_date");
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add(date);
                        for (int i1 = 0; i1 < arrayList.size(); i1++) {
                            if (arrayList.contains(cudtae)) {
                                String time = jsonObject.getString("a_time");
                                arraylisttime.add(time);
                                Log.e("arraylisttime", arraylisttime.toString());
//                                for (int i2 = 0; i2 < arraylisttime.size(); i2++) {

                                if (arraylisttime.contains("06:00:00")) {
                                    sixam.setClickable(false);
                                    sixam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("06:30:00")) {
                                    sixthirtyam.setClickable(false);
                                    sixthirtyam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("07:00:00")) {
                                    sevenam.setClickable(false);
                                    sevenam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("07:30:00")) {
                                    seventhirtyam.setClickable(false);
                                    seventhirtyam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("08:00:00")) {
                                    eightam.setClickable(false);
                                    eightam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("08:30:00")) {
                                    eightthirtyam.setClickable(false);
                                    eightthirtyam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("09:00:00")) {
                                    nineam.setClickable(false);
                                    nineam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("09:30:00")) {
                                    ninethirtyam.setClickable(false);
                                    ninethirtyam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("10:00:00")) {
                                    tenam.setClickable(false);
                                    tenam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("10:30:00")) {
                                    tenthirtyam.setClickable(false);
                                    tenthirtyam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("11:00:00")) {
                                    elevenam.setClickable(false);
                                    elevenam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("11:30:00")) {
                                    eleventhirtyam.setClickable(false);
                                    eleventhirtyam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("12:00:00")) {
                                    twelveam.setClickable(false);
                                    twelveam.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("12:30:00")) {
                                    twelvethirtypm.setClickable(false);
                                    twelvethirtypm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("13:00:00")) {
                                    onepm.setClickable(false);
                                    onepm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("13:30:00")) {
                                    onethirtypm.setClickable(false);
                                    onethirtypm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("14:00:00")) {
                                    twopm.setClickable(false);
                                    twopm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("14:30:00")) {
                                    twotpm.setClickable(false);
                                    twotpm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("15:00:00")) {
                                    threetpm.setClickable(false);
                                    threetpm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("15:30:00")) {
                                    threepm.setClickable(false);
                                    threepm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("16:00:00")) {
                                    fourpm.setClickable(false);
                                    fourpm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("16:30:00")) {
                                    fourtpm.setClickable(false);
                                    fourtpm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("17:00:00")) {
                                    fivepm.setClickable(false);
                                    fivepm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("17:30:00")) {
                                    fivetpm.setClickable(false);
                                    fivetpm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("18:00:00")) {
                                    sixpm.setClickable(false);
                                    sixpm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("18:30:00")) {
                                    sixtpm.setClickable(false);
                                    sixtpm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("19:00:00")) {
                                    sevenpm.setClickable(false);
                                    sevenpm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("19:30:00")) {
                                    seventpm.setClickable(false);
                                    seventpm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("20:00:00")) {
                                    eightpm.setClickable(false);
                                    eightpm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("20:30:00")) {
                                    eightthirtypm.setClickable(false);
                                    eightthirtypm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("21:00:00")) {
                                    ninepm.setClickable(false);
                                    ninepm.setTextColor(Color.RED);
                                }
                                if (arraylisttime.contains("21:30:00")) {
                                    ninethirtypm.setClickable(false);
                                    ninethirtypm.setTextColor(Color.RED);
                                }
//                                }
                            }
                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }
    }

    public void getTime(String startTime,String endTime){
        try {

            SimpleDateFormat sdf=new SimpleDateFormat("hh:mm");





            String someRandomTim = "06:00:00";

            try{
                Date d1=sdf.parse(someRandomTim);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
//                System.out.println();
                if(timechecking(d1, d2, d3 )){
                    sixam.setVisibility(View.VISIBLE);
                }
                else{
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }



            String someRandomTim1 = "06:30:00";

            try{
                Date d1=sdf.parse(someRandomTim1);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                System.out.println(timechecking(d1, d2, d3 ));
                if(timechecking(d1, d2, d3 )){
                    sixthirtyam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            Log.e("yes","yes");

            String someRandomTim3 = "07:00:00";

            try{
                Date d1=sdf.parse(someRandomTim3);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    sevenam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            Log.e("yes","yes");
            //checkes whether the current time is between 14:49:00 and 20:11:13.
            System.out.println(true);

            String seventhirtyaam = "07:30:00";
            try{
                Date d1=sdf.parse(seventhirtyaam);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    seventhirtyam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            Log.e("yes","yes");
            System.out.println(true);


            String someRandomTim4 = "08:00:00";
            try{
                Date d1=sdf.parse(someRandomTim4);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    eightam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            Log.e("yes","yes");
            //checkes whether the current time is between 14:49:00 and 20:11:13.
            System.out.println(true);

            String someRandomTim5 = "08:30:00";
            try{
                Date d1=sdf.parse(someRandomTim5);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    eightthirtyam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime = "09:00:00";
            try{
                Date d1=sdf.parse(someRandomTime);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    nineam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime1 = "09:30:00";
            try{
                Date d1=sdf.parse(someRandomTime1);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    ninethirtyam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }


            String someRandomTime2 = "10:00:00";
            try{
                Date d1=sdf.parse(someRandomTime2);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    tenam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            String someRandomTime3 = "10:30:00";
            try{
                Date d1=sdf.parse(someRandomTime3);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    tenthirtyam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            String someRandomTime4 = "11:00:00";
            try{
                Date d1=sdf.parse(someRandomTime4);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    elevenam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime5 = "11:30:00";
            try{
                Date d1=sdf.parse(someRandomTime5);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    eleventhirtyam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean timeIsBefore(Date d1, Date d2) {
        DateFormat f = new SimpleDateFormat("HH:mm:ss.SSS");
        return f.format(d1).compareTo(f.format(d2)) < 0;
    }
    public static boolean timeIsAfter(Date d1,Date d2){
        DateFormat f = new SimpleDateFormat("HH:mm:ss.SSS");
        return f.format(d1).compareTo(f.format(d2)) > 0;
    }
    public static boolean sameTime(Date d1,Date d2){
        DateFormat f = new SimpleDateFormat("HH:mm:ss.SSS");
        return f.format(d1).compareTo(f.format(d2)) == 0;
    }
    public static boolean timechecking(Date d1,Date d2,Date d3){

        if(sameTime(d1, d2)||sameTime(d1, d3)){
            return true;
        }
        else{
            if(timeIsBefore(d1, d3)&&timeIsAfter(d1, d2)){
                return true;
            }
            else{
                return false;
            }
        }

    }
    public void getTimeaftr(String startTime,String endTime){
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm");
        try {

            String someRandomTime = "12:00:00";
            try{
                Date d1=sdf.parse(someRandomTime);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    twelveam.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            Log.e("yes","yes");
            System.out.println(true);

            String someRandomTime1 = "12:30:00";
            Log.e("yes","yes");
            try{
                Date d1=sdf.parse(someRandomTime1);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    twelvethirtypm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            System.out.println(true);


            String someRandomTime2 = "13:00:00";
            try{
                Date d1=sdf.parse(someRandomTime2);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    onepm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime3 = "13:30:00";
            try{
                Date d1=sdf.parse(someRandomTime3);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    onethirtypm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime4 = "14:00:00";
            try{
                Date d1=sdf.parse(someRandomTime4);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    twopm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime5 = "14:30:00";
            try{
                Date d1=sdf.parse(someRandomTime5);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    twotpm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime6 = "15:00:00";
            try{
                Date d1=sdf.parse(someRandomTime6);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    threepm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime7 = "15:30:00";
            try{
                Date d1=sdf.parse(someRandomTime7);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    threetpm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            String someRandomTime8 = "16:00:00";
            try{
                Date d1=sdf.parse(someRandomTime8);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    fourpm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime9 = "16:30:00";
            try{
                Date d1=sdf.parse(someRandomTime9);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    fourtpm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTimeeven(String startTime,String endTime){
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm");
        try {
            String string1 = startTime;

            String string2 = endTime;


            String someRandomTime1 = "17:00:00";

            try{
                Date d1=sdf.parse(someRandomTime1);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    fivepm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            Log.e("yes","yes");
            //checkes whether the current time is between 14:49:00 and 20:11:13.
            System.out.println(true);


            String someRandomTime2 = "17:30:00";
            try{
                Date d1=sdf.parse(someRandomTime2);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    fivetpm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime3 = "18:00:00";
            try{
                Date d1=sdf.parse(someRandomTime3);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    sixpm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime4 = "18:30:00";
            try{
                Date d1=sdf.parse(someRandomTime4);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    sixtpm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime5 = "19:00:00";
            try{
                Date d1=sdf.parse(someRandomTime5);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    sevenpm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime6 = "19:30:00";
            try{
                Date d1=sdf.parse(someRandomTime6);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    seventpm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime7 = "20:00:00";
            try{
                Date d1=sdf.parse(someRandomTime7);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    eightpm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            String someRandomTime8 = "20:30:00";
            try{
                Date d1=sdf.parse(someRandomTime8);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    eightthirtypm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            String someRandomTime9 = "21:00:00";
            try{
                Date d1=sdf.parse(someRandomTime9);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    ninepm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            String someRandomTime10 = "21:30:00";
            try{
                Date d1=sdf.parse(someRandomTime10);
                Date d2=sdf.parse(startTime);
                Date d3=sdf.parse(endTime);
                if(timechecking(d1, d2, d3 )){
                    ninethirtypm.setVisibility(View.VISIBLE);
                }
                else{
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
                String result = data.getStringExtra("result");
                Log.e("result", result);
                refresh();
            }
            if (resultCode == getActivity().RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }//
    }*/
    public void refresh(){
        new CallServices1().execute(ApiConfig.DOCTOR_APPO_INFO);
    }
}