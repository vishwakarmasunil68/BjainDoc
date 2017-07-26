package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.pojo.member.GetAllMembers;
import com.emobi.bjaindoc.pojo.member.MemberResultPOJO;
import com.emobi.bjaindoc.pojo.patient.PatientResultPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.PreferenceData;

public class PrescriptionActivity extends AppCompatActivity implements WebServicesCallBack {
    //    TextView description_tv;

    TextView d_name_et, d_age_et, textDoc, d_name, d_age, d_sex, d_sex_et, d_speicality_et, d_speicality;
    TextView name_et, age_et, btn_Submit, prescription_et;
    TextView Title;
    TextView no_medicine_tv;
    TextView name, age, addM;
    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
    String pres;
    LinearLayout medicine_layout, ll, l18;
    ProgressDialog pd;
    PrecriptionPOJO pojo = Prescription.pojo;
    Button proceed;
    FrameLayout medicine_set, print_frame;
    int length = 1;
    ArrayList<TextView> arrayList = new ArrayList<>();
    HttpEntity resEntity;
    String response_str;
    Activity activity;
    ActionBar actionBar;
    ImageView whitearrow;
    Toolbar toolbar;
    String selectedImagePath;
    String file_name;
    PatientResultPOJO patientResultPOJO;

    @BindView(R.id.spinner_members)
    Spinner spinner_members;
    @BindView(R.id.scroll_presc)
    ScrollView scroll_presc;

    MemberResultPOJO memberResultPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        ButterKnife.bind(this);
        activity = this;
        patientResultPOJO = (PatientResultPOJO) getIntent().getSerializableExtra("patient");
        if (patientResultPOJO != null) {
            memberResultPOJO= (MemberResultPOJO) getIntent().getSerializableExtra("member");
        } else {
            finish();
        }

        medicine_layout = (LinearLayout) findViewById(R.id.medicine_layout);
        l18 = (LinearLayout) findViewById(R.id.l18);
        ll = (LinearLayout) findViewById(R.id.ll);
        print_frame = (FrameLayout) findViewById(R.id.print_frame);
        medicine_set = (FrameLayout) findViewById(R.id.medicine_set);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        d_name_et = (TextView) findViewById(R.id.d_name_et);
        d_name = (TextView) findViewById(R.id.d_name);
        d_age_et = (TextView) findViewById(R.id.d_age_et);
        d_age = (TextView) findViewById(R.id.d_phone);
        d_sex_et = (TextView) findViewById(R.id.d_sex_et);
        d_sex = (TextView) findViewById(R.id.d_address);
        d_speicality_et = (TextView) findViewById(R.id.d_speicality_et);
        d_speicality = (TextView) findViewById(R.id.d_special);

        name_et = (TextView) findViewById(R.id.name_et);
        age_et = (TextView) findViewById(R.id.age_et);
        name = (TextView) findViewById(R.id.tv_profile_name);
        textDoc = (TextView) findViewById(R.id.textDoc);
        age = (TextView) findViewById(R.id.age);
        addM = (TextView) findViewById(R.id.addM);

        prescription_et = (EditText) findViewById(R.id.prescription_et);
        Title = (TextView) findViewById(R.id.Title);
        proceed = (Button) findViewById(R.id.proceed);
        btn_Submit = (TextView) findViewById(R.id.btn_submit);
        no_medicine_tv = (TextView) findViewById(R.id.no_medicine_tv);
        whitearrow = (ImageView) findViewById(R.id.whitearrow);
        whitearrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        SpannableString s = new SpannableString("Personal Details");
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

        d_name_et.setText(PreferenceData.getName(getApplicationContext()));
        d_age_et.setText(PreferenceData.getDoctorNumber(getApplicationContext()));
        d_sex_et.setText(PreferenceData.getDoctorclinic_address(getApplicationContext()));
        d_speicality_et.setText(PreferenceData.getSpecialist(getApplicationContext()));

