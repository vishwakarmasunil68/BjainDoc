package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Emobi-Android-002 on 8/16/2016.
 */
public class Updatepatient_patient extends Fragment {
    public static EditText ed1,edtxt_pmob,ed3, ed4, ed5, ed6, ed7,edtxt_descrption;
    public static final String REGISTER_URL = "http://www.bjain.com/doctor/registration.php";
    TextView txt_pname,basic_infor;

    Button btn_Submit;
    RadioGroup rg1,rg2;
    public static String rate1,rate2,reason;
    public static String selectedImagePath;
    public static EditText ed2;
    CircleImageView profile_image;
    private static final int FILE_SELECT_CODE = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       final  View view= inflater.inflate(R.layout.updateinfopatient_patient, container, false);
//        setContentView(R.layout.updateinfopatient_patient);
        edtxt_pmob = (EditText) view.findViewById(R.id.edtxt_pmob);
        ed1 = (EditText) view.findViewById(R.id.edtxt_pname);
        ed2 = (EditText) view.findViewById(R.id.edtxt_pemail);
        ed3 = (EditText) view.findViewById(R.id.edtxt_ppassword);
        ed4 = (EditText) view.findViewById(R.id.edtxt_page);
        ed5 = (EditText) view.findViewById(R.id.edtxt_pbgroup);
//        btn_Submit=(Button) view.findViewById(R.id.btn_submit);
        ed6 = (EditText) view.findViewById(R.id.edtxt_pwt);
        ed7 = (EditText) view.findViewById(R.id.edtxt_pheight);
        edtxt_descrption = (EditText) view.findViewById(R.id.edtxt_descrption);

        txt_pname = (TextView) view.findViewById(R.id.txt_pname);
        basic_infor = (TextView) view.findViewById(R.id.basic_infor);

        rg2 = (RadioGroup) view.findViewById(R.id.p_rg_Condition);
        profile_image= (CircleImageView) view.findViewById(R.id.img_profile);

        reason= PreferenceData.getPatientId(getActivity());
        ed1.setText(Categories_Fragment.p_name);
        txt_pname.setText(Categories_Fragment.p_name);
        edtxt_pmob.setText(Categories_Fragment.p_mob);
        ed2.setText(PreferenceData.getPatientEmail(getActivity()));
        ed3.setText(Categories_Fragment.p_pass);
        ed4.setText(Categories_Fragment.p_age);
        ed5.setText(Categories_Fragment.p_bloodgroup);
        ed6.setText(Categories_Fragment.p_weight);
        ed7.setText(Categories_Fragment.p_height);
        edtxt_descrption.setText(Categories_Fragment.description);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        ed1.setTypeface(tf);
        ed2.setTypeface(tf);
        ed3.setTypeface(tf);
        ed4.setTypeface(tf);
        edtxt_pmob.setTypeface(tf);
        ed5.setTypeface(tf);
        basic_infor.setTypeface(tf);
        ed6.setTypeface(tf);
        ed7.setTypeface(tf);
        edtxt_descrption.setTypeface(tf);

        if (Categories_Fragment.condition.equals("Normal")) {
            rg2.check(R.id.Normal);
            rate2="Normal";
        }
        else if (Categories_Fragment.condition.equals("Critical")){
            rg2.check(R.id.Critical);
            rate2="Critical";
        }
        else {
        }

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                int pos1 = rg2.indexOfChild(view.findViewById(checkedId));
                /*Toast.makeText(getBaseContext(), "Method 1 ID = " + String.valueOf(pos),
                        Toast.LENGTH_SHORT).show();*/
//                dataMovement.timeText.setText(String.valueOf(pos));
//                dataMovement.timeText.setText("You have selected for blocking time for 10 seconds");
//                Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),
//                        Toast.LENGTH_SHORT).show();

