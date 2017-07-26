package com.emobi.bjaindoc.widgets;

import android.widget.BaseAdapter;

/**
 * Created by sunil on 31-03-2017.
 */

public abstract class BaseSwipListAdapter extends BaseAdapter {

    public boolean getSwipEnableByPosition(int position){
        return true;
    }
}
