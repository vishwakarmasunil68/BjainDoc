package com.anton46.whatsapp_profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emobi.bjaindoc.ApiConfig;
import com.emobi.bjaindoc.DoctorAccount;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.TypefaceSpan;
import com.emobi.bjaindoc.UpdateByDoc;
import com.emobi.bjaindoc.Util;
import com.emobi.bjaindoc.Utils;
import com.emobi.bjaindoc.UtilsValidate;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityDoctorProfile extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

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
    ImageView bg_image,clear;
    ImageView edit;
    Button btn_booking;
    private boolean isHideToolbarView = false;
    TextView last_seen;
    LinearLayout linear_d_all_timing,alldata;
    SharedPreferences settings;
    String SigninName,Signinid,SigninEmail,Signinphoto,Signindevice_token,SigninPassword;
    public static String avl_start_time1_morn,morn_loca_day1,avl_end_time1_morn,avl_start_time1_afternoon,
            avl_end_time1_afternoon,afternoon_loca_day1,avl_start_time1_even,avl_end_time1_even,even_loca_day1
            ,
            avl_start_time2_morn,avl_end_time2_morn,morn_loca_day2,avl_start_time2_afternoon,avl_end_time2_afternoon
            ,afternoon_loca_day2,avl_start_time2_even,avl_end_time2_even,even_loca_day2,

    avl_start_time3_morn,avl_end_time3_morn,morn_loca_day3,avl_start_time3_afternoon,avl_end_time3_afternoon
            ,afternoon_loca_day3,avl_start_time3_even,avl_end_time3_even,even_loca_day3,

    avl_start_time4_morn,avl_end_time4_morn,morn_loca_day4,aafternoon_loca_day4,even_loca_day4,avl_start_time4_afternoon,avl_end_time4_afternoon
            ,avl_start_time4_even,avl_end_time4_even,

    avl_start_time5_morn,avl_end_time5_morn,avl_start_time5_afternoon,avl_end_time5_afternoon
            ,avl_start_time5_even,avl_end_time5_even,morn_loca_day5,aafternoon_loca_day5,even_loca_day5,

    avl_start_time6_morn,avl_end_time6_morn,avl_start_time6_afternoon,avl_end_time6_afternoon
            ,avl_start_time6_even,avl_end_time6_even,morn_loca_day6,aafternoon_loca_day6,even_loca_day6,

    avl_start_time7_morn,avl_end_time7_morn,avl_start_time7_afternoon,avl_end_time7_afternoon
            ,morn_loca_day7,aafternoon_loca_day7,even_loca_day7,avl_start_time7_even,avl_end_time7_even;;

    TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,
            textView9,textView10,textView11,textView12,textView13,textView14,textView15,textView16,
            textView17,textView18,textView19,textView20,textView21,textView22,textView23,
            textView24,textView25, textView26,textView27, textView28, textView29,textView30,textView31, textView32,textView33,textView34,textView35,textView36,textView37,textView38,textView39,textView40,textView41,textView42
            ,textView43,textView44,textView45,textView46,textView47,textView48,textView49,textView50,textView51,textView52,textView53,textView54,textView55,textView56,textView57,textView58,textView59,textView60
            ,textView61,textView62,textView63,textView64,textView65,textView66,textView67,textView68,textView69,textView70;
    TextView mmorngfrom,mmorngto,mevngfrom,mevngto,tumorngfrom,tumorngto,tuevngfrom,tuevngto,wmorngfrom,wmorngto
            ,wevngfrom,wevngto,thmorngfrom,thmorngto,thevngfrom,thevngto,fmorngfrom,fmorngto,fevngfrom,fevngto,samorngfrom,samorngto,
            saevngfrom,saevngto,sumorngfrom,sumorngto,suevngfrom,suevngto
            ,mplace,tuplace,wplace,thplace,fplace,saplace,suplace;
    TextView txt_d_all_timing,d_avail;
    EditText text_d_name,txt_d_email_id,txt_d_designation,txt_d_phone,text_d_address,txt_d_department,text_d_special,txt_d_dob;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor_profile);
        SpannableString s = new SpannableString("");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar=getSupportActionBar();
        linear_d_all_timing=(LinearLayout)findViewById(R.id.linear_all_timing);

        alldata=(LinearLayout)findViewById(R.id.alldata);
        txt_d_all_timing=(TextView)findViewById(R.id.txt_all_timing);
        d_avail=(TextView)findViewById(R.id.d_avail);
        text_d_name=(EditText)findViewById(R.id.text_d_name);
        txt_d_email_id=(EditText)findViewById(R.id.d_email_id);
        txt_d_designation= (EditText) findViewById(R.id.d_degree);
        txt_d_phone=(EditText)findViewById(R.id.txt_d_phone);
        text_d_address=(EditText)findViewById(R.id.d_address);
        txt_d_department=(EditText)findViewById(R.id.d_department);
        text_d_special=(EditText)findViewById(R.id.d_special);
        txt_d_dob=(EditText)findViewById(R.id.d_designation);
        text_hdr_name=(TextView)findViewById(R.id.tv_profile_name);
        last_seen=(TextView)findViewById(R.id.last_seen);
        edit=(ImageView)findViewById(R.id.img_edit);
        prof_pic=(CircleImageView)findViewById(R.id.image);
        bg_image =(ImageView)findViewById(R.id.iv_profile_image);
        clear =(ImageView)findViewById(R.id.clear);

        mmorngfrom=(TextView)findViewById(R.id.mmorngfrom);
        mmorngto=(TextView)findViewById(R.id.mmorngto);
        mevngfrom=(TextView)findViewById(R.id.mevngfrom);
        mevngto=(TextView)findViewById(R.id.mevngto);
        mplace=(TextView)findViewById(R.id.mplace);

        tumorngfrom=(TextView)findViewById(R.id.tumorngfrom);
        tumorngto=(TextView)findViewById(R.id.tumorngto);
        tuevngfrom=(TextView)findViewById(R.id.tuevngfrom);
        tuevngto=(TextView)findViewById(R.id.tuevngto);
        tuplace=(TextView)findViewById(R.id.tuplace);

        wmorngfrom=(TextView)findViewById(R.id.wmorngfrom);
        wmorngto=(TextView)findViewById(R.id.wmorngto);
        wevngfrom=(TextView)findViewById(R.id.wevngfrom);
        wevngto=(TextView)findViewById(R.id.wevngto);
        wplace=(TextView)findViewById(R.id.wedplace);

        thmorngfrom=(TextView)findViewById(R.id.thmorngfrom);
        thmorngto=(TextView)findViewById(R.id.thmorngto);
        thevngfrom=(TextView)findViewById(R.id.thevngfrom);
        thevngto=(TextView)findViewById(R.id.thevngto);
        thplace=(TextView)findViewById(R.id.thplace);

        fmorngfrom=(TextView)findViewById(R.id.fmorngfrom);
        fmorngto=(TextView)findViewById(R.id.fmorngto);
        fevngfrom=(TextView)findViewById(R.id.fevngfrom);
        fevngto=(TextView)findViewById(R.id.fevngto);
        fplace=(TextView)findViewById(R.id.fplace);

        samorngfrom=(TextView)findViewById(R.id.samorngfrom);
        samorngto=(TextView)findViewById(R.id.samorngto);
        saevngfrom=(TextView)findViewById(R.id.saevngfrom);
        saevngto=(TextView)findViewById(R.id.saevngto);
        saplace=(TextView)findViewById(R.id.saplace);

        sumorngfrom=(TextView)findViewById(R.id.sumorngfrom);
        sumorngto=(TextView)findViewById(R.id.sumorngto);
        suevngfrom=(TextView)findViewById(R.id.suevngfrom);
        suevngto=(TextView)findViewById(R.id.suevngto);
        suplace=(TextView)findViewById(R.id.suplace);

        txt_d_all_timing.setVisibility(View.VISIBLE);
        d_avail.setVisibility(View.GONE);

        Typeface tf1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
