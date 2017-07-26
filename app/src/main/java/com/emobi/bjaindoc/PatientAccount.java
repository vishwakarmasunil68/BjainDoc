package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.whatsapp_profile.MainActivityDoctorProfile;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;
import patient_side.BroadCast_view;
import patient_side.Search_Doctor;

public class PatientAccount extends AppCompatActivity {

    String []IteamList = {"Profile","Patient","Appointment","Broadcast messages","Notification","Activation","Logout"};
    Integer[] imageId = {
            R.drawable.profile,
            R.drawable.otherpatient,
            R.drawable.bmsg,
            R.drawable.email,
            R.drawable.notificationsound,
            R.drawable.activation_new,
            R.drawable.logoutnew

    };
    ListView list;
    LinearLayout top,btn_submit;
    SharedPreferences settings;
    ActionBar actionBar;

    SharedPreferences.Editor editor;
    RelativeLayout share_App,patient_layout,mid_layout,notification_layout,activat_layout;
    public static CircleImageView profilepic;
    public static TextView textDoc,toolbar_title,sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientsecond);


        /*actionBar=getSupportActionBar();
        SpannableString s = new SpannableString("  My Account");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(R.drawable.bjainicon);
//        actionBar.set
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("  My Account");
        actionBar.setTitle(s);*/

        list = (ListView)findViewById(R.id.list);
        top = (LinearLayout) findViewById(R.id.top);
        btn_submit = (LinearLayout) findViewById(R.id.btn_submit);
        textDoc = (TextView) findViewById(R.id.textDoc);
        sign = (TextView) findViewById(R.id.sign);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        share_App =(RelativeLayout)findViewById(R.id.rl_appointments);
        patient_layout =(RelativeLayout)findViewById(R.id.rl_interaction);
        mid_layout =(RelativeLayout)findViewById(R.id.mid_layout);
        notification_layout =(RelativeLayout)findViewById(R.id.rl_prescription);
        activat_layout =(RelativeLayout)findViewById(R.id.rl_notes);
        profilepic= (CircleImageView) findViewById(R.id.profilepic);
        TextView share_App1=(TextView)findViewById(R.id.share1);
        TextView rate1=(TextView)findViewById(R.id.rate1);
        TextView how_1=(TextView)findViewById(R.id.how_1);
        TextView notification_stop1=(TextView)findViewById(R.id.notification_stop1);
        TextView notification_sound1=(TextView)findViewById(R.id.notification_sound1);

        refresh();

        try {
            if (PreferenceData.getPatientPhoto(getApplicationContext()).length() > 0) {
                String bitmap = "http://www.bjain.com/doctor/upload/" + PreferenceData.getPatientPhoto(getApplicationContext());

                Log.e("stringToBitmap", bitmap.toString());
                Picasso.with(getApplicationContext()).load(bitmap).resize(100, 100).into(profilepic);
            }
        }
        catch (Exception e){

        }

        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        share_App1.setTypeface(tf);
        rate1.setTypeface(tf);
        how_1.setTypeface(tf);
        notification_stop1.setTypeface(tf);
        notification_sound1.setTypeface(tf);

        textDoc.setText(PreferenceData.getPatientName(getApplicationContext()));
        list.setAdapter(new customAdapter());
        textDoc.setTypeface(tf);
        sign.setTypeface(tf);
        toolbar_title.setTypeface(tf);

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientAccount.this,MainActivity.class));
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceData.setPatientLogin(getApplicationContext(),false);
                Intent intent=new Intent(PatientAccount.this,SignWithPatient.class);
                Toast.makeText(getApplicationContext(),"Signout successfully", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finishAffinity();
            }
        });
        share_App.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientAccount.this,DoctorProfile.class));
            }
            });

        patient_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientAccount.this,GetAppointmentByPatient.class));
            }
        });

        mid_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientAccount.this, BroadCast_view.class));
            }
        });

        notification_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BroadNote_view
                startActivity(new Intent(PatientAccount.this, View_notes_patient.class));
            }
        });

        activat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientAccount.this, Search_Doctor.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
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

    public class customAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return IteamList.length;
        }

        @Override
        public Object getItem(int position) {
            return IteamList;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            convertView = inflater.inflate(R.layout.listitem,null);
            TextView text_listitem = (TextView)convertView.findViewById(R.id.text_listitem);
            ImageView image_iteam = (ImageView)convertView.findViewById(R.id.image_pic);
            text_listitem.setText(IteamList[position]);
            Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
            text_listitem.setTypeface(tf);
            image_iteam.setImageResource(imageId [position]);
            convertView.setTag(position);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int click = (Integer) v.getId();
                    if (position == 0) {
                        startActivityForResult(new Intent(PatientAccount.this,MainActivityDoctorProfile.class),1);
                    }
                    if (position == 1){
                        startActivity(new Intent(PatientAccount.this,ViewPatientList.class));
                    }
                    if (position == 2){
                        startActivity(new Intent(PatientAccount.this,ViewAppointmentList.class));
                    }
                    if (position == 3){
                        startActivity(new Intent(PatientAccount.this, BroadCast_Docview.class));
                    }
                    if (position == 4){
                        startActivity(new Intent(PatientAccount.this, View_notes.class));
                    }
                    if (position == 5) {
                        startActivity(new Intent(PatientAccount.this, ViewPatientListactivation.class));
                    }
                    if (position == 6) {
                        settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
                        editor = settings.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(PatientAccount.this, SIgnInOption.class);
                        Toast.makeText(getApplicationContext(), "logout successfully", Toast.LENGTH_LONG).show();
                        LoginManager.getInstance().logOut();
                        startActivity(intent);
                        finishAffinity();
                    }
                }
            });
            return convertView;
        }
    }

    public void refresh(){
        Log.d("va","refresh.");
        new CallServices().execute(ApiConfig.LOGIN_PATIENT);
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
            pd = new ProgressDialog(PatientAccount.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("p_login_id", PreferenceData.getPatientEmail(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", PreferenceData.getPatientPassword(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("p_device_token", PreferenceData.getDevice_Token(getApplicationContext())));

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
                    Log.e("result", result);
                    JSONObject objresponse = new JSONObject(result);
                    String message = objresponse.getString("p_message");
                    if (message.equalsIgnoreCase("invalid")) {
                        Toast.makeText(getApplicationContext(), "information is incorrect", Toast.LENGTH_SHORT).show();
                        UtilsValidate.showError(getApplicationContext(), getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    } else {
                        String p_status = objresponse.getString("p_status");

                        PreferenceData.setPatientStatus(getApplicationContext(), objresponse.getString("p_status"));
                        Log.d("va","refresh...");

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == PatientAccount.this.RESULT_OK){
                String result=data.getStringExtra("result1");
                Log.e("result",result);
//                refresh();
            }
            if (resultCode == PatientAccount.this.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
