package com.why.happy_movie.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.why.happy_movie.adapter.MyCommentAdapter;
import com.why.happy_movie.adapter.VideoAdapter;
import com.why.happy_movie.bean.MovieDBean;
import com.why.happy_movie.bean.MoviesDBean;
import com.why.happy_movie.bean.MyComment;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.presenter.CommentReply222Presenter;
import com.why.happy_movie.presenter.MovieCommertGreatPresenter;
import com.why.happy_movie.presenter.MovieCommertPresenter;
import com.why.happy_movie.presenter.MovieDPresenter;
import com.why.happy_movie.presenter.MoviesDPresenter;
import com.why.happy_movie.presenter.MyCommentPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayerStandard;

public class DetailsActivity extends BaseActivity implements View.OnClickListener, DataCall<Result<MovieDBean>> {

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

    private LinearLayout lll;
    MoviesDBean moviesDBean = new MoviesDBean();
    private PopupWindow popupWindow;
    private int id;
    String movieName;
    private MyCommentAdapter myCommentAdapter;
    private int userId;
    private String sessionId;
    private List<MyComment> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
        if (userBeans.size() > 0) {
            userId = userBeans.get(0).getUserId();
            sessionId = userBeans.get(0).getSessionId();
        }
        dDetails.setOnClickListener(this);
        dReturn.setOnClickListener(this);
        dPhoto.setOnClickListener(this);
        dAdvance.setOnClickListener(this);
        dBuy.setOnClickListener(this);
        dComment.setOnClickListener(this);
        lll = findViewById(R.id.lll);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);

        MovieDPresenter movieDPresenter = new MovieDPresenter(this);
        movieDPresenter.reqeust(userId, sessionId, id);

        MoviesDPresenter moviesDPresenter = new MoviesDPresenter(new DianYing());
        moviesDPresenter.reqeust(userId, sessionId, id);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.d_details:
                View inflate = View.inflate(this, R.layout.popu_movie, null);
                popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                SimpleDraweeView iv2 = inflate.findViewById(R.id.iv2);
                iv2.setImageURI(moviesDBean.getImageUrl());
                TextView leixing = inflate.findViewById(R.id.leixing);
                leixing.setText("类型：" + moviesDBean.getMovieTypes());
                TextView daoyan = inflate.findViewById(R.id.daoyan);
                daoyan.setText("导演：" + moviesDBean.getDirector());
                ImageView dowm = inflate.findViewById(R.id.down);
                TextView shichang = inflate.findViewById(R.id.shichang);
                shichang.setText("时长：" + moviesDBean.getDuration());
                TextView chandi = inflate.findViewById(R.id.chandi);
                TextView juqing = inflate.findViewById(R.id.juqing);
                chandi.setText("产地：" + moviesDBean.getPlaceOrigin());
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
                popupWindow = new PopupWindow(inflate1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                ImageView back = inflate1.findViewById(R.id.back);
                RecyclerView recyclerView = inflate1.findViewById(R.id.list3);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                List<String> posterList = moviesDBean.getPosterList();
                JuZhaoAdapter juZhaoAdapter = new JuZhaoAdapter(this, posterList);
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
                popupWindow = new PopupWindow(inflate2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                ImageView back1 = inflate2.findViewById(R.id.back);
                List<MoviesDBean.ShortFilmListBean> shortFilmList = moviesDBean.getShortFilmList();
                RecyclerView list4 = inflate2.findViewById(R.id.list4);
                list4.setLayoutManager(new LinearLayoutManager(this));
                VideoAdapter videoAdapter = new VideoAdapter(this, shortFilmList);
                list4.setAdapter(videoAdapter);

                back1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        JZVideoPlayerStandard.releaseAllVideos();
                    }
                });
                break;
            case R.id.d_buy:
                Intent intent = new Intent(this, BuyCinemaActivity.class);
                intent.putExtra("movieId", id);
                intent.putExtra("name", movieName);
                startActivity(intent);
                break;
            case R.id.d_comment:
                View inflate3 = View.inflate(this, R.layout.popu_advance, null);
                final LinearLayout ll4 = inflate3.findViewById(R.id.ll4);
                final LinearLayout ll8 = inflate3.findViewById(R.id.ll8);
                EditText  et_pl2 = inflate3.findViewById(R.id.et_pl2);
                final Button huifu  = inflate3.findViewById(R.id.huifu);
                ll4.setVisibility(View.GONE);
                final EditText et_pl  = inflate3.findViewById(R.id.et_pl);
                Button fabiaopl = inflate3.findViewById(R.id.fabiaopl);
                fabiaopl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trim = et_pl.getText().toString().trim();
                        if(trim.equals("")){
                            return;
                        }
                        MovieCommertPresenter movieCommertPresenter = new MovieCommertPresenter(new Fpl());
                        movieCommertPresenter.reqeust(userId,sessionId,id,trim);
                    }
                });
                final RecyclerView comrecycler = inflate3.findViewById(R.id.comment_recycler);
                ImageView  publish = inflate3.findViewById(R.id.publish);
                publish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        comrecycler.setVisibility(View.GONE);
                        ll4.setVisibility(View.VISIBLE);
                    }
                });
                final PopupWindow popupWindow = new PopupWindow(inflate3, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(lll, 0, 0, 0);
                ImageView coreturn = inflate3.findViewById(R.id.comment_return);
                ImageView coreturn2 = inflate3.findViewById(R.id.ad_return);
                MyCommentPresenter myCommentPresenter = new MyCommentPresenter(new CommentCall());
                myCommentPresenter.reqeust(userId, sessionId, id, 1, 10);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                comrecycler.setLayoutManager(linearLayoutManager);
                myCommentAdapter = new MyCommentAdapter(this);
                comrecycler.setAdapter(myCommentAdapter);
                myCommentAdapter.getgreat(new MyCommentAdapter.Great12() {
                    @Override
                    public void ongreat(int i) {
                        MovieCommertGreatPresenter movieCommertGreatPresenter = new MovieCommertGreatPresenter(new DianZan());
                        movieCommertGreatPresenter.reqeust(userId,sessionId,result.get(i).getCommentId());
                    }

                    @Override
                    public void fogreat(int i) {

                    }
                });
                myCommentAdapter.tjpl(new MyCommentAdapter.Tjpl2() {
                    @Override
                    public void ontjj2(final int i) {
                        ll8.setVisibility(View.VISIBLE);
                        huifu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CommentReply222Presenter commentReply222Presenter = new CommentReply222Presenter(new HuiFu());
                                commentReply222Presenter.reqeust(userId,sessionId,result.get(i).getCommentId(),"佛曰：永无BUG！");
                                ll8.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                coreturn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                coreturn2.setOnClickListener(new View.OnClickListener() {
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
        movieName = result.getName();
    }

    @Override
    public void fail(ApiException e) {

    }

    class DianYing implements DataCall<Result<MoviesDBean>> {

        @Override
        public void success(Result<MoviesDBean> data) {
            moviesDBean = data.getResult();
            int followMovie = moviesDBean.getFollowMovie();
            dLove.setChecked(followMovie == 1 ? true : false);
            // Toast.makeText(DetailsActivity.this, "11"+moviesDBean.getImageUrl(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class DianZan implements DataCall<Result> {

        @Override
        public void success(Result data) {
//            Toast.makeText(DetailsActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
            boolean zai = MApp.sharedPreferences.getBoolean("zai", false);
            if(!zai){
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "请先登录……", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class Fpl implements DataCall<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(DetailsActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class CommentCall implements DataCall<Result<List<MyComment>>> {
        @Override
        public void success(Result<List<MyComment>> data) {
//            Toast.makeText(getBaseContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            result = data.getResult();
            myCommentAdapter.addAll(result);
            myCommentAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class HuiFu implements DataCall<Result> {
        @Override
        public void success(Result data) {
            Toast.makeText(DetailsActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
