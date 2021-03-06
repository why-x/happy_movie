package com.why.happy_movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.activity.BuyMoieListActivity;
import com.why.happy_movie.activity.StatActivity;
import com.why.happy_movie.bean.MyComment;
import com.why.happy_movie.bean.MyPay;

import java.util.ArrayList;
import java.util.List;

/*
查询影片评论
 */
public class MyRccordAdapter extends RecyclerView.Adapter<MyRccordAdapter.MyHolder> {

    Context context;
    ZhiFu zhiFu;


    public MyRccordAdapter(Context context) {
        this.context = context;
    }

    public void getZhifu(ZhiFu zhiFu){
        this.zhiFu=zhiFu;
    }

    List<MyPay> list=new ArrayList<>();
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rccord_item1, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        final MyPay myPay = list.get(i);
        myHolder.moviename.setText(myPay.getMovieName());
        myHolder.ordernumber.setText(myPay.getOrderId());
        myHolder.cinemaname.setText(myPay.getCinemaName());
        myHolder.moviehall.setText(myPay.getScreeningHall());
        myHolder.rccordtime.setText(myPay.getBeginTime()+"  "+myPay.getEndTime());
        myHolder.rccordnum.setText(myPay.getAmount()+"张");
        myHolder.rccordmoney.setText(myPay.getPrice()+"元");
        if (myPay.getStatus()==2){
            myHolder.buy.setVisibility(View.GONE);
        }
        myHolder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhiFu.onZhiFu(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<MyPay> result) {
        if (result!=null){
            list.addAll(result);
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView moviename,ordernumber,cinemaname,moviehall,rccordtime,rccordnum,rccordmoney;
        Button buy;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            moviename=itemView.findViewById(R.id.moviename);
            ordernumber=itemView.findViewById(R.id.ordernumber);
            cinemaname=itemView.findViewById(R.id.cinemaname);
            moviehall=itemView.findViewById(R.id.moviehall);
            rccordtime=itemView.findViewById(R.id.rccordtime);
            rccordnum=itemView.findViewById(R.id.rccordnum);
            rccordmoney=itemView.findViewById(R.id.rccordmoney);
            buy = itemView.findViewById(R.id.moviebuy);

        }
    }

    public interface ZhiFu{
        void onZhiFu(int possion);
    }
}
