package com.why.happy_movie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.presenter.RegisterPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;
import com.why.happy_movie.utils.util.EncryptUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements DataCall<Result> {

    private RegisterPresenter registerPresenter;
    private EditText nicheng;
    private EditText phone;
    private EditText pass;
    int boo=1;
    private EditText emaile;
    private EditText date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerPresenter = new RegisterPresenter(this);

        nicheng = findViewById(R.id.nicheng);
        RadioGroup radio_register = findViewById(R.id.radio_register);
        date = findViewById(R.id.date);
        phone = findViewById(R.id.phone);
        emaile = findViewById(R.id.emaile);
        pass = findViewById(R.id.pass);
        Button  btn_login2 = findViewById(R.id.btn_login2);
        btn_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nicheng1 = nicheng.getText().toString().trim();
                String phone1 = phone.getText().toString().trim();
                String pass1 = pass.getText().toString().trim();
                String encrypt = EncryptUtil.encrypt(pass1);
                String emaile1 = emaile.getText().toString().trim();
                String date1 = date.getText().toString().trim();
                if(phone1.equals("")||pass1.equals("")){
                    return;
                }
                registerPresenter.reqeust(nicheng1,phone1,encrypt,encrypt,boo+"",date1,emaile1);
            }
        });

        radio_register.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_boys:
                        boo=1;
                    break;
                    case R.id.btn_girl:
                        boo=2;
                        break;
                }
            }
        });

    }



    //正则表达式
    public static boolean isMatcherFinded(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    @Override
    public void success(Result data) {
        Toast.makeText(this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        if(data.getStatus().equals("0000")){
            finish();
        }
    }

    @Override
    public void fail(ApiException e) {

    }
}
