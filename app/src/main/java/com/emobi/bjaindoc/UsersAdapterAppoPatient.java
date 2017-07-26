package com.emobi.bjaindoc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapterAppoPatient extends RecyclerView.Adapter<UsersAdapterAppoPatient.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
    private CoordinatorLayout mSnackBarLayout;
    Context ctx;
    public static String device_token,patient_id,a_time,a_date;
    public int listposition;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, address, date,contact,time, dire;
        CircleImageView edit,imageview,medication,img_cancel,prescription;
        Button status;
        String convertdate,converttime;
        TextView call;
        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.d_date);
            dire = (TextView) view.findViewById(R.id.dire);
            name = (TextView) view.findViewById(R.id.d_name);
            contact = (TextView) view.findViewById(R.id.d_department);
            time = (TextView) view.findViewById(R.id.d_time);
            address = (TextView) view.findViewById(R.id.d_address);
            imageview = (CircleImageView) view.findViewById(R.id.d_photo);
            call = (TextView) view.findViewById(R.id.call);
//            mSnackBarLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);

        }
    }


    public UsersAdapterAppoPatient(List<InfoApps> moviesList, Context ctx, CoordinatorLayout mSnackBarLayout) {
        this.moviesList = moviesList;
        this.ctx = ctx;
        this.mSnackBarLayout = mSnackBarLayout;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps movie = moviesList.get(position);
        holder.contact.setText(movie.getDesignation());
        holder.name.setText(movie.getName());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("call",moviesList.get(position).getPaid_amount());
                String numb=moviesList.get(position).getPaid_amount();
                call(numb);

            }
        });

        holder.dire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("call",moviesList.get(position).getPaid_amount());
                ctx.startActivity(new Intent(ctx,DoctorAddress.class));

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
            final Date dateObj = sdf.parse(movie.getN_time());
            System.out.println(dateObj);
            holder.converttime=new SimpleDateFormat("hh:mm   a").format(dateObj);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.date.setText(holder.convertdate);
        holder.time.setText(holder.converttime);
        holder.address.setText(movie.getDataAdd());

        Log.e("addr",movie.getDesignation()+movie.getDataAdd());
        try {
            if (moviesList.get(position).getAppname().length() > 0) {
                Picasso.with(ctx.getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + moviesList.get(position).getAppname()).resize(100, 100).into(holder.imageview);
            }
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }

        Typeface tf=Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
//        holder.name.setTypeface(tf);
        holder.dire.setTypeface(tf);
        holder.time.setTypeface(tf);

        holder.address.setTypeface(tf);
        holder.contact.setTypeface(tf);
        holder.date.setTypeface(tf);

    }


    @Override
    public int getItemCount() {
        Log.e("size",String.valueOf(moviesList.size()));
        return moviesList.size();

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

    private void showSnackMessage(String msg) {
        Snackbar snack = Snackbar.make(mSnackBarLayout, msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(ctx, R.color.nav_drawer_item_text_selected));
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }


}
