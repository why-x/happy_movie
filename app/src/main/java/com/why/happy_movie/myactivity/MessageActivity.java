package com.why.happy_movie.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.MApp;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.bean.YongHuBean;
import com.why.happy_movie.presenter.UserInfoPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener,DataCall<Result<YongHuBean>> {


    @BindView(R.id.my_return)
    ImageView myReturn;
    @BindView(R.id.my_head)
    SimpleDraweeView my_head;
    @BindView(R.id.my_name)
    TextView my_name;
    @BindView(R.id.my_sex)
    TextView my_sex;
    @BindView(R.id.my_data)
    TextView my_data;
    @BindView(R.id.my_phone)
    TextView my_phone;
    @BindView(R.id.emaile)
    TextView emaile;
    YongHuBean yongHuBean=new YongHuBean();
    int userId =1771;
    String sessionId="15482908826721771";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        myReturn.setOnClickListener(this);

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        userId = userBeans.get(0).getUserId();
        sessionId = userBeans.get(0).getSessionId();

        UserInfoPresenter userInfoPresenter = new UserInfoPresenter(this);
        userInfoPresenter.reqeust(userId,sessionId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_return:
                finish();
                break;
        }
    }

    @Override
    public void success(Result<YongHuBean> data) {
        yongHuBean= data.getResult();
        my_head.setImageURI(yongHuBean.getHeadPic());
        my_name.setText(yongHuBean.getNickName());
        int sex = yongHuBean.getSex();
        my_sex.setText(sex==1? "男":"女");
        long birthday = yongHuBean.getBirthday();
        Date date = new Date(birthday);
        SimpleDateFormat fomat2 = new SimpleDateFormat("yyyy-MM-dd");
        String mydate = fomat2.format(date);
        my_data.setText(mydate);
        my_phone.setText(yongHuBean.getPhone());
        emaile.setText(yongHuBean.getEmail());
    }

    @Override
    public void fail(ApiException e) {

    }
}
