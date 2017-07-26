package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.whatsapp_profile.MainActivityDoctorProfile;
import com.emobi.bjaindoc.pojo.ClinicInfoPOJO;
import com.emobi.bjaindoc.pojo.ClinicInfoPOJOResult;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.emobi.bjaindoc.R.id.d_department;

/**
 * Created by Emobi-Android-002 on 8/16/2016.
 */
public class UpdateDoc_Addi extends Fragment implements View.OnClickListener, WebServicesCallBack {
    public static EditText dpmt, addr, deisgnation, degree, speciality, ed7, edtxt_descrption, et_doc_clinic_name, et_doc_clinc_address, et_doc_clinic_pincode, et_doc_clinic_city, et_doc_clinic_state;
    public static final String REGISTER_URL = "http://www.bjain.com/doctor/registration.php";
    AutoCompleteTextView et_doc_clinic_country;
    PlacesTask placesTask;
    ParserTask parserTask;
    List<EditText> list_address_edittext = new ArrayList<>();
    TextView txt_pname;

    Button btn_Submit;
    RadioGroup rg1, rg2;
    public static String rate1, rate2, reason;
    ImageView more;
    public static String selectedImagePath;
    public static TextView ed2;
    CircleImageView profile_image;
    private static final int FILE_SELECT_CODE = 0;
    LinearLayout parent;
    RecyclerView rv_clinic_info;
    FloatingActionButton fab_clinic, fab_timing;
    View layout_mon, layout_tue, layout_wed, layout_thu, layout_fri, layout_sat, layout_sun;
    LinearLayout dialog_mon, dialog_tue, dialog_wed, dialog_thu, dialog_fri, dialog_sat, dialog_sun;
    List<LinearLayout> list_linear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.updateinfodocadditional, container, false);

        more = (ImageView) view.findViewById(R.id.more);
        dpmt = (EditText) view.findViewById(d_department);
        addr = (EditText) view.findViewById(R.id.edtxt_mobile);
        deisgnation = (EditText) view.findViewById(R.id.edtxt_ppassword);
//        deisgnation = (EditText) view.findViewById(R.id.edtxt_mobile);

        et_doc_clinic_name = (EditText) view.findViewById(R.id.et_doc_clinic_name);
        et_doc_clinc_address = (EditText) view.findViewById(R.id.et_doc_clinc_address);
        et_doc_clinic_pincode = (EditText) view.findViewById(R.id.et_doc_clinic_pincode);
        et_doc_clinic_city = (EditText) view.findViewById(R.id.et_doc_clinic_city);
        et_doc_clinic_state = (EditText) view.findViewById(R.id.et_doc_clinic_state);
        et_doc_clinic_country = (AutoCompleteTextView) view.findViewById(R.id.et_doc_clinic_country);


        fab_clinic = (FloatingActionButton) view.findViewById(R.id.fab_clinic);
        fab_timing = (FloatingActionButton) view.findViewById(R.id.fab_timing);

        rv_clinic_info= (RecyclerView) view.findViewById(R.id.rv_clinic_info);
        GetALlClinicInfo();
        fab_clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clearData();
                if (isUpdatingclinic) {
                    CallWebService(true);
                } else {
                    CallWebService(false);
                }
            }
        });
        fab_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimings();
            }
        });

        parent = (LinearLayout) view.findViewById(R.id.parentmain);
        degree = (EditText) view.findViewById(R.id.edtxt_d_degree);
        speciality = (EditText) view.findViewById(R.id.edtxt_d_specialist);
        txt_pname = (TextView) view.findViewById(R.id.txt_pname);

        profile_image = (CircleImageView) view.findViewById(R.id.img_profile);

        reason = PreferenceData.getPatientId(getActivity());
        dpmt.setText(MainActivityDoctorProfile.reg_department);
        addr.setText(MainActivityDoctorProfile.reg_clinic_address);
        deisgnation.setText(MainActivityDoctorProfile.reg_designation);
        degree.setText(MainActivityDoctorProfile.reg_degree);
        speciality.setText(MainActivityDoctorProfile.reg_specialist);


        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        dpmt.setTypeface(tf1);
        addr.setTypeface(tf1);
        deisgnation.setTypeface(tf1);
        speciality.setTypeface(tf1);


//        ed4.setText(Categories_Fragment.p_age);
//        ed5.setText(Categories_Fragment.p_bloodgroup);
//        ed6.setText(Categories_Fragment.p_weight);
//        ed7.setText(Categories_Fragment.p_height);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, FILE_SELECT_CODE);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ll = new LinearLayout(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.gravity= Gravity.RIGHT;
                ll.setLayoutParams(params);

                parent.addView(ll);
                EditText edittext = new EditText(getActivity());
                edittext.setLayoutParams(params);
                edittext.setHint("add address");


                list_address_edittext.add(edittext);

                ll.addView(edittext);
            }
        });

        et_doc_clinic_country.setThreshold(1);

        et_doc_clinic_country.addTextChangedListener(new TextWatcher() {
            Boolean accepted =false;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accepted =true;
                if (accepted) {
                    placesTask = new PlacesTask();
                    placesTask.execute(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                et_doc_clinic_country.setSelection(et_doc_clinic_country.getText().toString().length());
                /*if (et_doc_clinic_country.getText().length()>4){
                    et_doc_clinic_country.setThreshold(10);
                }
                else if(et_doc_clinic_country.getText().length()<4){
                    et_doc_clinic_country.setThreshold(1);
                }*/
//                    accepted =false;
//                et_doc_clinic_country.setThreshold(1000);
            }
        });
        return view;
    }


    public void getPojo(ClinicInfoPOJOResult result) {
        Log.d(TAG, result.toString());
        if (result != null) {
            isUpdatingclinic = true;
            clinicInfoPOJOResult = result;
        }
        et_doc_clinic_name.setText(result.getClinic_name());
        et_doc_clinc_address.setText(result.getClinic_address());
        et_doc_clinic_pincode.setText(result.getClinic_pincode());
        et_doc_clinic_city.setText(result.getClinic_city());
        et_doc_clinic_state.setText(result.getClinic_state());
        et_doc_clinic_country.setText(result.getClinic_country());
        dpmt.setText(result.getClinic_department());
        deisgnation.setText(result.getClinic_designation());
        degree.setText(result.getClinic_degree());
        speciality.setText(result.getClinic_specialist());
        boolean mon_visibility = checkVisibility(result.getMon_mor_starttime(), result.getMon_mor_endtime(), result.getMon_eve_startime(), result.getMon_eve_endtime());
        boolean Tue_visibility = checkVisibility(result.getTue_mor_starttime(), result.getTue_mor_endtime(), result.getTue_eve_startime(), result.getTue_eve_endtime());
        boolean Wed_visibility = checkVisibility(result.getWed_mor_starttime(), result.getWed_mor_endtime(), result.getWed_eve_startime(), result.getWed_eve_endtime());
        boolean Thus_visibility = checkVisibility(result.getThus_mor_starttime(), result.getThus_mor_endtime(), result.getThus_eve_startime(), result.getThus_eve_endtime());
        boolean Fri_visibility = checkVisibility(result.getFri_mor_starttime(), result.getFri_mor_endtime(), result.getFri_eve_startime(), result.getFri_eve_endtime());
        boolean Sat_visibility = checkVisibility(result.getSat_mor_starttime(), result.getSat_mor_endtime(), result.getSat_eve_startime(), result.getSat_eve_endtime());
        boolean Sun_visibility = checkVisibility(result.getSun_mor_starttime(), result.getSun_mor_endtime(), result.getSun_eve_startime(), result.getSun_eve_endtime());
        save_linear[0] = mon_visibility;
        save_linear[1] = Tue_visibility;
        save_linear[2] = Wed_visibility;
        save_linear[3] = Thus_visibility;
        save_linear[4] = Fri_visibility;
        save_linear[5] = Sat_visibility;
        save_linear[6] = Sun_visibility;

        first_selected_pos = getFirstSelectedPosition();
//        showTimings();
    }

    ClinicInfoPOJOResult clinicInfoPOJOResult;

    public int getFirstSelectedPosition() {
        for (int i = 0; i < save_linear.length; i++) {
            if (save_linear[i]) {
                is_selected = true;
                return i;
            }
        }
        return -1;
    }

    public boolean isUpdatingclinic = false;
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception whil download", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches all places from GooglePlaces AutoComplete Web Service
    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "key=AIzaSyDw7i4yOrUd7sQ-5ls77LyEn7C1F_9deIY";

            String input="";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            // place type to be searched
            String types = "types=geocode";

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = input+"&"+types+"&"+sensor+"&"+key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;

            try{
                // Fetching the data from we service
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask();

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);
                Log.d("shubhamjsondata",jsonData.toString());

                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {


            Log.d("shubham",result.toString());
            String[] from = new String[] { "description"};
            Log.d("shubhamfrom",from.toString());
            int[] to = new int[] { android.R.id.text1 };

            // Creating a SimpleAdapter for the AutoCompleteTextView
            SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), result, android.R.layout.simple_list_item_1, from, to);

            // Setting the adapter
            et_doc_clinic_country.setAdapter(adapter);
//            Log.e("co",et_doc_clinic_country.setAdapter(adapter))
            String setstringadapter = et_doc_clinic_country.getText().toString();

            value(setstringadapter);
