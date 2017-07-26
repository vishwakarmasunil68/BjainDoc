package com.emobi.bjaindoc.activity;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.emobi.bjaindoc.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VideoViewActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnVideoSizeChangedListener {

    private MediaPlayer mp;
    private TextureView tv;
    public static String MY_VIDEO = "https://s3-ap-southeast-1.amazonaws.com/media-production-filmfans/uploads/post/content_video/90536/13675088_319171565081544_1648141638_n.mp4";
    public static String TAG = "TextureViewActivity";
    ImageView close_player;
    public Handler myHandler = new Handler();
    TextView tv_start_time,tv_end_time;
    SeekBar song_seekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        tv = (TextureView) findViewById(R.id.textureView1);

        tv_start_time= (TextView) findViewById(R.id.tv_start_time);
        tv_end_time= (TextView) findViewById(R.id.tv_end_time);
        song_seekbar= (SeekBar) findViewById(R.id.song_seekbar);

        close_player = (ImageView) findViewById(R.id.close_player);


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            MY_VIDEO=bundle.getString("url");
        }
        close_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp!=null){
                    mp.stop();
                }
                finish();
            }
        });
        tv.setSurfaceTextureListener(this);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        myHandler.postDelayed(UpdateSongTime, 1000);
        int finalTime = mediaPlayer.getDuration();
        tv_end_time.setText(String.format("%d : %d ",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Surface s = new Surface(surfaceTexture);

        try {
            mp = new MediaPlayer();
            mp.setDataSource(MY_VIDEO);
            mp.setSurface(s);
            mp.prepare();

            mp.setOnBufferingUpdateListener(this);
            mp.setOnCompletionListener(this);
            mp.setOnPreparedListener(this);
            mp.setOnVideoSizeChangedListener(this);

            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
    public Runnable UpdateSongTime = new Runnable()

    {
        public  void run() {


            int startTime = mp.getCurrentPosition();
            song_seekbar.setMax(mp.getDuration());
            song_seekbar.setProgress(mp.getCurrentPosition());
            // seekHandler.postDelayed(run, 1000);
            myHandler.postDelayed(this, 1000);
            tv_start_time.setText(String.format("%d: %d ",

                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );

        }
    };
}
