package com.why.happy_movie.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;
import com.why.happy_movie.utils.util.WDActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNewsActivity extends WDActivity {

    @BindView(R.id.news_return)
    ImageView newsReturn;

    @Override
    protected void initView() {
        newsReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_news;
    }

    @Override
    protected void destoryData() {

    }

}
