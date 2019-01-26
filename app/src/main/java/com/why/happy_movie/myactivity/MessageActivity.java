package com.why.happy_movie.myactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.MApp;
import com.why.happy_movie.activity.ResetPwdActivity;
import com.why.happy_movie.bean.MyUpdate;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.bean.YongHuBean;
import com.why.happy_movie.presenter.MyUpdatePresenter;
import com.why.happy_movie.presenter.UserInfoPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;
import com.why.happy_movie.utils.util.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener, DataCall<Result<YongHuBean>> {


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
    YongHuBean yongHuBean = new YongHuBean();
    int userId = 1771;
    String sessionId = "15482908826721771";
    @BindView(R.id.my_reset)
    ImageView myReset;
    private int index = 0;// 记录单选对话框的下标
    private MyUpdatePresenter myUpdatePresenter;
    private String newsex;
    private int sex;
    private String email;
    private String nickName;
    private List<UserBean> userBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        myReturn.setOnClickListener(this);

        userBeans = MApp.userBeanDao.loadAll();
        userId = userBeans.get(0).getUserId();
        sessionId = userBeans.get(0).getSessionId();

        UserInfoPresenter userInfoPresenter = new UserInfoPresenter(this);
        userInfoPresenter.reqeust(userId, sessionId);
        myReset.setOnClickListener(this);
        //修改用户信息
        myupdate();
    }

    private void myupdate() {
        my_name.setOnClickListener(this);
        my_sex.setOnClickListener(this);
        emaile.setOnClickListener(this);
        myUpdatePresenter = new MyUpdatePresenter(new MyUpdateCall());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_return:
                finish();
                break;
            case R.id.my_reset:
                Intent intent = new Intent(MessageActivity.this, ResetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.my_name:
                final EditText editText = new EditText(this);
                editText.setText(yongHuBean.getNickName());
                AlertDialog builder1 = new AlertDialog.Builder(this).
                        setTitle("修改用户名称")
                        .setView(editText)//设置输入框
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String myname = editText.getText().toString().trim();
                                userBeans.get(0).setNickName(myname);
                                my_name.setText(myname);
                                myUpdatePresenter.reqeust(userId, sessionId, myname, yongHuBean.getSex(), email);
                            }
                        }).setNegativeButton("取消", null).create();
                builder1.show();
                break;
            case R.id.my_sex:
                int sex1 = yongHuBean.getSex();
                int sexid = 0;
                if (sex1 == 1) {
                    sexid = 0;
                } else {
                    sexid = 1;
                }
                final String[] sex = {"男", "女"};
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setSingleChoiceItems(sex, sexid, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        index = which;
                    }
                });
                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        newsex=yongHuBean.getSex();
                        newsex = sex[index];
                        my_sex.setText(newsex);
                        /*Toast.makeText(getBaseContext(), "选择的性别:" + sex[index], Toast.LENGTH_SHORT)
                                .show();*/
                        if (newsex.equals("男")) {
                            myUpdatePresenter.reqeust(userId, sessionId, nickName, 1, email);
                        } else {
                            myUpdatePresenter.reqeust(userId, sessionId, nickName, 2, email);

                        }
                    }
                });
                // 显示对话框
                builder2.show();

                break;
            case R.id.emaile:
                final EditText editemail = new EditText(this);
                editemail.setText(yongHuBean.getEmail());
                AlertDialog builder3 = new AlertDialog.Builder(this)
                        .setTitle("修改邮箱")
                        .setView(editemail)//设置输入框
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String trim = editemail.getText().toString().trim();
                                boolean email = isEmail(trim + "");
                                if (email) {
                                    emaile.setText(trim);
                                    myUpdatePresenter.reqeust(userId, sessionId, nickName, yongHuBean.getSex(), trim);
                                } else {
                                    UIUtils.showToastSafe("请输入正确的邮箱");
                                    return;
                                }
                            }
                        }).setNegativeButton("取消", null).create();
                builder3.show();


                break;

        }
    }

    @Override
    public void success(Result<YongHuBean> data) {
        yongHuBean = data.getResult();
        my_head.setImageURI(yongHuBean.getHeadPic());
        my_name.setText(yongHuBean.getNickName());
        int sex = yongHuBean.getSex();
        my_sex.setText(sex == 1 ? "男" : "女");
        long birthday = yongHuBean.getBirthday();
        Date date = new Date(birthday);
        SimpleDateFormat fomat2 = new SimpleDateFormat("yyyy-MM-dd");
        String mydate = fomat2.format(date);
        my_data.setText(mydate);
        my_phone.setText(yongHuBean.getPhone());
        emaile.setText(yongHuBean.getEmail());

        nickName = yongHuBean.getNickName();
        email = yongHuBean.getEmail();


        myUpdatePresenter.reqeust(userId, sessionId, nickName, sex, email);
    }

    @Override
    public void fail(ApiException e) {

    }

    private class MyUpdateCall implements DataCall<Result<MyUpdate>> {
        @Override
        public void success(Result<MyUpdate> data) {
            Toast.makeText(getBaseContext(),data.getMessage(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }


    //正则邮箱
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }

}
