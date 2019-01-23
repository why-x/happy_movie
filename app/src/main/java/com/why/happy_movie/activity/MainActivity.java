package com.why.happy_movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bw.movie.R;

public class MainActivity extends AppCompatActivity {


    int i = 3;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                i--;
                if(i<=0){
                    boolean jump = sp.getBoolean("jump", false);
                    if(!jump){
                        Intent intent = new Intent(MainActivity.this, JumpActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    return;
                }
                handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("sp",MODE_PRIVATE);
        handler.sendEmptyMessageDelayed(1,1000);

    }
}
