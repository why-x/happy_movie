package com.why.happy_movie.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLoveActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.movie)
    RadioButton movie;
    @BindView(R.id.cinema)
    RadioButton cinema;
    @BindView(R.id.rg_yy)
    RadioGroup rgYy;
    @BindView(R.id.love_return)
    ImageView loveReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_love);
        ButterKnife.bind(this);

        movie.setTextColor(Color.WHITE);
        rgYy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.movie:
                        movie.setTextColor(Color.WHITE);
                        cinema.setTextColor(Color.BLACK);
                        break;
                    case R.id.cinema:
                        movie.setTextColor(Color.BLACK);
                        cinema.setTextColor(Color.WHITE);
                        break;
                }
            }
        });

        loveReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.love_return:
                finish();
                break;
        }
    }
}
