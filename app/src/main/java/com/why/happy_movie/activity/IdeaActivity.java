package com.why.happy_movie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.why.happy_movie.MApp;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.presenter.MyIdeaPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdeaActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.idea_lall)
    LinearLayout ideaLall;
    @BindView(R.id.idea_lall2)
    LinearLayout ideaLall2;
    @BindView(R.id.idea_put)
    Button ideaPut;
    @BindView(R.id.idea_lall3)
    LinearLayout ideaLall3;
    @BindView(R.id.idea_return)
    ImageView ideaReturn;
    @BindView(R.id.myidea)
    EditText myidea;
    private int userId;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idea_activity);
        ButterKnife.bind(this);
        ideaReturn.setOnClickListener(this);
        ideaPut.setOnClickListener(this);
        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if (userBeans.size()>0){
            userId = userBeans.get(0).getUserId();
            sessionId = userBeans.get(0).getSessionId();

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idea_return:
                finish();
                break;
            case R.id.idea_put:
                String idea = myidea.getText().toString().trim();
                if(idea.equals("")){
                    Toast.makeText(this, "请输入内容……", Toast.LENGTH_SHORT).show();
                    return;
                }
                MyIdeaPresenter myIdeaPresenter = new MyIdeaPresenter(new IdeaCall());
                myIdeaPresenter.reqeust(userId,sessionId,idea);
                break;
        }
    }

    private class IdeaCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(getBaseContext(),data.getMessage()+"",Toast.LENGTH_SHORT).show();
            ideaLall.setVisibility(View.VISIBLE);
            ideaLall2.setVisibility(View.GONE);
            ideaLall3.setVisibility(View.GONE);
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(),"反馈失败！！！",Toast.LENGTH_SHORT).show();

        }
    }
}
