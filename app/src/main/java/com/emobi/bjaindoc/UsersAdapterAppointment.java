package com.emobi.bjaindoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
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

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapterAppointment extends RecyclerView.Adapter<UsersAdapterAppointment.MyViewHolder> {
    public static String a_date;
    public static String a_time;
    public static String device_token;
    public static String patient_id;
    Context ctx;
    Boolean flag;
    public int listposition;
    private CoordinatorLayout mSnackBarLayout;
    private List<InfoApps> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contact;
        public TextView date;
        String convertdate,converttime;
        ImageView edit;
        public TextView genre;
        CircleImageView d_photo;
        ImageView medication;
        public TextView name,d_department,d_dire,d_address;
        ImageView prescription;
        Button status;
        public TextView time;
        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.d_date);
            d_department = (TextView) view.findViewById(R.id.d_department);
            d_address = (TextView) view.findViewById(R.id.d_address);
            d_dire = (TextView) view.findViewById(R.id.d_dire);
            name = (TextView) view.findViewById(R.id.d_name);
            contact = (TextView) view.findViewById(R.id.d_call);
            time = (TextView) view.findViewById(R.id.d_time);
            d_photo = (CircleImageView) view.findViewById(R.id.d_photo);
        }
    }


    public UsersAdapterAppointment(List<InfoApps> moviesList, Context ctx, CoordinatorLayout mSnackBarLayout) {
        this.moviesList = moviesList;
        this.ctx = ctx;
        this.mSnackBarLayout = mSnackBarLayout;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcomingdoc, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps movie = moviesList.get(position);
//        holder.contact.setText(movie.getNumber());
        holder.d_department.setText(PreferenceData.getDoctorDepartment(ctx.getApplicationContext()));
        holder.d_address.setText(PreferenceData.getDoctorclinic_address(ctx.getApplicationContext()));
        holder.name.setText(movie.getName());

        try {
                String bitmap = "http://www.bjain.com/doctor/upload/" + movie.getPass();
          String bitmap1 = movie.getPass();
                Log.e("stringToBitmap", bitmap.toString());
            if (bitmap1.length()>0) {
                Picasso.with(ctx.getApplicationContext()).load(bitmap).resize(100, 100).into(holder.d_photo);
            }
            else {
                holder.d_photo.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_action_person
                ));
            }
            }
        catch (Exception e){

        }

        Typeface tf1= Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        holder.contact.setTypeface(tf1);
        holder.d_department.setTypeface(tf1);
        holder.d_address.setTypeface(tf1);
        holder.d_dire.setTypeface(tf1);
        holder.date.setTypeface(tf1);
        holder.time.setTypeface(tf1);

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("call",moviesList.get(position).getPaid_amount());
                String numb=moviesList.get(position).getNumber();
                call(numb);

            }

            private void call(String Number) {

                try{
                    Intent in=new Intent(Intent.ACTION_DIAL, Uri.parse(Number));
                    in.setData(Uri.parse("tel:"+Number));
                    Log.e("call","call");
                    ctx.startActivity(in);
                }

                catch (Exception e){
                    Log.e("catch",e.toString());
                    Toast.makeText(ctx,"Number is not found",Toast.LENGTH_SHORT).show();
                }
            }
        });

        try {
            SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat writeFormat = new SimpleDateFormat("MMM  dd,yyyy", Locale.US);
            String resr=movie.getInvo_date();
            holder.convertdate = writeFormat.format(readFormat.parse(resr));


            Log.v("Changed date",""+writeFormat.format(holder.convertdate));
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(movie.getAppotime());
            System.out.println(dateObj);
            holder.converttime=new SimpleDateFormat("hh:mm   a").format(dateObj);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.date.setText(holder.convertdate);
        holder.time.setText(holder.converttime);



        holder.d_dire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("list","li");
                    patient_id = TabDoctorUpcomingDate.contactDetails1.get(position).getId();
                    a_date = TabDoctorUpcomingDate.contactDetails1.get(position).getInvo_date();
                    device_token = TabDoctorUpcomingDate.contactDetails1.get(position).getDevice_token();
                    a_time = TabDoctorUpcomingDate.contactDetails1.get(position).getAppotime();
                    Log.e("list","li");
                    new CallServices().execute(ApiConfig.CANCEL_APPOINTMENT);
                }
                catch (Exception e){
                    e.toString();
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        Log.e("size",String.valueOf(moviesList.size()));
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
                Log.e("list","list12");
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
            namevaluepair.add(new BasicNameValuePair("a_p_id", patient_id));
            namevaluepair.add(new BasicNameValuePair("a_date", a_date));
            namevaluepair.add(new BasicNameValuePair("a_time", a_time));
            namevaluepair.add(new BasicNameValuePair("title", "Cancel Appointment"));
            namevaluepair.add(new BasicNameValuePair("message", "Your Appointment has been canceled,please contact to your doctor"));
            namevaluepair.add(new BasicNameValuePair("token", device_token));
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

                    moviesList.remove(listposition);
                    notifyItemRemoved(listposition);
//                    moviesList.notify();
                    showSnackMessage("Appointment canceled successfully");
                    Snackbar.make(mSnackBarLayout, "Appointment canceled successfully", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();

                    showSnackMessage("Appointment canceled successfully");
//                    notifyItemRangeChanged(,moviesList.size());
                    Log.e("Post Method", "toString(");

                    JSONObject jsonObject = new JSONObject(result);

                    String message=jsonObject.getString("a_message");

                    if (message.equals("Appoiment cancel Successfully")){
//                        Toast.makeText(ctx,"Appointment canceled successfully",Toast.LENGTH_LONG).show();
                        /*moviesList.remove(listposition);
                        notifyItemRemoved(listposition);*/
//                        showSnackMessage("Appointment canceled successfully");
                    }
                    else {
                        Toast.makeText(ctx,"Appointment  not canceled,please try again",Toast.LENGTH_LONG).show();

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
