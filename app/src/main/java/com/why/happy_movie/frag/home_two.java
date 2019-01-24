package com.why.happy_movie.frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.why.happy_movie.adapter.CinemasAdapter;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.YingYuanBean;
import com.why.happy_movie.presenter.NearCinemasPresenter;
import com.why.happy_movie.presenter.RecommendCinemasPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author happy_movie
 * @date 2019/1/22 20:27
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class home_two extends Fragment implements DataCall<Result<List<YingYuanBean>>> {

    private RadioButton yy_one;
    private RadioButton yy_two;
    int userId =1771;
    String sessionId="15482908826721771";
    int page=1;
    int count=10;
    List<YingYuanBean> yingYuanBeanList = new ArrayList<>();
    private CinemasAdapter cinemasAdapter;
    private EditText et_sou;
    private TextView tv_sou;
    private RecommendCinemasPresenter recommendCinemasPresenter;
    private NearCinemasPresenter nearCinemasPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_two,container,false);

        recommendCinemasPresenter = new RecommendCinemasPresenter(this);
        nearCinemasPresenter = new NearCinemasPresenter(new Fujin());

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

        yy_one = view.findViewById(R.id.yy_one);
        yy_two = view.findViewById(R.id.yy_two);
        RadioGroup radioGroup = view.findViewById(R.id.rg_yy);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        yy_one.setTextColor(Color.WHITE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.yy_one:
                        yy_one.setTextColor(Color.WHITE);
                        yy_two.setTextColor(Color.BLACK);
                        recommendCinemasPresenter.reqeust(userId,sessionId,page,count);
                        break;
                    case R.id.yy_two:
                        yy_one.setTextColor(Color.BLACK);
                        yy_two.setTextColor(Color.WHITE);
                        nearCinemasPresenter.reqeust(userId,sessionId,"116.30551391385724","40.04571807462411",page,count);
                        break;
                }
            }
        });

        recommendCinemasPresenter.reqeust(userId,sessionId,page,count);

        RecyclerView recyclerView = view.findViewById(R.id.list2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cinemasAdapter = new CinemasAdapter(getContext(), yingYuanBeanList);
        recyclerView.setAdapter(cinemasAdapter);


        return view;
    }

    @Override
    public void success(Result<List<YingYuanBean>> data) {
        List<YingYuanBean> result = data.getResult();
        yingYuanBeanList.clear();
        yingYuanBeanList.addAll(result);
        cinemasAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(ApiException e) {

    }

    class Fujin implements DataCall<Result<List<YingYuanBean>>> {

        @Override
        public void success(Result<List<YingYuanBean>> data) {
            List<YingYuanBean> result = data.getResult();
            yingYuanBeanList.clear();
            yingYuanBeanList.addAll(result);
            cinemasAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
