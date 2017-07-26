package com.emobi.bjaindoc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Emobi-Android-002 on 11/8/2016.
 */
public class AvailabiltyFragment4 extends Fragment implements TimePickerDialog.OnTimeSetListener {
    Calendar now;
    public static EditText mmrng,mafter,mevng,tmrng,tafter,tevng,wmrng,wafter,wevng,thmrng,thafter,thevng,fmrng,fafter,fevng,smrng,safter,sevng,sumrng,suafter,suevng;
    public static Spinner mmrng1,mmrng2,mafter1,mafter2,mevng1,mevng2,tmrng1,tmrng2,tafter1,tafter2,
            tevng1,tevng2,wmrng1,wmrng2,wafter1,wafter2,wevng1,wevng2,thmrng1,thmrng2,
            thafter1,thafter2,thevng1,thevng2,fmrng1,fmrng2,fafter1,fafter2,fevng1,fevng2,smrng1,smrng2,
            safter1,safter2,sevng1,sevng2,sumrng1,sumrng2,suafter1,suafter2,suevng1,suevng2;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.choo_tabs, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)view. findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        now = Calendar.getInstance();

        viewPager.setOffscreenPageLimit(7);

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
        /*mmrng1=(Spinner)view.findViewById(R.id.sp_place_m_mrng1);
        mmrng2=(Spinner)view.findViewById(R.id.sp_place_m_mrng2);
        mafter1=(Spinner)view.findViewById(R.id.sp_place_m_after1);
        mafter2=(Spinner)view.findViewById(R.id.sp_place_m_after2);
        mevng1=(Spinner)view.findViewById(R.id.sp_place_m_evng1);
        mevng2=(Spinner)view.findViewById(R.id.sp_place_m_evng2);
        tmrng1=(Spinner)view.findViewById(R.id.sp_place_t_mrng1);
        tmrng2=(Spinner)view.findViewById(R.id.sp_place_t_mrng2);
        tafter1=(Spinner)view.findViewById(R.id.sp_place_t_after1);
        tafter2=(Spinner)view.findViewById(R.id.sp_place_t_after2);
        tevng1=(Spinner)view.findViewById(R.id.sp_place_t_evng1);
        tevng2=(Spinner)view.findViewById(R.id.sp_place_t_evng2);
        wmrng1=(Spinner)view.findViewById(R.id.sp_place_w_mrng1);
        wmrng2=(Spinner)view.findViewById(R.id.sp_place_w_mrng2);
        wafter1=(Spinner)view.findViewById(R.id.sp_place_w_after1);
        wafter2=(Spinner)view.findViewById(R.id.sp_place_w_after2);
        wevng1=(Spinner)view.findViewById(R.id.sp_place_w_evng1);
        wevng2=(Spinner)view.findViewById(R.id.sp_place_w_evng2);
        thmrng1=(Spinner)view.findViewById(R.id.sp_place_th_mrng1);
        thmrng2=(Spinner)view.findViewById(R.id.sp_place_th_mrng2);
        thafter1=(Spinner)view.findViewById(R.id.sp_place_th_after1);
        thafter2=(Spinner)view.findViewById(R.id.sp_place_th_after2);
        thevng1=(Spinner)view.findViewById(R.id.sp_place_th_evng1);
        thevng2=(Spinner)view.findViewById(R.id.sp_place_th_evng2);
        fmrng1=(Spinner)view.findViewById(R.id.sp_place_f_mrng1);
        fmrng2=(Spinner)view.findViewById(R.id.sp_place_f_mrng2);
        fafter1=(Spinner)view.findViewById(R.id.sp_place_f_after1);
        fafter2=(Spinner)view.findViewById(R.id.sp_place_f_after2);
        fevng1=(Spinner)view.findViewById(R.id.sp_place_f_evng1);
        fevng2=(Spinner)view.findViewById(R.id.sp_place_f_evng2);
        smrng1=(Spinner)view.findViewById(R.id.sp_place_s_mrng1);
        smrng2=(Spinner)view.findViewById(R.id.sp_place_s_mrng2);
        safter1=(Spinner)view.findViewById(R.id.sp_place_s_after1);
        safter2=(Spinner)view.findViewById(R.id.sp_place_s_after2);
        sevng1=(Spinner)view.findViewById(R.id.sp_place_s_evng1);
        sevng2=(Spinner)view.findViewById(R.id.sp_place_s_evng2);
        sumrng1=(Spinner)view.findViewById(R.id.sp_place_su_mrng1);
        sumrng2=(Spinner)view.findViewById(R.id.sp_place_su_mrng2);
        suafter1=(Spinner)view.findViewById(R.id.sp_place_su_after1);
        suafter2=(Spinner)view.findViewById(R.id.sp_place_su_after2);
        suevng1=(Spinner)view.findViewById(R.id.sp_place_su_evng1);
        suevng2=(Spinner)view.findViewById(R.id.sp_place_su_evng2);


        mmrng = (EditText) view.findViewById(R.id.ed_place_m_mrng);
        mafter = (EditText) view.findViewById(R.id.ed_place_m_after);
        mevng = (EditText) view.findViewById(R.id.ed_place_m_evng);
        tmrng = (EditText) view.findViewById(R.id.ed_place_t_mrng);
        tafter = (EditText)view. findViewById(R.id.ed_place_t_after);
        tevng = (EditText) view.findViewById(R.id.ed_place_t_evng);
        wmrng = (EditText) view.findViewById(R.id.ed_place_w_mrng);
        wafter = (EditText) view.findViewById(R.id.ed_place_w_after);
        wevng = (EditText)view. findViewById(R.id.ed_place_w_evng);
        thmrng = (EditText) view.findViewById(R.id.ed_place_th_mrng);
        thafter = (EditText)view. findViewById(R.id.ed_place_th_after);
        thevng = (EditText)view. findViewById(R.id.ed_place_th_evng);
        fmrng = (EditText) view.findViewById(R.id.ed_place_f_mrng);
        fafter = (EditText)view. findViewById(R.id.ed_place_f_after);
        fevng = (EditText) view.findViewById(R.id.ed_place_f_evng);
        smrng = (EditText)view. findViewById(R.id.ed_place_s_mrng);
        safter = (EditText)view. findViewById(R.id.ed_place_s_after);
        sevng = (EditText) view.findViewById(R.id.ed_place_s_evng);
        sumrng = (EditText)view. findViewById(R.id.ed_place_su_mrng);
        suafter = (EditText)view. findViewById(R.id.ed_place_su_after);
        suevng = (EditText) view.findViewById(R.id.ed_place_su_evng);


        // Show a timepicker when the timeButton is clicked
        mmrng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AvailabiltyFragment4.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),isAdded()
                );

                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
            }
        });*/

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new ChooseMonday (), "mon");
        adapter.addFrag(new ChooseTueday (), "tue");
        adapter.addFrag(new ChooseWedday(), "wed");
        adapter.addFrag(new ChooseThurday (), "thu");
        adapter.addFrag(new ChooseFriday(), "fri");
        adapter.addFrag(new ChooseSatday (), "sat");
        adapter.addFrag(new ChooseSunday(), "sun");
        /*adapter.addFrag(new SixFragment(), "SIX");
        adapter.addFrag(new SevenFragment(), "SEVEN");
        adapter.addFrag(new EightFragment(), "EIGHT");
        adapter.addFrag(new NineFragment(), "NINE");
        adapter.addFrag(new TenFragment(), "TEN");*/
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setText("mon");
        tabLayout.getTabAt(1).setText("tue");
        tabLayout.getTabAt(2).setText("wed");
        tabLayout.getTabAt(3).setText("thu");
        tabLayout.getTabAt(4).setText("fri");
        tabLayout.getTabAt(5).setText("sat");
        tabLayout.getTabAt(6).setText("sun");
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
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {



        String am_pm = "";
        String time = hourOfDay+"h"+minute+"m";
        if (now.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (now.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
        mmrng.setText(time);
    }
}
