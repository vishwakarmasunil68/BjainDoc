package patient_side;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emobi.bjaindoc.ApiConfig;
import com.emobi.bjaindoc.DoctorInfo;
import com.emobi.bjaindoc.InfoApps;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.Util;
import com.emobi.bjaindoc.Utils;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emobi-Android-002 on 8/24/2016.
 */
public class UsersAdapter_Serach_Doc extends RecyclerView.Adapter<UsersAdapter_Serach_Doc.MyViewHolder> {
    Boolean flag = false;
    private List<InfoApps> moviesList;
    ImageView img_profile;
    public static String reg_id,reg_name,reg_address,reg_designation;
    public static String avl_start_time1_morn,morn_loca_day1,avl_end_time1_morn,avl_start_time1_afternoon,
            avl_end_time1_afternoon,afternoon_loca_day1,avl_start_time1_even,avl_end_time1_even,even_loca_day1
            ,
            avl_start_time2_morn,avl_end_time2_morn,morn_loca_day2,avl_start_time2_afternoon,avl_end_time2_afternoon
            ,afternoon_loca_day2,avl_start_time2_even,avl_end_time2_even,even_loca_day2,

    avl_start_time3_morn,avl_end_time3_morn,morn_loca_day3,avl_start_time3_afternoon,avl_end_time3_afternoon
            ,afternoon_loca_day3,avl_start_time3_even,avl_end_time3_even,even_loca_day3,

    avl_start_time4_morn,avl_end_time4_morn,morn_loca_day4,aafternoon_loca_day4,even_loca_day4,avl_start_time4_afternoon,avl_end_time4_afternoon
            ,avl_start_time4_even,avl_end_time4_even,

    avl_start_time5_morn,avl_end_time5_morn,avl_start_time5_afternoon,avl_end_time5_afternoon
            ,avl_start_time5_even,avl_end_time5_even,morn_loca_day5,aafternoon_loca_day5,even_loca_day5,

    avl_start_time6_morn,avl_end_time6_morn,avl_start_time6_afternoon,avl_end_time6_afternoon
            ,avl_start_time6_even,avl_end_time6_even,morn_loca_day6,aafternoon_loca_day6,even_loca_day6,

    avl_start_time7_morn,avl_end_time7_morn,avl_start_time7_afternoon,avl_end_time7_afternoon,
    morn_loca_day7,aafternoon_loca_day7,even_loca_day7,avl_start_time7_even,avl_end_time7_even;
    public static String reg_mob,name,email,department,address,design,speciality,photo;
    Context ctx;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,time;

        Button status;
        LinearLayout cv;

