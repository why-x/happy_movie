package com.why.happy_movie.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.why.happy_movie.R;
import com.why.happy_movie.frag.frag_four;
import com.why.happy_movie.frag.frag_one;
import com.why.happy_movie.frag.frag_three;
import com.why.happy_movie.frag.frag_two;

import java.util.ArrayList;
import java.util.List;

public class JumpActivity extends AppCompatActivity {

    private List<Fragment> list;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);


        list = new ArrayList<>();
        list.add(new frag_one());
        list.add(new frag_two());
        list.add(new frag_three());
        list.add(new frag_four());

        ViewPager viewPager = findViewById(R.id.my_viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        radioGroup = findViewById(R.id.my_rg);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                radioGroup.check(radioGroup.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}
