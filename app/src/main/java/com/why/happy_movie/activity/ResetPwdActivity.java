package com.why.happy_movie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.why.happy_movie.MApp;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.presenter.UpdatePwdPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;
import com.why.happy_movie.utils.util.EncryptUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPwdActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.my_pass)
    EditText myPhone;
    @BindView(R.id.re_pwd)
    EditText rePwd;
    @BindView(R.id.new_pwd)
    EditText newPwd;
    @BindView(R.id.reset_return)
    ImageView resetReturn;
    @BindView(R.id.affirm)
    Button affirm;
    private List<UserBean> userBeans;
    private int userId;
    private String sessionId;
    private UpdatePwdPresenter updatePwdPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reset_pwd);
        ButterKnife.bind(this);
        userBeans = MApp.userBeanDao.loadAll();
        userId = userBeans.get(0).getUserId();
        sessionId = userBeans.get(0).getSessionId();

        resetReturn.setOnClickListener(this);


        affirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_return:
                finish();
                break;
            case R.id.affirm:
                updatePwdPresenter = new UpdatePwdPresenter(new UpdateCall());
                String oldpass = myPhone.getText().toString().trim();

                String nrepwd = rePwd.getText().toString().trim();
                String nrepwd2 = newPwd.getText().toString().trim();
                String encrypt1 = EncryptUtil.encrypt(nrepwd);
                String encrypt2 = EncryptUtil.encrypt(nrepwd2);
                String oldpass2 = EncryptUtil.encrypt(oldpass);

                Log.i("mimamen","mima"+nrepwd+"      "+encrypt1+"hahah");
                updatePwdPresenter.reqeust(userId, sessionId, oldpass2, encrypt1, encrypt2);

                break;
        }
    }

    private class UpdateCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}