//            et_doc_clinic_country.setText(from[0]);
            try {
                Log.d("shubhamfrom",  setstringadapter);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            /*try {

                et_doc_clinic_country.setText(setstringadapter.split(",")[2].split("=")[1]);
                dpmt.requestFocus();
                Log.d("setstringadap", setstringadapter.split(",")[2]);

                try{
//                    et_doc_clinic_country.setFocusable(true);
                    String splitstring =et_doc_clinic_country.getText().toString();
                    if (splitstring.contains("}")){
                        splitstring.replace("//}","");
//                        et_doc_clinic_city.setFocusable(true);
                        et_doc_clinic_country.setText(splitstring);
                        dpmt.requestFocus();
                    }
//                    et_doc_clinic_country.setFocusable(false);
//                    et_doc_clinic_country.setThreshold(1000);
//                    Log.d("setstringadap", setstringadapter.split(",")[2].split("=")[1].split("}")[0]);
                }
                catch(Exception e){
                    Log.d("setstringadap",  e.toString());
                }
            }
            catch(Exception e){
                Log.d("setstringadap",  e.toString());

//                et_doc_clinic_country.setThreshold(1000);
            }*/
            synchronized (adapter){
                adapter.notifyDataSetChanged();
            }
        }
    }
    public boolean checkVisibility(String mrng_start_time, String mrng_end_time, String eve_start_time, String eve_end_time) {
        if (!mrng_start_time.equals("")
                || !mrng_end_time.equals("")
                || !eve_start_time.equals("")
                || !eve_end_time.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void CallWebService(boolean isupdating) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (isupdating) {
            nameValuePairs.add(new BasicNameValuePair("doctor_clinic_id", clinicInfoPOJOResult.getDoctor_clinic_id()));
        }
        Log.d(TAG, "doctor id:-" + PreferenceData.getId(getActivity()));
        nameValuePairs.add(new BasicNameValuePair("doctor_id", PreferenceData.getId(getActivity())));
        nameValuePairs.add(new BasicNameValuePair("clinic_name", et_doc_clinic_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_address", et_doc_clinc_address.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_pincode", et_doc_clinic_pincode.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_city", et_doc_clinic_city.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_state", et_doc_clinic_state.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_country", et_doc_clinic_country.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_department", dpmt.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_designation", deisgnation.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_degree", degree.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("clinic_specialist", speciality.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("mon_mor_starttime", string_mon_mrng_start_time));
        nameValuePairs.add(new BasicNameValuePair("mon_mor_endtime", string_mon_mrng_end_time));
        nameValuePairs.add(new BasicNameValuePair("mon_interval_mor", string_mon_mrng_interval_time));
        nameValuePairs.add(new BasicNameValuePair("mon_eve_startime", string_mon_eve_start_time));
        nameValuePairs.add(new BasicNameValuePair("mon_eve_endtime", string_mon_eve_end_time));
        nameValuePairs.add(new BasicNameValuePair("mon_interval_eve", string_mon_eve_interval_time));
        nameValuePairs.add(new BasicNameValuePair("tue_mor_starttime", string_tue_mrng_start_time));
        nameValuePairs.add(new BasicNameValuePair("tue_mor_endtime", string_tue_mrng_end_time));
        nameValuePairs.add(new BasicNameValuePair("tue_interval_mor", string_tue_mrng_interval_time));
        nameValuePairs.add(new BasicNameValuePair("tue_eve_startime", string_tue_eve_start_time));
        nameValuePairs.add(new BasicNameValuePair("tue_eve_endtime", string_tue_eve_end_time));
        nameValuePairs.add(new BasicNameValuePair("tue_interval_eve", string_tue_eve_interval_time));
        nameValuePairs.add(new BasicNameValuePair("wed_mor_starttime", string_wed_mrng_start_time));
        nameValuePairs.add(new BasicNameValuePair("wed_mor_endtime", string_wed_mrng_end_time));
        nameValuePairs.add(new BasicNameValuePair("wed_interval_mor", string_wed_mrng_interval_time));
        nameValuePairs.add(new BasicNameValuePair("wed_eve_startime", string_wed_eve_start_time));
        nameValuePairs.add(new BasicNameValuePair("wed_eve_endtime", string_wed_eve_end_time));
        nameValuePairs.add(new BasicNameValuePair("wed_interval_eve", string_wed_eve_interval_time));
        nameValuePairs.add(new BasicNameValuePair("thus_mor_starttime", string_thu_mrng_start_time));
        nameValuePairs.add(new BasicNameValuePair("thus_mor_endtime", string_thu_mrng_end_time));
        nameValuePairs.add(new BasicNameValuePair("thus_interval_mor", string_thu_mrng_interval_time));
        nameValuePairs.add(new BasicNameValuePair("thus_eve_startime", string_thu_eve_start_time));
        nameValuePairs.add(new BasicNameValuePair("thus_eve_endtime", string_thu_eve_end_time));
        nameValuePairs.add(new BasicNameValuePair("thus_interval_eve", string_thu_eve_interval_time));
        nameValuePairs.add(new BasicNameValuePair("fri_mor_starttime", string_fri_mrng_start_time));
        nameValuePairs.add(new BasicNameValuePair("fri_mor_endtime", string_fri_mrng_end_time));
        nameValuePairs.add(new BasicNameValuePair("fri_interval_mor", string_fri_mrng_interval_time));
        nameValuePairs.add(new BasicNameValuePair("fri_eve_startime", string_fri_eve_start_time));
        nameValuePairs.add(new BasicNameValuePair("fri_eve_endtime", string_fri_eve_end_time));
        nameValuePairs.add(new BasicNameValuePair("fri_interval_eve", string_fri_eve_interval_time));
        nameValuePairs.add(new BasicNameValuePair("sat_mor_starttime", string_sat_mrng_start_time));
        nameValuePairs.add(new BasicNameValuePair("sat_mor_endtime", string_sat_mrng_end_time));
        nameValuePairs.add(new BasicNameValuePair("sat_interval_mor", string_sat_mrng_interval_time));
        nameValuePairs.add(new BasicNameValuePair("sat_eve_startime", string_sat_eve_start_time));
        nameValuePairs.add(new BasicNameValuePair("sat_eve_endtime", string_sat_eve_end_time));
        nameValuePairs.add(new BasicNameValuePair("sat_interval_eve", string_sat_eve_interval_time));
        nameValuePairs.add(new BasicNameValuePair("sun_mor_starttime", string_sun_mrng_start_time));
        nameValuePairs.add(new BasicNameValuePair("sun_mor_endtime", string_sun_mrng_end_time));
        nameValuePairs.add(new BasicNameValuePair("sun_interval_mor", string_sun_mrng_interval_time));
        nameValuePairs.add(new BasicNameValuePair("sun_eve_startime", string_sun_eve_start_time));
        nameValuePairs.add(new BasicNameValuePair("sun_eve_endtime", string_sun_eve_end_time));
        nameValuePairs.add(new BasicNameValuePair("sun_interval_eve", string_sun_eve_interval_time));

        if (isupdating) {
            new WebServiceBase(nameValuePairs, getActivity(), this, "updating").execute("http://www.bjain.com/doctor/update_doctor_clinic.php");
        } else {
            new WebServiceBase(nameValuePairs, getActivity(), this, "api1").execute("http://www.bjain.com/doctor/doctor_clinic.php");
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        Log.d(TAG, "response:-" + response);
        switch (apicall) {
            case "api1":
                parseResponse(response, false);
                break;
            case "updating":
                parseResponse(response, true);
                break;
            case "api3":parseResponse(response);
                break;

        }
    }

    public void parseResponse(String response, boolean updated) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.optString("success");
            if (success.equals("true")) {
                if (updated) {
                    Toast.makeText(getActivity().getApplicationContext(), "Clinic Updated", Toast.LENGTH_LONG).show();
                    JSONObject result = jsonObject.optJSONObject("result");
                    clearData();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Clinic Saved", Toast.LENGTH_LONG).show();
                    JSONObject result = jsonObject.optJSONObject("result");
                    clearData();
                }
                Intent intent = new Intent(getActivity(), UpdateByDoc.class);
                intent.putExtra("position", 1);
                startActivity(intent);
                getActivity().finish();
            }
        } catch (Exception e) {

        }
    }

    public void clearData() {
        et_doc_clinic_name.setText("");
        et_doc_clinc_address.setText("");
        et_doc_clinic_pincode.setText("");
        et_doc_clinic_city.setText("");
        et_doc_clinic_state.setText("");
        et_doc_clinic_country.setText("");
        dpmt.setText("");
        deisgnation.setText("");
        degree.setText("");
        speciality.setText("");
        isUpdatingclinic = false;
        clinicInfoPOJOResult = null;
    }

    private String string_mon_mrng_start_time = "", string_mon_mrng_end_time = "", string_mon_eve_start_time = "", string_mon_eve_end_time = "", string_mon_mrng_interval_time = "", string_mon_eve_interval_time = "", string_tue_mrng_start_time = "", string_tue_mrng_end_time = "", string_tue_eve_start_time = "", string_tue_eve_end_time = "", string_tue_mrng_interval_time = "", string_tue_eve_interval_time = "", string_wed_mrng_start_time = "", string_wed_mrng_end_time = "", string_wed_eve_start_time = "", string_wed_eve_end_time = "", string_wed_mrng_interval_time = "", string_wed_eve_interval_time = "", string_thu_mrng_start_time = "", string_thu_mrng_end_time = "", string_thu_eve_start_time = "", string_thu_eve_end_time = "", string_thu_mrng_interval_time = "", string_thu_eve_interval_time = "", string_fri_mrng_start_time = "", string_fri_mrng_end_time = "", string_fri_eve_start_time = "", string_fri_eve_end_time = "", string_fri_mrng_interval_time = "", string_fri_eve_interval_time = "", string_sat_mrng_start_time = "", string_sat_mrng_end_time = "", string_sat_eve_start_time = "", string_sat_eve_end_time = "", string_sat_mrng_interval_time = "", string_sat_eve_interval_time = "", string_sun_mrng_start_time = "", string_sun_mrng_end_time = "", string_sun_eve_start_time = "", string_sun_eve_end_time = "", string_sun_mrng_interval_time = "", string_sun_eve_interval_time = "";

    public void showTimings() {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setContentView(R.layout.dialog_time_view);
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();
        params.height = LinearLayout.LayoutParams.FILL_PARENT;
        dialog1.getWindow().setAttributes((WindowManager.LayoutParams) params);
        dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog1.setTitle("Timings");
        dialog1.show();
        dialog1.setCancelable(true);


        dialog_mon = (LinearLayout) dialog1.findViewById(R.id.dialog_mon);
        dialog_tue = (LinearLayout) dialog1.findViewById(R.id.dialog_tue);
        dialog_wed = (LinearLayout) dialog1.findViewById(R.id.dialog_wed);
        dialog_thu = (LinearLayout) dialog1.findViewById(R.id.dialog_thu);
        dialog_fri = (LinearLayout) dialog1.findViewById(R.id.dialog_fri);
        dialog_sat = (LinearLayout) dialog1.findViewById(R.id.dialog_sat);
        dialog_sun = (LinearLayout) dialog1.findViewById(R.id.dialog_sun);


        layout_mon = dialog1.findViewById(R.id.include_mon);
        layout_tue = dialog1.findViewById(R.id.include_tue);
        layout_wed = dialog1.findViewById(R.id.include_wed);
        layout_thu = dialog1.findViewById(R.id.include_thu);
        layout_fri = dialog1.findViewById(R.id.include_fri);
        layout_sat = dialog1.findViewById(R.id.include_sat);
        layout_sun = dialog1.findViewById(R.id.include_sun);
        List<View> list_views = new ArrayList<>();
        list_views.add(layout_mon);
        list_views.add(layout_tue);
        list_views.add(layout_wed);
        list_views.add(layout_thu);
        list_views.add(layout_fri);
        list_views.add(layout_sat);
        list_views.add(layout_sun);

        list_linear = new ArrayList<>();
        list_linear.add(dialog_mon);
        list_linear.add(dialog_tue);
        list_linear.add(dialog_wed);
        list_linear.add(dialog_thu);
        list_linear.add(dialog_fri);
        list_linear.add(dialog_sat);
        list_linear.add(dialog_sun);

        dialog_mon.setBackgroundResource(R.drawable.linearlayoutsolid);
        dialog_tue.setBackgroundResource(R.drawable.linearlayoutborder);
        dialog_wed.setBackgroundResource(R.drawable.linearlayoutborder);
        dialog_thu.setBackgroundResource(R.drawable.linearlayoutborder);
        dialog_fri.setBackgroundResource(R.drawable.linearlayoutborder);
        dialog_sat.setBackgroundResource(R.drawable.linearlayoutborder);
        dialog_sun.setBackgroundResource(R.drawable.linearlayoutborder);


        dialog_mon.setOnClickListener(this);
        dialog_tue.setOnClickListener(this);
        dialog_wed.setOnClickListener(this);
        dialog_thu.setOnClickListener(this);
        dialog_fri.setOnClickListener(this);
        dialog_sat.setOnClickListener(this);
        dialog_sun.setOnClickListener(this);

        Button btn_cancel = (Button) dialog1.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) dialog1.findViewById(R.id.btn_ok);


        final EditText et_mon_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_mon_mrng_start_time);
        final EditText et_mon_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_mon_mrng_end_time);
        final EditText et_mon_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_mon_mrng_time_interval);
        final EditText et_mon_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_mon_eve_start_timing);
        final EditText et_mon_eve_end_time = (EditText) dialog1.findViewById(R.id.et_mon_eve_end_time);
        final EditText et_mon_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_mon_eve_time_interval);
        final EditText et_tue_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_tue_mrng_start_time);
        final EditText et_tue_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_tue_mrng_end_time);
        final EditText et_tue_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_tue_mrng_time_interval);
        final EditText et_tue_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_tue_eve_start_timing);
        final EditText et_tue_eve_end_time = (EditText) dialog1.findViewById(R.id.et_tue_eve_end_time);
        final EditText et_tue_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_tue_eve_time_interval);
        final EditText et_wed_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_wed_mrng_start_time);
        final EditText et_wed_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_wed_mrng_end_time);
        final EditText et_wed_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_wed_mrng_time_interval);
        final EditText et_wed_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_wed_eve_start_timing);
        final EditText et_wed_eve_end_time = (EditText) dialog1.findViewById(R.id.et_wed_eve_end_time);
        final EditText et_wed_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_wed_eve_time_interval);
        final EditText et_thu_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_thu_mrng_start_time);
        final EditText et_thu_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_thu_mrng_end_time);
        final EditText et_thu_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_thu_mrng_time_interval);
        final EditText et_thu_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_thu_eve_start_timing);
        final EditText et_thu_eve_end_time = (EditText) dialog1.findViewById(R.id.et_thu_eve_end_time);
        final EditText et_thu_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_thu_eve_time_interval);
        final EditText et_fri_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_fri_mrng_start_time);
        final EditText et_fri_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_fri_mrng_end_time);
        final EditText et_fri_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_fri_mrng_time_interval);
        final EditText et_fri_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_fri_eve_start_timing);
        final EditText et_fri_eve_end_time = (EditText) dialog1.findViewById(R.id.et_fri_eve_end_time);
        final EditText et_fri_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_fri_eve_time_interval);
        final EditText et_sat_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_sat_mrng_start_time);
        final EditText et_sat_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_sat_mrng_end_time);
        final EditText et_sat_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_sat_mrng_time_interval);
        final EditText et_sat_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_sat_eve_start_timing);
        final EditText et_sat_eve_end_time = (EditText) dialog1.findViewById(R.id.et_sat_eve_end_time);
        final EditText et_sat_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_sat_eve_time_interval);
        final EditText et_sun_mrng_start_time = (EditText) dialog1.findViewById(R.id.et_sun_mrng_start_time);
        final EditText et_sun_mrng_end_time = (EditText) dialog1.findViewById(R.id.et_sun_mrng_end_time);
        final EditText et_sun_mrng_time_interval = (EditText) dialog1.findViewById(R.id.et_sun_mrng_time_interval);
        final EditText et_sun_eve_start_timing = (EditText) dialog1.findViewById(R.id.et_sun_eve_start_timing);
        final EditText et_sun_eve_end_time = (EditText) dialog1.findViewById(R.id.et_sun_eve_end_time);
        final EditText et_sun_eve_time_interval = (EditText) dialog1.findViewById(R.id.et_sun_eve_time_interval);


        final Spinner spinner_sun_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sun_mrng_end_ampm);
        final Spinner spinner_sun_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sun_eve_start_ampm);
        final Spinner spinner_sun_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sun_eve_end_ampm);
        final Spinner spinner_sun_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sun_mrng_start_ampm);
        final Spinner spinner_sat_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sat_mrng_end_ampm);
        final Spinner spinner_sat_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sat_eve_start_ampm);
        final Spinner spinner_sat_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sat_eve_end_ampm);
        final Spinner spinner_sat_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_sat_mrng_start_ampm);
        final Spinner spinner_fri_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_fri_mrng_end_ampm);
        final Spinner spinner_fri_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_fri_eve_start_ampm);
        final Spinner spinner_fri_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_fri_eve_end_ampm);
        final Spinner spinner_fri_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_fri_mrng_start_ampm);
        final Spinner spinner_thu_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_thu_mrng_end_ampm);
        final Spinner spinner_thu_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_thu_eve_start_ampm);
        final Spinner spinner_thu_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_thu_eve_end_ampm);
        final Spinner spinner_thu_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_thu_mrng_start_ampm);
        final Spinner spinner_wed_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_wed_mrng_end_ampm);
        final Spinner spinner_wed_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_wed_eve_start_ampm);
        final Spinner spinner_wed_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_wed_eve_end_ampm);
        final Spinner spinner_wed_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_wed_mrng_start_ampm);
        final Spinner spinner_tue_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_tue_mrng_end_ampm);
        final Spinner spinner_tue_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_tue_eve_start_ampm);
        final Spinner spinner_tue_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_tue_eve_end_ampm);
        final Spinner spinner_tue_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_tue_mrng_start_ampm);
        final Spinner spinner_mon_mrng_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_mon_mrng_end_ampm);
        final Spinner spinner_mon_eve_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_mon_eve_start_ampm);
        final Spinner spinner_mon_eve_end_ampm = (Spinner) dialog1.findViewById(R.id.spinner_mon_eve_end_ampm);
        final Spinner spinner_mon_mrng_start_ampm = (Spinner) dialog1.findViewById(R.id.spinner_mon_mrng_start_ampm);


        if (isUpdatingclinic) {
            if(first_selected_pos!=-1) {
                for (int i = 0; i < list_linear.size(); i++) {
                    LinearLayout ll = list_linear.get(i);
                    View v = list_views.get(i);
                    if (i == first_selected_pos) {
                        v.setVisibility(View.VISIBLE);
//                        ll.setBackgroundResource(R.drawable.linearlayoutlight);
                    } else {
                        v.setVisibility(View.GONE);
                        ll.setBackgroundResource(R.drawable.linearlayoutborder);
                    }
                }
                for (int i = 0; i < save_linear.length; i++) {
                    LinearLayout ll = list_linear.get(i);
                    if (save_linear[i]) {
//                        ll.setBackgroundResource(R.drawable.linearlayoutlight);
                    } else {
                        ll.setBackgroundResource(R.drawable.linearlayoutborder);
                    }
                }

                et_mon_mrng_start_time.setText(clinicInfoPOJOResult.getMon_mor_starttime());
                et_mon_mrng_end_time.setText(clinicInfoPOJOResult.getMon_mor_endtime());
                et_mon_mrng_time_interval.setText(clinicInfoPOJOResult.getMon_interval_mor());
                et_mon_eve_start_timing.setText(clinicInfoPOJOResult.getMon_eve_startime());
                et_mon_eve_end_time.setText(clinicInfoPOJOResult.getMon_eve_endtime());
                et_mon_eve_time_interval.setText(clinicInfoPOJOResult.getMon_interval_eve());

                et_tue_mrng_start_time.setText(clinicInfoPOJOResult.getTue_mor_starttime());
                et_tue_mrng_end_time.setText(clinicInfoPOJOResult.getTue_mor_endtime());
                et_tue_mrng_time_interval.setText(clinicInfoPOJOResult.getTue_interval_mor());
                et_tue_eve_start_timing.setText(clinicInfoPOJOResult.getTue_eve_startime());
                et_tue_eve_end_time.setText(clinicInfoPOJOResult.getTue_eve_endtime());
                et_tue_eve_time_interval.setText(clinicInfoPOJOResult.getTue_interval_eve());

                et_wed_mrng_start_time.setText(clinicInfoPOJOResult.getWed_mor_starttime());
                et_wed_mrng_end_time.setText(clinicInfoPOJOResult.getWed_mor_endtime());
                et_wed_mrng_time_interval.setText(clinicInfoPOJOResult.getWed_interval_mor());
                et_wed_eve_start_timing.setText(clinicInfoPOJOResult.getWed_eve_startime());
                et_wed_eve_end_time.setText(clinicInfoPOJOResult.getWed_eve_endtime());
                et_wed_eve_time_interval.setText(clinicInfoPOJOResult.getWed_interval_eve());

                et_thu_mrng_start_time.setText(clinicInfoPOJOResult.getThus_mor_starttime());
                et_thu_mrng_end_time.setText(clinicInfoPOJOResult.getThus_mor_endtime());
                et_thu_mrng_time_interval.setText(clinicInfoPOJOResult.getThus_interval_mor());
                et_thu_eve_start_timing.setText(clinicInfoPOJOResult.getThus_eve_startime());
                et_thu_eve_end_time.setText(clinicInfoPOJOResult.getThus_eve_endtime());
                et_thu_eve_time_interval.setText(clinicInfoPOJOResult.getThus_interval_eve());

                et_fri_mrng_start_time.setText(clinicInfoPOJOResult.getFri_mor_starttime());
                et_fri_mrng_end_time.setText(clinicInfoPOJOResult.getFri_mor_endtime());
                et_fri_mrng_time_interval.setText(clinicInfoPOJOResult.getFri_interval_mor());
                et_fri_eve_start_timing.setText(clinicInfoPOJOResult.getFri_eve_startime());
                et_fri_eve_end_time.setText(clinicInfoPOJOResult.getFri_eve_endtime());
                et_fri_eve_time_interval.setText(clinicInfoPOJOResult.getFri_interval_eve());

                et_sat_mrng_start_time.setText(clinicInfoPOJOResult.getSat_mor_starttime());
                et_sat_mrng_end_time.setText(clinicInfoPOJOResult.getSat_mor_endtime());
                et_sat_mrng_time_interval.setText(clinicInfoPOJOResult.getSat_interval_mor());
                et_sat_eve_start_timing.setText(clinicInfoPOJOResult.getSat_eve_startime());
                et_sat_eve_end_time.setText(clinicInfoPOJOResult.getSat_eve_endtime());
                et_sat_eve_time_interval.setText(clinicInfoPOJOResult.getSat_interval_eve());

                et_sun_mrng_start_time.setText(clinicInfoPOJOResult.getSun_mor_starttime());
                et_sun_mrng_end_time.setText(clinicInfoPOJOResult.getSun_mor_endtime());
                et_sun_mrng_time_interval.setText(clinicInfoPOJOResult.getSun_interval_mor());
                et_sun_eve_start_timing.setText(clinicInfoPOJOResult.getSun_eve_startime());
                et_sun_eve_end_time.setText(clinicInfoPOJOResult.getSun_eve_endtime());
                et_sun_eve_time_interval.setText(clinicInfoPOJOResult.getSun_interval_eve());


//                if (first_selected_pos == -1) {
//                    save_linear = new boolean[7];
//                    changed_ll = null;
//                    is_selected = false;
//                    first_selected_pos = -1;
//                    bol_timings = new boolean[42];
//                    dialog_mon.setVisibility(View.VISIBLE);
//                }
            }
        } else

        {
            save_linear = new boolean[7];
            changed_ll = null;
            is_selected = false;
            first_selected_pos = -1;
            bol_timings = new boolean[42];
        }

        btn_ok.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          String mrng_start_time = "";
                                          String mrng_end_time = "";
                                          String mrng_time_interval = "";
                                          String eve_start_time = "";
                                          String eve_end_time = "";
                                          String eve_interval = "";


                                          if (first_selected_pos != -1) {
                                              switch (first_selected_pos) {
                                                  case 0:
                                                      mrng_start_time = ValidateEdittext(et_mon_mrng_start_time) + " " + ValidateSpinnerText(spinner_mon_mrng_start_ampm);
                                                      mrng_end_time = ValidateEdittext(et_mon_mrng_end_time) + " " + ValidateSpinnerText(spinner_mon_mrng_end_ampm);
                                                      mrng_time_interval = ValidateEdittext(et_mon_mrng_time_interval);
                                                      eve_start_time = ValidateEdittext(et_mon_eve_start_timing) + " " + ValidateSpinnerText(spinner_mon_eve_start_ampm);
                                                      eve_end_time = ValidateEdittext(et_mon_eve_end_time) + " " + ValidateSpinnerText(spinner_mon_eve_end_ampm);
                                                      eve_interval = ValidateEdittext(et_mon_eve_time_interval);
                                                      break;
                                                  case 1:
                                                      mrng_start_time = ValidateEdittext(et_tue_mrng_start_time) + " " + ValidateSpinnerText(spinner_tue_mrng_start_ampm);
                                                      mrng_end_time = ValidateEdittext(et_tue_mrng_end_time) + " " + ValidateSpinnerText(spinner_tue_mrng_end_ampm);
                                                      mrng_time_interval = ValidateEdittext(et_tue_mrng_time_interval);
                                                      eve_start_time = ValidateEdittext(et_tue_eve_start_timing) + " " + ValidateSpinnerText(spinner_tue_eve_start_ampm);
                                                      eve_end_time = ValidateEdittext(et_tue_eve_end_time) + " " + ValidateSpinnerText(spinner_tue_eve_end_ampm);
                                                      eve_interval = ValidateEdittext(et_tue_eve_time_interval);
                                                      break;
                                                  case 2:
                                                      mrng_start_time = ValidateEdittext(et_wed_mrng_start_time) + " " + ValidateSpinnerText(spinner_wed_mrng_start_ampm);
                                                      mrng_end_time = ValidateEdittext(et_wed_mrng_end_time) + " " + ValidateSpinnerText(spinner_wed_mrng_end_ampm);
                                                      mrng_time_interval = ValidateEdittext(et_wed_mrng_time_interval);
                                                      eve_start_time = ValidateEdittext(et_wed_eve_start_timing) + " " + ValidateSpinnerText(spinner_wed_eve_start_ampm);
                                                      eve_end_time = ValidateEdittext(et_wed_eve_end_time) + " " + ValidateSpinnerText(spinner_wed_eve_end_ampm);
                                                      eve_interval = ValidateEdittext(et_wed_eve_time_interval);
                                                      break;
                                                  case 3:
                                                      mrng_start_time = ValidateEdittext(et_thu_mrng_start_time) + " " + ValidateSpinnerText(spinner_thu_mrng_start_ampm);
                                                      mrng_end_time = ValidateEdittext(et_thu_mrng_end_time) + " " + ValidateSpinnerText(spinner_thu_mrng_end_ampm);
                                                      mrng_time_interval = ValidateEdittext(et_thu_mrng_time_interval);
                                                      eve_start_time = ValidateEdittext(et_thu_eve_start_timing) + " " + ValidateSpinnerText(spinner_thu_eve_start_ampm);
                                                      eve_end_time = ValidateEdittext(et_thu_eve_end_time) + " " + ValidateSpinnerText(spinner_thu_eve_end_ampm);
                                                      eve_interval = ValidateEdittext(et_thu_eve_time_interval);
                                                      break;
                                                  case 4:
                                                      mrng_start_time = ValidateEdittext(et_fri_mrng_start_time) + " " + ValidateSpinnerText(spinner_fri_mrng_start_ampm);
                                                      mrng_end_time = ValidateEdittext(et_fri_mrng_end_time) + " " + ValidateSpinnerText(spinner_fri_mrng_end_ampm);
                                                      mrng_time_interval = ValidateEdittext(et_fri_mrng_time_interval);
                                                      eve_start_time = ValidateEdittext(et_fri_eve_start_timing) + " " + ValidateSpinnerText(spinner_fri_eve_start_ampm);
                                                      eve_end_time = ValidateEdittext(et_fri_eve_end_time) + " " + ValidateSpinnerText(spinner_fri_eve_end_ampm);
                                                      eve_interval = ValidateEdittext(et_fri_eve_time_interval);
                                                      break;
                                                  case 5:
                                                      mrng_start_time = ValidateEdittext(et_sat_mrng_start_time) + " " + ValidateSpinnerText(spinner_sat_mrng_start_ampm);
                                                      mrng_end_time = ValidateEdittext(et_sat_mrng_end_time) + " " + ValidateSpinnerText(spinner_sat_mrng_end_ampm);
                                                      mrng_time_interval = ValidateEdittext(et_sat_mrng_time_interval);
                                                      eve_start_time = ValidateEdittext(et_sat_eve_start_timing) + " " + ValidateSpinnerText(spinner_sat_eve_start_ampm);
                                                      eve_end_time = ValidateEdittext(et_sat_eve_end_time) + " " + ValidateSpinnerText(spinner_sat_eve_end_ampm);
                                                      eve_interval = ValidateEdittext(et_sat_eve_time_interval);
                                                      break;
                                                  case 6:
                                                      mrng_start_time = ValidateEdittext(et_sun_mrng_start_time) + " " + ValidateSpinnerText(spinner_sun_mrng_start_ampm);
                                                      mrng_end_time = ValidateEdittext(et_sun_mrng_end_time) + " " + ValidateSpinnerText(spinner_sun_mrng_end_ampm);
                                                      mrng_time_interval = ValidateEdittext(et_sun_mrng_time_interval);
                                                      eve_start_time = ValidateEdittext(et_sun_eve_start_timing) + " " + ValidateSpinnerText(spinner_sun_eve_start_ampm);
                                                      eve_end_time = ValidateEdittext(et_sun_eve_end_time) + " " + ValidateSpinnerText(spinner_sun_eve_end_ampm);
                                                      eve_interval = ValidateEdittext(et_sun_eve_time_interval);
                                                      break;

                                              }
                                          }
                                          if (save_linear[0]) {
                                              string_mon_mrng_start_time = mrng_start_time;
                                              string_mon_mrng_end_time = mrng_end_time;
                                              string_mon_eve_start_time = eve_start_time;
                                              string_mon_eve_end_time = eve_end_time;
                                              string_mon_mrng_interval_time = mrng_time_interval;
                                              string_mon_eve_interval_time = eve_interval;
                                          } else {
                                              string_mon_mrng_start_time = "";
                                              string_mon_mrng_end_time = "";
                                              string_mon_eve_start_time = "";
                                              string_mon_eve_end_time = "";
                                              string_mon_mrng_interval_time = "";
                                              string_mon_eve_interval_time = "";
                                          }

                                          if (save_linear[1]) {
                                              string_tue_mrng_start_time = mrng_start_time;
                                              string_tue_mrng_end_time = mrng_end_time;
                                              string_tue_eve_start_time = eve_start_time;
                                              string_tue_eve_end_time = eve_end_time;
                                              string_tue_mrng_interval_time = mrng_time_interval;
                                              string_tue_eve_interval_time = eve_interval;
                                          } else {
                                              string_tue_mrng_start_time = "";
                                              string_tue_mrng_end_time = "";
                                              string_tue_eve_start_time = "";
                                              string_tue_eve_end_time = "";
                                              string_tue_mrng_interval_time = "";
                                              string_tue_eve_interval_time = "";
                                          }
                                          if (save_linear[2]) {
                                              string_wed_mrng_start_time = mrng_start_time;
                                              string_wed_mrng_end_time = mrng_end_time;
                                              string_wed_eve_start_time = eve_start_time;
                                              string_wed_eve_end_time = eve_end_time;
                                              string_wed_mrng_interval_time = mrng_time_interval;
                                              string_wed_eve_interval_time = eve_interval;
                                          } else {
                                              string_wed_mrng_start_time = "";
                                              string_wed_mrng_end_time = "";
                                              string_wed_eve_start_time = "";
                                              string_wed_eve_end_time = "";
                                              string_wed_mrng_interval_time = "";
                                              string_wed_eve_interval_time = "";
                                          }
                                          if (save_linear[3]) {
                                              string_thu_mrng_start_time = mrng_start_time;
                                              string_thu_mrng_end_time = mrng_end_time;
                                              string_thu_eve_start_time = eve_start_time;
                                              string_thu_eve_end_time = eve_end_time;
                                              string_thu_mrng_interval_time = mrng_time_interval;
                                              string_thu_eve_interval_time = eve_interval;
                                          } else {
                                              string_thu_mrng_start_time = "";
                                              string_thu_mrng_end_time = "";
                                              string_thu_eve_start_time = "";
                                              string_thu_eve_end_time = "";
                                              string_thu_mrng_interval_time = "";
                                              string_thu_eve_interval_time = "";
                                          }

                                          if (save_linear[4]) {
                                              string_fri_mrng_start_time = mrng_start_time;
                                              string_fri_mrng_end_time = mrng_end_time;
                                              string_fri_eve_start_time = eve_start_time;
                                              string_fri_eve_end_time = eve_end_time;
                                              string_fri_mrng_interval_time = mrng_time_interval;
                                              string_fri_eve_interval_time = eve_interval;
                                          } else {
                                              string_fri_mrng_start_time = "";
                                              string_fri_mrng_end_time = "";
                                              string_fri_eve_start_time = "";
                                              string_fri_eve_end_time = "";
                                              string_fri_mrng_interval_time = "";
                                              string_fri_eve_interval_time = "";
                                          }
                                          if (save_linear[5]) {
                                              string_sat_mrng_start_time = mrng_start_time;
                                              string_sat_mrng_end_time = mrng_end_time;
                                              string_sat_eve_start_time = eve_start_time;
                                              string_sat_eve_end_time = eve_end_time;
                                              string_sat_mrng_interval_time = mrng_time_interval;
                                              string_sat_eve_interval_time = eve_interval;
                                          } else {
                                              string_sat_mrng_start_time = "";
                                              string_sat_mrng_end_time = "";
                                              string_sat_eve_start_time = "";
                                              string_sat_eve_end_time = "";
                                              string_sat_mrng_interval_time = "";
                                              string_sat_eve_interval_time = "";
                                          }
                                          if (save_linear[6]) {
                                              string_sun_mrng_start_time = mrng_start_time;
                                              string_sun_mrng_end_time = mrng_end_time;
                                              string_sun_eve_start_time = eve_start_time;
                                              string_sun_eve_end_time = eve_end_time;
                                              string_sun_mrng_interval_time = mrng_time_interval;
                                              string_sun_eve_interval_time = eve_interval;
                                          } else {
                                              string_sun_mrng_start_time = "";
                                              string_sun_mrng_end_time = "";
                                              string_sun_eve_start_time = "";
                                              string_sun_eve_end_time = "";
                                              string_sun_mrng_interval_time = "";
                                              string_sun_eve_interval_time = "";
                                          }

                                          Log.d(TAG, "result:-" + resultString());
                                          dialog1.dismiss();
                                          for(int i=0;i<save_linear.length;i++) {
                                              Log.d(TAG, "linear"+i+" :-"+save_linear[i]);
                                          }
                                      }
                                  }

        );
        btn_cancel.setOnClickListener(new View.OnClickListener()

                                      {
                                          @Override
                                          public void onClick(View v) {
                                              dialog1.dismiss();
                                          }
                                      }

        );


        et_mon_mrng_start_time.addTextChangedListener(new

                EditTextWatcher(et_mon_mrng_start_time, 0, dialog_mon)

        );
        et_mon_mrng_end_time.addTextChangedListener(new

                EditTextWatcher(et_mon_mrng_end_time, 1, dialog_mon)

        );
