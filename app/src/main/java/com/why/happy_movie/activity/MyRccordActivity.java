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
import android.widget.Toast;

import com.bw.movie.R;
import com.why.happy_movie.MApp;
import com.why.happy_movie.adapter.MyRccordAdapter;
import com.why.happy_movie.bean.MyPay;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.presenter.MyPayPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购票记录
 */
public class MyRccordActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.w_pay)
    RadioButton wPay;
    @BindView(R.id.ok_pay)
    RadioButton okPay;
    @BindView(R.id.rg_yy)
    RadioGroup rgYy;
    @BindView(R.id.pay_return)
    ImageView payReturn;
    @BindView(R.id.pay_recycler)
    RecyclerView payRecycler;
    private MyRccordAdapter myRccordAdapter;
    private int userId;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rccord);
        ButterKnife.bind(this);

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if (userBeans.size() > 0) {
            userId = userBeans.get(0).getUserId();
            sessionId = userBeans.get(0).getSessionId();


        }
        wPay.setOnClickListener(this);
        okPay.setOnClickListener(this);
        payReturn.setOnClickListener(this);
        MyPayPresenter myPayPresenter=new MyPayPresenter(new MyPayCall());
        myPayPresenter.reqeust(userId,sessionId,1,5,1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        payRecycler.setLayoutManager(linearLayoutManager);
        myRccordAdapter = new MyRccordAdapter(this);
        payRecycler.setAdapter(myRccordAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.w_pay:
                wPay.setTextColor(Color.WHITE);
                okPay.setTextColor(Color.BLACK);
                break;
            case R.id.ok_pay:
                wPay.setTextColor(Color.BLACK);
                okPay.setTextColor(Color.WHITE);
                break;
            case R.id.pay_return:
                finish();
                break;

        }
    }

    private class MyPayCall implements DataCall<Result<List<MyPay>>> {
        @Override
        public void success(Result<List<MyPay>> data) {
            Toast.makeText(getBaseContext(),data.getMessage(),Toast.LENGTH_SHORT).show();

            List<MyPay> result = data.getResult();
            myRccordAdapter.addAll(result);
            myRccordAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
