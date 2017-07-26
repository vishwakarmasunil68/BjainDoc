/*
package com.emobi.bjaindoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UserModeFragment extends AppCompatActivity {

    private View mViewPatient, mViewBranchAdmin, mViewTherapist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mode);
        mViewPatient = (View) findViewById(R.id.layout_patient);
        mViewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserModeFragment.this,SignUpactivity.class));
                */
/*SignUpDetailFragment fragment = SignUpDetailFragment.newInstance(Constants.GlobalConst.USER_PATIENT);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();*//*

            }
        });
    }


        */
/*

        mViewBranchAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpDetailFragment fragment = SignUpDetailFragment.newInstance(Constants.GlobalConst.USER_BRANCH_ADMIN);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        mViewTherapist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpDetailFragment fragment = SignUpDetailFragment.newInstance(Constants.GlobalConst.USER_THERAPIST);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });*//*


    }*/
