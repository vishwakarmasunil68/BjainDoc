package com.emobi.bjaindoc;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Emobi-Android-002 on 9/2/2016.
 */
public class DoctorAppointment extends AppCompatActivity {

    TextView tv,date;
    ImageView right,left;
    ViewPager pager;
    int formattedDate;
    public static String appointment_date;
    ActionBar actionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorinfo);
        Calendar c = Calendar.getInstance();
        formattedDate =(c.get(Calendar.DAY_OF_WEEK));
        actionBar=getSupportActionBar();

        SpannableString s = new SpannableString("Book Appointment");
        s.setSpan(new TypefaceSpan(this, "fonts/Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setLogo(R.drawable.bjainicon);
        pager= (ViewPager) findViewById(R.id.viewPager);
        date= (TextView) findViewById(R.id.date);
        tv= (TextView) findViewById(R.id.tv);
        left= (ImageView) findViewById(R.id.left);
        right= (ImageView) findViewById(R.id.right);

        Typeface tf1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
        tv.setTypeface(tf1);
        date.setTypeface(tf1);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pager.setCurrentItem(position+1);
//                Log.e("pager",pager.getCurrentItem()+"");
                if(pager.getCurrentItem()!=0) {
                    pager.setCurrentItem(pager.getCurrentItem() - 1);
                }

            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem()!=6) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                }

            }
        });



        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));



        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {

switch (position){

    case 0:

        int day=formattedDate;
        if((formattedDate)>7){
            day=(formattedDate)%7;
        }
        else{
            day=formattedDate;
        }
        Log.e("day","day:-"+day+"");
        tv.setText(UtilsgetCurrentDate.getDay(day)+","+"\t"+ UtilsgetCurrentDate.getCurrentDate());
//        if (formattedDate==2) {
            date.setText(UtilsgetCurrentDate.getCurrentDate());
            appointment_date=date.getText().toString();
//        }
        break;
    case 1:
        int day1=formattedDate;
        if((formattedDate+1)>7){
            day1=(formattedDate+1)%7;
        }
        else{
            day1=formattedDate+1;
        }
        Log.e("day","day:-"+day1+"");
        tv.setText(UtilsgetCurrentDate.getDay(day1)+","+"\t"+ UtilsgetCurrentDate.getTomorrowDate());
//        if (formattedDate==3) {
            date.setText(UtilsgetCurrentDate.getTomorrowDate());
            appointment_date=date.getText().toString();
//        }
        break;
    case 2:
        int day2=formattedDate;
        if((formattedDate+2)>7){
            day2=(formattedDate+2)%7;
        }
        else{
            day2=formattedDate+2;
        }
        Log.e("day","day:-"+day2+"");
        tv.setText(UtilsgetCurrentDate.getDay(day2)+","+"\t"+ UtilsgetCurrentDate.getWedenessdayDate());
//        if (formattedDate==4) {
        date.setText(UtilsgetCurrentDate.getWedenessdayDate());
        appointment_date=date.getText().toString();
//        }
        break;
    case 3:
        int day3=formattedDate;
        if((formattedDate+3)>7){
            day3=(formattedDate+3)%7;
        }
        else{
            day3=formattedDate+3;
        }
        Log.e("day","day:-"+day3+"");
        tv.setText(UtilsgetCurrentDate.getDay(day3)+","+"\t"+ UtilsgetCurrentDate.getThursdayDate());
//        if (formattedDate==5) {
        date.setText(UtilsgetCurrentDate.getThursdayDate());
        appointment_date=date.getText().toString();
//        }
        break;
    case 4:
        int day4=formattedDate;
        if((formattedDate+4)>7){
            day4=(formattedDate+4)%7;
        }
        else{
            day4=formattedDate+4;
        }
        Log.e("day","day:-"+day4+"");
        tv.setText(UtilsgetCurrentDate.getDay(day4)+","+"\t"+ UtilsgetCurrentDate.getFridayDate());
//        if (formattedDate==6) {
        date.setText(UtilsgetCurrentDate.getFridayDate());
        appointment_date=date.getText().toString();
//        }
        break;
    case 5:
        int day5=formattedDate;
        if((formattedDate+5)>7){
            day5=(formattedDate+5)%7;
        }
        else{
            day5=formattedDate+5;
        }
        Log.e("day","day:-"+day5+"");
        tv.setText(UtilsgetCurrentDate.getDay(day5)+ ","+"\t"+ UtilsgetCurrentDate.getSaturdayDate());
//        if (formattedDate==7) {
        date.setText(UtilsgetCurrentDate.getSaturdayDate());
        appointment_date=date.getText().toString();
//        }
        break;
    case 6:
        int day6=formattedDate;
        if((formattedDate+6)>7){
            day6=(formattedDate+6)%7;
        }
        else{
            day6=formattedDate+6;
        }
        Log.e("day","day:-"+day6+"");
        tv.setText(UtilsgetCurrentDate.getDay(day6)+","+"\t"+UtilsgetCurrentDate.getSundayDate());
//        if (formattedDate==0) {
            date.setText(UtilsgetCurrentDate.getSundayDate());
        appointment_date=date.getText().toString();
//        }
        break;
    default:
        return;

}
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return new MondayFragment();
                case 1: return new TuesdayFragment();
                case 2: return new WednesdayFragment();
                case 3: return new ThursdayFragment();
                case 4: return new FridayFragment();
                case 5: return new SaturdayFragment();
                case 6: return new SundayFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 7;
        }
    }
}
