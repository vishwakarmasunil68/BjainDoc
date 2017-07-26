package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.bjaindoc.utls.ToastClass;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 *Create to bind jobs in list
 *
 * @version 1.0
 * @author prabhunathy
 * @since 1/4/16.
 */

public class UsersAdapter2 extends ArrayAdapter<InfoApps> {
    private List<InfoApps> items;
    Activity mContext;

    public UsersAdapter2(Activity context, int textViewResourceID, List<InfoApps> items){

        super(context,textViewResourceID,items);
        mContext = context;
        this.items = items;
    }

    @Override

    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = convertView;



        if(v==null)
        {

            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v=inflater.inflate(R.layout.view_patient_pre_adapter,null);
        }
        TextView title = (TextView) v.findViewById(R.id.tv_medication_time);
        CardView cardview = (CardView) v.findViewById(R.id.cardview);
        ImageView genre = (ImageView) v.findViewById(R.id.tv_medication);
        TextView txt_patient_filename = (TextView) v.findViewById(R.id.txt_patient_filename);
        title.setText(items.get(position).getNumber());

        txt_patient_filename.setText(items.get(position).getName());
        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Regular.ttf");
        txt_patient_filename.setTypeface(tf);
        title.setTypeface(tf);
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String file_path=items.get(position).getDataAdd();
//                Intent intent=new Intent(mContext,PDFFromServerActivity.class);
//                Log.d("file_path",file_path);
//                intent.putExtra("file_path", file_path);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
                String PATH=Environment.getExternalStorageDirectory()+ File.separator+"bjain"+File.separator+file_path;
                if(new File(PATH).exists()){
                    showPdf(mContext,PATH);
                }
                else {
                    new DownloadFile(mContext, file_path).execute();
                }
            }
        });
//        genre.setText(items.get(position).getDataAdd());
        return v;

    }

    class DownloadFile extends AsyncTask<String,Integer,Long> {
        ProgressDialog mProgressDialog;
        String strFolderName;
        String PATH;
        Activity activity;
        String file_path;
        DownloadFile(Activity activity,String file_path){
            this.activity=activity;
            this.file_path=file_path;
            mProgressDialog = new ProgressDialog(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(mProgressDialog!=null) {
                mProgressDialog.setMessage("Downloading");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setCancelable(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.show();
            }
        }
        @Override
        protected Long doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL("http://www.bjain.com/doctor/pdf/"+file_path);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                String targetFileName="Name"+".pdf";//Change name and subname
                int lenghtOfFile = conexion.getContentLength();
                PATH = Environment.getExternalStorageDirectory()+ File.separator+"bjain";
                File folder = new File(PATH);
                if(!folder.exists()){
                    folder.mkdir();//If there is no folder it will be created.
                }
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(PATH+File.separator+file_path);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress ((int)(total*100/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {}
            return null;
        }
        protected void onProgressUpdate(Integer... progress) {
            mProgressDialog.setProgress(progress[0]);
            if(mProgressDialog.getProgress()==mProgressDialog.getMax()){
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "File Downloaded", Toast.LENGTH_SHORT).show();
                showPdf(activity,PATH+File.separator+file_path);
            }
        }
        protected void onPostExecute(String result) {


        }
    }

    private final String TAG=getClass().getSimpleName();
    public void showPdf(Activity activity,String file_path)
    {
        Log.d(TAG, "showfile");
        File file = new File(file_path);
        if (file.exists()) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.emobi.bjaindoc.fileProvider", file);
                    intent.setDataAndType(contentUri, type);
                } else {
                    intent.setDataAndType(Uri.fromFile(file), type);
                }
                activity.startActivity(intent);
            } catch (ActivityNotFoundException anfe) {
                Toast.makeText(getApplicationContext(), "No activity found to open this attachment.", Toast.LENGTH_LONG).show();
            }
        } else {
            ToastClass.ShowLongToast(getApplicationContext(), "File Not Found");
        }

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
}
