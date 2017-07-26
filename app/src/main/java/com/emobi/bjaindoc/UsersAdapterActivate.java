package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import database.PreferenceData;
import interfaces.AdapterToActivity;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapterActivate extends RecyclerView.Adapter<UsersAdapterActivate.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
    public static String reason,patient_name,patient_Email,patient_id,patie_name,p_bg,p_status,p_email_id,p_age,p_height
            ,p_weight,p_password;;
    static Context ctx;
    public static String device_token;

    int i=0;
    List<ImageView> imageViews=new ArrayList<>();
    boolean[] statuslist;
    String[] patient_id_list;
    String[] patient_name_list;
    String[] patient_email_list;
    String[] patient_age_list;
    String[] patient_bg_list;
    String[] patient_password_list;
    String[] patient_height_list;
    String[] patient_weight_list;
    String[] patient_status_list;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, genre;
        ImageView edit,medication,prescription;
        Button status;
        CardView cv;
        ImageView img_profile,textactivation;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_medication_time);
            genre = (TextView) view.findViewById(R.id.tv_medication);
            edit=(ImageView) view.findViewById(R.id.img_edit);
            cv = (CardView) view.findViewById(R.id.cv);
            img_profile= (ImageView) view.findViewById(R.id.img_profile);
            textactivation= (ImageView) view.findViewById(R.id.txt_patient_activation);
            SpannableString s = new SpannableString(ctx.getResources().getString(R.string.app_name));
            s.setSpan(new TypefaceSpan(ctx, "fonts/AlexBrush-Regular.ttf"), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }


    public UsersAdapterActivate(ArrayList<InfoApps> moviesList, Activity ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
        statuslist=new boolean[moviesList.size()];
        patient_id_list=new String[moviesList.size()];
        patient_name_list=new String[moviesList.size()];
        patient_email_list=new String[moviesList.size()];
        patient_age_list=new String[moviesList.size()];
        patient_bg_list=new String[moviesList.size()];
        patient_password_list=new String[moviesList.size()];
        patient_height_list=new String[moviesList.size()];
        patient_weight_list=new String[moviesList.size()];
        patient_status_list=new String[moviesList.size()];
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_patient_adapteracti, parent, false);
//        img_profile = (ImageView) itemView.findViewById(R.id.img_profile);
//        textactivation = (ImageView) itemView.findViewById(R.id.txt_patient_activation);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps movie = moviesList.get(position);
        holder.title.setText(movie.getName());

        String status=movie.getStatus();
        if (status.equalsIgnoreCase("active")){
            holder.textactivation.setImageResource(R.drawable.toggle1);
            flag=true;
            holder.textactivation.getLayoutParams().width  = 120;
            holder.textactivation.getLayoutParams().height = 50;;
        }
        else {

            flag=false;
            holder.textactivation.setImageResource(R.drawable.toggle2);
            holder.textactivation.getLayoutParams().width  = 120;
            holder.textactivation.getLayoutParams().height = 50;;
        }
        Typeface tf1= Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        holder.title.setTypeface(tf1);
        holder.genre.setTypeface(tf1);
        holder.genre.setText(movie.getEmail_id());

        try {
            if(movie.getAppname().length()>0) {
                String bitmap = "http://www.bjain.com/doctor/upload/" + movie.getAppname();

                Log.e("stringToBitmap", bitmap.toString());
                Picasso.with(ctx.getApplicationContext()).load(bitmap).resize(100, 100).into(holder.img_profile);
            }
        }
        catch (Exception e){

        }

        String p_status1= ViewPatientListactivation.contactDetails1.get(position).getStatus();
        if(p_status1.equals("active")){
            statuslist[position]=true;
        }
        else {
            statuslist[position]=false;
        }
        patient_id_list[position]= ViewPatientListactivation.contactDetails1.get(position).getId();
        patient_name_list[position]= ViewPatientListactivation.contactDetails1.get(position).getName();
        patient_email_list[position]= ViewPatientListactivation.contactDetails1.get(position).getEmail_id();
        patient_age_list[position]= ViewPatientListactivation.contactDetails1.get(position).getAge();
        patient_bg_list[position]= ViewPatientListactivation.contactDetails1.get(position).getBg();
        patient_password_list[position]= ViewPatientListactivation.contactDetails1.get(position).getPass();
        patient_height_list[position]= ViewPatientListactivation.contactDetails1.get(position).getHeight();
        patient_weight_list[position]= ViewPatientListactivation.contactDetails1.get(position).getWeight();
        patient_status_list[position]= ViewPatientListactivation.contactDetails1.get(position).getStatus();

        holder.textactivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CallServices(position).execute(ApiConfig.UPDATE_PATIENT);
            }
        });
        holder.textactivation.setTag(position + "");
        imageViews.add(holder.textactivation);
    }

    public class CallServices extends AsyncTask<String, String, String> {
        //        ProgressDialog pd;
        int pos;
        public CallServices(int position){
            this.pos=position;
        }

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//            pd = new ProgressDialog(ctx);
//
//            pd.setMessage("Working ...");
//            pd.setIndeterminate(false);
//            pd.setCancelable(false);
//            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub


            Log.d("sunil", "id:-" + patient_id_list[pos] + "\n" +
                    "name:-" + patient_name_list[pos] + "\n" +
                    "pass:-" + patient_password_list[pos] + "\n" +
                    "email:-" + patient_email_list[pos] + "\n" +
                    "age:-" + patient_age_list[pos] + "\n" +
                    "bg:-" + patient_bg_list[pos] + "\n" +
                    "weight:-" + patient_weight_list[pos] + "\n" +
                    "height:-" + patient_height_list[pos] + "\n" +
                    "status:-" + patient_status_list[pos]);
            namevaluepair.add(new BasicNameValuePair("p_id", patient_id_list[pos]));
            namevaluepair.add(new BasicNameValuePair("p_name", patient_name_list[pos]));
            namevaluepair.add(new BasicNameValuePair("p_login_pass", patient_password_list[pos]));
            namevaluepair.add(new BasicNameValuePair("p_login_id", patient_email_list[pos]));
            namevaluepair.add(new BasicNameValuePair("p_age", patient_age_list[pos]));
            namevaluepair.add(new BasicNameValuePair("p_bloodgroup", patient_bg_list[pos]));
            namevaluepair.add(new BasicNameValuePair("p_weight", patient_weight_list[pos]));
            namevaluepair.add(new BasicNameValuePair("p_height", patient_height_list[pos]));

            if(patient_status_list[pos].equals("active")){
                patient_status_list[pos]="deactive";
                statuslist[pos]=false;
            }
            else{
                if(patient_status_list[pos].equals("deactive")) {
                    patient_status_list[pos] = "active";
                    statuslist[pos]=true;
                }
            }
            namevaluepair.add(new BasicNameValuePair("p_status", patient_status_list[pos]));

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

//            if (pd != null) {
//                pd.dismiss();
//            }
            Toast.makeText(ctx, "updated successfully", Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonObject = new JSONObject(result);
                String patient_id = jsonObject.getString("p_id");
                String p_st = jsonObject.getString("p_status");
                setNotification("",patient_id);
//                if (p_st.equalsIgnoreCase("active")) {
//                    textactivation.setImageResource(R.drawable.deactiveac);
//                }
//                else {
//                    textactivation.setImageResource(R.drawable.activationac);
//                }
                if(statuslist[pos]){
                    for(ImageView imageView:imageViews){
                        if(imageView.getTag().equals(pos+"")){
                            imageView.setImageResource(R.drawable.toggle1);
                            statuslist[pos]=false;
                        }
                    }
                }
                else{
                    for(ImageView imageView:imageViews){
                        if(imageView.getTag().equals(pos+"")){
                            imageView.setImageResource(R.drawable.toggle2);
                            statuslist[pos]=true;
                        }
                    }
                }

                AdapterToActivity obj= (AdapterToActivity) ctx;
                obj.refreshView();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
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
                try {
                    pd = new ProgressDialog(ctx.getApplicationContext());

                    pd.setMessage("Working ...");
                    pd.setIndeterminate(false);
                    pd.setCancelable(false);
                    pd.show();
                }
                catch (Exception e){

                }
            }

            @SuppressWarnings("deprecation")
            @Override
            protected String doInBackground(String... params) {
                //  TODO Auto-generated method stub

                namevaluepair.add(new BasicNameValuePair("token", ""));
                Log.d("sun", device_token);
                namevaluepair.add(new BasicNameValuePair("message", "doctor::"));
                namevaluepair.add(new BasicNameValuePair("purgentchat", ""));
                namevaluepair.add(new BasicNameValuePair("chat_doc_id", PreferenceData.getId(ctx)));
                namevaluepair.add(new BasicNameValuePair("chat_p_id", msg));
                namevaluepair.add(new BasicNameValuePair("title", "Bjain Doctor"));
                namevaluepair.add(new BasicNameValuePair("date", UtilsValidate.getCurrentDate()));
                namevaluepair.add(new BasicNameValuePair("time", UtilsValidate.getCurrentDateTime()));
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
                try {
                    if (pd != null) {
                        pd.dismiss();
                    }
                }
                catch (Exception e){

                }
                try {


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}