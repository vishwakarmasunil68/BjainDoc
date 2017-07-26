package com.emobi.bjaindoc.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.emobi.bjaindoc.ApiConfig;
import com.emobi.bjaindoc.DoctorAccount;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.pojo.chat.ChatPOJO;
import com.emobi.bjaindoc.pojo.chat.ChatPatientPOJO;
import com.emobi.bjaindoc.pojo.chat.ChatPatientResultPOJO;
import com.emobi.bjaindoc.pojo.chat.ChatResultPOJO;
import com.emobi.bjaindoc.pojo.member.MemberResultPOJO;
import com.emobi.bjaindoc.pojo.patient.PatientResultPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.emobi.bjaindoc.services.WebUploadService;
import com.emobi.bjaindoc.utls.FileUtil;
import com.emobi.bjaindoc.utls.Pref;
import com.emobi.bjaindoc.utls.StringUtils;
import com.emobi.bjaindoc.utls.ToastClass;
import com.emobi.bjaindoc.widgets.ui.ViewProxy;
import com.google.common.io.Files;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class ChatActivityPat extends AppCompatActivity implements WebServicesCallBack, View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private final static String OLD_CHAT_API = "old_chat_api";
    private final static String SEND_CHAT_API = "send_chat_api";
    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_VIDEO_REQUEST = 102;
    private static final int VIDEO_CAPTURE = 101;
    private static final int CAMERA_REQUEST = 1888;


    public String image_path_string = "";

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_attach_photo)
    ImageView iv_attach_photo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.scroll_layout)
    ScrollView scroll_layout;
    @BindView(R.id.et_msg)
    EditText et_msg;
    @BindView(R.id.send)
    ImageView send;
    @BindView(R.id.chat_frame_layout)
    FrameLayout chat_frame_layout;

    @BindView(R.id.reveal_items)
    LinearLayout mRevealView;


    @BindView(R.id.ll_camera)
    LinearLayout ll_camera;
    @BindView(R.id.ll_gallery)
    LinearLayout ll_gallery;
    @BindView(R.id.ll_video)
    LinearLayout ll_video;
    @BindView(R.id.ll_audio)
    LinearLayout ll_audio;
    @BindView(R.id.iv_camera_edit)
    ImageView iv_camera_edit;


    boolean hidden = true;


    List<ChatResultPOJO> list_chat_messages;

    LinearLayout final_chat_ll;
    PatientResultPOJO patientResultPOJO;
    ChatResultPOJO chatResultPOJO;


    //rec butn

    private TextView recordTimeText;
    private ImageButton audioSendButton;
    private View recordPanel;
    private View slideText;
    private float startedDraggingX = -1;
    private float distCanMove = dp(80);
    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private Timer timer;

    MemberResultPOJO memberResultPOJO;
    String basic_m_id = "";
    ChatResultPOJO chatResultPOJO1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        ButterKnife.bind(this);
//        Bundle bundle=getIntent().getExtras();
//        if(bundle!=null){
//            String result=bundle.getString("result");
//            Log.d(TAG,"result:-"+result);
//        }
        mRevealView.setVisibility(View.INVISIBLE);
        recordPanel = findViewById(R.id.record_panel);
        recordTimeText = (TextView) findViewById(R.id.recording_time_text);
        slideText = findViewById(R.id.slideText);
        audioSendButton = (ImageButton) findViewById(R.id.chat_audio_send_button);
        TextView textView = (TextView) findViewById(R.id.slideToCancelTextView);
        textView.setText("SlideToCancel");

        patientResultPOJO = (PatientResultPOJO) getIntent().getSerializableExtra("patient");
        memberResultPOJO = (MemberResultPOJO) getIntent().getSerializableExtra("member");
        if (patientResultPOJO != null) {
            chatResultPOJO = (ChatResultPOJO) getIntent().getSerializableExtra("chat");
//            if(chatResultPOJO!=null){
//
//            }
            if (memberResultPOJO != null) {
                basic_m_id = memberResultPOJO.getM_id();
            }
            Log.d(TAG, "patient:-" + patientResultPOJO.toString());
        } else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String type = bundle.getString("type");
                String message = bundle.getString("result");
                Log.d(TAG, "type:-" + type);
                Log.d(TAG, "result:-" + message);
                Gson gson = new Gson();
                chatResultPOJO1 = gson.fromJson(message, ChatResultPOJO.class);
            }
        }
        send.setOnClickListener(this);
        iv_attach_photo.setOnClickListener(this);