//        text_d_name.setTypeface(tf1);
        txt_d_email_id.setTypeface(tf1);
        text_hdr_name.setTypeface(tf1);
        last_seen.setTypeface(tf1);
        text_d_address.setTypeface(tf1);
        txt_d_department.setTypeface(tf1);
        text_d_special.setTypeface(tf1);
        txt_d_dob.setTypeface(tf1);
        txt_d_all_timing.setTypeface(tf1);

        textView1=(TextView)findViewById(R.id.textView1);
        textView2=(TextView)findViewById(R.id.textView2);
        textView3=(TextView)findViewById(R.id.textView3);
        textView4=(TextView)findViewById(R.id.textView4);
        textView5=(TextView)findViewById(R.id.textView5);
        textView6=(TextView)findViewById(R.id.textView6);
        textView7=(TextView)findViewById(R.id.textView7);
        textView8=(TextView)findViewById(R.id.textView8);
        textView9=(TextView)findViewById(R.id.textView9);
        textView10=(TextView)findViewById(R.id.textView10);
        textView11=(TextView)findViewById(R.id.textView11);
        textView12=(TextView)findViewById(R.id.textView12);
        textView13=(TextView)findViewById(R.id.textView13);
        textView14=(TextView)findViewById(R.id.textView14);
        textView15=(TextView)findViewById(R.id.textView15);
        textView16=(TextView)findViewById(R.id.textView16);
        textView17=(TextView)findViewById(R.id.textView17);
        textView18=(TextView)findViewById(R.id.textView18);
        textView19=(TextView)findViewById(R.id.textView19);
        textView20=(TextView)findViewById(R.id.textView20);
        textView21=(TextView)findViewById(R.id.textView21);
        textView22=(TextView)findViewById(R.id.textView22);
        textView23=(TextView)findViewById(R.id.textView23);
        textView24=(TextView)findViewById(R.id.textView24);
        textView25=(TextView)findViewById(R.id.textView25);
        textView26=(TextView)findViewById(R.id.textView26);
        textView27=(TextView)findViewById(R.id.textView27);
        textView28=(TextView)findViewById(R.id.textView28);
        textView29=(TextView)findViewById(R.id.textView29);
        textView30=(TextView)findViewById(R.id.textView30);
        textView31=(TextView)findViewById(R.id.textView31);
        textView32=(TextView)findViewById(R.id.textView32);
        textView33=(TextView)findViewById(R.id.textView33);
        textView34=(TextView)findViewById(R.id.textView34);
        textView35=(TextView)findViewById(R.id.textView35);
        textView36=(TextView)findViewById(R.id.textView36);
        textView37=(TextView)findViewById(R.id.textView37);
        textView38=(TextView)findViewById(R.id.textView38);
        textView39=(TextView)findViewById(R.id.textView39);
        textView40=(TextView)findViewById(R.id.textView40);
        textView41=(TextView)findViewById(R.id.textView41);
        textView42=(TextView)findViewById(R.id.textView42);
        textView43=(TextView)findViewById(R.id.textView43);
        textView44=(TextView)findViewById(R.id.textView44);
        textView45=(TextView)findViewById(R.id.textView45);
        textView46=(TextView)findViewById(R.id.textView46);
        textView47=(TextView)findViewById(R.id.textView47);
        textView48=(TextView)findViewById(R.id.textView48);
        textView49=(TextView)findViewById(R.id.textView49);
        textView50=(TextView)findViewById(R.id.textView50);
        textView51=(TextView)findViewById(R.id.textView51);
        textView52=(TextView)findViewById(R.id.textView52);
        textView53=(TextView)findViewById(R.id.textView53);
        textView54=(TextView)findViewById(R.id.textView54);
        textView55=(TextView)findViewById(R.id.textView55);
        textView56=(TextView)findViewById(R.id.textView56);
        textView57=(TextView)findViewById(R.id.textView57);
        textView58=(TextView)findViewById(R.id.textView58);
        textView59=(TextView)findViewById(R.id.textView59);
        textView60=(TextView)findViewById(R.id.textView60);
        textView62=(TextView)findViewById(R.id.textView62);
        textView63=(TextView)findViewById(R.id.textView63);
        textView64=(TextView)findViewById(R.id.textView64);
        textView65=(TextView)findViewById(R.id.textView65);
        textView66=(TextView)findViewById(R.id.textView66);
        textView67=(TextView)findViewById(R.id.textView67);
        textView68=(TextView)findViewById(R.id.textView68);
        textView69=(TextView)findViewById(R.id.textView69);
        textView70=(TextView)findViewById(R.id.textView70);

