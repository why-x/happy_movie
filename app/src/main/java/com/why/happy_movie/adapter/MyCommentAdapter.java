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
import com.why.happy_movie.bean.SearchCnimea;

import java.util.ArrayList;
import java.util.List;

/*
查询影片评论
 */
public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.MyHolder> {

    Context context;

    public MyCommentAdapter(Context context) {
        this.context = context;
    }

    List<MyComment> list=new ArrayList<>();
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyComment myComment = list.get(i);
        myHolder.head.setImageURI(myComment.getCommentHeadPic());
        myHolder.name.setText(myComment.getCommentUserName());
        myHolder.title.setText(myComment.getCommentContent());
        myHolder.num.setText(myComment.getReplyNum()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<MyComment> result) {
        if (result!=null){
            list.addAll(result);
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView head;
        TextView title,name,time,num;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.comment_head);
            name=itemView.findViewById(R.id.comment_name);
            title=itemView.findViewById(R.id.comment_title);
            time=itemView.findViewById(R.id.comment_time);
            num=itemView.findViewById(R.id.comment_num);
        }
    }
}
