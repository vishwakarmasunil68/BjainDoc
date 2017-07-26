package com.emobi.bjaindoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Emobi-Android-002 on 8/13/2016.
 */
public class SIgnInOption extends AppCompatActivity {
    TextView patient,doctor;
    RelativeLayout layout_patient,layout_doc;
    ActionBar actionBar;
    ImageView img_therapist,img_patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mode);

        actionBar=getSupportActionBar();
        SpannableString s = new SpannableString("  Sign In/Register");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(R.drawable.bjainicon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("  Sign In/Register");
        actionBar.setTitle(s);
        img_therapist=(ImageView)findViewById(R.id.img_therapist);
        img_patient=(ImageView)findViewById(R.id.img_patient);
        /*layout_doc=(RelativeLayout)findViewById(R.id.layout_doc);
        img_patient=(RelativeLayout)findViewById(R.id.layout_patient);
        Typeface tf= Typeface.createFromAsset(this.getAssets(),"fonts/Roboto-Regular.ttf");
        doctor.setTypeface(tf);*/
        img_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SIgnInOption.this, SignWithPatient.class));
//                finish();
            }
        });
        img_therapist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SIgnInOption.this, LoginActivity.class));
//                finish();
            }
        });
    }


}
