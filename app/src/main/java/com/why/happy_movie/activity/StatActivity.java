package com.why.happy_movie.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.why.happy_movie.MApp;
import com.why.happy_movie.bean.PayBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.core.Interfacea;
import com.why.happy_movie.presenter.BuyMovieTicketPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.NetWorkManager;
import com.why.happy_movie.utils.exception.ApiException;
import com.why.happy_movie.utils.util.MD5Utils;
import com.why.happy_movie.view.SeatTable;

import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StatActivity extends BaseActivity {

    private TextView yingcheng;
    private TextView txt_address;
    private TextView moviename;
    private TextView txt_shijian;
    private TextView tct_ting;
    public SeatTable seatTableView;
    private double price;
    double zongprice=0;
    int mount=0;
    private TextView txtprice;
    private ImageView xiadan;
    private int paiqiid;
    private int userId;
    private String sessionId;
    public static Activity activity;
    public static boolean zai=false;
    private IWXAPI api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        activity=this;
        zai=true;
        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");//第二个参数为APPID
        api.registerApp("wxb3852e6a6b7d9516");

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if(userBeans.size()>0){
            userId = userBeans.get(0).getUserId();
            sessionId = userBeans.get(0).getSessionId();

        }

        yingcheng = findViewById(R.id.yingcheng);
        txt_address = findViewById(R.id.address);
        moviename = findViewById(R.id.moviename);
        txt_shijian = findViewById(R.id.shijian);
        tct_ting = findViewById(R.id.ting);
        txtprice = findViewById(R.id.price);
        ImageView  back = findViewById(R.id.back);
        xiadan = findViewById(R.id.xiadan);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        paiqiid = intent.getIntExtra("paiqiid", 0);
        String cinemaname = intent.getStringExtra("cinemaname");
        String address = intent.getStringExtra("address");
        String movienamem = intent.getStringExtra("movienamem");
        String shijian = intent.getStringExtra("shijian");
        String ting = intent.getStringExtra("ting");
        price = intent.getDoubleExtra("price", 0d);
        yingcheng.setText(cinemaname);
        txt_address.setText(address);
        moviename.setText(movienamem);
        txt_shijian.setText(shijian);
        tct_ting.setText(ting);


        seatTableView =  findViewById(R.id.seatView);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(5);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                zongprice+=price;
                mount++;
                DecimalFormat df2 = new DecimalFormat("#0.00");
                String format = df2.format(zongprice);
                txtprice.setText(format+"");
            }

            @Override
            public void unCheck(int row, int column) {
                zongprice-=price;
                mount--;
                DecimalFormat df2 = new DecimalFormat("#0.00");
                String format = df2.format(zongprice);
                txtprice.setText(format+"");
            }


        });
        seatTableView.setData(10,15);

        xiadan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mount==0){
                    Toast.makeText(StatActivity.this, "请选坐下单……", Toast.LENGTH_SHORT).show();
                    return;
                }

                LayoutInflater factory = LayoutInflater.from(StatActivity.this);
                View view = factory.inflate(R.layout.popu_stat, null);

                final Dialog dialog = new Dialog(StatActivity.this,R.style.DialogTheme);
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
                textView.setText("微信支付"+zongprice+"元");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean zai = MApp.sharedPreferences.getBoolean("zai", false);
                        if(!zai){
                            Intent intent1 = new Intent(StatActivity.this,LoginActivity.class);
                            startActivity(intent1);
                            Toast.makeText(StatActivity.this, "请先登录……", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
                        if(userBeans.size()>0){
                            userId = userBeans.get(0).getUserId();
                            sessionId = userBeans.get(0).getSessionId();

                        }
                        /**
                         * 创建订单
                         */
                        BuyMovieTicketPresenter buyMovieTicketPresenter = new BuyMovieTicketPresenter(new  XiaDan());
                        String md5=userId+""+paiqiid+""+mount+"movie";
                        String s = MD5Utils.md5(md5);
                        buyMovieTicketPresenter.reqeust(userId,sessionId,paiqiid,mount,s);

                    }
                });
                ImageView back  = view.findViewById(R.id.back);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    private class XiaDan implements DataCall<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(StatActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            if(data.getStatus().equals("0000")){
                String orderId = data.getOrderId();
                Interfacea interfacea = NetWorkManager.getInstance().create(Interfacea.class);
                interfacea.pay(userId,sessionId,1,orderId).subscribeOn(Schedulers.newThread())
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
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class PayClass implements DataCall<Result<PayBean>> {
        @Override
        public void success(Result<PayBean> data) {

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
