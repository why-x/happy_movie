package com.why.happy_movie.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.why.happy_movie.R;
import com.why.happy_movie.frag.home_one;
import com.why.happy_movie.frag.home_three;
import com.why.happy_movie.frag.home_two;

public class HomeActivity extends AppCompatActivity {

    private com.why.happy_movie.frag.home_one home_one;
    private com.why.happy_movie.frag.home_two home_two;
    private com.why.happy_movie.frag.home_three home_three;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        home_one = new home_one();
        home_two = new home_two();
        home_three = new home_three();
        fragmentTransaction.add(R.id.fl, home_one);
        fragmentTransaction.add(R.id.fl, home_two);
        fragmentTransaction.add(R.id.fl, home_three);
        fragmentTransaction.hide(home_two);
        fragmentTransaction.hide(home_three);
        fragmentTransaction.commit();

        RadioGroup radioGroup = findViewById(R.id.home_rg);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(home_one);
                fragmentTransaction.hide(home_two);
                fragmentTransaction.hide(home_three);
                switch (checkedId){
                    case R.id.home_rb1 :
                        fragmentTransaction.show(home_one);
                        break;
                    case R.id.home_rb2 :
                        fragmentTransaction.show(home_two);
                        break;
                    case R.id.home_rb3 :
                        fragmentTransaction.show(home_three);
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }
}
