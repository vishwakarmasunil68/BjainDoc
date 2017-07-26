package com.emobi.bjaindoc;

/**
 * Copyright (C) 2015 Mustafa Ozcan
 * Created on 06 May 2015 (www.mustafaozcan.net)
 * *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * *
 * http://www.apache.org/licenses/LICENSE-2.0
 * *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import database.PreferenceData;
import de.hdodenhof.circleimageview.CircleImageView;
import patient_side.BroadCast_view;
import patient_side.BroadNote_view;
import patient_side.BroadNoteview1;
import patient_side.Search_Doctor;

public class MainActivity extends AppCompatActivity  {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private int[] titles ={R.drawable.blog,R.drawable.pro
            ,R.drawable.chat4,R.drawable.medicine,R.drawable.prescription,R.drawable.docss,R.drawable.settings};
//    private String titles[] = new String[]{"Home","Health Feed","Profile","Chat","Prescription","Medication","Settings"};
    private NavigationView mNavigationView;
    private int mCurrentSelectedPosition;
    public static TextView tv,txt_email;



    LinearLayout navigation_header_patient;
//    SlidingTabLayout slidingTabLayout;
    ViewPager vpPager;
    ContentFragmentAdapter adapterViewPager;
   public static CircleImageView img;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpNavigationDrawer();

        // Initial tab count
//        setTabs(4);
//        mNavigationView.setCheckedItem(R.id.navigation_item_6);

    }

    private void setUpNavigationDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        try {
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setLogo(R.drawable.bjainicon);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setSubtitle(getString(R.string.subtitle));
            actionBar.setDisplayShowTitleEnabled(true);
        } catch (Exception ignored) {
        }
        vpPager = (ViewPager) findViewById(R.id.vpPager);
        setupViewPager(vpPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vpPager);
        setupTabIcons();

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(vpPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#479736"));

                        /*switch (tab.getPosition()){
                            case 0:
                                tabLayout.getTabAt(0).setIcon(R.drawable.homee);
                                tabLayout.getTabAt(1).setIcon(R.drawable.blo);
                                tabLayout.getTabAt(2).setIcon(R.drawable.pro);
                                tabLayout.getTabAt(3).setIcon(R.drawable.blo);
                                tabLayout.getTabAt(4).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(5).setIcon(R.drawable.medication);
                                tabLayout.getTabAt(6).setIcon(R.drawable.set);
                                break;
                            case 1:
                                tabLayout.getTabAt(1).setIcon(R.drawable.blogg);
                                tabLayout.getTabAt(0).setIcon(R.drawable.hom);
                                tabLayout.getTabAt(2).setIcon(R.drawable.pro);
                                tabLayout.getTabAt(3).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(4).setIcon(R.drawable.medication);
                                tabLayout.getTabAt(5).setIcon(R.drawable.prescripti);
                                tabLayout.getTabAt(6).setIcon(R.drawable.set);
                                break;
                            case 2:
                                tabLayout.getTabAt(2).setIcon(R.drawable.proff);
                                tabLayout.getTabAt(0).setIcon(R.drawable.hom);
                                tabLayout.getTabAt(1).setIcon(R.drawable.blo);
                                tabLayout.getTabAt(3).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(4).setIcon(R.drawable.medication);
                                tabLayout.getTabAt(5).setIcon(R.drawable.prescripti);
                                tabLayout.getTabAt(6).setIcon(R.drawable.set);
                                break;
                            case 3:
                                tabLayout.getTabAt(3).setIcon(R.drawable.chatg24);
                                tabLayout.getTabAt(0).setIcon(R.drawable.hom);
                                tabLayout.getTabAt(1).setIcon(R.drawable.blo);
                                tabLayout.getTabAt(2).setIcon(R.drawable.pro);
                                tabLayout.getTabAt(4).setIcon(R.drawable.medication);
                                tabLayout.getTabAt(5).setIcon(R.drawable.prescripti);
                                tabLayout.getTabAt(6).setIcon(R.drawable.set);
                                break;
                            case 4:
                                tabLayout.getTabAt(4).setIcon(R.drawable.medic36);
                                tabLayout.getTabAt(0).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(1).setIcon(R.drawable.blo);
                                tabLayout.getTabAt(2).setIcon(R.drawable.pro);
                                tabLayout.getTabAt(3).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(5).setIcon(R.drawable.prescripti);
                                tabLayout.getTabAt(6).setIcon(R.drawable.set);
                                break;
                            case 5:
                                tabLayout.getTabAt(5).setIcon(R.drawable.presc36);
                                tabLayout.getTabAt(0).setIcon(R.drawable.hom);
                                tabLayout.getTabAt(1).setIcon(R.drawable.blo);
                                tabLayout.getTabAt(2).setIcon(R.drawable.pro);
                                tabLayout.getTabAt(3).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(4).setIcon(R.drawable.medication);
                                tabLayout.getTabAt(6).setIcon(R.drawable.set);
                                break;
                            case 6:
                                tabLayout.getTabAt(6).setIcon(R.drawable.settt);
                                tabLayout.getTabAt(0).setIcon(R.drawable.hom);
                                tabLayout.getTabAt(1).setIcon(R.drawable.blo);
                                tabLayout.getTabAt(2).setIcon(R.drawable.pro);
                                tabLayout.getTabAt(3).setIcon(R.drawable.cha);
                                tabLayout.getTabAt(4).setIcon(R.drawable.medication);
                                tabLayout.getTabAt(5).setIcon(R.drawable.prescripti);
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


//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerView = mNavigationView.inflateHeaderView(R.layout.drawer_header);
//        View view=mNavigationView.inflateHeaderView(R.layout.drawer_header);
        img= (CircleImageView) headerView.findViewById(R.id.profilepic);

//        overrideFonts(getApplicationContext(),mNavigationView);
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        navigation_header_patient= (LinearLayout) headerView.findViewById(R.id.navigation_header_patient);
        /*mNavigationView.setVisibility(View.INVISIBLE);
        mDrawerLayout.setVisibility(View.INVISIBLE);
        headerView.setVisibility(View.INVISIBLE);*/
        Menu m = mNavigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        tv= (TextView) headerView.findViewById(R.id.profilename);
        txt_email= (TextView) headerView.findViewById(R.id.txt_email);

        Typeface tf1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
        tv.setTypeface(tf1);
        txt_email.setTypeface(tf1);

        txt_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpPager.setCurrentItem(2);
