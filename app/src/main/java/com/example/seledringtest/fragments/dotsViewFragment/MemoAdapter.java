package com.example.seledringtest.fragments.dotsViewFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.seledringtest.utilities.GeneralUtilis;

/**
 * Created by AttaUrRahman on 5/8/2018.
 */

public class MemoAdapter extends FragmentPagerAdapter {

    private int NUM_ITEMS = 4;

    public MemoAdapter(FragmentManager fm, Context context) {
        super(fm);
        NUM_ITEMS = GeneralUtilis.getSharedPreferences(context).getInt("memo_view_pager", 4);

    }


    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                return new Memo1Fragment();
            case 1:
                return new Memo2Fragment();
            case 2:
                return new Memo3Fragment();
            case 3:
                return new Memo4Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
