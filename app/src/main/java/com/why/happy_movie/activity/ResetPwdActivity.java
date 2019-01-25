package com.why.happy_movie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPwdActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.my_phone)
    TextView myPhone;
    @BindView(R.id.re_pwd)
    EditText rePwd;
    @BindView(R.id.new_pwd)
    EditText newPwd;
    @BindView(R.id.reset_return)
    ImageView resetReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reset_pwd);
        ButterKnife.bind(this);


        resetReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_return:
                finish();
                break;
        }
    }
}