//                if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
//                    mDrawerLayout.closeDrawer(mNavigationView);
//                }
            }
        });

        try {
            tv.setText(PreferenceData.getPatientName(getApplicationContext()));
        }
        catch (Exception e){
        }

        /*try {
//            settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
            String bitmap = "http://www.bjain.com/doctor/upload/" + PreferenceData.getPatientPhoto(getApplicationContext());

            Log.e("stringToBitmap", bitmap.toString());
            getBitmapFromURL(bitmap);
        }
        catch (Exception e){
            img.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_person));
        }*/
        try {
            if (PreferenceData.getPatientPhoto(getApplicationContext()).length() > 0) {
                String bitmap = "http://www.bjain.com/doctor/upload/" + PreferenceData.getPatientPhoto(getApplicationContext());

                Log.e("stringToBitmap", bitmap.toString());
                Picasso.with(getApplicationContext()).load(bitmap).resize(100, 100).into(img);
            }
        }
        catch (Exception e){

        }
//        if (FacebookActivity.prFmofilepicimage!=null){
//        try {
//            File f = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "profile.png");
//            Log.d("sunil","file:-"+f.exists());
//            if(f.exists()) {
//                Bitmap bmImg = BitmapFactory.decodeFile(f.toString());
//                img.setImageBitmap(bmImg);
//            }
//            else{
//                SharedPreferences sp=getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
//                String imag_path=sp.getString("profile_pic","");
//                if(imag_path.equals("")){
//                    img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
//                }
//                else{
//                    File f1=new File(imag_path);
//                    Bitmap bmImg1 = BitmapFactory.decodeFile(f1.toString());
//                    img.setImageBitmap(bmImg1);
//                }
//            }
//        }
//        catch (Exception e){
//            Log.d("sunil",e.toString());
//            SharedPreferences sp=getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
//            String imag_path=sp.getString("profile_pic","");
//            if(imag_path.equals("")){
//                img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
//            }
//            else{
//                File f1=new File(imag_path);
//                Bitmap bmImg1 = BitmapFactory.decodeFile(f1.toString());
//                img.setImageBitmap(bmImg1);
//            }
//        }
       /* }
        else {
            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        }*/
        /*if (LoginActivity.textname!=null){
            tv.setText(LoginActivity.textname);
        }
        else {
            tv.setText("Profile UserName");
        }*/
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                slidingTabLayout.setTabSelected(0);
//                Toast.makeText(getApplicationContext(),"context",Toast.LENGTH_SHORT).show();
                vpPager.setCurrentItem(0);
                /*if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
                    mDrawerLayout.closeDrawer(mNavigationView);
                } else {
                    mDrawerLayout.openDrawer(mNavigationView);
                }*/
            }
        });
        navigation_header_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                slidingTabLayout.setTabSelected(2);
