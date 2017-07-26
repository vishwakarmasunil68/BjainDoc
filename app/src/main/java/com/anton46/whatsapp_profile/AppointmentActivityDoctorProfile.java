package com.anton46.whatsapp_profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.SignWithnormlPatient;
import com.emobi.bjaindoc.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import patient_side.UsersAdapter_Serach_Doc;

public class AppointmentActivityDoctorProfile extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.toolbar_header_view)
    protected HeaderView toolbarHeaderView;

    @BindView(R.id.float_header_view)
    protected HeaderView floatHeaderView;

    @BindView(R.id.appbar)
    protected AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    TextView text_hdr_name,d_name,d_email_id,d_address;
    public static String reg_id, reg_name, reg_email, reg_pass, reg_cpass, dob, reg_department, reg_clinic_address, reg_designation, reg_specialist, reg_degree;
    ImageView img_rot;
    CircleImageView prof_pic;
    private boolean isHideToolbarView = false;
    TextView last_seen;
    TextView text_d_name,txt_d_email_id,text_d_address,txt_d_department,text_d_special,txt_d_dob;
    public static String avl_start_time1_morm,avl_emd_time1_morm,avl_start_time1_aftermoom,
            avl_emd_time1_aftermoom,avl_start_time1_evem,avl_emd_time1_evem,

    avl_start_time2_morm,avl_emd_time2_morm,avl_start_time2_aftermoom,avl_emd_time2_aftermoom
            ,avl_start_time2_evem,avl_emd_time2_evem,

    avl_start_time3_morm,avl_emd_time3_morm,avl_start_time3_aftermoom,avl_emd_time3_aftermoom
            ,avl_start_time3_evem,avl_emd_time3_evem,

    avl_start_time4_morm,avl_emd_time4_morm,avl_start_time4_aftermoom,avl_emd_time4_aftermoom
            ,avl_start_time4_evem,avl_emd_time4_evem,

    avl_start_time5_morm,avl_emd_time5_morm,avl_start_time5_aftermoom,avl_emd_time5_aftermoom
            ,avl_start_time5_evem,avl_emd_time5_evem,

    avl_start_time6_morm,avl_emd_time6_morm,avl_start_time6_aftermoom,avl_emd_time6_aftermoom
            ,avl_start_time6_evem,avl_emd_time6_evem,

    avl_start_time7_morm,avl_emd_time7_morm,avl_start_time7_aftermoom,avl_emd_time7_aftermoom
            ,avl_start_time7_evem,avl_emd_time7_evem;


    Button btn_booking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_doctor_profile);

//        text_d_name=(TextView)findViewById(R.id.text_d_name);
        txt_d_email_id=(TextView)findViewById(R.id.d_email_id);
        text_d_address=(TextView)findViewById(R.id.d_address);
        txt_d_department=(TextView)findViewById(R.id.d_department);
        text_d_special=(TextView)findViewById(R.id.d_special);
        txt_d_dob=(TextView)findViewById(R.id.d_designation);
        text_hdr_name=(TextView)findViewById(R.id.tv_profile_name);
        last_seen=(TextView)findViewById(R.id.last_seen);
        prof_pic=(CircleImageView)findViewById(R.id.image);
        img_rot=(ImageView)findViewById(R.id.img_rot);
        btn_booking= (Button) findViewById(R.id.btn_booking);

        try {
            String bitmap = "http://www.bjain.com/doctor/upload/" + UsersAdapter_Serach_Doc.photo;

            Log.e("stringToBitmap", bitmap.toString());
            getBitmapFromURL(bitmap);
        }
        catch (Exception e){
            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.background);
            prof_pic.setImageBitmap(largeIcon);
            Log.e("stringToBitmap", largeIcon.toString());
        }

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"good", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AppointmentActivityDoctorProfile.this, SignWithnormlPatient.class));
            }
        });
//        text_d_name.setText(UsersAdapter_Serach_Doc.name);
        txt_d_email_id.setText(UsersAdapter_Serach_Doc.email);
        text_d_address.setText(UsersAdapter_Serach_Doc.address);
        txt_d_department.setText(UsersAdapter_Serach_Doc.department);
        text_d_special.setText(UsersAdapter_Serach_Doc.speciality);
        txt_d_dob.setText(UsersAdapter_Serach_Doc.design);

        Typeface tf1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
        text_d_name.setTypeface(tf1);
