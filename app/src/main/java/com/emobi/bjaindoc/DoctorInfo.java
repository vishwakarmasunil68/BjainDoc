package com.emobi.bjaindoc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anton46.whatsapp_profile.HeaderView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import patient_side.UsersAdapter_Serach_Doc;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class DoctorInfo extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.toolbar_header_view)
    protected HeaderView toolbarHeaderView;

    @BindView(R.id.float_header_view)
    protected HeaderView floatHeaderView;

    @BindView(R.id.appbar)
    protected AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    TextView text_hdr_name,d_name,d_email_id,d_address;
    public static String reg_name, reg_email, reg_pass, reg_cpass, dob, reg_department, reg_clinic_address, reg_designation, reg_specialist, reg_degree;
    ImageView edit;
    private boolean isHideToolbarView = false;
    TextView txt_d_phone,last_seen;
    TextView text_d_name,txt_d_email_id,text_d_address,txt_d_department,text_d_special,txt_d_dob
            ,textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,
            textView9,textView10,textView11,textView12,textView13,textView14,textView15,textView16,
    textView17,textView18,textView19,textView20,textView21,textView22,textView23,
    textView24,textView25, textView26,textView27, textView28, textView29,textView30,textView31, textView32,textView33,textView34,textView35,textView36,textView37,textView38,textView39,textView40,textView41,textView42
            ,textView43,textView44,textView45,textView46,textView47,textView48,textView49,textView50,textView51,textView52,textView53,textView54,textView55,textView56,textView57,textView58,textView59,textView60
            ,textView61,textView62,textView63,textView64,textView65,textView66,textView67,textView68,textView69,textView70;

    TextView d_avail,txt_d_all_timing;
    LinearLayout linear_d_all_timing;
    public static String reg_id;
    ActionBar actionBar;
    int formattedDate;
