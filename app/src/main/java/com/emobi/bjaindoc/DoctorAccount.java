package com.emobi.bjaindoc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.whatsapp_profile.MainActivityDoctorProfile;
import com.emobi.bjaindoc.activity.HelpActivity;
import com.emobi.bjaindoc.activity.NewPatientActivity;
import com.emobi.bjaindoc.activity.UrgentChatPatientListActivity;
import com.emobi.bjaindoc.pojo.urgentchatpatient.NewUrgentChatResultPOJO;
import com.emobi.bjaindoc.services.WebServiceBase;
import com.emobi.bjaindoc.services.WebServicesCallBack;
import com.emobi.bjaindoc.utls.Database;
import com.emobi.bjaindoc.utls.Pref;
import com.emobi.bjaindoc.utls.StringUtils;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.emobi.bjaindoc.utls.StringUtils.status;

public class DoctorAccount extends AppCompatActivity implements WebServicesCallBack {

    LinearLayout top, btn_submit;
    SharedPreferences settings;
    //    ActionBar actionBar;
    SharedPreferences.Editor editor;
    RelativeLayout rl_appointments, patient_layout, mid_layout, notification_layout, activat_layout;
    public static CircleImageView profilepic;
    public static TextView textDoc, toolbar_title, sign;
    String type = "";
    String result = "";

    @BindView(R.id.rl_urgent_chat)
    RelativeLayout rl_urgent_chat;
    @BindView(R.id.tv_urgent_chat_number)
    TextView tv_urgent_chat_number;
    @BindView(R.id.rl_patient_activation)
    RelativeLayout rl_patient_activation;

    Database helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorsecond);
        ButterKnife.bind(this);
        helper = new Database(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");
            result = bundle.getString("result");
            Log.d(TAG,"type:-"+type);
            Log.d(TAG,"result:-"+result);

            if (type != null && result != null) {
                checkType(type, result);
            }
//            Log.d(TAG,"message:-"+msg);
        }

        top = (LinearLayout) findViewById(R.id.top);
        btn_submit = (LinearLayout) findViewById(R.id.btn_submit);
        textDoc = (TextView) findViewById(R.id.textDoc);
        sign = (TextView) findViewById(R.id.sign);
        rl_appointments = (RelativeLayout) findViewById(R.id.rl_appointments);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        patient_layout = (RelativeLayout) findViewById(R.id.rl_interaction);
        mid_layout = (RelativeLayout) findViewById(R.id.mid_layout);
        notification_layout = (RelativeLayout) findViewById(R.id.rl_prescription);
        activat_layout = (RelativeLayout) findViewById(R.id.rl_notes);
        profilepic = (CircleImageView) findViewById(R.id.profilepic);
        TextView share_App1 = (TextView) findViewById(R.id.share1);
        TextView rate1 = (TextView) findViewById(R.id.rate1);
        TextView how_1 = (TextView) findViewById(R.id.how_1);
        TextView notification_stop1 = (TextView) findViewById(R.id.notification_stop1);
        TextView notification_sound1 = (TextView) findViewById(R.id.notification_sound1);

        try {
            if (PreferenceData.getPhoto(getApplicationContext()).length() > 0) {
                Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + PreferenceData.getPhoto(getApplicationContext())).into(profilepic);
            }
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        share_App1.setTypeface(tf);
        rate1.setTypeface(tf);
        how_1.setTypeface(tf);
        notification_stop1.setTypeface(tf);
        notification_sound1.setTypeface(tf);

        textDoc.setText(PreferenceData.getName(getApplicationContext()));
        textDoc.setTypeface(tf);
        toolbar_title.setTypeface(tf);
        sign.setTypeface(tf);

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorAccount.this, MainActivityDoctorProfile.class));
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutMethod();
            }
        });
        rl_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorAccount.this, GetAppointmentByDoctor.class));
            }
        });

        patient_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorAccount.this, ViewPatientList.class));
            }
        });

        mid_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorAccount.this, BroadCast_Docview.class));
            }
        });

        notification_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorAccount.this, View_notes.class));
            }
        });

        activat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DoctorAccount.this, ViewPatientListactivation.class));
            }
        });

        rl_urgent_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorAccount.this, UrgentChatPatientListActivity.class);
                startActivity(intent);
            }
        });
        rl_patient_activation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorAccount.this, NewPatientActivity.class));
            }
        });
