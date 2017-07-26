package com.emobi.bjaindoc.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.emobi.bjaindoc.MainActivity;
import com.emobi.bjaindoc.R;

public class TestDrag extends AppCompatActivity {

    private GestureDetector gesturedetector = null;

    private Intent i;

    private FrameLayout flContainer;
    private LinearLayout ivLayer1,ivLayer2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_drag);

        flContainer = (FrameLayout) findViewById(R.id.flContainer);
        ivLayer1 = (LinearLayout)findViewById(R.id.layTopUp);
        ivLayer2 = (LinearLayout)findViewById(R.id.layScratchCard);


        gesturedetector = new GestureDetector(new MyGestureListener());

        flContainer.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                gesturedetector.onTouchEvent(event);

                return true;

            }

        });

    }

    public boolean dispatchTouchEvent(MotionEvent ev) {

        super.dispatchTouchEvent(ev);

        return gesturedetector.onTouchEvent(ev);

    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_MIN_DISTANCE = 20;

        private static final int SWIPE_MAX_OFF_PATH = 100;

        private static final int SWIPE_THRESHOLD_VELOCITY = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,

                               float velocityY) {

            float dX = e2.getX() - e1.getX();

            float dY = e1.getY() - e2.getY();

            if (Math.abs(dY) < SWIPE_MAX_OFF_PATH &&

                    Math.abs(velocityX) >= SWIPE_THRESHOLD_VELOCITY &&

                    Math.abs(dX) >= SWIPE_MIN_DISTANCE) {

                if (dX > 0) {

                    Toast.makeText(getApplicationContext(), "Right Swipe",
                            Toast.LENGTH_SHORT).show();
                    //Now Set your animation

                    if(ivLayer2.getVisibility()==View.GONE)
                    {
                        Animation fadeInAnimation = AnimationUtils.loadAnimation(TestDrag.this, R.anim.slide_right_in);
                        ivLayer2.startAnimation(fadeInAnimation);
                        ivLayer2.setVisibility(View.VISIBLE);
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Left Swipe",
                            Toast.LENGTH_SHORT).show();

                    if(ivLayer2.getVisibility()==View.VISIBLE)
                    {
                        Animation fadeInAnimation = AnimationUtils.loadAnimation(TestDrag.this, R.anim.slide_left_out);
                        ivLayer2.startAnimation(fadeInAnimation);
                        ivLayer2.setVisibility(View.GONE);
                    }

                }

                return true;

            } else if (Math.abs(dX) < SWIPE_MAX_OFF_PATH &&

                    Math.abs(velocityY) >= SWIPE_THRESHOLD_VELOCITY &&

                    Math.abs(dY) >= SWIPE_MIN_DISTANCE) {

                if (dY > 0) {

                    Toast.makeText(getApplicationContext(), "Up Swipe",
                            Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Down Swipe",
                            Toast.LENGTH_SHORT).show();
                }

                return true;

            }

            return false;

        }

    }

}