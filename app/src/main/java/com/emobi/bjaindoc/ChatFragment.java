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
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 8/20/2016.
 */
public class ChatFragment extends Fragment {
    LinearLayout old_chat_layout;
    TextView chat_msg;
    private int PICK_IMAGE_REQUEST = 1;
    Button upload, getUpload;
    String file,selectedImagePath;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    VideoView videoView;
    public static final int MEDIA_TYPE_IMAGE = 1;
    HttpEntity resEntity;
    public static final int MEDIA_TYPE_VIDEO = 2;
    ImageView imagemedia,imagemedia_photo;
    String filepath;
    String chat_message,note_date,chat_file;;
    ImageView image_urgent,send;
    ScrollView scroll;
    EditText et;
    public static Bitmap bmThumbnail,bmThumbnailforvideo;
    Typeface tf;
    LinearLayout linearchat, chat_layout;
    public static Boolean d_chat;
    String c_time;

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
            if (editable.length() == 0) {
//                send.setImageResource(R.drawable.ic_chat_send);
            } else {
//                send.setImageResource(R.drawable.ic_chat_send_active);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chat_msg = (TextView) view.findViewById(R.id.chat_txt);
        send = (ImageView) view.findViewById(R.id.send);
        et = (EditText) view.findViewById(R.id.msg_et);
        image_urgent=(ImageView)view.findViewById(R.id.image_urgent);
        scroll = (ScrollView) view.findViewById(R.id.scrollview);
        old_chat_layout = (LinearLayout) view.findViewById(R.id.old_chat_layout);
        chat_layout = (LinearLayout) view.findViewById(R.id.chat_layout);
        imagemedia_photo=(ImageView)view.findViewById(R.id

                .imagemedia_photo);
        if (PreferenceData.getPatientStatus(getActivity()).equalsIgnoreCase("deactive")){
            Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montez-Regular.ttf");
            chat_msg.setText("Your id is deactive, contact to your doctor");
            chat_msg.setTypeface(tf);
            chat_msg.setVisibility(View.VISIBLE);
            chat_layout.setVisibility(View.GONE);
//            imagemedia.setVisibility(View.GONE);
            imagemedia_photo.setVisibility(View.GONE);
            image_urgent.setVisibility(View.GONE);
        }
        et.addTextChangedListener(watcher1);
        String getDatetime=UtilsValidate.getCurrentDateTime();
        String time[]=getDatetime.split(" ");
        c_time=time[1];

        tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
        new CallServices().execute(ApiConfig.FCM_NOTIFICATION_CHAT);

        imagemedia_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK );
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        image_urgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urgent_chat_msg=et.getText().toString();

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

