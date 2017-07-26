package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.whatsapp_profile.HeaderView;
import com.anton46.whatsapp_profile.MainActivityDoctorProfile;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

public class PatientCompleteDetailAccount extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{


    @BindView(R.id.toolbar_header_view)
    protected HeaderView toolbarHeaderView;

    @BindView(R.id.float_header_view)
    protected HeaderView floatHeaderView;

    @BindView(R.id.appbar)
    protected AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    TextView text_hdr_name,d_name,d_email_id,d_address;
    public static String reg_id, reg_name, reg_email, reg_pass, reg_mob, dob, reg_department, reg_clinic_address, reg_designation, reg_specialist, reg_degree;
    CircleImageView prof_pic;
    ImageView clear;
    ImageView edit;
    Button btn_booking;
    private boolean isHideToolbarView = false;
    TextView last_seen;
    LinearLayout linear_d_all_timing,alldata;
    String []IteamList = {"Chat","Medication","Prescription","Notes"};
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
    ImageView bg_image,txt_email;
    SharedPreferences settings;
//    ActionBaactionBar;
    SharedPreferences.Editor editor;
    ImageView backarrow;
    RelativeLayout share_App,patient_layout,mid_layout,notification_layout,activat_layout;
    public static CircleImageView profilepic;
    public static String p_status;
    String message;
    public static TextView toolbar_title,textDoc,sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor_patient_profile);


        list = (ListView)findViewById(R.id.list);
        top = (LinearLayout) findViewById(R.id.top);
        btn_submit = (LinearLayout) findViewById(R.id.btn_submit);
        bg_image =(ImageView)findViewById(R.id.iv_profile_image);
        txt_email = (ImageView)findViewById(R.id.txt_email);
        textDoc = (TextView) findViewById(R.id.textDoc);
        sign = (TextView) findViewById(R.id.sign);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        share_App =(RelativeLayout)findViewById(R.id.rl_appointments);
        patient_layout =(RelativeLayout)findViewById(R.id.rl_interaction);
        mid_layout =(RelativeLayout)findViewById(R.id.mid_layout);
        notification_layout =(RelativeLayout)findViewById(R.id.rl_prescription);
        activat_layout =(RelativeLayout)findViewById(R.id.rl_notes);
        profilepic= (CircleImageView) findViewById(R.id.profilepic);
        backarrow = (ImageView) findViewById(R.id.backarrow);
        TextView share_App1=(TextView)findViewById(R.id.share1);
        TextView rate1=(TextView)findViewById(R.id.rate1);
        TextView how_1=(TextView)findViewById(R.id.how_1);
        TextView notification_stop1=(TextView)findViewById(R.id.notification_stop1);
        TextView notification_sound1=(TextView)findViewById(R.id.notification_sound1);


        String p_id  =getIntent().getStringExtra("reason");
        p_status =getIntent().getStringExtra("patient_status");
        PreferenceData.setPatientID(getApplicationContext(),p_id);

        String photo = getIntent().getStringExtra("patient_photo");
        try {
            if (photo.length() > 0) {
                Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + getIntent().getStringExtra("patient_photo")).into(bg_image);
            }
            else {

            }

        }
        catch (Exception e) {
            Log.d("sahil", e.toString());
        }
        try {
            if (photo.length() > 0) {
                String bitmap = "http://www.bjain.com/doctor/upload/" + getIntent().getStringExtra("patient_photo");

                Log.e("stringToBitmap", bitmap.toString());
                Picasso.with(getApplicationContext()).load(bitmap).resize(100, 100).into(profilepic);
            }
        }
        catch (Exception e){

        }

        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        share_App1.setTypeface(tf);
        rate1.setTypeface(tf);
        toolbar_title.setTypeface(tf);
        how_1.setTypeface(tf);
        notification_stop1.setTypeface(tf);
        notification_sound1.setTypeface(tf);

        message = getIntent().getStringExtra("patient_name");
        textDoc.setText(message);
        toolbar_title.setText(message);
        list.setAdapter(new customAdapter());
        textDoc.setTypeface(tf);
        sign.setTypeface(tf);



        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientCompleteDetailAccount.this,Updatepatientinlist.class));
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceData.setPatientLogin(getApplicationContext(),false);
                Intent intent=new Intent(PatientCompleteDetailAccount.this,SIgnInOption.class);
                Toast.makeText(getApplicationContext(),"Signout successfully", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finishAffinity();
            }
        });
        share_App.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientCompleteDetailAccount.this,Medication.class));
            }
            });

        patient_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientCompleteDetailAccount.this,ChatActivity.class));
            }
        });

        mid_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientCompleteDetailAccount.this, Medication.class));
            }
        });

        notification_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BroadNote_view
                startActivity(new Intent(PatientCompleteDetailAccount.this, Prescription.class));
            }
        });

        activat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientCompleteDetailAccount.this, DoctorNotes.class));
            }
        });

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        initUi();
    }

    private void initUi() {
        appBarLayout.addOnOffsetChangedListener(this);

        toolbarHeaderView.bindTo(message, "");
        floatHeaderView.bindTo(message, "");
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;



        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
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
                        startActivityForResult(new Intent(PatientCompleteDetailAccount.this,MainActivityDoctorProfile.class),1);
                    }
                    if (position == 1){
                        startActivity(new Intent(PatientCompleteDetailAccount.this,ViewPatientList.class));
                    }
                    if (position == 2){
                        startActivity(new Intent(PatientCompleteDetailAccount.this,ViewAppointmentList.class));
                    }
                    if (position == 3){
                        startActivity(new Intent(PatientCompleteDetailAccount.this, BroadCast_Docview.class));
                    }
                    if (position == 4){
                        startActivity(new Intent(PatientCompleteDetailAccount.this, View_notes.class));
                    }
                    if (position == 5) {
                        startActivity(new Intent(PatientCompleteDetailAccount.this, ViewPatientListactivation.class));
                    }
                    if (position == 6) {
                        settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
                        editor = settings.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(PatientCompleteDetailAccount.this, SIgnInOption.class);
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
            pd = new ProgressDialog(PatientCompleteDetailAccount.this);

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
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == PatientCompleteDetailAccount.this.RESULT_OK){
                String result=data.getStringExtra("result1");
                Log.e("result",result);
//                refresh();
            }
            if (resultCode == PatientCompleteDetailAccount.this.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
