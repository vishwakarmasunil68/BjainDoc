package com.emobi.bjaindoc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Emobi-Android-002 on 10/20/2016.
 */
public class PagerAdapterDoctor extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment upcomingtab,pasttab;
    public PagerAdapterDoctor(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                upcomingtab=new TabDoctorUpcomingDate();
                return upcomingtab;
            case 1:
                pasttab=new TabDoctorPastDate();
                return pasttab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