                    LinearLayout ll=new LinearLayout(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity=Gravity.RIGHT;
                    ll.setLayoutParams(params);
                    params.setMargins(20,50,50,0);
                    old_chat_layout.addView(ll);
                    ll.setBackground( getResources().getDrawable(R.drawable.chat_incoming_green));
                    scroll.fullScroll(View.FOCUS_DOWN);
                    TextView tv = new TextView(getActivity());
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.CENTER);
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTypeface(tf);
                    tv.setTextColor(Color.parseColor("#72A47F"));
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
                    Toast.makeText(getActivity(),"msg cannot be empty",Toast.LENGTH_LONG).show();
                }

            }

        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String normal_chat_msg=et.getText().toString();
                if (!et.getText().toString().equals("")) {
                    setNotification(PreferenceData.getDoctor_Token(getActivity()), normal_chat_msg);
                    /*TextView tv = new TextView(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity=Gravity.RIGHT;
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.RIGHT);
                    tv.setTextColor(Color.WHITE);
                    tv.setText("Me:-" + et.getText().toString());
                    chat_layout.addView(tv);*/
                    LinearLayout ll=new LinearLayout(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity=Gravity.RIGHT;
                    ll.setLayoutParams(params);
                    params.setMargins(20,50,50,0);
                    old_chat_layout.addView(ll);
                    ll.setBackground( getResources().getDrawable(R.drawable.chat_incoming_green));
                    TextView tv = new TextView(getActivity());
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.CENTER);
                    Typeface tf1= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
                    tv.setTypeface(tf1);
                    tv.setTextColor(Color.parseColor("#72A47F"));
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
                } else {
                    Toast.makeText(getActivity(), "msg cannot be empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mMessageReceiver, new IntentFilter("client"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mMessageReceiver);
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
                d_chat = true;
                ChatActivity.p_chat = false;
                pd = new ProgressDialog(getActivity());

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.hide();
            }

            @SuppressWarnings("deprecation")
            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub
                namevaluepair.add(new BasicNameValuePair("token", PreferenceData.getDoctor_Token(getActivity())));
//                Log.d("sun",device_token);
                namevaluepair.add(new BasicNameValuePair("message", "patient::"+"urgent_chat" +"::"+ msg));
                namevaluepair.add(new BasicNameValuePair("chat_doc_id", PreferenceData.getchatDocid(getActivity())));
                namevaluepair.add(new BasicNameValuePair("chat_p_id", PreferenceData.getPatientId(getActivity())));
                namevaluepair.add(new BasicNameValuePair("title", PreferenceData.getPatientName(getActivity())));
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
                pd = new ProgressDialog(getActivity().getApplicationContext());

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
            namevaluepair.add(new BasicNameValuePair("p_id", PreferenceData.getPatientId(getActivity())));
            namevaluepair.add(new BasicNameValuePair("doc_id", PreferenceData.getchatDocid(getActivity())));
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
                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("Post Method", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        Log.e("2", jsonObject2.toString());
                        /*String msg = jsonObject2.getString("message");

                        if (msg.equals("Record not available")){
                            Toast.makeText(getActivity(),"no record available",Toast.LENGTH_LONG).show();
                        }
                        else {*/
                        chat_message = jsonObject2.getString("chat_msg");


                        if (chat_message.contains("@@Photo@@")) {
                            Log.d("shubham","photos");
                            chat_file = jsonObject2.getString("chat_file");
                            getmessagePhoto(chat_message, old_chat_layout,chat_file);

                        }
                        if (!chat_message.contains("@@Photo@@")){
                            getmessage(chat_message, old_chat_layout);
                        }
                    }
                }

                catch (Exception e) {

                    e.printStackTrace();
                }
                /*mAdapterbroad = new UsersAdapterchat_Doc(contactDetails1,getActivity());
                mRecyclerView.setAdapter(mAdapterbroad);
                Log.e("stringArrayList", stringArrayList.toString());*/
            }

        }
    }

    public String getmessage(String s,LinearLayout ll){
        String date="";
        try{
            String[] str=s.split("::");
            if (!str[1].equalsIgnoreCase("Photo")) {
                scroll.fullScroll(View.FOCUS_DOWN);
                if (str[0].equals("doctor")) {
                    LinearLayout llold = new LinearLayout(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.LEFT;
                    llold.setLayoutParams(params);
                    params.setMargins(20, 50, 50, 0);
                    old_chat_layout.addView(llold);
                    llold.setBackground(getResources().getDrawable(R.drawable.chat_incoming_gray));
                    TextView tv = new TextView(getActivity());
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.CENTER);
                    Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
                    tv.setTypeface(tf);
                    tv.setTextColor(Color.parseColor("#72A47F"));
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText(str[1]);
                    llold.addView(tv);
                }
                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.BLUE);
                tv.setBackgroundResource(R.drawable.balloon_outgoing_normal);*/
                else {
                    LinearLayout llold = new LinearLayout(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.RIGHT;
                    llold.setLayoutParams(params);
                    old_chat_layout.addView(llold);
                    llold.setBackground(getResources().getDrawable(R.drawable.chat_incoming_green));

                    TextView tv = new TextView(getActivity());
                    tv.setLayoutParams(params);
                    params.setMargins(50, 50, 80, 0);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTypeface(tf);
                    tv.setTextColor(Color.parseColor("#72A47F"));
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTextColor(Color.BLACK);
                    tv.setText(str[1]);
                    llold.addView(tv);
//                scroll.fullScroll(View.FOCUS_DOWN);
                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setTextColor(Color.RED);
                tv.setBackgroundResource(R.drawable.balloon_incoming_normal);*/
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
            if (str[0].equals("doctor")){
                Log.d("shubham","photos");
                LinearLayout llold=new LinearLayout(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.LEFT;
                llold.setLayoutParams(params);
                params.setMargins(20,50,50,0);
                old_chat_layout.addView(llold);
//                llold.setBackground( getResources().getDrawable(R.drawable.balloon_incoming_normal));
                final ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(params);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("bitmap","bitmap");
                        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        Intent i=new Intent(getActivity(),GetBitmapActivity.class);
                        i.putExtra("bitmap",bm);
                        startActivity(i);
                    }
                });

                try {
//            settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
                    String bitmap = "http://www.bjain.com/doctor/upload/" + chat_photo;

                    Log.e("stringToBitmap", bitmap.toString());
//                    imageView.setImageResource(bitmap str[1]);
                    llold.addView(imageView);
//                    Picasso.with(getActivity()).load("http://www.bjain.com/doctor/upload/" + chat_photo).resize(100, 100).into(imageView);
//                    imageView.setImageBitmap(b);
                    Picasso.with(getActivity()).load("http://www.bjain.com/doctor/upload/" + chat_photo).resize(500,500).into(imageView);
                    llold.addView(imageView);
//                    getBitmapFromURL(bitmap,llold,imageView);
                }
                catch (Exception e){

                }


                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.BLUE);
                tv.setBackgroundResource(R.drawable.balloon_outgoing_normal);*/
            }
            else {
                LinearLayout llold=new LinearLayout(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.RIGHT;
                llold.setLayoutParams(params);
                params.setMargins(20,50,50,0);
                old_chat_layout.addView(llold);
//                llold.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
                final ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(params);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("bitmap","bitmap");
                        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        Intent i=new Intent(getActivity(),GetBitmapActivity.class);
                        i.putExtra("bitmap",bm);
                        startActivity(i);
                    }
                });
                try {
                    final String bitmap = "http://www.bjain.com/doctor/upload/" + chat_photo;

                    Log.e("stringToBitmap", bitmap.toString());
                    llold.addView(imageView);
                    Picasso.with(getActivity()).load("http://www.bjain.com/doctor/upload/" + chat_photo).resize(500,500).into(imageView);
                    llold.addView(imageView);
//                    imageView.get
                }
                catch (Exception e){

                }

