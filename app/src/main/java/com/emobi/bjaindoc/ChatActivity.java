package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import database.PreferenceData;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class ChatActivity extends AppCompatActivity {
    LinearLayout old_chat_layout,chat_layout;
    TextView toolbar_title,msg_text;
    private int PICK_IMAGE_REQUEST = 1;
    Button upload, getUpload;
    String file,selectedImagePath;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    VideoView videoView;
    public static final int MEDIA_TYPE_IMAGE = 1;
    HttpEntity resEntity;
    public static final int MEDIA_TYPE_VIDEO = 2;
    ImageView imagemedia,image_urgent,reloadsdatas,send,drawer_ham,imagemedia_photo;
    String filepath;
    String normal_chat_msg,urgent_chat_msg;
    EditText et;
    ScrollView scroll;
    private Uri fileUri; // file url to store image/video
    String c_time;
    public static Boolean p_chat;
    TextView chat_msg;
    private RecyclerView mRecyclerView;
    private CoordinatorLayout mSnackBarLayout;
    public String note_title,chat_message,chat_urgent_message,chat_file;
    public static ArrayList<InfoApps> contactDetails1;
    ListView listView;
    private int itemPosition;
    InfoApps Detailapp;
    ArrayList<String> stringArrayList;
    private ProgressDialog pDialog;
    //    LocationAdapter5 locationAdapter5;
    public static Button savechange;
    Typeface tf;
    public static Bitmap bmThumbnail,bmThumbnailforvideo;
    UsersAdapterchat_Doc mAdapterbroad;

    private final TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            if (et.getText().toString().equals("")) {

            } else {
//                send.setImageResource(R.drawable.ic_chat_send);

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length()==0){
//                send.setImageResource(R.drawable.ic_chat_send);
            }else{
//                send.setImageResource(R.drawable.ic_chat_send_active);
            }
        }
    };
    public ChatActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chat_msg = (TextView) findViewById(R.id.chat_txt);

        tf= Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        chat_layout= (LinearLayout) findViewById(R.id.chat_layout);
        old_chat_layout= (LinearLayout) findViewById(R.id.old_chat_layout);

        reloadsdatas =(ImageView) findViewById(R.id.reloadsdatas);
        send= (ImageView) findViewById(R.id.send);
        drawer_ham = (ImageView) findViewById(R.id.drawer_ham);
        et= (EditText) findViewById(R.id.msg_et);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        imagemedia=(ImageView)findViewById(R.id.imagemedia);
        image_urgent=(ImageView)findViewById(R.id.image_urgent);
        imagemedia_photo=(ImageView)findViewById(R.id.imagemedia_photo);

        scroll=(ScrollView) findViewById(R.id.scrollview);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
