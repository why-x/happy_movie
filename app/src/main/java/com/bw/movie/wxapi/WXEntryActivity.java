package com.bw.movie.wxapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.why.happy_movie.MApp;
import com.why.happy_movie.activity.LoginActivity;
import com.why.happy_movie.bean.LoginBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.bean.UserInfoBean;
import com.why.happy_movie.bean.WxLoginBean;
import com.why.happy_movie.presenter.LoginPresenter;
import com.why.happy_movie.presenter.WXLoginPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

/**
 * @author happy_movie
 * @date 2019/1/28 11:11
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注册API
        api = WXAPIFactory.createWXAPI(this,"wxb3852e6a6b7d9516");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.e("flag", "-----code:ok");
                if (resp instanceof SendAuth.Resp) {
                    SendAuth.Resp sendAuthResp = (SendAuth.Resp) resp;
                    String code = sendAuthResp.code;
                    getAccessToken(code);
                    // 发起登录请求
                    Log.e("flag", "-----code:" + sendAuthResp.code);
                    WXLoginPresenter wxLoginPresenter = new WXLoginPresenter(new WxLogin());
                    wxLoginPresenter.reqeust(sendAuthResp.code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (resp instanceof SendAuth.Resp) {}
                Log.e("flag", "-----授权取消:");
                Toast.makeText(this, "授权取消:", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                if (resp instanceof SendAuth.Resp) {}
                Log.e("flag", "-----授权失败:");
                Toast.makeText(this, "授权失败:", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }

    // 根据授权code  获取AccessToken
    public void getAccessToken(String code){

    }

    class WxLogin implements DataCall<Result<WxLoginBean>> {

        @Override
        public void success(Result<WxLoginBean> data) {
            WxLoginBean result = data.getResult();
            WxLoginBean.UserInfoBean userInfo = result.getUserInfo();
            UserBean userBean = new UserBean(1,result.getSessionId(),result.getUserId(),2018-8-8,userInfo.getLastLoginTime(),userInfo.getNickName(),"",userInfo.getSex(),userInfo.getHeadPic());
            MApp.userBeanDao.insertOrReplace(userBean);
            SharedPreferences.Editor edit = MApp.sharedPreferences.edit();
            edit.putBoolean("zai",true);
            edit.commit();
            finish();
            LoginActivity.activity.finish();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