//                Toast.makeText(getApplicationContext(),"context",Toast.LENGTH_SHORT).show();
                vpPager.setCurrentItem(2);
                /*if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
                    mDrawerLayout.closeDrawer(mNavigationView);
                } else {
                    mDrawerLayout.openDrawer(mNavigationView);
                }*/
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    /*case R.id.navigation_item_1:
                        mCurrentSelectedPosition = 0;
                        break;
                    case R.id.navigation_item_2:
                        mCurrentSelectedPosition = 1;
//                        startActivity(new Intent(MainActivity.this,AddWithActPatient.class));
                        break;
                    case R.id.navigation_item_3:
                        mCurrentSelectedPosition = 2;
                        break;
                    case R.id.navigation_item_4:
                        mCurrentSelectedPosition = 3;
                        break;
                    case R.id.navigation_item_5:
                        mCurrentSelectedPosition = 4;
                        break;*/
                    case R.id.navigation_item_5:
                        mCurrentSelectedPosition = 4;
                        startActivity(new Intent(MainActivity.this, Search_Doctor.class));
                        break;
                    case R.id.navigation_item_3:
                        mCurrentSelectedPosition = 2;
                        Intent i=new Intent(MainActivity.this, BroadCast_view.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_item_4:
                        mCurrentSelectedPosition = 3;
                        startActivity(new Intent(MainActivity.this, BroadNote_view.class));
                        break;

                    case R.id.navigation_item_10:
                        mCurrentSelectedPosition = 5;
                        startActivity(new Intent(MainActivity.this, DoctorProfile.class));
                        break;
                    case R.id.navigation_item_7:
                        mCurrentSelectedPosition = 2;
                        startActivity(new Intent(MainActivity.this, GetAppointmentByPatient.class));
                        break;
                    case R.id.navigation_item_6:
                        PreferenceData.setPatientLogin(getApplicationContext(),false);
                        Intent intent=new Intent(MainActivity.this,SIgnInOption.class);
                        Toast.makeText(getApplicationContext(),"logout successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finishAffinity();
                        mCurrentSelectedPosition = 5;
                        break;
                }

//                setTabs(mCurrentSelectedPosition + 1);
//                mDrawerLayout.closeDrawer(mNavigationView);
                return true;
            }
        });

        /*mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle(getString(R.string.drawer_opened));
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);*/

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

