package com.why.happy_movie.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.why.happy_movie.MApp;
import com.why.happy_movie.adapter.MyRccordAdapter;
import com.why.happy_movie.bean.MyPay;
import com.why.happy_movie.bean.PayBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.core.Interfacea;
import com.why.happy_movie.presenter.MyPayPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.NetWorkManager;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 购票记录
 */
public class MyRccordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.w_pay)
    RadioButton wPay;
    @BindView(R.id.ok_pay)
    RadioButton okPay;
    @BindView(R.id.rg_yy)
    RadioGroup rgYy;
    @BindView(R.id.pay_return)
    ImageView payReturn;
    @BindView(R.id.pay_recycler)
    RecyclerView payRecycler;
    private MyRccordAdapter myRccordAdapter;
    private int userId;
    private String sessionId;
    private LinearLayoutManager linearLayoutManager;
    private MyPayPresenter myPayPresenter;
    private List<MyPay> result;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rccord);
        ButterKnife.bind(this);

        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");//第二个参数为APPID
        api.registerApp("wxb3852e6a6b7d9516");

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if (userBeans.size() > 0) {
            userId = userBeans.get(0).getUserId();
            sessionId = userBeans.get(0).getSessionId();


        }
        wPay.setOnClickListener(this);
        okPay.setOnClickListener(this);
        payReturn.setOnClickListener(this);
        myPayPresenter = new MyPayPresenter(new MyPayCall());
        myPayPresenter.reqeust(userId,sessionId,1,5,1);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        payRecycler.setLayoutManager(linearLayoutManager);
        myRccordAdapter = new MyRccordAdapter(this);
        payRecycler.setAdapter(myRccordAdapter);
        myRccordAdapter.getZhifu(new MyRccordAdapter.ZhiFu() {
            @Override
            public void onZhiFu(int possion) {
                final MyPay myPay = result.get(possion);
                LayoutInflater factory = LayoutInflater.from(MyRccordActivity.this);
                View view = factory.inflate(R.layout.popu_stat, null);

                final Dialog dialog = new Dialog(MyRccordActivity.this,R.style.DialogTheme);
                dialog.setContentView(view);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                Window dialogWindow = dialog.getWindow();
                WindowManager m = getWindow().getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                //p.height = (int) (d.getHeight() * 0.15); // 高度设置为屏幕的0.6，根据实际情况调整
                p.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65，根据实际情况调整
                dialogWindow.setAttributes(p);
                dialog.show();

                TextView textView = view.findViewById(R.id.chuangjian);
                textView.setText("微信支付"+myPay.getPrice()+"元");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Interfacea interfacea = NetWorkManager.getInstance().create(Interfacea.class);
                        interfacea.pay(userId,sessionId,1,myPay.getOrderId()).subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<PayBean>() {
                                    @Override
                                    public void accept(PayBean payBean) throws Exception {
                                        PayReq req = new PayReq();
                                        req.appId = payBean.getAppId();
                                        req.partnerId = payBean.getPartnerId();
                                        req.prepayId = payBean.getPrepayId();
                                        req.nonceStr = payBean.getNonceStr();
                                        req.timeStamp = payBean.getTimeStamp();
                                        req.packageValue = payBean.getPackageValue();
                                        req.sign = payBean.getSign();
                                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                        //3.调用微信支付sdk支付方法
                                        api.sendReq(req);
                                    }
                                });
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.w_pay:
                wPay.setTextColor(Color.WHITE);
                okPay.setTextColor(Color.BLACK);
                payRecycler.setLayoutManager(linearLayoutManager);
                myRccordAdapter = new MyRccordAdapter(this);
                payRecycler.setAdapter(myRccordAdapter);
                myPayPresenter.reqeust(userId,sessionId,1,5,1);

                break;
            case R.id.ok_pay:
                wPay.setTextColor(Color.BLACK);
                okPay.setTextColor(Color.WHITE);
                payRecycler.setLayoutManager(linearLayoutManager);
                myRccordAdapter = new MyRccordAdapter(this);
                payRecycler.setAdapter(myRccordAdapter);
                myPayPresenter.reqeust(userId,sessionId,1,5,2);

                break;
            case R.id.pay_return:
                finish();
                break;

        }
    }

    private class MyPayCall implements DataCall<Result<List<MyPay>>> {
        @Override
        public void success(Result<List<MyPay>> data) {
            result = data.getResult();
            myRccordAdapter.addAll(result);
            myRccordAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
