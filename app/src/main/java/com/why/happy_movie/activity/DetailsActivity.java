package com.why.happy_movie.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bw.movie.R;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.MApp;
import com.why.happy_movie.adapter.JuZhaoAdapter;
import com.why.happy_movie.bean.MovieDBean;
import com.why.happy_movie.bean.MoviesDBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.presenter.MovieDPresenter;
import com.why.happy_movie.presenter.MoviesDPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener,DataCall<Result<MovieDBean>> {

    @BindView(R.id.d_love)
    CheckBox dLove;
    @BindView(R.id.details_movie)
    SimpleDraweeView detailsMovie;
    @BindView(R.id.d_details)
    Button dDetails;
    @BindView(R.id.d_advance)
    Button dAdvance;
    @BindView(R.id.d_photo)
    Button dPhoto;
    @BindView(R.id.d_comment)
    Button dComment;
    @BindView(R.id.d_return)
    ImageView dReturn;
    @BindView(R.id.d_buy)
    Button dBuy;
    @BindView(R.id.d_name)
    TextView d_name;
    int userId =1771;
    String sessionId="15482908826721771";
    private LinearLayout lll;
    MoviesDBean moviesDBean = new MoviesDBean();
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        dDetails.setOnClickListener(this);
        dReturn.setOnClickListener(this);
        dPhoto.setOnClickListener(this);
        dAdvance.setOnClickListener(this);

        lll = findViewById(R.id.lll);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);

        MovieDPresenter movieDPresenter = new MovieDPresenter(this);
        movieDPresenter.reqeust(userId,sessionId,id);

        MoviesDPresenter moviesDPresenter = new MoviesDPresenter(new DianYing());
        moviesDPresenter.reqeust(userId,sessionId,id);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.d_details:
                View inflate = View.inflate(this, R.layout.popu_movie, null);
                popupWindow = new PopupWindow(inflate,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll,0,0,0);
                SimpleDraweeView iv2 = inflate.findViewById(R.id.iv2);
                iv2.setImageURI(moviesDBean.getImageUrl());
                TextView leixing = inflate.findViewById(R.id.leixing);
                leixing.setText("类型："+moviesDBean.getMovieTypes());
                TextView daoyan = inflate.findViewById(R.id.daoyan);
                daoyan.setText("导演："+moviesDBean.getDirector());
                ImageView dowm = inflate.findViewById(R.id.down);
                TextView shichang = inflate.findViewById(R.id.shichang);
                shichang.setText("时长："+moviesDBean.getDuration());
                TextView chandi = inflate.findViewById(R.id.chandi);
                TextView juqing = inflate.findViewById(R.id.juqing);
                chandi.setText("产地："+moviesDBean.getPlaceOrigin());
                juqing.setText(moviesDBean.getSummary());
                dowm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.d_return:
                finish();
                break;
            case R.id.d_photo:
                View inflate1 = View.inflate(this, R.layout.popu_photo, null);
                popupWindow = new PopupWindow(inflate1,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll,0,0,0);
                ImageView back = inflate1.findViewById(R.id.back);
                RecyclerView recyclerView = inflate1.findViewById(R.id.list3);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                List<String> posterList = moviesDBean.getPosterList();
                JuZhaoAdapter juZhaoAdapter = new JuZhaoAdapter(this,posterList);
                recyclerView.setAdapter(juZhaoAdapter);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.d_advance:
                View inflate2 = View.inflate(this, R.layout.popu_pian, null);
                popupWindow = new PopupWindow(inflate2,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll,0,0,0);
                ImageView back1 = inflate2.findViewById(R.id.back);
                List<MoviesDBean.ShortFilmListBean> shortFilmList = moviesDBean.getShortFilmList();
                MoviesDBean.ShortFilmListBean shortFilmListBean = shortFilmList.get(0);



                back1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
                
        }
    }

    @Override
    public void success(Result<MovieDBean> data) {
        MovieDBean result = data.getResult();
        detailsMovie.setImageURI(result.getImageUrl());
        d_name.setText(result.getName());
    }

    @Override
    public void fail(ApiException e) {

    }

    class DianYing implements DataCall<Result<MoviesDBean>> {

        @Override
        public void success(Result<MoviesDBean> data) {
            moviesDBean = data.getResult();
           // Toast.makeText(DetailsActivity.this, "11"+moviesDBean.getImageUrl(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
