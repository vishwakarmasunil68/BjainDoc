package patient_side;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.emobi.bjaindoc.InfoApps;
import com.emobi.bjaindoc.R;

import java.util.List;

/**
 * Created by Emobi-Android-002 on 8/24/2016.
 */
public class UsersAdapter_Broadnote extends RecyclerView.Adapter<UsersAdapter_Broadnote.MyViewHolder> {
    Boolean flag = false;
    private List<InfoApps> moviesList;
    Context ctx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView edit, medication, prescription;
        Button status;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_medication_time);
//            genre = (TextView) view.findViewById(R.id.txt_patient_id);

        }
    }


    public UsersAdapter_Broadnote(List<InfoApps> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_broadmsg, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InfoApps movie = moviesList.get(position);
//        holder.title.setText(movie.getNumber());
        holder.title.setText(movie.getDataAdd());

        Typeface tf=Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        holder.title.setTypeface(tf);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
