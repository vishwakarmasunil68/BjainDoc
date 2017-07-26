package com.emobi.bjaindoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ScrollableTabsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private int[] titles ={R.drawable.chat4,R.drawable.medicine,R.drawable.prescription
            ,R.drawable.notes,R.drawable.pro,R.drawable.notification,R.drawable.address24};
    CircleImageView img_patient;
    TextView patient_name;
    ActionBar actionBar;
    private ViewPager viewPager;
    public static  String c_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_tabs);

        img_patient = (CircleImageView) findViewById(R.id.img_patient);
        patient_name = (TextView) findViewById(R.id.patient_name);
        String photo = getIntent().getStringExtra("patient_photo");
        Log.e("photo",photo);
        String message = getIntent().getStringExtra("patient_name");
        patient_name.setText(message);

        try {
                Picasso.with(getApplicationContext()).load("http://www.bjain.com/doctor/upload/" + photo).resize(100, 100).into(img_patient);
            }
    catch (Exception e) {
            Log.d("sunil", e.toString());
        }
//
//        Log.d("shubha", "Notification Message" + message);
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//        if (!message.isEmpty()){
//            c_message=message;
//        }
        /*SpannableString s = new SpannableString(getResources().getString(R.string.app_name));
        s.setSpan(new TypefaceSpan(this, "fonts/AlexBrush-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        toolbar.setTitle(s);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#006400"));

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#479736"));

                        /*switch (tab.getPosition()){
                            case 0:
                                tabLayout.getTabAt(0).setIcon(R.drawable.chatg24);
                                tabLayout.getTabAt(1).setIcon(R.drawable.medication);
                                tabLayout.getTabAt(2).setIcon(R.drawable.prescripti);
                                tabLayout.getTabAt(3).setIcon(R.drawable.pres);
                                tabLayout.getTabAt(4).setIcon(R.drawable.pro);
                                break;
                            case 1:
                                tabLayout.getTabAt(0).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(1).setIcon(R.drawable.medic36);
                                tabLayout.getTabAt(2).setIcon(R.drawable.prescripti);
                                tabLayout.getTabAt(3).setIcon(R.drawable.pres);
                                tabLayout.getTabAt(4).setIcon(R.drawable.pro);
                                break;
                            case 2:
                                tabLayout.getTabAt(0).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(1).setIcon(R.drawable.medication);
                                tabLayout.getTabAt(2).setIcon(R.drawable.presc36);
                                tabLayout.getTabAt(3).setIcon(R.drawable.pres);
                                tabLayout.getTabAt(4).setIcon(R.drawable.pro);
                                break;
                            case 3:
                                tabLayout.getTabAt(0).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(1).setIcon(R.drawable.medication);
                                tabLayout.getTabAt(2).setIcon(R.drawable.prescripti);
                                tabLayout.getTabAt(3).setIcon(R.drawable.noteg32);
                                tabLayout.getTabAt(4).setIcon(R.drawable.pro);
                                break;
                            case 4:
                                tabLayout.getTabAt(0).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(1).setIcon(R.drawable.medication);
                                tabLayout.getTabAt(2).setIcon(R.drawable.prescripti);
                                tabLayout.getTabAt(3).setIcon(R.drawable.pres);
                                tabLayout.getTabAt(4).setIcon(R.drawable.proff);
                                break;
                            default:
                                break;

                        }*/

                        super.onTabSelected(tab);
//                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);

//                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_OUT);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
//                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
//                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_OUT);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

        setupTabIcons();
        /*if (!actionBar.isShowing()){
            Log.d("shubham","shubham");
        }*/
    }


    @Override
    public void onResume() {
        super.onResume();
//        getApplicationContext().unregisterReceiver(mMessageReceiver);
//        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter("client"));
    }

    @Override
    public void onPause() {
        super.onPause();
//        getApplicationContext().unregisterReceiver(mMessageReceiver);

    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
//            if (message.equalsIgnoreCase("Client::" + message)) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            Log.d("sunil", "message from activity:-" + message);
            /*LinearLayout ll=new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity= Gravity.LEFT;
            ll.setLayoutParams(params);
            chat_layout.addView(ll);
            ll.setBackground( getResources().getDrawable(R.drawable.balloon_incoming_normal));

            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(params);
            params.setMargins(20,30,20,0);
            tv.setGravity(Gravity.RIGHT);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(Color.BLACK);
            tv.setText(message);
            ll.addView(tv);
            et.setText("");*/
                /*TextView tv = new TextView(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(params);
            params.gravity=Gravity.LEFT;
            params.setLayoutDirection(Gravity.LEFT);
                tv.setGravity(Gravity.RIGHT);
//            tv.setLayoutDirection(Gravity.RIGHT);
             tv.setTextColor(Color.BLACK);
                tv.setText("Patient:-" + message);*/
//                chat_layout.addView(tv);
//            chat_msg.append("Doctor: "+message+"\n");
            //do other stuff here
        }
//        }
    };
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        /*adapter.addFrag(new "" (), "Chat");
        adapter.addFrag(new Medication (), "Medication");
        adapter.addFrag(new Prescription(), "Prescription");
        adapter.addFrag(new DoctorNotes (), "Notes");
        adapter.addFrag(new Medication(), "Edit");*/
        /*adapter.addFrag(new SixFragment(), "SIX");
        adapter.addFrag(new SevenFragment(), "SEVEN");
        adapter.addFrag(new EightFragment(), "EIGHT");
        adapter.addFrag(new NineFragment(), "NINE");
        adapter.addFrag(new TenFragment(), "TEN");*/
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(titles[0]);
        tabLayout.getTabAt(1).setIcon(titles[1]);
        tabLayout.getTabAt(2).setIcon(titles[2]);
        tabLayout.getTabAt(3).setIcon(titles[3]);
        tabLayout.getTabAt(4).setIcon(titles[4]);
//        tabLayout.getTabAt(5).setIcon(titles[5]);
//        tabLayout.getTabAt(6).setIcon(titles[6]);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
//            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Log.e("clik", "action bar clicked");
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
