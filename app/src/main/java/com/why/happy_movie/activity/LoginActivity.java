package com.why.happy_movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bw.movie.R;
import com.why.happy_movie.bean.LoginBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.presenter.LoginPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;
import com.why.happy_movie.utils.util.EncryptUtil;

public class LoginActivity extends AppCompatActivity implements DataCall<Result<LoginBean>> {

    private EditText ed_login_number;
    private EditText ed_login_password;
    private LoginPresenter loginPresenter;
    private CheckBox save_pwd;
    private SharedPreferences sp;
    private String trim;
    private String trim1;
    private ImageView btn_ying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);

        sp = getSharedPreferences("sp",MODE_PRIVATE);

        btn_ying = findViewById(R.id.btn_ying);
        ed_login_number = findViewById(R.id.ed_login_number);
        ed_login_password = findViewById(R.id.ed_login_password);
        Button btn_login = findViewById(R.id.btn_login);
        save_pwd = findViewById(R.id.save_pwd);

        if(sp.getBoolean("jizhu",false)){
            String phone = sp.getString("phone","");
            ed_login_number.setText(phone);
            String pass = sp.getString("pass", "");
            ed_login_password.setText(pass);
            save_pwd.setChecked(true);
        }

        btn_ying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHide(ed_login_password);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trim = ed_login_number.getText().toString().trim();
                trim1 = ed_login_password.getText().toString().trim();
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
        if(data.getStatus().equals("0000")){

            SharedPreferences.Editor edit = sp.edit();
            boolean checked = save_pwd.isChecked();
            edit.putBoolean("jizhu",checked);
            edit.putString("phone",trim);
            edit.putString("pass",trim1);
            edit.commit();

            /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);*/
            finish();
        }

    }

    @Override
    public void fail(ApiException e) {

    }

    /**
     * 密码显示或隐藏 （切换）
     */
    private void showOrHide(EditText etPassword){
        //记住光标开始的位置
        int pos = etPassword.getSelectionStart();
        if(etPassword.getInputType()!= (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)){//隐藏密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else{//显示密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        etPassword.setSelection(pos);

    }
}
