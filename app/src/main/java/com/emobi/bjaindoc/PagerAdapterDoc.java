package com.emobi.bjaindoc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Emobi-Android-002 on 10/20/2016.
 */
public class PagerAdapterDoc extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment frag0,frag1,frag2;
    public PagerAdapterDoc(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                frag0=new UpdateDoc_Dcotor();
                return frag0;
            case 1:
                frag1=new UpdateDoctorFragment();
                return frag1;

            case 2:
                frag2=new FragmentDocAvailability();
                return frag2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
