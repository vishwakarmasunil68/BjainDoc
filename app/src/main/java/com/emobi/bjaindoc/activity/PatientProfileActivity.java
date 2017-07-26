package com.emobi.bjaindoc.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anton46.whatsapp_profile.HeaderView;
import com.bumptech.glide.Glide;
import com.emobi.bjaindoc.ApiConfig;
import com.emobi.bjaindoc.Medication;
import com.emobi.bjaindoc.Prescription;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.pojo.chat.ChatResultPOJO;
import com.emobi.bjaindoc.pojo.member.GetAllMembers;
import com.emobi.bjaindoc.pojo.member.MemberResultPOJO;
import com.emobi.bjaindoc.pojo.patient.PatientResultPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.emobi.bjaindoc.utls.Pref;
import com.emobi.bjaindoc.utls.StringUtils;
import com.emobi.bjaindoc.utls.ToastClass;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PatientProfileActivity extends AppCompatActivity implements WebServicesCallBack,AppBarLayout.OnOffsetChangedListener{
    private final static String GET_ALL_MEMBERS_API="get_all_members_api";
    private final String TAG=getClass().getSimpleName();

    private static final String SHOW_MEMBER="show_member";
    private static final String HIDE_MEMBER="hide_member";

    private String MEMBER_VISIBILITY=HIDE_MEMBER;

    @BindView(R.id.toolbar_header_view)
    protected HeaderView toolbarHeaderView;

    @BindView(R.id.float_header_view)
    protected HeaderView floatHeaderView;

    @BindView(R.id.appbar)
    protected AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    private boolean isHideToolbarView = false;

    TextView tv_profile_name;

    @BindView(R.id.iv_profile_image)
    ImageView iv_profile_image;
    @BindView(R.id.rl_interaction)
    RelativeLayout rl_interaction;
    @BindView(R.id.rl_appointments)
    RelativeLayout rl_medication;
    @BindView(R.id.rl_prescription)
    RelativeLayout rl_prescription;
    @BindView(R.id.rl_notes)
    RelativeLayout rl_notes;
    @BindView(R.id.rl_view_members)
    RelativeLayout rl_view_members;
    @BindView(R.id.iv_show_members)
    ImageView iv_show_members;
    @BindView(R.id.ll_view_members)
    protected LinearLayout ll_view_members;


    String message;
    TextView toolbar_title,textDoc;
    PatientResultPOJO patientResultPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        ButterKnife.bind(this);
        tv_profile_name= (TextView) findViewById(R.id.tv_profile_name);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        textDoc = (TextView) findViewById(R.id.textDoc);
//        tv_profile_name112= (TextView) findViewById(R.id.tv_profile_name112);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        patientResultPOJO= (PatientResultPOJO) getIntent().getSerializableExtra("patient");
        if(patientResultPOJO!=null){
//            tv_profile_name112.setText(patientResultPOJO.getP_name());
            message=patientResultPOJO.getP_name();
            initUi();

            textDoc.setText(message);
            toolbar_title.setText(message);
//            textDoc.setTypeface(tf);

            tv_profile_name.setText(patientResultPOJO.getP_name());
            String image_url="";
            if(patientResultPOJO.getP_photo().contains("upload/"))
            {
                image_url="http://www.bjain.com/doctor/"+patientResultPOJO.getP_photo();
            }
            else{
                image_url="http://www.bjain.com/doctor/upload/"+patientResultPOJO.getP_photo();
            }
            Glide.with(getApplicationContext())
                    .load(image_url)
                    .into(iv_profile_image);

            ChatResultPOJO chatResultPOJO= (ChatResultPOJO) getIntent().getSerializableExtra("chat");
            if(chatResultPOJO!=null){
                Intent intent=new Intent(PatientProfileActivity.this, ChatActivityPat.class);
                intent.putExtra("patient",patientResultPOJO);
                intent.putExtra("chat",chatResultPOJO);
                startActivity(intent);
            }
        }
        else{
            finish();
        }
        View view=findViewById(R.id.float_header_view);
        TextView tv_profile_name= (TextView) view.findViewById(R.id.tv_profile_name);
        tv_profile_name.setText(patientResultPOJO.getP_name());
        rl_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PatientProfileActivity.this,Medication.class);
                intent.putExtra("patient",patientResultPOJO);
                startActivity(intent);

            }
        });
        rl_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PatientProfileActivity.this, Prescription.class);
                intent.putExtra("patient",patientResultPOJO);
                startActivity(intent);
            }
        });

        iv_show_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Pref.GetStringPref(getApplicationContext(),StringUtils.ALL_MEMBER_DATA,"").length()>0){
                    setMembersVisibility();
                }
            }
        });
        rl_interaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PatientProfileActivity.this, ChatActivityPat.class);
                intent.putExtra("patient",patientResultPOJO);
                startActivity(intent);
            }
        });
        rl_view_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Pref.GetStringPref(getApplicationContext(),StringUtils.ALL_MEMBER_DATA,"").length()>0){
                    setMembersVisibility();
                }
                try {
                    if (memberResultPOJOs.size() > 0) {

                    } else {
                        ToastClass.ShowLongToast(getApplicationContext(), "No Member Added.");
                    }
                }
                catch (Exception e){
                    ToastClass.ShowLongToast(getApplicationContext(), "No Member Added.");
                }
            }
        });
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getAllMembers();
    }
    public void getAllMembers(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("p_id", patientResultPOJO.getP_id()));
        new WebServiceBase(nameValuePairs, this, GET_ALL_MEMBERS_API, false).execute(ApiConfig.GET_ALL_MEMBERS_URL);
    }

    private void initUi() {
        appBarLayout.addOnOffsetChangedListener(this);

        toolbarHeaderView.bindTo(message, message);
        floatHeaderView.bindTo(message, message);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case GET_ALL_MEMBERS_API:
                parseMemberData(response);
                break;
        }
    }
    public void parseMemberData(String response){
        Log.d(TAG,"response:-"+response);
        try{
            Gson gson=new Gson();
            GetAllMembers getAllMembers=gson.fromJson(response,GetAllMembers.class);
            if(getAllMembers.getSuccess().equals("true")){
                List<MemberResultPOJO> list_members=getAllMembers.getMemberResultPOJOList();
                setAllMembers(list_members);
                Pref.SetStringPref(getApplicationContext(),StringUtils.ALL_MEMBER_DATA,response);
            }
        }
        catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }
    List<MemberResultPOJO> memberResultPOJOs;
    public void setAllMembers(final List<MemberResultPOJO> list_members){
        ll_view_members.removeAllViews();
        memberResultPOJOs=list_members;
        for (int i = 0; i < list_members.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_members, null);
            CircleImageView iv_member= (CircleImageView) view.findViewById(R.id.iv_member);
            RelativeLayout rl_member= (RelativeLayout) view.findViewById(R.id.rl_member);
            TextView tv_member_name= (TextView) view.findViewById(R.id.tv_member_name);

            tv_member_name.setText(list_members.get(i).getM_name());
            Picasso.with(getApplicationContext())
                    .load("http://www.bjain.com/doctor/"+list_members.get(i).getM_photo())
                    .error(R.drawable.ic_action_person)
                    .into(iv_member);

            final int finalI = i;

            rl_member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(PatientProfileActivity.this,MemberAccountActivity.class);
                    intent.putExtra("patient",patientResultPOJO);
                    intent.putExtra("member",list_members.get(finalI));
                    startActivity(intent);
                }
            });

            ll_view_members.addView(view);
        }
    }
    public void setMembersVisibility(){
        switch (MEMBER_VISIBILITY){
            case SHOW_MEMBER:
                ll_view_members.setVisibility(View.GONE);
                MEMBER_VISIBILITY=HIDE_MEMBER;
                break;
            case HIDE_MEMBER:
                ll_view_members.setVisibility(View.VISIBLE);
                MEMBER_VISIBILITY=SHOW_MEMBER;
                break;
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
