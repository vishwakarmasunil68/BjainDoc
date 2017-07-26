package com.emobi.bjaindoc;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import database.PreferenceData;

/**
 * Created by Emobi-Android-002 on 9/19/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
Context ctx;
    private static final String TAG = "shubham";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        PreferenceData.setDevice_Token(getApplicationContext(),refreshedToken);
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}