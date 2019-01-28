package com.why.happy_movie.activity;

import android.app.Activity;
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
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.why.happy_movie.MApp;
import com.why.happy_movie.bean.LoginBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.bean.UserInfoBean;
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
    private ImageView weixin_login;
    private IWXAPI api;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity=this;
        loginPresenter = new LoginPresenter(this);

        sp = getSharedPreferences("sp",MODE_PRIVATE);

        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this,"wxb3852e6a6b7d9516",true);
        //将应用的appid注册到微信
        api.registerApp("wxb3852e6a6b7d9516");


        btn_ying = findViewById(R.id.btn_ying);
        ed_login_number = findViewById(R.id.ed_login_number);
        ed_login_password = findViewById(R.id.ed_login_password);
        Button btn_login = findViewById(R.id.btn_login);
        save_pwd = findViewById(R.id.save_pwd);
        weixin_login = findViewById(R.id.weixin_login);

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

        /**
         * 微信登录
         */
        weixin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_微信登录"; // 自行填写
                api.sendReq(req);
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
    public void success(Result<LoginBean> data) {
        Toast.makeText(this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        if(data.getStatus().equals("0000")){
            LoginBean result = data.getResult();
            UserInfoBean userInfo = result.getUserInfo();
            UserBean userBean = new UserBean(1,result.getSessionId(),result.getUserId(),userInfo.getBirthday(),userInfo.getLastLoginTime(),userInfo.getNickName(),userInfo.getPhone(),userInfo.getSex(),userInfo.getHeadPic());
            MApp.userBeanDao.insertOrReplace(userBean);

            SharedPreferences.Editor edit = sp.edit();
            boolean checked = save_pwd.isChecked();
            edit.putBoolean("jizhu",checked);
            edit.putString("phone",trim);
            edit.putString("pass",trim1);
            edit.putBoolean("zai",true);
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