//        img_rot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_d_all_timing.setVisibility(View.GONE);
                clear.setVisibility(View.GONE);
                alldata.setVisibility(View.VISIBLE);
            }
        });
        txt_d_all_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityDoctorProfile.this,UpdateByDoc.class);
                intent.putExtra("view","view");
                startActivity(intent);
                /*if (linear_d_all_timing.getVisibility()== View.VISIBLE){
                    linear_d_all_timing.setVisibility(View.GONE);
                    clear.setVisibility(View.GONE);
                    alldata.setVisibility(View.VISIBLE);

                }
                else {
                    linear_d_all_timing.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);

                    alldata.setVisibility(View.GONE);

//                    scroll.fullScroll(View.FOCUS_DOWN);

                }*/
            }
        });
        /*settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
        SigninName=settings.getString("reg_name",null);
        SigninPassword=settings.getString("reg_pass",null);
        Signinid=settings.getString("reg_id",null);
        Signinphoto=settings.getString("dob",null);
        Signindevice_token=settings.getString("device_token",null);
        SigninEmail=settings.getString("reg_email",null);*/


        refresh();
        new  CallServices().execute(ApiConfig.LOGIN_URL);
           /* }
        });*/
//        String stringToBitmap=LoginActivity.dob;
//        try {
////            settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
//            String bitmap = "http://www.bjain.com/doctor/upload/" + PreferenceData.getPhoto(getApplicationContext());
//
//            Log.e("stringToBitmap", bitmap.toString());
//            getBitmapFromURL(bitmap);
//        }
//        catch (Exception e){
//
//        }

        /*try {
            Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + PreferenceData.getPhoto(getApplicationContext())).into(prof_pic);
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }*/
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivityDoctorProfile.this,UpdateByDoc.class),1);
            }
        });