try {
    if (PatientCompleteDetailAccount.p_status.equals("deactive")) {
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Montez-Regular.ttf");
        chat_msg.setText("Patient id is deactive");
        chat_msg.setTypeface(tf);
        chat_msg.setVisibility(View.VISIBLE);
        chat_layout.setVisibility(View.GONE);
//            imagemedia.setVisibility(View.GONE);
        imagemedia_photo.setVisibility(View.GONE);
        image_urgent.setVisibility(View.GONE);
    }
}
catch (Exception e){
    e.toString();
}
        toolbar_title.setTypeface(tf);

        stringArrayList=new ArrayList<String>();
        contactDetails1=new ArrayList<InfoApps>();
        new CallServices().execute(ApiConfig.FCM_NOTIFICATION_CHAT);
        et.addTextChangedListener(watcher1);
        Log.d("sunil","Name:-"+UsersAdapter.patient_name);
        Log.d("sunil","device:-"+UsersAdapter.device_token);
        String getDatetime=UtilsValidate.getCurrentDateTime();
        String time[]=getDatetime.split(" ");
        c_time=time[1];
        imagemedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentvdeo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

                // set video quality
                intentvdeo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

                intentvdeo.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
                // name

                // start the video capture Intent
                startActivityForResult(intentvdeo, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
            }
        });

        image_urgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                urgent_chat_msg=et.getText().toString();

                if(!et.getText().toString().equals("")) {
                    setNotification1("cuCCCUpYDEc:APA91bF7OanVGNnXytZsbTMUvXoWWh0_v9MyRuQhMVQpnlynGeWaYeNPiixaom-GfNgEtKwlM-wkiEgXEvm-x5twFMbPDRhIbrfRCV9WPzQJJ3_FHpS77sPhY_c5i8gTiMFSlUDLCu_2", urgent_chat_msg);
//                    TextView tv = new TextView(getApplicationContext());
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.gravity=Gravity.RIGHT;
//                    tv.setLayoutParams(params);
//                    tv.setGravity(Gravity.RIGHT);
//                    tv.setTextColor(Color.WHITE);
//                    tv.setText("Me:-" + et.getText().toString());
//                    chat_layout.addView(tv);
//                    et.setText("");

                    LinearLayout ll=new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity=Gravity.RIGHT;
                    ll.setLayoutParams(params);
                    params.setMargins(20,50,50,0);
                    chat_layout.addView(ll);
                    ll.setBackground( getResources().getDrawable(R.drawable.chat_incoming_green));
                    TextView tv = new TextView(getApplicationContext());
                    tv.setTypeface(tf);
                    tv.setTextColor(Color.parseColor("#72A47F"));
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.CENTER);
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText(et.getText().toString());
                    ll.addView(tv);
                    et.setText("");
                    scroll.post(new Runnable() {

                        @Override
                        public void run() {
                            scroll.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"msg cannot be empty",Toast.LENGTH_LONG).show();
                }
            }
        });

        imagemedia_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK );
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        new CallServices1().execute("http://www.bjain.com/doctor/json_length.php");
        drawer_ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        new CallServices().execute(ApiConfig.FCM_NOTIFICATION_CHAT);
        reloadsdatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jsonArray!=null) {
                    if ((looped_data + 10) > jsonArray.length()) {
                        int k=jsonArray.length()-looped_data;
                        looped_data=looped_data+k;
                    }
                    else {
                        looped_data=looped_data+10;
                    }
                    setChatData(jsonArray);
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                normal_chat_msg=et.getText().toString();
                if(!et.getText().toString().equals("")) {
                    setNotification("cuCCCUpYDEc:APA91bF7OanVGNnXytZsbTMUvXoWWh0_v9MyRuQhMVQpnlynGeWaYeNPiixaom-GfNgEtKwlM-wkiEgXEvm-x5twFMbPDRhIbrfRCV9WPzQJJ3_FHpS77sPhY_c5i8gTiMFSlUDLCu_2", normal_chat_msg);
//                    TextView tv = new TextView(getApplicationContext());
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.gravity=Gravity.RIGHT;
//                    tv.setLayoutParams(params);
//                    tv.setGravity(Gravity.RIGHT);
//                    tv.setTextColor(Color.WHITE);
//                    tv.setText("Me:-" + et.getText().toString());
//                    chat_layout.addView(tv);
//                    et.setText("");

                    LinearLayout ll=new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity=Gravity.RIGHT;
                    ll.setLayoutParams(params);
                    params.setMargins(20,50,50,0);
                    chat_layout.addView(ll);
                    ll.setBackground( getResources().getDrawable(R.drawable.chat_incoming_green));
                    TextView tv = new TextView(getApplicationContext());
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.CENTER);
                    Typeface tf1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
                    tv.setTypeface(tf1);
                    tv.setTextColor(Color.parseColor("#72A47F"));
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText(et.getText().toString());
                    ll.addView(tv);
                    et.setText("");
                    scroll.fullScroll(View.FOCUS_DOWN);
                    scroll.post(new Runnable() {

                        @Override
                        public void run() {
                            scroll.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"msg cannot be empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class CallServices1 extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            old_chat_layout.setVisibility(View.GONE);
            try {
                pd = new ProgressDialog(ChatActivity.this);

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("p_id", PreferenceData.getPatientId(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getchatDocid(getApplicationContext())));
            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

                Log.e("result",result.toString());
            } catch (Exception e) {

                e.printStackTrace();

            }

            return result;


        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);




            old_chat_layout.setVisibility(View.VISIBLE);
            try {
                if (pd != null) {
                    pd.dismiss();
                }
            }
            catch (Exception e){

            }


            if (result != null) {
                Log.e("result",result.toString());
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", jsonArray.toString());
//                    int j= jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        Log.e("2", jsonObject2.toString());
                        /*String msg = jsonObject2.getString("message");

                        if (msg.equals("Record not available")){
                            Toast.makeText(getApplicationContext(),"no record available",Toast.LENGTH_LONG).show();
                        }
                        else {*/
                        chat_message = jsonObject2.getString("chat_msg");


                        if (chat_message.contains("@@Video@@")) {
                            Log.d("shubham","photos");
                            chat_file = jsonObject2.getString("chat_file");
                            getmessageVideo(chat_message, old_chat_layout,chat_file);

                        }

                        if (chat_message.contains("@@Photo@@")) {
                            Log.d("shubham","photos");
                            chat_file = jsonObject2.getString("chat_file");
                            getmessagePhoto(chat_message, old_chat_layout,chat_file);

                        }
                        if (!chat_message.contains("@@Photo@@")){
                            getmessage(chat_message, old_chat_layout);
                        }

                        if (chat_message.contains("urgent_chat")) {
                            Log.d("shubham","photos");
                            chat_urgent_message = jsonObject2.getString("purgentchat");
                            geturgentmessage(chat_urgent_message, old_chat_layout);

                        }
                    }
                }

                catch (Exception e) {

                    e.printStackTrace();
                }
                /*mAdapterbroad = new UsersAdapterchat_Doc(contactDetails1,getApplicationContext());
                mRecyclerView.setAdapter(mAdapterbroad);
                Log.e("stringArrayList", stringArrayList.toString());*/
            }

        }
    }

    public void setNotification(final String device_token, final String msg){
        new AsyncTask<String,String,String>(){
            ProgressDialog pd;
            ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
            String result;
            String tag;

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                p_chat=true;
                ChatFragment.d_chat=false;
                pd = new ProgressDialog(getApplicationContext());

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.hide();
            }

            @SuppressWarnings("deprecation")
            @Override
            protected String doInBackground(String... params) {
                //  TODO Auto-generated method stub

                namevaluepair.add(new BasicNameValuePair("token", PreferenceData.getPatient_Device_Token(getApplicationContext())));
                Log.d("sun",device_token);
                namevaluepair.add(new BasicNameValuePair("message", "doctor::"+msg));
                namevaluepair.add(new BasicNameValuePair("purgentchat", ""));
                namevaluepair.add(new BasicNameValuePair("chat_doc_id",  PreferenceData.getId(getApplicationContext())));
                namevaluepair.add(new BasicNameValuePair("chat_p_id", PreferenceData.getchatPatient_id(getApplicationContext())));
                namevaluepair.add(new BasicNameValuePair("title", "Bjain Doctor"));
                namevaluepair.add(new BasicNameValuePair("date", UtilsValidate.getCurrentDate()));
                namevaluepair.add(new BasicNameValuePair("time", UtilsValidate.getCurrentDateTime()));
                try {

                    result = Util.executeHttpPost(ApiConfig.SEND_MESSAGE, namevaluepair);

                    Log.e("sunil",result.toString());
                } catch (Exception e) {

                    e.printStackTrace();

                }

                return result;


            }


            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                try {


                    JSONObject jsonObject = new JSONObject(result);

                    String sendmessage =jsonObject.getString("success");
                    if (sendmessage.equalsIgnoreCase("1")){
                        Toast.makeText(getApplicationContext(),"message send successfully",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"message not send, invalid rgistration",Toast.LENGTH_SHORT).show();
                    }
                /*if (pd != null) {
                    pd.dismiss();
                }*/

                    if (result != null) {
                        Log.e("sunil", "sent");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    @Override
    public void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter("client"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }




    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
//            if (message.equalsIgnoreCase("Client::" + message)) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            Log.d("sunil", "message in fragment:-" + message);
            String[] msgsplt=message.split("::");

            try {
                if (msgsplt[0].equalsIgnoreCase("patient")) {
                    Log.e("phtosssss",msgsplt[1]);
                    if (msgsplt[1].equals("@@Photo@@")) {
                        Log.e("phtosssss", "bitmap");
                        LinearLayout llold = new LinearLayout(getApplicationContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.RIGHT;
                        llold.setLayoutParams(params);
                        params.setMargins(20, 50, 50, 0);
                        old_chat_layout.addView(llold);
//                llold.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
                        final ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setLayoutParams(params);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("bitmap", "bitmap");
                                Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                bm.copy(Bitmap.Config.ARGB_8888, true);
//                        bm = imageView.getDrawable().();
                                bm = Bitmap.createScaledBitmap(bm, 350, 350, true);
//                        Bitmap bitmap1 = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.RGB_565);
                        /*FileOutputStream out = null;
                        try {
                            out = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"temp.png");
                            bm.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                            // PNG is a lossless format, the compression factor (100) is ignored
                            Intent i=new Intent(getApplicationContext(),GetBitmapActivity.class);
                            i.putExtra("bitmap",bm);
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }*/
                        /*SharedPreferences myPrefrence;
                        myPrefrence = getApplicationContext().getSharedPreferences("locked", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefrence.edit();
//                        editor.putString("namePreferance", itemNAme);
                        editor.putString("imagePreferance", encodeTobase64(bm));
                        editor.commit();*/
//                        bm=bm.createBitmap(500, 500,  Bitmap.Config.ARGB_8888);
                                Intent i = new Intent(getApplicationContext(), GetBitmapActivity.class);
                                i.putExtra("bitmap", bm);
                                startActivity(i);
                            }
                        });
                        try {
                            /*final String bitmap = "http://www.bjain.com/doctor/upload/" + chat_photo;

                            Log.e("stringToBitmap", bitmap.toString());*/
                            llold.addView(imageView);
                            Picasso.with(getApplicationContext()).load(msgsplt[2]).into(imageView);

                            llold.addView(imageView);
//                    imageView.get
                        } catch (Exception e) {

                        }
                        scroll.post(new Runnable() {

                            @Override
                            public void run() {
                                scroll.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });

                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.BLUE);F
                tv.setBackgroundResource(R.drawable.balloon_outgoing_normal);*/
                    }

                    else if (msgsplt[1].equals("urgent_chat")) {
                        Log.d("urgent_chat","urgent_chat");
                        LinearLayout ll = new LinearLayout(getApplicationContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.LEFT;
                        ll.setLayoutParams(params);
                        chat_layout.addView(ll);
                        ll.setBackground(getResources().getDrawable(R.drawable.chat_incoming_gray));

                        TextView tv = new TextView(getApplicationContext());
                        tv.setLayoutParams(params);
                        tv.setTypeface(tf);
                        tv.setTextColor(Color.parseColor("#686868"));
                        params.setMargins(50, 50, 80, 0);
                        tv.setGravity(Gravity.CENTER);
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        tv.setText("Urgent" + "-" +msgsplt[2]);
                        ll.addView(tv);
                        scroll.fullScroll(View.FOCUS_DOWN);
                        scroll.post(new Runnable() {

                            @Override
                            public void run() {
                                scroll.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }

                    else if (!msgsplt[1].equals("@@Photo@@")) {
                        LinearLayout ll = new LinearLayout(getApplicationContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.LEFT;
                        ll.setLayoutParams(params);
                        chat_layout.addView(ll);
                        ll.setBackground(getResources().getDrawable(R.drawable.chat_incoming_gray));

                        TextView tv = new TextView(getApplicationContext());
                        tv.setLayoutParams(params);
                        tv.setTypeface(tf);
                        tv.setTextColor(Color.parseColor("#686868"));
                        params.setMargins(50, 50, 80, 0);
                        tv.setGravity(Gravity.CENTER);
                        Typeface tf1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
                        tv.setTypeface(tf1);
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        tv.setText(msgsplt[1]);
                        ll.addView(tv);
                        scroll.fullScroll(View.FOCUS_DOWN);
                        scroll.post(new Runnable() {

                            @Override
                            public void run() {
                                scroll.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }


                }
            }
            catch (Exception e){

            }



                /*TextView tv = new TextView(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(params);
            params.gravity=Gravity.LEFT;
            params.setLayoutDirection(Gravity.LEFT);
                tv.setGravity(Gravity.RIGHT);
//            tv.setLayoutDirection(Gravity.RIGHT);
             tv.setTextColor(Color.BLACK);
                tv.setText("Patient:-" + message);*/
//                chat_layout.addView(tv);
//            chat_msg.append("Doctor: "+message+"\n");
            //do other stuff here
        }
//        }
    };
    private static Uri getOutputMediaFileUri(int type) {

        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {

        // Check that the SDCard is mounted
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraVideo");


        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {


                /*Toast.makeText(activityContext, "Failed to create directory MyCameraVideo.",
                        Toast.LENGTH_LONG).show();*/

                Log.d("MyCameraVideo", "Failed to create directory MyCameraVideo.");
                return null;
            }
        }


        // Create a media file name

        // For unique file name appending current timeStamp with file name
        java.util.Date date = new java.util.Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(date.getTime());

        File mediaFile;

        if (type == MEDIA_TYPE_VIDEO) {

            // For unique video file name appending current timeStamp with file name
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");


        } else {
            return null;
        }

        return mediaFile;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // After camera screen this code will excuted

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            Log.e("filePath", filePath.toString());
//            selectedImagePath=filePath.getPath();
            try {
                //Getting the Bitmap from Gallery
                bmThumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                LinearLayout ll=new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.RIGHT;
                ll.setLayoutParams(params);
                params.setMargins(20,50,50,0);
                chat_layout.addView(ll);
//            ll.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
                scroll.fullScroll(View.FOCUS_DOWN);
                ImageView iv = new ImageView(getApplicationContext());
                iv.setLayoutParams(params);
                iv.setMaxWidth(500);
                iv.setMaxHeight(500);
                iv.setImageBitmap(bmThumbnail);
//                            iv.setGravity(Gravity.CENTER);
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                           /* iv.setTextColor(Color.BLACK);
                            tv.setText(et.getText().toString());*/
                ll.addView(iv);
                Log.e("photos",bmThumbnail.toString());
                //Setting the Bitmap to ImageView
//                img_profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (filePath != null) {
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


            getRealPathFromUri(getApplicationContext(), filePath);

            file = filePath.getPath();
        }

        else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
//            ImageView img = (ImageView) findViewById(R.id.image);
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                Log.e("filePath",selectedImageUri.getPath());
                filepath=selectedImageUri.getPath();
                bmThumbnailforvideo = ThumbnailUtils.createVideoThumbnail(filepath, MediaStore.Images.Thumbnails.MINI_KIND);
                Log.e("video",bmThumbnailforvideo.toString());
                LinearLayout ll=new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.RIGHT;
                ll.setLayoutParams(params);
                params.setMargins(20,50,50,0);
                chat_layout.addView(ll);
//            ll.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
                scroll.fullScroll(View.FOCUS_DOWN);

                ImageView iv = new ImageView(getApplicationContext());
                iv.setLayoutParams(params);
//				            img.setImageBitmap(bmThumbnail);
                iv.setImageBitmap(bmThumbnailforvideo);
//                            iv.setGravity(Gravity.CENTER);
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                           /* iv.setTextColor(Color.BLACK);
                            tv.setText(et.getText().toString());*/
                ll.addView(iv);
                scroll.post(new Runnable() {

                    @Override
                    public void run() {
                        scroll.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                if (filepath != null) {
                    Toast.makeText(getApplicationContext(),
                            "file path existing!", Toast.LENGTH_LONG).show();
                    Thread thread2 = new Thread(new Runnable() {
                        public void run() {
                            doFileUpload1();
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

//                setNotification(UsersAdapter.device_token, filePath);
                /*Intent i=new Intent(getApplicationContext(),UploadActivity.class);
//          	vdat=data.getData().toString();
				*//*Uri selectedImageUri = data.getData();
				vdat = getPath(selectedImageUri);*//*
				*//*Toast.makeText(this, "Video saved to " +
						vdat, Toast.LENGTH_LONG).show();*//*
                i.putExtra("filePath",selectedImageUri.getPath() );
////		i.putExtra("isImage", isImage);
                startActivity(i);*/
//				launchUploadActivity(false);

                // Video captured and saved to fileUri specified in the Intent
				/*Toast.makeText(this, "Video saved to: " +
						data.getData(), Toast.LENGTH_LONG).show();*/

//				.setImageBitmap(bmThumbnail);

//				loginUserVideo();




            } else if (resultCode == RESULT_CANCELED) {


                // User cancelled the video capture
                Toast.makeText(getApplicationContext(), "User cancelled the video capture.",
                        Toast.LENGTH_LONG).show();

            } else {


                // Video capture failed, advise user
                Toast.makeText(getApplicationContext(), "Video capture failed.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void doFileUpload1() {
        Log.i("RESPONSE", "file1");

        try {
            Log.i("RESPONSE", "file2");

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(ApiConfig.SEND_MESSAGE);
            MultipartEntity reqEntity = new MultipartEntity();
            File file1 = new File(filepath);
            String file_name=file1.getName();
            FileBody bin1 = new FileBody(file1);
            reqEntity.addPart("title", new StringBody(("Bjain Doctor")));
            reqEntity.addPart("chat_file", bin1);
            reqEntity.addPart("message", new StringBody("doctor::"+"@@Video@@::"+"http://www.bjain.com/doctor/upload/"+file_name));
            reqEntity.addPart("chat_doc_id", new StringBody((PreferenceData.getId(getApplicationContext()))));
            reqEntity.addPart("chat_p_id", new StringBody(PreferenceData.getchatPatient_id(getApplicationContext())));
            reqEntity.addPart("token", new StringBody(PreferenceData.getPatient_Device_Token(getApplicationContext())));
            reqEntity.addPart("date", new StringBody(UtilsValidate.getCurrentDate()));
            reqEntity.addPart("time", new StringBody(c_time));
            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            if (resEntity != null) {
                Log.e("RESPONSE", response_str);
                LinearLayout ll=new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.RIGHT;
                ll.setLayoutParams(params);
//                params.setMargins(20,50,50,0);
                chat_layout.addView(ll);
                ll.setBackground( getResources().getDrawable(R.drawable.chat_incoming_green));
                scroll.fullScroll(View.FOCUS_DOWN);
                ImageView iv = new ImageView(getApplicationContext());
                iv.setLayoutParams(params);
//				            img.setImageBitmap(bmThumbnail);
                iv.setImageBitmap(bmThumbnailforvideo);
//                            iv.setGravity(Gravity.CENTER);
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                           /* iv.setTextColor(Color.BLACK);
                            tv.setText(et.getText().toString());*/
                ll.addView(iv);
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
    private final String TAG=getClass().getSimpleName();

    public class CallServices extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                pd = new ProgressDialog(ChatActivity.this);

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.d(TAG,"doc_id:-"+PreferenceData.getId(getApplicationContext()));
            Log.d(TAG,"p_id:-"+UsersAdapter.patient_id);
            namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("p_id", UsersAdapter.patient_id));
            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

                Log.e("result",result.toString());
            } catch (Exception e) {

                e.printStackTrace();

            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {
                if (pd != null) {
                    pd.dismiss();
                }
            }
            catch (Exception e){

            }


            if (result != null) {
                Log.e("result",result.toString());
                try {
                    JSONArray array = new JSONArray(result);
//                    Log.e("Post Method", array.toString());
                    jsonArray=array;
                    if(array!=null) {
                        setChatData(array);
                    }
                }

                catch (Exception e) {

                    e.printStackTrace();
                }
            }

        }
    }
    public JSONArray jsonArray;
    public int looped_data=10;
    public void setChatData(JSONArray jsonArray) {
        old_chat_layout.removeAllViews();
        try {

            int loop_time=jsonArray.length()-looped_data;
            List<JSONObject> jsonObjectList=new ArrayList<>();
            for (int i = (jsonArray.length()-1) ; i > loop_time; i--) {

                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Log.e("2", jsonObject2.toString());
                jsonObjectList.add(jsonObject2);

            }
            Collections.reverse(jsonObjectList);

            for (JSONObject jsonObject2:jsonObjectList) {

                Log.e("2", jsonObject2.toString());
                        /*String msg = jsonObject2.getString("message");

                        if (msg.equals("Record not available")){
                            Toast.makeText(getApplicationContext(),"no record available",Toast.LENGTH_LONG).show();
                        }
                        else {*/
                chat_message = jsonObject2.getString("chat_msg");
                        /*String c_msg[]=chat_message.split("::");
                        String  msg=c_msg[1];
                        Log.d("c_msg_1",c_msg[1]);
                        Log.d("c_msg_0",c_msg[0]);*/
                if (chat_message.contains("@@Video@@")) {
                    Log.d("shubham", "photos");
                    chat_file = jsonObject2.getString("chat_file");
                    getmessageVideo(chat_message, old_chat_layout, chat_file);

                }

                if (chat_message.contains("@@Photo@@")) {
                    Log.d("shubham", "photos");
                    chat_file = jsonObject2.getString("chat_file");
                    getmessagePhoto(chat_message, old_chat_layout, chat_file);

                }
                if (!chat_message.contains("@@Photo@@")) {
                    getmessage(chat_message, old_chat_layout);
                }

                if (chat_message.contains("urgent_chat")) {
                    Log.d("shubham", "photos");
                    chat_urgent_message = jsonObject2.getString("purgentchat");
                    geturgentmessage(chat_urgent_message, old_chat_layout);

                }
            }
        }
        catch (Exception e){

        }
    }
    public String getmessageVideo(String s,LinearLayout ll,String chat_photo){
        String date="";
        try{
            Log.d("shubham","videos");
            String[] str=s.split("::");
            scroll.fullScroll(View.FOCUS_DOWN);
            if (str[0].equalsIgnoreCase("doctor")){
                Log.d("shubham","videos");
                LinearLayout llold=new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.RIGHT;
                llold.setLayoutParams(params);
                params.setMargins(20,50,50,0);
                old_chat_layout.addView(llold);
//                llold.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
                final ImageView imageView = new ImageView(getApplicationContext());
                imageView.setLayoutParams(params);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("bitmap","bitmap");
                        String bm="http://www.bjain.com/doctor/upload/" + "VID_20161004_195754.mp4";
                        Intent i=new Intent(getApplicationContext(),Play.class);
                        i.putExtra("url",bm);
                        startActivity(i);
                    }
                });
                try {
                    final String bitmap = "http://www.bjain.com/doctor/upload/" + chat_photo;

                    Log.e("stringToBitmap", bitmap.toString());
                    llold.addView(imageView);

                    FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
                    mmr.setDataSource(bitmap);
                    mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
                    mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
                    Bitmap b = mmr.getFrameAtTime(2000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST); // frame at 2 seconds
                    byte [] artwork = mmr.getEmbeddedPicture();

                    mmr.release();
                    imageView.setImageBitmap(b);
                    /*ContentResolver crThumb = getApplicationContext().getContentResolver();
                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inSampleSize = 1;
                    Glide.with(getApplicationContext())
                            .load(bitmap) // or URI/path
                            .into(imageView);*/
//                    Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, bitmap, MediaStore.Video.Thumbnails.MICRO_KIND, options);
//                    Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + chat_photo).resize(500,500).into(imageView);
                    llold.addView(imageView);

//                    imageView.get
                }
                catch (Exception e){

                }


                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.BLUE);
                tv.setBackgroundResource(R.drawable.balloon_outgoing_normal);*/
            }
            else {
                LinearLayout llold=new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.LEFT;
                llold.setLayoutParams(params);
                params.setMargins(20,50,50,0);
                old_chat_layout.addView(llold);
//                llold.setBackground( getResources().getDrawable(R.drawable.balloon_incoming_normal));
                final ImageView imageView = new ImageView(getApplicationContext());
                imageView.setLayoutParams(params);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("bitmap","bitmap");
                        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        Intent i=new Intent(getApplicationContext(),GetBitmapActivity.class);
                        i.putExtra("bitmap",bm);
                        startActivity(i);
                    }
                });
                try {
                    final String bitmap = "http://www.bjain.com/doctor/upload/" + chat_photo;

                    Log.e("stringToBitmap", bitmap.toString());
                    llold.addView(imageView);
                    Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + chat_photo).resize(500,500).into(imageView);
                    llold.addView(imageView);
//                    imageView.get
                }
                catch (Exception e){

                }

            }
            scroll.post(new Runnable() {

                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            date=str[2]+"-"+str[1]+"-"+str[0];
        }
        catch (Exception e){
            date=s;
        }
        return date;
    }

    public String getmessagePhoto(String s,LinearLayout ll,String chat_photo){
        Log.d("shubham","photos");
        String date="";
        try{
            Log.d("shubham","photos");
            String[] str=s.split("::");
            scroll.fullScroll(View.FOCUS_DOWN);
            if (str[0].equalsIgnoreCase("doctor")){
                Log.d("shubham","photos");
                LinearLayout llold=new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.RIGHT;
                llold.setLayoutParams(params);
                params.setMargins(20,50,50,0);
                old_chat_layout.addView(llold);
//                llold.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
                final ImageView imageView = new ImageView(getApplicationContext());
                imageView.setLayoutParams(params);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("bitmap","bitmap");
                        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        bm.copy(Bitmap.Config.ARGB_8888,true);
//                        bm = imageView.getDrawable().();
                        bm = Bitmap.createScaledBitmap(bm, 350, 350, true);
//                        Bitmap bitmap1 = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.RGB_565);
                        /*FileOutputStream out = null;
                        try {
                            out = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"temp.png");
                            bm.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                            // PNG is a lossless format, the compression factor (100) is ignored
                            Intent i=new Intent(getApplicationContext(),GetBitmapActivity.class);
                            i.putExtra("bitmap",bm);
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }*/
                        /*SharedPreferences myPrefrence;
                        myPrefrence = getApplicationContext().getSharedPreferences("locked", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefrence.edit();
//                        editor.putString("namePreferance", itemNAme);
                        editor.putString("imagePreferance", encodeTobase64(bm));
                        editor.commit();*/
//                        bm=bm.createBitmap(500, 500,  Bitmap.Config.ARGB_8888);
                        Intent i=new Intent(getApplicationContext(),GetBitmapActivity.class);
                        i.putExtra("bitmap",bm);
                        startActivity(i);
                    }
                });
                try {
                    final String bitmap = "http://www.bjain.com/doctor/upload/" + chat_photo;

                    Log.e("stringToBitmap", bitmap.toString());
                    llold.addView(imageView);
                    Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + chat_photo).resize(500,500).into(imageView);

                    llold.addView(imageView);
//                    imageView.get
                }
                catch (Exception e){

                }


                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.BLUE);
                tv.setBackgroundResource(R.drawable.balloon_outgoing_normal);*/
            }
            else {
                LinearLayout llold=new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.LEFT;
                llold.setLayoutParams(params);
                params.setMargins(20,50,50,0);
                old_chat_layout.addView(llold);
//                llold.setBackground( getResources().getDrawable(R.drawable.balloon_incoming_normal));
                final ImageView imageView = new ImageView(getApplicationContext());
                imageView.setLayoutParams(params);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("bitmap","bitmap");
                        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        Intent i=new Intent(getApplicationContext(),GetBitmapActivity.class);
                        i.putExtra("bitmap",bm);
                        startActivity(i);
                    }
                });
                try {
                    final String bitmap = "http://www.bjain.com/doctor/upload/" + chat_photo;

                    Log.e("stringToBitmap", bitmap.toString());
                    llold.addView(imageView);
                    Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + chat_photo).resize(500,500).into(imageView);
                    llold.addView(imageView);
//                    imageView.get
                }
                catch (Exception e){

                }

            }
            scroll.post(new Runnable() {

                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            date=str[2]+"-"+str[1]+"-"+str[0];
        }
        catch (Exception e){
            date=s;
        }
        return date;
    }

    private Bitmap getBitmapFromURL(String imageUrl,LinearLayout llold,ImageView imageView) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            // Bitmap myBitmap = BitmapFactory.decodeStream(input);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            Bitmap b = Utils.decodeSampledBitmapFromStream(input, width, height);
            Log.e("bitmap",b.toString());
            imageView.setImageBitmap(b);
            llold.addView(imageView);
            return b;


        } catch (IOException e) {
            Log.e("bitmap",e.toString());
            e.printStackTrace();
            return null;
        }
    }


    public String getmessage(String s,LinearLayout ll){
        String date="";
        try{
            String[] str=s.split("::");
            scroll.fullScroll(View.FOCUS_DOWN);
            if (str[0].equals("doctor")){
                scroll.fullScroll(View.FOCUS_DOWN);
                if (!str[1].equals("@@Photo@@")) {
                    LinearLayout llold = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.RIGHT;
                    llold.setLayoutParams(params);
                    params.setMargins(20, 50, 50, 0);
                    old_chat_layout.addView(llold);
                    llold.setBackground(getResources().getDrawable(R.drawable.chat_incoming_green));
                    TextView tv = new TextView(getApplicationContext());
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.CENTER);
                    Typeface tf1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
                    tv.setTypeface(tf1);
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTextColor(Color.parseColor("#72A47F"));

                    tv.setText(str[1]);
                    llold.addView(tv);
                }
                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.BLUE);
                tv.setBackgroundResource(R.drawable.balloon_outgoing_normal);*/
            }
            else {
                LinearLayout llold=new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.LEFT;
                llold.setLayoutParams(params);
                old_chat_layout.addView(llold);
                llold.setBackground( getResources().getDrawable(R.drawable.chat_incoming_gray));

                TextView tv = new TextView(getApplicationContext());
                tv.setLayoutParams(params);
                tv.setTypeface(tf);
                tv.setTextColor(Color.parseColor("#686868"));
                params.setMargins(50,50,80,0);
                Typeface tf1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
                tv.setTypeface(tf1);
                tv.setGravity(Gravity.CENTER);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setText(str[1]);
                llold.addView(tv);
                scroll.fullScroll(View.FOCUS_DOWN);
                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setTextColor(Color.RED);
                tv.setBackgroundResource(R.drawable.balloon_incoming_normal);*/
            }
            scroll.post(new Runnable() {

                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                }
            });
            date=str[2]+"-"+str[1]+"-"+str[0];
        }
        catch (Exception e){
            date=s;
        }
        return date;
    }


    private void getRealPathFromUri(Context ctx, Uri uri) {

        String[] filePathColumn = { MediaStore.Files.FileColumns.DATA };

        Cursor cursor = ctx.getContentResolver().query(uri, filePathColumn,
                null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        selectedImagePath = cursor.getString(columnIndex);

        Log.e("picturePath", "picturePath : " + selectedImagePath);
        cursor.close();

    }

    private void doFileUpload2() {
        Log.i("RESPONSE", "file1");

        try {
            Log.i("RESPONSE", "file2");

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(ApiConfig.SEND_MESSAGE);
            MultipartEntity reqEntity = new MultipartEntity();
            File file1 = new File(selectedImagePath);
            String file_name=file1.getName();
            FileBody bin1 = new FileBody(file1);
            reqEntity.addPart("title", new StringBody(("Bjain Doctor")));
            reqEntity.addPart("chat_file", bin1);
            reqEntity.addPart("message", new StringBody("doctor::"+"@@Photo@@::"+"http://www.bjain.com/doctor/upload/"+file_name));
            reqEntity.addPart("chat_doc_id", new StringBody((PreferenceData.getId(getApplicationContext()))));
            reqEntity.addPart("chat_p_id", new StringBody(PreferenceData.getchatPatient_id(getApplicationContext())));
            reqEntity.addPart("token", new StringBody(PreferenceData.getPatient_Device_Token(getApplicationContext())));
            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            if (resEntity != null) {
//            LinearLayout ll=new LinearLayout(getApplicationContext());
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.gravity=Gravity.RIGHT;
//            ll.setLayoutParams(params);
//            params.setMargins(20,50,50,0);
//            chat_layout.addView(ll);
////            ll.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
//            scroll.fullScroll(View.FOCUS_DOWN);
//            ImageView iv = new ImageView(getApplicationContext());
//            iv.setLayoutParams(params);
////				            img.setImageBitmap(bmThumbnail);
//            iv.setImageBitmap(bmThumbnail);
////                            iv.setGravity(Gravity.CENTER);
////                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
////                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
//                           /* iv.setTextColor(Color.BLACK);
//                            tv.setText(et.getText().toString());*/
//            ll.addView(iv);
                Log.e("RESPONSE", response_str);
            }
            else{
                Log.i("RESPONSE", "file4");
            }
        } catch (Exception ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
    }
    // method for bitmap to base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public void setNotification1(final String device_token, final String msg) {
        new AsyncTask<String, String, String>() {
            ProgressDialog pd;
            ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
            String result;
            String tag;

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
//                d_chat = true;
                ChatActivity.p_chat = false;
                pd = new ProgressDialog(getApplicationContext());

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.hide();
            }

            @SuppressWarnings("deprecation")
            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub
                namevaluepair.add(new BasicNameValuePair("token", PreferenceData.getDoctor_Token(getApplicationContext())));
//                Log.d("sun",device_token);
                namevaluepair.add(new BasicNameValuePair("message", "patient::" + "urgent_chat"));
                namevaluepair.add(new BasicNameValuePair("chat_doc_id", PreferenceData.getId(getApplicationContext())));
                namevaluepair.add(new BasicNameValuePair("chat_p_id", PreferenceData.getPatientId(getApplicationContext())));
                namevaluepair.add(new BasicNameValuePair("title", PreferenceData.getName(getApplicationContext())));
                namevaluepair.add(new BasicNameValuePair("purgentchat","patient::" + msg));
                namevaluepair.add(new BasicNameValuePair("date", UtilsValidate.getCurrentDate()));
                namevaluepair.add(new BasicNameValuePair("time",  UtilsValidate.getCurrentDateTime()));
                try {

                    result = Util.executeHttpPost(ApiConfig.SEND_MESSAGE, namevaluepair);

                    Log.e("sunil", result.toString());
                } catch (Exception e) {

                    e.printStackTrace();

                }

                return result;


            }


            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);

                /*if (pd != null) {
                    pd.dismiss();
                }*/


                if (result != null) {
                    Log.e("sunil", "sent");

                }

            }
        }.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
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

    public String geturgentmessage(String s,LinearLayout ll){
        String date="";
        try{
            String[] str=s.split("::");
            scroll.fullScroll(View.FOCUS_DOWN);
            if (str[0].equals("doctor")){
                scroll.fullScroll(View.FOCUS_DOWN);
                if (!str[1].equals("@@Photo@@")) {
                    LinearLayout llold = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.RIGHT;
                    llold.setLayoutParams(params);
                    params.setMargins(20, 50, 50, 0);
                    old_chat_layout.addView(llold);
                    llold.setBackground(getResources().getDrawable(R.drawable.chat_incoming_green));
                    TextView tv = new TextView(getApplicationContext());
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.CENTER);
                    Typeface tf1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
                    tv.setTypeface(tf1);
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTextColor(Color.parseColor("#72A47F"));

                    tv.setText("urgent"+ "-" + str[1]);
                    llold.addView(tv);
                }
                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.BLUE);
                tv.setBackgroundResource(R.drawable.balloon_outgoing_normal);*/
            }
            else {
                LinearLayout llold=new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.LEFT;
                llold.setLayoutParams(params);
                old_chat_layout.addView(llold);
                llold.setBackground( getResources().getDrawable(R.drawable.chat_incoming_gray));

                TextView tv = new TextView(getApplicationContext());
                tv.setLayoutParams(params);
                tv.setTypeface(tf);
                tv.setTextColor(Color.parseColor("#686868"));
                params.setMargins(50,50,80,0);
                Typeface tf1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
                tv.setTypeface(tf1);
                tv.setGravity(Gravity.CENTER);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setText(str[1]);
                llold.addView(tv);
                scroll.fullScroll(View.FOCUS_DOWN);
                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setTextColor(Color.RED);
                tv.setBackgroundResource(R.drawable.balloon_incoming_normal);*/
            }
            scroll.post(new Runnable() {

                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            date=str[2]+"-"+str[1]+"-"+str[0];
        }
        catch (Exception e){
            date=s;
        }
        return date;
    }

}
