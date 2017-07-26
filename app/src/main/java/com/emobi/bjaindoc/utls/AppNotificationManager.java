package com.emobi.bjaindoc.utls;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.emobi.bjaindoc.GetAppointmentByDoctor;
import com.emobi.bjaindoc.R;
import com.emobi.bjaindoc.activity.ChatActivityPat;
import com.emobi.bjaindoc.activity.UrgentChatActivity;
import com.emobi.bjaindoc.pojo.chat.ChatResultPOJO;
import com.emobi.bjaindoc.pojo.urgent.UrgentChatResultPOJO;
import com.emobi.bjaindoc.pojo.urgentchatpatient.NewUrgentChatResultPOJO;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by sunil on 18-03-2017.
 */

public class AppNotificationManager {

    private final static String TAG="AppNotificationManager";


    public static void ConfirmAppointmentNot(Context context,String title,Map<String,String> messagebody){
        String message=messagebody.get("result").toString();
        updateMyActivity(context,message,StringUtils.BOOK_APPOINTMENT);
        try{
            Intent intent = new Intent(context, GetAppointmentByDoctor.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("title",title);
            intent.putExtra("message",message);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_arrow)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            android.app.NotificationManager notificationManager =
                    (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
        catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }


    public static void sendChat(Context context,String title,Map<String,String> messagebody){
        String message=messagebody.get("result").toString();
        Log.d(TAG,"chat result:-"+message);
        updateMyActivity(context,message,StringUtils.CHAT);
        try{
            Gson gson=new Gson();
            ChatResultPOJO chatResultPOJO=gson.fromJson(message,ChatResultPOJO.class);
            Intent intent = new Intent(context, ChatActivityPat.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("type",StringUtils.CHAT);
            intent.putExtra("result",message);
//            intent.putExtra("message",message);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_arrow)
                    .setContentTitle(chatResultPOJO.getChat_title())
                    .setContentText(chatResultPOJO.getChat_msg())
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            android.app.NotificationManager notificationManager =
                    (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
        catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }

    public static void sendUrgentChatNot(Context context,String title,Map<String,String> messagebody){
        String message=messagebody.get("result").toString();
        try{
            Gson gson = new Gson();
            UrgentChatResultPOJO chatResultPOJO = gson.fromJson(message, UrgentChatResultPOJO.class);
            Intent intent = new Intent(context, UrgentChatActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("result",message);
//            intent.putExtra("message",message);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_arrow)
                    .setContentTitle("Urgent "+chatResultPOJO.getU_chat_title())
                    .setContentText(chatResultPOJO.getU_chat_msg())
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            android.app.NotificationManager notificationManager =
                    (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
        catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }


    public static void sendUrgentNewChat(Context context,String title,Map<String,String> messagebody){
        String message=messagebody.get("result").toString();
        updateMyActivity(context,message,StringUtils.NEW_URGENT_CHAT);

        try{
            Gson gson = new Gson();
            NewUrgentChatResultPOJO chatResultPOJO = gson.fromJson(message, NewUrgentChatResultPOJO.class);
            Intent intent = new Intent(context, UrgentChatActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("result",message);
//            intent.putExtra("message",message);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_arrow)
                    .setContentTitle("Urgent "+chatResultPOJO.getU_chat_title())
                    .setContentText(chatResultPOJO.getU_chat_msg())
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            android.app.NotificationManager notificationManager =
                    (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
        catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }


    static void updateMyActivity(Context context, String message,String client) {
        Intent intent = new Intent(client);
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }
}
