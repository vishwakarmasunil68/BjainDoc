package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.whatsapp_profile.MainActivityDoctorProfile;
import com.emobi.bjaindoc.pojo.ClinicInfoPOJOResult;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.emobi.bjaindoc.R.id.btn_submit;

/**
 * Created by Emobi-Android-002 on 10/20/2016.
 */
public class UpdateByDoc extends AppCompatActivity {

    TextView textDoc, btn_Submit;
    public static String reg_id, reg_name, reg_email, reg_pass, reg_cpass, dob, reg_department, reg_clinic_address, reg_designation, reg_specialist, reg_degree;
    String rate1, rate2, reason;
    ActionBar actionBar;
    HttpEntity resEntity;
    String response_str;
    Activity activity;
    LinearLayout ll_save;
    private TabLayout tabLayout;
    CircleImageView img_profile;
    private ViewPager viewPager;
    private static final int FILE_SELECT_CODE = 0;
    ImageView whitearrow;
    TextView txt_pname;
    String selectedImagePath;
    boolean show_save=false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    PagerAdapterDoc adapter;

    //    String selectedImagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_bypatient);
        activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ll_save = (LinearLayout) findViewById(R.id.ll_save);
        setSupportActionBar(toolbar);


        actionBar = getSupportActionBar();
        SpannableString s = new SpannableString("      Personal Details");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(R.drawable.bjainicon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("    Personal Details");
        actionBar.setTitle(s);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //.setIcon(R.drawable.activation)
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Clinic Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Availability"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        btn_Submit = (TextView) findViewById(btn_submit);
        txt_pname = (TextView) findViewById(R.id.txt_pname);
        img_profile =(CircleImageView) findViewById(R.id.img_profile);
        textDoc = (TextView) findViewById(R.id.textDoc);
        whitearrow = (ImageView) findViewById(R.id.whitearrow);
        whitearrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        textDoc.setTypeface(tf);
//        Log.e("d_dob",UpdateDoc_Dcotor.edtxt_d_dob.getText().toString());
        viewPager = (ViewPager) findViewById(R.id.pager);

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, FILE_SELECT_CODE);
            }
        });
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(viewPager.getCurrentItem()==1){
                    try {
                        UpdateDoctorFragment addi = (UpdateDoctorFragment) adapter.frag1;
                        addi.UpdateClinicInfo();
                    }
                    catch (Exception e){

                    }
                }
                else {

                    if (selectedImagePath == null) {
                        Log.e("if", "if");
                        selectedImagePath = PreferenceData.getPhoto(getApplicationContext());

                        new CallServices().execute(ApiConfig.UPDATE_DOCTOR_INFO);

                    } else {
                        Log.e("if", "else");
//                    selectedImagePath="http://www.bjain.com/doctor/upload/"+PreferenceData.getPhoto(getApplicationContext());

                        Thread thread2 = new Thread(new Runnable() {
                            public void run() {

//
                                doFileUpload2();
                                runOnUiThread(new Runnable() {
                                    public void run() {


                                    }
                                });
                            }
                        });
                        thread2.start();
                    }
                }
            }
        });

        txt_pname.setText(MainActivityDoctorProfile.reg_name);
        Typeface tf1= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        txt_pname.setTypeface(tf1);
        try {
            if (PreferenceData.getPhoto(getApplicationContext()).length() > 0) {
                String bitmap = "http://www.bjain.com/doctor/upload/" + PreferenceData.getPhoto(getApplicationContext());

                Log.e("stringToBitmap", bitmap.toString());
                Picasso.with(getApplicationContext()).load(bitmap).resize(100, 100).into(img_profile);
            }
            else{

            }
        }

        catch (Exception e){

        }
