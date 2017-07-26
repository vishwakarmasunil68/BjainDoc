package com.emobi.bjaindoc;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
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

public class UsersAdapterchat_Doc extends RecyclerView.Adapter<UsersAdapterchat_Doc.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView edit,medication,prescription;
        Button status;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_medication_time);
            genre = (TextView) view.findViewById(R.id.tv_medication);

        }
    }


    public UsersAdapterchat_Doc(List<InfoApps> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_doc_chat_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InfoApps movie = moviesList.get(position);
        String messgae=movie.getName();
        getmessage(movie.getName(),holder.title,movie);


    }
    public String getmessage(String s,TextView tv,InfoApps movie){
        String date="";
        try{
            String[] str=s.split("::");
            if (str[0].equals("doctor")){
                tv.setText(movie.getName());
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.BLUE);
                tv.setBackgroundResource(R.drawable.balloon_outgoing_normal);
            }
            else {
                tv.setText(movie.getName());
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setTextColor(Color.RED);
                tv.setBackgroundResource(R.drawable.balloon_incoming_normal);
            }
            date=str[2]+"-"+str[1]+"-"+str[0];
        }
        catch (Exception e){
            date=s;
        }
        return date;
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
}
