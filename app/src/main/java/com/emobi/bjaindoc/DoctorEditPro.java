package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 8/11/2016.
 */
public class DoctorEditPro extends AppCompatActivity {
    ImageView imageView_edit;
    EditText name,email,pass,confpass;
    Button btn_submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profileedit_doctor);
        imageView_edit = (ImageView) findViewById(R.id.img_edit);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        name=(EditText)findViewById(R.id.update_name);
        email=(EditText)findViewById(R.id.update_email);
        pass=(EditText)findViewById(R.id.update_password);
        confpass=(EditText)findViewById(R.id.update_confirmpassword);
        name.setText(LoginActivity.reg_name);
        email.setText(LoginActivity.reg_email);
        pass.setText(LoginActivity.reg_pass);
        confpass.setText(LoginActivity.reg_cpass);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CallServices().execute(ApiConfig.UPDATE_DOCTOR_INFO);
            }
        });
    }
    public class CallServices extends AsyncTask<String, String, String> {
        final String name1 =name.getText().toString().trim();
        final String email1 =email.getText().toString().trim();
        final String password =pass.getText().toString().trim();
        final String confirm_password =confpass.getText().toString().trim();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(DoctorEditPro.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("reg_id", PreferenceData.getId(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("reg_name", name1));
            namevaluepair.add(new BasicNameValuePair("reg_pass", password));
            namevaluepair.add(new BasicNameValuePair("reg_email", email1));
            namevaluepair.add(new BasicNameValuePair("reg_cpass", confirm_password));


            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

                Log.e("result",result.toString());
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
                Toast.makeText(getApplicationContext(),"Successfully patient added",Toast.LENGTH_LONG).show();
                finish();
                try {


                    JSONObject jobj = new JSONObject(result);

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


    }
}
