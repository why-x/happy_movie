package com.why.happy_movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.bean.MyComment;
import com.why.happy_movie.bean.SearchCnimea;
import com.why.happy_movie.presenter.MovieCommertGreatPresenter;

import java.util.ArrayList;
import java.util.List;

/*
查询影片评论
 */
public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.MyHolder> {

    Context context;
    Great12 great12;

    public MyCommentAdapter(Context context) {
        this.context = context;
    }

    public void getgreat(Great12 great12){
        this.great12=great12;
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
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
        final MyComment myComment = list.get(i);
        myHolder.head.setImageURI(myComment.getCommentHeadPic());
        myHolder.name.setText(myComment.getCommentUserName());
        myHolder.title.setText(myComment.getCommentContent());
        myHolder.num.setText(myComment.getGreatNum()+"");
        myHolder.repNum.setText(myComment.getReplyNum()+"");
        int isGreat = myComment.getIsGreat();
        myHolder.hand.setChecked(isGreat == 1? true:false);
        if (isGreat == 1? true:false){
            myHolder.hand.setEnabled(false);
        }
        myHolder.hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                if(checked){
                    great12.ongreat(i);
                    myComment.setGreatNum(myComment.getGreatNum()+1);
                    myComment.setIsGreat(1);
                    notifyItemChanged(i);
                }else {
                    great12.fogreat(i);
                }
            }
        });
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
        CheckBox hand;
        TextView title,name,time,num,repNum;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            hand  = itemView.findViewById(R.id.comment_hand);
            head=itemView.findViewById(R.id.comment_head);
            name=itemView.findViewById(R.id.comment_name);
            title=itemView.findViewById(R.id.comment_title);
            time=itemView.findViewById(R.id.comment_time);
            num=itemView.findViewById(R.id.comment_num);
            repNum=itemView.findViewById(R.id.replyNum);
        }
    }

    public interface Great12{
        void ongreat(int i);
        void fogreat(int i);
    }
}