        Log.e("data", PreferenceData.getName(getApplicationContext()));
        Log.e("data", PreferenceData.getDoctorNumber(getApplicationContext()));
        Log.e("data", PreferenceData.getDoctorclinic_address(getApplicationContext()));
        Log.e("data", PreferenceData.getSpecialist(getApplicationContext()));
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Regular.ttf");
        d_name_et.setTypeface(tf);

        Title.setText(patientResultPOJO.getP_name());
        Title.setTypeface(tf);

        textDoc.setTypeface(tf);

        name_et.setTypeface(tf);
        age_et.setTypeface(tf);
        name.setTypeface(tf);
        age.setTypeface(tf);
        addM.setTypeface(tf);

        d_name.setTypeface(tf);
        d_age.setTypeface(tf);
        d_sex.setTypeface(tf);
        d_speicality.setTypeface(tf);
        no_medicine_tv.setTypeface(tf);
        name_et.setText(patientResultPOJO.getP_name());
        age_et.setText(patientResultPOJO.getP_age());


        GetAllMembers();
        medicine_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1(medicine_layout);
                addM.setVisibility(View.GONE);

            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prescription_et.setCursorVisible(false);
                l18.setVisibility(View.GONE);
                medicine_set.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                print_frame.setDrawingCacheEnabled(true);
//                print();
//                getBitmapByView(scroll_presc);
                SaveBitmapPDF1();
//                showDialog1(medicine_layout);
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "";
                for (int i = 0; i < arrayList.size(); i++) {
                    TextView tv = arrayList.get(i);
                    if ((i + 1) == arrayList.size()) {
                        s += tv.getText().toString();
                    } else {
                        s += tv.getText().toString() + "::";
                    }
                }

                Intent intent = new Intent(PrescriptionActivity.this, ProceedCheckOut.class);
                intent.putExtra("name", name_et.getText().toString());
                intent.putExtra("age", age_et.getText().toString());
//                intent.putExtra("sex",sex_et.getText().toString());
                intent.putExtra("prescription", prescription_et.getText().toString());
                intent.putExtra("medicine", s);
                startActivityForResult(intent, 1);
            }
        });
