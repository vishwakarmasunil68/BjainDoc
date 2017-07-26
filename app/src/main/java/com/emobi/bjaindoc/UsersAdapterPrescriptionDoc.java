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

import java.util.ArrayList;
import java.util.List;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapterPrescriptionDoc extends RecyclerView.Adapter<UsersAdapterPrescriptionDoc.MyViewHolder> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
    Context ctx;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, txt_patient_filename,year;
        ImageView edit,genre,medication,prescription;
        Button status;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_patient_date);
            txt_patient_filename = (TextView) view.findViewById(R.id.txt_patient_filename);
            genre = (ImageView) view.findViewById(R.id.txt_patient_message);

        }
    }


    public UsersAdapterPrescriptionDoc(ArrayList<InfoApps> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_doc_pre_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InfoApps movie = moviesList.get(position);
        holder.title.setText(movie.getNumber());

        holder.txt_patient_filename.setText(movie.getName());
        Typeface tf=Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        holder.txt_patient_filename.setTypeface(tf);
        holder.title.setTypeface(tf);
        holder.genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file_path = movie.getDataAdd();
                Intent intent=new Intent(ctx,PDFFromServerActivity.class);
                Log.d("file_path",file_path);
                intent.putExtra("file_path", file_path);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("size",String.valueOf(moviesList.size()));
        return moviesList.size();

    }
}
