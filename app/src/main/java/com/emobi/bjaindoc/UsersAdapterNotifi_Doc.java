package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapterNotifi_Doc extends RecyclerView.Adapter<UsersAdapterNotifi_Doc.MyViewHolder> {
    Boolean flag=false;
    public int listposition;
    private List<InfoApps> moviesList;
    String patient_id;
    private CoordinatorLayout mSnackBarLayout;
    Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date,time, msg;
        ImageView edit,img_cancel,medication,prescription;
        Button status;
        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.tv_medication_time);
            msg = (TextView) view.findViewById(R.id.tv_medication);
            title = (TextView) view.findViewById(R.id.txt_patient_title);
            time  = (TextView) view.findViewById(R.id.txt_patient_time);
            img_cancel= (ImageView) view.findViewById(R.id.img_cancel);
        }
    }


    public UsersAdapterNotifi_Doc(List<InfoApps> moviesList, Context ctx,CoordinatorLayout mSnackBarLayout) {
        this.moviesList = moviesList;
        this.ctx = ctx;
        this.mSnackBarLayout = mSnackBarLayout;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_doc_noti_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InfoApps movie = moviesList.get(position);
        String date=movie.getNumber();
        holder.img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    patient_id = View_notes.contactDetails1.get(position).getEmail_id().toString();
                    new CallServices().execute(ApiConfig.CANCEL_NOTIFY  );
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        holder.title.setText(movie.getId());
        holder.date.setText(movie.getInvo_date());
        holder.msg.setText(movie.getName());
        holder.time.setText(movie.getN_time());

        Typeface tf1= Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        holder.title.setTypeface(tf1);
        holder.date.setTypeface(tf1);
        holder.time.setTypeface(tf1);
        holder.msg.setTypeface(tf1);



    }
    public String getDate(String s){
        String date="";
        try{
            String[] str=s.split("-");
            date=str[2]+"-"+str[1]+"-"+str[0];
        }
        catch (Exception e){
            date=s;
        }
        return date;
    }

    @Override
    public int getItemCount() {
        Log.e("sizepre",String.valueOf(moviesList.size()));
        return moviesList.size();
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
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("note_id", patient_id));
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

//                    moviesList.remove(listposition);
//                    notifyItemRemoved(listposition);
//                    moviesList.notify();
                    Snackbar.make(mSnackBarLayout, "Notification canceled successfully", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();

                    showSnackMessage("Notification canceled successfully");

                    JSONObject jsonObject = new JSONObject(result);

                    String message=jsonObject.getString("message");

                    if (message.equals("Notification cancel Successfully")){
//                        Toast.makeText(ctx,"Appointment canceled successfully",Toast.LENGTH_LONG).show();
                        moviesList.remove(listposition);
                        notifyItemRemoved(listposition);
//                        showSnackMessage("Appointment canceled successfully");
                    }
                    else {
                        Toast.makeText(ctx,"Notification not canceled,please try again",Toast.LENGTH_LONG).show();

                    }

                    /*for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String message=jsonObject2.getString("p_message");
                        if (message.equals("Not Book any Appoiment")){
                            Toast.makeText(ViewAppointmentList.this,"Not Book any Appoiment",Toast.LENGTH_LONG).show();
                        }
                        else {

                            String  p_id = jsonObject2.getString("p_id");
                            String a_date = jsonObject2.getString("a_date");
                            String a_time = jsonObject2.getString("a_time");
                            String p_name = jsonObject2.getString("p_name");
                            String p_mob = jsonObject2.getString("p_mob");
                            String p_device_token = jsonObject2.getString("p_device_token");
                            Log.e("2", jsonObject2.toString());

                            Detailapp = new InfoApps();

                            Detailapp.setId(p_id);
                            Detailapp.setNumber(p_mob);
                            Detailapp.setName(p_name);
                            Detailapp.setInvo_date(a_date);
                            Detailapp.setAppotime(a_time);
                            Detailapp.setDevice_token(p_device_token);
                            contactDetails1.add(Detailapp);
                            mAdapter = new UsersAdapterAppointment(contactDetails1,getApplicationContext());
                            mRecyclerView.setAdapter(mAdapter);*/

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }

    }

    private void showSnackMessage(String msg) {
        Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(ctx, R.color.nav_drawer_item_text_selected));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }

}
