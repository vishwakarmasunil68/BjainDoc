package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 8/24/2016.
 */
public class BroadCast_Msg_Class extends AppCompatActivity {
    RelativeLayout relativeLayout;
    Button btn_submit;
    ImageView text_medication;
    EditText edtxt_medication;
    String id;
    FloatingActionButton fab;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broad_msg);
        edtxt_medication=(EditText)findViewById(R.id.edtxt_medication);
        btn_submit=(Button)findViewById(R.id.btn_submit);
//        fab=(FloatingActionButton)findViewById(R.id.fab);
        relativeLayout=(RelativeLayout)findViewById(R.id.rellayout);
        text_medication=(ImageView)findViewById(R.id.text_medication);
        id=getIntent().getStringExtra("reason");
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BroadCast_Msg_Class.this,View_notes.class));
            }
        });*/
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
                new CallServices().execute(ApiConfig.SEND_BROADCAST_MESSAGE);
            }
        });
    }
    public class CallServices extends AsyncTask<String, String, String> {
        String contentstring = edtxt_medication.getText().toString();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(BroadCast_Msg_Class.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("bro_mess", contentstring));
            namevaluepair.add(new BasicNameValuePair("bro_time", UtilsValidate.getCurrentDateTime()));
            namevaluepair.add(new BasicNameValuePair("bro_date", UtilsValidate.getCurrentDate()));
            namevaluepair.add(new BasicNameValuePair("bro_doc_id", PreferenceData.getId(getApplicationContext())));


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

            if (pd != null) {
                pd.dismiss();
            }


            if (result != null) {

                try {
                    /*JSONObject objresponse = new JSONObject(result);
                    String message = objresponse.getString("message");
                    if (message.equalsIgnoreCase("invalid")) {
                        UtilsValidate.showError(BroadCast_Msg_Class.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    } else {
                        String reg_id = objresponse.getString("reg_id");

                        *//*Intent intent = new Intent(Medication.this, DoctorAccount.class);
                        startActivity(intent);*//*

                    }*/
                    edtxt_medication.setText("");
                    Toast.makeText(BroadCast_Msg_Class.this,"message send successfully",Toast.LENGTH_SHORT).show();
                    Log.e("result", result.toString());


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }
    }
}
