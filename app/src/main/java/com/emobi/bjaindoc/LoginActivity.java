package com.emobi.bjaindoc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.pojo.login.LoginPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.emobi.bjaindoc.utls.Pref;
import com.emobi.bjaindoc.utls.StringUtils;
import com.emobi.bjaindoc.utls.ToastClass;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.gson.Gson;
import com.util.ConnectionCheck;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database.PreferenceData;

import static android.Manifest.permission.READ_CONTACTS;
import static com.emobi.bjaindoc.ApiConfig.RESEND_LINK;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, WebServicesCallBack {
    public static String d_devicetoken, reg_id, reg_name, reg_email, reg_pass, reg_cpass, dob, reg_department, reg_clinic_address, reg_designation, reg_specialist, reg_degree, reg_photo;
    public static String emailid, text, profilepicimage, textname, bd;
    TextView btn_forgot_password;
    LinearLayout mEmailSignInButton;
    String file, selectedImagePath;
    URL imageURL;
    public static Bitmap b;
    String fbem;
    CallbackManager callbackManager;
    LoginButton mEmailSignInFBButton;
    ProfilePictureView profile;
    SharedPreferences settings;
    HttpEntity resEntity;
    TextView Sign_up_text;

    private static final int REQUEST_READ_CONTACTS = 0;
    public static String name;
    JSONArray jsonArray;

    TextView text_response, txt_patient_title;
    String response;
    private AutoCompleteTextView mEmailView;
    private EditText fname, mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    public static Boolean isAccepted;
    SharedPreferences.Editor editor;
    TextView sign, link;
    ActionBar actionBar;
    Activity activity;
    String fb_email_id, fb_passwo, fb_device_token;
    Button btn_id;
    ImageView image_cancel;
    ImageView backarrow;
    TextView toolbar_title;
    String type = "";
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        RequestData();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        setContentView(R.layout.activity_login);
        activity = this;


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");
            result = bundle.getString("result");
//            Log.d(TAG,"message:-"+msg);
        }

        btn_id = (Button) findViewById(R.id.btn_id);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        backarrow = (ImageView) findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
        sign = (TextView) findViewById(R.id.sign);
        link = (TextView) findViewById(R.id.link);

        editor = settings.edit();
        isAccepted = settings.getBoolean("locked", false);
        if (isAccepted) {
//            boolean bol = Pref.GetBooleanPref(getApplicationContext(), StringUtils.ACCOUNT_CONFIRM, false);
//            if (bol) {
                Intent intent = new Intent(LoginActivity.this, DoctorAccount.class);
                intent.putExtra("type", type);
                intent.putExtra("result", result);
                startActivity(intent);
                finish();
//            } else {
//                showConfirmDialog();
//            }
        } else {
//            webView.loadUrl("file:///android_asset/condition.html");
        }

        mEmailSignInFBButton = (LoginButton) findViewById(R.id.email_sign_in_Facebookbutton);
        profile = (ProfilePictureView) findViewById(R.id.picture);
        Sign_up_text = (TextView) findViewById(R.id.btn_register);
        final Animation animScale = AnimationUtils.loadAnimation(this,
                R.anim.anim_scale);

        link.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignWithPatient.class));
            }
        });
        Sign_up_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpactivity.class));
            }
        });

        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        mEmailView.setTypeface(tf);
        mPasswordView.setTypeface(tf);
        mEmailSignInButton = (LinearLayout) findViewById(R.id.email_sign_in_button);
        btn_forgot_password = (TextView) findViewById(R.id.btn_forgot_password);
        TextView subm = (TextView) findViewById(R.id.subm);
        subm.setTypeface(tf);
        btn_forgot_password.setTypeface(tf);
        sign.setTypeface(tf);

//         mEmailSignInFBButton = (LoginButton) findViewById(R.id.email_sign_in_Facebookbutton);

        btn_forgot_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(LoginActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialog_edit);
