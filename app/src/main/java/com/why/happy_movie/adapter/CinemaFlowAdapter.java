package com.why.happy_movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @author happy_movie
 * @date 2019/1/23 19:42
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class CinemaFlowAdapter extends RecyclerView.Adapter<CinemaFlowAdapter.MyViewHolder>  {
    private List<Integer> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public CinemaFlowAdapter(Context context, List<Integer> datas){
        this.mContext=context;
        this.mDatas=datas;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.simpleDraweeView.setImageURI("res:///"+mDatas.get(position%mDatas.size()));
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cinema_flow_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder { //承载Item视图的子布局
        SimpleDraweeView simpleDraweeView;

        public MyViewHolder(View view) {
            super(view);
            simpleDraweeView = view.findViewById(R.id.simp_cinema_flow);
        }
    }


}