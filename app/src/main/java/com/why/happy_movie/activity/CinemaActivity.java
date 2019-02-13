package com.why.happy_movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.adapter.CinemaFlowAdapter2;
import com.why.happy_movie.adapter.CinemaTimeAdapter;
import com.why.happy_movie.bean.CimemaldListBean;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.bean.MovieScheduleListBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.presenter.CimemaldListPresenter;
import com.why.happy_movie.presenter.MovieScheduleListPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class CinemaActivity extends BaseActivity implements View.OnClickListener,DataCall<Result<List<CimemaldListBean>>> {

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
    private String address;
    private String name1;
    private MovieListBean movieListBean;
    private RelativeLayout lll;
    private PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_all);
        ButterKnife.bind(this);
        lll = findViewById(R.id.lll);


        movieScheduleListPresenter = new MovieScheduleListPresenter(new MovieList());

        Intent intent = getIntent();
        ccid = intent.getIntExtra("cid",0);
        name1 = intent.getStringExtra("name1");
        String logo = intent.getStringExtra("logo");
        address = intent.getStringExtra("address");
        cinemaPlace.setText(address);
        cinemaImg.setImageURI(logo);
        cinemaName.setText(name1);
        cinemaReturn.setOnClickListener(this);

        /**
         * pop详情
         */
        RelativeLayout rll = findViewById(R.id.rll);
        rll.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                View inflate1 = View.inflate(CinemaActivity.this, R.layout.popu_cinema, null);
                popupWindow = new PopupWindow(inflate1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                TextView txt_address = inflate1.findViewById(R.id.address);
                txt_address.setText(address);
                TextView txt_phone  = inflate1.findViewById(R.id.phone);
                txt_phone.setText("010-18801039327");
                ImageView back = inflate1.findViewById(R.id.back);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

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
        cinemaTimeAdapter.getXuan(new CinemaTimeAdapter.XuanZuo() {
            @Override
            public void onXuanZuo(int possion) {
                MovieScheduleListBean movieScheduleListBean = listBeans.get(possion);
                Intent intent = new Intent(CinemaActivity.this, StatActivity.class);
                intent.putExtra("movienamem",movieListBean.getName());
                intent.putExtra("cinemaname",name1);
                intent.putExtra("address", address);
                intent.putExtra("shijian",movieScheduleListBean.getBeginTime()+"-"+movieScheduleListBean.getEndTime());
                intent.putExtra("ting",movieScheduleListBean.getScreeningHall());
                intent.putExtra("paiqiid",movieScheduleListBean.getId());
                intent.putExtra("price",movieScheduleListBean.getPrice());
                startActivity(intent);
            }
        });

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
            movieListBean = new MovieListBean();
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
