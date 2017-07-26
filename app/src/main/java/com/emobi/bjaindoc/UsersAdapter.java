package com.emobi.bjaindoc;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Create to bind jobs in list
 *
 * @author prabhunathy
 * @version 1.0
 * @since 1/4/16.
 */

public class UsersAdapter extends ArrayAdapter<InfoApps> {
    private List<InfoApps> items;
    Activity mContext;
    public static String device_token,patient_id,patient_name,patient_age,patient_Email;
    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
    LayoutInflater inflater ;
    public UsersAdapter(Activity context, int textViewResourceID, List<InfoApps> items) {

        super(context, textViewResourceID, items);
        mContext = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

//        View v = convertView;

        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = inflater.inflate(R.layout.view_patient_adapter, null);
            viewHolder.imageview = (CircleImageView) convertView.findViewById(R.id.img_profile);
            viewHolder.txt_patient_name = (TextView) convertView.findViewById(R.id.tv_medication_time);
            viewHolder.txt_patient_id = (TextView) convertView.findViewById(R.id.tv_medication);
            viewHolder.txt_last_seenid = (TextView) convertView.findViewById(R.id.txt_last_seenid);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.cv = (LinearLayout) convertView.findViewById(R.id.cv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt_patient_name.setText(items.get(position).getName());
        viewHolder.txt_patient_id.setText(items.get(position).getEmail_id());
        String msg=items.get(position).getEmail_id();
        String lastchat=items.get(position).getN_time();
try {
    String lastchat1 = lastchat.split(" ")[1];

    try {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        final Date dateObj = sdf.parse(lastchat1);
        System.out.println(dateObj);
        String converttime = new SimpleDateFormat("hh:mm  a").format(dateObj);
        viewHolder.txt_last_seenid.setText(converttime);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
catch (Exception e){
    e.printStackTrace();
}
        if(msg.contains("urgent")) {


                ValueAnimator colorAnim = ObjectAnimator.ofInt(viewHolder.txt_patient_id, "backgroundColor", RED, BLUE);
                colorAnim.setDuration(3000);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                colorAnim.start();
            String mess=msg.split("::")[2];
            viewHolder.txt_patient_id.setText(mess);
        }
        try {
            if (items.get(position).getAppname().length() > 0) {
                Picasso.with(mContext.getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + items.get(position).getAppname()).resize(100, 100).into(viewHolder.imageview);
            }
            else{
                    viewHolder.imageview.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_person));
                Log.d("el","else");
            }
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                device_token = ViewPatientList.contactDetails1.get(position).getDevice_token().toString();;
                patient_name = items.get(position).getName();
                Intent intent = new Intent(mContext, PatientCompleteDetailAccount.class);
                patient_id = ViewPatientList.contactDetails1.get(position).getId().toString();
                patient_name = ViewPatientList.contactDetails1.get(position).getName().toString();
                patient_age= ViewPatientList.contactDetails1.get(position).getAge().toString();
                patient_Email = ViewPatientList.contactDetails1.get(position).getBg().toString();
                String p_pass = ViewPatientList.contactDetails1.get(position).getPass().toString();
                String photo=ViewPatientList.contactDetails1.get(position).getAppname().toString();
                String status=ViewPatientList.contactDetails1.get(position).getStatus().toString();
                PreferenceData.setPatient_Device_Token(getContext().getApplicationContext(), device_token);
                PreferenceData.setchatPatient_id(getContext(), patient_id);
                PreferenceData.setPatientPassword(getContext().getApplicationContext(), p_pass);
                PreferenceData.setPatientEmail(getContext(), patient_Email);
                Log.d("device_token",device_token);
                intent.putExtra("reason", patient_id);
                intent.putExtra("patient_name", patient_name);
                intent.putExtra("patient_photo",photo);
                intent.putExtra("patient_Email", patient_Email);
                intent.putExtra("patient_status", status);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
//                mContext.startActivityForResult(intent, 1);
            }
        });

        return convertView;

   }

    static class ViewHolder{
        CircleImageView imageview;
        TextView txt_patient_name;
        TextView txt_patient_id,txt_last_seenid;

        TextView textView;
        LinearLayout cv;
    }
}