//                scroll.fullScroll(View.FOCUS_DOWN);
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

    public void setNotification(final String device_token, final String msg) {
        new AsyncTask<String, String, String>() {
            ProgressDialog pd;
            ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
            String result;
            String tag;

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                d_chat = true;
                ChatActivity.p_chat = false;
                pd = new ProgressDialog(getActivity());

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.hide();
            }

            @SuppressWarnings("deprecation")
            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub
                namevaluepair.add(new BasicNameValuePair("token", PreferenceData.getDoctor_Token(getActivity())));
//                Log.d("sun",device_token);
                namevaluepair.add(new BasicNameValuePair("message", "patient::" + msg));
                namevaluepair.add(new BasicNameValuePair("chat_doc_id", PreferenceData.getchatDocid(getActivity())));
                namevaluepair.add(new BasicNameValuePair("chat_p_id", PreferenceData.getPatientId(getActivity())));
                namevaluepair.add(new BasicNameValuePair("title", PreferenceData.getPatientName(getActivity())));
                namevaluepair.add(new BasicNameValuePair("purgentchat","" ));
                namevaluepair.add(new BasicNameValuePair("date",UtilsValidate.getCurrentDate()));
                namevaluepair.add(new BasicNameValuePair("time",UtilsValidate.getCurrentDateTime()));
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


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("sunil", "patient, fragment:-" + message);
            String[] msgstr = message.split("::");
            try {
                if (msgstr[0].equals("doctor")) {
                    Log.e("phtosssss",msgstr[1]);
                    if (msgstr[1].equals("@@Photo@@")) {
                        Log.e("photochat_fragmen",msgstr[2]);
                        LinearLayout llold = new LinearLayout(getActivity());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.RIGHT;
                        llold.setLayoutParams(params);
                        params.setMargins(20, 50, 50, 0);
                        old_chat_layout.addView(llold);
//                llold.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
                        final ImageView imageView = new ImageView(getActivity());
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
                            Intent i=new Intent(getActivity(),GetBitmapActivity.class);
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
                        myPrefrence = getActivity().getSharedPreferences("locked", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefrence.edit();
//                        editor.putString("namePreferance", itemNAme);
                        editor.putString("imagePreferance", encodeTobase64(bm));
                        editor.commit();*/
//                        bm=bm.createBitmap(500, 500,  Bitmap.Config.ARGB_8888);
                                Intent i = new Intent(getActivity(), GetBitmapActivity.class);
                                i.putExtra("bitmap", bm);
                                startActivity(i);
                            }
                        });
                        try {
//                            final String bitmap = "http://www.bjain.com/doctor/upload/" + chat_photo;

//                            Log.e("stringToBitmap", bitmap.toString());
                            llold.addView(imageView);
                            Picasso.with(getActivity()).load(msgstr[2]).into(imageView);

                            llold.addView(imageView);
//                    imageView.get
                        } catch (Exception e) {

                        }


                /*tv.setText(movie.getName());
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.BLUE);
                tv.setBackgroundResource(R.drawable.balloon_outgoing_normal);*/
                    }
                   else if (!msgstr[1].equals("@@Photo@@")) {

                        LinearLayout ll = new LinearLayout(getActivity());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.LEFT;
                        ll.setLayoutParams(params);
                        old_chat_layout.addView(ll);
                        ll.setBackground(getResources().getDrawable(R.drawable.chat_incoming_gray));

                        TextView tv = new TextView(getActivity());
                        tv.setLayoutParams(params);
                        Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
                        tv.setTypeface(tf);
                        tv.setTextColor(Color.parseColor("#72A47F"));
                        tv.setGravity(Gravity.LEFT);
                        params.setMargins(20, 50, 50, 0);
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        tv.setText(msgstr[1]);
                        ll.addView(tv);

                    } else {

                    }
                    scroll.post(new Runnable() {

                        @Override
                        public void run() {
                            scroll.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                    scroll.fullScroll(View.FOCUS_DOWN);
                }
            }
            catch (Exception e) {

            }
//            if (message.equals("doctor::"+message)) {
//            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
//            Log.d("sunil", "message:-" + message);
//                /*TextView tv = new TextView(getActivity());
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.gravity=Gravity.LEFT;
//                tv.setLayoutParams(params);
//                tv.setGravity(Gravity.RIGHT);
//                tv.setText(message);
//                chat_layout.addView(tv);*/
//            LinearLayout ll=new LinearLayout(getActivity());
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.gravity= Gravity.LEFT;
//            ll.setLayoutParams(params);
//            chat_layout.addView(ll);
//            ll.setBackground( getResources().getDrawable(R.drawable.balloon_incoming_normal));
//
//            TextView tv = new TextView(getActivity());
//            tv.setLayoutParams(params);
//            tv.setGravity(Gravity.RIGHT);
//            tv.setGravity(Gravity.CENTER_HORIZONTAL);
//            tv.setTextColor(Color.BLACK);
//            tv.setText(message);
//            ll.addView(tv);
//            }

//            chat_msg.append("Doctor: "+message+"\n");
            //do other stuff here
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // After camera screen this code will excuted

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            Log.e("filePath", filePath.toString());
//            selectedImagePath=filePath.getPath();
            try {
                //Getting the Bitmap from Gallery
                bmThumbnail = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                LinearLayout ll=new LinearLayout(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.RIGHT;
                ll.setLayoutParams(params);
                params.setMargins(20,50,50,0);
                old_chat_layout.addView(ll);
//            ll.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
                scroll.fullScroll(View.FOCUS_DOWN);
                ImageView iv = new ImageView(getActivity());
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
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {


                            }
                        });
                    }
                });
                thread2.start();
            } else {
                Toast.makeText(getActivity(),
                        "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
            }


            getRealPathFromUri(getActivity(), filePath);

            file = filePath.getPath();
        }

        else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
