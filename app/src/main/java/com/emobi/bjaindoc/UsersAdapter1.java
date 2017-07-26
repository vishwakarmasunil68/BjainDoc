package com.emobi.bjaindoc;

import android.content.Context;
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

public class UsersAdapter1 extends RecyclerView.Adapter<UsersAdapter1.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
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


    public UsersAdapter1(List<InfoApps> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_patient_med_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InfoApps movie = moviesList.get(position);
        String date=movie.getNumber();

        holder.title.setText(getDate(date));

        holder.genre.setText(movie.getDataAdd());

        Typeface tf=Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        holder.title.setTypeface(tf);
        holder.genre.setTypeface(tf);

        /*ValueAnimator colorAnim = ObjectAnimator.ofInt(holder.genre, "backgroundColor", RED, BLUE);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();*/


//        if(textColorAnim != null) {
//        textColorAnim.cancel();


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
        Log.e("sizepre", String.valueOf(moviesList.size()));
        return moviesList.size();
    }
}
