package database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Emobi-Android-002 on 9/15/2016.
 */
public class PreferenceData {



    public static void setDevice_Token(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked_1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("reg_devicetoken",value);
        editor.commit();
    }
    public static String getDevice_Token(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked_1", Context.MODE_PRIVATE);
        return sp.getString("reg_devicetoken","");
    }

    public static void setName(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("reg_name",value);
        editor.commit();
    }
    public static String getName(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("reg_name","");
    }
    public static void setId(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("reg_id",value);
        editor.commit();
    }
    public static String getId(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("reg_id","");
    }

    public static void setEmail(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("reg_email",value);
        editor.commit();
    }
    public static String getEmail(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("reg_email","");
    }

    public static void setPass(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("reg_pass",value);
        editor.commit();
    }
    public static String getPass(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("reg_pass","");
    }

    public static void setPhoto(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("photo",value);
        editor.commit();
    }
    public static String getPhoto(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("photo","");
    }
    public static void setDoctorSpecialist(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doc_specialist",value);
        editor.commit();
    }
    public static String getSpecialist(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doc_specialist","");
    }


    public static void setDoctorNumber(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doc_number",value);
        editor.commit();
    }
    public static String getDoctorNumber(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doc_number","");
    }

    public static void setDoctorclinic_address(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doc_clinic_address",value);
        editor.commit();
    }
    public static String getDoctorclinic_address(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doc_clinic_address","");
    }

    public static void setDoctorDesignation(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doc_designation",value);
        editor.commit();
    }
    public static String getDoctorDesignation(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doc_designation","");
    }
    public static void setDoctorDegree(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doc_degree",value);
        editor.commit();
    }
    public static String getDoctorDegree(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doc_degree","");
    }

    public static void setDoctorEmail(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doc_email",value);
        editor.commit();
    }
    public static String getDoctorEmail(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doc_email","");
    }



    public static void setDoctorName(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doc_name",value);
        editor.commit();
    }
    public static String getDoctorName(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doc_name","");
    }


    public static void setDoctorDepartment(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doc_department",value);
        editor.commit();
    }
    public static String getDoctorDepartment(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doc_department","");
    }

    public static void setDoctorPhotoUrl(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doc_photo_url",value);
        editor.commit();
    }
    public static String getDoctorPhotoUrl(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doc_photo_url","");
    }

    public static void setDoctorDob(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doc_dob",value);
        editor.commit();
    }
    public static String getDoctorDob(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doc_dob","");
    }
    public static void setPatientLogin(Context context, boolean value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("patient_login",value);
        editor.commit();
    }
    public static Boolean getPatientLogin(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getBoolean("patient_login",false);
    }

    public static void setPatientName(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("patient_nme",value);
        editor.commit();
    }
    public static String getPatientName(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("patient_nme","");
    }

    public static void setPatientID(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("patient_id",value);
        editor.commit();
    }
    public static String getPatientId(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("patient_id","");
    }

    public static void setPatientEmail(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("patient_email",value);
        editor.commit();
    }
    public static String getPatientEmail(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("patient_email","");
    }

    public static void setPatientPassword(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("patient_password",value);
        editor.commit();
    }
    public static String getPatientPassword(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("patient_password","");
    }

    public static void setPatientNumber(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("patient_number",value);
        editor.commit();
    }
    public static String getPatientNumber(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("patient_number","");
    }
    public static void setchatDocid(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("doctor_chat_id",value);
        editor.commit();
    }
    public static String getchatDocid(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("doctor_chat_id","");
    }

    public static void setDoctor_Token(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("device_token",value);
        editor.commit();
    }
    public static String getDoctor_Token(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("device_token","");
    }


    public static void setPatientPhoto(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("patient_photo",value);
        editor.commit();
    }
    public static String getPatientPhoto(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("patient_photo","");
    }

    public static void setPatientStatus(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("Patient_Status",value);
        editor.commit();
    }
    public static String getPatientStatus(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("Patient_Status","");
    }

    public static void setPatient_Device_Token(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("reg_Patient_devicetoken",value);
        editor.commit();
    }
    public static String getPatient_Device_Token(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("reg_Patient_devicetoken","");
    }

    public static void setchatPatient_id(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("reg_chatPatient_id",value);
        editor.commit();
    }
    public static String getchatPatient_id(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("reg_chatPatient_id","");
    }

    public static void setlastchatdate(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("lastchatdate",value);
        editor.commit();
    }
    public static String getlastchatdate(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("lastchatdate","");
    }

    public static void setlastchattime(Context context, String value){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("lastchattime",value);
        editor.commit();
    }
    public static String getlastchattime(Context context){
        SharedPreferences sp=context.getSharedPreferences("locked", Context.MODE_PRIVATE);
        return sp.getString("lastchattime","");
    }

}
