package com.why.happy_movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.bean.MyComment;

import java.util.ArrayList;
import java.util.List;

/*
查询影片评论
 */
public class MyRccordAdapter extends RecyclerView.Adapter<MyRccordAdapter.MyHolder> {

    Context context;

    public MyRccordAdapter(Context context) {
        this.context = context;
    }

    List<MyComment> list=new ArrayList<>();
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rccord_item1, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyHolder extends RecyclerView.ViewHolder {


        public MyHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
