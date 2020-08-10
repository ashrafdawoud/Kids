package com.example.faroukproject.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.faroukproject.ViewPagerFragments.Fragment1;
import com.example.faroukproject.ViewPagerFragments.Fragment2;
import com.example.faroukproject.ViewPagerFragments.Fragment3;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super (fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            default:
                return new Fragment1();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
