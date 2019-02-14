package com.why.happy_movie.activity;

import android.animation.ObjectAnimator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.why.happy_movie.frag.home_one;
import com.why.happy_movie.frag.home_three;
import com.why.happy_movie.frag.home_two;

public class HomeActivity extends BaseActivity {

    private com.why.happy_movie.frag.home_one home_one;
    private com.why.happy_movie.frag.home_two home_two;
    private com.why.happy_movie.frag.home_three home_three;
    private FragmentTransaction fragmentTransaction;
    long mExitTime=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final RadioButton home_rb1 = findViewById(R.id.home_rb1);
        final RadioButton home_rb2 = findViewById(R.id.home_rb2);
        final RadioButton home_rb3 = findViewById(R.id.home_rb3);

        final ObjectAnimator anim3 = ObjectAnimator.ofFloat(home_rb3, "scaleY", 1f, 1.2f);
        final ObjectAnimator anim31 = ObjectAnimator.ofFloat(home_rb3, "scaleX", 1f, 1.2f);
        anim3.setDuration(250);
        anim31.setDuration(250);
        final ObjectAnimator anim32 = ObjectAnimator.ofFloat(home_rb3, "scaleY", 1f, 1f);
        final ObjectAnimator anim33 = ObjectAnimator.ofFloat(home_rb3, "scaleX", 1f, 1f);
        anim32.setDuration(250);
        anim33.setDuration(250);

        final ObjectAnimator anim2 = ObjectAnimator.ofFloat(home_rb2, "scaleY", 1f, 1.2f);
        final ObjectAnimator anim21 = ObjectAnimator.ofFloat(home_rb2, "scaleX", 1f, 1.2f);
        anim2.setDuration(250);
        anim21.setDuration(250);
        final ObjectAnimator anim22 = ObjectAnimator.ofFloat(home_rb2, "scaleY", 1f, 1f);
        final ObjectAnimator anim23 = ObjectAnimator.ofFloat(home_rb2, "scaleX", 1f, 1f);
        anim22.setDuration(250);
        anim23.setDuration(250);

        final ObjectAnimator anim1 = ObjectAnimator.ofFloat(home_rb1, "scaleY", 1f, 1.2f);
        final ObjectAnimator anim11 = ObjectAnimator.ofFloat(home_rb1, "scaleX", 1f, 1.2f);
        anim1.setDuration(250);
        anim11.setDuration(250);
        final ObjectAnimator anim12 = ObjectAnimator.ofFloat(home_rb1, "scaleY", 1f, 1f);
        final ObjectAnimator anim13 = ObjectAnimator.ofFloat(home_rb1, "scaleX", 1f, 1f);
        anim12.setDuration(250);
        anim13.setDuration(250);
        anim1.start();
        anim11.start();

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
                        // 正式开始启动执行动画
                        anim1.start();
                        anim11.start();
                        anim32.start();
                        anim33.start();
                        anim22.start();
                        anim23.start();
                        break;
                    case R.id.home_rb2 :
                        // 正式开始启动执行动画
                        anim2.start();
                        anim21.start();
                        anim12.start();
                        anim13.start();
                        anim32.start();
                        anim33.start();
                        fragmentTransaction.show(home_two);
                        break;
                    case R.id.home_rb3 :
                        // 将一个TextView沿垂直方向先从原大小（1f）放大到5倍大小（5f），然后再变回原大小。
                        // 正式开始启动执行动画
                        anim3.start();
                        anim31.start();
                        anim12.start();
                        anim13.start();
                        anim22.start();
                        anim23.start();
                        fragmentTransaction.show(home_three);
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }

    //第一次按返回键系统的时间戳，默认为0。
    private long firstTime = 0;
    @Override
    public void onBackPressed() {
        //第二次按返回键的时间戳
        long secondTime = System.currentTimeMillis();
        //如果第二次的时间戳减去第一次的时间戳大于2000毫秒，则提示再按一次退出，如果小于2000毫秒则直接退出。
        if (secondTime - firstTime > 2000) {
            //弹出是提示消息，推荐Snackbar
            Toast.makeText(this, "再按一次退出……", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            finish();
        }
    }
}
