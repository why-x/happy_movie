package com.why.happy_movie.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.why.happy_movie.MApp;
import com.why.happy_movie.adapter.NewSAdapter;
import com.why.happy_movie.bean.MyNewsBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.presenter.MyNewsPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;
import com.why.happy_movie.utils.util.WDActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNewsActivity extends WDActivity implements DataCall<Result<List<MyNewsBean>>> {

    @BindView(R.id.news_return)
    ImageView newsReturn;
    private int userId;
    private String sessionId;
    @BindView(R.id.shu)
    TextView shu;
    @BindView(R.id.list7)
    RecyclerView list7;
    private List<MyNewsBean> list = new ArrayList<>();
    private MyNewsPresenter myNewsPresenter;
    private NewSAdapter newSAdapter;

    @Override
    protected void initView() {

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if(userBeans.size()>0){
            UserBean userBean = userBeans.get(0);
            userId = userBean.getUserId();
            sessionId = userBean.getSessionId();
        }

        list7.setLayoutManager(new LinearLayoutManager(this));
        newSAdapter = new NewSAdapter(this,list);
        list7.setAdapter(newSAdapter);
        myNewsPresenter = new MyNewsPresenter(this);
        myNewsPresenter.reqeust(userId,sessionId,1,10);

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

    @Override
    public void success(Result<List<MyNewsBean>> data) {
        list.addAll( data.getResult());
        newSAdapter.notifyDataSetChanged();
        shu.setText("系统消息（"+ list.size()+"条未读）");

    }

    @Override
    public void fail(ApiException e) {

    }
}
