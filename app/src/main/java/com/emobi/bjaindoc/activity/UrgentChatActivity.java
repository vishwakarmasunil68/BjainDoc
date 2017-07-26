package com.emobi.bjaindoc.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.emobi.bjaindoc.ApiConfig;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.pojo.urgent.UrgentChatPOJO;
import com.emobi.bjaindoc.pojo.urgent.UrgentChatPatientPOJO;
import com.emobi.bjaindoc.pojo.urgent.UrgentChatPatientResultPOJO;
import com.emobi.bjaindoc.pojo.urgent.UrgentChatResultPOJO;
import com.emobi.bjaindoc.pojo.urgentchatpatient.UrgentPatientResultPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.emobi.bjaindoc.utls.BjainDocDBHelper;
import com.emobi.bjaindoc.utls.ImageUtil;
import com.emobi.bjaindoc.utls.Pref;
import com.emobi.bjaindoc.utls.StringUtils;
import com.emobi.bjaindoc.utls.ToastClass;
import com.google.common.io.Files;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.emobi.bjaindoc.utls.Pref.GetStringPref;

public class UrgentChatActivity extends AppCompatActivity implements WebServicesCallBack, View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private final static String OLD_URGENT_CHAT_API = "old_urgent_chat_api";
    private final static String SEND_URGENT_CHAT_API = "send_urgent_chat_api";
    private int PICK_IMAGE_REQUEST = 1;

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

    List<UrgentChatResultPOJO> list_chat_messages;
    LinearLayout final_chat_ll;
    int patient_index;
    BjainDocDBHelper helper;
    UrgentPatientResultPOJO urgentPatientResultPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_chat);
        ButterKnife.bind(this);
        helper = new BjainDocDBHelper(this);
        urgentPatientResultPOJO = (UrgentPatientResultPOJO) getIntent().getSerializableExtra("patient");
        if (urgentPatientResultPOJO != null) {
            //Log.d(TAG, "patient:-" + urgentPatientResultPOJO.toString());
            patient_index = getIntent().getIntExtra("position", -1);
        } else {
            finish();
        }
        send.setOnClickListener(this);
        iv_attach_photo.setOnClickListener(this);
        parseOldUrgentChat(GetStringPref(getApplicationContext(), StringUtils.OLD_URGENT_CHAT, ""));
        callOldUrgentChat();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void callOldUrgentChat() {
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("doc_id", urgentPatientResultPOJO.getDoc_id()));
            nameValuePairs.add(new BasicNameValuePair("pat_id", urgentPatientResultPOJO.getP_id()));
            new WebServiceBase(nameValuePairs, this, OLD_URGENT_CHAT_API).execute(ApiConfig.GET_ALL_URGENT_CHAT);

