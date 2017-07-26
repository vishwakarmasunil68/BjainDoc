package patient_side;

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

import com.emobi.bjaindoc.InfoApps;
import com.emobi.bjaindoc.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Emobi-Android-002 on 8/24/2016.
 */
public class UsersAdapterBroadcastPatient extends RecyclerView.Adapter<UsersAdapterBroadcastPatient.MyViewHolder> {
    Boolean flag = false;
    private List<InfoApps> moviesList;
    Context ctx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,time;
        ImageView  medication, prescription;
        CircleImageView img_profile;
        Button status;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_medication_time);
            genre = (TextView) view.findViewById(R.id.tv_medication);
            time = (TextView) view.findViewById(R.id.text_broad);
            img_profile= (CircleImageView) view.findViewById(R.id.img_profile);

            try {
                Picasso.with(ctx.getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + PreferenceData.getDoctorPhotoUrl(ctx.getApplicationContext())).into(img_profile);
            } catch (Exception e) {
                Log.d("sunil", e.toString());
            }


        }
    }


    public UsersAdapterBroadcastPatient(List<InfoApps> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_patientbroadcastmessage_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InfoApps movie = moviesList.get(position);


        holder.title.setText(movie.getNumber());
        holder.genre.setText(movie.getName());
        holder.time.setText(movie.getDataAdd());
        Typeface tf=Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        holder.title.setTypeface(tf);
        holder.genre.setTypeface(tf);
        holder.time.setTypeface(tf);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
