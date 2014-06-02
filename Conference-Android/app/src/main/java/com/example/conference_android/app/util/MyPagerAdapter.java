package com.example.conference_android.app.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.conference_android.app.FullScheduleFragment;
import com.example.conference_android.app.PersonalScheduleFragment;

/**
 * Created by danmikita on 5/29/14.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FullScheduleFragment.newInstance(0, "Page # 1");
            case 1:
                return PersonalScheduleFragment.newInstance(1, "Page # 2");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}