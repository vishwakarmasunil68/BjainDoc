package com.emobi.bjaindoc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by emobi5 on 6/20/2016.
 */
public class Play extends Activity {
    VideoView video_player_view;
    DisplayMetrics dm;
    MediaController media_Controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        video_player_view = (VideoView) findViewById(R.id.video_player_view);

        getInit();
    }
    public void getInit() {
        media_Controller = new MediaController(this);
        /*dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        video_player_view.setMinimumWidth(width);
        video_player_view.setMinimumHeight(height);*/
        video_player_view.setMediaController(media_Controller);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
        Uri video = Uri.parse(url);
        Log.e("videopath",video.toString());
        video_player_view.setVideoURI(video);
        video_player_view.start();
    }

    @Override
    public void onBackPressed() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if(appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                    super.onBackPressed();
                }
            }
        }
            }
                public static boolean deleteDir(File dir) {
                    if (dir != null && dir.isDirectory()) {
                        String[] children = dir.list();
                        for (int i = 0; i < children.length; i++) {
                            boolean success = deleteDir(new File(dir, children[i]));
                            if (!success) {
                                return false;
                            }
                        }
                    }


                    return dir.delete();
                }
}
