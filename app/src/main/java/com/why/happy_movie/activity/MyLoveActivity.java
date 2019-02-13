package com.why.happy_movie.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.why.happy_movie.MApp;
import com.why.happy_movie.adapter.MyLoveCinemaAdapter;
import com.why.happy_movie.adapter.MyLoveMovieAdapter;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.SearchCnimea;
import com.why.happy_movie.bean.SearchMovie;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.presenter.FindCinemaPresenter;
import com.why.happy_movie.presenter.FindMoviePresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的关注
 */
public class MyLoveActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.movie)
    RadioButton movie;
    @BindView(R.id.cinema)
    RadioButton cinema;
    @BindView(R.id.rg_yy)
    RadioGroup rgYy;
    @BindView(R.id.love_return)
    ImageView loveReturn;
    @BindView(R.id.love_recycler)
    RecyclerView loveRecycler;
    private MyLoveCinemaAdapter myLoveCinemaAdapter;
    private int userId;
    private String sessionId;
    private MyLoveMovieAdapter myLoveMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_love);
        ButterKnife.bind(this);

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if (userBeans.size() > 0) {
            userId = userBeans.get(0).getUserId();
            sessionId = userBeans.get(0).getSessionId();

        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        loveRecycler.setLayoutManager(linearLayoutManager);

        //关注的电影
        myLoveMovieAdapter = new MyLoveMovieAdapter(MyLoveActivity.this);
        loveRecycler.setAdapter(myLoveMovieAdapter);
        FindMoviePresenter findMoviePresenter = new FindMoviePresenter(new FindMovie());
        findMoviePresenter.reqeust(userId, sessionId, 1, 10);

        movie.setTextColor(Color.WHITE);
        rgYy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.movie:
                        movie.setTextColor(Color.WHITE);
                        cinema.setTextColor(Color.BLACK);
                        //关注的电影
                        myLoveMovieAdapter = new MyLoveMovieAdapter(MyLoveActivity.this);
                        loveRecycler.setAdapter(myLoveMovieAdapter);
                        FindMoviePresenter findMoviePresenter = new FindMoviePresenter(new FindMovie());
                        findMoviePresenter.reqeust(userId, sessionId, 1, 10);
                        break;
                    case R.id.cinema:
                        movie.setTextColor(Color.BLACK);
                        cinema.setTextColor(Color.WHITE);
                        //关注的影院
                        myLoveCinemaAdapter = new MyLoveCinemaAdapter(MyLoveActivity.this);
                        loveRecycler.setAdapter(myLoveCinemaAdapter);
                        FindCinemaPresenter findCinemaPresenter = new FindCinemaPresenter(new FindCinema());
                        findCinemaPresenter.reqeust(userId, sessionId, 1, 10);
                        break;
                }
            }
        });

        loveReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.love_return:
                finish();
                break;
        }
    }

    private class FindCinema implements DataCall<Result<List<SearchCnimea>>> {
        @Override
        public void success(Result<List<SearchCnimea>> data) {
            List<SearchCnimea> result = data.getResult();
            myLoveCinemaAdapter.addAll(result);
            myLoveCinemaAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    private class FindMovie implements DataCall<Result<List<SearchMovie>>> {
        @Override
        public void success(Result<List<SearchMovie>> data) {
            List<SearchMovie> result = data.getResult();
            myLoveMovieAdapter.addAll(result);
            myLoveMovieAdapter.notifyDataSetChanged();

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
