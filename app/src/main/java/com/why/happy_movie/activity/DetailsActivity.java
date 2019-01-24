package com.why.happy_movie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.d_love)
    ImageButton dLove;
    @BindView(R.id.details_movie)
    ImageView detailsMovie;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        dDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.d_details:
                break;
                
        }
    }
}
