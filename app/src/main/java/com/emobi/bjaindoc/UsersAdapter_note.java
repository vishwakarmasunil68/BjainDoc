package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import interfaces.AdapterToActivity;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapter_note extends ArrayAdapter<InfoApps> {
    Boolean flag=false;
    private List<InfoApps> moviesList;
    String positi;
    public static String reason,patient_name,patient_Email;
    Context ctx;
    LayoutInflater inflater ;
    public static String device_token;
    private AdapterToActivity mCallback;



    public static TextView title, year;
    static ImageView broadmsg,notes,prescription;
    static public Spinner genre;
    static Button status;






    public UsersAdapter_note(Activity context, int textViewResourceID, List<InfoApps> moviesList, AdapterToActivity mCallback) {
        super(context, textViewResourceID, moviesList);
        this.ctx = context;
        this.moviesList = moviesList;
        this.mCallback=mCallback;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = inflater.inflate(R.layout.view_doc_adapternote, null);
        title = (TextView) convertView.findViewById(R.id.tv_medication_time);
        genre = (Spinner) convertView.findViewById(R.id.tv_medication);
        broadmsg = (ImageView) convertView.findViewById(R.id.img_edit);
//            notes = (ImageView) view.findViewById(R.id.img_medication);
        prescription = (ImageView) convertView.findViewById(R.id.img_prescription);
        status = (Button) convertView.findViewById(R.id.btn_status);

        Typeface tf1= Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
        title.setTypeface(tf1);
        final InfoApps movie = moviesList.get(position);
        title.setText(movie.getName());

        String stat =(movie.getStatus());
        int currentselection=0;
        if (stat.equals("public")){
            genre.setSelection(1);
            currentselection=1;
        }
        else{
            genre.setSelection(0);
            currentselection=0;
        }

        final int finalCurrentselection = currentselection;
        genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selecteditee;
                if(finalCurrentselection ==i) {
//                int init =holder.genre.getSelectedItemPosition();

                }
                else{
                    if (i == 0) {


                        selecteditee = "private";
                        positi = DoctorNotes.contactDetails1.get(position).getId();
                        new CallServices(selecteditee, positi).execute(ApiConfig.UPDATE_NOTES);
                    } else if (i == 1) {
                        selecteditee = "public";

                        positi = DoctorNotes.contactDetails1.get(position).getId();
                        new CallServices(selecteditee, positi).execute(ApiConfig.UPDATE_NOTES);
                    }
                    Log.d("sunil", DoctorNotes.contactDetails1.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        return convertView;




    }







    public class CallServices extends AsyncTask<String, String, String> {
                ProgressDialog pd;
        String pos;
        String data;
        public CallServices(String data,String position){
            this.pos=position;
            this.data=data;
        }

        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
        String result;
        String tag;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            try {
                pd = new ProgressDialog(ctx);

                pd.setMessage("Working ...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub


            namevaluepair.add(new BasicNameValuePair("note_id", pos ));
            namevaluepair.add(new BasicNameValuePair("note_msg",data  ));

            try {

                result = Util.executeHttpPost(params[0], namevaluepair);

                Log.e("result", result.toString());

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
}catch (Exception e){
    e.printStackTrace();
}
            Toast.makeText(ctx, "updated successfully", Toast.LENGTH_SHORT).show();

            mCallback.refreshView();

        }
    }
    public int getItemCount() {
        return moviesList.size();
    }
}
