package com.emobi.bjaindoc.utls;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sunil on 10-03-2017.
 */

public class ToastClass {
    public static void ShowLongToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public static void ShowShortToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
