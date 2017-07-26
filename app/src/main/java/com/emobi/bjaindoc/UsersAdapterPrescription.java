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

public class UsersAdapterPrescription extends RecyclerView.Adapter<UsersAdapterPrescription.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView edit,medication,prescription;
        Button status;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_patient_date);
            genre = (TextView) view.findViewById(R.id.txt_patient_message);

        }
    }


    public UsersAdapterPrescription(List<InfoApps> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_patient_pre_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InfoApps movie = moviesList.get(position);
        holder.title.setText(movie.getNumber());
        holder.genre.setText(movie.getDataAdd());
        Typeface tf1= Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        holder.title.setTypeface(tf1);
        holder.genre.setTypeface(tf1);
    }

    @Override
    public int getItemCount() {
        Log.e("size",String.valueOf(moviesList.size()));
        return moviesList.size();

    }
}
