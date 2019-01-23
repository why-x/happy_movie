package com.why.happy_movie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.presenter.LoginPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;
import com.why.happy_movie.utils.util.EncryptUtil;

public class LoginActivity extends AppCompatActivity implements DataCall<Result> {

    private EditText ed_login_number;
    private EditText ed_login_password;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);

        ed_login_number = findViewById(R.id.ed_login_number);
        ed_login_password = findViewById(R.id.ed_login_password);
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = ed_login_number.getText().toString().trim();
                String trim1 = ed_login_password.getText().toString().trim();
                String encrypt = EncryptUtil.encrypt(trim1);
                loginPresenter.reqeust(trim,encrypt);
            }
        });




        TextView zhuce = findViewById(R.id.btn_regirect_tiao);
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void success(Result data) {
        Toast.makeText(this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void fail(ApiException e) {

    }
}
