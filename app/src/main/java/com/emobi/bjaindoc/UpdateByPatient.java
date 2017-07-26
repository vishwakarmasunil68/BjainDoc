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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by Emobi-Android-002 on 10/20/2016.
 */
public class UpdateByPatient extends AppCompatActivity {

    TextView textDoc, btn_Submit;
    public String selectedImagePath;
    private static final int FILE_SELECT_CODE = 0;
    CircleImageView profile_image;
    String rate1, rate2, reason;
    ActionBar actionBar;
    HttpEntity resEntity;
    String response_str;
    Activity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView whitearrow;
    TextView txt_pname;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //    String selectedImagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_bypatient);
        activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        actionBar.setTitle("Personal Details");
        actionBar.setTitle(s);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //.setIcon(R.drawable.activation)
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Additional Info"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        btn_Submit = (TextView) findViewById(R.id.btn_submit);
        txt_pname = (TextView) findViewById(R.id.txt_pname);
        textDoc = (TextView) findViewById(R.id.textDoc);
        profile_image= (CircleImageView) findViewById(R.id.img_profile);
        whitearrow = (ImageView) findViewById(R.id.whitearrow);
        whitearrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        textDoc.setTypeface(tf);
        viewPager = (ViewPager) findViewById(R.id.pager);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, FILE_SELECT_CODE);
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("selectedpath","path:-"+selectedImagePath);
                if (selectedImagePath==null||selectedImagePath.equals("")){
                    selectedImagePath= Categories_Fragment.p_photo;
                    new CallServices().execute(ApiConfig.UPDATE_PATIENT);
                }
                else {
                    if (TabFragment2.rate2.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "please select your condition", Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("f,f", "F");

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
                    }
                }
//                    new CallServices().execute(ApiConfig.UPDATE_PATIENT);
                /*try {

                if (TabFragment2.rate2.isEmpty()){
                    Toast.makeText(getApplicationContext(),"please select your condition",Toast.LENGTH_LONG).show();
                }
                else {
                    Log.e("f,f", "F");
                    new CallServices().execute(ApiConfig.UPDATE_PATIENT);
                } }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"please select your condition",Toast.LENGTH_LONG).show();
                }
*/


            }
        });

        txt_pname.setText(Categories_Fragment.p_name);
        try {
            if (Categories_Fragment.p_photo.length() > 0) {
                String bitmap = "http://www.bjain.com/doctor/upload/" + Categories_Fragment.p_photo;

                Log.e("stringToBitmap", bitmap.toString());
                Picasso.with(getApplicationContext()).load(bitmap).resize(100, 100).into(profile_image);
            }
        }
        catch (Exception e){

        }
