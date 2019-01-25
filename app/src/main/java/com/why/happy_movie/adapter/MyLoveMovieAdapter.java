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
import com.why.happy_movie.bean.SearchCnimea;
import com.why.happy_movie.bean.SearchMovie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
我关注的电影
 */
public class MyLoveMovieAdapter extends RecyclerView.Adapter<MyLoveMovieAdapter.MyHolder> {

    Context context;

    public MyLoveMovieAdapter(Context context) {
        this.context = context;
    }

    List<SearchMovie> list=new ArrayList<>();
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        SearchMovie searchMovie = list.get(i);
        myHolder.cinemaimg.setImageURI(searchMovie.getImageUrl());
        myHolder.name.setText(searchMovie.getName());
        myHolder.title.setText(searchMovie.getSummary());
        Date date = new Date(searchMovie.getReleaseTime());
        SimpleDateFormat fomat2 = new SimpleDateFormat("yyyy-MM-dd");
        String mydate = fomat2.format(date);
        myHolder.time.setText(mydate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<SearchMovie> result) {
        if (result!=null){
            list.addAll(result);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cinemaimg;
        TextView title,name,time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cinemaimg=itemView.findViewById(R.id.movie_img);
            name=itemView.findViewById(R.id.movie_name);
            title=itemView.findViewById(R.id.movie_title);
            time=itemView.findViewById(R.id.movie_time);
        }
    }
}
