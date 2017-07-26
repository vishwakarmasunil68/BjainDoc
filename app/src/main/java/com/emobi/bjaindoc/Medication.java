package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.pojo.medication.MedicationPOJO;
import com.emobi.bjaindoc.pojo.medication.MedicationResultPOJO;
import com.emobi.bjaindoc.pojo.member.MemberResultPOJO;
import com.emobi.bjaindoc.pojo.patient.PatientResultPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Emobi-Android-002 on 8/17/2016.
 */
public class Medication extends AppCompatActivity implements WebServicesCallBack {
    RelativeLayout relativeLayout;
    InfoApps Detailapp;
    ProgressDialog pd;
    public static ArrayList<InfoApps> contactDetails1;
    private RecyclerView mRecyclerView;
    ImageView btn_submit;
    ImageView text_medication;
    ActionBar actionBar;
    EditText edtxt_medication;
    ImageView backarrow;
    TextView toolbar_title;
    UsersAdapterMedi_Doc mAdapter1;
    String id;

    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.scroll_layout)
    ScrollView scroll_layout;

    PatientResultPOJO patientResultPOJO;
    MemberResultPOJO memberResultPOJO;

    public Medication() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication);
        ButterKnife.bind(this);

        patientResultPOJO = (PatientResultPOJO) getIntent().getSerializableExtra("patient");
        memberResultPOJO= (MemberResultPOJO) getIntent().getSerializableExtra("member");

        if (patientResultPOJO != null) {
            id = patientResultPOJO.getP_id();
            Log.d(TAG,"token:-"+patientResultPOJO.toString());
        } else {
            finish();
        }

        backarrow = (ImageView) findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        contactDetails1 = new ArrayList<InfoApps>();
        edtxt_medication = (EditText) findViewById(R.id.edtxt_medication);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btn_submit = (ImageView) findViewById(R.id.btn_submit);
        relativeLayout = (RelativeLayout) findViewById(R.id.rellayout);
        text_medication = (ImageView) findViewById(R.id.text_medication);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_title.setTypeface(tf);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        new CallServices1().execute(ApiConfig.VIEW_MEDICATION_URL);
        GetALLMedication();
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_medication.setVisibility(View.GONE);
            }
        });
        text_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtxt_medication.setText("");
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtxt_medication.getText().toString().length() > 0) {
                    if(memberResultPOJO!=null){
                        ArrayList<NameValuePair> namevaluepair=new ArrayList<NameValuePair>();
                        namevaluepair.add(new BasicNameValuePair("med_mess", edtxt_medication.getText().toString()));
                        namevaluepair.add(new BasicNameValuePair("med_p_id", patientResultPOJO.getP_id()));
                        namevaluepair.add(new BasicNameValuePair("med_time", UtilsValidate.getCurrentTimeaccforma()));
                        namevaluepair.add(new BasicNameValuePair("med_date", UtilsValidate.getCurrentDate()));
                        namevaluepair.add(new BasicNameValuePair("med_doc_id", patientResultPOJO.getDoc_id()));
                        namevaluepair.add(new BasicNameValuePair("med_m_id", memberResultPOJO.getM_id()));
                        namevaluepair.add(new BasicNameValuePair("token", patientResultPOJO.getP_device_token()));
                        new CallServices(namevaluepair).execute(ApiConfig.SEND_MEDICATION_TO_MEMBER);
                    }
                    else {
                        ArrayList<NameValuePair> namevaluepair=new ArrayList<NameValuePair>();
                        namevaluepair.add(new BasicNameValuePair("med_mess", edtxt_medication.getText().toString()));
                        namevaluepair.add(new BasicNameValuePair("med_p_id", patientResultPOJO.getP_id()));
                        namevaluepair.add(new BasicNameValuePair("med_time", UtilsValidate.getCurrentTimeaccforma()));
                        namevaluepair.add(new BasicNameValuePair("med_date", UtilsValidate.getCurrentDate()));
                        namevaluepair.add(new BasicNameValuePair("med_doc_id", patientResultPOJO.getDoc_id()));
                        namevaluepair.add(new BasicNameValuePair("token", patientResultPOJO.getP_device_token()));
                        new CallServices(namevaluepair).execute(ApiConfig.MEDICATION_URL);
                    }
                }
            }
        });

        edtxt_medication.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Log.e("shubham", "next one");
                    edtxt_medication.setSelection(edtxt_medication.getText().length() + 1);

                    // Perform action on Enter key press
                    /*txtUserid.clearFocus();
                    txtUserPasword.requestFocus();*/
                    return true;
                }
                return false;
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
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void GetALLMedication() {
        if(memberResultPOJO!=null) {
            Log.d(TAG,"member medication");
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("med_m_id", memberResultPOJO.getM_id()));
            nameValuePairs.add(new BasicNameValuePair("med_doc_id", patientResultPOJO.getDoc_id()));
            new WebServiceBase(nameValuePairs, this, GET_ALL_MEDICATION).execute(ApiConfig.VIEW_MEMBER_MEDICATION_URL);
        }
        else{
            Log.d(TAG,"patient medication");
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("med_p_id", patientResultPOJO.getP_id()));
            new WebServiceBase(nameValuePairs, this, GET_ALL_MEDICATION).execute(ApiConfig.VIEW_MEDICATION_URL);
        }
    }

    private final String TAG = getClass().getSimpleName();
    private static final String GET_ALL_MEDICATION = "get_all_medication";

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_MEDICATION:
                parseMedicationData(response);
                break;
        }
    }

    public void parseMedicationData(String response) {
        Log.d(TAG, "response:-" + response);
        try {
            Gson gson = new Gson();
            MedicationPOJO medicationPOJO = gson.fromJson(response, MedicationPOJO.class);
            if (medicationPOJO.getSuccess().equals("true")) {
                ShowALLMedication(medicationPOJO.getList_medication());
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    public void ShowALLMedication(final List<MedicationResultPOJO> list_medResultPOJOs) {
        ll_scroll.removeAllViews();
        for (int i = 0; i < list_medResultPOJOs.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.view_doc_med_adapter, null);
            TextView tv_medication = (TextView) view.findViewById(R.id.tv_medication);
            TextView tv_medication_time = (TextView) view.findViewById(R.id.tv_medication_time);

            tv_medication.setText(list_medResultPOJOs.get(i).getMed_mess());
            String date=list_medResultPOJOs.get(i).getMed_date();
            try{
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yyyy");
                date=sdf1.format(sdf.parse(date));
            }
            catch (Exception e){
                date=list_medResultPOJOs.get(i).getMed_date();
            }
            tv_medication_time.setText(date + " | "
                    + list_medResultPOJOs.get(i).getMed_time());

            ll_scroll.addView(view);
        }
        scroll_layout.post(new Runnable() {
            @Override
            public void run() {
                scroll_layout.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public class CallServices extends AsyncTask<String, String, String> {

        ArrayList<NameValuePair> namevaluepair;
        String result;
        String tag;

        public CallServices(ArrayList<NameValuePair> namevaluepair){
            this.namevaluepair=namevaluepair;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                pd = new ProgressDialog(Medication.this);

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
            } catch (Exception e) {

            }
            if (result != null) {
                Toast.makeText(getApplicationContext(), "medication send successfully", Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                edtxt_medication.setText("");
                GetALLMedication();
//                try {
//                    Log.d(TAG,"result:-"+result.toString());
//
////                    try{
////                        JSONObject object=new JSONObject(result.toString());
////                        String multicast_id=object.optString("multicast_id");
////                        if(multicast_id.length()>0){
////                            Toast.makeText(getApplicationContext(), "message send successfully", Toast.LENGTH_SHORT).show();
////                            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
////                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
////                            GetALLMedication();
////                        }
////                    }
////                    catch (Exception e){
////                        Log.d(TAG,e.toString());
////                    }
////
////                    try{
////                        JSONObject object=new JSONObject(result.toString());
////                        String success=object.optString("success");
////                        if(success.equals("false")){
////                            ToastClass.ShowLongToast(getApplicationContext(),"Message Not Sent");
////                        }
////                    }
////                    catch (Exception e){
////                        Log.d(TAG,"error:-"+e.toString());
////                    }
////                    edtxt_medication.setText("");
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
            }
        }
    }
}