//            ImageView img = (ImageView) findViewById(R.id.image);
            if (resultCode == getActivity().RESULT_OK) {
                Uri selectedImageUri = data.getData();
                Log.e("filePath",selectedImageUri.getPath());
                filepath=selectedImageUri.getPath();
                bmThumbnailforvideo = ThumbnailUtils.createVideoThumbnail(getActivity().getPackageCodePath(), MediaStore.Images.Thumbnails.MINI_KIND);
//                Log.e("video",bmThumbnailforvideo.toString());
                LinearLayout ll=new LinearLayout(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.RIGHT;
                ll.setLayoutParams(params);
                params.setMargins(20,50,50,0);
                old_chat_layout.addView(ll);
//            ll.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
                scroll.fullScroll(View.FOCUS_DOWN);
                ImageView iv = new ImageView(getActivity());
                iv.setLayoutParams(params);
//				            img.setImageBitmap(bmThumbnail);
                iv.setImageBitmap(bmThumbnailforvideo);
//                            iv.setGravity(Gravity.CENTER);
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                           /* iv.setTextColor(Color.BLACK);
                            tv.setText(et.getText().toString());*/
                ll.addView(iv);
                if (filepath != null) {
                    Toast.makeText(getActivity(),
                            "file path existing!", Toast.LENGTH_LONG).show();
                    Thread thread2 = new Thread(new Runnable() {
                        public void run() {
                            doFileUpload1();
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {


                                }
                            });
                        }


                    });
                    thread2.start();
                } else {
                    Toast.makeText(getActivity(),
                            "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
                }

//                setNotification(UsersAdapter.device_token, filePath);
                /*Intent i=new Intent(getActivity(),UploadActivity.class);
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




            } else if (resultCode == getActivity().RESULT_CANCELED) {


                // User cancelled the video capture
                Toast.makeText(getActivity(), "User cancelled the video capture.",
                        Toast.LENGTH_LONG).show();

            } else {


                // Video capture failed, advise user
                Toast.makeText(getActivity(), "Video capture failed.",
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
            reqEntity.addPart("message", new StringBody("patient::"+"@@Video@@::"+"http://www.bjain.com/doctor/upload/"+file_name));
            reqEntity.addPart("chat_doc_id", new StringBody((PreferenceData.getchatDocid(getActivity()))));
            reqEntity.addPart("chat_p_id", new StringBody(PreferenceData.getId(getActivity())));
            reqEntity.addPart("token", new StringBody(PreferenceData.getDoctor_Token(getActivity())));
            reqEntity.addPart("date", new StringBody(UtilsValidate.getCurrentDate()));
            reqEntity.addPart("time", new StringBody(UtilsValidate.getCurrentDateTime()));
            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            if (resEntity != null) {
                Log.e("RESPONSE", response_str);
                LinearLayout ll=new LinearLayout(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.RIGHT;
                ll.setLayoutParams(params);
//                params.setMargins(20,50,50,0);
                old_chat_layout.addView(ll);
                ll.setBackground( getResources().getDrawable(R.drawable.chat_incoming_green));
                scroll.fullScroll(View.FOCUS_DOWN);
                ImageView iv = new ImageView(getActivity());
                iv.setLayoutParams(params);
//				            img.setImageBitmap(bmThumbnail);
                iv.setImageBitmap(bmThumbnailforvideo);
//                            iv.setGravity(Gravity.CENTER);
//                    tv.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                           /* iv.setTextColor(Color.BLACK);
                            tv.setText(et.getText().toString());*/
                ll.addView(iv);
                getActivity().runOnUiThread(new Runnable() {
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
            Log.d("file_name",file_name);
            FileBody bin1 = new FileBody(file1);
            reqEntity.addPart("title", new StringBody(("Bjain Doctor")));
            reqEntity.addPart("chat_file", bin1);
            reqEntity.addPart("message", new StringBody("patient::"+"@@Photo@@::"+"http://www.bjain.com/doctor/upload/"+file_name));
            reqEntity.addPart("chat_doc_id", new StringBody((PreferenceData.getchatDocid(getActivity()))));
            reqEntity.addPart("chat_p_id", new StringBody(PreferenceData.getId(getActivity())));
            reqEntity.addPart("token", new StringBody(PreferenceData.getDoctor_Token(getActivity())));
            reqEntity.addPart("date", new StringBody(UtilsValidate.getCurrentDate()));
            reqEntity.addPart("time", new StringBody(UtilsValidate.getCurrentDateTime()));
            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            if (resEntity != null) {
//            LinearLayout ll=new LinearLayout(getActivity());
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.gravity=Gravity.RIGHT;
//            ll.setLayoutParams(params);
//            params.setMargins(20,50,50,0);
//            chat_layout.addView(ll);
////            ll.setBackground( getResources().getDrawable(R.drawable.balloon_outgoing_normal));
//            scroll.fullScroll(View.FOCUS_DOWN);
//            ImageView iv = new ImageView(getActivity());
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                Log.e("clik","clik");

//            new FileUpload().execute();
                if (!et.getText().toString().equals("")) {
                    setNotification(SignWithPatient.p_doctor_device_token, et.getText().toString());
                    /*TextView tv = new TextView(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity=Gravity.RIGHT;
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.RIGHT);
                    tv.setTextColor(Color.WHITE);
                    tv.setText("Me:-" + et.getText().toString());
                    chat_layout.addView(tv);*/
                    LinearLayout ll = new LinearLayout(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.RIGHT;
                    ll.setLayoutParams(params);
                    old_chat_layout.addView(ll);
                    ll.setBackground(getResources().getDrawable(R.drawable.chat_incoming_green));

                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params1.gravity = Gravity.RIGHT;
                    params1.setMargins(20, 50, 50, 0);
                    TextView tv = new TextView(getActivity());
                    tv.setLayoutParams(params1);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
                    tv.setTypeface(tf);
                    tv.setTextColor(Color.parseColor("#72A47F"));
                    tv.setText(et.getText().toString());
                    ll.addView(tv);
                    scroll.fullScroll(View.FOCUS_DOWN);
                    et.setText("");
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
