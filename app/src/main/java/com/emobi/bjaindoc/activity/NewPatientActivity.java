package com.emobi.bjaindoc.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emobi.bjaindoc.ApiConfig;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.pojo.patient.PatientPOJO;
import com.emobi.bjaindoc.pojo.patient.PatientResultPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.emobi.bjaindoc.utls.ToastClass;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.PreferenceData;

public class NewPatientActivity extends AppCompatActivity implements WebServicesCallBack{

    private final String TAG=getClass().getSimpleName();
    private final static String GET_PATIENT_LIST="get_patient_list";
    private final static String ACTIVATE_PATIENT="activate_patient";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.backarrow)
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);
//        Log.d(TAG,"doc_id:-"+PreferenceData.getId(getApplicationContext()));
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }
    public void getList(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("method", "getpatients"));
        nameValuePairs.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
        new WebServiceBase(nameValuePairs, this, GET_PATIENT_LIST).execute(ApiConfig.PATIENT_ACTIVATION);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_PATIENT_LIST:
                parseGetPatients(response);
                break;
            case ACTIVATE_PATIENT:
                parseActivatePatients(response);
                break;
        }
    }
    List<PatientResultPOJO> list_of_patients;
    public void parseGetPatients(String response){
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
        ll_scroll.removeAllViews();
        for (int i = 0; i < list_patients.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_activate_patients, null);
            LinearLayout ll_patient = (LinearLayout) view.findViewById(R.id.ll_patient);
            ImageView iv_profile = (ImageView) view.findViewById(R.id.iv_profile);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_email = (TextView) view.findViewById(R.id.tv_email);
            TextView tv_activate = (TextView) view.findViewById(R.id.tv_activate);

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
            tv_activate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activatePatient(list_patients.get(finalI));
                }
            });

            ll_scroll.addView(view);
        }
    }
    public void activatePatient(PatientResultPOJO patientResultPOJO){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("method", "activatepatient"));
        nameValuePairs.add(new BasicNameValuePair("p_id", patientResultPOJO.getP_id()));
        new WebServiceBase(nameValuePairs, this, ACTIVATE_PATIENT).execute(ApiConfig.PATIENT_ACTIVATION);
    }

    public void parseActivatePatients(String response){
        Log.d(TAG,"activated response:-"+response);
        try{
//            JSONObject jsonObject=new JSONObject(response);
            if(response.contains("true")){
                getList();
                ToastClass.ShowLongToast(getApplicationContext(),"Patient Activated");
            }
            else{
                ToastClass.ShowLongToast(getApplicationContext(),"Patient not Activated");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            ToastClass.ShowLongToast(getApplicationContext(),"something went wrong");
        }
    }
}
