package com.emobi.bjaindoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.net.HttpURLConnection;

import database.PreferenceData;


public class UploadActivity extends AppCompatActivity {
    public static final String KEY_METHOD = "method";
    public static final String KEY_USERDOCTOR="diseases_doctor_id";
    public static final String KEY_USERNAMEFILE = "file";
    String filePath;
    private Button btnUpload;
    private TextView textViewResponse;
    HttpURLConnection conn = null;
    HttpEntity resEntity;
    DataOutputStream dos = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);
        // Receiving the data from previous activity
        btnUpload = (Button) findViewById(R.id.btnUpload);
        textViewResponse = (TextView) findViewById(R.id.textViewResponse);
        Intent i = getIntent();

        // image or video path that is captured in previous activity
        filePath = i.getStringExtra("filePath");
        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (filePath != null) {
                    Toast.makeText(getApplicationContext(),
                            "file path existing!", Toast.LENGTH_LONG).show();
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
            }
        });
    }

/*
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


//                uploadVideo();

                // uploading the file to server
//                loginUserVideo();
//                new UploadFileToServer().execute();
            }
        });
*/




//    }

/*    private void uploadVideo() {
    }
 class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(UploadActivity.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                super.onPostExecute(s);
                uploading.dismiss();

textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            protected String doInBackground(Void... params) {

Upload u = new Upload();
                String msg = u.uploadVideo(filePath);
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

                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }*/

    private void doFileUpload2() {
        Log.i("RESPONSE", "file1");

        try {
            Log.i("RESPONSE", "file2");

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(ApiConfig.SEND_MESSAGE);
            MultipartEntity reqEntity = new MultipartEntity();
            File file1 = new File(filePath);
            FileBody bin1 = new FileBody(file1);
            reqEntity.addPart("title", new StringBody(("Bjain Doctor")));
            reqEntity.addPart("chat_file", bin1);
            reqEntity.addPart("message", new StringBody("doctor::"+"Video"));
            reqEntity.addPart("chat_doc_id", new StringBody((PreferenceData.getId(getApplicationContext()))));
            reqEntity.addPart("chat_p_id", new StringBody(PreferenceData.getchatPatient_id(getApplicationContext())));
            reqEntity.addPart("token", new StringBody(PreferenceData.getPatient_Device_Token(getApplicationContext())));
            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            if (resEntity != null) {
                Log.e("RESPONSE", response_str);
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


}