                //Method 2 For Getting Index of RadioButton
//                pos1=rgroup.indexOfChild(findViewById(rgroup.getCheckedRadioButtonId()));

//                Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),
//                        Toast.LENGTH_SHORT).show();
                /*Intent intent1=new Intent(TimeSetActivity.this,DataMovement.class);
                intent1.putExtra("timeindex0",pos);
                startActivity(intent1);*/
                switch (pos1) {

                    case 0:
                        rate2 = "Normal";
                        break;
                    case 1:
                        rate2 = "Critical";
                        break;
                    default:
                        break;
                }
            }
        });


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, FILE_SELECT_CODE);
            }
        });


        /*try {

            SharedPreferences sp=getActivity().getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
            String imag_path=sp.getString("profile_pic","");
            if(imag_path.equals("")){
                profile_image.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
            }
            else{
                File f1=new File(imag_path);
                Bitmap bmImg1 = BitmapFactory.decodeFile(f1.toString());
                profile_image.setImageBitmap(bmImg1);
            }
        }
        catch (Exception e){
            Log.d("sunil",e.toString());

            profile_image.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        }*/

        return view;
    }

//    public class CallServices extends AsyncTask<String, String, String> {
//        final String name = ed1.getText().toString().trim();
//        final String email = ed2.getText().toString().trim();
//        final String password = ed3.getText().toString().trim();
//        final String age = ed4.getText().toString().trim();
//        final String weigt = ed6.getText().toString().trim();
//        final String height = ed7.getText().toString().trim();
//        final String descrption = edtxt_descrption.getText().toString().trim();
//        final String blood = ed5.getText().toString().trim();
//        ProgressDialog pd;
//
//        ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
//        String result;
//        String tag;
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//            pd = new ProgressDialog(getActivity());
//
//            pd.setMessage("Working ...");
//            pd.setIndeterminate(false);
//            pd.setCancelable(false);
//            pd.show();
//        }
//
//        @SuppressWarnings("deprecation")
//        @Override
//        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//            namevaluepair.add(new BasicNameValuePair("p_id", reason));
//            namevaluepair.add(new BasicNameValuePair("p_name", name));
//            namevaluepair.add(new BasicNameValuePair("p_login_pass", password));
//            namevaluepair.add(new BasicNameValuePair("p_login_id", email));
//            namevaluepair.add(new BasicNameValuePair("p_age", age));
//            namevaluepair.add(new BasicNameValuePair("p_bloodgroup", blood));
//            namevaluepair.add(new BasicNameValuePair("p_weight", weigt));
//            namevaluepair.add(new BasicNameValuePair("p_height", height));
//            namevaluepair.add(new BasicNameValuePair("p_status", "active"));
//            namevaluepair.add(new BasicNameValuePair("description", descrption));
//            namevaluepair.add(new BasicNameValuePair("conditions", rate2));
//
//            try {
//
//                result = Util.executeHttpPost(params[0], namevaluepair);
//
//                Log.e("result", result.toString());
//
//            } catch (Exception e) {
//
//                e.printStackTrace();
//
//            }
//
//            return result;
//
//
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//
//            if (pd != null) {
//                pd.dismiss();
//            }
//            Toast.makeText(getActivity(), "updated successfully", Toast.LENGTH_LONG).show();
//            Intent returnIntent = new Intent();
//            returnIntent.putExtra("result","okay");
//            getActivity().setResult(Activity.RESULT_OK,returnIntent);
//            getActivity().finish();
//        }
//    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result","okay");
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE) {
            Log.d("sun","on activity result");
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                selectedImagePath = getPath(
                        getActivity(), selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if(selectedImagePath!=null&&selectedImagePath!=""){
                    String image_path_string=selectedImagePath;
                    Log.d("sunil", selectedImagePath);

                    Bitmap bmImg = BitmapFactory.decodeFile(image_path_string);
                    SharedPreferences sp=getActivity().getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("profile_pic",image_path_string);
                    editor.commit();
                    profile_image.setImageBitmap(bmImg);
                }
                else{
                    Toast.makeText(getActivity(),"File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path ="+selectedImagePath);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
}