//        tabLayout.setupWithViewPager(viewPager);
        /*tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#578A48"));*/

        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                /578A48
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#479736"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                tab.set(Color.parseColor("#B4B4B4"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        final String name = Updatepatient_patient.ed1.getText().toString().trim();
        final String p_mob = Updatepatient_patient.edtxt_pmob.getText().toString().trim();
        final String p_medication = TabFragment2.one;
        final String p_alergi = TabFragment2.two;
        final String p_digoxin = TabFragment2.three;
        final String email = Updatepatient_patient.ed2.getText().toString().trim();
        final String password = Updatepatient_patient.ed3.getText().toString().trim();
        final String age = Updatepatient_patient.ed4.getText().toString().trim();
        final String weigt = Updatepatient_patient.ed6.getText().toString().trim();
        final String height = Updatepatient_patient.ed7.getText().toString().trim();
        final String descrption = TabFragment2.edtxt_descrption.getText().toString().trim();
        final String blood = Updatepatient_patient.ed5.getText().toString().trim();

        //        Log.d("values",name+"::"+email+"::"+password+"::"+age+"::"+weigt+"::"+height+"::"+descrption);
        ProgressDialog pd;


        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Log.d("values", name + "::" + email + "::" + password + "::" + age + "::" + weigt + "::" + height + "::" + descrption);
            pd = new ProgressDialog(getApplicationContext());

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("p_id", Updatepatient_patient.reason));
            namevaluepair.add(new BasicNameValuePair("p_mob", p_mob));
            namevaluepair.add(new BasicNameValuePair("p_medication", p_medication));
            namevaluepair.add(new BasicNameValuePair("p_alergi", p_alergi));
            namevaluepair.add(new BasicNameValuePair("p_digoxin", p_digoxin));
            namevaluepair.add(new BasicNameValuePair("p_name", name));
            namevaluepair.add(new BasicNameValuePair("p_photo", selectedImagePath));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", password));
            namevaluepair.add(new BasicNameValuePair("p_login_id", email));
            namevaluepair.add(new BasicNameValuePair("p_age", age));
            namevaluepair.add(new BasicNameValuePair("p_bloodgroup", blood));
            namevaluepair.add(new BasicNameValuePair("p_weight", weigt));
            namevaluepair.add(new BasicNameValuePair("p_height", height));
            namevaluepair.add(new BasicNameValuePair("p_status", "active"));
            namevaluepair.add(new BasicNameValuePair("description", descrption));
            namevaluepair.add(new BasicNameValuePair("conditions", TabFragment2.rate2));

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
            Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG).show();
            try {
                JSONObject objresponse = new JSONObject(result);
                Log.e("sub", objresponse.toString());
                String message = objresponse.getString("p_message");
                if (message.equalsIgnoreCase("invalid")) {
                }

                else {
                    String  p_name = objresponse.getString("p_name");
                    String  p_photo=objresponse.getString("p_photo");
                    PatientAccount.textDoc.setText(p_name);
//                    email.setText(p_login_id);
//						PreferenceData.setPatientStatus(getActivity().getApplicationContext(),objresponse.getString("p_status"));
                    PreferenceData.setPatientName(getApplicationContext(),objresponse.getString("p_name"));
                    PreferenceData.setPatientPhoto(getApplicationContext(),objresponse.getString("p_photo"));

                    try {
                        String bitmap = "http://www.bjain.com/doctor/upload/" + p_photo;

                        Log.e("stringToBitmap", bitmap.toString());
                        Picasso.with(getApplicationContext()).load(bitmap).resize(100,100).into(PatientAccount.profilepic);
                    }
                    catch (Exception e){

                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
    }

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "okay");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    private void doFileUpload2() {
        Log.i("RESPONSE", "file1");
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    Log.i("RESPONSE", "file2");
                    final String name = Updatepatient_patient.ed1.getText().toString().trim();
                    final String email = Updatepatient_patient.ed2.getText().toString().trim();
                    final String password = Updatepatient_patient.ed3.getText().toString().trim();
                    final String age = Updatepatient_patient.ed4.getText().toString().trim();
                    final String p_mob = Updatepatient_patient.edtxt_pmob.getText().toString().trim();
                    final String p_medication = TabFragment2.one;
                    final String p_alergi = TabFragment2.two;
                    final String p_digoxin = TabFragment2.three;
                    final String weigt = Updatepatient_patient.ed6.getText().toString().trim();
                    final String height = Updatepatient_patient.ed7.getText().toString().trim();
                    final String descrption = TabFragment2.edtxt_descrption.getText().toString().trim();
                    final String blood = Updatepatient_patient.ed5.getText().toString().trim();
                    final HttpClient client = new DefaultHttpClient();
                    final  HttpPost post = new HttpPost("http://www.bjain.com/doctor/updatepatient.php");
                    MultipartEntity reqEntity = new MultipartEntity();
                    File file1 = new File(selectedImagePath);
                    Log.e("file1", selectedImagePath);
                    FileBody bin1 = new FileBody(file1);

                    reqEntity.addPart("p_photo", bin1);
                    reqEntity.addPart("p_mob", new StringBody(p_mob));
                    reqEntity.addPart("p_medication", new StringBody(p_medication));
                    reqEntity.addPart("p_alergi", new StringBody(p_alergi));
                    reqEntity.addPart("p_digoxin",new StringBody( p_digoxin));
                    reqEntity.addPart("p_login_id", new StringBody(email));
                    reqEntity.addPart("p_id", new StringBody(Updatepatient_patient.reason));
                    reqEntity.addPart("p_name", new StringBody(name));
                    reqEntity.addPart("p_login_pass", new StringBody(password));
                    reqEntity.addPart("p_age", new StringBody(age));
                    reqEntity.addPart("p_bloodgroup", new StringBody(blood));
                    reqEntity.addPart("p_weight", new StringBody(weigt));
                    reqEntity.addPart("p_height", new StringBody(height));
                    reqEntity.addPart("p_status", new StringBody("active"));
                    reqEntity.addPart("description", new StringBody(descrption));
                    reqEntity.addPart("conditions", new StringBody(TabFragment2.rate2));
                    post.setEntity(reqEntity);
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                HttpResponse response = client.execute(post);
                                resEntity = response.getEntity();
                                final String response_str = EntityUtils.toString(resEntity);
                                Log.e("RESPONSE", response_str);
                                Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG).show();
                                try {
                                    JSONObject objresponse = new JSONObject(response_str);
                                    Log.e("sub", objresponse.toString());
                                    String message = objresponse.getString("p_message");
                                    if (message.equalsIgnoreCase("invalid")) {
                                    }

                                    else {
                                        String  p_name = objresponse.getString("p_name");
                                        String  p_photo=objresponse.getString("p_photo");
                                        PatientAccount.textDoc.setText(p_name);
//                    email.setText(p_login_id);
//						PreferenceData.setPatientStatus(getActivity().getApplicationContext(),objresponse.getString("p_status"));
                                        PreferenceData.setPatientName(getApplicationContext(),objresponse.getString("p_name"));
                                        PreferenceData.setPatientPhoto(getApplicationContext(),objresponse.getString("p_photo"));

                                        try {
                                            String bitmap = "http://www.bjain.com/doctor/upload/" + p_photo;

                                            Log.e("stringToBitmap", bitmap.toString());
                                            Picasso.with(getApplicationContext()).load(bitmap).resize(100,100).into(PatientAccount.profilepic);
                                        }
                                        catch (Exception e){

                                        }
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", "okay");
                                setResult(Activity.RESULT_OK, returnIntent);
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
                    if (resEntity != null) {
                        Log.e("RESPONSE", response_str);


//            doSignInwithfb(response_str);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    System.out.println("<><><>res" + response_str);
                                    Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG).show();
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("result", "okay");
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
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
                    Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG).show();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", "okay");
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
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
                    SharedPreferences sp=getApplicationContext().getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("profile_pic",image_path_string);
                    editor.commit();
                    profile_image.setImageBitmap(bmImg);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else {
            Log.e("f,f", "F");

            doFileUpload2();
//                    new CallServices().execute(ApiConfig.UPDATE_PATIENT);
        }
        return super.onOptionsItemSelected(item);
    }
}
