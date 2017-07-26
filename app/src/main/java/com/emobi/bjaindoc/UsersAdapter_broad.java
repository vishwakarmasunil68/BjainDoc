package com.emobi.bjaindoc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapter_broad extends RecyclerView.Adapter<UsersAdapter_broad.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
    public static String reason,patient_name,patient_Email;
Context ctx;
    public static String device_token;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView broadmsg,notes,prescription;
        Button status;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_medication_time);
            genre = (TextView) view.findViewById(R.id.tv_medication);
            broadmsg = (ImageView) view.findViewById(R.id.img_edit);
            notes = (ImageView) view.findViewById(R.id.img_medication);
            prescription = (ImageView) view.findViewById(R.id.img_prescription);
            status = (Button) view.findViewById(R.id.btn_status);
        }
    }


    public UsersAdapter_broad(List<InfoApps> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_patient_adapterbroad, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps movie = moviesList.get(position);
        holder.title.setText(movie.getName());
        holder.title.setTypeface(Typeface.create("Montez-Regular.ttf",Typeface.BOLD));
        holder.genre.setText(movie.getEmail_id());

        Typeface tf1= Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        holder.genre.setTypeface(tf1);
       if (movie.getStatus().equals("active")){
           holder.status.setText("Active");
           holder.status.setBackgroundColor(holder.status.getContext().getResources().getColor(R.color.green_500));
       }
        else {
           holder.status.setText("Deactive");
           holder.status.setBackgroundColor(holder.status.getContext().getResources().getColor(R.color.white));
       }
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                device_token=movie.getDevice_token();
                patient_name=movie.getName();

                Intent intent=new Intent(ctx,ChatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        holder.broadmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 reason=ViewPatientList_broad.contactDetails1.get(position).getId().toString();
                 patient_name=ViewPatientList_broad.contactDetails1.get(position).getName().toString();
                 patient_Email=ViewPatientList_broad.contactDetails1.get(position).getEmail_id().toString();
                Log.e("login_id",reason);
                Intent msgintent=new Intent(ctx, BroadCast_Msg_Class.class);
                msgintent.putExtra("reason", reason);
                msgintent.putExtra("patient_name", patient_name);
                msgintent.putExtra("patient_Email", patient_Email);
                msgintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(msgintent);


            }
        });
        holder.notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason=ViewPatientList_broad.contactDetails1.get(position).getId().toString();
                /*String patient_name=ViewPatientList.contactDetails1.get(position).getName().toString();
                String patient_Email=ViewPatientList.contactDetails1.get(position).getEmail_id().toString();*/
                Log.e("login_id",reason);
                Intent msgintent=new Intent(ctx, DoctorNotes.class);
                msgintent.putExtra("reason", reason);
                /*msgintent.putExtra("patient_name", patient_name);
                msgintent.putExtra("patient_Email", patient_Email);*/
                msgintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(msgintent);


            }
        });
        holder.prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason=ViewPatientList.contactDetails1.get(position).getId().toString();
                /*String patient_name=ViewPatientList.contactDetails1.get(position).getName().toString();
                String patient_Email=ViewPatientList.contactDetails1.get(position).getEmail_id().toString();*/
                Log.e("login_id",reason);
                Intent msgintent=new Intent(ctx, Prescription.class);
                msgintent.putExtra("reason", reason);
                /*msgintent.putExtra("patient_name", patient_name);
                msgintent.putExtra("patient_Email", patient_Email);*/
                msgintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(msgintent);


            }
        });
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPatientList.savechange.setVisibility(View.VISIBLE);
                if (flag==false){
                    flag=true;
                    holder.status.setText("Deactive");
                    holder.status.setTextColor(holder.status.getContext().getResources().getColor(R.color.white));
                    holder.status.setBackgroundColor(holder.status.getContext().getResources().getColor(R.color.colorBtnPressed));
                }
                else if (flag==true){
                    flag=false;
                holder.status.setText("Active");
                holder.status.setBackgroundColor(holder.status.getContext().getResources().getColor(R.color.green_500));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
