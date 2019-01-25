package com.why.happy_movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.activity.DetailsActivity;
import com.why.happy_movie.activity.ThreeListActivity;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.presenter.MyLovePresenter;

import java.util.List;

/**
 * @author happy_movie
 * @date 2019/1/23 19:42
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class ThreeListAdapter extends RecyclerView.Adapter<ThreeListAdapter.MyViewHolder>  {
    private List<MovieListBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    MyLove myLove;

    public ThreeListAdapter(Context context, List<MovieListBean> datas){
        this.mContext=context;
        this.mDatas=datas;
        inflater=LayoutInflater.from(mContext);
    }

    public   void xihuan(MyLove myLove){
        this.myLove=myLove;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.simpleDraweeView.setImageURI(mDatas.get(position).getImageUrl());
        holder.name.setText(mDatas.get(position).getName());
        holder.cont.setText(mDatas.get(position).getSummary());
        int followMovie = mDatas.get(position).isFollowMovie();
        holder.love.setChecked(followMovie==1? true:false);
        holder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                if(checked){
                    myLove.onLove(position);
                }else {
                    myLove.onCancle(position);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                int id = mDatas.get(position).getId();
                intent.putExtra("id",id);
                mContext.startActivity(intent);
            }
        });
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.threelist_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder { //承载Item视图的子布局
        SimpleDraweeView simpleDraweeView;
        TextView name;
        TextView cont;
        CheckBox love;

        public MyViewHolder(View view) {
            super(view);
            simpleDraweeView = view.findViewById(R.id.iv);
            name = view.findViewById(R.id.name);
            cont = view.findViewById(R.id.cont);
            love = view.findViewById(R.id.love);
        }
    }

    public interface MyLove{
        void onLove(int possion);
        void onCancle(int possion);
    }


}