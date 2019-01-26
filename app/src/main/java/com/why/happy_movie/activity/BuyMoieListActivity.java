package com.why.happy_movie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.MApp;
import com.why.happy_movie.adapter.CinemaTimeAdapter2;
import com.why.happy_movie.bean.MovieScheduleListBean;
import com.why.happy_movie.bean.MoviesDBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.presenter.MovieScheduleListPresenter;
import com.why.happy_movie.presenter.MoviesDPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

public class BuyMoieListActivity extends AppCompatActivity implements DataCall<Result<List<MovieScheduleListBean>>>{

    private int movieId;
    private int cinemaid;
    private int userId;
    private String sessionId;
    private SimpleDraweeView iv4;
    private TextView moviename;
    private TextView leixing;
    private TextView daoyan;
    private TextView shichang;
    private TextView chandi;
    private RecyclerView recyclerView;
    List<MovieScheduleListBean> movieScheduleListBeans = new ArrayList<>();
    private CinemaTimeAdapter2 cinemaTimeAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buymoielist);

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if(userBeans.size()>0){
            userId = userBeans.get(0).getUserId();
            sessionId = userBeans.get(0).getSessionId();

        }
        iv4 = findViewById(R.id.iv4);
        moviename = findViewById(R.id.moviename);
        leixing = findViewById(R.id.leixing);
        daoyan = findViewById(R.id.daoyan);
        shichang = findViewById(R.id.shichang);
        chandi = findViewById(R.id.chandi);
        ImageView  back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.list6);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cinemaTimeAdapter2 = new CinemaTimeAdapter2(this, movieScheduleListBeans);
        recyclerView.setAdapter(cinemaTimeAdapter2);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        movieId = intent.getIntExtra("movieId", 0);
        cinemaid = intent.getIntExtra("cinemaid", 0);

        MoviesDPresenter moviesDPresenter = new MoviesDPresenter(new MovieXiang());
        moviesDPresenter.reqeust(userId,sessionId,movieId);

        TextView  tex_name = findViewById(R.id.name);
        tex_name.setText(name);
        TextView txt_address = findViewById(R.id.address);
        txt_address.setText(address);


        MovieScheduleListPresenter movieScheduleListPresenter = new MovieScheduleListPresenter(this);
        movieScheduleListPresenter.reqeust(cinemaid,movieId);


    }

    @Override
    public void success(Result<List<MovieScheduleListBean>> data) {
        List<MovieScheduleListBean> result = data.getResult();
        movieScheduleListBeans.addAll(result);
        cinemaTimeAdapter2.notifyDataSetChanged();
    }

    @Override
    public void fail(ApiException e) {

    }

    class MovieXiang implements DataCall<Result<MoviesDBean>> {

        @Override
        public void success(Result<MoviesDBean> data) {
            MoviesDBean result = data.getResult();
            iv4.setImageURI(result.getImageUrl());
            moviename.setText(result.getName());
            leixing.setText("类型："+result.getMovieTypes());
            daoyan.setText("导演："+result.getDirector());
            shichang.setText("时常："+result.getDuration());
            chandi.setText("产地："+result.getPlaceOrigin());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