        public MyViewHolder(View view) {
            super(view);
            img_profile= (ImageView) view.findViewById(R.id.img_profile);
            title = (TextView) view.findViewById(R.id.tv_medication_time);
            genre = (TextView) view.findViewById(R.id.txt_patient_designation);
            time = (TextView) view.findViewById(R.id.txt_patient_address);
            cv= (LinearLayout) view.findViewById(R.id.cv);


        }
    }


    public UsersAdapter_Serach_Doc(List<InfoApps> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_searchdoc, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InfoApps movie = moviesList.get(position);
        holder.title.setText(movie.getName());

        Typeface tf=Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        holder.title.setTypeface(tf);
        holder.genre.setTypeface(tf);
        holder.time.setTypeface(tf);


        /*try {
            String bitmap = "http://www.bjain.com/doctor/upload/" + movie.getNumber();

            Log.e("stringToBitmap", bitmap.toString());
//            imageLoader.DisplayImage(bitmap, img_profile);
            getBitmapFromURL(bitmap);
        }
        catch (Exception e){
            Bitmap largeIcon = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_action_person);
            img_profile.setImageBitmap(largeIcon);
            Log.e("stringToBitmap", largeIcon.toString());
        }*/
        try {
            String bitmap = "http://www.bjain.com/doctor/upload/" + movie.getNumber();

            Log.e("stringToBitmap", bitmap.toString());
//            imageLoader.DisplayImage(bitmap, img_profile);
//            getBitmapFromURL(bitmap);
            if(movie.getNumber().length()>0){
                Picasso.with(ctx.getApplicationContext()).load(bitmap).resize(100, 100).into(img_profile);
            }
        }
        catch (Exception e){
            Bitmap largeIcon = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.bjainicon);
            img_profile.setImageBitmap(largeIcon);
            Log.e("stringToBitmap", largeIcon.toString());
        }
        holder.genre.setText(movie.getDesignation());
        holder.time.setText(movie.getDataAdd());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg_id= Search_Doctor.contactDetails1.get(position).getId();
                reg_name= Search_Doctor.contactDetails1.get(position).getName();
                reg_address= Search_Doctor.contactDetails1.get(position).getDataAdd();
                reg_designation= Search_Doctor.contactDetails1.get(position).getDesignation();
                Log.e("reg_id",reg_id);
                new CallServices().execute(ApiConfig.DOCTOR_INFO_URL);
                    /*Intent intent=new Intent(ctx,DoctorInfo.class);
                    intent.putExtra("reg_id",reg_id);
                    ctx.startActivity(intent);*/
            }
        });
        img_profile.setImageResource(R.drawable.bjainicon);
    }

    private Bitmap getBitmapFromURL(String imageUrl) {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            // Bitmap myBitmap = BitmapFactory.decodeStream(input);
            DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            Bitmap b = Utils.decodeSampledBitmapFromStream(input, width, height);
            Log.e("bitmap",b.toString());
            img_profile.setImageBitmap(b);
            return b;


        } catch (IOException e) {
            Log.e("bitmap",e.toString());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
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
//            Log.e("image",image);
            pd = new ProgressDialog(ctx);

            pd.setMessage("Working ...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            namevaluepair.add(new BasicNameValuePair("doc_id",reg_id));

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

            if (pd != null) {
                pd.dismiss();
            }


            if (result != null) {

                try {
                    JSONArray jsonArray=new JSONArray(result);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);

                    name=jsonObject.getString("reg_name");
                    email=jsonObject.getString("reg_email");
                    reg_mob =jsonObject.getString("reg_mob");
                    department=jsonObject.getString("department");
                    speciality=jsonObject.getString("specialist");
                    design=jsonObject.getString("designation");
                    address=jsonObject.getString("clinic_address");
                    photo=jsonObject.getString("photo");
                    Log.e("info",photo+design+department+speciality+address);

                    avl_start_time1_morn=jsonObject.getString("avl_start_time1_morn");
                    avl_end_time1_morn=jsonObject.getString("avl_end_time1_morn");
                    avl_start_time1_afternoon=jsonObject.getString("avl_start_time1_afternoon");
                    avl_end_time1_afternoon=jsonObject.getString("avl_end_time1_afternoon");
                    avl_start_time1_even=jsonObject.getString("avl_start_time1_even");
                    avl_end_time1_even=jsonObject.getString("avl_end_time1_even");
                    morn_loca_day1=jsonObject.getString("morn_loca_day1");
                    afternoon_loca_day1=jsonObject.getString("afternoon_loca_day1");
                    even_loca_day1=jsonObject.getString("even_loca_day1");

                    avl_start_time2_morn=jsonObject.getString("avl_start_time2_morn");
                    avl_end_time2_morn=jsonObject.getString("avl_end_time2_morn");
                    avl_start_time2_afternoon=jsonObject.getString("avl_start_time2_afternoon");
                    avl_end_time2_afternoon=jsonObject.getString("avl_end_time2_afternoon");
                    avl_start_time2_even=jsonObject.getString("avl_start_time2_even");
                    avl_end_time2_even=jsonObject.getString("avl_end_time2_even");
                    morn_loca_day2=jsonObject.getString("morn_loca_day2");
                    afternoon_loca_day2=jsonObject.getString("afternoon_loca_day2");
                    even_loca_day2=jsonObject.getString("even_loca_day2");

                    avl_start_time3_morn=jsonObject.getString("avl_start_time3_morn");
                    avl_end_time3_morn=jsonObject.getString("avl_end_time3_morn");
                    avl_start_time3_afternoon=jsonObject.getString("avl_start_time3_afternoon");
                    avl_end_time3_afternoon=jsonObject.getString("avl_end_time3_afternoon");
                    avl_start_time3_even=jsonObject.getString("avl_start_time3_even");
                    avl_end_time3_even=jsonObject.getString("avl_end_time3_even");
                    morn_loca_day3=jsonObject.getString("morn_loca_day3");
                    afternoon_loca_day3=jsonObject.getString("afternoon_loca_day3");
                    even_loca_day3=jsonObject.getString("even_loca_day3");

                    avl_start_time4_morn=jsonObject.getString("avl_start_time4_morn");
                    avl_end_time4_morn=jsonObject.getString("avl_end_time4_morn");
                    avl_start_time4_afternoon=jsonObject.getString("avl_start_time4_afternoon");
                    avl_end_time4_afternoon=jsonObject.getString("avl_end_time4_afternoon");
                    avl_start_time4_even=jsonObject.getString("avl_start_time4_even");
                    avl_end_time4_even=jsonObject.getString("avl_end_time4_even");
                    morn_loca_day4=jsonObject.getString("morn_loca_day4");
                    aafternoon_loca_day4=jsonObject.getString("afternoon_loca_day4");
                    even_loca_day4=jsonObject.getString("even_loca_day4");

                    avl_start_time5_morn=jsonObject.getString("avl_start_time5_morn");
                    avl_end_time5_morn=jsonObject.getString("avl_end_time5_morn");
                    avl_start_time5_afternoon=jsonObject.getString("avl_start_time5_afternoon");
                    avl_end_time5_afternoon=jsonObject.getString("avl_end_time5_afternoon");
                    avl_start_time5_even=jsonObject.getString("avl_start_time5_even");
                    avl_end_time5_even=jsonObject.getString("avl_end_time5_even");
                    morn_loca_day5=jsonObject.getString("morn_loca_day5");
                    aafternoon_loca_day5=jsonObject.getString("afternoon_loca_day5");
                    even_loca_day5=jsonObject.getString("even_loca_day5");

                    avl_start_time6_morn=jsonObject.getString("avl_start_time6_morn");
                    avl_end_time6_morn=jsonObject.getString("avl_end_time6_morn");
                    avl_start_time6_afternoon=jsonObject.getString("avl_start_time6_afternoon");
                    avl_end_time6_afternoon=jsonObject.getString("avl_end_time6_afternoon");
                    avl_start_time6_even=jsonObject.getString("avl_start_time6_even");
                    avl_end_time6_even=jsonObject.getString("avl_end_time6_even");
                    morn_loca_day6=jsonObject.getString("morn_loca_day6");
                    aafternoon_loca_day6=jsonObject.getString("afternoon_loca_day6");
                    even_loca_day6=jsonObject.getString("even_loca_day6");

                    avl_start_time7_morn=jsonObject.getString("avl_start_time7_morn");
                    avl_end_time7_morn=jsonObject.getString("avl_end_time7_morn");
                    avl_start_time7_afternoon=jsonObject.getString("avl_start_time7_afternoon");
                    avl_end_time7_afternoon=jsonObject.getString("avl_end_time7_afternoon");
                    avl_start_time7_even=jsonObject.getString("avl_start_time7_even");
                    avl_end_time7_even=jsonObject.getString("avl_end_time7_even");
                    morn_loca_day7=jsonObject.getString("morn_loca_day7");
                    aafternoon_loca_day7=jsonObject.getString("afternoon_loca_day7");
                    even_loca_day7=jsonObject.getString("even_loca_day7");

                    ctx.startActivity(new Intent(ctx,DoctorInfo.class));
//                    finish();


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }


    }
}