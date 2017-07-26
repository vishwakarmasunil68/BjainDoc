package com.emobi.bjaindoc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
class ContentFragmentAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 7;
    private final Context c;
    private int[] titles ;

    public ContentFragmentAdapter(FragmentManager fragmentManager, Context context, int[] titles2) {
        super(fragmentManager);
        titles=titles2;
        c = context;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // Open FragmentTab1.java
            case 0:
                return new Home_Fragment();

            case 1:
                return new Health_Feed_Fragment();
            case 2:
                return new Categories_Fragment();
            case 3:
                return new ChatFragment();

            case 4:
                return new Search_Fragment();

            case 5:
                return new Favourite_Fragment();

            case 6:
                return new Settings_Fragment();

            default:

        }
        return null;
    }

    @Override
    public CharSequence  getPageTitle(int position) {
        /*Drawable image = c.getResources().getDrawable(titles[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;*/
        return titles[position]+"";
    }
//
//    public Integer[] getTitles() {
//        return titles;
//    }
    // Returns the page title for the top indicator
//    public Integer getPageTitle(int position) {
//        return titles[position];
//    }

}
