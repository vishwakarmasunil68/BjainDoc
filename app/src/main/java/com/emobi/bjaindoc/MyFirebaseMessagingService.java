package com.emobi.bjaindoc;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.emobi.bjaindoc.pojo.urgentchatpatient.NewUrgentChatResultPOJO;
import com.emobi.bjaindoc.utls.AppNotificationManager;
import com.emobi.bjaindoc.utls.Database;
import com.emobi.bjaindoc.utls.StringUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by Emobi-Android-002 on 9/19/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "sunil";
    public static String messagebody, Title;
    Database helper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
//        Title = remoteMessage.getNotification().getTitle();
//        //Calling method to generate notification
//        sendNotification(remoteMessage.getNotification().getBody());
        helper=new Database(getApplicationContext());
        try {
            Log.d(TAG, "remote msg:-" + remoteMessage.getData().toString());
            Log.d(TAG, "success:-" + remoteMessage.getData().get("success"));
            Log.d(TAG, "message:-" + remoteMessage.getData().get("result"));
            Log.d(TAG, "type:-" + remoteMessage.getData().get("type"));
            typeCheck(remoteMessage.getData().get("type"), remoteMessage.getData());

        } catch (Exception e) {
            Log.d(TAG, e.toString());
            try {
                Log.d(TAG, "From: " + remoteMessage.getFrom());
                Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            } catch (Exception e1) {
                Log.d(TAG, e1.toString());
            }
        }
    }

    public void typeCheck(String type, Map<String, String> result) {
        Log.d(TAG, "type:-" + type);
//        Log.d(TAG,"result:-"+result);
        switch (type) {
            case StringUtils.CHAT:
                AppNotificationManager.sendChat(getApplicationContext(), type, result);
                break;
            case StringUtils.NEW_URGENT_CHAT:
                storeUrgentChat(result.get("result"));
                updateActivity(getApplicationContext(),StringUtils.DOC_ACCOUNT_URGENT_CHAT,result.get("result"));
                updateActivity(getApplicationContext(),StringUtils.PATIENT_LIST_URGENT_CHAT,result.get("result"));
                AppNotificationManager.sendUrgentNewChat(getApplicationContext(), type, result);
                break;
            case StringUtils.BOOK_APPOINTMENT:
                AppNotificationManager.ConfirmAppointmentNot(getApplicationContext(), type, result);
                break;
            case StringUtils.URGENT_CHAT:
                storeUrgentChat(result.get("result"));
                updateActivity(getApplicationContext(),StringUtils.DOC_ACCOUNT_URGENT_CHAT,result.get("result"));
                updateActivity(getApplicationContext(),StringUtils.PATIENT_LIST_URGENT_CHAT,result.get("result"));
                updateUrgentChatActivity(getApplicationContext(), result.get("result"));
                AppNotificationManager.sendUrgentChatNot(getApplicationContext(), type, result);
                break;
        }
    }

    public void storeUrgentChat(String response){
        try{
            Gson gson=new Gson();
            NewUrgentChatResultPOJO urgentChatResultPOJO=gson.fromJson(response,NewUrgentChatResultPOJO.class);
            helper.insertServerUrgentChat(urgentChatResultPOJO);
        }
        catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
        updateMyActivity(getApplicationContext(), messageBody);
//            if (message.equalsIgnoreCase("Client::" + message)) {
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        Log.d("sunil", "message in fragment:-" + messageBody);
        String[] msgsplt = messageBody.split("::");
        if (msgsplt[0].equals("BJain")) {
            Intent intent = (new Intent(this, View_notes.class));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Log.d(TAG, "Notification Message" + messageBody);
            intent.putExtra("message", messageBody);
//            messagebody=messageBody;
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_arrow)
                    .setContentTitle("BJain Doctor")
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        } else if (msgsplt[0].equalsIgnoreCase("doctor")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Log.d(TAG, "Notification Message" + messageBody);
            intent.putExtra("message", messageBody);
            messagebody = messageBody;
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_arrow)
                    .setContentTitle(Title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        } else if (msgsplt[0].equals("Patient")) {
            Intent intent = new Intent(this, ScrollableTabsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Log.d(TAG, "Notification Message" + messageBody);
            intent.putExtra("message", messageBody);
            messagebody = messageBody;
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_arrow)
                    .setContentTitle(Title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }

    }


    static void updateMyActivity(Context context, String message) {

        Intent intent = new Intent("client");

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }

    static void updateUrgentChatActivity(Context context, String message) {
        Intent intent = new Intent(StringUtils.URGENT_CHAT);
        //put whatever data you want to send, if any
        intent.putExtra("message", message);
        //send broadcast
        context.sendBroadcast(intent);
    }
     void updateActivity(Context context,String client, String message) {
        Intent intent = new Intent(client);
        //put whatever data you want to send, if any
        intent.putExtra("message", message);
        //send broadcast
        context.sendBroadcast(intent);
    }
}