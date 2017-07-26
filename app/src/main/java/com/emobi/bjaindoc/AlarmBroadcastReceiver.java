package com.emobi.bjaindoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by emobi5 on 4/26/2016.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        /*ComponentName receiver = new ComponentName(context, PhoneCallReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        ComponentName receiversms = new ComponentName(context, SMSReceiver.class);
        PackageManager pmsms = context.getPackageManager();

        pmsms.setComponentEnabledSetting(receiversms,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);*/

        // Extract data included in the Intent
        String message = intent.getStringExtra("message");
//            if (message.equalsIgnoreCase("Client::" + message)) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        Log.d("sunil", "message in fragment:-" + message);
        String[] msgsplt=message.split("::");
        if (msgsplt[0].equals("BJain")){
            context.startActivity(new Intent(context,View_notes.class));
        }
        else {
            context.startActivity(new Intent(context,View_notes.class));
        }

    }
}
