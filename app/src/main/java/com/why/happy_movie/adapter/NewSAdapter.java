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
import com.why.happy_movie.bean.MyNewsBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author happy_movie
 * @date 2019/1/23 19:42
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class NewSAdapter extends RecyclerView.Adapter<NewSAdapter.MyViewHolder>  {
    private List<MyNewsBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public NewSAdapter(Context context, List<MyNewsBean> datas){
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
            holder.count.setText(mDatas.get(position).getContent());
            holder.tit.setText(mDatas.get(position).getTitle());
        Date date = new Date(mDatas.get(position).getPushTime());
        SimpleDateFormat fomat2 = new SimpleDateFormat("hh:mm");
        String mydate = fomat2.format(date);
            holder.shijian.setText(mydate);
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder { //承载Item视图的子布局
        TextView count;
        TextView tit;
         TextView shijian;

        public MyViewHolder(View view) {
            super(view);
            count = view.findViewById(R.id.count);
            tit = view.findViewById(R.id.tit);
            shijian = view.findViewById(R.id.shijian);
        }
    }


}