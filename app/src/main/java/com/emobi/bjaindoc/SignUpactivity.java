package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.emobi.bjaindoc.utls.ToastClass;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import database.PreferenceData;

//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

/**
 * Created by Emobi-Android-002 on 8/11/2016.
 */
public class SignUpactivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, WebServicesCallBack {
    EditText ed1, ed2, ed3, ed4, ed5, ed6;
    public static final String REGISTER_URL = "http://www.bjain.com/doctor/registration.php";
    TextView sign, subm;
    private Button buttonChoose;
    private Button buttonUpload;


    private EditText editTextName;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private Bitmap bmp, bitmap;
    ImageView img_profile;
    private int PICK_IMAGE_REQUEST = 1;
    LinearLayout dobimage, btn_Submit;
    private volatile boolean running = true;
    public static String reg_id, reg_name, reg_email, reg_pass, dob;
    String file, selectedImagePath="";
    private View mViewPatient, mViewBranchAdmin, mViewTherapist;
    String date;
    ProgressDialog pd;
    HttpURLConnection conn = null;
    Calendar myCalendar;
    SharedPreferences settings;
    ProgressBar progressBar;
    String email_id, passwor, device_token;
    ActionBar actionBar;
    ImageView backarrow;
    TextView toolbar_title;
    HttpEntity resEntity;
    String response_str;
    Activity activity;
    SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup_details);
        activity = this;
        settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
        editor = settings.edit();
        /*actionBar=getSupportActionBar();
        SpannableString s = new SpannableString("Sign Up");
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
        actionBar.setTitle("Sign Up");
        actionBar.setTitle(s);*/

        img_profile = (ImageView) findViewById(R.id.img_profile);
        btn_Submit = (LinearLayout) findViewById(R.id.btn_submit);
        subm = (TextView) findViewById(R.id.subm);
        sign = (TextView) findViewById(R.id.sign);
        ed1 = (EditText) findViewById(R.id.edtxt_fname);
        ed2 = (EditText) findViewById(R.id.edtxt_email);
        ed3 = (EditText) findViewById(R.id.edtxt_mobile);
        ed4 = (EditText) findViewById(R.id.edtxt_password);
        ed5 = (EditText) findViewById(R.id.edtxt_confpassword);
        ed6 = (EditText) findViewById(R.id.edtxt_pmob);
        backarrow = (ImageView) findViewById(R.id.backarrow);
        dobimage = (LinearLayout) findViewById(R.id.dob);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        subm.setTypeface(tf);
        ed1.setTypeface(tf);
        ed2.setTypeface(tf);
        ed3.setTypeface(tf);
        ed4.setTypeface(tf);

        ed5.setTypeface(tf);
        ed6.setTypeface(tf);
        sign.setTypeface(tf);

        /*ed1.setText(LoginActivity.reg_name);
        ed2.setText(LoginActivity.reg_email);
        ed3.setText(LoginActivity.reg_pass);
        ed4.setText(LoginActivity.reg_cpass);*/
