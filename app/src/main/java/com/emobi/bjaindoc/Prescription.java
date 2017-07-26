package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.emobi.bjaindoc.pojo.member.MemberResultPOJO;
import com.emobi.bjaindoc.pojo.patient.PatientResultPOJO;
import com.emobi.bjaindoc.utls.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Emobi-Android-002 on 8/17/2016.
 */
public class Prescription extends AppCompatActivity {

    Button btn_submit;
    InfoApps Detailapp;
    public static ArrayList<InfoApps> contactDetails2;
    private ListView mRecyclerView;
    public static String id;
    ProgressDialog pd;
    ActionBar actionBar;
    UsersAdapterPrescriptionDoc mAdapter2;
    FloatingActionButton fab;
    public static PrecriptionPOJO pojo=null;
    ImageView backarrow;
    TextView toolbar_title;
    public Prescription() {
        // Required empty public constructor
    }
    PatientResultPOJO patientResultPOJO;

    MemberResultPOJO memberResultPOJO;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescriptiondocside);
        ButterKnife.bind(this);
        patientResultPOJO= (PatientResultPOJO) getIntent().getSerializableExtra("patient");
        memberResultPOJO= (MemberResultPOJO) getIntent().getSerializableExtra("member");
        if(patientResultPOJO!=null){
            id=patientResultPOJO.getP_id();
        }
        else{
            finish();
        }


        backarrow = (ImageView) findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        contactDetails2 = new ArrayList<InfoApps>();
        mRecyclerView = (ListView) findViewById(R.id.recycler_view);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_title.setTypeface(tf);

    }

    public void refresh() {
        if(memberResultPOJO!=null){
            ArrayList<NameValuePair> namevaluepair=new ArrayList<>();
            namevaluepair.add(new BasicNameValuePair("m_id", memberResultPOJO.getM_id()));
            new CallServices1(namevaluepair).execute(ApiConfig.VIEW_MEMBER_PRESCRIPTION_URL);
        }
        else {
            ArrayList<NameValuePair> namevaluepair=new ArrayList<>();
            namevaluepair.add(new BasicNameValuePair("pre_p_id", patientResultPOJO.getP_id()));
            new CallServices1(namevaluepair).execute(ApiConfig.VIEW_PRESCRIPTION_URL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(pojo!=null)
                Intent i = new Intent(getApplicationContext(),PrescriptionActivity.class);
                i.putExtra("patient",patientResultPOJO);
                if(memberResultPOJO!=null){
                    i.putExtra("member",memberResultPOJO);
                }
                startActivityForResult(i, 1);
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
    private final String TAG=getClass().getSimpleName();
    public class CallServices1 extends AsyncTask<String, String, String> {

        ArrayList<NameValuePair> namevaluepair;
        String result;
        String tag;
        public CallServices1(ArrayList<NameValuePair> namevaluepair){
            this.namevaluepair=namevaluepair;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            contactDetails2.clear();
            super.onPreExecute();
            try {
                pd = new ProgressDialog(Prescription.this);

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
            Log.d(TAG,"patient id:-"+id);
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

            try {
                if (pd != null) {
                    pd.dismiss();
                }
            }
            catch (Exception e){

            }
            if (result != null) {
                try {
                    JSONObject object=new JSONObject(result);
                    if(object.optString("success").equals("true")) {
                        JSONArray jsonArray = object.optJSONArray("result");
                        Log.e("Post Method", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            Log.e("2", jsonObject2.toString());
                            String pre_date = jsonObject2.getString("pre_datetime");
                            String pre_medicine = jsonObject2.getString("pre_medicine");
                            String pre_file = jsonObject2.getString("pre_file");

                            InfoApps Detailapp = new InfoApps();
                            Detailapp.setNumber(pre_date);
                            Detailapp.setName(pre_medicine);
                            Detailapp.setDataAdd(pre_file);


                            contactDetails2.add(Detailapp);
//                        Log.d("contactDetails2",contactDetails2.toString());

                        }
                        for (InfoApps obj : contactDetails2) {
                            Log.d("sunil", obj.toString());
                        }
                        UsersAdapter2 mAdapter2 = new UsersAdapter2(Prescription.this, R.layout.view_patient_pre_adapter, contactDetails2);
                        mRecyclerView.setAdapter(mAdapter2);

                    }
                    else{
                        ToastClass.ShowLongToast(getApplicationContext(),"No Prescription Found");
                    }

                } catch (Exception e) {
                    Log.d(TAG,e.toString());
                    e.printStackTrace();
                }


            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                Log.e("result", result);
                refresh();
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