//        copydatabase();
        checkDoctorActivation();
    }

    public void checkDoctorActivation(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("method", "status"));
        nameValuePairs.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
        Log.d(TAG,"doc_id:-"+PreferenceData.getId(getApplicationContext()));
        new WebServiceBase(nameValuePairs, this, CHECK_DOCTOR_ACTIVATION, false).execute(ApiConfig.DOCTOR_ACTIVATION);
    }

    private final String CHECK_DOCTOR_ACTIVATION="check_doctor_activation";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        evaluateUrgentChat();
        getApplicationContext().registerReceiver(mUrgentChatReceiver, new IntentFilter(StringUtils.DOC_ACCOUNT_URGENT_CHAT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mUrgentChatReceiver);

    }

    private BroadcastReceiver mUrgentChatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra("message");
            evaluateUrgentChat();
        }
    };

    public void evaluateUrgentChat() {
        try {
            List<NewUrgentChatResultPOJO> list_stored_chat = helper.getAllStoredUrgentChat();
            List<NewUrgentChatResultPOJO> list_server_chat = helper.getAllSERVERUrgentChat();
            Log.d(TAG, "server_chat_size:-" + list_server_chat.size());
            Log.d(TAG, "stored_chat_size:-" + list_stored_chat.size());
            if (list_stored_chat.size() > 0) {
                if ((list_server_chat.size() - list_stored_chat.size()) > 0) {
                    Log.d(TAG, "1");
                    tv_urgent_chat_number.setVisibility(View.VISIBLE);
                    tv_urgent_chat_number.setText(String.valueOf(list_server_chat.size() - list_stored_chat.size()));
                } else {
                    tv_urgent_chat_number.setVisibility(View.INVISIBLE);
                    tv_urgent_chat_number.setText("");
                }
            } else {
                if (list_server_chat.size() > 0) {
                    Log.d(TAG, "2");
                    tv_urgent_chat_number.setVisibility(View.VISIBLE);
                    tv_urgent_chat_number.setText(String.valueOf(list_server_chat.size()));
                } else {
                    tv_urgent_chat_number.setVisibility(View.INVISIBLE);
                    tv_urgent_chat_number.setText("");
                }
            }
            if (tv_urgent_chat_number.getText().toString().equals("0")) {
                tv_urgent_chat_number.setVisibility(View.INVISIBLE);
                tv_urgent_chat_number.setText("");
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
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


    public void refresh() {
        new CallServices().execute(ApiConfig.LOGIN_URL);
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
            pd = new ProgressDialog(DoctorAccount.this);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("reg_email", PreferenceData.getEmail(getApplicationContext())));
            namevaluepair.add(new BasicNameValuePair("reg_pass", PreferenceData.getPass(getApplicationContext())));
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

            if (pd != null) {
                pd.dismiss();
            }


            if (result != null) {

                try {
                    Log.e("result", result);
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.getString("d_message");
                    if (message.equalsIgnoreCase("invalid")) {
                        UtilsValidate.showError(DoctorAccount.this, getResources().getString(R.string.error), getResources().getString(R.string.err_email));
                    } else {
                        String reg_name = jsonObject.getString("reg_name");
                        String reg_email = jsonObject.getString("reg_email");
                        String reg_mob = jsonObject.getString("reg_mob");
                        String reg_pass = jsonObject.getString("reg_pass");
                        String reg_degree = jsonObject.getString("degree");
                        String reg_designation = jsonObject.getString("designation");
                        String reg_clinic_address = jsonObject.getString("clinic_address");
                        String reg_id = jsonObject.getString("reg_dob");
                        String reg_department = jsonObject.getString("department");
                        String dob = jsonObject.getString("photo");
                        String reg_specialist = jsonObject.getString("specialist");
                        Log.e("dob", dob);

//                        PreferenceData.setName(getApplicationContext(),reg_name);
                        textDoc.setText(reg_name);

//                        PreferenceData.setPhoto(getApplicationContext(),dob);
                        try {
                            if (PreferenceData.getPhoto(getApplicationContext()).length() > 0) {
                                Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + dob).into(profilepic);
                            }
                        } catch (Exception e) {
                            Log.d("sunil", e.toString());
                        }

                    }
                    Log.e("result", result.toString());


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == DoctorAccount.this.RESULT_OK) {
                String result = data.getStringExtra("result1");
                Log.e("result", result);
                refresh();
            }
            if (resultCode == DoctorAccount.this.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void signOutMethod() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("doc_id", PreferenceData.getId(getApplicationContext())));
        nameValuePairs.add(new BasicNameValuePair("device_token", ""));
        new WebServiceBase(nameValuePairs, this, "signoutapi").execute("http://www.bjain.com/doctor/update_doctor_token.php");

//        settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
//        editor = settings.edit();
//        editor.clear();
//        editor.commit();
//        Intent intent = new Intent(DoctorAccount.this, LoginActivity.class);
//        Toast.makeText(getApplicationContext(), "logout successfully", Toast.LENGTH_LONG).show();
//        LoginManager.getInstance().logOut();
//        startActivity(intent);
//        finishAffinity();
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case "signoutapi":
                parseSignOutResponse(response);
                break;
            case CHECK_DOCTOR_ACTIVATION:
                parseDoctorActivation(response);
                break;
        }
    }

    public void parseDoctorActivation(String response){
        Log.d(TAG,"activation result:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            String success=jsonObject.optString("success");
            if(success.equals("true")){
                Pref.SetBooleanPref(getApplicationContext(), status,true);
            }
            else{
                Pref.SetBooleanPref(getApplicationContext(), status,false);
                final Dialog dialog = new Dialog(DoctorAccount.this, android.R.style.Theme_DeviceDefault_Dialog);

                //setting custom layout to dialog
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_account_active_dialog);
                dialog.setTitle("Account Deactivated");
                dialog.setCancelable(false);
                LinearLayout ll_submit= (LinearLayout) dialog.findViewById(R.id.ll_submit);

                ll_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent=new Intent(DoctorAccount.this, HelpActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void parseSignOutResponse(String response) {
        try {
            Log.d(TAG, "response:-" + response);
            helper.deleteAllServerChat();
            helper.deleteAllStoreChat();
            settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
            editor = settings.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(DoctorAccount.this, LoginActivity.class);
            Toast.makeText(getApplicationContext(), "logout successfully", Toast.LENGTH_LONG).show();
            LoginManager.getInstance().logOut();
            startActivity(intent);
            finishAffinity();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    private final String TAG = getClass().getSimpleName();

    public void checkType(String type, String result) {

        Log.d(TAG, "type:-" + type);
        Log.d(TAG, "result:-" + result);

        switch (type) {
            case StringUtils.BOOK_APPOINTMENT:
                startAppointmentActivity();
                break;
            case StringUtils.CHAT:
                startChatActivity(result);
                break;
        }
    }

    public void startAppointmentActivity() {
        startActivity(new Intent(DoctorAccount.this, GetAppointmentByDoctor.class));
    }

    public void startChatActivity(String result) {
        Intent intent = new Intent(DoctorAccount.this, ViewPatientList.class);
        intent.putExtra("result", result);
        startActivity(intent);
    }

    public void copydatabase() {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.emobi.bjaindoc" + "/databases/" + "bjaindoc";
        String backupDBPath = "bjaindoc";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