//                dialog.setTitle( Html.fromHtml("<font color='#FFFFFF'>Forgot Password</font>"));

                ImageView showImage = new ImageView(LoginActivity.this);
                //adding text dynamically
                final EditText text_email_id = (EditText) dialog.findViewById(R.id.text_email_id);

                text_response = (TextView) dialog.findViewById(R.id.text_response);


                ImageView imagecancel = (ImageView) dialog.findViewById(R.id.image_cancel);
                txt_patient_title = (TextView) dialog.findViewById(R.id.txt_patient_title);
                LinearLayout btn_submit = (LinearLayout) dialog.findViewById(R.id.btn_submit);

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        response = text_email_id.getText().toString();
                        new CallServicesFp().execute(ApiConfig.FORGET_PASSWORD);
                    }
                });

                imagecancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_title.setTypeface(tf);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectionCheck.isNetworkAvailable(getApplicationContext())) {
                    if (mEmailView.getText().toString().length() < 1) {
                        attemptLogin();

                    } else if ((mPasswordView.getText().toString().length() < 1)) {
                        mPasswordView.requestFocus();
                        UtilsValidate.showError(getApplicationContext(), getResources().getString(R.string.error), getResources().getString(R.string.err_pass));
                    } else {
                        new CallServices().execute(ApiConfig.LOGIN_URL);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connectivity", Toast.LENGTH_LONG).show();
                }
            }
        });
        if (AccessToken.getCurrentAccessToken() != null) {
            RequestData();
        }
        mEmailSignInFBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    profile.setProfileId(null);
                }
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        btn_id.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionCheck.isNetworkAvailable(getApplicationContext())) {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
                } else {
                    Toast.makeText(getApplicationContext(), "Please Check Your Internet Connectivity", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showConfirmDialog() {
        final Dialog dialog = new Dialog(LoginActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_confirm_email);

        LinearLayout ll_resend_link = (LinearLayout) dialog.findViewById(R.id.ll_resend_link);
        LinearLayout ll_email_confirm = (LinearLayout) dialog.findViewById(R.id.ll_email_confirm);
        TextView tv_confirm_text = (TextView) dialog.findViewById(R.id.tv_confirm_text);
        tv_confirm_text.setText("An Email Verification Link has already been sent to your Email.Please Confirm your account.");
        ll_resend_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
                new WebServiceBase(nameValuePairs, LoginActivity.this, RESEND_LINK).execute(ApiConfig.NEW_SEND_MESSAGE);
            }
        });
        ll_email_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

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
                ToastClass.ShowLongToast(getApplicationContext(), "Server is temporary down");
            }
        } catch (Exception e) {
            ToastClass.ShowLongToast(getApplicationContext(), "Server is temporary down");
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        /*if (mAuthTask != null) {
          String email_id=  mEmailView.getText().toString();
            String password=mPasswordView.getText().toString();*/
//            if (email_id!=null && password!=null){
//                shake();
//            }
//        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                Log.e("jsondata", json.toString());
                try {
                    Log.e("jsondata1", json.toString());
                    if (json != null) {
                        Log.e("jsondata2", json.toString());
                        text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") + "<br><br><b>Profile link :</b> " + json.getString("link");
                        profilepicimage = json.getString("link");
                        getFacebookProfilePicture(json.getString("id"));
                        textname = json.getString("name");
                        emailid = json.getString("email");
                        profile.setProfileId(json.getString("id"));
                        if (json.toString().contains("email")) {
                            Log.d(TAG, json.getString("email"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void doFileUpload2() {
        Log.i("RESPONSE", "file1");

        try {
            Log.i("RESPONSE", "file2");
            final String name = textname;
            final String email = emailid;
            final String dob = "";
//            final String image = selectedImagePath;
            final String password = "";
            final String confirm_password = "";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.bjain.com/doctor/registration.php");
            MultipartEntity reqEntity = new MultipartEntity();
            File file1 = new File(selectedImagePath);
            Log.e("file1", selectedImagePath);
            FileBody bin1 = new FileBody(file1);
            reqEntity.addPart("photo", bin1);
            reqEntity.addPart("reg_email", new StringBody(email));
            reqEntity.addPart("reg_name", new StringBody(name));
            reqEntity.addPart("reg_pass", new StringBody(password));
            reqEntity.addPart("reg_cpass", new StringBody(confirm_password));
            reqEntity.addPart("reg_dob", new StringBody(dob));
            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            Log.e("RESPONSE", response_str);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        //register response.
                        Log.d(TAG, "passing tag" + response_str);
                        JSONObject jsonObject = new JSONObject(response_str);
                        String message = jsonObject.getString("Success");

                        if (message.equals("false")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("Result");

                            fbem = jsonObject1.getString("reg_email");
                            fb_passwo = jsonObject1.getString("reg_pass");
                            Log.d("shub", fbem + fb_passwo);
                            new CallServicesforFacebook().execute(ApiConfig.LOGIN_URL);
                        } else {
                            fbem = jsonObject.getString("reg_email");
                            fb_passwo = jsonObject.getString("reg_pass");
                            fb_device_token = jsonObject.getString("device_token");
                            Log.d("shub", fbem + fb_passwo);
                            new CallServicesforFacebook().execute(ApiConfig.LOGIN_URL);
                        }
                    } catch (Exception e) {
//                e.printStackTrace();
                        Log.e("error", e.toString());
                    }
                }
            });
//            pd.hide();


//            progressBar.setVisibility(View.GONE);
            if (resEntity != null) {
                Log.e("RESPONSE", response_str);

//            doSignInwithfb(response_str);
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("<><><>res" + response_str);
                            parseLoginwithFacebookresponse(response_str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Log.i("RESPONSE", "file4");
            }
        } catch (Exception ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
    }

    public void parseLoginwithFacebookresponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            String Success=jsonObject.optString("Success");
            if(Success.equals("true")){
                String Message=jsonObject.optString("Message");
                ToastClass.ShowLongToast(getApplicationContext(),Message);
                JSONObject result=jsonObject.optJSONObject("Result");
                fbem=result.optString("reg_email");
                fb_passwo=result.optString("reg_pass");
                new CallServicesforFacebook().execute(ApiConfig.LOGIN_URL);
            }
        }
        catch(Exception e){
            Log.d(TAG,e.toString());
            e.printStackTrace();
        }
    }
    private void doSignInwithfb(String response_str) {
        try {
            Log.e("dob", response_str);
            JSONObject objresponse = new JSONObject(response_str);
            String message = objresponse.getString("message");
            Log.e("dob", message);
            if (message.equalsIgnoreCase("invalid")) {
                Log.e("dob1", message);
//                new CallServicesforFacebook().execute(ApiConfig.LOGIN_URL);
            } else {
//                        d_devicetoken=objresponse.getString("reg_id");
                Log.e("dob2", message);
                reg_id = objresponse.getString("reg_id");
                reg_name = objresponse.getString("reg_name");
                reg_email = objresponse.getString("reg_email");
                reg_pass = objresponse.getString("reg_pass");
                reg_degree = objresponse.getString("degree");
                reg_designation = objresponse.getString("designation");
                reg_clinic_address = objresponse.getString("clinic_address");
                reg_photo = objresponse.getString("reg_dob");
                reg_department = objresponse.getString("department");
//                        dob=objresponse.getString("photo");
                reg_specialist = objresponse.getString("specialist");
                Log.e("dob", dob);
                Intent intent = new Intent(LoginActivity.this, DoctorAccount.class);
                startActivity(intent);

            }
            Log.e("result", response_str);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void getFacebookProfilePicture(final String user_id) {

        new AsyncTask<Void, Void, Void>() {
            Profile profile;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                profile = Profile.getCurrentProfile();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    imageURL = new URL("https://graph.facebook.com/" + user_id + "/picture?type=large");
                    Uri uri = Uri.parse(imageURL.toURI().toString());
                    Log.e("jsondata4", imageURL.toString());


                    Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + File.separator + "profile.png");
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                        out.flush();
                        out.close();
                        Log.d("sun", "saved");
                        File f = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "profile.png");
                        if (f.exists()) {
                            b = BitmapFactory.decodeFile(f.toString());
                            String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), b, null, null);
                            Uri image1 = Uri.parse(path);
                            getRealPathFromUri(getApplicationContext(), image1);
                        }
                    } catch (Exception e) {
                        Log.d("sun", e.toString());
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            Log.d("sun", e.toString());
                        }
                    }
                } catch (Exception e) {
                    Log.d("sun", e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }.execute();
    }

    private void getRealPathFromUri(Context ctx, Uri uri) {

        String[] filePathColumn = {MediaStore.Files.FileColumns.DATA};

        Cursor cursor = ctx.getContentResolver().query(uri, filePathColumn,
                null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        selectedImagePath = cursor.getString(columnIndex);
        Log.e("jsondata5", "picturePath : " + imageURL);
        if (selectedImagePath != null) {
//                            progressBar.setVisibility(View.VISIBLE);
            Thread thread2 = new Thread(new Runnable() {
                public void run() {
                    doFileUpload2();
                    runOnUiThread(new Runnable() {
                        public void run() {


                        }
                    });
                }
            });
            thread2.start();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
        cursor.close();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public class CallServicesFp extends AsyncTask<String, String, String> {
        String mEmail = mEmailView.getText().toString().trim();
        String pass = mPasswordView.getText().toString().trim();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("reg_email", response));


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
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonobj = jsonArray.getJSONObject(0);
                    String message = jsonobj.getString("message");
                    if (message.equalsIgnoreCase("Email id does not exit!")) {
                        text_response.setText("Email id does not exists!!");
                    } else {
                        text_response.setText("your password has been send to your registerd email id!!");
                    }
                    Log.e("result", result.toString());

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


    }

    public void shake() {
        // Create shake effect from xml resource
        Animation shake1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        // View element to be shaken
//        TextView s = (TextView) findViewById(R.id.email_sign_in_button);
        Toast.makeText(getApplicationContext(), "Enter Email_id first", Toast.LENGTH_LONG).show();
        // Perform animation
        mEmailSignInButton.startAnimation(shake1);
    }

    public class CallServicesRegisterApi extends AsyncTask<String, String, String> {
        final String name = textname;
        final String email = emailid;
        final String dob = "1994-12-12";
        final String image = profilepicimage;
        final String password = "";
        final String confirm_password = "";
        String unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//            Log.e("image",image);
            pd = new ProgressDialog(LoginActivity.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("reg_name", name));
            namevaluepair.add(new BasicNameValuePair("reg_pass", password));
            namevaluepair.add(new BasicNameValuePair("reg_email", email));
            namevaluepair.add(new BasicNameValuePair("reg_cpass", confirm_password));
            namevaluepair.add(new BasicNameValuePair("reg_dob", dob));
            namevaluepair.add(new BasicNameValuePair("photo", image));
//            namevaluepair.add(new BasicNameValuePair("gcm_id", unique_id));


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
                startActivity(new Intent(LoginActivity.this, DoctorAccount.class));
                pd.dismiss();
            }


            if (result != null) {

                try {
                    JSONObject objresponse = new JSONObject(result);
                    String message = objresponse.getString("d_message");
                    if (message.equalsIgnoreCase("invalid")) {
//                        new CallServicesforFacebook().execute(ApiConfig.LOGIN_URL);
                    } else {
//                        d_devicetoken=objresponse.getString("reg_id");
                        reg_id = objresponse.getString("reg_id");
                        reg_name = objresponse.getString("reg_name");
                        reg_email = objresponse.getString("reg_email");
                        reg_pass = objresponse.getString("reg_pass");
                        reg_degree = objresponse.getString("degree");
                        reg_designation = objresponse.getString("designation");
                        reg_clinic_address = objresponse.getString("clinic_address");
                        reg_photo = objresponse.getString("reg_dob");
                        reg_department = objresponse.getString("department");
//                        dob=objresponse.getString("photo");
                        reg_specialist = objresponse.getString("specialist");
                        Log.e("dob", dob);
                        Intent intent = new Intent(LoginActivity.this, DoctorAccount.class);
                        startActivity(intent);

                    }
                    Log.e("result", result.toString());


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


    }

    public class CallServicesforFacebook extends AsyncTask<String, String, String> {
        String mEmail = mEmailView.getText().toString().trim();
        String pass = mPasswordView.getText().toString().trim();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("reg_email", fbem));
            namevaluepair.add(new BasicNameValuePair("reg_pass", fb_passwo));
            namevaluepair.add(new BasicNameValuePair("device_token", PreferenceData.getDevice_Token(getApplicationContext())));

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
            Log.e("dob3", "dob3");
            try {
                if (pd != null) {
                    pd.dismiss();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }


            if (result != null) {
                Log.e("dob2", result);
                try {
                    Log.e("dob3", result);
                    JSONObject objresponse = new JSONObject(result);
                    String message = objresponse.getString("d_message");
                    if (message.equalsIgnoreCase("invalid")) {
                        UtilsValidate.showError(LoginActivity.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    } else {
                        d_devicetoken = objresponse.getString("reg_id");
                        editor.putBoolean("locked", true);
                        reg_id = objresponse.getString("reg_id");
                        reg_name = objresponse.getString("reg_name");
                        reg_email = objresponse.getString("reg_email");
                        reg_pass = objresponse.getString("reg_pass");
                        reg_degree = objresponse.getString("degree");
                        reg_designation = objresponse.getString("designation");
                        reg_clinic_address = objresponse.getString("clinic_address");
                        reg_photo = objresponse.getString("reg_dob");
                        reg_department = objresponse.getString("department");
                        dob = objresponse.getString("photo");
                        reg_specialist = objresponse.getString("specialist");


                        PreferenceData.setDoctorNumber(getApplicationContext(), objresponse.getString("reg_mob"));
                        PreferenceData.setDoctorclinic_address(getApplicationContext(), objresponse.getString("clinic_address"));
                        PreferenceData.setName(getApplicationContext(), reg_name);
                        PreferenceData.setId(getApplicationContext(), reg_id);
                        PreferenceData.setEmail(getApplicationContext(), reg_email);
                        PreferenceData.setPass(getApplicationContext(), reg_pass);
                        PreferenceData.setPhoto(getApplicationContext(), dob);
                        PreferenceData.setDoctorSpecialist(getApplicationContext(), reg_specialist);
                        PreferenceData.setDoctorNumber(getApplicationContext(), objresponse.getString("reg_mob"));
                        editor.commit();

                        Log.e("dob", dob);

                        Log.d("data", "reg_name:-" + reg_name);
                        Log.d("data", "reg_id:-" + reg_id);
                        Log.d("data", "reg_email:-" + reg_email);
                        Log.d("data", "reg_pass:-" + reg_pass);
                        Log.d("data", "dob:-" + dob);
                        Log.d("data", "reg_specialist:-" + reg_specialist);

                        Intent intent = new Intent(LoginActivity.this, DoctorAccount.class);
                        startActivity(intent);
                        finish();
                    }
                    Log.e("result", result.toString());


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


    }

    private final String TAG = getClass().getSimpleName();

    public class CallServices extends AsyncTask<String, String, String> {
        String mEmail = mEmailView.getText().toString().trim();
        String pass = mPasswordView.getText().toString().trim();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("reg_email", mEmail));
            namevaluepair.add(new BasicNameValuePair("reg_pass", pass));
            namevaluepair.add(new BasicNameValuePair("device_token", PreferenceData.getDevice_Token(getApplicationContext())));
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
                Log.d(TAG, "login_result:-" + result);
                Log.e("result", result);
                try {
                    JSONObject objresponse = new JSONObject(result);
                    String message = objresponse.getString("d_message");
                    if (message.equalsIgnoreCase("invalid")) {
                        UtilsValidate.showError(LoginActivity.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    } else {
                        d_devicetoken = objresponse.getString("device_token");
                        reg_id = objresponse.getString("reg_id");
                        reg_name = objresponse.getString("reg_name");
                        reg_email = objresponse.getString("reg_email");
                        reg_pass = objresponse.getString("reg_pass");
                        reg_degree = objresponse.getString("degree");
                        reg_designation = objresponse.getString("designation");
                        reg_clinic_address = objresponse.getString("clinic_address");
                        reg_photo = objresponse.getString("reg_dob");
                        reg_department = objresponse.getString("department");
                        dob = objresponse.getString("photo");
                        reg_specialist = objresponse.getString("specialist");
                        String confirm_status = objresponse.getString("status");


                        PreferenceData.setName(getApplicationContext(), reg_name);
                        PreferenceData.setId(getApplicationContext(), reg_id);
                        PreferenceData.setEmail(getApplicationContext(), reg_email);
                        PreferenceData.setPass(getApplicationContext(), reg_pass);
                        PreferenceData.setPhoto(getApplicationContext(), dob);
                        PreferenceData.setDoctorNumber(getApplicationContext(), objresponse.getString("reg_mob"));
                        PreferenceData.setDoctorclinic_address(getApplicationContext(), objresponse.getString("clinic_address"));
                        PreferenceData.setDoctorSpecialist(getApplicationContext(), reg_specialist);
                        editor.putBoolean("locked", true);
                        editor.putString("reg_name", reg_name);
                        editor.putString("device_token", d_devicetoken);
                        editor.putString("reg_id", reg_id);
                        String id = settings.getString("reg_id", null);
                        editor.putString("reg_email", reg_email);
                        editor.putString("reg_pass", reg_pass);
                        editor.putString("reg_degree", reg_degree);
                        editor.putString("reg_designation", reg_designation);
                        editor.putString("reg_clinic_address", reg_clinic_address);
                        editor.putString("reg_department", reg_department);
                        editor.putString("photo", dob);
                        editor.commit();
                        try {
                            Gson gson = new Gson();
                            LoginPOJO pojo = gson.fromJson(result, LoginPOJO.class);
                            if (pojo != null) {
                                SaveLoginData(pojo);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("dob", id);
//                        if (confirm_status.equals("0")) {
//                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.ACCOUNT_CONFIRM, false);
//                            showConfirmDialog();
//                        } else {
//                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.ACCOUNT_CONFIRM, true);
                            Intent intent = new Intent(LoginActivity.this, DoctorAccount.class);
                            startActivity(intent);
                            finish();
//                        }

                    }
                    Log.e("result", result.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void SaveLoginData(LoginPOJO pojo) {
        Pref.SetStringPref(getApplicationContext(), StringUtils.reg_id, pojo.getReg_id());
        Pref.SetStringPref(getApplicationContext(), StringUtils.reg_p_id, pojo.getReg_p_id());
        Pref.SetStringPref(getApplicationContext(), StringUtils.reg_name, pojo.getReg_name());
        Pref.SetStringPref(getApplicationContext(), StringUtils.reg_email, pojo.getReg_email());
        Pref.SetStringPref(getApplicationContext(), StringUtils.reg_mob, pojo.getReg_mob());
        Pref.SetStringPref(getApplicationContext(), StringUtils.reg_pass, pojo.getReg_pass());
        Pref.SetStringPref(getApplicationContext(), StringUtils.reg_cpass, pojo.getReg_cpass());
        Pref.SetStringPref(getApplicationContext(), StringUtils.reg_dob, pojo.getReg_dob());
        Pref.SetStringPref(getApplicationContext(), StringUtils.department, pojo.getDepartment());
        Pref.SetStringPref(getApplicationContext(), StringUtils.clinic_address, pojo.getClinic_address());
        Pref.SetStringPref(getApplicationContext(), StringUtils.availability, pojo.getAvailability());
        Pref.SetStringPref(getApplicationContext(), StringUtils.designation, pojo.getDesignation());
        Pref.SetStringPref(getApplicationContext(), StringUtils.degree, pojo.getDegree());
        Pref.SetStringPref(getApplicationContext(), StringUtils.specialist, pojo.getSpecialist());
        Pref.SetStringPref(getApplicationContext(), StringUtils.photo, pojo.getPhoto());
        Pref.SetStringPref(getApplicationContext(), StringUtils.gcm_id, pojo.getGcm_id());
        Pref.SetStringPref(getApplicationContext(), StringUtils.device_token, pojo.getDevice_token());
        Pref.SetStringPref(getApplicationContext(), StringUtils.un_success_msg, pojo.getUn_success_msg());
        Pref.SetStringPref(getApplicationContext(), StringUtils.d_message, pojo.getD_message());
        Pref.SetStringPref(getApplicationContext(), StringUtils.status, pojo.getStatus());
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
}