//        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private final String TAG = getClass().getSimpleName();
    private final static String GET_ALL_MEMBERS_API = "get_all_member_api";

    public void GetAllMembers() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("p_id", patientResultPOJO.getP_id()));
        new WebServiceBase(nameValuePairs, this, GET_ALL_MEMBERS_API, false).execute(ApiConfig.GET_ALL_MEMBERS_URL);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_MEMBERS_API:
                parseMemberData(response);
                break;
        }
    }

    public Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        //get the actual height of scrollview
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.color.white);
        }
        // create bitmap with target size
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
//        FileOutputStream out = null;
//        try {
//            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "bjain");
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            out = new FileOutputStream(file.toString() + File.separator + System.currentTimeMillis() + ".png");
////        Toast.makeText(c,"")
//        } catch (Exception e) {
//            Log.e("fileerror1", e.toString());
//            e.printStackTrace();
//        }
//        try {
//            if (null != out) {
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                out.flush();
//                out.close();
//                Toast.makeText(getApplicationContext(), "File saved to gallery", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            Log.e("fileerror4", e.toString());
//            Toast.makeText(getApplicationContext(), "File not saved to gallery", Toast.LENGTH_SHORT).show();
//            // TODO: handle exception
//        }
        return bitmap;
    }

    private void SaveBitmapPDF1() {
        new AsyncTask<Void, Void, Void>() {
            File f;
            ProgressDialog pd;
            //            ArrayList<Bitmap> list;
            Bitmap bitmap;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = new ProgressDialog(PrescriptionActivity.this);
                pd.setMessage("Please Wait...");
                pd.setCancelable(false);
                pd.show();

                bitmap = getBitmapByView(scroll_presc);
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    f = new File(Environment.getExternalStorageDirectory() + File.separator + "bjain" + File.separator + System.currentTimeMillis() + ".pdf");
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(f));
                    document.open();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);


                    Image image = Image.getInstance(stream.toByteArray());
                    float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / image.getWidth()) * 100;
                    image.scalePercent(scaler);
                    document.add(image);
                    document.close();
                } catch (Exception e) {
                    pd.dismiss();
                    Log.d(TAG, e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (pd != null) {
                    pd.dismiss();
                }
                if (f != null) {
                    if (f.exists()) {
                        String presc="";
                        Log.d(TAG,"prescription:-"+presc);
                        if(prescription_et.getText().toString().length()>0){
                            presc=prescription_et.getText().toString();
                        }
                        else{
                            presc="Prescription";
                        }
                        new FileUpload(presc,f.toString()).execute();
                        l18.setVisibility(View.VISIBLE);
                        medicine_set.setVisibility(View.VISIBLE);
//                        toolbar.setVisibility(View.VISIBLE);
                    }
                }
            }
        }.execute();

    }

    List<MemberResultPOJO> list_member_pojos;
    int initial_member = 0;

    public void parseMemberData(String response) {
        Log.d(TAG, "response:-" + response);
        try {
            Gson gson = new Gson();
            GetAllMembers getAllMembers = gson.fromJson(response, GetAllMembers.class);
            if (getAllMembers.getSuccess().equals("true")) {
                final List<MemberResultPOJO> list_members = getAllMembers.getMemberResultPOJOList();
                list_member_pojos = list_members;
//                setAllMembers(list_members);
                if (list_members.size() > 0) {
//                    spinner_members.setVisibility(View.VISIBLE);
                    List<String> list_string = new ArrayList<>();
                    list_string.add("select member");
                    for (MemberResultPOJO pojos : list_members) {
                        list_string.add(pojos.getM_name());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_string); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_members.setAdapter(spinnerArrayAdapter);

                    spinner_members.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i != 0) {
                                initial_member = i - 1;
                                MemberResultPOJO memberResultPOJO = list_members.get(i - 1);
                                name_et.setText(memberResultPOJO.getM_name());
                                age_et.setText(memberResultPOJO.getM_age());
                            } else {
                                initial_member = 0;
                                name_et.setText(patientResultPOJO.getP_name());
                                age_et.setText(patientResultPOJO.getP_age());
                            }
                            Log.d(TAG, "initial Member:-" + initial_member);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else {
                    spinner_members.setVisibility(View.GONE);
                }

//                Pref.SetStringPref(getApplicationContext(), StringUtils.ALL_MEMBER_DATA, response);
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                if (result.equals("ok")) {
                    finish();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    public void showDialog(final TextView tv) {

        final Dialog dialog = new Dialog(PrescriptionActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.setContentView(R.layout.prescription_dialog);
        dialog.setTitle("setDetails");


        Button ok = (Button) dialog.findViewById(R.id.ok);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        final EditText edit = (EditText) dialog.findViewById(R.id.edittext);
        if (!tv.getText().toString().equals("Please Enter Prescription here"))
            edit.setText(tv.getText().toString());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(edit.getText().toString());
                dialog.dismiss();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                no_medicine_tv.setVisibility(View.GONE);
            }
        });

        dialog.show();
    }


    public void showDialog1(final LinearLayout ll) {

        final Dialog dialog = new Dialog(PrescriptionActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.setContentView(R.layout.prescription_dialog);
        dialog.setTitle("add medicine");


        Button ok = (Button) dialog.findViewById(R.id.ok);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        final EditText edit = (EditText) dialog.findViewById(R.id.edittext);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                final TextView tv = new TextView(PrescriptionActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(params);
                tv.setText(length++ + ". " + edit.getText().toString());
                ll.addView(tv);
//                ll.setTag(position);
                tv.setTag(length);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg));
                        showDialog2(tv);
                        String tvnew = tv.getText().toString();

                    }
                });
                arrayList.add(tv);

                dialog.dismiss();
                no_medicine_tv.setVisibility(View.GONE);
            }
        });

        dialog.show();
    }


    public void showDialog2(final TextView newtv) {

        final Dialog dialog = new Dialog(PrescriptionActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.setContentView(R.layout.prescription_dialog);
        dialog.setTitle("edit medicine");


        Button ok = (Button) dialog.findViewById(R.id.ok);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        final EditText edit = (EditText) dialog.findViewById(R.id.edittext);
        edit.setText(newtv.getText().toString());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtv.setText(edit.getText().toString());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                dialog.dismiss();
                no_medicine_tv.setVisibility(View.GONE);
            }
        });

        dialog.show();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.prep_menu, menu);
