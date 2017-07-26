package com.emobi.bjaindoc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static Bitmap decodeSampledBitmapFromStream(InputStream inputStream,
                                                       int reqWidth, int reqHeight) {
         Log.i("reqWidth&reqHeight", "reqWidth"+reqWidth+" reqHeight"+reqHeight);
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
     
        

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return  BitmapFactory.decodeStream(inputStream,null,options);
    }
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and width
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);

        // Choose the smallest ratio as inSampleSize value, this will guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
}
	
	public static String getISOCountryCode(Activity activity) {
		String countryISOCode = null;
		 TelephonyManager teleMgr = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
	     if (teleMgr != null){
	        countryISOCode = teleMgr.getSimCountryIso();
	        if(countryISOCode == null || countryISOCode.equals("")) {
	        	return "ALC";
	        }else {
	        	return countryISOCode;
	        }
	        
	     }
	     
		return countryISOCode;
		
	}
}