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
import com.why.happy_movie.adapter.MyCommentAdapter;
import com.why.happy_movie.adapter.MyRccordAdapter;

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
    /*@BindView(R.id.pay_recycler)
    RecyclerView payRecycler;*/
    @BindView(R.id.pay_return)
    ImageView payReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rccord);
        ButterKnife.bind(this);
        wPay.setOnClickListener(this);
        okPay.setOnClickListener(this);
        payReturn.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
/*
        payRecycler.setLayoutManager(linearLayoutManager);
        MyRccordAdapter myRccordAdapter = new MyRccordAdapter(this);
        payRecycler.setAdapter(myRccordAdapter);*/
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
}
