package com.example.phoen.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.example.phoen.popularmovies.fragments.ReviewsFragment;
import com.example.phoen.popularmovies.fragments.VideosFragment;
import com.example.phoen.popularmovies.utils.CustomPager;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] {"Reviews", "Videos"};
    private Context context;
    private List<Fragment> fragments;
    private int mCurrentPosition = -1;

//    public DetailsFragmentPagerAdapter(FragmentManager fm, Context context) {
//        super(fm);
//        this.context = context;
//    }

    public DetailsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
        fragments.add(new ReviewsFragment());
        fragments.add(new VideosFragment());
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    //    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                return new ReviewsFragment();
//            case 1:
//                return new VideosFragment();
//            default: return null;
//        }
//    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            CustomPager pager = (CustomPager) container;
            if (fragment != null && fragment.getView() != null) {
                mCurrentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }

    public void setTitle(int i, String s) {
        tabTitles[i]=s;
    }



}
