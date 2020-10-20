package com.sashashtmv.myshop.adapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> arrayList = new ArrayList<>();


    public ViewPagerFragmentAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    public void addFragment(Fragment fragment) {
        arrayList.add(fragment);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}