//        et_mon_mrng_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_mon_mrng_time_interval, 2, dialog_mon)
//
//        );
        et_mon_eve_start_timing.addTextChangedListener(new

                EditTextWatcher(et_mon_eve_start_timing, 3, dialog_mon)

        );
        et_mon_eve_end_time.addTextChangedListener(new

                EditTextWatcher(et_mon_eve_end_time, 4, dialog_mon)

        );
//        et_mon_eve_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_mon_eve_time_interval, 5, dialog_mon)
//
//        );

        et_tue_mrng_start_time.addTextChangedListener(new

                EditTextWatcher(et_tue_mrng_start_time, 6, dialog_tue)

        );
        et_tue_mrng_end_time.addTextChangedListener(new

                EditTextWatcher(et_tue_mrng_end_time, 7, dialog_tue)

        );
//        et_tue_mrng_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_tue_mrng_time_interval, 8, dialog_tue)
//
//        );
        et_tue_eve_start_timing.addTextChangedListener(new

                EditTextWatcher(et_tue_eve_start_timing, 9, dialog_tue)

        );
        et_tue_eve_end_time.addTextChangedListener(new

                EditTextWatcher(et_tue_eve_end_time, 10, dialog_tue)

        );
//        et_tue_eve_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_tue_eve_time_interval, 11, dialog_tue)
//
//        );

        et_wed_mrng_start_time.addTextChangedListener(new

                EditTextWatcher(et_wed_mrng_start_time, 12, dialog_wed)

        );
        et_wed_mrng_end_time.addTextChangedListener(new

                EditTextWatcher(et_wed_mrng_end_time, 13, dialog_wed)

        );
