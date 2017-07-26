package com.emobi.bjaindoc.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anton46.whatsapp_profile.HeaderView;
import com.emobi.bjaindoc.Medication;
import com.emobi.bjaindoc.Prescription;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.pojo.member.MemberResultPOJO;
import com.emobi.bjaindoc.pojo.patient.PatientResultPOJO;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.offset;


public class MemberAccountActivity extends AppCompatActivity implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener{
    @BindView(R.id.toolbar_header_view)
    protected HeaderView toolbarHeaderView;

    @BindView(R.id.float_header_view)
    protected HeaderView floatHeaderView;

    @BindView(R.id.appbar)
    protected AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rl_interaction)
    RelativeLayout rl_interaction;
    @BindView(R.id.rl_medication)
    RelativeLayout rl_medication;
    @BindView(R.id.rl_prescription)
    RelativeLayout rl_prescription;

    private boolean isHideToolbarView = false;

    @BindView(R.id.iv_profile_pic)
    ImageView iv_profile_pic;

    PatientResultPOJO patientResultPOJO;
    MemberResultPOJO memberResultPOJO;
    //    TextView toolbar_title,textDoc;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_account);

        ButterKnife.bind(this);

        memberResultPOJO = (MemberResultPOJO) getIntent().getSerializableExtra("member");
        patientResultPOJO=(PatientResultPOJO) getIntent().getSerializableExtra("patient");
        if (memberResultPOJO != null&&patientResultPOJO!=null) {
            Picasso.with(getApplicationContext())
                    .load("http://www.bjain.com/doctor/" + memberResultPOJO.getM_photo())
                    .error(R.drawable.ic_action_person)
                    .into(iv_profile_pic);
        } else {
            finish();
        }

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        initUi();


        rl_medication.setOnClickListener(this);
        rl_interaction.setOnClickListener(this);
        rl_prescription.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.rl_interaction:
                startchatActivity();
                break;
            case R.id.rl_medication:
                startMedicationActivity();
                break;
            case R.id.rl_prescription:
                startPrescriptionActivity();
                break;
        }
    }
    public void startMedicationActivity(){
        Intent intent=new Intent(MemberAccountActivity.this,Medication.class);
        intent.putExtra("patient",patientResultPOJO);
        intent.putExtra("member",memberResultPOJO);
        startActivity(intent);
    }

    public void startPrescriptionActivity(){
        Intent intent=new Intent(MemberAccountActivity.this,Prescription.class);
        intent.putExtra("patient",patientResultPOJO);
        intent.putExtra("member",memberResultPOJO);
        startActivity(intent);
    }

    public void startchatActivity(){
        Intent intent=new Intent(MemberAccountActivity.this,ChatActivityPat.class);
        intent.putExtra("patient",patientResultPOJO);
        intent.putExtra("member",memberResultPOJO);
        startActivity(intent);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                if (result.equals("true")) {
//                    getAllMembers();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUi() {
        appBarLayout.addOnOffsetChangedListener(this);

        toolbarHeaderView.bindTo(memberResultPOJO.getM_name(), "");
        floatHeaderView.bindTo(memberResultPOJO.getM_name(), "");
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
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