Button btn_booking;
    CircleImageView prof_pic;
    ImageView img_rot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_doctor_profile);

        SpannableString s = new SpannableString("");
        s.setSpan(new TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar=getSupportActionBar();

        /*actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Find Doctor");
        actionBar.setTitle(s);*/

        Calendar c = Calendar.getInstance();
        formattedDate =(c.get(Calendar.DAY_OF_WEEK));

        linear_d_all_timing=(LinearLayout)findViewById(R.id.linear_all_timing);
        txt_d_all_timing=(TextView) findViewById(R.id.txt_all_timing);

        prof_pic=(CircleImageView)findViewById(R.id.image);

//        Log.e("formattedDate",String.valueOf(formattedDate));

//        text_d_name=(TextView)findViewById(R.id.text_d_name);
        txt_d_email_id=(TextView)findViewById(R.id.d_email_id);
        txt_d_phone=(TextView)findViewById(R.id.txt_d_phone);
        text_d_address=(TextView)findViewById(R.id.d_address);
        txt_d_department=(TextView)findViewById(R.id.d_department);
        text_d_special=(TextView)findViewById(R.id.d_special);
        txt_d_dob=(TextView)findViewById(R.id.d_designation);
        text_hdr_name=(TextView)findViewById(R.id.tv_profile_name);
        last_seen=(TextView)findViewById(R.id.last_seen);
        edit=(ImageView)findViewById(R.id.img_edit);
        img_rot=(ImageView)findViewById(R.id.img_rot);
        d_avail=(TextView)findViewById(R.id.d_avail);
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

        if (formattedDate==1) {
        d_avail.setText("Today - "+ UsersAdapter_Serach_Doc.avl_start_time7_morn + "-" + UsersAdapter_Serach_Doc.avl_start_time7_even);

        }
        else if (formattedDate==2){
            d_avail.setText("Today - "+ UsersAdapter_Serach_Doc.avl_start_time1_morn + "-" + UsersAdapter_Serach_Doc.avl_start_time1_even);
        }

        else if (formattedDate==3){
            d_avail.setText("Today - "+ UsersAdapter_Serach_Doc.avl_start_time2_morn + "-" + UsersAdapter_Serach_Doc.avl_start_time2_even);
        }

        else if (formattedDate==4){
            d_avail.setText("Today - "+ UsersAdapter_Serach_Doc.avl_start_time3_morn + "-" + UsersAdapter_Serach_Doc.avl_start_time3_even);
        }

        else if (formattedDate==5){
            d_avail.setText("Today - "+ UsersAdapter_Serach_Doc.avl_start_time4_morn + "-" + UsersAdapter_Serach_Doc.avl_start_time4_even);
        }

        else if (formattedDate==6){
            d_avail.setText("Today - "+ UsersAdapter_Serach_Doc.avl_start_time5_morn + "-" + UsersAdapter_Serach_Doc.avl_start_time5_even);
        }

        else if (formattedDate==7){
            d_avail.setText("Today - "+ UsersAdapter_Serach_Doc.avl_start_time6_morn + "-" + UsersAdapter_Serach_Doc.avl_start_time6_even);
        }

        edit.setVisibility(View.GONE);

        btn_booking= (Button) findViewById(R.id.btn_booking);

        try {
            String bitmap = "http://www.bjain.com/doctor/upload/" + UsersAdapter_Serach_Doc.photo;

            Log.e("stringToBitmap", bitmap.toString());
            Picasso.with(getApplicationContext()).load(bitmap).into(prof_pic);
        }
        catch (Exception e){
            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.background);
            prof_pic.setImageBitmap(largeIcon);
            Log.e("stringToBitmap", largeIcon.toString());
        }
        /*try {
            String bitmap = "http://www.bjain.com/doctor/upload/" + UsersAdapter_Serach_Doc.photo;

            Log.e("stringToBitmap", bitmap.toString());
            getBitmapFromURL(bitmap);
        }
        catch (Exception e){
            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.background);
            prof_pic.setImageBitmap(largeIcon);
            Log.e("stringToBitmap", largeIcon.toString());
        }
*/
        txt_d_all_timing.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        if (linear_d_all_timing.getVisibility()== View.VISIBLE){
            linear_d_all_timing.setVisibility(View.GONE);
        }
        else {
            linear_d_all_timing.setVisibility(View.VISIBLE);
            }
        }
    });


        textView2.setText(UsersAdapter_Serach_Doc.avl_start_time1_morn);
        textView3.setText(UsersAdapter_Serach_Doc.avl_end_time1_morn);
        textView4.setText(UsersAdapter_Serach_Doc.morn_loca_day1);
        textView5.setText(UsersAdapter_Serach_Doc.avl_start_time1_afternoon);
        textView6.setText(UsersAdapter_Serach_Doc.avl_end_time1_afternoon);
        textView7.setText(UsersAdapter_Serach_Doc.afternoon_loca_day1);
        textView8.setText(UsersAdapter_Serach_Doc.avl_start_time1_even);
        textView9.setText(UsersAdapter_Serach_Doc.avl_end_time1_even);
        textView10.setText(UsersAdapter_Serach_Doc.even_loca_day1);
        textView12.setText(UsersAdapter_Serach_Doc.avl_start_time2_morn);
        textView13.setText(UsersAdapter_Serach_Doc.avl_end_time2_morn);
        textView14.setText(UsersAdapter_Serach_Doc.morn_loca_day2);
        textView15.setText(UsersAdapter_Serach_Doc.avl_start_time2_afternoon);
        textView16.setText(UsersAdapter_Serach_Doc.avl_end_time2_afternoon);
        textView17.setText(UsersAdapter_Serach_Doc.afternoon_loca_day2);
        textView18.setText(UsersAdapter_Serach_Doc.avl_start_time2_even);
        textView19.setText(UsersAdapter_Serach_Doc.avl_end_time2_even);
        textView20.setText(UsersAdapter_Serach_Doc.even_loca_day2);
        textView22.setText(UsersAdapter_Serach_Doc.avl_start_time3_morn);
        textView23.setText(UsersAdapter_Serach_Doc.avl_end_time3_morn);
        textView24.setText(UsersAdapter_Serach_Doc.morn_loca_day3);
        textView25.setText(UsersAdapter_Serach_Doc.avl_start_time3_afternoon);
        textView26.setText(UsersAdapter_Serach_Doc.avl_end_time3_afternoon);
        textView27.setText(UsersAdapter_Serach_Doc.afternoon_loca_day1);
        textView28.setText(UsersAdapter_Serach_Doc.avl_start_time3_even);
        textView29.setText(UsersAdapter_Serach_Doc.avl_end_time3_even);
        textView30.setText(UsersAdapter_Serach_Doc.even_loca_day3);
        textView32.setText(UsersAdapter_Serach_Doc.avl_start_time4_morn);
        textView33.setText(UsersAdapter_Serach_Doc.avl_end_time4_morn);
        textView34.setText(UsersAdapter_Serach_Doc.morn_loca_day4);
        textView35.setText(UsersAdapter_Serach_Doc.avl_start_time4_afternoon);
        textView36.setText(UsersAdapter_Serach_Doc.avl_end_time4_afternoon);
        textView37.setText(UsersAdapter_Serach_Doc.aafternoon_loca_day4);
        textView38.setText(UsersAdapter_Serach_Doc.avl_start_time4_even);
        textView39.setText(UsersAdapter_Serach_Doc.avl_end_time4_even);
        textView40.setText(UsersAdapter_Serach_Doc.even_loca_day4);
        textView42.setText(UsersAdapter_Serach_Doc.avl_start_time5_morn);
        textView43.setText(UsersAdapter_Serach_Doc.avl_end_time5_morn);
        textView44.setText(UsersAdapter_Serach_Doc.morn_loca_day5);
        textView45.setText(UsersAdapter_Serach_Doc.avl_start_time5_afternoon);
        textView46.setText(UsersAdapter_Serach_Doc.avl_end_time5_afternoon);
        textView47.setText(UsersAdapter_Serach_Doc.aafternoon_loca_day5);
        textView48.setText(UsersAdapter_Serach_Doc.avl_start_time5_even);
        textView49.setText(UsersAdapter_Serach_Doc.avl_end_time5_even);
        textView50.setText(UsersAdapter_Serach_Doc.even_loca_day5);
        textView52.setText(UsersAdapter_Serach_Doc.avl_start_time6_morn);
        textView53.setText(UsersAdapter_Serach_Doc.avl_end_time6_morn);
        textView54.setText(UsersAdapter_Serach_Doc.morn_loca_day6);
        textView55.setText(UsersAdapter_Serach_Doc.avl_start_time6_afternoon);
        textView56.setText(UsersAdapter_Serach_Doc.avl_end_time6_afternoon);
        textView57.setText(UsersAdapter_Serach_Doc.aafternoon_loca_day6);
        textView58.setText(UsersAdapter_Serach_Doc.avl_start_time6_even);
        textView59.setText(UsersAdapter_Serach_Doc.avl_end_time6_even);
        textView60.setText(UsersAdapter_Serach_Doc.even_loca_day6);
        textView62.setText(UsersAdapter_Serach_Doc.avl_start_time7_morn);
        textView63.setText(UsersAdapter_Serach_Doc.avl_end_time7_morn);
        textView64.setText(UsersAdapter_Serach_Doc.morn_loca_day7);
        textView65.setText(UsersAdapter_Serach_Doc.avl_start_time7_afternoon);
        textView66.setText(UsersAdapter_Serach_Doc.avl_end_time7_afternoon);
        textView67.setText(UsersAdapter_Serach_Doc.aafternoon_loca_day7);
        textView68.setText(UsersAdapter_Serach_Doc.avl_start_time7_even);
        textView69.setText(UsersAdapter_Serach_Doc.avl_end_time7_even);
        textView70.setText(UsersAdapter_Serach_Doc.even_loca_day7);