//        return true;
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pre_menu, menu);
        /*View view = new View(this);
        MenuItem item=menu.getItem(0);
        item.setActionView(view);
        view.setPadding(16,0,16,0);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.print) {
            prescription_et.setCursorVisible(false);
            l18.setVisibility(View.GONE);
            Title.setCursorVisible(false);
            medicine_set.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
            print_frame.setDrawingCacheEnabled(true);
            print();

        }
        return super.onOptionsItemSelected(item);
    }

    class FileUpload extends AsyncTask<Void, Void, Void> {
        File f;
        String id;
        String description;
        String medicine;
        String s = "";
        String m_id = "";
        String m_name = "";
        String m_age = "";
        String file_path="";
        public FileUpload(String description,String file_path) {
            this.description = description;
            this.file_path=file_path;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            try {
//                m_id = list_member_pojos.get(initial_member).getM_id();
//                m_name = list_member_pojos.get(initial_member).getM_name();
//                m_age = list_member_pojos.get(initial_member).getM_age();
//            } catch (Exception e) {
//                Log.d(TAG, e.toString());
//            }
            if(memberResultPOJO!=null){
                m_id=memberResultPOJO.getM_id();
                m_name=memberResultPOJO.getM_name();
                m_age=memberResultPOJO.getM_age();
            }
            else{
                m_id="";
                m_name="";
                m_age="";
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://www.bjain.com/doctor/prescription.php");
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

//                File file1 = new File(f.toString());
//                File file1 = new File(Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + file_name + ".pdf");
                FileBody bin1 = new FileBody(new File(file_path));

                Log.d("sunil", "pre_p_id:-" + patientResultPOJO.getP_id());
                Log.d("sunil", "pre_doc_id:-" + PreferenceData.getId(getApplicationContext()));
                Log.d("sunil", "pre_date:-" + UtilsValidate.getCurrentDate());
                Log.d("sunil", "pre_message:-" + description);
                Log.d(TAG, "index:-" + initial_member);
                Log.d(TAG, "id:-" + m_id);
                Log.d(TAG, "name:-" + m_name);
                Log.d(TAG, "age:-" + m_age);

                reqEntity.addPart("pre_p_id", new StringBody(patientResultPOJO.getP_id()));
                reqEntity.addPart("pre_doc_id", new StringBody(PreferenceData.getId(getApplicationContext())));
                reqEntity.addPart("pre_date", new StringBody(UtilsValidate.getCurrentDate()));
                reqEntity.addPart("pre_time", new StringBody(UtilsValidate.getCurrentTime()));
                reqEntity.addPart("m_id", new StringBody(m_id));
                reqEntity.addPart("m_name", new StringBody(m_name));
                reqEntity.addPart("m_age", new StringBody(m_age));
                reqEntity.addPart("pre_message", new StringBody(description));
                reqEntity.addPart("pre_medicine", new StringBody("Medicine"));
                reqEntity.addPart("pre_file", bin1);
                post.setEntity(reqEntity);
                HttpResponse response = client.execute(post);
                HttpEntity resEntity = response.getEntity();
                String response_str = EntityUtils.toString(resEntity);
                Log.e(TAG, "response presc:-" + response_str);
                file_uploaded=true;
                if (resEntity != null) {
                    file_uploaded=true;
                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.v("sunil", "Response: " + responseStr);
                    s = responseStr;
                    Toast.makeText(getApplicationContext(), "Send successfully", Toast.LENGTH_LONG).show();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", "okay");
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            file_uploaded=true;
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }

            finish();
            Log.e("sunil", "response:-" + s);
        }
    }
    boolean file_uploaded=false;
    @Override
    protected void onResume() {
        try {
            pres = prescription_et.getText().toString();
        } catch (Exception e) {
            pres = "no any prescription";
        }
        if (file_name != null && file_name.length() > 0) {

            Log.d(TAG, "onResume with file_name");
            medicine_set.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
//            new FileUpload(pres).execute();
        } else {
            Log.d(TAG, "onResume without file_name");
        }

        super.onResume();
    }

    private void print() {
        // Get the print manager.
        PrintHelper printHelper = new PrintHelper(activity);
        // Set the desired scale mode.
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        // Get the bitmap for the ImageView's drawable.
        Bitmap bitmap = overlay(print_frame);
        // Print the bitmap.
        String time = System.currentTimeMillis() + "";
        try {
            file_name = Title.getText().toString();
            printHelper.printBitmap(file_name, bitmap);
        } catch (Exception e) {
            file_name = "Report";
            printHelper.printBitmap(file_name, bitmap);
        }


    }

    public Bitmap overlay(FrameLayout frame_layout) {
        Bitmap bitmap = Bitmap.createBitmap(frame_layout.getWidth(), frame_layout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        frame_layout.draw(canvas);
//		image.setImageBitmap(bitmap);
        frame_layout.setDrawingCacheEnabled(true);
        frame_layout.getDrawingCache();
        return frame_layout.getDrawingCache();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("shubham", "onPausefilename");
    }

    @Override
    public void onBackPressed() {
        if(file_uploaded) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "okay");
            setResult(Activity.RESULT_OK, returnIntent);
        }else{
            finish();
        }
        super.onBackPressed();

    }
    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==R.id.print){
//            Log.d("sunil",description_tv.getText().toString());
//            String s="";
//            for(int i=0;i<arrayList.size();i++){
//                TextView tv=arrayList.get(i);
//                if((i+1)==arrayList.size()) {
//                    s += tv.getText().toString();
//                }
//                else{
//                    s += tv.getText().toString() + "::";
//                }
//                final String id=PreferenceData.getId(PrescriptionActivity.this);
//                final String file_path= Environment.getExternalStorageDirectory()+File.separator+"Download"+File.separator+"small1.pdf";
////                new CallServices(description_tv.getText().toString(),s,id).execute("http://www.bjain.com/doctor/prescription.php");
////
////                Uri filePath = Uri.parse(file_path);
////                Log.e("filePath", filePath.toString());
//////            selectedImagePath=filePath.getPath();
//                getRealPathFromUri(getApplicationContext(), filePath);
//
////               final String file=filePath.getPath();
//
//                Log.e("sunil",new File(file_path).exists()+"");
//                final String finalS = s;
//                new FileUpload(file_path,id,description_tv.getText().toString(),finalS).execute();
////                Thread thread2 = new Thread(new Runnable() {
////                    public void run() {
////                        doFileUpload2(file_path,id,description_tv.getText().toString(), finalS);
////                        runOnUiThread(new Runnable() {
////                            public void run() {
////
////
////                            }
////                        });
////                    }
////                });
////                thread2.start();
//
//            }
//            Log.d("sunil",s);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//    private void getRealPathFromUri(Context ctx, Uri uri) {
//
//        String[] filePathColumn = { MediaStore.Files.FileColumns.DATA };
//
//        Cursor cursor = ctx.getContentResolver().query(uri, filePathColumn,
//                null, null, null);
//        cursor.moveToFirst();
//        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//        selectedImagePath = cursor.getString(columnIndex);
//        Log.e("picturePath", "picturePath : " + selectedImagePath);
//        cursor.close();
//
//    }
}
//
//    class FileUpload extends AsyncTask<Void,Void,Void>{
//        File f;
//        String id;
//        String description;
//        String medicine;
//        String s="";
//        public FileUpload(String file_path,String id,String description,String medicine){
//            f=new File(file_path);
//            this.id=id;
//            this.description=description;
//            this.medicine=medicine;
//        }
//        @Override
//        protected Void doInBackground(Void... params) {
//            try
//            {
//                HttpClient client = new DefaultHttpClient();
//                HttpPost post = new HttpPost("http://www.bjain.com/doctor/prescription.php");
//                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//                File file1 = new File(f.toString());
//                FileBody bin1 = new FileBody(file1);
//
//                Log.d("sunil","pre_p_id:-"+Prescription.id);
//                Log.d("sunil","pre_doc_id:-"+id);
//                Log.d("sunil","pre_date:-"+UtilsValidate.getCurrentDate());
//                Log.d("sunil","pre_message:-"+description);
//                Log.d("sunil","pre_medicine:-"+medicine);
//
//                reqEntity.addPart("pre_p_id", new StringBody(Prescription.id));
//                reqEntity.addPart("pre_doc_id", new StringBody(id));
//                reqEntity.addPart("pre_date", new StringBody(UtilsValidate.getCurrentDate()));
//                reqEntity.addPart("pre_message", new StringBody(description));
//                reqEntity.addPart("pre_medicine", new StringBody(medicine));
//                reqEntity.addPart("pre_file", bin1);
//                post.setEntity(reqEntity);
//                HttpResponse response = client.execute(post);
//                resEntity = response.getEntity();
//                response_str = EntityUtils.toString(resEntity);
//                Log.e("sunil", response_str);
//                if (resEntity != null) {
//
//                    String responseStr = EntityUtils.toString(resEntity).trim();
//                    Log.v("sunil", "Response: " +  responseStr);
//                    s=responseStr;
//                    // you can add an if statement here and do other actions based on the response
//                }
//            }
//            catch(Exception e)
//            {
//                Log.d("sunil",e.toString());
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Log.e("sunil","response:-"+s);
//        }
//    }
//
//    private void doFileUpload2(String file_path,String id,String description,String medicine) {
//        try {
//
//            HttpClient client = new DefaultHttpClient();
//            HttpPost post = new HttpPost("http://www.bjain.com/doctor/prescription.php");
//            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//            File file1 = new File(file_path);
//            FileBody bin1 = new FileBody(file1);
//
//            Log.d("sunil","pre_p_id:-"+Prescription.id);
//            Log.d("sunil","pre_doc_id:-"+id);
//            Log.d("sunil","pre_date:-"+UtilsValidate.getCurrentDate());
//            Log.d("sunil","pre_message:-"+description);
//            Log.d("sunil","pre_medicine:-"+medicine);
//
//            reqEntity.addPart("pre_p_id", new StringBody(Prescription.id));
//            reqEntity.addPart("pre_doc_id", new StringBody(id));
//            reqEntity.addPart("pre_date", new StringBody(UtilsValidate.getCurrentDate()));
//            reqEntity.addPart("pre_message", new StringBody(description));
//            reqEntity.addPart("pre_medicine", new StringBody(medicine));
//            reqEntity.addPart("pre_file", bin1);
//            post.setEntity(reqEntity);
//            HttpResponse response = client.execute(post);
//            resEntity = response.getEntity();
//            response_str = EntityUtils.toString(resEntity);
//            Log.e("sunil", response_str);
//
//
//            activity.runOnUiThread(new Runnable() {
//                public void run() {
//
//                    try {
//
////                        JSONObject jsonObject=new JSONObject(response_str);
//                        Log.e("sunil","response:-"+response_str);
////                        String success_message=jsonObject.getString("success");
//
//
//                    }
//                    catch (Exception e){
////                e.printStackTrace();
//                        Log.e("sunil",e.toString());
//                    }
//                }
//            });
//
////            pd.hide();
////            progressBar.setVisibility(View.GONE);
//
//
//            if (resEntity != null) {
//                Log.e("sunil", "err2:-"+response_str);
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        try {
//                            System.out.println("sunil" + response_str);
//                            Log.e("sunil", "err1:-" + response_str);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//            else{
//                Log.i("sunil", "file4");
//            }
//        } catch (Exception ex) {
//            Log.e("sunil", "error: " + ex.getMessage(), ex);
//        }
//    }
//
//
//}
