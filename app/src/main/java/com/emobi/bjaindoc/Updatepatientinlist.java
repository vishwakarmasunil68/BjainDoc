package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 8/16/2016.
 */
public class Updatepatientinlist extends AppCompatActivity{
    EditText ed1, ed3, ed4, ed5, ed6, ed7,edtxt_descrption;
    public static final String REGISTER_URL = "http://www.bjain.com/doctor/registration.php";

    LinearLayout btn_Submit;
    RadioGroup rg1,rg2;
    String rate1,p_medication,p_mob,p_digoxin,p_alergi,rate2,reason;
    EditText ed2;
    ImageView backarrow;
    TextView toolbar_title;
    ActionBar actionBar;
    RadioButton Normal,Critical;
    RadioButton active,deactive;
    public Updatepatientinlist() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateinfopatient);
        backarrow = (ImageView) findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
//        actionBar=getSupportActionBar();
//        SpannableString s = new SpannableString("  Edit Profile");
//        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////        actionBar.setDisplayHomeAsUpEnabled(true);
////        actionBar.setHomeButtonEnabled(true);
////        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
//        actionBar.setLogo(R.drawable.bjainicon);
////        actionBar.set
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
////            actionBar.setSubtitle(getString(R.string.subtitle));
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle("  Edit Profile");
//        actionBar.setTitle(s);
        ed1 = (EditText) findViewById(R.id.edtxt_pname);
        ed2 = (EditText) findViewById(R.id.edtxt_pemail);
        ed3 = (EditText) findViewById(R.id.edtxt_ppassword);
        ed4 = (EditText) findViewById(R.id.edtxt_page);
        ed5 = (EditText) findViewById(R.id.edtxt_pbgroup);
        btn_Submit = (LinearLayout) findViewById(R.id.btn_submit);
        ed6 = (EditText) findViewById(R.id.edtxt_pwt);
        ed7 = (EditText) findViewById(R.id.edtxt_pheight);
        edtxt_descrption = (EditText) findViewById(R.id.edtxt_descrption);

        rg1 = (RadioGroup) findViewById(R.id.p_rg_Account);
        rg2 = (RadioGroup) findViewById(R.id.p_rg_Condition);

        active = (RadioButton) findViewById(R.id.active);
        deactive = (RadioButton) findViewById(R.id.deactive);

        Normal = (RadioButton) findViewById(R.id.Normal);
        Critical = (RadioButton) findViewById(R.id.Critical);

        //        refresh();
        reason = getIntent().getStringExtra("reason");


        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        toolbar_title.setTypeface(tf);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Typeface tf1= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        ed1.setTypeface(tf1);
        ed2.setTypeface(tf1);
        ed3.setTypeface(tf1);
        ed4.setTypeface(tf1);
        ed5.setTypeface(tf1);
        ed6.setTypeface(tf1);
        ed7.setTypeface(tf1);
        edtxt_descrption.setTypeface(tf1);
        active.setTypeface(tf1);
        deactive.setTypeface(tf1);
        Normal.setTypeface(tf1);
        Critical.setTypeface(tf1);
        refresh();
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos1 = rg1.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos1) {

                    case 0:
                        rate1 = "active";
                        break;
                    case 1:
                        rate1 = "deactive";
                        break;
                    default:
                        break;
                }
            }
        });


        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos1 = rg2.indexOfChild(findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos1) {

                    case 0:
                        rate2 = "Normal";
                        break;
                    case 1:
                        rate2 = "Critical";
                        break;
                    default:
                        break;
                }
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new CallServices().execute(ApiConfig.UPDATE_PATIENT);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"please fill all information",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void refresh(){
        new CallServicesrefresh().execute(ApiConfig.LOGIN_PATIENT);
    }

    public class CallServices extends AsyncTask<String, String, String> {
        final String name = ed1.getText().toString().trim();
        final String email = ed2.getText().toString().trim();
        final String password = ed3.getText().toString().trim();
        final String age = ed4.getText().toString().trim();
        final String blood = ed5.getText().toString().trim();
        final String weigt = ed6.getText().toString().trim();
        final String height = ed7.getText().toString().trim();
        final String description = edtxt_descrption.getText().toString().trim();
        final String activation = rate1.toString().trim();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                pd = new ProgressDialog(Updatepatientinlist.this);

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("p_id", PreferenceData.getPatientId(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("p_photo", PreferenceData.getPatientPhoto(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("p_name", name));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", password));
            namevaluepair.add(new BasicNameValuePair("p_login_id", email));
            namevaluepair.add(new BasicNameValuePair("p_age", age));
            namevaluepair.add(new BasicNameValuePair("p_bloodgroup", blood));
            namevaluepair.add(new BasicNameValuePair("p_weight", weigt));
            namevaluepair.add(new BasicNameValuePair("p_height", height ));
            namevaluepair.add(new BasicNameValuePair("p_status", activation));
            namevaluepair.add(new BasicNameValuePair("description", description));
            namevaluepair.add(new BasicNameValuePair("p_medication", p_medication));
            namevaluepair.add(new BasicNameValuePair("p_alergi", p_alergi));
            namevaluepair.add(new BasicNameValuePair("p_digoxin", p_digoxin));
            namevaluepair.add(new BasicNameValuePair("p_mob", p_mob));
            namevaluepair.add(new BasicNameValuePair("conditions", rate2));


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
}catch (Exception e){
    e.toString();
}
            Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    public class CallServicesrefresh extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                pd = new ProgressDialog(Updatepatientinlist.this);

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
            namevaluepair.add(new BasicNameValuePair("p_login_id", PreferenceData.getPatientEmail(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", PreferenceData.getPatientPassword(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("p_device_token", PreferenceData.getPatient_Device_Token(getApplicationContext())));

            //namevaluepair.add(new BasicNameValuePair("cat", "HAIR"));

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
                    JSONObject objresponse = new JSONObject(result);
                    Log.e("sub", objresponse.toString());
                    String message = objresponse.getString("p_message");
                    if (message.equalsIgnoreCase("invalid")) {
                        Toast.makeText(getApplicationContext(), "information is incorrect", Toast.LENGTH_SHORT).show();
                        UtilsValidate.showError(getApplicationContext(), getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    }

                    else {
                        String p_name = objresponse.getString("p_name");
                        String p_login_id = objresponse.getString("p_login_id");
                        String  p_pass = objresponse.getString("p_login_pass");
                        String  p_status = objresponse.getString("p_status");
                        String  p_weight = objresponse.getString("p_weight");
                        String p_age = objresponse.getString("p_age");
                        String p_bloodgroup = objresponse.getString("p_bloodgroup");
                        String p_height = objresponse.getString("p_height");
                        String condition = objresponse.getString("conditions");
                        String description = objresponse.getString("description");
                         p_medication = objresponse.getString("p_medication");
                         p_alergi = objresponse.getString("p_alergi");
                          p_mob = objresponse.getString("p_mob");
                          p_digoxin = objresponse.getString("p_digoxin");
                        ed1.setText(p_name);
                        ed2.setText(p_login_id);
                        ed3.setText(p_pass);
                        ed4.setText(p_age);
                        ed5.setText(p_bloodgroup);
                        ed6.setText(p_weight);
                        ed7.setText(p_height);
                        if (condition.equals("Normal")) {
                            rg2.check(R.id.Normal);
                        }
                        else{
                            rg2.check(R.id.Critical);
                        }
                        if (p_status.equals("active"))
                        {
                            rg1.check(R.id.active);
                        }
                        else {
                            rg1.check(R.id.deactive);
                        }
                        edtxt_descrption.setText(description);
//                        PreferenceData.setPatientStatus(getActivity().getApplicationContext(),objresponse.getString("p_status"));

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

}






