package com.anton46.whatsapp_profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.ApiConfig;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 8/16/2016.
 */
public class UpdateDocInfo extends Activity {
    Button btn_submit;
    public static String reg_id, reg_name, reg_email, reg_pass, reg_cpass, dob, reg_department, reg_clinic_address, reg_designation, reg_specialist, reg_degree;
    EditText name,edtxt_pmob,
    pass, cpass, dpmt, addr,  deisgnation, degree, speciality;
    TextView email_id,avail;
    ScrollView svav;
ImageView img_avail;
    EditText mmrng,mafter,mevng,tmrng,tafter,tevng,wmrng,wafter,wevng,thmrng,thafter,thevng,fmrng,fafter,fevng,smrng,safter,sevng,sumrng,suafter,suevng;
    Spinner mmrng1,mmrng2,mafter1,mafter2,mevng1,mevng2,tmrng1,tmrng2,tafter1,tafter2,
           tevng1,tevng2,wmrng1,wmrng2,wafter1,wafter2,wevng1,wevng2,thmrng1,thmrng2,
           thafter1,thafter2,thevng1,thevng2,fmrng1,fmrng2,fafter1,fafter2,fevng1,fevng2,smrng1,smrng2,
           safter1,safter2,sevng1,sevng2,sumrng1,sumrng2,suafter1,suafter2,suevng1,suevng2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateinfodoc);

        mmrng1=(Spinner)findViewById(R.id.sp_place_m_mrng1);
        mmrng2=(Spinner)findViewById(R.id.sp_place_m_mrng2);
        mafter1=(Spinner)findViewById(R.id.sp_place_m_after1);
        mafter2=(Spinner)findViewById(R.id.sp_place_m_after2);
        mevng1=(Spinner)findViewById(R.id.sp_place_m_evng1);
        mevng2=(Spinner)findViewById(R.id.sp_place_m_evng2);
        tmrng1=(Spinner)findViewById(R.id.sp_place_t_mrng1);
        tmrng2=(Spinner)findViewById(R.id.sp_place_t_mrng2);
        tafter1=(Spinner)findViewById(R.id.sp_place_t_after1);
        tafter2=(Spinner)findViewById(R.id.sp_place_t_after2);
        tevng1=(Spinner)findViewById(R.id.sp_place_t_evng1);
        tevng2=(Spinner)findViewById(R.id.sp_place_t_evng2);
        wmrng1=(Spinner)findViewById(R.id.sp_place_w_mrng1);
        wmrng2=(Spinner)findViewById(R.id.sp_place_w_mrng2);
        wafter1=(Spinner)findViewById(R.id.sp_place_w_after1);
        wafter2=(Spinner)findViewById(R.id.sp_place_w_after2);
        wevng1=(Spinner)findViewById(R.id.sp_place_w_evng1);
        wevng2=(Spinner)findViewById(R.id.sp_place_w_evng2);
        thmrng1=(Spinner)findViewById(R.id.sp_place_th_mrng1);
        thmrng2=(Spinner)findViewById(R.id.sp_place_th_mrng2);
        thafter1=(Spinner)findViewById(R.id.sp_place_th_after1);
        thafter2=(Spinner)findViewById(R.id.sp_place_th_after2);
        thevng1=(Spinner)findViewById(R.id.sp_place_th_evng1);
        thevng2=(Spinner)findViewById(R.id.sp_place_th_evng2);
        fmrng1=(Spinner)findViewById(R.id.sp_place_f_mrng1);
        fmrng2=(Spinner)findViewById(R.id.sp_place_f_mrng2);
        fafter1=(Spinner)findViewById(R.id.sp_place_f_after1);
        fafter2=(Spinner)findViewById(R.id.sp_place_f_after2);
        fevng1=(Spinner)findViewById(R.id.sp_place_f_evng1);
        fevng2=(Spinner)findViewById(R.id.sp_place_f_evng2);
        smrng1=(Spinner)findViewById(R.id.sp_place_s_mrng1);
        smrng2=(Spinner)findViewById(R.id.sp_place_s_mrng2);
        safter1=(Spinner)findViewById(R.id.sp_place_s_after1);
        safter2=(Spinner)findViewById(R.id.sp_place_s_after2);
        sevng1=(Spinner)findViewById(R.id.sp_place_s_evng1);
        sevng2=(Spinner)findViewById(R.id.sp_place_s_evng2);
        sumrng1=(Spinner)findViewById(R.id.sp_place_su_mrng1);
        sumrng2=(Spinner)findViewById(R.id.sp_place_su_mrng2);
        suafter1=(Spinner)findViewById(R.id.sp_place_su_after1);
        suafter2=(Spinner)findViewById(R.id.sp_place_su_after2);
        suevng1=(Spinner)findViewById(R.id.sp_place_su_evng1);
        suevng2=(Spinner)findViewById(R.id.sp_place_su_evng2);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        name = (EditText) findViewById(R.id.edtxt_d_name);
        email_id = (TextView) findViewById(R.id.edtxt_d_email);
        edtxt_pmob = (EditText) findViewById(R.id.edtxt_pmob);
        pass = (EditText) findViewById(R.id.edtxt_d_password);
        cpass = (EditText) findViewById(R.id.edtxt_d_cpass);
        dpmt = (EditText) findViewById(R.id.d_department);
        addr = (EditText) findViewById(R.id.edtxt_d_add);
        avail = (TextView) findViewById(R.id.d_avail);
        deisgnation = (EditText) findViewById(R.id.d_desig);
        degree = (EditText) findViewById(R.id.d_degree);
        speciality = (EditText) findViewById(R.id.d_spcialist);

        mmrng = (EditText) findViewById(R.id.ed_place_m_mrng);
        mafter = (EditText) findViewById(R.id.ed_place_m_after);
        mevng = (EditText) findViewById(R.id.ed_place_m_evng);
        tmrng = (EditText) findViewById(R.id.ed_place_t_mrng);
        tafter = (EditText) findViewById(R.id.ed_place_t_after);
        tevng = (EditText) findViewById(R.id.ed_place_t_evng);
        wmrng = (EditText) findViewById(R.id.ed_place_w_mrng);
        wafter = (EditText) findViewById(R.id.ed_place_w_after);
        wevng = (EditText) findViewById(R.id.ed_place_w_evng);
        thmrng = (EditText) findViewById(R.id.ed_place_th_mrng);
        thafter = (EditText) findViewById(R.id.ed_place_th_after);
        thevng = (EditText) findViewById(R.id.ed_place_th_evng);
        fmrng = (EditText) findViewById(R.id.ed_place_f_mrng);
        fafter = (EditText) findViewById(R.id.ed_place_f_after);
        fevng = (EditText) findViewById(R.id.ed_place_f_evng);
        smrng = (EditText) findViewById(R.id.ed_place_s_mrng);
        safter = (EditText) findViewById(R.id.ed_place_s_after);
        sevng = (EditText) findViewById(R.id.ed_place_s_evng);
        sumrng = (EditText) findViewById(R.id.ed_place_su_mrng);
        suafter = (EditText) findViewById(R.id.ed_place_su_after);
        suevng = (EditText) findViewById(R.id.ed_place_su_evng);

        img_avail=(ImageView)findViewById(R.id.img_avail);
        svav= (ScrollView) findViewById(R.id.svav);
        name.setText(MainActivityDoctorProfile.reg_name);
        email_id.setText(MainActivityDoctorProfile.reg_email);
        pass.setText(PreferenceData.getPass(getApplicationContext()));
        dpmt.setText(MainActivityDoctorProfile.reg_department);
        addr.setText(MainActivityDoctorProfile.reg_clinic_address);
        deisgnation.setText(MainActivityDoctorProfile.reg_designation);
        degree.setText(MainActivityDoctorProfile.reg_degree);
        speciality.setText(MainActivityDoctorProfile.reg_specialist);

        mmrng.setText(MainActivityDoctorProfile.morn_loca_day1);
        mafter.setText(MainActivityDoctorProfile.afternoon_loca_day1);
        mevng.setText(MainActivityDoctorProfile.even_loca_day1);

        tmrng.setText(MainActivityDoctorProfile.morn_loca_day2);
        tafter.setText(MainActivityDoctorProfile.afternoon_loca_day2);
        tevng.setText(MainActivityDoctorProfile.even_loca_day2);

        wmrng.setText(MainActivityDoctorProfile.morn_loca_day3);
        wafter.setText(MainActivityDoctorProfile.afternoon_loca_day3);
        wevng.setText(MainActivityDoctorProfile.even_loca_day3);

        thmrng.setText(MainActivityDoctorProfile.morn_loca_day4);
        thafter.setText(MainActivityDoctorProfile.aafternoon_loca_day4);
        thevng.setText(MainActivityDoctorProfile.even_loca_day4);

        fmrng.setText(MainActivityDoctorProfile.morn_loca_day5);
        fafter.setText(MainActivityDoctorProfile.aafternoon_loca_day5);
        fevng.setText(MainActivityDoctorProfile.even_loca_day5);

        smrng.setText(MainActivityDoctorProfile.morn_loca_day6);
        safter.setText(MainActivityDoctorProfile.aafternoon_loca_day6);
        sevng.setText(MainActivityDoctorProfile.even_loca_day6);

        sumrng.setText(MainActivityDoctorProfile.morn_loca_day7);
        suafter.setText(MainActivityDoctorProfile.aafternoon_loca_day7);
        suevng.setText(MainActivityDoctorProfile.even_loca_day7);

        img_avail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (svav.getVisibility()== View.VISIBLE) {
                    svav.setVisibility(View.GONE);
                }
                else {
                    svav.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CallServices().execute(ApiConfig.UPDATE_DOCTOR_INFO);
            }
        });
    }
    public String ValidateSpinner(Spinner edit){
        try {
            if (edit.equals(null)) {
                return "";
            } else {
                if (edit.getSelectedItem().toString().equals(null) || edit.getSelectedItem().toString().equals("")) {
                    return "";
                } else {
                    return edit.getSelectedItem().toString().trim();
                }

            }
        }
        catch (Exception e){
            return "";
        }
    }
    public String ValidateEdit(EditText edit){
        try {
            if (edit.equals(null)) {
                return "";
            } else {
                if (edit.getText().toString().equals(null) || edit.getText().toString().equals("")) {
                    return "";
                } else {
                    return edit.getText().toString().trim();
                }

            }
        }
        catch (Exception e){
            return "";
        }
    }
    public class CallServices extends AsyncTask<String, String, String> {
        String d_name = name.getText().toString().trim();
        String d_email = email_id.getText().toString().trim();
        String d_pmob = edtxt_pmob.getText().toString().trim();
        String d_pass = pass.getText().toString().trim();
        String d_cpass = cpass.getText().toString().trim();
        String d_department = dpmt.getText().toString().trim();
        String d_avail = avail.getText().toString().trim();
        String d_address = addr.getText().toString().trim();
        String d_desig = deisgnation.getText().toString().trim();
        String d_degree = degree.getText().toString().trim();
        String d_spcialist = speciality.getText().toString().trim();
        String avl_start_time1_morn=ValidateSpinner(mmrng1);
        String avl_end_time1_morn=ValidateSpinner(mmrng2);
        String morn_loca_day1=ValidateEdit(mmrng);
        String avl_start_time1_afternoon=ValidateSpinner(mafter1);
        String avl_end_time1_afternoon=ValidateSpinner(mafter2);
        String afternoon_loca_day1=ValidateEdit(mafter);
        String avl_start_time1_even=ValidateSpinner(mevng1);
        String avl_end_time1_even=ValidateSpinner(mevng2);
        String even_loca_day1=ValidateEdit(mevng);

        String avl_start_time2_morn=ValidateSpinner(tmrng1);
        String avl_end_time2_morn=ValidateSpinner(tmrng2);
        String morn_loca_day2=ValidateEdit(tmrng);
        String avl_start_time2_afternoon=ValidateSpinner(tafter1);
        String avl_end_time2_afternoon=ValidateSpinner(tafter2);
        String afternoon_loca_day2=ValidateEdit(tafter);
        String avl_start_time2_even=ValidateSpinner(tevng1);
        String avl_end_time2_even=ValidateSpinner(tevng2);
        String even_loca_day2=ValidateEdit(tevng);

        String avl_start_time3_morn=ValidateSpinner(wmrng1);
        String avl_end_time3_morn=ValidateSpinner(wmrng2);
        String morn_loca_day3=ValidateEdit(wmrng);
        String avl_start_time3_afternoon=ValidateSpinner(wafter1);
        String avl_end_time3_afternoon=ValidateSpinner(wafter2);
        String afternoon_loca_day3=ValidateEdit(wafter);
        String avl_start_time3_even=ValidateSpinner(wevng1);
        String avl_end_time3_even=ValidateSpinner(wevng1);
        String even_loca_day3=ValidateEdit(wevng);

        String avl_start_time4_morn=ValidateSpinner(thmrng1);
        String avl_end_time4_morn=ValidateSpinner(thmrng2);
        String morn_loca_day4=ValidateEdit(thmrng);
        String avl_start_time4_afternoon=ValidateSpinner(thafter1);
        String avl_end_time4_afternoon=ValidateSpinner(thafter2);
        String afternoon_loca_day4=ValidateEdit(thafter);
        String avl_start_time4_even=ValidateSpinner(thevng1);
        String avl_end_time4_even=ValidateSpinner(thevng2);
        String even_loca_day4=ValidateEdit(thevng);

        String avl_start_time5_morn=ValidateSpinner(fmrng1);
        String avl_end_time5_morn=ValidateSpinner(fmrng2);
        String morn_loca_day5=ValidateEdit(fmrng);
        String avl_start_time5_afternoon=ValidateSpinner(fafter1);
        String avl_end_time5_afternoon=ValidateSpinner(fafter2);
        String afternoon_loca_day5=ValidateEdit(fafter);
        String avl_start_time5_even=ValidateSpinner(fevng1);
        String avl_end_time5_even=ValidateSpinner(fevng2);
        String even_loca_day5=ValidateEdit(fevng);

        String avl_start_time6morn=ValidateSpinner(smrng1);
        String avl_end_time6_morn=ValidateSpinner(smrng2);
        String morn_loca_day6=ValidateEdit(smrng);
        String avl_start_time6_afternoon=ValidateSpinner(safter1);
        String avl_end_time6_afternoon=ValidateSpinner(safter2);
        String afternoon_loca_day6=ValidateEdit(safter);
        String avl_start_time6_even=ValidateSpinner(sevng1);
        String avl_end_time6_even=ValidateSpinner(sevng1);
        String even_loca_day6=ValidateEdit(sevng);

        String avl_start_time7_morn=ValidateSpinner(sumrng1);
        String avl_end_time7_morn=ValidateSpinner(sumrng2);
        String morn_loca_day7=ValidateEdit(sumrng);
        String avl_start_time7_afternoon=ValidateSpinner(suafter1);
        String avl_end_time7_afternoon=ValidateSpinner(suafter2);
        String afternoon_loca_day7=ValidateEdit(suafter);
        String avl_start_time7_even=ValidateSpinner(suevng1);
        String avl_end_time7_even=ValidateSpinner(suevng2);
        String even_loca_day7=ValidateEdit(suevng);

        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(UpdateDocInfo.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("reg_id", PreferenceData.getId(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("reg_name", d_name));
            namevaluepair.add(new BasicNameValuePair("reg_pass", d_pass));
            namevaluepair.add(new BasicNameValuePair("reg_cpass", d_cpass));
            namevaluepair.add(new BasicNameValuePair("reg_email", d_email));
            namevaluepair.add(new BasicNameValuePair("reg_mob", d_pmob));
            namevaluepair.add(new BasicNameValuePair("department", d_department));
            namevaluepair.add(new BasicNameValuePair("clinic_address", d_address));
            namevaluepair.add(new BasicNameValuePair("availability", d_avail));
            namevaluepair.add(new BasicNameValuePair("designation", d_desig));
            namevaluepair.add(new BasicNameValuePair("degree", d_degree));
            namevaluepair.add(new BasicNameValuePair("specialist", d_spcialist));

            namevaluepair.add(new BasicNameValuePair("avl_start_time1_morn", avl_start_time1_morn));
            namevaluepair.add(new BasicNameValuePair("avl_end_time1_morn", avl_end_time1_morn));
            namevaluepair.add(new BasicNameValuePair("morn_loca_day1", morn_loca_day1));
            namevaluepair.add(new BasicNameValuePair("avl_start_time1_afternoon",avl_start_time1_afternoon ));
            namevaluepair.add(new BasicNameValuePair("avl_end_time1_afternoon",avl_end_time1_afternoon ));
            namevaluepair.add(new BasicNameValuePair("afternoon_loca_day1", afternoon_loca_day1));
            namevaluepair.add(new BasicNameValuePair("avl_start_time1_even", avl_start_time1_even));
            namevaluepair.add(new BasicNameValuePair("avl_end_time1_even", avl_end_time1_even));
            namevaluepair.add(new BasicNameValuePair("even_loca_day1", even_loca_day1));
            namevaluepair.add(new BasicNameValuePair("avl_day1", "Monday"));

            namevaluepair.add(new BasicNameValuePair("avl_start_time2_morn", avl_start_time2_morn));
            namevaluepair.add(new BasicNameValuePair("avl_end_time2_morn", avl_end_time2_morn));
            namevaluepair.add(new BasicNameValuePair("morn_loca_day2", morn_loca_day2));
            namevaluepair.add(new BasicNameValuePair("avl_start_time2_afternoon", avl_start_time2_afternoon));
            namevaluepair.add(new BasicNameValuePair("avl_end_time2_afternoon", avl_end_time2_afternoon));
            namevaluepair.add(new BasicNameValuePair("afternoon_loca_day2", afternoon_loca_day2));
            namevaluepair.add(new BasicNameValuePair("avl_start_time2_even", avl_start_time2_even));
            namevaluepair.add(new BasicNameValuePair("avl_end_time2_even", avl_end_time2_even));
            namevaluepair.add(new BasicNameValuePair("even_loca_day2", even_loca_day2));
            namevaluepair.add(new BasicNameValuePair("avl_day2", "Tuesday"));

            namevaluepair.add(new BasicNameValuePair("avl_start_time3_morn", avl_start_time3_morn));
            namevaluepair.add(new BasicNameValuePair("avl_end_time3_morn", avl_end_time3_morn));
            namevaluepair.add(new BasicNameValuePair("morn_loca_day3", morn_loca_day3));
            namevaluepair.add(new BasicNameValuePair("avl_start_time3_afternoon", avl_start_time3_afternoon));
            namevaluepair.add(new BasicNameValuePair("avl_end_time3_afternoon", avl_end_time3_afternoon));
            namevaluepair.add(new BasicNameValuePair("afternoon_loca_day3", afternoon_loca_day3));
            namevaluepair.add(new BasicNameValuePair("avl_start_time3_even", avl_start_time3_even));
            namevaluepair.add(new BasicNameValuePair("avl_end_time3_even", avl_end_time3_even));
            namevaluepair.add(new BasicNameValuePair("even_loca_day3", even_loca_day3));
            namevaluepair.add(new BasicNameValuePair("avl_day3", "Wednesday"));

            namevaluepair.add(new BasicNameValuePair("avl_start_time4_morn", avl_start_time4_morn));
            namevaluepair.add(new BasicNameValuePair("avl_end_time4_morn", avl_end_time4_morn));
            namevaluepair.add(new BasicNameValuePair("morn_loca_day4", morn_loca_day4));
            namevaluepair.add(new BasicNameValuePair("avl_start_time4_afternoon", avl_start_time4_afternoon));
            namevaluepair.add(new BasicNameValuePair("avl_end_time4_afternoon", avl_end_time4_afternoon));
            namevaluepair.add(new BasicNameValuePair("afternoon_loca_day4", afternoon_loca_day4));
            namevaluepair.add(new BasicNameValuePair("avl_start_time4_even", avl_start_time4_even));
            namevaluepair.add(new BasicNameValuePair("avl_end_time4_even", avl_end_time4_even));
            namevaluepair.add(new BasicNameValuePair("even_loca_day4", even_loca_day4));
            namevaluepair.add(new BasicNameValuePair("avl_day4", "Thursday"));

            namevaluepair.add(new BasicNameValuePair("avl_start_time5_morn", avl_start_time5_morn));
            namevaluepair.add(new BasicNameValuePair("avl_end_time5_morn", avl_end_time5_morn));
            namevaluepair.add(new BasicNameValuePair("morn_loca_day5", morn_loca_day5));
            namevaluepair.add(new BasicNameValuePair("avl_start_time5_afternoon", avl_start_time5_afternoon));
            namevaluepair.add(new BasicNameValuePair("avl_end_time5_afternoon", avl_end_time5_afternoon));
            namevaluepair.add(new BasicNameValuePair("afternoon_loca_day5", afternoon_loca_day5));
            namevaluepair.add(new BasicNameValuePair("avl_start_time5_even", avl_start_time5_even));
            namevaluepair.add(new BasicNameValuePair("avl_end_time5_even", avl_end_time5_even));
            namevaluepair.add(new BasicNameValuePair("even_loca_day5", even_loca_day5));
            namevaluepair.add(new BasicNameValuePair("avl_day5", "Friday"));

            namevaluepair.add(new BasicNameValuePair("avl_start_time6_morn", avl_start_time6morn));
            namevaluepair.add(new BasicNameValuePair("avl_end_time6_morn", avl_end_time6_morn));
            namevaluepair.add(new BasicNameValuePair("morn_loca_day6", morn_loca_day6));
            namevaluepair.add(new BasicNameValuePair("avl_start_time6_afternoon", avl_start_time6_afternoon));
            namevaluepair.add(new BasicNameValuePair("avl_end_time6_afternoon", avl_end_time6_afternoon));
            namevaluepair.add(new BasicNameValuePair("afternoon_loca_day6", afternoon_loca_day6));
            namevaluepair.add(new BasicNameValuePair("avl_start_time6_even", avl_start_time6_even));
            namevaluepair.add(new BasicNameValuePair("avl_end_time6_even", avl_end_time6_even));
            namevaluepair.add(new BasicNameValuePair("even_loca_day6", even_loca_day6));
            namevaluepair.add(new BasicNameValuePair("avl_day6", "Saturday"));

            namevaluepair.add(new BasicNameValuePair("avl_start_time7_morn", avl_start_time7_morn));
            namevaluepair.add(new BasicNameValuePair("avl_end_time7_morn", avl_end_time7_morn));
            namevaluepair.add(new BasicNameValuePair("morn_loca_day7", morn_loca_day7));
            namevaluepair.add(new BasicNameValuePair("avl_start_time7_afternoon", avl_start_time7_afternoon));
            namevaluepair.add(new BasicNameValuePair("avl_end_time7_afternoon", avl_end_time7_afternoon));
            namevaluepair.add(new BasicNameValuePair("afternoon_loca_day7", afternoon_loca_day7));
            namevaluepair.add(new BasicNameValuePair("avl_start_time7_even", avl_start_time7_even));
            namevaluepair.add(new BasicNameValuePair("avl_end_time7_even", avl_end_time7_even));
            namevaluepair.add(new BasicNameValuePair("even_loca_day7", even_loca_day7));
            namevaluepair.add(new BasicNameValuePair("avl_day7", "Sunday"));
            //namevaluepair.add(new BasicNameValuePair("cat", "HAIR"));

            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

                Log.e("result", result.toString());

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
                    JSONObject objresponse = new JSONObject(result);
                    JSONObject jsonObject=new JSONObject(result);
                    String d_message=jsonObject.getString("d_message");
                    if (d_message.equalsIgnoreCase("success"))
                    {
                        Toast.makeText(UpdateDocInfo.this,"Successfully updated yor information", Toast.LENGTH_LONG).show();
                        reg_designation = objresponse.getString("designation");
                        reg_clinic_address = objresponse.getString("clinic_address");
                        reg_name = objresponse.getString("reg_email");
                        reg_pass= objresponse.getString("reg_pass");
                        reg_department = objresponse.getString("department");
                        dob = objresponse.getString("photo");
                        reg_specialist = objresponse.getString("specialist");
                        PreferenceData.setName(getApplicationContext(),objresponse.getString("reg_name"));
                        PreferenceData.setEmail(getApplicationContext(),reg_name);
                        PreferenceData.setPass(getApplicationContext(),reg_pass);
                        /*PreferenceData.setPhoto(getApplicationContext(),dob);
                        PreferenceData.setId(getApplicationContext(),reg_id);
                        PreferenceData.setEmail(getApplicationContext(),reg_email);
                        PreferenceData.setPass(getApplicationContext(),reg_pass);*/
                        PreferenceData.setDoctorSpecialist(getApplicationContext(), reg_specialist);
                        PreferenceData.setDoctorNumber(getApplicationContext(), objresponse.getString("reg_mob"));
                        PreferenceData.setDoctorclinic_address(getApplicationContext(), objresponse.getString("clinic_address"));
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result1","okay");
                        setResult(UpdateDocInfo.this.RESULT_OK,returnIntent);
                        finish();
                    }
                    else {
//                        Toast.makeText(UpdateDocInfo.this,"please enter the location correctly", Toast.LENGTH_LONG).show();
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