//        parseOldChatData(Pref.GetStringPref(getApplicationContext(), StringUtils.OLD_URGENT_CHAT, ""));
        callOldChat();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ll_camera.setOnClickListener(this);
        ll_gallery.setOnClickListener(this);
        ll_video.setOnClickListener(this);
        ll_audio.setOnClickListener(this);
        iv_camera_edit.setOnClickListener(this);


        et_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_msg.getText().toString().length() > 0) {
                    send.setVisibility(View.VISIBLE);
                    audioSendButton.setVisibility(View.GONE);
                } else {
                    send.setVisibility(View.GONE);
                    audioSendButton.setVisibility(View.VISIBLE);
                }
            }
        });

        audioSendButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showRecordView(view, motionEvent);
                return true;
            }
        });

        scroll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hidden) {
                    startAttachAnimation();
                }
            }
        });
    }

    public void showRecordView(View view, MotionEvent motionEvent) {
        recordPanel.setVisibility(View.VISIBLE);
        chat_frame_layout.setVisibility(View.GONE);
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) slideText
                    .getLayoutParams();
            params.leftMargin = dp(30);
            slideText.setLayoutParams(params);
            ViewProxy.setAlpha(slideText, 1);
            startedDraggingX = -1;
            // startRecording();
            startrecord();
            audioSendButton.getParent()
                    .requestDisallowInterceptTouchEvent(true);
            recordPanel.setVisibility(View.VISIBLE);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP
                || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            startedDraggingX = -1;
            recordPanel.setVisibility(View.GONE);
            chat_frame_layout.setVisibility(View.VISIBLE);
            stoprecord();
            // stopRecording(true);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            float x = motionEvent.getX();
            if (x < -distCanMove) {
//                stoprecord();
                cancelRecording();
                // stopRecording(false);
            }
            x = x + ViewProxy.getX(audioSendButton);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) slideText
                    .getLayoutParams();
            if (startedDraggingX != -1) {
                float dist = (x - startedDraggingX);
                params.leftMargin = dp(30) + (int) dist;
                slideText.setLayoutParams(params);
                float alpha = 1.0f + dist / distCanMove;
                if (alpha > 1) {
                    alpha = 1;
                } else if (alpha < 0) {
                    alpha = 0;
                }
                ViewProxy.setAlpha(slideText, alpha);
            }
            if (x <= ViewProxy.getX(slideText) + slideText.getWidth()
                    + dp(30)) {
                if (startedDraggingX == -1) {
                    startedDraggingX = x;
                    distCanMove = (recordPanel.getMeasuredWidth()
                            - slideText.getMeasuredWidth() - dp(48)) / 2.0f;
                    if (distCanMove <= 0) {
                        distCanMove = dp(80);
                    } else if (distCanMove > dp(80)) {
                        distCanMove = dp(80);
                    }
                }
            }
            if (params.leftMargin > dp(30)) {
                params.leftMargin = dp(30);
                slideText.setLayoutParams(params);
                ViewProxy.setAlpha(slideText, 1);
                startedDraggingX = -1;
            }
        }
        view.onTouchEvent(motionEvent);
    }

    //    MediaRecorder myAudioRecorder ;
    private void startrecord() {
        // TODO Auto-generated method stub
        MediaRecorderReady();
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            ToastClass.ShowShortToast(getApplicationContext(), "Recording started");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        startTime = SystemClock.uptimeMillis();
        timer = new Timer();
        MyTimerTask myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 1000);
        vibrate();
    }

    MediaRecorder mediaRecorder;
    String audioFilename = FileUtil.getAudioChatDir() + File.separator + System.currentTimeMillis() + ".3gp";

    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(audioFilename);
    }

    private void stoprecord() {
        // TODO Auto-generated method stub
        if (timer != null) {
            timer.cancel();
            try {
                if (mediaRecorder != null) {
                    mediaRecorder.stop();

                    ToastClass.ShowShortToast(getApplicationContext(), "Recording Completed");
                    sendFileMessage("audio", "", audioFilename);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (recordTimeText.getText().toString().equals("00:00")) {
            return;
        }
        recordPanel.setVisibility(View.GONE);
        recordTimeText.setText("00:00");
        vibrate();
    }

    private void cancelRecording() {
        // TODO Auto-generated method stub
        if (timer != null) {
            timer.cancel();
            try {
                if (mediaRecorder != null) {
                    mediaRecorder.stop();

                    ToastClass.ShowShortToast(getApplicationContext(), "Recording Cancelled");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (recordTimeText.getText().toString().equals("00:00")) {
            return;
        }
        recordPanel.setVisibility(View.GONE);
        recordTimeText.setText("00:00");
        vibrate();
    }

    private void vibrate() {
        // TODO Auto-generated method stub
        try {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int dp(float value) {
        return (int) Math.ceil(1 * value);
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            final String hms = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(updatedTime)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(updatedTime)),
                    TimeUnit.MILLISECONDS.toSeconds(updatedTime)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(updatedTime)));
            long lastsec = TimeUnit.MILLISECONDS.toSeconds(updatedTime)
                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                    .toMinutes(updatedTime));
            System.out.println(lastsec + " hms " + hms);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (recordTimeText != null)
                            recordTimeText.setText(hms);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.CHAT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra("message");
            Log.d(TAG, "chatresult:-" + result);
            try {
                try {
                    Gson gson = new Gson();
                    ChatResultPOJO chatResultPOJO = gson.fromJson(result, ChatResultPOJO.class);
                    if (memberResultPOJO != null) {
                        if (!chatResultPOJO.getChat_m_id().equals("0")) {
                            if (chatResultPOJO.getChat_m_id().equals(memberResultPOJO.getM_id())) {
                                inflateSingleChatMessage(chatResultPOJO);
                            }
                        }
                    } else {
                        if (chatResultPOJO.getChat_m_id().equals("0")) {
                            if (chatResultPOJO.getChat_p_id().equals(patientResultPOJO.getP_id())) {
                                inflateSingleChatMessage(chatResultPOJO);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }

            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    };


    public void callOldChat() {
        if (memberResultPOJO == null && patientResultPOJO == null) {
            if (chatResultPOJO1 != null) {
                if (chatResultPOJO1.getChat_m_id().equals("0")) {
                    getPatientOldChat(chatResultPOJO1.getChat_p_id(), chatResultPOJO1.getChat_doc_id());
                    patientResultPOJO = new PatientResultPOJO();
                    patientResultPOJO.setP_id(chatResultPOJO1.getChat_p_id());
                    patientResultPOJO.setDoc_id(chatResultPOJO1.getChat_doc_id());
                    patientResultPOJO.setP_device_token("");
                    patientResultPOJO.setP_name("");
                } else {
                    getMemberOldChat(chatResultPOJO1.getChat_p_id(), chatResultPOJO1.getChat_doc_id(),
                            chatResultPOJO1.getChat_m_id());

                    patientResultPOJO = new PatientResultPOJO();
                    patientResultPOJO.setP_id(chatResultPOJO1.getChat_p_id());
                    patientResultPOJO.setDoc_id(chatResultPOJO1.getChat_doc_id());
                    patientResultPOJO.setP_device_token("");
                    patientResultPOJO.setP_name("");


                    memberResultPOJO = new MemberResultPOJO();
                    memberResultPOJO.setM_id(chatResultPOJO1.getChat_m_id());
                    memberResultPOJO.setDoc_id(chatResultPOJO1.getChat_doc_id());
                    memberResultPOJO.setM_name("");
                }
            } else {
                finish();
            }
        } else {
            if (memberResultPOJO != null) {
                getMemberOldChat(memberResultPOJO.getP_id(), memberResultPOJO.getDoc_id(),
                        memberResultPOJO.getM_id());
            } else {
                getPatientOldChat(patientResultPOJO.getP_id(), patientResultPOJO.getDoc_id());
            }
        }
    }

    public void getMemberOldChat(String p_id, String doc_id, String chat_m_id) {
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("p_id", p_id));
            nameValuePairs.add(new BasicNameValuePair("doc_id", doc_id));
            nameValuePairs.add(new BasicNameValuePair("chat_m_id", chat_m_id));
            new WebServiceBase(nameValuePairs, this, OLD_CHAT_API).execute(ApiConfig.GET_MEMBER_OLD_CHAT);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    public void getPatientOldChat(String p_id, String doc_id) {
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("p_id", p_id));
            nameValuePairs.add(new BasicNameValuePair("doc_id", doc_id));
            new WebServiceBase(nameValuePairs, this, OLD_CHAT_API).execute(ApiConfig.FCM_NOTIFICATION_CHAT);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case OLD_CHAT_API:
                parseOldChatData(response);
                break;
            case SEND_CHAT_API:
                parseNewSendMessage(response);
                break;
        }
    }

    public void parseOldChatData(String response) {
        evaluateChatLength();
        Log.d(TAG, "old chat:-" + response);
        Pref.SetStringPref(getApplicationContext(), StringUtils.OLD_URGENT_CHAT, response);
        try {
            Gson gson = new Gson();
            ChatPOJO chatPOJO = gson.fromJson(response, ChatPOJO.class);
            if (chatPOJO.getSuccess().equals("true")) {
                showChatMessages(chatPOJO.getChatResultPOJOs());
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }


    public void showChatMessages(final List<ChatResultPOJO> list_chat) {

        list_chat_messages = list_chat;
        List<String> list_dates = new ArrayList<>();
        Set<String> set_date = new HashSet<>();
        for (ChatResultPOJO chatResultPOJO : list_chat) {
            list_dates.add(chatResultPOJO.getDate());
        }
        set_date.addAll(list_dates);
        List<String> list_string = new ArrayList<>();
        list_string.addAll(set_date);

        Collections.sort(list_string, new Comparator<String>() {

            @Override
            public int compare(String arg0, String arg1) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                int compareResult = 0;
                try {
                    Date arg0Date = format.parse(arg0);
                    Date arg1Date = format.parse(arg1);
                    compareResult = arg0Date.compareTo(arg1Date);
                } catch (Exception e) {
                    e.printStackTrace();
                    compareResult = arg0.compareTo(arg1);
                }
                return compareResult;
            }
        });


        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        for (String s : list_string) {

            JSONObject date_object = new JSONObject();
            JSONArray date_array = new JSONArray();

            for (ChatResultPOJO chatResultPOJO : list_chat) {
                if (s.equals(chatResultPOJO.getDate())) {
                    try {
                        JSONObject object1 = new JSONObject();
                        object1.put("chat_p_id", chatResultPOJO.getChat_p_id());
                        object1.put("chat_doc_id", chatResultPOJO.getChat_doc_id());
                        object1.put("chat_m_id", chatResultPOJO.getChat_m_id());
                        object1.put("chat_title", chatResultPOJO.getChat_title());
                        object1.put("chat_msg", chatResultPOJO.getChat_msg());
                        object1.put("date", chatResultPOJO.getDate());
                        object1.put("time", chatResultPOJO.getTime());
                        object1.put("chat_file", chatResultPOJO.getChat_file());
                        object1.put("thumb_file", chatResultPOJO.getThumb_file());
                        object1.put("direction", chatResultPOJO.getDirection());
                        object1.put("message_type", chatResultPOJO.getMessage_type());
                        object1.put("chat_id", chatResultPOJO.getChat_id());

                        date_array.put(object1);
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                    }
                }
            }
            try {
                date_object.put("date", s);
                date_object.put("result", date_array);

                array.put(date_object);
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }

        try {
            if (array.length() > 0) {
                object.put("success", true);
                object.put("result", array);
            } else {
                object.put("success", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "json:-" + object.toString());
        try {
            Gson gson = new Gson();
            ChatPatientPOJO chatPatientPOJO = gson.fromJson(object.toString(), ChatPatientPOJO.class);
            inflateChatMessage(chatPatientPOJO.getList_chat_patient_result_pojo());
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }


    public void parseNewSendMessage(String response) {
        Log.d(TAG, "new chat msg:-" + response);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                sendMessage();
                break;
            case R.id.iv_attach_photo:
//                sendImageMessageClick();
                startAttachAnimation();
                break;
            case R.id.ll_camera:
                startCamera();
                startAttachAnimation();
                break;
            case R.id.iv_camera_edit:
                startCamera();
                break;
            case R.id.ll_audio:
//                sendVideoMessageType();
                startAttachAnimation();
                break;
            case R.id.ll_video:
                showVideoDialog();
                startAttachAnimation();
                break;
            case R.id.ll_gallery:
                sendImageMessageClick();
                startAttachAnimation();
                break;
        }
    }


    public void sendVideoMessageType() {
        String file_name = FileUtil.getvideoChatDir() + File.separator + "1490787148660.mp4";
        File f = new File(file_name);
        if (f.exists()) {
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(f.toString(), MediaStore.Video.Thumbnails.MINI_KIND);
//            iv_image.setImageBitmap(thumb);

            String storage_file = FileUtil.BASE_FILE + File.separator + System.currentTimeMillis() + ".png";
            FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(new File(storage_file));
                Log.d(TAG, "taking photos");
                thumb.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
//                sendFileMessage("image", file_name.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "file not exist");
        }
    }


    public void showVideoDialog() {
        final Dialog dialog1 = new Dialog(ChatActivityPat.this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_video_option);
        dialog1.setTitle("Video");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ll_video_camera = (LinearLayout) dialog1.findViewById(R.id.ll_video_camera);
        LinearLayout ll_open_gallery = (LinearLayout) dialog1.findViewById(R.id.ll_open_gallery);
        Button btn_cancel = (Button) dialog1.findViewById(R.id.btn_cancel);

        ll_video_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openvideoCamera();
                dialog1.dismiss();
            }
        });
        ll_open_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVideoGallery();
                dialog1.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
    }

    public void openVideoGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }

    String video_file = "";

    public void openvideoCamera() {
        File mediaFile = new File(FileUtil.getvideoChatDir() + File.separator + System.currentTimeMillis() + ".mp4");
        video_file = mediaFile.toString();
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        Uri videoUri = Uri.fromFile(mediaFile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.emobi.bjaindoc.fileProvider", mediaFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        }
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }


    String pictureImagePath = "";

    public void startCamera() {
        String strMyImagePath = Environment.getExternalStorageDirectory() + File.separator + "temp.png";

        pictureImagePath = strMyImagePath;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.emobi.bjaindoc.fileProvider", file);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        } else {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        }
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void startAttachAnimation() {
        int cx = (mRevealView.getLeft() + mRevealView.getRight());
//                int cy = (mRevealView.getTop() + mRevealView.getBottom())/2;
        int cy = mRevealView.getTop();

        int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {


            SupportAnimator animator =
                    ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(800);

            SupportAnimator animator_reverse = animator.reverse();

            if (hidden) {
                mRevealView.setVisibility(View.VISIBLE);
                animator.start();
                hidden = false;
            } else {
                animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;

                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });
                animator_reverse.start();

            }
        } else {
            if (hidden) {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
                hidden = false;

            } else {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;
                    }
                });
                anim.start();

            }
        }

    }


    public void sendMessage() {
        if (et_msg.getText().toString().length() > 0) {

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = sdf.format(d).split(" ")[0];
            String time = sdf.format(d).split(" ")[1];

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("chat_p_id", patientResultPOJO.getP_id()));
            nameValuePairs.add(new BasicNameValuePair("chat_doc_id", patientResultPOJO.getDoc_id()));
            nameValuePairs.add(new BasicNameValuePair("chat_m_id", basic_m_id));
//            if(memberResultPOJO!=null) {
//                nameValuePairs.add(new BasicNameValuePair("chat_m_id", memberResultPOJO.getM_id()));
//            }
//            else{
//                nameValuePairs.add(new BasicNameValuePair("chat_m_id", ""));
//            }
            nameValuePairs.add(new BasicNameValuePair("token", patientResultPOJO.getP_device_token()));
            nameValuePairs.add(new BasicNameValuePair("date", date));
            nameValuePairs.add(new BasicNameValuePair("time", time));
            nameValuePairs.add(new BasicNameValuePair("chat_msg", et_msg.getText().toString()));
            if (memberResultPOJO != null) {
                nameValuePairs.add(new BasicNameValuePair("chat_title", memberResultPOJO.getM_name()));
            } else {
                nameValuePairs.add(new BasicNameValuePair("chat_title", patientResultPOJO.getP_name()));
            }
            nameValuePairs.add(new BasicNameValuePair("message_type", "text"));
            nameValuePairs.add(new BasicNameValuePair("file", ""));
            nameValuePairs.add(new BasicNameValuePair("direction", "false"));
            new WebServiceBase(nameValuePairs, this, SEND_CHAT_API, false).execute(ApiConfig.DOCTOR_CHAT_API);

//            String m_id="";
//            if(memberResultPOJO!=null){
//                m_id=memberResultPOJO.getM_id();
//            }

            ChatResultPOJO pojo = new ChatResultPOJO("", patientResultPOJO.getP_id(),
                    patientResultPOJO.getDoc_id(),
                    basic_m_id,
                    date, time,
                    et_msg.getText().toString(),
                    "normal message", "", "", "normal", "false");
//            list_chat_messages.add(pojo);
//            showChatMessages(list_chat_messages);
            inflateSingleChatMessage(pojo);
        } else {
            ToastClass.ShowLongToast(getApplicationContext(), "Please Enter message");
        }
        et_msg.setText("");
    }


    public void sendImageMessageClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                String selectedImagePath = getPath(
                        ChatActivityPat.this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TAG, "selected path:-" + selectedImagePath);
                    sendFileMessage("image", "", selectedImagePath);


//                    Bitmap bmImg = BitmapFactory.decodeFile(image_path_string);
//                    iv_profile.setImageBitmap(bmImg);
//                    Preferences.setCardImagePath(getApplicationContext(),selectedImagePath);

//                    startActivity(new Intent(SelectImageActivity.this,CardActivity.class));
                } else {
                    Toast.makeText(ChatActivityPat.this, "File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            Log.d(TAG, photo.toString());

            File imgFile = new File(pictureImagePath);
            if (imgFile.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(pictureImagePath);
                bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 4, bmp.getHeight() / 4, false);
                String strMyImagePath = FileUtil.getChatDir();
                File file_name = new File(strMyImagePath + File.separator + System.currentTimeMillis() + ".png");
                FileOutputStream fos = null;

                try {
                    fos = new FileOutputStream(file_name);
                    Log.d(TAG, "taking photos");
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    sendFileMessage("image", "", file_name.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "Video saved to:\n" +
//                        data.getData(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "video file path:-" + video_file);

                sendVideoMessage(video_file);
                video_file = "";
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == PICK_VIDEO_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                String selectedImagePath = getPath(
                        ChatActivityPat.this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TAG, "selected path:-" + selectedImagePath);
//                    sendFileMessage("video", selectedImagePath);

                    sendVideoMessage(selectedImagePath);
//                    Bitmap bmImg = BitmapFactory.decodeFile(image_path_string);
//                    iv_profile.setImageBitmap(bmImg);
//                    Preferences.setCardImagePath(getApplicationContext(),selectedImagePath);

//                    startActivity(new Intent(SelectImageActivity.this,CardActivity.class));
                } else {
                    Toast.makeText(ChatActivityPat.this, "File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
        }
    }

    public void sendVideoMessage(String file_path) {
//        String file_name=FileUtil.getvideoChatDir()+ File.separator+"1490787148660.mp4";
        File f = new File(file_path);
        if (f.exists()) {
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(f.toString(), MediaStore.Video.Thumbnails.MINI_KIND);
//            iv_image.setImageBitmap(thumb);

            String storage_file = FileUtil.BASE_FILE + File.separator + System.currentTimeMillis() + ".png";
            FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(new File(storage_file));
                Log.d(TAG, "taking photos");
                thumb.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();

                try {
                    File srcFile = new File(file_path);
                    String ext = Files.getFileExtension(file_path);
                    File destFile = new File(FileUtil.getvideoChatDir() + File.separator + System.currentTimeMillis() + "." + ext);
                    if (copyFile(srcFile, destFile)) {
                        sendFileMessage("video", storage_file, destFile.toString());
                    } else {
                        ToastClass.ShowShortToast(getApplicationContext(), "File Sending Failed");
                    }
                } catch (Exception e) {
                    ToastClass.ShowShortToast(getApplicationContext(), "File Sending Failed");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "file not exist");
        }
    }

    public void sendFileMessage(String type, String thumb, String image_path) {

//        String ext = Files.getFileExtension(image_path);
//        Log.d(TAG, "ext:-" + ext);
        if (image_path.length() > 0) {
            try {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = sdf.format(d).split(" ")[0];
                String time = sdf.format(d).split(" ")[1];
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                FileBody bin1 = new FileBody(new File(image_path));
                reqEntity.addPart("chat_p_id", new StringBody(patientResultPOJO.getP_id()));
                reqEntity.addPart("chat_doc_id", new StringBody(patientResultPOJO.getDoc_id()));
                if (memberResultPOJO != null) {
                    reqEntity.addPart("chat_m_id", new StringBody(memberResultPOJO.getM_id()));
                } else {
                    reqEntity.addPart("chat_m_id", new StringBody(""));
                }
                reqEntity.addPart("token", new StringBody(patientResultPOJO.getP_device_token()));
                reqEntity.addPart("date", new StringBody(date));
                reqEntity.addPart("time", new StringBody(time));
                reqEntity.addPart("chat_msg", new StringBody(""));
                if (memberResultPOJO != null) {
                    reqEntity.addPart("chat_title", new StringBody(memberResultPOJO.getM_name()));
                } else {
                    reqEntity.addPart("chat_title", new StringBody(patientResultPOJO.getP_name()));
                }
                reqEntity.addPart("message_type", new StringBody(type));
                reqEntity.addPart("file", bin1);
                if (thumb.length() > 0) {
                    FileBody bin = new FileBody(new File(thumb));
                    reqEntity.addPart("thumb", bin);
                } else {
                    reqEntity.addPart("thumb", new StringBody(""));
                }
                reqEntity.addPart("direction", new StringBody("false"));
                new WebUploadService(reqEntity, this, SEND_CHAT_API).execute(ApiConfig.DOCTOR_CHAT_API);


//                String chat_id, String chat_p_id, String chat_doc_id, String chat_m_id,
//                        String date, String time, String chat_msg, String chat_title,
//                        String chat_file, String thumb_file, String message_type, String direction
                String m_id = "";
                if (memberResultPOJO != null) {
                    m_id = memberResultPOJO.getM_id();
                }

                ChatResultPOJO pojo = new ChatResultPOJO("", patientResultPOJO.getP_id(),
                        patientResultPOJO.getDoc_id(),
                        m_id,
                        date, time,
                        "",
                        "normal message", image_path, thumb, type, "false");
//                list_chat_messages.add(pojo);
//                showChatMessages(list_chat_messages);
                inflateSingleChatMessage(pojo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastClass.ShowLongToast(getApplicationContext(), "Please Enter message");
        }
        image_path_string = "";
    }

    public void inflateChatMessage(List<ChatPatientResultPOJO> list_chat_patient) {
        for (int i = 0; i < list_chat_patient.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_chat_layout, null);
            LinearLayout ll_chat_scroll = (LinearLayout) view.findViewById(R.id.ll_chat_scroll);
            if ((i + 1) == list_chat_patient.size()) {
                final_chat_ll = ll_chat_scroll;
            }
            TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_date.setText(list_chat_patient.get(i).getDate());
            List<ChatResultPOJO> list_chats = list_chat_patient.get(i).getList_chat();
            for (int k = 0; k < list_chats.size(); k++) {
                final LayoutInflater inflater_chat = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view_chat = inflater_chat.inflate(R.layout.inflate_chats, null);
                FrameLayout frame_video = (FrameLayout) view_chat.findViewById(R.id.frame_video);

                LinearLayout ll_main_msg = (LinearLayout) view_chat.findViewById(R.id.ll_main_msg);
                LinearLayout ll_audio_msg = (LinearLayout) view_chat.findViewById(R.id.ll_audio_msg);
                LinearLayout ll_msg = (LinearLayout) view_chat.findViewById(R.id.ll_msg);
                final ImageView iv_image = (ImageView) view_chat.findViewById(R.id.iv_image);
                ImageView iv_video_image = (ImageView) view_chat.findViewById(R.id.iv_video_image);
                final ImageView iv_download = (ImageView) view_chat.findViewById(R.id.iv_download);
                final ImageView iv_video_download = (ImageView) view_chat.findViewById(R.id.iv_video_download);
                final ImageView iv_play = (ImageView) view_chat.findViewById(R.id.iv_play);
                final SeekBar seek_play_pause = (SeekBar) view_chat.findViewById(R.id.seek_play_pause);
                TextView tv_msg = (TextView) view_chat.findViewById(R.id.tv_msg);
                TextView tv_time = (TextView) view_chat.findViewById(R.id.tv_time);
                final ChatResultPOJO chatResultPOJO = list_chats.get(k);
                Log.d(TAG, "message:-" + chatResultPOJO.getMessage_type());
                if (chatResultPOJO.getMessage_type().equals("image") || chatResultPOJO.getMessage_type().equals("video") || chatResultPOJO.getMessage_type().equals("audio")) {
                    if (chatResultPOJO.getMessage_type().equals("image")) {
                        if (chatResultPOJO.getChat_file().contains("upload/")) {
                            iv_image.setVisibility(View.VISIBLE);
                            loadImagewithGlide(iv_image, "http://www.bjain.com/doctor/" + chatResultPOJO.getChat_file());

                        } else {
                            loadImagewithGlide(iv_image, chatResultPOJO.getChat_file());
                        }
                    } else {
                        if (chatResultPOJO.getMessage_type().equals("video")) {
                            if (chatResultPOJO.getChat_file().contains("chatupload/")) {
                                final String file_name = chatResultPOJO.getChat_file().replace("chatupload/", "");
                                final File f = new File(FileUtil.getvideoChatDir() + File.separator + file_name);
                                if (!f.exists()) {
                                    iv_video_download.setVisibility(View.VISIBLE);
                                }
                                frame_video.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (f.exists()) {
                                            openVideoFile(f.toString());
                                        } else {
                                            String video_path = FileUtil.getvideoChatDir() + File.separator + file_name;
                                            downloadAudioFile(iv_video_download, "http://www.bjain.com/doctor/" + chatResultPOJO.getChat_file(), video_path);
                                            Log.d(TAG, "file not exist:-" + f.toString());
                                        }
                                    }
                                });
                                loadImagewithGlide(iv_video_image, "http://www.bjain.com/doctor/" + chatResultPOJO.getThumb_file());
                                iv_video_image.setVisibility(View.VISIBLE);
                                frame_video.setVisibility(View.VISIBLE);
                            } else {

                                loadImagewithGlide(iv_video_image, chatResultPOJO.getThumb_file());
                                iv_video_image.setVisibility(View.VISIBLE);
                                frame_video.setVisibility(View.VISIBLE);
                                frame_video.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        File f = new File(chatResultPOJO.getChat_file());
                                        if (f.exists()) {
                                            openVideoFile(f.toString());
                                        } else {
                                            ToastClass.ShowShortToast(getApplicationContext(), "Video Not Found");
                                        }
                                    }
                                });
                            }

                        } else {
                            if (chatResultPOJO.getMessage_type().equals("audio")) {
                                ll_audio_msg.setVisibility(View.VISIBLE);
                                if (chatResultPOJO.getChat_file().contains("chatupload/")) {
                                    String file_name = chatResultPOJO.getChat_file().replace("chatupload/", "");
                                    final String audio_path = FileUtil.getAudioChatDir() + File.separator + file_name;
                                    final File audio_file = new File(audio_path);
                                    if (!audio_file.exists()) {
                                        iv_download.setVisibility(View.VISIBLE);
                                    } else {

                                    }
                                    ll_audio_msg.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (audio_file.exists()) {
                                                openAudioFile(audio_path);
                                            } else {
                                                downloadAudioFile(iv_download, "http://www.bjain.com/doctor/" + chatResultPOJO.getChat_file(), audio_path);
                                            }
                                        }
                                    });
                                } else {
                                    iv_play.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            File f = new File(chatResultPOJO.getChat_file());
                                            if (f.exists()) {
                                                openAudioFile(f.toString());
                                            } else {
                                                ToastClass.ShowShortToast(getApplicationContext(), "Video Not Found");
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }

                } else {
                    tv_msg.setVisibility(View.VISIBLE);
                    tv_msg.setText(chatResultPOJO.getChat_msg());
                }
                if (chatResultPOJO.getDirection().equals("true")) {
                    ll_msg.setBackgroundResource(R.drawable.chat_receive_linear_back);
                    ll_main_msg.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                } else {
                    ll_msg.setBackgroundResource(R.drawable.chat_send_linear_back);
                    ll_main_msg.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String time = chatResultPOJO.getTime();
                    Date d = sdf.parse(time);
                    SimpleDateFormat sf = new SimpleDateFormat("hh:mm a");
                    tv_time.setText(sf.format(d));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    tv_time.setText(chatResultPOJO.getTime());
                }

                ll_chat_scroll.addView(view_chat);
            }
            ll_scroll.addView(view);
        }
        makescrolldown();
    }

    public void loadImagewithGlide(ImageView imageView, String url) {
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        makescrolldown();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        makescrolldown();
                        return false;
                    }
                })
                .into(imageView);
    }

    public void makescrolldown() {
        scroll_layout.post(new Runnable() {
            @Override
            public void run() {
                scroll_layout.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void openAudioFile(String audio_path) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(audio_path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.emobi.bjaindoc.fileProvider", file);
            intent.setDataAndType(contentUri, "audio/*");

        } else {
            intent.setDataAndType(Uri.fromFile(file), "audio/*");
        }
        startActivity(intent);
    }


    public void downloadAudioFile(final ImageView imageView, final String audio_url, final String path) {
        new AsyncTask<String, Integer, Long>() {

            ProgressDialog mProgressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(ChatActivityPat.this);
                mProgressDialog.setMessage("Downloading");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setCancelable(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.show();
            }

            @Override
            protected Long doInBackground(String... aurl) {
                int count;
                try {
                    URL url = new URL(audio_url);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    int lenghtOfFile = conexion.getContentLength();
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(path);
                    byte data[] = new byte[1024];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress((int) (total * 100 / lenghtOfFile));
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                }
                return null;
            }

            protected void onProgressUpdate(Integer... progress) {
                mProgressDialog.setProgress(progress[0]);
                if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "File Downloaded", Toast.LENGTH_SHORT).show();
                    imageView.setVisibility(View.GONE);
                }
            }

            protected void onPostExecute(String result) {


            }
        }.execute();

    }

    public void seekUpdation(SeekBar seek_play_pause, Handler seekHandler, MediaPlayer mediaPlayer, Runnable run) {
        seek_play_pause.setProgress(mediaPlayer.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    public void inflateSingleChatMessage(final ChatResultPOJO chatResultPOJO) {
        if (final_chat_ll != null) {
            final LayoutInflater inflater_chat = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view_chat = inflater_chat.inflate(R.layout.inflate_chats, null);

            LinearLayout ll_main_msg = (LinearLayout) view_chat.findViewById(R.id.ll_main_msg);
            LinearLayout ll_msg = (LinearLayout) view_chat.findViewById(R.id.ll_msg);
            FrameLayout frame_video = (FrameLayout) view_chat.findViewById(R.id.frame_video);
            ImageView iv_image = (ImageView) view_chat.findViewById(R.id.iv_image);
            ImageView iv_video_image = (ImageView) view_chat.findViewById(R.id.iv_video_image);
            final ImageView iv_video_download = (ImageView) view_chat.findViewById(R.id.iv_video_download);
            TextView tv_msg = (TextView) view_chat.findViewById(R.id.tv_msg);
            TextView tv_time = (TextView) view_chat.findViewById(R.id.tv_time);
            LinearLayout ll_audio_msg = (LinearLayout) view_chat.findViewById(R.id.ll_audio_msg);
            final ImageView iv_download = (ImageView) view_chat.findViewById(R.id.iv_download);
            final ImageView iv_play = (ImageView) view_chat.findViewById(R.id.iv_play);

            if (chatResultPOJO.getMessage_type().equals("image") || chatResultPOJO.getMessage_type().equals("video") || chatResultPOJO.getMessage_type().equals("audio")) {
                if (chatResultPOJO.getMessage_type().equals("image")) {
                    if (chatResultPOJO.getChat_file().contains("upload/")) {
                        loadImagewithGlide(iv_image, "http://www.bjain.com/doctor/" + chatResultPOJO.getChat_file());
                        iv_image.setVisibility(View.VISIBLE);
                    } else {
                        loadImagewithGlide(iv_image, chatResultPOJO.getChat_file());
                        iv_image.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (chatResultPOJO.getMessage_type().equals("video")) {
                        if (chatResultPOJO.getChat_file().contains("chatupload/")) {
                            final String file_name = chatResultPOJO.getChat_file().replace("chatupload/", "");
                            final File f = new File(FileUtil.getvideoChatDir() + File.separator + file_name);
                            if (!f.exists()) {
                                iv_video_download.setVisibility(View.VISIBLE);
                            }
                            frame_video.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (f.exists()) {
                                        openVideoFile(f.toString());
                                    } else {
                                        String video_path = FileUtil.getvideoChatDir() + File.separator + file_name;
                                        downloadAudioFile(iv_video_download, "http://www.bjain.com/doctor/" + chatResultPOJO.getChat_file(), video_path);
                                        Log.d(TAG, "file not exist:-" + f.toString());
                                    }
                                }
                            });
                            loadImagewithGlide(iv_video_image, "http://www.bjain.com/doctor/" + chatResultPOJO.getThumb_file());
                            iv_video_image.setVisibility(View.VISIBLE);
                            frame_video.setVisibility(View.VISIBLE);
                        } else {
                            loadImagewithGlide(iv_video_image, chatResultPOJO.getThumb_file());
                            iv_video_image.setVisibility(View.VISIBLE);
                            frame_video.setVisibility(View.VISIBLE);
                            frame_video.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    File f = new File(chatResultPOJO.getChat_file());
                                    if (f.exists()) {
                                        openVideoFile(f.toString());
                                    } else {
                                        ToastClass.ShowShortToast(getApplicationContext(), "Video Not Found");
                                    }
                                }
                            });
                        }

                    } else {
                        if (chatResultPOJO.getMessage_type().equals("audio")) {
                            ll_audio_msg.setVisibility(View.VISIBLE);
                            if (chatResultPOJO.getChat_file().contains("chatupload/")) {
                                String file_name = chatResultPOJO.getChat_file().replace("chatupload/", "");
                                final String audio_path = FileUtil.getAudioChatDir() + File.separator + file_name;
                                final File audio_file = new File(audio_path);
                                if (!audio_file.exists()) {
                                    iv_download.setVisibility(View.VISIBLE);
                                } else {

                                }
                                ll_audio_msg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (audio_file.exists()) {
                                            openAudioFile(audio_path);
                                        } else {
                                            downloadAudioFile(iv_download, "http://www.bjain.com/doctor/" + chatResultPOJO.getChat_file(), audio_path);
                                        }
                                    }
                                });
                            } else {
                                iv_play.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        File f = new File(chatResultPOJO.getChat_file());
                                        if (f.exists()) {
                                            openAudioFile(f.toString());
                                        } else {
                                            ToastClass.ShowShortToast(getApplicationContext(), "Video Not Found");
                                        }
                                    }
                                });
                            }
                        }
                    }
                }

            } else {
                tv_msg.setVisibility(View.VISIBLE);
                tv_msg.setText(chatResultPOJO.getChat_msg());
            }
            if (chatResultPOJO.getDirection().equals("true")) {
                ll_msg.setBackgroundResource(R.drawable.chat_receive_linear_back);
                ll_main_msg.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            } else {
                ll_msg.setBackgroundResource(R.drawable.chat_send_linear_back);
                ll_main_msg.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String time = chatResultPOJO.getTime();
                Date d = sdf.parse(time);
                SimpleDateFormat sf = new SimpleDateFormat("hh:mm a");
                tv_time.setText(sf.format(d));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                tv_time.setText(chatResultPOJO.getTime());
            }

            final_chat_ll.addView(view_chat);
            scroll_layout.post(new Runnable() {
                @Override
                public void run() {
                    scroll_layout.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        } else {
            List<ChatResultPOJO> chatResultPOJOList = new ArrayList<>();
            chatResultPOJOList.add(chatResultPOJO);
            showChatMessages(chatResultPOJOList);

        }
    }


    public void openVideoFile(String file_name) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(file_name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.emobi.bjaindoc.fileProvider", file);
            intent.setDataAndType(contentUri, "video/*");

        } else {
            intent.setDataAndType(Uri.fromFile(file), "video/*");
        }
        startActivity(intent);
    }


    public void evaluateChatLength() {
        Pref.SetStringPref(getApplicationContext(), StringUtils.VIEWED_CHAT_TOKEN, Pref.GetStringPref(getApplicationContext(), StringUtils.CHAT_TOKEN, ""));
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
                final String[] selectionArgs = new String[]{split[1]};

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
        final String[] projection = {column};

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

    public boolean copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());

            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void onBackPressed() {
        if (chatResultPOJO1 != null) {
            Intent intent = new Intent(ChatActivityPat.this, DoctorAccount.class);
            startActivity(intent);
            finishAffinity();
        } else {
            finish();
        }
    }
}
