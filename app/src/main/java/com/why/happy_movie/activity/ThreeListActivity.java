package com.why.happy_movie.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapView;
import com.bw.movie.R;
import com.why.happy_movie.MApp;
import com.why.happy_movie.adapter.ThreeListAdapter;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.presenter.ComingSoonMoviePresenter;
import com.why.happy_movie.presenter.HotMoviePresenter;
import com.why.happy_movie.presenter.MyCanclePresenter;
import com.why.happy_movie.presenter.MyLovePresenter;
import com.why.happy_movie.presenter.ReleaseMoviePresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

public class ThreeListActivity extends AppCompatActivity  implements DataCall<Result<List<MovieListBean>>> {

    private EditText et_sou;
    private TextView tv_sou;
    private RadioButton rb_one;
    private RadioButton rb_two;
    private RadioButton rb_three;
    int userId =1771;
    String sessionId="15482908826721771";
    int page=1;
    int count=10;
    List<MovieListBean> remendianyinglist = new ArrayList<>();
    private ThreeListAdapter threeListAdapter;
    private ReleaseMoviePresenter releaseMoviePresenter;
    private HotMoviePresenter hotMoviePresenter;
    private ComingSoonMoviePresenter comingSoonMoviePresenter;
    private LocationClient mLocationClient =null;
    private TextView addre;
    private MapView mMapView = null;
    private MyLocationListener myListener = new MyLocationListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_list);

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if(userBeans.size()>0){
            userId=  userBeans.get(0).getUserId();
            sessionId =  userBeans.get(0).getSessionId();
        }

        hotMoviePresenter = new HotMoviePresenter(this);
        hotMoviePresenter.reqeust(userId,sessionId,page,count);
        releaseMoviePresenter = new ReleaseMoviePresenter(new Zhengzai());
        comingSoonMoviePresenter = new ComingSoonMoviePresenter(new Jjiang());

        addre = findViewById(R.id.addre);
        mLocationClient = new LocationClient(getApplicationContext());
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


        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView sou = findViewById(R.id.sou);
        et_sou = findViewById(R.id.et_sou);
        tv_sou = findViewById(R.id.tv_sou);
        sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_sou.setVisibility(View.VISIBLE);
                tv_sou.setVisibility(View.VISIBLE);
            }
        });
        tv_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_sou.setVisibility(View.GONE);
                tv_sou.setVisibility(View.GONE);
            }
        });

        rb_one = findViewById(R.id.rb_one);
        rb_two = findViewById(R.id.rb_two);
        rb_three = findViewById(R.id.rb_three);
        RadioGroup radioGroup = findViewById(R.id.rg_aa);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        rb_one.setTextColor(Color.WHITE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_one:
                        rb_one.setTextColor(Color.WHITE);
                        rb_two.setTextColor(Color.BLACK);
                        rb_three.setTextColor(Color.BLACK);
                        hotMoviePresenter.reqeust(userId,sessionId,page,count);
                        break;
                    case R.id.rb_two:
                        rb_two.setTextColor(Color.WHITE);
                        rb_one.setTextColor(Color.BLACK);
                        rb_three.setTextColor(Color.BLACK);
                        releaseMoviePresenter.reqeust(userId,sessionId,page,count);
                        break;
                    case R.id.rb_three:
                        rb_three.setTextColor(Color.WHITE);
                        rb_two.setTextColor(Color.BLACK);
                        rb_one.setTextColor(Color.BLACK);
                        comingSoonMoviePresenter.reqeust(userId,sessionId,page,count);
                        break;
                }
            }
        });


        RecyclerView recyclerView = findViewById(R.id.list);
        threeListAdapter = new ThreeListAdapter(this, remendianyinglist);
        threeListAdapter.xihuan(new ThreeListAdapter.MyLove() {
            @Override
            public void onLove(int possion) {
                MovieListBean movieListBean = remendianyinglist.get(possion);
                int id = movieListBean.getId();
                MyLovePresenter myLovePresenter = new MyLovePresenter(new Xihuan());
                myLovePresenter.reqeust(userId,sessionId,id);
            }

            @Override
            public void onCancle(int possion) {
                MovieListBean movieListBean = remendianyinglist.get(possion);
                int id = movieListBean.getId();
                MyCanclePresenter myCanclePresenter = new MyCanclePresenter(new Cancle());
                myCanclePresenter.reqeust(userId,sessionId,id);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(threeListAdapter);

    }

    @Override
    public void success(Result<List<MovieListBean>> data) {
        remendianyinglist.clear();
        remendianyinglist.addAll(data.getResult());
        threeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(ApiException e) {

    }

    /**
     * 正在热映
     */
    class Zhengzai implements DataCall<Result<List<MovieListBean>>> {

        @Override
        public void success(Result<List<MovieListBean>> data) {
            remendianyinglist.clear();
            remendianyinglist.addAll(data.getResult());
            threeListAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 即将上映
     */
    class Jjiang implements DataCall<Result<List<MovieListBean>>> {

        @Override
        public void success(Result<List<MovieListBean>> data) {
            remendianyinglist.clear();
            remendianyinglist.addAll(data.getResult());
            threeListAdapter.notifyDataSetChanged();
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
            double weidu = location.getLongitude();
            double jingdu = location.getLatitude();
            addre.setText(addr);
        }
    }

    class Xihuan implements DataCall<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(ThreeListActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Cancle implements DataCall<Result> {


        @Override
        public void success(Result data) {
            Toast.makeText(ThreeListActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
