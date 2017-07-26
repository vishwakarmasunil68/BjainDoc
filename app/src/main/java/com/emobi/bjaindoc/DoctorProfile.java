package com.emobi.bjaindoc;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Emobi-Android-002 on 8/13/2016.
 */
public class DoctorProfile extends AppCompatActivity {
    Toolbar toolbar;
    TextView txt_name,txt_email,txt_phone,toolbar_title,txt_dob,txt_designation,txt_specialist,txt_degree,txt_department,txt_address;
    CircleImageView img;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorprofile);

        toolbar= (Toolbar) findViewById(R.id.toolbar);

        txt_name= (TextView) findViewById(R.id.txt_name);
        txt_email= (TextView) findViewById(R.id.txt_email);
        txt_phone= (TextView) findViewById(R.id.txt_phone);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        txt_dob= (TextView) findViewById(R.id.txt_dob);
        txt_designation= (TextView) findViewById(R.id.txt_designation);
        txt_specialist= (TextView) findViewById(R.id.txt_specialist);
        txt_address= (TextView) findViewById(R.id.txt_address);
        txt_department= (TextView) findViewById(R.id.txt_department);
        txt_degree= (TextView) findViewById(R.id.txt_degree);
        txt_address= (TextView) findViewById(R.id.txt_address);

        img= (CircleImageView) findViewById(R.id.img_profile);
        backarrow = (ImageView) findViewById(R.id.backarrow);
        /*SpannableString s = new SpannableString("  Profile");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.bjainicon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
*/
        SharedPreferences sp=getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);


        txt_name.setText(PreferenceData.getDoctorName(getApplicationContext()));
        txt_email.setText(PreferenceData.getDoctorEmail(getApplicationContext()));
        txt_phone.setText(PreferenceData.getDoctorNumber(getApplicationContext()));
        txt_dob.setText(PreferenceData.getDoctorDob(getApplicationContext()));
        txt_designation.setText(PreferenceData.getDoctorDesignation(getApplicationContext()));
        txt_specialist.setText(PreferenceData.getSpecialist(getApplicationContext()));
        txt_degree.setText(PreferenceData.getDoctorDegree(getApplicationContext()));
        txt_department.setText(PreferenceData.getDoctorDepartment(getApplicationContext()));
        txt_address.setText(PreferenceData.getDoctorclinic_address(getApplicationContext()));

        Typeface tf= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
        txt_name.setTypeface(tf);
        txt_email.setTypeface(tf);
        txt_phone.setTypeface(tf);
        txt_dob.setTypeface(tf);
        txt_designation.setTypeface(tf);
        txt_specialist.setTypeface(tf);
        txt_degree.setTypeface(tf);
        txt_department.setTypeface(tf);
        txt_address.setTypeface(tf);
        toolbar_title.setTypeface(tf);



        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String photo_url= PreferenceData.getDoctorPhotoUrl(getApplicationContext());
        if(photo_url.equals("")){

        }
        else{
            Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/"+photo_url).into(img);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
