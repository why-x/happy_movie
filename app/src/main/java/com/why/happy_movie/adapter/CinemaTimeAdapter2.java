package com.why.happy_movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.why.happy_movie.bean.MovieScheduleListBean;

import java.util.List;

/**
 * @author happy_movie
 * @date 2019/1/23 19:42
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class CinemaTimeAdapter2 extends RecyclerView.Adapter<CinemaTimeAdapter2.MyViewHolder>  {
    private List<MovieScheduleListBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public CinemaTimeAdapter2(Context context, List<MovieScheduleListBean> datas){
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
        holder.ting.setText(mDatas.get(position).getScreeningHall());
        holder.begintime.setText(mDatas.get(position).getBeginTime());
        holder.endtime.setText(mDatas.get(position).getEndTime()+"  end");
        holder.price.setText(""+mDatas.get(position).getPrice());
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cinema_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder { //承载Item视图的子布局
        TextView ting;
        TextView begintime;
        TextView  endtime;
        TextView price;

        public MyViewHolder(View view) {
            super(view);
            ting = view.findViewById(R.id.ting);
            begintime = view.findViewById(R.id.begintime);
            endtime = view.findViewById(R.id.endtime);
            price = view.findViewById(R.id.price);
        }
    }


}