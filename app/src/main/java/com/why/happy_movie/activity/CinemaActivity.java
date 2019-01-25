package com.why.happy_movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.YingYuanBean;
import com.why.happy_movie.presenter.NearCinemasPresenter;
import com.why.happy_movie.presenter.RecommendCinemasPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.RecyclerCoverFlow;

public class CinemaActivity extends AppCompatActivity implements View.OnClickListener {

    int userId =1771;
    String sessionId="15482908826721771";
    int page=1;
    int count=10;
    @BindView(R.id.cinema_img)
    SimpleDraweeView cinemaImg;
    @BindView(R.id.cinema_name)
    TextView cinemaName;
    @BindView(R.id.cinema_place)
    TextView cinemaPlace;
    @BindView(R.id.cinema_flow)
    RecyclerCoverFlow cinemaFlow;
    @BindView(R.id.cinema_time)
    RecyclerView cinemaTime;
    @BindView(R.id.cinema_return)
    ImageView cinemaReturn;
    private int ccid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_all);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        ccid = intent.getIntExtra("cid",0);


        cinemaReturn.setOnClickListener(this);
       /* cinemaImg.setImageURI(result.get(ccid).getLogo());
        cinemaName.setText(result.get(ccid).getName());
        cinemaPlace.setText(result.get(ccid).getAddress());*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cinema_return:
                finish();
                break;
        }
    }


}