//        txt_d_email_id.setTypeface(tf1);
        text_d_address.setTypeface(tf1);
        txt_d_department.setTypeface(tf1);
        text_d_special.setTypeface(tf1);
        txt_d_dob.setTypeface(tf1);

        avl_start_time1_morm=UsersAdapter_Serach_Doc.avl_start_time1_morn;
        avl_emd_time1_morm=UsersAdapter_Serach_Doc.avl_end_time1_morn;
        avl_start_time1_aftermoom=UsersAdapter_Serach_Doc.avl_start_time1_afternoon;
        avl_emd_time1_aftermoom=UsersAdapter_Serach_Doc.avl_end_time1_afternoon;
        avl_start_time1_evem=UsersAdapter_Serach_Doc.avl_start_time1_even;
        avl_emd_time1_evem=UsersAdapter_Serach_Doc.avl_end_time1_even;

        avl_start_time2_morm=UsersAdapter_Serach_Doc.avl_start_time2_morn;
        avl_emd_time2_morm=UsersAdapter_Serach_Doc.avl_end_time2_morn;
        avl_start_time2_aftermoom=UsersAdapter_Serach_Doc.avl_start_time2_afternoon;
        avl_emd_time2_aftermoom=UsersAdapter_Serach_Doc.avl_end_time2_afternoon;
        avl_start_time2_evem=UsersAdapter_Serach_Doc.avl_start_time2_even;
        avl_emd_time2_evem=UsersAdapter_Serach_Doc.avl_end_time2_even;

        avl_start_time3_morm=UsersAdapter_Serach_Doc.avl_start_time3_morn;
        avl_emd_time3_evem=UsersAdapter_Serach_Doc.avl_end_time3_morn;
        avl_start_time3_aftermoom=UsersAdapter_Serach_Doc.avl_start_time3_afternoon;
        avl_emd_time3_aftermoom=UsersAdapter_Serach_Doc.avl_end_time3_afternoon;
        avl_start_time3_evem=UsersAdapter_Serach_Doc.avl_start_time3_even;
        avl_emd_time3_evem=UsersAdapter_Serach_Doc.avl_end_time3_even;

        avl_start_time4_morm=UsersAdapter_Serach_Doc.avl_start_time4_morn;
        avl_emd_time4_morm=UsersAdapter_Serach_Doc.avl_end_time4_morn;
        avl_start_time4_aftermoom=UsersAdapter_Serach_Doc.avl_start_time4_afternoon;
        avl_emd_time4_aftermoom=UsersAdapter_Serach_Doc.avl_end_time4_afternoon;
        avl_start_time4_evem=UsersAdapter_Serach_Doc.avl_start_time4_even;
        avl_emd_time4_evem=UsersAdapter_Serach_Doc.avl_end_time4_even;

        avl_start_time5_morm=UsersAdapter_Serach_Doc.avl_start_time5_morn;
        avl_emd_time5_morm=UsersAdapter_Serach_Doc.avl_end_time5_morn;
        avl_start_time5_aftermoom=UsersAdapter_Serach_Doc.avl_start_time5_afternoon;
        avl_emd_time5_aftermoom=UsersAdapter_Serach_Doc.avl_end_time5_afternoon;
        avl_start_time5_evem=UsersAdapter_Serach_Doc.avl_start_time5_even;
        avl_emd_time5_evem=UsersAdapter_Serach_Doc.avl_end_time5_even;

        avl_start_time6_morm=UsersAdapter_Serach_Doc.avl_start_time6_morn;
        avl_emd_time6_morm=UsersAdapter_Serach_Doc.avl_end_time6_morn;
        avl_start_time6_aftermoom=UsersAdapter_Serach_Doc.avl_start_time6_afternoon;
        avl_emd_time6_aftermoom=UsersAdapter_Serach_Doc.avl_end_time6_afternoon;
        avl_start_time6_evem=UsersAdapter_Serach_Doc.avl_start_time6_even;
        avl_emd_time6_evem=UsersAdapter_Serach_Doc.avl_end_time6_even;

        avl_start_time7_morm=UsersAdapter_Serach_Doc.avl_start_time7_morn;
        avl_emd_time7_morm=UsersAdapter_Serach_Doc.avl_end_time7_morn;
        avl_start_time7_aftermoom=UsersAdapter_Serach_Doc.avl_start_time7_afternoon;
        avl_emd_time7_aftermoom=UsersAdapter_Serach_Doc.avl_end_time7_afternoon;
        avl_start_time7_evem=UsersAdapter_Serach_Doc.avl_start_time7_even;
        avl_emd_time7_evem=UsersAdapter_Serach_Doc.avl_end_time7_even;


//        text_d_name.setText(UsersAdapter_Serach_Doc.name);

        /*String stringToBitmap=LoginActivity.dob;
        String bitmap="http://www.bjain.com/doctor/upload/"+ LoginActivity.dob;
        Log.e("stringToBitmap",bitmap.toString());
//        getBitmapFromURL(bitmap);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppointmentActivityDoctorProfile.this,UpdateDocInfo.class));
                finish();
            }
        });*/
        text_hdr_name.setText(UsersAdapter_Serach_Doc.name);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUi();
    }

    private void initUi() {
        appBarLayout.addOnOffsetChangedListener(this);

        toolbarHeaderView.bindTo(UsersAdapter_Serach_Doc.name, "");
        floatHeaderView.bindTo(UsersAdapter_Serach_Doc.name, "");
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
            prof_pic.setImageBitmap(b);
            return b;


        } catch (IOException e) {
            Log.e("bitmap",e.toString());
            e.printStackTrace();
            return null;
        }
    }
}
