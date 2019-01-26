package com.why.happy_movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.activity.DetailsActivity;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.bean.MoviesDBean;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author happy_movie
 * @date 2019/1/23 19:42
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>  {
    private List<MoviesDBean.ShortFilmListBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public VideoAdapter(Context context, List<MoviesDBean.ShortFilmListBean> datas){
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

        holder.jzVideoPlayer.setUp(mDatas.get(position).getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
        Glide.with(mContext).load(mDatas.get(position).getImageUrl()).into(holder.jzVideoPlayer.thumbImageView);

    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.video_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder { //承载Item视图的子布局
        JZVideoPlayerStandard jzVideoPlayer;


        public MyViewHolder(View view) {
            super(view);
            jzVideoPlayer = view.findViewById(R.id.videoplayer);
        }
    }


}