//        tabLayout.setupWithViewPager(viewPager);
        /*tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#578A48"));*/

        adapter = new PagerAdapterDoc
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                /578A48
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#469834"));
                Log.d(TAG,"page selected:-"+viewPager.getCurrentItem());
                if(viewPager.getCurrentItem()==2){
                    btn_Submit.setVisibility(View.GONE);
                }
                if(show_save){
                    btn_Submit.setVisibility(View.VISIBLE);
                }
                else {
                    if (viewPager.getCurrentItem() == 1) {
                        btn_Submit.setVisibility(View.GONE);
                    }
                }
                if(viewPager.getCurrentItem()==0){
                    btn_Submit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        viewPager.setOffscreenPageLimit(3);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            int pos=bundle.getInt("position");
            viewPager.setCurrentItem(pos);
        }
        try {
            String viewpag = getIntent().getStringExtra("view");
            Log.d("error", viewpag.toString());
            if (viewpag.equals("view")) {
                viewPager.setCurrentItem(2);
            }
        }
        catch(Exception e){
            Log.d("error", e.toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UpdateByPatient Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.emobi.bjaindoc/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
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
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UpdateByPatient Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.emobi.bjaindoc/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class CallServices extends AsyncTask<String, String, String> {
        String d_name = ValidateEdit(UpdateDoc_Dcotor.name);
        String d_email = UpdateDoc_Dcotor.email_id.getText().toString();
        String d_dob = UpdateDoc_Dcotor.edtxt_d_dob.getText().toString();
        String d_pmob = ValidateEdit(UpdateDoc_Dcotor.edtxt_pmob);
        String d_pass = ValidateEdit(UpdateDoc_Dcotor.pass);
        String d_cpass = ValidateEdit(UpdateDoc_Dcotor.name);
        String d_department = ValidateEdit(UpdateDoc_Addi.dpmt);
        String d_avail = "";
        String d_desig = ValidateEdit(UpdateDoc_Dcotor.et_department);
        String d_degree = ValidateEdit(UpdateDoc_Dcotor.et_degree);
        String d_address = ValidateEdit(UpdateDoc_Dcotor.et_address);
        String d_spcialist = ValidateEdit(UpdateDoc_Dcotor.et_speciality);

        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Log.e("d_dob",UpdateDoc_Dcotor.edtxt_d_dob.getText().toString());
            pd = new ProgressDialog(UpdateByDoc.this);

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
            namevaluepair.add(new BasicNameValuePair("photo", selectedImagePath));
            namevaluepair.add(new BasicNameValuePair("reg_name", d_name));
            namevaluepair.add(new BasicNameValuePair("reg_pass", d_pass));
            namevaluepair.add(new BasicNameValuePair("reg_cpass", d_cpass));
            namevaluepair.add(new BasicNameValuePair("reg_email", d_email));
            namevaluepair.add(new BasicNameValuePair("reg_dob", d_dob));
            namevaluepair.add(new BasicNameValuePair("reg_mob", d_pmob));
            namevaluepair.add(new BasicNameValuePair("department", d_department));
            namevaluepair.add(new BasicNameValuePair("clinic_address", d_address));
            namevaluepair.add(new BasicNameValuePair("availability", d_avail));
            namevaluepair.add(new BasicNameValuePair("designation", d_desig));
            namevaluepair.add(new BasicNameValuePair("degree", d_degree));
            namevaluepair.add(new BasicNameValuePair("specialist", d_spcialist));


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
                        Toast.makeText(UpdateByDoc.this,"Successfully updated yor information", Toast.LENGTH_LONG).show();
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
                        setResult(UpdateByDoc.this.RESULT_OK,returnIntent);
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

    private void doFileUpload2() {
        Log.i("RESPONSE", "file1");
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    Log.i("RESPONSE", "file2");
                    String d_name = ValidateEdit(UpdateDoc_Dcotor.name);
                    String d_email = UpdateDoc_Dcotor.email_id.getText().toString();
                    String d_pmob = ValidateEdit(UpdateDoc_Dcotor.edtxt_pmob);
                    String d_pass = ValidateEdit(UpdateDoc_Dcotor.pass);
                    String d_cpass = ValidateEdit(UpdateDoc_Dcotor.name);
                    String d_department = ValidateEdit(UpdateDoc_Addi.dpmt);
                    String d_avail = "";
                    String d_address = ValidateEdit(UpdateDoc_Addi.addr);
                    String d_desig = ValidateEdit(UpdateDoc_Addi.deisgnation);
                    String d_degree = ValidateEdit(UpdateDoc_Addi.degree);
                    String d_spcialist = ValidateEdit(UpdateDoc_Addi.speciality);


                    /*String avl_start_time1_morn=ChooseMonday.text;
                    String avl_end_time1_morn=ChooseMonday.text;
                    String morn_loca_day1=ValidateEdit(ChooseMonday.txt_ht);
                    String avl_start_time1_afternoon=ChooseMonday.text;
                    String avl_end_time1_afternoon=ChooseMonday.text;
                    String afternoon_loca_day1=ValidateEdit(ChooseMonday.txt_ht);
                    String avl_start_time1_even=ChooseMonday.text;
                    String avl_end_time1_even=ChooseMonday.text;
                    String even_loca_day1=ValidateEdit(ChooseMonday.txt_ht);

                    String avl_start_time2_morn=ChooseTueday.text;
                    String avl_end_time2_morn=ChooseTueday.text;
                    String morn_loca_day2=ValidateEdit(ChooseTueday.txt_ht);
                    String avl_start_time2_afternoon=ChooseTueday.text;
                    String avl_end_time2_afternoon=ChooseTueday.text;
                    String afternoon_loca_day2=ValidateEdit(ChooseTueday.txt_ht);
                    String avl_start_time2_even=ChooseTueday.text;
                    String avl_end_time2_even=ChooseTueday.text;
                    String even_loca_day2=ValidateEdit(ChooseTueday.txt_ht);

                    String avl_start_time3_morn=ChooseWedday.text;
                    String avl_end_time3_morn=ChooseWedday.text;
                    String morn_loca_day3=ValidateEdit(ChooseWedday.txt_ht);
                    String avl_start_time3_afternoon=ChooseWedday.text;
                    String avl_end_time3_afternoon=ChooseWedday.text;
                    String afternoon_loca_day3=ValidateEdit(ChooseWedday.txt_ht);
                    String avl_start_time3_even=ChooseWedday.text;
                    String avl_end_time3_even=ChooseWedday.text;
                    String even_loca_day3=ValidateEdit(ChooseWedday.txt_ht);

                    String avl_start_time4_morn=ChooseThurday.text;
                    String avl_end_time4_morn=ChooseThurday.text;
                    String morn_loca_day4=ValidateEdit(ChooseThurday.txt_ht);
                    String avl_start_time4_afternoon=ChooseThurday.text;
                    String avl_end_time4_afternoon=ChooseThurday.text;
                    String afternoon_loca_day4=ValidateEdit(ChooseThurday.txt_ht);
                    String avl_start_time4_even=ChooseThurday.text;
                    String avl_end_time4_even=ChooseThurday.text;
                    String even_loca_day4=ValidateEdit(ChooseThurday.txt_ht);

                    String avl_start_time5_morn=ChooseFriday.text;
                    String avl_end_time5_morn=ChooseFriday.text;
                    String morn_loca_day5=ValidateEdit(ChooseFriday.txt_ht);
                    String avl_start_time5_afternoon=ChooseFriday.text;
                    String avl_end_time5_afternoon=ChooseFriday.text;
                    String afternoon_loca_day5=ValidateEdit(ChooseFriday.txt_ht);
                    String avl_start_time5_even=ChooseFriday.text;
                    String avl_end_time5_even=ChooseFriday.text;
                    String even_loca_day5=ValidateEdit(ChooseFriday.txt_ht);

                    String avl_start_time6morn=ChooseSatday.text;
                    String avl_end_time6_morn=ChooseSatday.text;
                    String morn_loca_day6=ValidateEdit(ChooseSatday.txt_ht);
                    String avl_start_time6_afternoon=ChooseSatday.text;
                    String avl_end_time6_afternoon=ChooseSatday.text;
                    String afternoon_loca_day6=ValidateEdit(ChooseSatday.txt_ht);
                    String avl_start_time6_even=ChooseSatday.text;
                    String avl_end_time6_even=ChooseSatday.text;
                    String even_loca_day6=ValidateEdit(ChooseSatday.txt_ht);

                    String avl_start_time7_morn=ChooseSunday.text;
                    String avl_end_time7_morn=ChooseSunday.text;
                    String morn_loca_day7=ValidateEdit(ChooseSunday.txt_ht);
                    String avl_start_time7_afternoon=ChooseSunday.text;
                    String avl_end_time7_afternoon=ChooseSunday.text;
                    String afternoon_loca_day7=ValidateEdit(ChooseSunday.txt_ht);
                    String avl_start_time7_even=ChooseSunday.text;
                    String avl_end_time7_even=ChooseSunday.text;
                    String even_loca_day7=ValidateEdit(ChooseSunday.txt_ht);*/

                    final  HttpClient client = new DefaultHttpClient();
                    final HttpPost post = new HttpPost("http://www.bjain.com/doctor/updatedoctor.php");
                    final  MultipartEntity reqEntity = new MultipartEntity();
                    File file1 = new File(selectedImagePath);
                    FileBody bin1 = new FileBody(file1);

                    reqEntity.addPart("photo", bin1);
                    reqEntity.addPart("reg_id", new StringBody(PreferenceData.getId(getApplicationContext())));
                    reqEntity.addPart("reg_name", new StringBody(d_name));
                    reqEntity.addPart("reg_pass",new StringBody( d_pass));
                    reqEntity.addPart("reg_cpass", new StringBody(d_cpass));
                    reqEntity.addPart("reg_email", new StringBody(d_email));
                    reqEntity.addPart("reg_mob", new StringBody(d_pmob));
                    reqEntity.addPart("department", new StringBody(d_department));
                    reqEntity.addPart("clinic_address", new StringBody(d_address));
                    reqEntity.addPart("availability",new StringBody(d_avail));
                    reqEntity.addPart("designation", new StringBody(d_desig));
                    reqEntity.addPart("degree", new StringBody(d_degree));
                    reqEntity.addPart("specialist", new StringBody(d_spcialist));

                    /*reqEntity.addPart("avl_start_time1_morn", new StringBody(avl_start_time1_morn));
                    reqEntity.addPart("avl_end_time1_morn", new StringBody(avl_end_time1_morn));
                    reqEntity.addPart("morn_loca_day1", new StringBody(morn_loca_day1));
                    reqEntity.addPart("avl_start_time1_afternoon",new StringBody(avl_start_time1_afternoon ));
                    reqEntity.addPart("avl_end_time1_afternoon",new StringBody(avl_end_time1_afternoon ));
                    reqEntity.addPart("afternoon_loca_day1", new StringBody(afternoon_loca_day1));
                    reqEntity.addPart("avl_start_time1_even", new StringBody(avl_start_time1_even));
                    reqEntity.addPart("avl_end_time1_even", new StringBody(avl_end_time1_even));
                    reqEntity.addPart("even_loca_day1", new StringBody(even_loca_day1));
                    reqEntity.addPart("avl_day1", new StringBody("Monday"));

                    reqEntity.addPart("avl_start_time2_morn", new StringBody(avl_start_time2_morn));
                    reqEntity.addPart("avl_end_time2_morn", new StringBody(avl_end_time2_morn));
                    reqEntity.addPart("morn_loca_day2", new StringBody(morn_loca_day2));
                    reqEntity.addPart("avl_start_time2_afternoon", new StringBody(avl_start_time2_afternoon));
                    reqEntity.addPart("avl_end_time2_afternoon", new StringBody(avl_end_time2_afternoon));
                    reqEntity.addPart("afternoon_loca_day2", new StringBody(afternoon_loca_day2));
                    reqEntity.addPart("avl_start_time2_even", new StringBody(avl_start_time2_even));
                    reqEntity.addPart("avl_end_time2_even", new StringBody(avl_end_time2_even));
                    reqEntity.addPart("even_loca_day2", new StringBody(even_loca_day2));
                    reqEntity.addPart("avl_day2", new StringBody("Tuesday"));

                    reqEntity.addPart("avl_start_time3_morn", new StringBody(avl_start_time3_morn));
                    reqEntity.addPart("avl_end_time3_morn", new StringBody(avl_end_time3_morn));
                    reqEntity.addPart("morn_loca_day3", new StringBody(morn_loca_day3));
                    reqEntity.addPart("avl_start_time3_afternoon",new StringBody(avl_start_time3_afternoon));
                    reqEntity.addPart("avl_end_time3_afternoon", new StringBody(avl_end_time3_afternoon));
                    reqEntity.addPart("afternoon_loca_day3", new StringBody(afternoon_loca_day3));
                    reqEntity.addPart("avl_start_time3_even", new StringBody(avl_start_time3_even));
                    reqEntity.addPart("avl_end_time3_even", new StringBody(avl_end_time3_even));
                    reqEntity.addPart("even_loca_day3", new StringBody(even_loca_day3));
                    reqEntity.addPart("avl_day3", new StringBody("Wednesday"));

                    reqEntity.addPart("avl_start_time4_morn", new StringBody(avl_start_time4_morn));
                    reqEntity.addPart("avl_end_time4_morn", new StringBody(avl_end_time4_morn));
                    reqEntity.addPart("morn_loca_day4", new StringBody(morn_loca_day4));
                    reqEntity.addPart("avl_start_time4_afternoon", new StringBody(avl_start_time4_afternoon));
                    reqEntity.addPart("avl_end_time4_afternoon", new StringBody(avl_end_time4_afternoon));
                    reqEntity.addPart("afternoon_loca_day4", new StringBody(afternoon_loca_day4));
                    reqEntity.addPart("avl_start_time4_even", new StringBody(avl_start_time4_even));
                    reqEntity.addPart("avl_end_time4_even", new StringBody(avl_end_time4_even));
                    reqEntity.addPart("even_loca_day4", new StringBody(even_loca_day4));
                    reqEntity.addPart("avl_day4", new StringBody("Thursday"));

                    reqEntity.addPart("avl_start_time5_morn", new StringBody(avl_start_time5_morn));
                    reqEntity.addPart("avl_end_time5_morn", new StringBody(avl_end_time5_morn));
                    reqEntity.addPart("morn_loca_day5", new StringBody(morn_loca_day5));
                    reqEntity.addPart("avl_start_time5_afternoon", new StringBody(avl_start_time5_afternoon));
                    reqEntity.addPart("avl_end_time5_afternoon", new StringBody(avl_end_time5_afternoon));
                    reqEntity.addPart("afternoon_loca_day5", new StringBody(afternoon_loca_day5));
                    reqEntity.addPart("avl_start_time5_even", new StringBody(avl_start_time5_even));
                    reqEntity.addPart("avl_end_time5_even", new StringBody(avl_end_time5_even));
                    reqEntity.addPart("even_loca_day5", new StringBody(even_loca_day5));
                    reqEntity.addPart("avl_day5", new StringBody("Friday"));

                    reqEntity.addPart("avl_start_time6_morn", new StringBody(avl_start_time6morn));
                    reqEntity.addPart("avl_end_time6_morn", new StringBody(avl_end_time6_morn));
                    reqEntity.addPart("morn_loca_day6", new StringBody(morn_loca_day6));
                    reqEntity.addPart("avl_start_time6_afternoon", new StringBody(avl_start_time6_afternoon));
                    reqEntity.addPart("avl_end_time6_afternoon", new StringBody(avl_end_time6_afternoon));
                    reqEntity.addPart("afternoon_loca_day6", new StringBody(afternoon_loca_day6));
                    reqEntity.addPart("avl_start_time6_even", new StringBody(avl_start_time6_even));
                    reqEntity.addPart("avl_end_time6_even", new StringBody(avl_end_time6_even));
                    reqEntity.addPart("even_loca_day6", new StringBody(even_loca_day6));
                    reqEntity.addPart("avl_day6", new StringBody("Saturday"));

                    reqEntity.addPart("avl_start_time7_morn", new StringBody(avl_start_time7_morn));
                    reqEntity.addPart("avl_end_time7_morn", new StringBody(avl_end_time7_morn));
                    reqEntity.addPart("morn_loca_day7", new StringBody(morn_loca_day7));
                    reqEntity.addPart("avl_start_time7_afternoon", new StringBody(avl_start_time7_afternoon));
                    reqEntity.addPart("avl_end_time7_afternoon", new StringBody(avl_end_time7_afternoon));
                    reqEntity.addPart("afternoon_loca_day7", new StringBody(afternoon_loca_day7));
                    reqEntity.addPart("avl_start_time7_even", new StringBody(avl_start_time7_even));
                    reqEntity.addPart("avl_end_time7_even", new StringBody(avl_end_time7_even));
                    reqEntity.addPart("even_loca_day7", new StringBody(even_loca_day7));
                    reqEntity.addPart("avl_day7", new StringBody("Sunday"));*/

                    post.setEntity(reqEntity);

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                HttpResponse response = client.execute(post);
                                resEntity = response.getEntity();
                                final String response_str = EntityUtils.toString(resEntity);
                                Log.e("RESPONSE", response_str);
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result1","okay");
                                setResult(UpdateByDoc.this.RESULT_OK,returnIntent);
                                finish();
                                //Your code goes here
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();






            /*activity.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        JSONObject jsonObject=new JSONObject(response_str);
                        String message=jsonObject.getString("p_message");

                        if (message.equals("Login id Already Exit!")){
                            JSONObject jsonObject1=jsonObject.getJSONObject("0");

                            fbem=jsonObject1.getString("p_login_id");
                            fb_passwo=jsonObject1.getString("p_login_pass");
                            Log.d("shub",fbem+fb_passwo);
                            new CallServicesforFacebook().execute(ApiConfig.LOGIN_URL);
                        }
                        else {
                            fbem = jsonObject.getString("p_login_id");
                            fb_passwo = jsonObject.getString("p_login_pass");
                            Log.d("shub",fbem+fb_passwo);
                            new CallServicesforFacebook().execute(ApiConfig.LOGIN_URL);
                        }
                    }
                    catch (Exception e){
//                e.printStackTrace();
                        Log.e("error",e.toString());
                    }
                }
            });*/
//            pd.hide();


//            progressBar.setVisibility(View.GONE);

                    if(resEntity!=null)

                    {

                        Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG).show();
                            /*Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", "okay");
                            setResult(Activity.RESULT_OK, returnIntent);*/

//            doSignInwithfb(response_str);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    System.out.println("<><><>res" + response_str);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    else

                    {
                        Log.i("RESPONSE", "file4");
                    }
                }

                catch (Exception ex) {
                    Log.e("Debug", "error: " + ex.getMessage(), ex);
                    Toast.makeText(UpdateByDoc.this, "File is corrupted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE) {
            Log.d("sun","on activity result");
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                selectedImagePath = getPath(
                        getApplicationContext(), selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if(selectedImagePath!=null&&selectedImagePath!=""){
                    String image_path_string=selectedImagePath;
                    Log.d("sunil", selectedImagePath);

                    Bitmap bmImg = BitmapFactory.decodeFile(image_path_string);
                    SharedPreferences sp=getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("profile_pic",image_path_string);
                    editor.commit();
                    img_profile.setImageBitmap(bmImg);
                }
                else{
                    Toast.makeText(getApplicationContext(),"File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path ="+selectedImagePath);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }


    /*private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(UpdateByDoc.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ApiConfig.UPDATE_DOCTOR_INFO);

            try {
                MultipartEntity entity = new MultipartEntity(


                        new MultipartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });
                File sourceFile = new File(select);

                // Adding file data to http body
                entity.addPart("image",new FileBody(sourceFile));//new FileBody(sourceFile)

                // Extra parameters pass to server

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else {
            Log.e("f,f", "F");

//            doFileUpload2();
//                    new CallServices().execute(ApiConfig.UPDATE_PATIENT);
        }
        return super.onOptionsItemSelected(item);
    }

    public void SendToUpdateFragment(ClinicInfoPOJOResult pojoResult){
        UpdateDoctorFragment addi= (UpdateDoctorFragment) adapter.frag1;
        addi.getPojo(pojoResult);
        viewPager.setCurrentItem(1);
        btn_Submit.setVisibility(View.VISIBLE);
        Log.d(TAG,"pojo:-"+pojoResult.toString());

    }
    private final String TAG=getClass().getSimpleName();
}
