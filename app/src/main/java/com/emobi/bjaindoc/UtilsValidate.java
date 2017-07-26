package com.emobi.bjaindoc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Jobswolf utility  class to  define reusable methods
 *
 * @version 1.0
 * @author prabhunathy
 * @since 12/22/15.
 */
public class UtilsValidate {

	public static boolean hideSpinnerKeyboard(Context context) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		return false;
	}
	/**
	 * Get current time
	 * @return
	 */


	public static String getCurrentTime() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}
	public static String getCurrentTimeaccforma() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}

	public static String getCurrentDateTime() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}
	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}

	public static String getDateMonth(){
		SimpleDateFormat df = new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH);
		Calendar c = Calendar.getInstance();
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}

	/**
	 * Get storage directory base path
	 * @return
	 */
	public static String getStorageDirectory(){
		String sdCardPath = "";

		Boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (isSDPresent) {
			sdCardPath = Environment.getExternalStorageDirectory().toString();
		} else {
			sdCardPath = Environment.getRootDirectory().toString();
		}

		return sdCardPath;
	}

	/**
	 * Video path to record a video in device storage
	 *
	 * @return String video file path
	 * @throws IOException
	 */
	private String videoPath() throws IOException {
		String timeStamp = String.valueOf(System.currentTimeMillis());
		String fileName = "VID_" + timeStamp + "_";
		File storageDir = new File(UtilsValidate.getStorageDirectory(), "Capri4Physio");
		if (!storageDir.exists()) {
			storageDir.mkdirs();
		}
		//File file = File.createTempFile(fileName, ".mp4", storageDir);
		//return file;
		fileName = storageDir.getPath() + "/" + fileName + ".mp4";
//		AppLog.e("Jobswolf", "Jobswolf video path :" + fileName);

		return fileName;
	}


	/**
	 * Check internet connection
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context){
		ConnectivityManager cm =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&activeNetwork.isConnectedOrConnecting();
		return isConnected;
	}

	/**
	 * Display alert message dialog
	 * @param context
	 * @param msg
	 */
	public static void showMessage(Context context, String msg){
		AlertDialog.Builder builder= new AlertDialog.Builder(context);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton(android.R.string.ok, null);
		builder.create();
		builder.show();
	}


	/**
	 * Display alert message dialog with error title
	 * @param context
	 * @param titile
	 * @param msg
	 */
	public static void showError(Context context, String titile, String msg){
		try {
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			if (titile != null && !titile.equals(""))
				alert.setTitle(titile);
			alert.setMessage(msg);
			alert.setPositiveButton(android.R.string.ok, null);
			alert.create();
			alert.show();
		}
		catch (Exception e){

		}
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static String getEncodedUrl(String url) {
		String imageUrl = url;
		String filenames[] = imageUrl.split("/");

		String filename = filenames[filenames.length - 1];
		String newfilename = "";
		try {
			newfilename = URLEncoder.encode(filename, "Utf-8");
			newfilename = newfilename.replace("+", "%20");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		imageUrl = imageUrl.replace(filename, newfilename);

		return imageUrl;
	}

	public static int getCurrentHour() {
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		return curHours;
	}

	public static String replaceExtraChar(String str_todecode) {
		String message = "";

		message = str_todecode.replace("%A0", "");

		return message;
	}


}
