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
import com.why.happy_movie.activity.CinemaActivity;
import com.why.happy_movie.activity.DetailsActivity;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.bean.YingYuanBean;

import java.util.List;

/**
 * @author happy_movie
 * @date 2019/1/23 19:42
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class CinemasAdapter extends RecyclerView.Adapter<CinemasAdapter.MyViewHolder>  {
    private List<YingYuanBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    Like like;

    public CinemasAdapter(Context context, List<YingYuanBean> datas){
        this.mContext=context;
        this.mDatas=datas;
        inflater=LayoutInflater.from(mContext);
    }

    public void getLike(Like like){
        this.like=like;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.iv1.setImageURI(mDatas.get(position).getLogo());
        holder.name.setText(mDatas.get(position).getName());
        holder.address.setText(mDatas.get(position).getAddress());
        holder.juli.setText(mDatas.get(position).getDistance()+"km");
        int followCinema = mDatas.get(position).getFollowCinema();
        holder.like.setChecked(followCinema==1?true:false);
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                if(checked){
                    like.onSuccess(position);
                }else {
                    like.onFaile(position);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,CinemaActivity.class);
                intent.putExtra("name1",mDatas.get(position).getName());
                intent.putExtra("logo",mDatas.get(position).getLogo());
                intent.putExtra("cid",mDatas.get(position).getId());
                intent.putExtra("address",mDatas.get(position).getAddress());
                mContext.startActivity(intent);
            }
        });
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cinemas_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder { //承载Item视图的子布局
        SimpleDraweeView iv1;
        TextView name;
        TextView address;
        TextView juli;
        CheckBox like;

        public MyViewHolder(View view) {
            super(view);
            iv1 = view.findViewById(R.id.iv1);
            name = view.findViewById(R.id.name);
            address = view.findViewById(R.id.address);
            juli = view.findViewById(R.id.juli);
            like = view.findViewById(R.id.like);

        }
    }

    public interface Like{
        void onSuccess(int possion);
        void onFaile(int possion);
    }


}