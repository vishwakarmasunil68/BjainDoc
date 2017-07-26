package com.emobi.bjaindoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Emobi-Android-002 on 8/11/2016.
 */
public class DoctorDetailpro extends AppCompatActivity {
    TextView name,email,phone;
    ImageView imageView_edit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_doctor);
        imageView_edit=(ImageView)findViewById(R.id.img_edit);
        name=(TextView)findViewById(R.id.txt_name);
        email=(TextView)findViewById(R.id.txt_email);
        phone=(TextView)findViewById(R.id.txt_phone);
        name.setText(LoginActivity.reg_name);
        email.setText(LoginActivity.reg_email);
        phone.setText(LoginActivity.dob);
        imageView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailpro.this,DoctorEditPro.class));

            }
        });
    }
}
