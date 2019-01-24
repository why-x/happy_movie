package com.why.happy_movie.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.why.happy_movie.adapter.CinemaFlowAdapter;
import com.why.happy_movie.adapter.DianyingAdapter;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.presenter.ComingSoonMoviePresenter;
import com.why.happy_movie.presenter.HotMoviePresenter;
import com.why.happy_movie.presenter.ReleaseMoviePresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.RecyclerCoverFlow;

/**
 * @author happy_movie
 * @date 2019/1/22 20:27
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class home_one extends Fragment implements DataCall<Result<List<MovieListBean>>> {

    List<MovieListBean> MovieListBeanlist = new ArrayList<>();
    int userId =1771;
    String sessionId="15482908826721771";
    int page=1;
    int count=10;
    private CinemaFlowAdapter cinemaFlowAdapter;
    List<MovieListBean> remendianyinglist = new ArrayList<>();
    List<MovieListBean> zhengzailist = new ArrayList<>();
    List<MovieListBean> jijianglist = new ArrayList<>();
    private DianyingAdapter dianyingAdapter;
    private RecyclerView remenxlist;
    private HotMoviePresenter hotMoviePresenter;
    private DianyingAdapter zhangzaiadapter;
    private DianyingAdapter jijiangadapter;
    private EditText et_sou;
    private TextView tv_sou;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_one,container,false);

        hotMoviePresenter = new HotMoviePresenter(this);
        hotMoviePresenter.reqeust(userId,sessionId,page,count);


        RecyclerCoverFlow recyclerCoverFlow = view.findViewById(R.id.rcf_cinema_flow);
        cinemaFlowAdapter = new CinemaFlowAdapter(getContext(), MovieListBeanlist);
        recyclerCoverFlow.setAdapter(cinemaFlowAdapter);

        ImageView sou = view.findViewById(R.id.sou);
        et_sou = view.findViewById(R.id.et_sou);
        tv_sou = view.findViewById(R.id.tv_sou);
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

        /**
         * 热门电影
         */
        remenxlist = view.findViewById(R.id.xlist);
        dianyingAdapter = new DianyingAdapter(getContext(), remendianyinglist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        remenxlist.setLayoutManager(linearLayoutManager);
        remenxlist.setAdapter(dianyingAdapter);

        /**
         * 正在热映
         */
        ReleaseMoviePresenter releaseMoviePresenter = new ReleaseMoviePresenter(new Zhengzai());
        releaseMoviePresenter.reqeust(userId,sessionId,page,count);
        RecyclerView zhengzai = view.findViewById(R.id.xlist2);
        zhangzaiadapter = new DianyingAdapter(getContext(), zhengzailist);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(OrientationHelper.HORIZONTAL);
        zhengzai.setLayoutManager(linearLayoutManager1);
        zhengzai.setAdapter(zhangzaiadapter);

        /**
         * 即将上映
         */
        ComingSoonMoviePresenter comingSoonMoviePresenter = new ComingSoonMoviePresenter(new Jjiang());
        comingSoonMoviePresenter.reqeust(userId,sessionId,page,count);
        RecyclerView jijiangrecycler = view.findViewById(R.id.xlist3);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(OrientationHelper.HORIZONTAL);
        jijiangrecycler.setLayoutManager(linearLayoutManager2);
        jijiangadapter = new DianyingAdapter(getContext(), jijianglist);
        jijiangrecycler.setAdapter(jijiangadapter);

        return view;
    }

    @Override
    public void success(Result<List<MovieListBean>> data) {
       MovieListBeanlist.addAll(data.getResult());
        cinemaFlowAdapter.notifyDataSetChanged();
        remendianyinglist.addAll(data.getResult());
        dianyingAdapter.notifyDataSetChanged();
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
            zhengzailist.addAll(data.getResult());
            zhangzaiadapter.notifyDataSetChanged();
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
            jijianglist.addAll(data.getResult());
            jijiangadapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