//        text_hdr_name.setText(LoginActivity.reg_name);
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

        toolbarHeaderView.bindTo(PreferenceData.getName(getApplicationContext()), "");
        floatHeaderView.bindTo(PreferenceData.getName(getApplicationContext()), "");
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
    private Bitmap getBitmapFromURL(String imageUrl) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            // Bitmap myBitmap = BitmapFactory.decodeStream(input);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            Bitmap b = Utils.decodeSampledBitmapFromStream(input, width, height);
            Log.e("bitmap",b.toString());
            bg_image.setImageBitmap(b);
            return b;


        } catch (IOException e) {
            Log.e("bitmap",e.toString());
            e.printStackTrace();
            return null;
        }
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
            pd = new ProgressDialog(MainActivityDoctorProfile.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("reg_email", PreferenceData.getEmail(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("reg_pass", PreferenceData.getPass(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("device_token", PreferenceData.getDevice_Token(getApplicationContext())));

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
                    Log.d(TAG,"doc result:-"+result);
                    JSONObject jsonObject = new JSONObject(result);
                    String message=jsonObject.getString("d_message");
                    if (message.equalsIgnoreCase("invalid")) {
                        UtilsValidate.showError(MainActivityDoctorProfile.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    }
                    else {
                        reg_name = jsonObject.getString("reg_name");
                        reg_email = jsonObject.getString("reg_email");
                        reg_mob = jsonObject.getString("reg_mob");
                        reg_pass = jsonObject.getString("reg_pass");
                        reg_degree = jsonObject.getString("degree");
                        reg_designation = jsonObject.getString("designation");
                        reg_clinic_address = jsonObject.getString("clinic_address");
                        reg_id = jsonObject.getString("reg_dob");
                        reg_department = jsonObject.getString("designation");
                        dob=jsonObject.getString("photo");
                        reg_specialist=jsonObject.getString("specialist");

                        text_d_name.setText(reg_name);
                        txt_d_email_id.setText(reg_email);
                        txt_d_phone.setText(jsonObject.getString("reg_mob"));
                        text_d_address.setText(reg_clinic_address);
                        txt_d_department.setText(reg_department);
                        text_d_special.setText(reg_specialist);
                        txt_d_dob.setText(reg_id);
                        txt_d_designation.setText(reg_degree);
                        toolbarHeaderView.bindTo(reg_name, "");
                        floatHeaderView.bindTo(reg_name, "");

                        try {
                            if (dob.length() > 0) {
                                Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + dob).into(bg_image);
                            }
                            else {

                            }

                        }
                        catch (Exception e) {
                            Log.d("sahil", e.toString());
                        }
                        DateFormat readFormat = new SimpleDateFormat("hh:mm aa");
                        DateFormat writeFormat = new SimpleDateFormat("hh:mm");
                        Date date= null;
                        Date date1= null;
                        Date date2= null;
                        Date date3= null;

                        Date date4= null;
                        Date date5= null;
                        Date date6= null;
                        Date date7= null;

                        Date date8= null;
                        Date date9= null;
                        Date date10= null;
                        Date date11= null;

                        Date date12= null;
                        Date date13= null;
                        Date date14= null;
                        Date date15= null;

                        Date date16= null;
                        Date date17= null;
                        Date date18= null;
                        Date date19= null;

                        Date date20= null;
                        Date date21= null;
                        Date date22= null;
                        Date date23= null;

                        Date date24= null;
                        Date date25= null;
                        Date date26= null;
                        Date date27= null;



                        Log.e("formateddates",getDate(avl_start_time1_morn));
                        mmorngfrom.setText(getDate(avl_start_time1_morn));
                        mmorngto.setText(getDate(avl_end_time1_morn));
                        mplace.setText(morn_loca_day1);
                        mevngfrom.setText(getDate(avl_start_time1_even));
                        mevngto.setText(getDate(avl_end_time1_even));
                        tumorngfrom.setText(getDate(avl_start_time2_morn));
                        tumorngto.setText(getDate(avl_start_time2_morn));
                        tuplace.setText(morn_loca_day2);
                        tuevngfrom.setText(getDate(avl_start_time2_even));
                        tuevngto.setText(getDate(avl_end_time2_even));
                        wmorngfrom.setText(getDate(avl_start_time3_morn));
                        wmorngto.setText(getDate(avl_end_time3_morn));
                        wplace.setText(morn_loca_day3);
                        wevngfrom.setText(getDate(avl_start_time3_even));
                        wevngto.setText(getDate(avl_end_time3_even));
                        thmorngfrom.setText(getDate(avl_start_time4_morn));
                        thmorngto.setText(getDate(avl_end_time4_morn));
                        thplace.setText(morn_loca_day4);
                        thevngfrom.setText(getDate(avl_start_time4_even));
                        thevngto.setText(getDate(avl_end_time4_even));
                        fmorngfrom.setText(getDate(avl_start_time5_morn));
                        fmorngto.setText(getDate(avl_end_time5_morn));
                        fplace.setText(morn_loca_day5);
                        fevngfrom.setText(getDate(avl_start_time5_even));
                        fevngto.setText(getDate(avl_end_time5_even));
                        samorngfrom.setText(getDate(avl_start_time6_morn));
                        samorngto.setText(getDate(avl_end_time6_morn));
                        saplace.setText(morn_loca_day6);
                        saevngfrom.setText(getDate(avl_start_time6_even));
                        saevngto.setText(getDate(avl_end_time6_even));
                        sumorngfrom.setText(getDate(avl_start_time7_morn));
                        sumorngto.setText(getDate(avl_end_time7_morn));
                        suplace.setText(morn_loca_day7);
                        suevngfrom.setText(getDate(avl_start_time7_even));
                        suevngto.setText(getDate(avl_end_time7_even));

                        PreferenceData.setName(getApplicationContext(),reg_name);
                        PreferenceData.setPhoto(getApplicationContext(),dob);
                        DoctorAccount.textDoc.setText(PreferenceData.getName(getApplicationContext()));



                        try {
                            String bitmap = "http://www.bjain.com/doctor/upload/" + PreferenceData.getPhoto(getApplicationContext());

                            Log.e("stringToBitmap", bitmap.toString());
                            Picasso.with(getApplicationContext()).load(bitmap).resize(100,100).into(DoctorAccount.profilepic);
                        }
                        catch (Exception e){

                        }

                    }
                    Log.e("result",result.toString());



                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }

    }
    private final String TAG=getClass().getSimpleName();
    public String getDate(String text){
        try {
            DateFormat readFormat = new SimpleDateFormat("hh:mm aa");
            DateFormat writeFormat = new SimpleDateFormat("hh:mm");
            Date date = readFormat.parse(text);;
            return writeFormat.format(date);
        }
        catch (Exception e){
            return "";
        }
    }
    public void refresh(){
        new CallServices().execute(ApiConfig.LOGIN_URL);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == MainActivityDoctorProfile.this.RESULT_OK){
                String result=data.getStringExtra("result1");
                Log.e("onActivityResultresult",result);
                refresh();
            }
            if (resultCode == MainActivityDoctorProfile.this.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result1","okay");
            setResult(MainActivityDoctorProfile.this.RESULT_OK,returnIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result1","okay");
        setResult(MainActivityDoctorProfile.this.RESULT_OK,returnIntent);
        finish();
    }


}