//        et_wed_mrng_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_wed_mrng_time_interval, 14, dialog_wed)
//
//        );
        et_wed_eve_start_timing.addTextChangedListener(new

                EditTextWatcher(et_wed_eve_start_timing, 15, dialog_wed)

        );
        et_wed_eve_end_time.addTextChangedListener(new

                EditTextWatcher(et_wed_eve_end_time, 16, dialog_wed)

        );
//        et_wed_eve_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_wed_eve_time_interval, 17, dialog_wed)
//
//        );

        et_thu_mrng_start_time.addTextChangedListener(new

                EditTextWatcher(et_thu_mrng_start_time, 18, dialog_thu)

        );
        et_thu_mrng_end_time.addTextChangedListener(new

                EditTextWatcher(et_thu_mrng_end_time, 19, dialog_thu)

        );
//        et_thu_mrng_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_thu_mrng_time_interval, 20, dialog_thu)
//
//        );
        et_thu_eve_start_timing.addTextChangedListener(new

                EditTextWatcher(et_thu_eve_start_timing, 21, dialog_thu)

        );
        et_thu_eve_end_time.addTextChangedListener(new

                EditTextWatcher(et_thu_eve_end_time, 22, dialog_thu)

        );
//        et_thu_eve_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_thu_eve_time_interval, 23, dialog_thu)
//
//        );

        et_fri_mrng_start_time.addTextChangedListener(new

                EditTextWatcher(et_fri_mrng_start_time, 24, dialog_fri)

        );
        et_fri_mrng_end_time.addTextChangedListener(new

                EditTextWatcher(et_fri_mrng_end_time, 25, dialog_fri)

        );