//            clearData();
//            savePrefData();
        } catch (Exception e) {
            //Log.d(TAG, e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", String.valueOf(patient_index));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void clearData() {
        Pref.SetStringPref(getApplicationContext(), StringUtils.URGENT_CHAT_DATA, "");
    }

    public void savePrefData() {

        String urgent_chat_data = Pref.GetStringPref(getApplicationContext(), StringUtils.URGENT_CHAT_DATA, "");
        Log.d(TAG, "urgent_chat_data:-" + urgent_chat_data);
        try {
            JSONObject jsonObject = new JSONObject(urgent_chat_data);
            try {
                JSONObject pat_data = jsonObject.optJSONObject(urgentPatientResultPOJO.getP_name());
                jsonObject.remove(pat_data.optString("p_name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
//                Gson gson=new Gson();
//                String json_string=gson.toJson(urgentPatientResultPOJO);
//                Log.d(TAG,"json string:-"+json_string);
            jsonObject.put(urgentPatientResultPOJO.getP_name(), getPOJOObject(urgentPatientResultPOJO));

            Pref.SetStringPref(getApplicationContext(), StringUtils.URGENT_CHAT_DATA, jsonObject.toString());

        } catch (Exception e) {
            Log.d(TAG, "error:-" + e.toString());
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(urgentPatientResultPOJO.getP_name(), getPOJOObject(urgentPatientResultPOJO));
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            Pref.SetStringPref(getApplicationContext(), StringUtils.URGENT_CHAT_DATA, jsonObject.toString());
        }

        Log.d(TAG, "final json:-" + Pref.GetStringPref(getApplicationContext(), StringUtils.URGENT_CHAT_DATA, ""));
    }

    public JSONObject getPOJOObject(UrgentPatientResultPOJO urgentPatientResultPOJO) {
        JSONObject object = new JSONObject();
        try {
            object.put("p_id", urgentPatientResultPOJO.getP_id());
            object.put("doc_id", urgentPatientResultPOJO.getDoc_id());
            object.put("p_name", urgentPatientResultPOJO.getP_name());
            object.put("p_login_id", urgentPatientResultPOJO.getP_login_id());
            object.put("p_login_pass", urgentPatientResultPOJO.getP_login_pass());
            object.put("p_age", urgentPatientResultPOJO.getP_age());
            object.put("p_bloodgroup", urgentPatientResultPOJO.getP_bloodgroup());
            object.put("p_weight", urgentPatientResultPOJO.getP_weight());
            object.put("p_height", urgentPatientResultPOJO.getP_height());
            object.put("p_mob", urgentPatientResultPOJO.getP_mob());
            object.put("p_status", urgentPatientResultPOJO.getP_status());
            object.put("p_photo", urgentPatientResultPOJO.getP_photo());
            object.put("p_gcm_id", urgentPatientResultPOJO.getP_gcm_id());
            object.put("p_device_token", urgentPatientResultPOJO.getP_device_token());
            object.put("p_medication", urgentPatientResultPOJO.getP_medication());
            object.put("p_alergi", urgentPatientResultPOJO.getP_alergi());
            object.put("p_digoxin", urgentPatientResultPOJO.getP_digoxin());
            object.put("msg", urgentPatientResultPOJO.getMsg());
            object.put("p_message", urgentPatientResultPOJO.getP_message());
            object.put("conditions", urgentPatientResultPOJO.getConditions());
            object.put("description", urgentPatientResultPOJO.getDescription());
            object.put("u_chat_id", urgentPatientResultPOJO.getU_chat_id());
            object.put("u_chat_p_id", urgentPatientResultPOJO.getU_chat_p_id());
            object.put("u_chat_doc_id", urgentPatientResultPOJO.getU_chat_doc_id());
            object.put("u_date", urgentPatientResultPOJO.getU_date());
            object.put("u_time", urgentPatientResultPOJO.getU_time());
            object.put("u_chat_msg", urgentPatientResultPOJO.getU_chat_msg());
            object.put("u_chat_title", urgentPatientResultPOJO.getU_chat_title());
            object.put("u_chat_file", urgentPatientResultPOJO.getU_chat_file());
            object.put("u_direction", urgentPatientResultPOJO.getU_direction());
            object.put("u_time1", urgentPatientResultPOJO.getU_time1());
            object.put("Count", urgentPatientResultPOJO.getCount());
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        return object;
    }


    @Override
    public void onGetMsg(String[] msg) {
        String api = msg[0];
        String response = msg[1];
        switch (api) {
            case OLD_URGENT_CHAT_API:
                parseOldUrgentChat(response);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.URGENT_CHAT));
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
            //Log.d(TAG, "chatresult:-" + result);

            try {
                Gson gson = new Gson();
                UrgentChatResultPOJO chatResultPOJO = gson.fromJson(result, UrgentChatResultPOJO.class);
                inflateSingleChatMessage(chatResultPOJO);
            } catch (Exception e) {
                //Log.d(TAG, e.toString());
            }
        }
    };

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
                        UrgentChatActivity.this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    //Log.d(TAG, "selected path:-" + selectedImagePath);
                    sendImageMessage(selectedImagePath);
//                    Bitmap bmImg = BitmapFactory.decodeFile(image_path_string);
//                    iv_profile.setImageBitmap(bmImg);
//                    Preferences.setCardImagePath(getApplicationContext(),selectedImagePath);

//                    startActivity(new Intent(SelectImageActivity.this,CardActivity.class));
                } else {
                    Toast.makeText(UrgentChatActivity.this, "File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
        }
    }

    public void parseOldUrgentChat(String response) {
        //Log.d(TAG, "response:-" + response);
        try {
            Gson gson = new Gson();
            UrgentChatPOJO urgentChatPOJO = gson.fromJson(response, UrgentChatPOJO.class);
            if (urgentChatPOJO.getSuccess().equals("true")) {
                Log.d(TAG, "deleted data:-" + helper.deleteStoreChatByPatientID(urgentPatientResultPOJO.getU_chat_p_id()));
                Log.d(TAG, "deleted data:-" + helper.deleteServerChatByPatientID(urgentPatientResultPOJO.getU_chat_p_id()));
                showChatMessages(urgentChatPOJO.getUrgentChatResultPOJOList());
            } else {
                ToastClass.ShowLongToast(getApplicationContext(), "No Chat Msg Found");
            }
        } catch (Exception e) {
            //Log.d(TAG, e.toString());
        }
    }

    public void showChatMessages(final List<UrgentChatResultPOJO> list_chat) {

        list_chat_messages = list_chat;
        List<String> list_dates = new ArrayList<>();
        Set<String> set_date = new HashSet<>();
        for (UrgentChatResultPOJO urgentChatResultPOJO : list_chat) {
            helper.insertStoredUrgentChat(urgentChatResultPOJO);
            helper.insertServerUrgentChat(urgentChatResultPOJO);
            list_dates.add(urgentChatResultPOJO.getU_date());
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

            for (UrgentChatResultPOJO urgentChatResultPOJO : list_chat) {
                if (s.equals(urgentChatResultPOJO.getU_date())) {
                    try {
                        JSONObject object1 = new JSONObject();
                        object1.put("u_chat_p_id", urgentChatResultPOJO.getU_chat_p_id());
                        object1.put("u_chat_doc_id", urgentChatResultPOJO.getU_chat_doc_id());
                        object1.put("u_chat_title", urgentChatResultPOJO.getU_chat_title());
                        object1.put("u_chat_msg", urgentChatResultPOJO.getU_chat_msg());
                        object1.put("u_date", urgentChatResultPOJO.getU_date());
                        object1.put("u_time", urgentChatResultPOJO.getU_time());
                        object1.put("u_chat_file", urgentChatResultPOJO.getU_chat_file());
                        object1.put("u_direction", urgentChatResultPOJO.getU_direction());
                        object1.put("u_chat_id", urgentChatResultPOJO.getU_chat_id());

                        date_array.put(object1);
                    } catch (Exception e) {
                        //Log.d(TAG, e.toString());
                    }
                }
            }
            try {
                date_object.put("date", s);
                date_object.put("result", date_array);

                array.put(date_object);
            } catch (Exception e) {
                //Log.d(TAG, e.toString());
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
        //Log.d(TAG, "json:-" + object.toString());
        try {
            Gson gson = new Gson();
            UrgentChatPatientPOJO urgentChatPatientPOJO = gson.fromJson(object.toString(), UrgentChatPatientPOJO.class);
            inflateChatMessage(urgentChatPatientPOJO.getUrgentChatPatientResultPOJOList());
        } catch (Exception e) {
            //Log.d(TAG, e.toString());
        }
    }

    public void inflateChatMessage(List<UrgentChatPatientResultPOJO> list_chat_patient) {
        for (int i = 0; i < list_chat_patient.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_chat_layout, null);
            LinearLayout ll_chat_scroll = (LinearLayout) view.findViewById(R.id.ll_chat_scroll);
            if ((i + 1) == list_chat_patient.size()) {
                final_chat_ll = ll_chat_scroll;
            }
            TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_date.setText(list_chat_patient.get(i).getDate());
            List<UrgentChatResultPOJO> list_chats = list_chat_patient.get(i).getUrgentChatResultPOJOList();
            for (int k = 0; k < list_chats.size(); k++) {
                final LayoutInflater inflater_chat = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view_chat = inflater_chat.inflate(R.layout.inflate_urgent_chats, null);

                LinearLayout ll_main_msg = (LinearLayout) view_chat.findViewById(R.id.ll_main_msg);
                LinearLayout ll_msg = (LinearLayout) view_chat.findViewById(R.id.ll_msg);
                ImageView iv_image = (ImageView) view_chat.findViewById(R.id.iv_image);
                TextView tv_msg = (TextView) view_chat.findViewById(R.id.tv_msg);
                TextView tv_time = (TextView) view_chat.findViewById(R.id.tv_time);
                UrgentChatResultPOJO urgentChatResultPOJO = list_chats.get(k);

                if (urgentChatResultPOJO.getU_chat_file().length() > 0) {
                    iv_image.setVisibility(View.VISIBLE);
                    if (urgentChatResultPOJO.getU_chat_file().contains("upload/")) {
                        iv_image.setVisibility(View.VISIBLE);
                        loadImagewithGlide(iv_image,"http://www.bjain.com/doctor/" + urgentChatResultPOJO.getU_chat_file());
                    } else {
                        iv_image.setVisibility(View.VISIBLE);
                        loadImagewithGlide(iv_image,urgentChatResultPOJO.getU_chat_file());
                    }
                    //Log.d(TAG, "image url:-" + urgentChatResultPOJO.getU_chat_file());
                } else {
                    tv_msg.setVisibility(View.VISIBLE);
                    tv_msg.setText(urgentChatResultPOJO.getU_chat_msg());
                }
                if (urgentChatResultPOJO.getU_direction().equals("true")) {
                    ll_msg.setBackgroundResource(R.drawable.chat_receive_linear_back);
                    ll_main_msg.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                } else {
                    ll_msg.setBackgroundResource(R.drawable.chat_send_linear_back);
                    ll_main_msg.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String time = urgentPatientResultPOJO.getU_time();
                    Date d = sdf.parse(time);
                    SimpleDateFormat sf = new SimpleDateFormat("hh:mm a");
                    tv_time.setText(sf.format(d));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    tv_time.setText(urgentPatientResultPOJO.getU_time());
                }

                ll_chat_scroll.addView(view_chat);
            }
            ll_scroll.addView(view);
        }

        scroll_layout.post(new Runnable() {
            @Override
            public void run() {
                scroll_layout.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void inflateSingleChatMessage(UrgentChatResultPOJO chatResultPOJO) {
        if (final_chat_ll != null) {
            helper.insertStoredUrgentChat(chatResultPOJO);
            helper.insertServerUrgentChat(chatResultPOJO);
            final LayoutInflater inflater_chat = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view_chat = inflater_chat.inflate(R.layout.inflate_urgent_chats, null);

            LinearLayout ll_main_msg = (LinearLayout) view_chat.findViewById(R.id.ll_main_msg);
            LinearLayout ll_msg = (LinearLayout) view_chat.findViewById(R.id.ll_msg);
            ImageView iv_image = (ImageView) view_chat.findViewById(R.id.iv_image);
            TextView tv_msg = (TextView) view_chat.findViewById(R.id.tv_msg);
            TextView tv_time = (TextView) view_chat.findViewById(R.id.tv_time);
            if (chatResultPOJO.getU_chat_file().length() > 0) {
//                    Picasso.with(getApplicationContext())
//                            .load(chatResultPOJO.getChat_file())
//                            .into(iv_image);
                if (chatResultPOJO.getU_chat_file().contains("upload/")) {
                    iv_image.setVisibility(View.VISIBLE);
                    loadImagewithGlide(iv_image,"http://www.bjain.com/doctor/" + chatResultPOJO.getU_chat_file());
                } else {
                    iv_image.setVisibility(View.VISIBLE);
                    loadImagewithGlide(iv_image,chatResultPOJO.getU_chat_file());
                }
                //Log.d(TAG, "image url:-" + chatResultPOJO.getU_chat_file());
            } else {
                tv_msg.setVisibility(View.VISIBLE);
                tv_msg.setText(chatResultPOJO.getU_chat_msg());
            }
            if (chatResultPOJO.getU_direction().equals("true")) {
                ll_msg.setBackgroundResource(R.drawable.chat_receive_linear_back);
                ll_main_msg.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            } else {
                ll_msg.setBackgroundResource(R.drawable.chat_send_linear_back);
                ll_main_msg.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String time = urgentPatientResultPOJO.getU_time();
                Date d = sdf.parse(time);
                SimpleDateFormat sf = new SimpleDateFormat("hh:mm a");
                tv_time.setText(sf.format(d));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                tv_time.setText(urgentPatientResultPOJO.getU_time());
            }

            final_chat_ll.addView(view_chat);
            scroll_layout.post(new Runnable() {
                @Override
                public void run() {
                    scroll_layout.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        } else {
            List<UrgentChatResultPOJO> urgentChatResultPOJOList=new ArrayList<>();
            urgentChatResultPOJOList.add(chatResultPOJO);
            showChatMessages(urgentChatResultPOJOList);
        }
    }
    public void loadImagewithGlide(ImageView imageView,String url){
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
    public void makescrolldown(){
        scroll_layout.post(new Runnable() {
            @Override
            public void run() {
                scroll_layout.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void sendMessage() {
        if (et_msg.getText().toString().length() > 0) {

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = sdf.format(d).split(" ")[0];
            String time = sdf.format(d).split(" ")[1];

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("u_chat_p_id", urgentPatientResultPOJO.getP_id()));
            nameValuePairs.add(new BasicNameValuePair("u_chat_doc_id", urgentPatientResultPOJO.getDoc_id()));
            nameValuePairs.add(new BasicNameValuePair("token", urgentPatientResultPOJO.getP_device_token()));
            nameValuePairs.add(new BasicNameValuePair("u_date", date));
            nameValuePairs.add(new BasicNameValuePair("u_time", time));
            nameValuePairs.add(new BasicNameValuePair("u_chat_msg", et_msg.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("u_chat_title", urgentPatientResultPOJO.getP_name()));
            nameValuePairs.add(new BasicNameValuePair("file_ext", ""));
            nameValuePairs.add(new BasicNameValuePair("file_base", ""));
            nameValuePairs.add(new BasicNameValuePair("u_direction", "false"));
            new WebServiceBase(nameValuePairs, this, SEND_URGENT_CHAT_API, false).execute(ApiConfig.SEND_URGENT_CHAT);
            UrgentChatResultPOJO pojo = new UrgentChatResultPOJO("",
                    urgentPatientResultPOJO.getP_id(),
                    urgentPatientResultPOJO.getDoc_id(),
                    date, time,
                    et_msg.getText().toString(),
                    urgentPatientResultPOJO.getP_name(),
                    "", "false");
            if (list_chat_messages != null) {
                list_chat_messages.add(pojo);
            } else {
                list_chat_messages = new ArrayList<>();
                list_chat_messages.add(pojo);
            }
//            showChatMessages(list_chat_messages);
            inflateSingleChatMessage(pojo);
        } else {
            ToastClass.ShowLongToast(getApplicationContext(), "Please Enter message");
        }
        et_msg.setText("");
    }

    public void sendImageMessage(String image_path) {

        String ext = Files.getFileExtension(image_path);
        //Log.d(TAG, "ext:-" + ext);
        if (image_path.length() > 0) {

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = sdf.format(d).split(" ")[0];
            String time = sdf.format(d).split(" ")[1];

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("u_chat_p_id", urgentPatientResultPOJO.getP_id()));
            nameValuePairs.add(new BasicNameValuePair("u_chat_doc_id", urgentPatientResultPOJO.getDoc_id()));
            nameValuePairs.add(new BasicNameValuePair("token", urgentPatientResultPOJO.getP_device_token()));
            nameValuePairs.add(new BasicNameValuePair("u_date", date));
            nameValuePairs.add(new BasicNameValuePair("u_time", time));
            nameValuePairs.add(new BasicNameValuePair("u_chat_msg", et_msg.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("u_chat_title", urgentPatientResultPOJO.getP_name()));
            nameValuePairs.add(new BasicNameValuePair("file_ext", ext));
            nameValuePairs.add(new BasicNameValuePair("file_base", ImageUtil.encodeTobase64(BitmapFactory.decodeFile(image_path_string))));
            nameValuePairs.add(new BasicNameValuePair("u_direction", "false"));
            new WebServiceBase(nameValuePairs, this, SEND_URGENT_CHAT_API).execute(ApiConfig.SEND_URGENT_CHAT);
            UrgentChatResultPOJO pojo = new UrgentChatResultPOJO("",
                    urgentPatientResultPOJO.getP_id(),
                    urgentPatientResultPOJO.getDoc_id(),
                    date, time,
                    et_msg.getText().toString(),
                    urgentPatientResultPOJO.getP_name(),
                    image_path_string, "false");
            list_chat_messages.add(pojo);
//            showChatMessages(list_chat_messages);
            inflateSingleChatMessage(pojo);
        } else {
            ToastClass.ShowLongToast(getApplicationContext(), "Please Enter message");
        }
        image_path_string = "";
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                sendMessage();
                break;
            case R.id.iv_attach_photo:
                sendImageMessageClick();
                break;
        }
    }


}
