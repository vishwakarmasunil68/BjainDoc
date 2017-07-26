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
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
import patient_side.Search_Doctor;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SignWithPatient extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    CardView cv;
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    TextView sign;
    private View mProgressView;
    private View mLoginFormView;
    public static String p_photo,p_status,d_id, p_doctor_device_token, p_id, p_name, p_login_id, p_weight, p_age, p_height, p_bloodgroup, p_pass;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    String file,response,selectedImagePath;
    public static Bitmap b;
    String fbem, fbpasswd;
    /*CallbackManager callbackManager;
    Button share, details;
    ShareDialog shareDialog;
    LoginButton mEmailSignInFBButton;
    ProfilePictureView profile;*/
    HttpEntity resEntity;
    URL imageURL;
    private GoogleApiClient client;
    Activity activity;
    String fb_email_id,fb_passwo,fb_device_token;
    ProfilePictureView profile;
    ActionBar actionBar;
    CallbackManager callbackManager;
    public static String emailid, text, profilepicimage, textname, bd;
    TextView email_sign_up_button_with_patient,link;
    TextView Sign_up_text,text_response,btn_forgot_password;
    Button btn_id;
    ImageView backarrow;
    TextView toolbar_title;
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
                        Toast.makeText(SignWithPatient.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(SignWithPatient.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        setContentView(R.layout.activity_sign_with_patient);

        activity=this;
        backarrow = (ImageView) findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
       /* actionBar=getSupportActionBar();
        SpannableString s = new SpannableString("  Sign In Patient");
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
        actionBar.setTitle("  Sign In");
        actionBar.setTitle(s);*/

        // Set up the login form.
        btn_id= (Button) findViewById(R.id.btn_id);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        sign= (TextView) findViewById(R.id.sign);
        populateAutoComplete();

        link= (TextView) findViewById(R.id.link);
        email_sign_up_button_with_patient= (TextView) findViewById(R.id.email_sign_up_button_with_patient);
        email_sign_up_button_with_patient.setVisibility(View.GONE);
        link.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignWithPatient.this,LoginActivity.class));
            }
        });
        mPasswordView = (EditText) findViewById(R.id.password);
        cv = (CardView) findViewById(R.id.txt_search);
        cv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignWithPatient.this, Search_Doctor.class));
            }
        });
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        LinearLayout mEmailSignInButton = (LinearLayout) findViewById(R.id.email_sign_in_button_with_patient);
//        callbackManager = CallbackManager.Factory.create();
        LoginButton  mEmailSignInFBButton = (LoginButton) findViewById(R.id.email_sign_in_Facebookbutton);
        profile = (ProfilePictureView) findViewById(R.id.picture);
        btn_forgot_password = (TextView) findViewById(R.id.btn_forgot_password);
        Sign_up_text = (TextView) findViewById(R.id.btn_register);
        TextView subm = (TextView) findViewById(R.id.subm);
        Typeface tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        sign.setTypeface(tf);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_title.setTypeface(tf);
        mEmailView.setTypeface(tf);
        btn_forgot_password.setTypeface(tf);
        Sign_up_text.setTypeface(tf);
        subm.setTypeface(tf);
        mPasswordView.setTypeface(tf);


        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SignWithPatient.this,DoctorAppointment.class));
                new CallServices().execute(ApiConfig.LOGIN_PATIENT);


//                ProgressDialog=(ProgressBar)
//
            }
        });

        btn_id.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SignWithPatient.this, Arrays.asList("public_profile", "email","user_friends"));
            }
        });

        btn_forgot_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SignWithPatient.this, android.R.style.Theme_DeviceDefault_Dialog);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //setting custom layout to dialog
                dialog.setContentView(R.layout.cusotm_dialog_edit);
                dialog.setTitle("Forgot - password");

                //adding text dynamically
                final EditText text_email_id = (EditText) dialog.findViewById(R.id.text_email_id);

                text_response = (TextView) dialog.findViewById(R.id.text_response);

                /*ImageView image = (ImageView)dialog.findViewById(R.id.image);
                image.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.ic_dialog_email));*/
                Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        response = text_email_id.getText().toString();
                        new CallServicesFp().execute(ApiConfig.FORGET_PASSWORD);
                    }
                });

                //adding button click event
                /*Button dismissButton = (Button) dialog.findViewById(R.id.button);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });*/
                dialog.show();
            }
        });

        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();
        }
        mEmailSignInFBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AccessToken.getCurrentAccessToken() != null) {
                    profile.setProfileId(null);
                }
            }
        });
