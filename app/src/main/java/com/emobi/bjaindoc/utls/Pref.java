package com.emobi.bjaindoc.utls;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sunil on 09-03-2017.
 */

public class Pref {


    private static final String PrefDB="bjaindoc.txt";


    public static void SetStringPref(Context context, String KEY, String Value){
        SharedPreferences sp=context.getSharedPreferences(PrefDB,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(KEY,Value);
        editor.commit();
    }

    public static String GetStringPref(Context context,String KEY,String defValue){
        SharedPreferences sp=context.getSharedPreferences(PrefDB,Context.MODE_PRIVATE);
        return sp.getString(KEY,defValue);
    }

    public static void SetBooleanPref(Context context,String KEY,boolean Value){
        SharedPreferences sp=context.getSharedPreferences(PrefDB,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(KEY,Value);
        editor.commit();
    }

    public static boolean GetBooleanPref(Context context,String KEY,boolean defValue){
        SharedPreferences sp=context.getSharedPreferences(PrefDB,Context.MODE_PRIVATE);
        return sp.getBoolean(KEY,defValue);
    }
    public static void SetIntegerPref(Context context,String KEY,int Value){
        SharedPreferences sp=context.getSharedPreferences(PrefDB,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt(KEY,Value);
        editor.commit();
    }

    public static int GetIntPref(Context context,String KEY,int defValue){
        SharedPreferences sp=context.getSharedPreferences(PrefDB,Context.MODE_PRIVATE);
        return sp.getInt(KEY,defValue);
    }
}
