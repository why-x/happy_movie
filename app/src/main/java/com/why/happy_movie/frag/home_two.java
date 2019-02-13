package com.why.happy_movie.frag;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.why.happy_movie.MApp;
import com.why.happy_movie.activity.LoginActivity;
import com.why.happy_movie.activity.ThreeListActivity;
import com.why.happy_movie.adapter.CinemasAdapter;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.bean.YingYuanBean;
import com.why.happy_movie.presenter.MyLikePresenter;
import com.why.happy_movie.presenter.MyNoLikePresenter;
import com.why.happy_movie.presenter.NearCinemasPresenter;
import com.why.happy_movie.presenter.RecommendCinemasPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author happy_movie
 * @date 2019/1/22 20:27
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class home_two extends Fragment implements DataCall<Result<List<YingYuanBean>>> {

    private RadioButton yy_one;
    private RadioButton yy_two;
    int userId =1771;
    String sessionId="15482908826721771";
    int page=1;
    int count=10;
    List<YingYuanBean> yingYuanBeanList = new ArrayList<>();
    private CinemasAdapter cinemasAdapter;
    private EditText et_sou;
    private TextView tv_sou;
    private RecommendCinemasPresenter recommendCinemasPresenter;
    private NearCinemasPresenter nearCinemasPresenter;
    private TextView addre;
    private LocationClient mLocationClient;
    private MyLocationListener myListener = new MyLocationListener();
    private double weidu;
    private double jingdu;
    private LinearLayout ll5;
    private ViewGroup.LayoutParams layoutParams;
    private ViewGroup.LayoutParams layoutParams1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_two,container,false);

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if(userBeans.size()>0){
            userId = userBeans.get(0).getUserId();
           sessionId = userBeans.get(0).getSessionId();
        }

        recommendCinemasPresenter = new RecommendCinemasPresenter(this);
        nearCinemasPresenter = new NearCinemasPresenter(new Fujin());


        addre = view.findViewById(R.id.addre);
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        ImageView sou = view.findViewById(R.id.sou);
        et_sou = view.findViewById(R.id.et_sou);
        tv_sou = view.findViewById(R.id.tv_sou);
        ll5 = view.findViewById(R.id.ll5);
        //这是隐藏进去的动画
        layoutParams = ll5.getLayoutParams();
        layoutParams1 = sou.getLayoutParams();

        float aa =  layoutParams.width-80;
        ObjectAnimator animator = ObjectAnimator.ofFloat(ll5, "translationX", 30f, aa);
        animator.setDuration(10);
        animator.start();
        sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float aa =  home_two.this.layoutParams.width-80;
                ObjectAnimator animator = ObjectAnimator.ofFloat(ll5, "translationX", aa, 30f);
                animator.setDuration(1000);
                animator.start();
            }
        });
        tv_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这是隐藏进去的动画
                float aa =  home_two.this.layoutParams.width-80;
                ObjectAnimator animator = ObjectAnimator.ofFloat(ll5, "translationX", 30f, aa);
                animator.setDuration(1000);
                animator.start();
            }
        });

        yy_one = view.findViewById(R.id.yy_one);
        yy_two = view.findViewById(R.id.yy_two);
        RadioGroup radioGroup = view.findViewById(R.id.rg_yy);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        yy_one.setTextColor(Color.WHITE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.yy_one:
                        yy_one.setTextColor(Color.WHITE);
                        yy_two.setTextColor(Color.BLACK);
                        recommendCinemasPresenter.reqeust(userId,sessionId,page,count);
                        break;
                    case R.id.yy_two:
                        yy_one.setTextColor(Color.BLACK);
                        yy_two.setTextColor(Color.WHITE);
                        nearCinemasPresenter.reqeust(userId,sessionId,jingdu+"",weidu+"",page,count);
                        break;
                }
            }
        });

        recommendCinemasPresenter.reqeust(userId,sessionId,page,count);

        RecyclerView recyclerView = view.findViewById(R.id.list2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cinemasAdapter = new CinemasAdapter(getContext(), yingYuanBeanList);
        recyclerView.setAdapter(cinemasAdapter);
        cinemasAdapter.getLike(new CinemasAdapter.Like() {
            @Override
            public void onSuccess(int possion) {
                boolean zai = MApp.sharedPreferences.getBoolean("zai", false);
                if(!zai){
                    Intent intent = new Intent(getContext(),LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "请先登录……", Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = yingYuanBeanList.get(possion).getId();
                MyLikePresenter myLikePresenter = new MyLikePresenter(new MyLike());
                myLikePresenter.reqeust(userId,sessionId,id);
            }

            @Override
            public void onFaile(int possion) {
                boolean zai = MApp.sharedPreferences.getBoolean("zai", false);
                if(!zai){
                    Intent intent = new Intent(getContext(),LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "请先登录……", Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = yingYuanBeanList.get(possion).getId();
                MyNoLikePresenter myNoLikePresenter = new MyNoLikePresenter(new NoLike());
                myNoLikePresenter.reqeust(userId,sessionId,id);
            }
        });

        return view;
    }

    @Override
    public void success(Result<List<YingYuanBean>> data) {
        List<YingYuanBean> result = data.getResult();
        yingYuanBeanList.clear();
        yingYuanBeanList.addAll(result);
        cinemasAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(ApiException e) {

    }

    class Fujin implements DataCall<Result<List<YingYuanBean>>> {

        @Override
        public void success(Result<List<YingYuanBean>> data) {
            List<YingYuanBean> result = data.getResult();
            yingYuanBeanList.clear();
            yingYuanBeanList.addAll(result);
            cinemasAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getAddrStr();    //获取详细地址信息
            location.getAddress();
            weidu = location.getLongitude();
            jingdu = location.getLatitude();
            addre.setText(addr);
        }
    }


    private class MyLike implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(getContext(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class NoLike implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(getContext(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if(userBeans.size()>0){
            userId = userBeans.get(0).getUserId();
            sessionId = userBeans.get(0).getSessionId();
        }
        recommendCinemasPresenter = new RecommendCinemasPresenter(this);
        nearCinemasPresenter = new NearCinemasPresenter(new Fujin());
    }

    @Override
    public void onResume() {
        super.onResume();
        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if(userBeans.size()>0){
            userId = userBeans.get(0).getUserId();
            sessionId = userBeans.get(0).getSessionId();
        }
        recommendCinemasPresenter = new RecommendCinemasPresenter(this);
        nearCinemasPresenter = new NearCinemasPresenter(new Fujin());
    }
}