//        et_fri_mrng_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_fri_mrng_time_interval, 26, dialog_fri)
//
//        );
        et_fri_eve_start_timing.addTextChangedListener(new

                EditTextWatcher(et_fri_eve_start_timing, 27, dialog_fri)

        );
        et_fri_eve_end_time.addTextChangedListener(new

                EditTextWatcher(et_fri_eve_end_time, 28, dialog_fri)

        );
//        et_fri_eve_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_fri_eve_time_interval, 29, dialog_fri)
//
//        );

        et_sat_mrng_start_time.addTextChangedListener(new

                EditTextWatcher(et_sat_mrng_start_time, 30, dialog_sat)

        );
        et_sat_mrng_end_time.addTextChangedListener(new

                EditTextWatcher(et_sat_mrng_end_time, 31, dialog_sat)

        );
//        et_sat_mrng_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_sat_mrng_time_interval, 32, dialog_sat)
//
//        );
        et_sat_eve_start_timing.addTextChangedListener(new

                EditTextWatcher(et_sat_eve_start_timing, 33, dialog_sat)

        );
        et_sat_eve_end_time.addTextChangedListener(new

                EditTextWatcher(et_sat_eve_end_time, 34, dialog_sat)

        );
//        et_sat_eve_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_sat_eve_time_interval, 35, dialog_sat)
//
//        );

        et_sun_mrng_start_time.addTextChangedListener(new

                EditTextWatcher(et_sun_mrng_start_time, 36, dialog_sun)

        );
        et_sun_mrng_end_time.addTextChangedListener(new

                EditTextWatcher(et_sun_mrng_end_time, 37, dialog_sun)

        );
