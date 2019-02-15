package com.why.happy_movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.MApp;
import com.why.happy_movie.bean.CommentReplyBean;
import com.why.happy_movie.bean.MyComment;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.SearchCnimea;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.presenter.CommentReplyPresenter;
import com.why.happy_movie.presenter.MovieCommertGreatPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/*
查询影片评论
 */
public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.MyHolder> {

    Context context;
    Great12 great12;
    Tjpl2 tjpl2;
    private int userId;
    private String sessionId;
    List<CommentReplyBean> commentReplyBeans = new ArrayList<>();
    private Comment2Adapter comment2Adapter;


    public MyCommentAdapter(Context context) {
        this.context = context;
    }

    public void getgreat(Great12 great12){
        this.great12=great12;
    }
    public void tjpl( Tjpl2 tjpl2){
        this.tjpl2=tjpl2;
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
        if(myComment.isaBoolean()){
            myHolder.list9.setVisibility(View.VISIBLE);
        }else {
            myHolder.list9.setVisibility(View.GONE);
        }

        myHolder.iv_discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).setaBoolean(false);
                }
                myComment.setaBoolean(true);

                tjpl2.ontjj2(i);
                myHolder.list9.setLayoutManager(new LinearLayoutManager(context));
                comment2Adapter = new Comment2Adapter(context,commentReplyBeans);
                myHolder.list9.setAdapter(comment2Adapter);
                commentReplyBeans.clear();
                CommentReplyPresenter commentReplyPresenter = new CommentReplyPresenter(new Pingll());
                commentReplyPresenter.reqeust(userId,sessionId,myComment.getCommentId(),1,10);

            }
        });
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
        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if (userBeans.size() > 0) {
            userId = userBeans.get(0).getUserId();
            sessionId = userBeans.get(0).getSessionId();
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView head;
        CheckBox hand;
        ImageView iv_discuss;
        TextView title,name,time,num,repNum;
        RecyclerView list9;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            hand  = itemView.findViewById(R.id.comment_hand);
            head=itemView.findViewById(R.id.comment_head);
            name=itemView.findViewById(R.id.comment_name);
            title=itemView.findViewById(R.id.comment_title);
            time=itemView.findViewById(R.id.comment_time);
            num=itemView.findViewById(R.id.comment_num);
            repNum=itemView.findViewById(R.id.replyNum);
            iv_discuss = itemView.findViewById(R.id.discuss);
            list9  = itemView.findViewById(R.id.list9);
        }
    }

    public interface Great12{
        void ongreat(int i);
        void fogreat(int i);
    }

    public interface Tjpl2{
        void ontjj2(int i);
    }

    private class Pingll implements DataCall<Result<List<CommentReplyBean>>> {
        @Override
        public void success(Result<List<CommentReplyBean>> data) {
            if (data.getResult().size()>0){
                commentReplyBeans.addAll(data.getResult());
                comment2Adapter.notifyDataSetChanged();
            }
            notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
