package com.emobi.bjaindoc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.whatsapp_profile.MainActivityDoctorProfile;
import com.emobi.bjaindoc.utls.ToastClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Emobi-Android-002 on 8/16/2016.
 */
public class UpdateDoc_Dcotor extends Fragment implements DatePickerDialog.OnDateSetListener{
    public static String reg_id, reg_name, reg_email, reg_pass, reg_cpass, dob, reg_department, reg_clinic_address, reg_designation, reg_specialist, reg_degree;
    public static EditText name,edtxt_pmob,edtxt_d_dob,
            pass, cpass, dpmt, addr,  deisgnation, degree, et_degree,et_speciality,et_department,et_address;
    public static EditText email_id,avail;
    public static final String REGISTER_URL = "http://www.bjain.com/doctor/registration.php";
    TextView txt_pname;

    Button btn_Submit;
    RadioGroup rg1,rg2;
    public static String rate1,rate2,reason;
    public static String selectedImagePath;
    public static TextView ed2;
    CircleImageView profile_image;
    private static final int FILE_SELECT_CODE = 0;
    String date;
    Calendar myCalendar;
    ImageView iv_dob;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       final  View view= inflater.inflate(R.layout.updateinfodoc_doctor, container, false);
//        setContentView(R.layout.updateinfopatient_patient);
        iv_dob = (ImageView) view.findViewById(R.id.iv_dob);
        edtxt_d_dob = (EditText) view.findViewById(R.id.edtxt_d_dob);
        name = (EditText) view.findViewById(R.id.edtxt_d_name);
        email_id = (EditText) view.findViewById(R.id.edtxt_d_email);
        edtxt_pmob = (EditText) view.findViewById(R.id.edtxt_pmob);
        pass = (EditText) view.findViewById(R.id.edtxt_d_password);
        et_degree= (EditText) view.findViewById(R.id.et_degree);
        et_speciality= (EditText) view.findViewById(R.id.et_speciality);
        et_department= (EditText) view.findViewById(R.id.et_department);
        et_address= (EditText) view.findViewById(R.id.et_address);

        txt_pname = (TextView) view.findViewById(R.id.txt_pname);

        profile_image= (CircleImageView) view.findViewById(R.id.img_profile);

        reason= PreferenceData.getPatientId(getActivity());
        name.setText(MainActivityDoctorProfile.reg_name);
        edtxt_d_dob.setText(MainActivityDoctorProfile.reg_id);
        email_id.setText(MainActivityDoctorProfile.reg_email);
        edtxt_pmob.setText(MainActivityDoctorProfile.reg_mob);
        et_department.setText(MainActivityDoctorProfile.reg_department);
        et_speciality.setText(MainActivityDoctorProfile.reg_specialist);
        et_address.setText(MainActivityDoctorProfile.reg_clinic_address);
        et_degree.setText(MainActivityDoctorProfile.reg_degree);
        pass.setText(PreferenceData.getPass(getActivity().getApplicationContext()));

        Typeface tf1= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
        name.setTypeface(tf1);
        email_id.setTypeface(tf1);
        edtxt_pmob.setTypeface(tf1);
        pass.setTypeface(tf1);
        edtxt_d_dob.setTypeface(tf1);

//        ed4.setText(Categories_Fragment.p_age);
//        ed5.setText(Categories_Fragment.p_bloodgroup);
//        ed6.setText(Categories_Fragment.p_weight);
//        ed7.setText(Categories_Fragment.p_height);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, FILE_SELECT_CODE);
            }
        });
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }



        };
//        edtxt_d_dob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                onDateSet();
//                new DatePickerDialog(getActivity(), date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
        iv_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onDateSet();
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
    private void updateLabel() {

//        String myFormat = "MM/dd/yy"; //In which you need put here
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date d = new Date();
        try {
            String now_date = sdf.format(d);
            String clicked_date = sdf.format(myCalendar.getTime());
            Date clicked = sdf.parse(clicked_date);
            if (d.before(clicked)) {
                ToastClass.ShowLongToast(getActivity().getApplicationContext(), "Invalid date");
            } else {
                if (now_date.equals(clicked_date)) {
                    ToastClass.ShowShortToast(getActivity().getApplicationContext(), "You cannot set today's date");
                } else {
                    edtxt_d_dob.setText(sdf.format(myCalendar.getTime()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            edtxt_d_dob.setText(sdf.format(myCalendar.getTime()));
        }
//        edtxt_d_dob.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        date = year+"/"+dayOfMonth+"/"+(monthOfYear+1);
        edtxt_d_dob.setText(date);
    }
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






