package com.sashashtmv.myshop;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.sashashtmv.myshop.adapter.ViewPagerFragmentAdapter;
import com.sashashtmv.myshop.fragment.OneFragment;
import com.sashashtmv.myshop.fragment.ThreeFragment;
import com.sashashtmv.myshop.fragment.TwoFragment;


public class MainActivity extends AppCompatActivity {

    ViewPager2 myViewPager2;
    ViewPagerFragmentAdapter myAdapter;
    OneFragment oneFragment;
    TwoFragment twoFragment;
    ThreeFragment threeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager2 = findViewById(R.id.view_pager);
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();

        myAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());

        myAdapter.addFragment(oneFragment);
        myAdapter.addFragment(twoFragment);
        myAdapter.addFragment(threeFragment);

        myViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        myViewPager2.setAdapter(myAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myViewPager2 != null)
            myViewPager2.setAdapter(myAdapter);
    }
}
