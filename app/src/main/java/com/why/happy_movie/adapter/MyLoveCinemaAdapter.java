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
import com.why.happy_movie.activity.MyLoveActivity;
import com.why.happy_movie.bean.SearchCnimea;

import java.util.ArrayList;
import java.util.List;
/*
我关注的影院
 */
public class MyLoveCinemaAdapter extends RecyclerView.Adapter<MyLoveCinemaAdapter.MyHolder> {

    Context context;

    public MyLoveCinemaAdapter(Context context) {
        this.context = context;
    }

    List<SearchCnimea> list=new ArrayList<>();
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cnima_my_item, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        SearchCnimea searchCnimea = list.get(i);
        myHolder.cinemaimg.setImageURI(searchCnimea.getLogo());
        myHolder.name.setText(searchCnimea.getName());
        myHolder.title.setText(searchCnimea.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<SearchCnimea> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cinemaimg;
        TextView title,name;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cinemaimg=itemView.findViewById(R.id.cinema_img);
            name=itemView.findViewById(R.id.cinema_name);
            title=itemView.findViewById(R.id.cinema_title);
        }
    }
}
