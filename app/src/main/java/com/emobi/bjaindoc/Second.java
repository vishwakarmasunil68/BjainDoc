package com.emobi.bjaindoc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import database.PreferenceData;

public class Second extends Activity {
    Integer[] imageId = {
            R.drawable.account,
            R.drawable.doctor,
            R.drawable.doctoraddress,
//            R.drawable.otherpatient,
            R.drawable.blog,
            R.drawable.notification,
//            R.drawable.activation_new,
            R.drawable.logout
    };
    //    String []IteamList = {"Account","Doctor","Doctor Address","Other Patient","Blog","Notification","Activation","Logout"};
    String[]IteamList = {"Account","Doctor","Doctor Address","Blog","Notification","Logout"};
    ListView list;
    TextView patient_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        patient_name=(TextView)findViewById(R.id.patient_name);
        patient_name.setText(PreferenceData.getPatientName(getApplicationContext()));
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/Montez-Regular.ttf");
        patient_name.setTypeface(tf);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(new customAdapter());
    }

    public class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IteamList.length;
        }

        @Override
        public Object getItem(int position) {
            return IteamList;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            convertView = inflater.inflate(R.layout.listitem,null);
            TextView text_listitem = (TextView)convertView.findViewById(R.id.text_listitem);
            ImageView image_iteam = (ImageView)convertView.findViewById(R.id.image_pic);
            text_listitem.setText(IteamList[position]);
            Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
            text_listitem.setTypeface(tf);
            image_iteam.setImageResource(imageId[position]);
            convertView.setTag(position);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int click = (Integer) v.getId();
                    if (position == 0) {
                        Intent i = new Intent(Second.this, MainActivity.class);
                        startActivity(i);
                    }
                    if (position == 1) {
                        startActivity(new Intent(Second.this, DoctorProfile.class));
                    }
                    if (position == 2) {
                        startActivity(new Intent(Second.this, DoctorAddress.class));
                    }
                    if (position == 3) {
                        startActivity(new Intent(Second.this, Blog.class));
                    }
                    if (position == 4) {
                        startActivity(new Intent(Second.this, View_notes_patient.class));
                    }

                    if (position == 5) {
//                        Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
                        PreferenceData.setPatientLogin(getApplicationContext(),false);
                        Intent intent=new Intent(Second.this,SIgnInOption.class);
                        Toast.makeText(getApplicationContext(),"logout successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finishAffinity();
                    }

                }
            });
            return convertView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Log.e("clik", "action bar clicked");
//            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