//        text_d_name.setText(UsersAdapter_Serach_Doc.name);
        txt_d_email_id.setText(UsersAdapter_Serach_Doc.email);
        txt_d_phone.setText(UsersAdapter_Serach_Doc.reg_mob);
        text_d_address.setText(UsersAdapter_Serach_Doc.address);
        txt_d_department.setText(UsersAdapter_Serach_Doc.department);
        text_d_special.setText(UsersAdapter_Serach_Doc.speciality);
        txt_d_dob.setText(UsersAdapter_Serach_Doc.design);



        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(new Intent(DoctorInfo.this,SignWithnormlPatient.class));
                intent.putExtra("message", "visible");
                startActivity(intent);
            }
        });

        ButterKnife.bind(this);

        Typeface tf= Typeface.createFromAsset(this.getAssets(),"fonts/Roboto-Regular.ttf");
//        text_d_name.setTypeface(tf);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(s);
        initUi();

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



    private void initUi() {
        appBarLayout.addOnOffsetChangedListener(this);

        toolbarHeaderView.bindTo(UsersAdapter_Serach_Doc.name, UsersAdapter_Serach_Doc.email);
        floatHeaderView.bindTo(UsersAdapter_Serach_Doc.name, UsersAdapter_Serach_Doc.email);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Log.e("clik", "action bar clicked");
            finish();
        }

        return super.onOptionsItemSelected(item);
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


}
