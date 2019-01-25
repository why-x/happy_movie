package com.why.happy_movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.activity.DetailsActivity;
import com.why.happy_movie.bean.MovieListBean;

import java.util.List;

/**
 * @author happy_movie
 * @date 2019/1/23 19:42
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class CinemaFlowAdapter2 extends RecyclerView.Adapter<CinemaFlowAdapter2.MyViewHolder>  {
    private List<MovieListBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public CinemaFlowAdapter2(Context context, List<MovieListBean> datas){
        this.mContext=context;
        this.mDatas=datas;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.simpleDraweeView.setImageURI(mDatas.get(position).getImageUrl());
        holder.text_cinema_flow1.setText(mDatas.get(position).getName());
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cinema_flow_item2,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder { //承载Item视图的子布局
        SimpleDraweeView simpleDraweeView;
        TextView text_cinema_flow1;

        public MyViewHolder(View view) {
            super(view);
            simpleDraweeView = view.findViewById(R.id.simp_cinema_flow);
           text_cinema_flow1  = view.findViewById(R.id.text_cinema_flow1);
        }
    }


}