//    public void setTabs(int count) {
//
////        adapterViewPager = new ContentFragmentAdapter(getSupportFragmentManager(), this, titles);
////        vpPager.setAdapter(adapterViewPager);
//
////        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
////        slidingTabLayout.setTextColor(getResources().getColor(R.color.tab_text_color));
////        slidingTabLayout.setTextColorSelected(getResources().getColor(R.color.tab_text_color_selected));
////        slidingTabLayout.setDistributeEvenly();
////        slidingTabLayout.setViewPager(vpPager);
////        slidingTabLayout.setTabSelected(0);
////
////        // Change indicator color
////        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
////            @Override
////            public int getIndicatorColor(int position) {
////                return getResources().getColor(R.color.tab_indicator);
////            }
////        });
//
//    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();


        /*if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawer(mNavigationView);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
    }


//    public static void overrideFonts(final Context context, final View v) {
//        Typeface typeface=Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.menu3));
//        try {
//            if (v instanceof ViewGroup) {
//                ViewGroup vg = (ViewGroup) v;
//                for (int i = 0; i < vg.getChildCount(); i++) {
//                    View child = vg.getChildAt(i);
//                    overrideFonts(context, child);
//                }
//            } else if (v instanceof TextView) {
//                ((TextView) v).setTypeface(typeface);
//            }
//        } catch (Exception e) {
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);
        int mNotifCount = 0;
        View count = menu.findItem(R.id.action_notification).getActionView();
    //        Home_Fragment.find.setText(String.valueOf(mNotifCount));

        if (searchItem != null) {
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

            // use this method for search process
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // use this method when query submitted
                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // use this method for auto complete search process
                    return false;
                }
            });

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item != null && item.getItemId() == android.R.id.home) {
            /*if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
                mDrawerLayout.closeDrawer(mNavigationView);
            } else {
                mDrawerLayout.openDrawer(mNavigationView);
            }*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void refreshImage() {
//        try {
//
//            SharedPreferences sp=getSharedPreferences("doctor.txt", Context.MODE_PRIVATE);
//            String imag_path=sp.getString("profile_pic","");
//            if(imag_path.equals("")){
//                img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
//            }
//            else{
//                File f1=new File(imag_path);
//                Bitmap bmImg1 = BitmapFactory.decodeFile(f1.toString());
//                img.setImageBitmap(bmImg1);
//            }
//        }
//        catch (Exception e){
//            Log.d("sunil",e.toString());
//
//            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
//        }
//    }

    private Bitmap getBitmapFromURL(String imageUrl) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            // Bitmap myBitmap = BitmapFactory.decodeStream(input);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            Bitmap b = Utils.decodeSampledBitmapFromStream(input, width, height);
            Log.e("bitmap",b.toString());
            img.setImageBitmap(b);
            return b;


        } catch (IOException e) {
            Log.e("bitmap",e.toString());
            e.printStackTrace();
            return null;
        }
    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new Home_Fragment(), "ONE");
        adapter.addFragment(new Health_Feed_Fragment(), "TWO");
        adapter.addFragment(new Categories_Fragment(), "THREE");
        adapter.addFragment(new ChatFragment(), "THREE");
        adapter.addFragment(new Favourite_Fragment(), "THREE");
        adapter.addFragment(new Search_Fragment(), "THREE");
        adapter.addFragment(new BroadNoteview1(), "THREE");
        adapter.addFragment(new Settings_Fragment(), "THREE");

        viewPager.setAdapter(adapter);
    }
    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(titles[0]);
        tabLayout.getTabAt(0).setIcon(titles[0]);
        tabLayout.getTabAt(1).setIcon(titles[1]);
        tabLayout.getTabAt(2).setIcon(titles[2]);
        tabLayout.getTabAt(3).setIcon(titles[3]);
        tabLayout.getTabAt(4).setIcon(titles[4]);
        tabLayout.getTabAt(5).setIcon(titles[5]);
        tabLayout.getTabAt(6).setIcon(titles[6]);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            /*tabLayout.getTabAt(0).setIcon(R.drawable.home12);
            tabLayout.getTabAt(1).setIcon(R.drawable.home12);
            tabLayout.getTabAt(2).setIcon(R.drawable.home12);
            tabLayout.getTabAt(3).setIcon(R.drawable.home12);
            tabLayout.getTabAt(4).setIcon(R.drawable.home12);
            tabLayout.getTabAt(5).setIcon(R.drawable.home12);
            tabLayout.getTabAt(6).setIcon(R.drawable.home12);*/
            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