//        et_sun_mrng_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_sun_mrng_time_interval, 38, dialog_sun)
//
//        );
        et_sun_eve_start_timing.addTextChangedListener(new

                EditTextWatcher(et_sun_eve_start_timing, 39, dialog_sun)

        );
        et_sun_eve_end_time.addTextChangedListener(new

                EditTextWatcher(et_sun_eve_end_time, 40, dialog_sun)

        );
//        et_sun_eve_time_interval.addTextChangedListener(new
//
//                EditTextWatcher(et_sun_eve_time_interval, 41, dialog_sun)
//
//        );

    }

    public String resultString() {
        return "Timings{" +
                "string_mon_mrng_start_time='" + string_mon_mrng_start_time + '\'' +
                ", string_mon_mrng_end_time='" + string_mon_mrng_end_time + '\'' +
                ", string_mon_eve_start_time='" + string_mon_eve_start_time + '\'' +
                ", string_mon_eve_end_time='" + string_mon_eve_end_time + '\'' +
                ", string_mon_mrng_interval_time='" + string_mon_mrng_interval_time + '\'' +
                ", string_mon_eve_interval_time='" + string_mon_eve_interval_time + '\'' +
                ", string_tue_mrng_start_time='" + string_tue_mrng_start_time + '\'' +
                ", string_tue_mrng_end_time='" + string_tue_mrng_end_time + '\'' +
                ", string_tue_eve_start_time='" + string_tue_eve_start_time + '\'' +
                ", string_tue_eve_end_time='" + string_tue_eve_end_time + '\'' +
                ", string_tue_mrng_interval_time='" + string_tue_mrng_interval_time + '\'' +
                ", string_tue_eve_interval_time='" + string_tue_eve_interval_time + '\'' +
                ", string_wed_mrng_start_time='" + string_wed_mrng_start_time + '\'' +
                ", string_wed_mrng_end_time='" + string_wed_mrng_end_time + '\'' +
                ", string_wed_eve_start_time='" + string_wed_eve_start_time + '\'' +
                ", string_wed_eve_end_time='" + string_wed_eve_end_time + '\'' +
                ", string_wed_mrng_interval_time='" + string_wed_mrng_interval_time + '\'' +
                ", string_wed_eve_interval_time='" + string_wed_eve_interval_time + '\'' +
                ", string_thu_mrng_start_time='" + string_thu_mrng_start_time + '\'' +
                ", string_thu_mrng_end_time='" + string_thu_mrng_end_time + '\'' +
                ", string_thu_eve_start_time='" + string_thu_eve_start_time + '\'' +
                ", string_thu_eve_end_time='" + string_thu_eve_end_time + '\'' +
                ", string_thu_mrng_interval_time='" + string_thu_mrng_interval_time + '\'' +
                ", string_thu_eve_interval_time='" + string_thu_eve_interval_time + '\'' +
                ", string_fri_mrng_start_time='" + string_fri_mrng_start_time + '\'' +
                ", string_fri_mrng_end_time='" + string_fri_mrng_end_time + '\'' +
                ", string_fri_eve_start_time='" + string_fri_eve_start_time + '\'' +
                ", string_fri_eve_end_time='" + string_fri_eve_end_time + '\'' +
                ", string_fri_mrng_interval_time='" + string_fri_mrng_interval_time + '\'' +
                ", string_fri_eve_interval_time='" + string_fri_eve_interval_time + '\'' +
                ", string_sat_mrng_start_time='" + string_sat_mrng_start_time + '\'' +
                ", string_sat_mrng_end_time='" + string_sat_mrng_end_time + '\'' +
                ", string_sat_eve_start_time='" + string_sat_eve_start_time + '\'' +
                ", string_sat_eve_end_time='" + string_sat_eve_end_time + '\'' +
                ", string_sat_mrng_interval_time='" + string_sat_mrng_interval_time + '\'' +
                ", string_sat_eve_interval_time='" + string_sat_eve_interval_time + '\'' +
                ", string_sun_mrng_start_time='" + string_sun_mrng_start_time + '\'' +
                ", string_sun_mrng_end_time='" + string_sun_mrng_end_time + '\'' +
                ", string_sun_eve_start_time='" + string_sun_eve_start_time + '\'' +
                ", string_sun_eve_end_time='" + string_sun_eve_end_time + '\'' +
                ", string_sun_mrng_interval_time='" + string_sun_mrng_interval_time + '\'' +
                ", string_sun_eve_interval_time='" + string_sun_eve_interval_time + '\'' +
                '}';

    }

    public String ValidateSpinnerText(Spinner spinner) {
        if (spinner != null && spinner.getSelectedItem().toString().length() > 0) {
            return spinner.getSelectedItem().toString();
        } else {
            return "";
        }
    }

    private String ValidateEdittext(EditText editText) {
        if (editText != null && editText.getText().toString().length() > 0) {
            return editText.getText().toString();
        } else {
            return "";
        }
    }

    private final String TAG = getClass().getName();
    private boolean[] bol_timings = new boolean[42];
    private boolean[] save_linear = new boolean[7];
    private LinearLayout changed_ll;
    private boolean is_selected = false;
    private int first_selected_pos = -1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_mon:
                setVisibility(0);
                break;
            case R.id.dialog_tue:

                setVisibility(1);
                break;
            case R.id.dialog_wed:

                setVisibility(2);
                break;
            case R.id.dialog_thu:

                setVisibility(3);
                break;
            case R.id.dialog_fri:

                setVisibility(4);
                break;
            case R.id.dialog_sat:

                setVisibility(5);
                break;
            case R.id.dialog_sun:

                setVisibility(6);
                break;
        }
    }

    public void setVisibility(int position) {
        if (!is_selected) {
            for (LinearLayout ll : list_linear) {
                ll.setBackgroundResource(R.drawable.linearlayoutborder);
            }
            list_linear.get(position).setBackgroundResource(R.drawable.linearlayoutsolid);
            switch (position) {
                case 0:
                    layout_mon.setVisibility(View.VISIBLE);
                    layout_tue.setVisibility(View.GONE);
                    layout_wed.setVisibility(View.GONE);
                    layout_thu.setVisibility(View.GONE);
                    layout_fri.setVisibility(View.GONE);
                    layout_sat.setVisibility(View.GONE);
                    layout_sun.setVisibility(View.GONE);
                    break;
                case 1:
                    layout_mon.setVisibility(View.GONE);
                    layout_tue.setVisibility(View.VISIBLE);
                    layout_wed.setVisibility(View.GONE);
                    layout_thu.setVisibility(View.GONE);
                    layout_fri.setVisibility(View.GONE);
                    layout_sat.setVisibility(View.GONE);
                    layout_sun.setVisibility(View.GONE);
                    break;
                case 2:
                    layout_mon.setVisibility(View.GONE);
                    layout_tue.setVisibility(View.GONE);
                    layout_wed.setVisibility(View.VISIBLE);
                    layout_thu.setVisibility(View.GONE);
                    layout_fri.setVisibility(View.GONE);
                    layout_sat.setVisibility(View.GONE);
                    layout_sun.setVisibility(View.GONE);
                    break;
                case 3:
                    layout_mon.setVisibility(View.GONE);
                    layout_tue.setVisibility(View.GONE);
                    layout_wed.setVisibility(View.GONE);
                    layout_thu.setVisibility(View.VISIBLE);
                    layout_fri.setVisibility(View.GONE);
                    layout_sat.setVisibility(View.GONE);
                    layout_sun.setVisibility(View.GONE);
                    break;
                case 4:
                    layout_mon.setVisibility(View.GONE);
                    layout_tue.setVisibility(View.GONE);
                    layout_wed.setVisibility(View.GONE);
                    layout_thu.setVisibility(View.GONE);
                    layout_fri.setVisibility(View.VISIBLE);
                    layout_sat.setVisibility(View.GONE);
                    layout_sun.setVisibility(View.GONE);
                    break;
                case 5:
                    layout_mon.setVisibility(View.GONE);
                    layout_tue.setVisibility(View.GONE);
                    layout_wed.setVisibility(View.GONE);
                    layout_thu.setVisibility(View.GONE);
                    layout_fri.setVisibility(View.GONE);
                    layout_sat.setVisibility(View.VISIBLE);
                    layout_sun.setVisibility(View.GONE);
                    break;
                case 6:
                    layout_mon.setVisibility(View.GONE);
                    layout_tue.setVisibility(View.GONE);
                    layout_wed.setVisibility(View.GONE);
                    layout_thu.setVisibility(View.GONE);
                    layout_fri.setVisibility(View.GONE);
                    layout_sat.setVisibility(View.GONE);
                    layout_sun.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            if (save_linear[position]) {
                save_linear[position] = false;
            } else {
                save_linear[position] = true;
            }
            for (int i = 0; i < list_linear.size(); i++) {
                if (save_linear[i]) {
//                    list_linear.get(i).setBackgroundResource(R.drawable.linearlayoutlight);
                } else {
                    list_linear.get(i).setBackgroundResource(R.drawable.linearlayoutborder);
                }
            }
//            for(int i=0;i<save_linear.length;i++) {
//                Log.d(TAG, "savelinernew pos :-" +i+" "+ save_linear[i]);
//            }
        }
    }


    class EditTextWatcher implements TextWatcher {
        EditText editText;
        int textlength;
        int position;
        LinearLayout ll;

        EditTextWatcher(EditText editText, int position, LinearLayout ll) {
            this.editText = editText;
            this.position = position;
            this.ll = ll;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            textlength = editText.getText().toString().length();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (charSequence.toString().length() == 1) {
                try {
                    int val = Integer.parseInt(charSequence.toString());
                    if (val > 1) {
                        editText.setText("0" + val + ":");
                        editText.setSelection(editText.getText().length());
                    }
                } catch (Exception e) {

                }
            }
            if (charSequence.toString().length() == 4) {
                try {
                    String str = charSequence.toString();
                    String beforestring = str.split(":")[0];
                    str = str.substring(str.length() - 1);
                    int val = Integer.parseInt(str);
                    if (val > 5) {
                        String newString = "0" + val;
                        editText.setText(beforestring + ":" + newString);
                        editText.setSelection(editText.getText().length());
                    }
                } catch (Exception e) {

                }
            }

            if (charSequence.toString().length() == 2) {
                try {
                    int val = Integer.parseInt(charSequence.toString());
                    if (val > 12) {
                        String s = charSequence.toString();
                        s = s.substring(0, s.length() - 1);
                        editText.setText(s);
                        editText.setSelection(editText.getText().length());
                    } else {
                        if (!bol_timings[position]) {
                            bol_timings[position] = true;
                            Log.d("sunil", "lenght==2");
                            editText.setText(editText.getText().toString() + ":");
                            editText.setSelection(editText.getText().length());
                        }
                    }
                } catch (Exception e) {

                }
            } else {
                if (!charSequence.toString().contains(":")) {
                    if (charSequence.toString().length() == 3) {
                        String str = charSequence.toString();
                        String str1 = str.substring(0, 2);
                        String str2 = str.substring(2, str.length());
                        String finalString = str1 + ":" + str2;
                        editText.setText(finalString);
                        editText.setSelection(editText.getText().length());
                    }
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int nowlength = editText.getText().toString().length();
            if (nowlength == 2) {
                bol_timings[position] = false;
            }
            if(!is_selected) {
                changed_ll = ll;
                is_selected = true;
                for (int i = 0; i < list_linear.size(); i++) {
                    LinearLayout linearLayout = list_linear.get(i);
                    if (linearLayout == ll) {
                        save_linear[i] = true;
                        first_selected_pos = i;
                    } else {
                        save_linear[i] = false;
                    }
                }
                for (int i = 0; i < save_linear.length; i++) {
                    Log.d(TAG, "saveliner:-" + i + " " + save_linear[i]);
                }
            }
        }

    }

    public String getAllAddresses() {
        String address1 = addr.getText().toString();
        for (EditText editText : list_address_edittext) {
            if (editText.getText().toString().length() > 0) {
                address1 += "," + editText.getText().toString();
            }
        }
        return address1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE) {
            Log.d("sun", "on activity result");
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                selectedImagePath = getPath(
                        getActivity(), selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    String image_path_string = selectedImagePath;
                    Log.d("sunil", selectedImagePath);

                    Bitmap bmImg = BitmapFactory.decodeFile(image_path_string);
                    SharedPreferences sp = getActivity().getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("profile_pic", image_path_string);
                    editor.commit();
                    profile_image.setImageBitmap(bmImg);
                } else {
                    Toast.makeText(getActivity(), "File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
        }
    }
    public String value(String str){
        String s="";
        String splitedstring="";

        try{
            splitedstring = str.substring(str.lastIndexOf("=") + 1);
            String[] spl=str.split(",");
            String eq=spl[2].split("=")[1];
            s+=eq;
            for(int i=3;i<spl.length;i++){
                s+=spl[i]+",";
                et_doc_clinic_country.setText(splitedstring);
                dpmt.requestFocus();

                Log.d("splitedstring",  splitedstring);
                Log.d("shubhamfrom",  spl[i]);
            }
        }
        catch(Exception e){

        }
        /*try{
            Log.d("splitedstring",  "try");
            if(splitedstring.endsWith("}")){
                splitedstring.replace("}","");
                et_doc_clinic_country.setText(splitedstring);
                dpmt.requestFocus();
            }

        }
        catch (Exception e){
            dpmt.requestFocus();
        }*/
        return s;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
    public void GetALlClinicInfo(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("doctor_id", PreferenceData.getId(getActivity())));
        new WebServiceBase(nameValuePairs,getActivity(), this, "api3").execute("http://www.bjain.com/doctor/doctor_cliniclist.php");

    }

    /*@Override
    public void onGetMsg(String[] msg) {
        String api=msg[0];
        String response=msg[1];
        switch (api){
            case "api1":parseResponse(response);
                break;
        }
    }*/
    public void parseResponse(String response){
        Gson gson=new Gson();
        ClinicInfoPOJO pojo = gson.fromJson(response, ClinicInfoPOJO.class);
        if(pojo!=null){
            if(pojo.getResult()!=null&&pojo.getResult().size()>0){
                HorizontalAdapter adapter = new HorizontalAdapter(getActivity(),getActivity(), pojo.getResult());

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                rv_clinic_info.setHasFixedSize(true);
                rv_clinic_info.setLayoutManager(layoutManager);

                rv_clinic_info.setAdapter(adapter);
            }
        }

    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<ClinicInfoPOJOResult> horizontalList;
        private Context context;
        private Activity activity;


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public CardView cv_clinic;
            public ImageView imageView;
            public LinearLayout linearLayout;
            public TextView tv_clinic_name;
            public TextView tv_clinic_address;
            public TextView tv_city;
            public TextView tv_pin_code;
            public TextView tv_degree;
            public TextView tv_specialist;
            public TextView tv_mon_mrng_time;
            public TextView tv_mon_eve_time;
            public TextView tv_tue_mrng_time;
            public TextView tv_tue_eve_time;
            public TextView tv_wed_mrng_time;
            public TextView tv_wed_eve_time;
            public TextView tv_thu_mrng_time;
            public TextView tv_thu_eve_time;
            public TextView tv_fri_mrng_time;
            public TextView tv_fri_eve_time;
            public TextView tv_sat_mrng_time;
            public TextView tv_sat_eve_time;
            public TextView tv_sun_mrng_time;
            public TextView tv_sun_eve_time;

            public LinearLayout ll_mon;
            public LinearLayout ll_tue;
            public LinearLayout ll_wed;
            public LinearLayout ll_thu;
            public LinearLayout ll_fri;
            public LinearLayout ll_sat;
            public LinearLayout ll_sun;
            public MyViewHolder(View view) {
                super(view);
                cv_clinic= (CardView) view.findViewById(R.id.cv_clinic);
                imageView= (ImageView) view.findViewById(R.id.scrollableimg);
                linearLayout= (LinearLayout) view.findViewById(R.id.linearlayout);
                tv_clinic_name= (TextView) view.findViewById(R.id.tv_clinic_name);
                tv_clinic_address= (TextView) view.findViewById(R.id.tv_clinic_address);
                tv_city= (TextView) view.findViewById(R.id.tv_city);
                tv_pin_code= (TextView) view.findViewById(R.id.tv_pin_code);
                tv_degree= (TextView) view.findViewById(R.id.tv_degree);
                tv_specialist= (TextView) view.findViewById(R.id.tv_specialist);
                tv_mon_mrng_time= (TextView) view.findViewById(R.id.tv_mon_mrng_time);
                tv_mon_eve_time= (TextView) view.findViewById(R.id.tv_mon_eve_time);
                tv_tue_mrng_time= (TextView) view.findViewById(R.id.tv_tue_mrng_time);
                tv_tue_eve_time= (TextView) view.findViewById(R.id.tv_tue_eve_time);
                tv_wed_mrng_time= (TextView) view.findViewById(R.id.tv_wed_mrng_time);
                tv_thu_mrng_time= (TextView) view.findViewById(R.id.tv_thu_mrng_time);
                tv_thu_eve_time= (TextView) view.findViewById(R.id.tv_thu_eve_time);
                tv_wed_eve_time= (TextView) view.findViewById(R.id.tv_wed_eve_time);
                tv_fri_mrng_time= (TextView) view.findViewById(R.id.tv_fri_mrng_time);
                tv_fri_eve_time= (TextView) view.findViewById(R.id.tv_fri_eve_time);
                tv_sat_mrng_time= (TextView) view.findViewById(R.id.tv_sat_mrng_time);
                tv_sat_eve_time= (TextView) view.findViewById(R.id.tv_sat_eve_time);
                tv_sun_mrng_time= (TextView) view.findViewById(R.id.tv_sun_mrng_time);
                tv_sun_eve_time= (TextView) view.findViewById(R.id.tv_sun_eve_time);
                ll_mon= (LinearLayout) view.findViewById(R.id.ll_mon);
                ll_tue= (LinearLayout) view.findViewById(R.id.ll_tue);
                ll_wed= (LinearLayout) view.findViewById(R.id.ll_wed);
                ll_thu= (LinearLayout) view.findViewById(R.id.ll_thu);
                ll_fri= (LinearLayout) view.findViewById(R.id.ll_fri);
                ll_sat= (LinearLayout) view.findViewById(R.id.ll_sat);
                ll_sun= (LinearLayout) view.findViewById(R.id.ll_sun);
            }
        }


        public HorizontalAdapter(Context context, Activity activity, List<ClinicInfoPOJOResult> horizontalList) {
            this.horizontalList = horizontalList;
            this.context = context;
            this.activity=activity;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inflate_clinic_info, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
//            holder.tv_task_no.setText("Task :- "+(position+1));
            ClinicInfoPOJOResult pojo=horizontalList.get(position);
            holder.tv_clinic_name.setText(pojo.getClinic_name());
            holder.tv_clinic_address.setText(pojo.getClinic_address());
            holder.tv_city.setText(pojo.getClinic_city());
            holder.tv_pin_code.setText(pojo.getClinic_pincode());
            holder.tv_degree.setText(pojo.getClinic_degree());
            holder.tv_specialist.setText(pojo.getClinic_specialist());

            String mon_mrng_time=ValidateString(pojo.getMon_mor_starttime())+"-"+ValidateString(pojo.getMon_mor_endtime());
            String mon_eve_time=ValidateString(pojo.getMon_eve_startime())+"-"+ValidateString(pojo.getMon_eve_endtime());
            String Tue_mrng_time=ValidateString(pojo.getTue_mor_starttime())+"-"+ValidateString(pojo.getTue_mor_endtime());
            String Tue_eve_time=ValidateString(pojo.getTue_eve_startime())+"-"+ValidateString(pojo.getTue_eve_endtime());
            String Wed_mrng_time=ValidateString(pojo.getWed_mor_starttime())+"-"+ValidateString(pojo.getWed_mor_endtime());
            String Wed_eve_time=ValidateString(pojo.getWed_eve_startime())+"-"+ValidateString(pojo.getWed_eve_endtime());
            String Thus_mrng_time=ValidateString(pojo.getThus_mor_starttime())+"-"+ValidateString(pojo.getThus_mor_endtime());
            String Thus_eve_time=ValidateString(pojo.getThus_eve_startime())+"-"+ValidateString(pojo.getThus_eve_endtime());
            String Fri_mrng_time=ValidateString(pojo.getFri_mor_starttime())+"-"+ValidateString(pojo.getFri_mor_endtime());
            String Fri_eve_time=ValidateString(pojo.getFri_eve_startime())+"-"+ValidateString(pojo.getFri_eve_endtime());
            String Sat_mrng_time=ValidateString(pojo.getSat_mor_starttime())+"-"+ValidateString(pojo.getSat_mor_endtime());
            String Sat_eve_time=ValidateString(pojo.getSat_eve_startime())+"-"+ValidateString(pojo.getSat_eve_endtime());
            String Sun_mrng_time=ValidateString(pojo.getSun_mor_starttime())+"-"+ValidateString(pojo.getSun_mor_endtime());
            String Sun_eve_time=ValidateString(pojo.getSun_eve_startime())+"-"+ValidateString(pojo.getSun_eve_endtime());

            if(ValidateNewString(pojo.getMon_mor_starttime(),pojo.getMon_mor_endtime(),pojo.getMon_eve_startime(),pojo.getMon_eve_endtime())){
                holder.ll_mon.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_mon.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getTue_mor_starttime(),pojo.getTue_mor_endtime(),pojo.getTue_eve_startime(),pojo.getTue_eve_endtime())){
                holder.ll_tue.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_tue.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getWed_mor_starttime(),pojo.getWed_mor_endtime(),pojo.getWed_eve_startime(),pojo.getWed_eve_endtime())){
                holder.ll_wed.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_wed.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getThus_mor_starttime(),pojo.getThus_mor_endtime(),pojo.getThus_eve_startime(),pojo.getThus_eve_endtime())){
                holder.ll_thu.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_thu.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getFri_mor_starttime(),pojo.getFri_mor_endtime(),pojo.getFri_eve_startime(),pojo.getFri_eve_endtime())){
                holder.ll_fri.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_fri.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getSat_mor_starttime(),pojo.getSat_mor_endtime(),pojo.getSat_eve_startime(),pojo.getSat_eve_endtime())){
                holder.ll_sat.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_sat.setVisibility(View.GONE);
            }
            if(ValidateNewString(pojo.getSun_mor_starttime(),pojo.getSun_mor_endtime(),pojo.getSun_eve_startime(),pojo.getSun_eve_endtime())){
                holder.ll_sun.setVisibility(View.VISIBLE);
            }
            else{
                holder.ll_sun.setVisibility(View.GONE);
            }

            holder.tv_mon_mrng_time.setText(mon_mrng_time);
            holder.tv_mon_eve_time.setText(mon_eve_time);
            holder.tv_tue_mrng_time.setText(Tue_mrng_time);
            holder.tv_tue_eve_time.setText(Tue_eve_time);
            holder.tv_wed_mrng_time.setText(Wed_mrng_time);
            holder.tv_wed_eve_time.setText(Wed_eve_time);
            holder.tv_thu_mrng_time.setText(Thus_mrng_time);
            holder.tv_thu_eve_time.setText(Thus_eve_time);
            holder.tv_fri_mrng_time.setText(Fri_mrng_time);
            holder.tv_fri_eve_time.setText(Fri_eve_time);
            holder.tv_sat_mrng_time.setText(Sat_mrng_time);
            holder.tv_sat_eve_time.setText(Sat_eve_time);
            holder.tv_sun_mrng_time.setText(Sun_mrng_time);
            holder.tv_sun_eve_time.setText(Sun_eve_time);


            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.linearLayout.getVisibility()==View.VISIBLE) {
                        holder.linearLayout.setVisibility(View.GONE);
                    }
                    else {
                        holder.linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        public String ValidateString(String s){
            if(s.equals("")){
                return "00:00";
            }
            else{
                return s;
            }
        }
        public boolean ValidateNewString(String start_mrng_time,String end_mrng_time,String eve_start_time,String eve_end_time){
            if(!start_mrng_time.equals("")||!end_mrng_time.equals("")||!eve_start_time.equals("")||!eve_end_time.equals("")){
                return true;
            }
            else{
                return false;
            }
        }
        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }
}






