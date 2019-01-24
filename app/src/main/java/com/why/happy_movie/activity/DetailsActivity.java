package com.why.happy_movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.bean.MovieDBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.presenter.MovieDPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        dDetails.setOnClickListener(this);
        dReturn.setOnClickListener(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);

        MovieDPresenter movieDPresenter = new MovieDPresenter(this);
        movieDPresenter.reqeust(userId,sessionId,id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.d_details:
                break;
            case R.id.d_return:
                finish();
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
}
