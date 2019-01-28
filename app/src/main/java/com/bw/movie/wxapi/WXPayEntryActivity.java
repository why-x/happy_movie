package com.bw.movie.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.why.happy_movie.activity.MyRccordActivity;
import com.why.happy_movie.activity.StatActivity;
import com.why.happy_movie.utils.util.LogUtils;

/**
 * @author happy_movie
 * @date 2019/1/27 11:35
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    private TextView payResult;
    private ImageView iv_faile;
    private ImageView iv_success;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        LogUtils.e("com.bw.movie.wxapi包哈哈哈啊");

        payResult = findViewById(R.id.pay_result);
        iv_faile = findViewById(R.id.iv_faile);
        iv_success = findViewById(R.id.iv_success);
        Button  chakan = findViewById(R.id.chakan);
        chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WXPayEntryActivity.this,MyRccordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");
        api.handleIntent(getIntent(), this);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //支付成功后的逻辑
                    iv_success.setVisibility(View.VISIBLE);
                    result = "微信支付成功";
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    iv_faile.setVisibility(View.VISIBLE);
                    result = "微信支付失败";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    iv_faile.setVisibility(View.VISIBLE);
                    result = "微信支付取消";
                    break;
                default:
                    iv_faile.setVisibility(View.VISIBLE);
                    result = "微信支付未知异常";
                    break;
            }
            payResult.setText(result);
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
       if(StatActivity.zai){
           StatActivity.activity.finish();
           StatActivity.zai=false;
       }
    }
}
