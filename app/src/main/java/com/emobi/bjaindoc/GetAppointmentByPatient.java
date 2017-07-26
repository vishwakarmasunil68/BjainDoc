package com.emobi.bjaindoc;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Emobi-Android-002 on 10/20/2016.
 */
public class GetAppointmentByPatient extends AppCompatActivity {

    Button btn_Submit;
    String rate1,rate2,reason;
    ActionBar actionBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
TextView toolbar_title;
    ImageView backarrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appoinment_bypatient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*actionBar=getSupportActionBar();
        SpannableString s = new SpannableString("  Appointments");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(R.drawable.bjainicon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("  Appointments");
        actionBar.setTitle(s);*/
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //.setIcon(R.drawable.activation)
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.addTab(tabLayout.newTab().setText("Past"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundResource(R.color.white);

        btn_Submit=(Button) findViewById(R.id.btn_submit);
        backarrow = (ImageView) findViewById(R.id.backarrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        viewPager = (ViewPager) findViewById(R.id.pager );

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }



        });

        btn_Submit.setVisibility(View.GONE);

        Typeface tf= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
        toolbar_title.setTypeface(tf);



        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        tabLayout.setupWithViewPager(viewPager);
        /*tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#578A48"));*/

        final PagerAdapter1 adapter = new PagerAdapter1
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                /578A48
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#479736"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