//        mViewTherapist = (View) findViewById(R.id.layout_therapist);
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValid()) {

                    return;
                } else {
                    if (isValidEmailAddress(ed2.getText().toString())) {
                        if (ed4.getText().toString().equals(ed5.getText().toString())) {
//                            if (selectedImagePath != null) {
                                callRegisterAPI();
//                            } else {
//                                Animation shake1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
//                                Toast.makeText(getApplicationContext(),
//                                        "Please, upload your photo", Toast.LENGTH_LONG).show();
//                                img_profile.startAnimation(shake1);
//
//                            }
//                    new CallServices().execute(ApiConfig.REGISTER_URL);
//                    executeMultipartPost();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter the correct confirm password", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        ToastClass.ShowLongToast(getApplicationContext(),"Please Enter Valid Email id");
                    }
                }
            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_title.setTypeface(tf);
        ed3.setFocusable(false);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        dobimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onDateSet();
                new DatePickerDialog(SignUpactivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//             new CallServices().execute(ApiConfig.REGISTER_URL);
        /*ed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SignUpactivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });*/
    }


    private void updateLabel() {

//        String myFormat = "MM/dd/yy"; //In which you need put here
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Date d = new Date();
        try {
            String now_date = sdf.format(d);
            String clicked_date = sdf.format(myCalendar.getTime());
            Date clicked = sdf.parse(clicked_date);
            if (d.before(clicked)) {
                ToastClass.ShowLongToast(getApplicationContext(), "Invalid date");
            } else {
                if (now_date.equals(clicked_date)) {
                    ToastClass.ShowShortToast(getApplicationContext(), "You cannot set today's date");
                } else {
                    ed3.setText(sdf.format(myCalendar.getTime()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            ed3.setText(sdf.format(myCalendar.getTime()));
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void executeMultipartPost() throws Exception {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 75, bos);
            byte[] data = bos.toByteArray();
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(
                    ApiConfig.REGISTER_URL);
            ByteArrayBody bab = new ByteArrayBody(data, "forest.jpg");
            // File file= new File("/mnt/sdcard/forest.png");
            // FileBody bin = new FileBody(file);
            MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("photo", bab);
//            reqEntity.addPart("photoCaption", new StringBody("sfsdfsdf"));
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            System.out.println("Response: " + s);
        } catch (Exception e) {
            // handle exception here
            Log.e(e.getClass().getName(), e.getMessage());
        }
    }

    /*public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
          date = year+"/"+dayOfMonth+"/"+(monthOfYear+1);
        ed3.setText(date);
    }*/

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        date = year + "/" + dayOfMonth + "/" + (monthOfYear + 1);
        date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        Log.d(TAG, "date selected:-" + date);
        ed3.setText(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class CallServices extends AsyncTask<String, String, String> {
        /*String mEmail=mEmailView.getText().toString().trim();
        String pass=mPasswordView.getText().toString().trim();*/
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(SignUpactivity.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("reg_email", email_id));
            namevaluepair.add(new BasicNameValuePair("reg_pass", passwor));
            namevaluepair.add(new BasicNameValuePair("device_token", device_token));

            //namevaluepair.add(new BasicNameValuePair("cat", "HAIR"));

            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

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
                    String message = objresponse.getString("d_message");
                    if (message.equalsIgnoreCase("invalid")) {
                        UtilsValidate.showError(SignUpactivity.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    } else {
//                        d_devicetoken=objresponse.getString("device_token");
                        reg_id = objresponse.getString("reg_id");
                        reg_name = objresponse.getString("reg_name");
                        reg_email = objresponse.getString("reg_email");
                        reg_pass = objresponse.getString("reg_pass");
                        dob = objresponse.getString("photo");
                        String confirm_status = objresponse.optString("status");
                        editor.putBoolean("locked", true);
                        editor.commit();
                        PreferenceData.setName(getApplicationContext(), reg_name);
                        String name = PreferenceData.getName(getApplicationContext());
                        PreferenceData.setId(getApplicationContext(), reg_id);
                        PreferenceData.setEmail(getApplicationContext(), reg_email);
                        PreferenceData.setPass(getApplicationContext(), reg_pass);
                        PreferenceData.setPhoto(getApplicationContext(), dob);
                        PreferenceData.setDoctorNumber(getApplicationContext(), objresponse.getString("reg_mob"));

                        Log.e("dob", dob);
//                        if (confirm_status.equals("0")) {
//                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.ACCOUNT_CONFIRM,false);
//                            showConfirmDialog();
//                        } else {
//                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.ACCOUNT_CONFIRM,true);
                        Intent intent = new Intent(SignUpactivity.this, DoctorAccount.class);
                        startActivity(intent);
                        finishAffinity();
//                        }
                    }
                    Log.e("result", result.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showConfirmDialog() {
        final Dialog dialog = new Dialog(SignUpactivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_confirm_email);

        LinearLayout ll_resend_link = (LinearLayout) dialog.findViewById(R.id.ll_resend_link);
        LinearLayout ll_email_confirm = (LinearLayout) dialog.findViewById(R.id.ll_email_confirm);
        ll_resend_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
                new WebServiceBase(nameValuePairs, SignUpactivity.this, RESEND_LINK).execute(ApiConfig.NEW_SEND_MESSAGE);
            }
        });
        ll_email_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private static final String RESEND_LINK = "resend_link";

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case RESEND_LINK:
                parseResendLinkData(response);
                break;
        }
    }

    public void parseResendLinkData(String response) {
        Log.d(TAG, "response:-" + response);
        try {
            JSONObject object = new JSONObject(response);
            if (object.optString("success").equals("true")) {
                Intent intent = new Intent(SignUpactivity.this, DoctorAccount.class);
                startActivity(intent);
                finishAffinity();
            }
        } catch (Exception e) {
            ToastClass.ShowLongToast(getApplicationContext(), "Server is temporary down");
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            Log.e("filePath", filePath.toString());
//            selectedImagePath=filePath.getPath();
            getRealPathFromUri(getApplicationContext(), filePath);

            file = filePath.getPath();

            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                img_profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean isValid() {

        String cName = ed1.getText().toString().trim();
        String email = ed2.getText().toString().trim();
        String mobile = ed6.getText().toString().trim();
        String dob1 = ed3.getText().toString().trim();
        String c_password = ed5.getText().toString().trim();
        String password = ed4.getText().toString().trim();
        if (cName.isEmpty()) {
            ed1.requestFocus();
            UtilsValidate.showError(SignUpactivity.this, getResources().getString(R.string.error), getResources().getString(R.string.err_name));
            return false;
        } else if (email.isEmpty()) {
            ed2.requestFocus();
            UtilsValidate.showError(SignUpactivity.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
            return false;
        } else if (mobile.isEmpty()) {
            ed6.requestFocus();
            UtilsValidate.showError(SignUpactivity.this, getResources().getString(R.string.error),
                    getResources().getString(R.string.err_phone));
            return false;
        } else if (dob1.isEmpty()) {
            ed3.requestFocus();
            UtilsValidate.showError(SignUpactivity.this, getResources().getString(R.string.error),
                    getResources().getString(R.string.err_dob));
            return false;
        } else if (password.isEmpty()) {
            ed4.requestFocus();
            UtilsValidate.showError(SignUpactivity.this, getResources().getString(R.string.error),
                    getResources().getString(R.string.err_pass));
            return false;
        } else if (c_password.isEmpty()) {
            ed5.requestFocus();
            UtilsValidate.showError(SignUpactivity.this, getResources().getString(R.string.error),
                    getResources().getString(R.string.err_pass));
            return false;
        }

        return true;
    }


    private void getRealPathFromUri(Context ctx, Uri uri) {

        String[] filePathColumn = {MediaStore.Files.FileColumns.DATA};

        Cursor cursor = ctx.getContentResolver().query(uri, filePathColumn,
                null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        selectedImagePath = cursor.getString(columnIndex);
        Log.e("picturePath", "picturePath : " + selectedImagePath);
        cursor.close();
    }

    public void callRegisterAPI() {
        final String name = ed1.getText().toString().trim();
        final String email = ed2.getText().toString().trim();
        final String dob = ed3.getText().toString();
        //            final String image = selectedImagePath;
        final String password = ed4.getText().toString().trim();
        final String confirm_password = ed5.getText().toString().trim();
        final String phone_number = ed6.getText().toString().trim();

        new RegisterClass(SignUpactivity.this, name, email, dob, password, confirm_password, phone_number).execute();
    }

    private final String TAG = getClass().getSimpleName();


    boolean bol = true;

    class RegisterClass extends AsyncTask<Void, Void, Void> {
        Activity activity;
        String name;
        String email;
        String dob;
        String password;
        String confirm_password;
        String phone_number;
        String result = "";
        ProgressDialog progressDialog;
        public RegisterClass asyncObject;

        public RegisterClass(Activity activity, String name, String email, String dob, String password,
                             String confirm_password, String phone_number) {
            this.activity = activity;
            this.name = name;
            this.email = email;
            this.dob = dob;
            this.password = password;
            this.confirm_password = confirm_password;
            this.phone_number = phone_number;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            asyncObject = this;
            new CountDownTimer(120000, 120000) {
                public void onTick(long millisUntilFinished) {
                    // You can monitor the progress here as well by changing the onTick() time
                }

                public void onFinish() {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    if (asyncObject.getStatus() == AsyncTask.Status.RUNNING) {
                        asyncObject.cancel(false);
                        Toast.makeText(activity, "Please Check Your Internet Connectivity", Toast.LENGTH_LONG).show();
                    }
                }
            }.start();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://www.bjain.com/doctor/newregistration.php");
                MultipartEntity reqEntity = new MultipartEntity();
                File file1 = new File(selectedImagePath);
                Log.e("file1", selectedImagePath);
                if(file1.exists()) {
                    FileBody bin1 = new FileBody(file1);
                    reqEntity.addPart("photo", bin1);
                }else{
                    reqEntity.addPart("photo", new StringBody(""));
                }
                reqEntity.addPart("reg_email", new StringBody(email));
                reqEntity.addPart("reg_name", new StringBody(name));
                reqEntity.addPart("reg_pass", new StringBody(confirm_password));
                reqEntity.addPart("reg_cpass", new StringBody(confirm_password));
                reqEntity.addPart("reg_dob", new StringBody(dob));
                reqEntity.addPart("reg_mob", new StringBody(phone_number));
                reqEntity.addPart("device_token", new StringBody(PreferenceData.getDevice_Token(getApplicationContext())));
                post.setEntity(reqEntity);
                HttpResponse response = client.execute(post);
                resEntity = response.getEntity();
                response_str = EntityUtils.toString(resEntity);

                result = response_str;
            } catch (Exception e) {
                result = "";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            Log.d(TAG, "register response:-" + result);
            if (result != null && result.length() > 0) {

                try {
                    JSONObject object = new JSONObject(result);
                    String Success = object.optString("Success");
                    if (Success.equals("true")) {
                        JSONObject Result = object.optJSONObject("Result");
                        email_id = Result.getString("reg_email");
                        passwor = Result.getString("reg_cpass");
                        device_token = Result.getString("device_token");
                        new CallServices().execute(ApiConfig.LOGIN_URL);
                    } else {
                        String Message = object.optString("Message");
                        if (Message.equals("Already Added Record")) {
                            ToastClass.ShowLongToast(getApplicationContext(), "This Email Address Already Exist");
                        } else {
                            ToastClass.ShowLongToast(getApplicationContext(), Message);
                        }
                    }
                } catch (Exception e) {
                    ToastClass.ShowLongToast(getApplicationContext(), "Server is Temporary down");
                }
            }
        }
    }
}