//        mEmailSignInFBButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ProgressDialog=(ProgressBar)
////         startActivity(new Intent(LoginActivity.this,FacebookActivity.class));
//            }
//        });

        Sign_up_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SignWithPatient.this, SignUpPatient.class));
                /*UserModeFragment fragment = UserModeFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();*/
            }
        });


        mEmailSignInFBButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if(AccessToken.getCurrentAccessToken() != null){
                    RequestData();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
            }
        });

        boolean islogin= PreferenceData.getPatientLogin(getApplicationContext());
        if(islogin){
            startActivity(new Intent(SignWithPatient.this, PatientAccount.class));
            finish();
        }

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }





    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                Log.e("jsondata",json.toString());
                try {
                    Log.e("jsondata1",json.toString());
                    if(json != null){
                        Log.e("jsondata2",json.toString());
                        text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
//                        details_txt.setText(Html.fromHtml(text));
                        profilepicimage=json.getString("link");
                        getFacebookProfilePicture(json.getString("id"));
                        textname=json.getString("name");
                        emailid=json.getString("email");
//                        bd=json.getString("birthday");
                        profile.setProfileId(json.getString("id"));
//                        editor.putBoolean("locked", true).commit();

                            /*new CallServicesRegisterApi().execute(ApiConfig.REGISTER_URL);*/

//                        getBitmapFromURL(profilepicimage);
                        Log.d("shub", json.toString());
//                        getFacebookProfilePicture();
//                            getFacebookProfilePicture(json.getString("id"));
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



    public void getFacebookProfilePicture(final String user_id){

        new AsyncTask<Void,Void,Void>(){
            Profile profile;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                profile= Profile.getCurrentProfile();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    imageURL = new URL("https://graph.fblogin380.com/" + user_id + "/picture?type=large");
                    Uri uri =  Uri.parse(imageURL.toURI().toString());
                    Log.e("jsondata4",imageURL.toString());


                    Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+ File.separator+"profile.png");
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                        out.flush();
                        out.close();
                        Log.d("sun","saved");
                        File f=new File(Environment.getExternalStorageDirectory().toString()+ File.separator+"profile.png");
                        if(f.exists()){
                            b = BitmapFactory.decodeFile(f.toString());
                            String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), b,null, null);
                            Uri image1= Uri.parse(path);
                            getRealPathFromUri(getApplicationContext(), image1);
                        }
                    } catch (Exception e) {
                        Log.d("sun",e.toString());
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            Log.d("sun",e.toString());
                        }
                    }
                }
                catch (Exception e){
                    Log.d("sun",e.toString());
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

        String[] filePathColumn = { MediaStore.Files.FileColumns.DATA };

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
        startActivity(new Intent(SignWithPatient.this, Second.class));
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
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

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SignWithPatient.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "SignWithPatient Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app:/com.emobi.bjaindoc/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SignWithPatient Page", // TODO: Define a title for the content shown.
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


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


        private void doFileUpload2() {
            Log.i("RESPONSE", "file1");

            try {
                Log.i("RESPONSE", "file2");
                final String name =textname;
                final String email =emailid;
                final String dob ="";
//            final String image = selectedImagePath;
                final String password ="";
                final String confirm_password ="deactive";
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://www.bjain.com/doctor/addpatient.php");
                MultipartEntity reqEntity = new MultipartEntity();
                File file1 = new File(selectedImagePath);
                Log.e("file1",selectedImagePath);
                FileBody bin1 = new FileBody(file1);
//                reqEntity.addPart("p_photo", bin1);
                reqEntity.addPart("doc_id", new StringBody(""));
                reqEntity.addPart("p_login_id", new StringBody(email));
                reqEntity.addPart("p_name", new StringBody(name));
                reqEntity.addPart("p_login_pass", new StringBody(password));
                reqEntity.addPart("p_status", new StringBody(confirm_password));
                reqEntity.addPart("p_mob", new StringBody(dob));
                post.setEntity(reqEntity);
                HttpResponse response = client.execute(post);
                resEntity = response.getEntity();
                final String response_str = EntityUtils.toString(resEntity);
                Log.e("RESPONSE", response_str);
                activity.runOnUiThread(new Runnable() {
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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else{
                    Log.i("RESPONSE", "file4");
                }
            } catch (Exception ex) {
                Log.e("Debug", "error: " + ex.getMessage(), ex);
            }
        }

    public class CallServices extends AsyncTask<String, String, String> {
        String mEmail = mEmailView.getText().toString().trim();
        String pass = mPasswordView.getText().toString().trim();
        String unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(SignWithPatient.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("p_login_id", mEmail));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", pass));
            namevaluepair.add(new BasicNameValuePair("p_device_token", PreferenceData.getDevice_Token(getApplicationContext())));

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
                    Log.e("sub", objresponse.toString());
                    String message = objresponse.getString("p_message");
                    if (message.equalsIgnoreCase("invalid")) {
                        Toast.makeText(SignWithPatient.this, "information is incorrect", Toast.LENGTH_SHORT).show();
                        UtilsValidate.showError(SignWithPatient.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    }
                    /*else {
                        if (result.toLowerCase().contains("deactive")) {
                            UtilsValidate.showError(SignWithPatient.this, getResources().getString(R.string.error), getResources().getString(R.string.err_notify));
                        }*/
                    else {
                        p_id = objresponse.getString("p_id");
                        p_name = objresponse.getString("p_name");
                        Log.d("sunil","p_name:-"+p_name);
                        d_id = objresponse.getString("doc_id");
                        p_login_id = objresponse.getString("p_login_id");
                        p_pass = objresponse.getString("p_login_pass");
                        p_weight = objresponse.getString("p_weight");
                        p_bloodgroup = objresponse.getString("p_bloodgroup");
                        p_age = objresponse.getString("p_age");
                        p_height = objresponse.getString("p_height");
                        p_doctor_device_token = objresponse.getString("p_device_token");
                        p_status = objresponse.getString("p_status");
                        p_photo = objresponse.getString("p_photo");
//                        SharedPreferences sp=getSharedPreferences("doctor.txt",Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor=sp.edit();
//                        PreferenceData.setP_id(getApplicationContext(),p_id);
//                        editor.putString("reg_name", objresponse.getString("reg_name"));
//                        editor.putString("reg_email",objresponse.getString("reg_email"));
//                        editor.putString("reg_dob",objresponse.getString("reg_dob"));
//                        editor.putString("photo",objresponse.getString("photo"));
//                        editor.putString("address", objresponse.getString("clinic_address"));
//
                        PreferenceData.setPatientName(getApplicationContext(),p_name);
                        PreferenceData.setPatientID(getApplicationContext(),p_id);
                        PreferenceData.setPatientEmail(getApplicationContext(),p_login_id);
                        PreferenceData.setPatientPassword(getApplicationContext(),p_pass);
                        PreferenceData.setPatientStatus(getApplicationContext(),p_status);
                        PreferenceData.setPatientPhoto(getApplicationContext(),p_photo);
                        PreferenceData.setchatDocid(getApplicationContext(),objresponse.getString("doc_id"));
                        PreferenceData.setDoctor_Token(getApplicationContext(),objresponse.getString("device_token"));
                        PreferenceData.setDoctorName(getApplicationContext(), objresponse.getString("reg_name"));
                        PreferenceData.setDoctorNumber(getApplicationContext(), objresponse.getString("reg_mob"));
                        PreferenceData.setDoctorEmail(getApplicationContext(), objresponse.getString("reg_email"));
                        PreferenceData.setDoctorDepartment(getApplicationContext(), objresponse.getString("department"));
                        PreferenceData.setDoctorDob(getApplicationContext(), objresponse.getString("reg_dob"));
                        PreferenceData.setDoctorPhotoUrl(getApplicationContext(), objresponse.getString("photo"));
                        PreferenceData.setDoctorclinic_address(getApplicationContext(), objresponse.getString("clinic_address"));
                        PreferenceData.setDoctorDesignation(getApplicationContext(), objresponse.getString("designation"));
                        PreferenceData.setDoctorSpecialist(getApplicationContext(), objresponse.getString("specialist"));
                        PreferenceData.setDoctorDegree(getApplicationContext(), objresponse.getString("degree"));
                        PreferenceData.setPatientLogin(getApplicationContext(),true);

//                        editor.commit();

                        finish();
                        Intent intent = new Intent(SignWithPatient.this, PatientAccount.class);
                        startActivity(intent);
                    }

                    Log.e("p_name", p_name);


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }
    }

    public class CallServicesforFacebook extends AsyncTask<String, String, String> {
        String mEmail=mEmailView.getText().toString().trim();
        String pass=mPasswordView.getText().toString().trim();
        ProgressDialog pd;

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(SignWithPatient.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("p_login_id", fbem));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", fb_passwo));
            namevaluepair.add(new BasicNameValuePair("p_device_token", PreferenceData.getDevice_Token(getApplicationContext())));

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
            Log.e("dob3","dob3");
            if (pd != null) {
                pd.dismiss();
            }


            if (result != null) {
                Log.e("dob2",result);
                try {
                    JSONObject objresponse = new JSONObject(result);
                    Log.e("sub", objresponse.toString());
                    String message = objresponse.getString("p_message");
                    if (message.equalsIgnoreCase("invalid")) {
                        Toast.makeText(SignWithPatient.this, "information is incorrect", Toast.LENGTH_SHORT).show();
                        UtilsValidate.showError(SignWithPatient.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    }
                    /*else {
                        if (result.toLowerCase().contains("deactive")) {
                            UtilsValidate.showError(SignWithPatient.this, getResources().getString(R.string.error), getResources().getString(R.string.err_notify));
                        }*/
                    else {
                        p_id = objresponse.getString("p_id");
                        p_name = objresponse.getString("p_name");
                        Log.d("sunil","p_name:-"+p_name);
                        d_id = objresponse.getString("doc_id");
                        p_login_id = objresponse.getString("p_login_id");
                        p_pass = objresponse.getString("p_login_pass");
                        p_weight = objresponse.getString("p_weight");
                        p_bloodgroup = objresponse.getString("p_bloodgroup");
                        p_age = objresponse.getString("p_age");
                        p_height = objresponse.getString("p_height");
                        p_doctor_device_token = objresponse.getString("p_device_token");
                        p_status = objresponse.getString("p_status");
//                        SharedPreferences sp=getSharedPreferences("doctor.txt",Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor=sp.edit();
//                        PreferenceData.setP_id(getApplicationContext(),p_id);
//                        editor.putString("reg_name", objresponse.getString("reg_name"));
//                        editor.putString("reg_email",objresponse.getString("reg_email"));
//                        editor.putString("reg_dob",objresponse.getString("reg_dob"));
//                        editor.putString("photo",objresponse.getString("photo"));
//                        editor.putString("address", objresponse.getString("clinic_address"));
//
                        PreferenceData.setPatientName(getApplicationContext(),p_name);
                        PreferenceData.setPatientID(getApplicationContext(),p_id);
                        PreferenceData.setPatientEmail(getApplicationContext(),p_login_id);
                        PreferenceData.setPatientPassword(getApplicationContext(),p_pass);
                        PreferenceData.setPatientStatus(getApplicationContext(),p_status);

                        PreferenceData.setchatDocid(getApplicationContext(),objresponse.getString("doc_id"));
                        PreferenceData.setDoctor_Token(getApplicationContext(),objresponse.getString("device_token"));
                        PreferenceData.setDoctorName(getApplicationContext(), objresponse.getString("reg_name"));
                        PreferenceData.setDoctorNumber(getApplicationContext(), objresponse.getString("reg_mob"));
                        PreferenceData.setDoctorEmail(getApplicationContext(), objresponse.getString("reg_email"));
                        PreferenceData.setDoctorDepartment(getApplicationContext(), objresponse.getString("department"));
                        PreferenceData.setDoctorDob(getApplicationContext(), objresponse.getString("reg_dob"));
                        PreferenceData.setDoctorPhotoUrl(getApplicationContext(), objresponse.getString("photo"));
                        PreferenceData.setDoctorclinic_address(getApplicationContext(), objresponse.getString("clinic_address"));
                        PreferenceData.setDoctorDesignation(getApplicationContext(), objresponse.getString("designation"));
                        PreferenceData.setDoctorSpecialist(getApplicationContext(), objresponse.getString("specialist"));
                        PreferenceData.setDoctorDegree(getApplicationContext(), objresponse.getString("degree"));
                        PreferenceData.setPatientLogin(getApplicationContext(),true);

//                        editor.commit();


                        Intent intent = new Intent(SignWithPatient.this, PatientAccount.class);
                        startActivity(intent);
                    }

                    Log.e("p_name", p_name);


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }
    }

    public class CallServicesFp extends AsyncTask<String, String, String> {
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
            pd = new ProgressDialog(SignWithPatient.this);

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
                    JSONObject jsonobj=jsonArray.getJSONObject(0);
                    String message=jsonobj.getString("message");
                    if (message.equalsIgnoreCase("Email id does not exit!")) {
                        text_response.setText("Email id does not exists!!");
                    }
                    else {
                        text_response.setText("your password has been send to your registerd email id!!");
                    }
                    Log.e("result",result.toString());

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


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

