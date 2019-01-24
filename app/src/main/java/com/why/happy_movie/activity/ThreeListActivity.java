package com.why.happy_movie.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.why.happy_movie.adapter.ThreeListAdapter;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.presenter.ComingSoonMoviePresenter;
import com.why.happy_movie.presenter.HotMoviePresenter;
import com.why.happy_movie.presenter.ReleaseMoviePresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

public class ThreeListActivity extends AppCompatActivity  implements DataCall<Result<List<MovieListBean>>> {

    private EditText et_sou;
    private TextView tv_sou;
    private RadioButton rb_one;
    private RadioButton rb_two;
    private RadioButton rb_three;
    int userId =1771;
    String sessionId="15482908826721771";
    int page=1;
    int count=10;
    List<MovieListBean> remendianyinglist = new ArrayList<>();
    private ThreeListAdapter threeListAdapter;
    private ReleaseMoviePresenter releaseMoviePresenter;
    private HotMoviePresenter hotMoviePresenter;
    private ComingSoonMoviePresenter comingSoonMoviePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_list);

        hotMoviePresenter = new HotMoviePresenter(this);
        hotMoviePresenter.reqeust(userId,sessionId,page,count);
        releaseMoviePresenter = new ReleaseMoviePresenter(new Zhengzai());
        comingSoonMoviePresenter = new ComingSoonMoviePresenter(new Jjiang());

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView sou = findViewById(R.id.sou);
        et_sou = findViewById(R.id.et_sou);
        tv_sou = findViewById(R.id.tv_sou);
        sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_sou.setVisibility(View.VISIBLE);
                tv_sou.setVisibility(View.VISIBLE);
            }
        });
        tv_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_sou.setVisibility(View.GONE);
                tv_sou.setVisibility(View.GONE);
            }
        });

        rb_one = findViewById(R.id.rb_one);
        rb_two = findViewById(R.id.rb_two);
        rb_three = findViewById(R.id.rb_three);
        RadioGroup radioGroup = findViewById(R.id.rg_aa);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        rb_one.setTextColor(Color.WHITE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_one:
                        rb_one.setTextColor(Color.WHITE);
                        rb_two.setTextColor(Color.BLACK);
                        rb_three.setTextColor(Color.BLACK);
                        hotMoviePresenter.reqeust(userId,sessionId,page,count);
                        break;
                    case R.id.rb_two:
                        rb_two.setTextColor(Color.WHITE);
                        rb_one.setTextColor(Color.BLACK);
                        rb_three.setTextColor(Color.BLACK);
                        releaseMoviePresenter.reqeust(userId,sessionId,page,count);
                        break;
                    case R.id.rb_three:
                        rb_three.setTextColor(Color.WHITE);
                        rb_two.setTextColor(Color.BLACK);
                        rb_one.setTextColor(Color.BLACK);
                        comingSoonMoviePresenter.reqeust(userId,sessionId,page,count);
                        break;
                }
            }
        });


        RecyclerView recyclerView = findViewById(R.id.list);
        threeListAdapter = new ThreeListAdapter(this, remendianyinglist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(threeListAdapter);

    }

    @Override
    public void success(Result<List<MovieListBean>> data) {
        remendianyinglist.clear();
        remendianyinglist.addAll(data.getResult());
        threeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(ApiException e) {

    }

    /**
     * 正在热映
     */
    class Zhengzai implements DataCall<Result<List<MovieListBean>>> {

        @Override
        public void success(Result<List<MovieListBean>> data) {
            remendianyinglist.clear();
            remendianyinglist.addAll(data.getResult());
            threeListAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 即将上映
     */
    class Jjiang implements DataCall<Result<List<MovieListBean>>> {

        @Override
        public void success(Result<List<MovieListBean>> data) {
            remendianyinglist.clear();
            remendianyinglist.addAll(data.getResult());
            threeListAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
