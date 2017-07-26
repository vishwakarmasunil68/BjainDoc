package com.emobi.bjaindoc;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;

public class ProceedCheckOut extends AppCompatActivity {
    Toolbar toolbar;
    TextView name,age,sex,prescription,medicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_check_out);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Prescription Checkout");

        name= (TextView) findViewById(R.id.name_et);
        age= (TextView) findViewById(R.id.age_et);
        sex= (TextView) findViewById(R.id.sex_et);
        prescription= (TextView) findViewById(R.id.prescription_et);
        medicine= (TextView) findViewById(R.id.medicine_tv);

        Bundle extraBundle=getIntent().getExtras();
        if(extraBundle!=null){
            name.setText(extraBundle.getString("name"));
            age.setText(extraBundle.getString("age"));
            sex.setText(extraBundle.getString("sex"));
            Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

            prescription.setText(extraBundle.getString("prescription"));
            prescription.setTypeface(tf);
            String med=extraBundle.getString("medicine");
            if(med.length()>0){
                medicine.setText("");
            }
            String[] str=med.split("::");
            if(str.length>0) {
                for(String s:str)
                medicine.append(s+"\n");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pre_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.print){
//            new FileUpload().execute();
        }
        return super.onOptionsItemSelected(item);
    }
    class FileUpload extends AsyncTask<Void,Void,Void> {
        File f;
        String id;
        String description;
        String medicine;
        String s="";
        public FileUpload(String file_path,String id,String description,String medicine){
            f=new File(file_path);
            this.id=id;
            this.description=description;
            this.medicine=medicine;
        }
        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://www.bjain.com/doctor/prescription.php");
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                File file1 = new File(f.toString());
                FileBody bin1 = new FileBody(file1);

                Log.d("sunil", "pre_p_id:-" + Prescription.id);
                Log.d("sunil","pre_doc_id:-"+id);
                Log.d("sunil","pre_date:-"+UtilsValidate.getCurrentDate());
                Log.d("sunil","pre_message:-"+description);
                Log.d("sunil","pre_medicine:-"+medicine);

                reqEntity.addPart("pre_p_id", new StringBody(Prescription.id));
                reqEntity.addPart("pre_doc_id", new StringBody(id));
                reqEntity.addPart("pre_date", new StringBody(UtilsValidate.getCurrentDate()));
                reqEntity.addPart("pre_message", new StringBody(description));
                reqEntity.addPart("pre_medicine", new StringBody(medicine));
                reqEntity.addPart("pre_file", bin1);
                post.setEntity(reqEntity);
                HttpResponse response = client.execute(post);
                HttpEntity resEntity = response.getEntity();
                String response_str = EntityUtils.toString(resEntity);
                Log.e("sunil", response_str);
                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.v("sunil", "Response: " +  responseStr);
                    s=responseStr;
                    // you can add an if statement here and do other actions based on the response
                }
            }
            catch(Exception e)
            {
                Log.d("sunil",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("sunil","response:-"+s);
        }
    }
}
