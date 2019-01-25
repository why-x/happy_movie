package com.why.happy_movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.adapter.CinemaFlowAdapter;
import com.why.happy_movie.adapter.CinemaFlowAdapter2;
import com.why.happy_movie.adapter.CinemaTimeAdapter;
import com.why.happy_movie.bean.CimemaldListBean;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.bean.MovieScheduleListBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.YingYuanBean;
import com.why.happy_movie.presenter.CimemaldListPresenter;
import com.why.happy_movie.presenter.MovieScheduleListPresenter;
import com.why.happy_movie.presenter.NearCinemasPresenter;
import com.why.happy_movie.presenter.RecommendCinemasPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class CinemaActivity extends AppCompatActivity implements View.OnClickListener,DataCall<Result<List<CimemaldListBean>>> {

    int userId =1771;
    String sessionId="15482908826721771";
    int page=1;
    int count=10;
    int movieid=0;
    @BindView(R.id.cinema_img)
    SimpleDraweeView cinemaImg;
    @BindView(R.id.cinema_name)
    TextView cinemaName;
    @BindView(R.id.cinema_place)
    TextView cinemaPlace;
   @BindView(R.id.cinema_time)
    RecyclerView cinemaTime;
    @BindView(R.id.cinema_return)
    ImageView cinemaReturn;
    private int ccid;
    List<MovieListBean> movieListBeans =new ArrayList<>();
    private CinemaFlowAdapter2 cinemaFlowAdapter;
    private MovieScheduleListPresenter movieScheduleListPresenter;
    List<MovieScheduleListBean> listBeans = new ArrayList<>();
    private CinemaTimeAdapter cinemaTimeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_all);
        ButterKnife.bind(this);

        movieScheduleListPresenter = new MovieScheduleListPresenter(new MovieList());

        Intent intent = getIntent();
        ccid = intent.getIntExtra("cid",0);
        String name1 = intent.getStringExtra("name1");
        String logo = intent.getStringExtra("logo");
        String address = intent.getStringExtra("address");
        cinemaPlace.setText(address);
        cinemaImg.setImageURI(logo);
        cinemaName.setText(name1);
        cinemaReturn.setOnClickListener(this);

        CimemaldListPresenter cimemaldListPresenter = new CimemaldListPresenter(this);
        cimemaldListPresenter.reqeust(ccid);


        RecyclerCoverFlow recyclerCoverFlow  = findViewById(R.id.rcf_cinema_flow);
        cinemaFlowAdapter = new CinemaFlowAdapter2(this, movieListBeans);
        recyclerCoverFlow.setAdapter(cinemaFlowAdapter);
        recyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {//滑动监听
            @Override
            public void onItemSelected(int position) {
                //Toast.makeText(CinemaActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                int id = movieListBeans.get(position).getId();
                listBeans.clear();
                movieScheduleListPresenter.reqeust(ccid,id);
            }
        });


        cinemaTime.setLayoutManager(new LinearLayoutManager(this));
        cinemaTimeAdapter = new CinemaTimeAdapter(this, listBeans);
        cinemaTime.setAdapter(cinemaTimeAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cinema_return:
                finish();
                break;
        }
    }


    @Override
    public void success(Result<List<CimemaldListBean>> data) {
        List<CimemaldListBean> result = data.getResult();
        for (int i = 0; i < result.size() ; i++) {
            MovieListBean movieListBean = new MovieListBean();
            movieListBean.setImageUrl(result.get(i).getImageUrl());
            movieListBean.setName(result.get(i).getName());
            movieListBean.setId(result.get(i).getId());
            movieListBeans.add(movieListBean);
        }
        movieid=result.get(0).getId();
        cinemaFlowAdapter.notifyDataSetChanged();
        movieScheduleListPresenter.reqeust(ccid,movieid);
    }

    @Override
    public void fail(ApiException e) {

    }
    class MovieList implements DataCall<Result<List<MovieScheduleListBean>>> {

        @Override
        public void success(Result<List<MovieScheduleListBean>> data) {
            List<MovieScheduleListBean> result = data.getResult();
            listBeans.addAll(result);
            cinemaTimeAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
