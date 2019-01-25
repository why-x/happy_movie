package com.why.happy_movie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdeaActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.idea_lall)
    LinearLayout ideaLall;
    @BindView(R.id.idea_lall2)
    LinearLayout ideaLall2;
    @BindView(R.id.idea_put)
    Button ideaPut;
    @BindView(R.id.idea_lall3)
    LinearLayout ideaLall3;
    @BindView(R.id.idea_return)
    ImageView ideaReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idea_activity);
        ButterKnife.bind(this);
        ideaReturn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idea_return:
                finish();
                break;
        }
    }
}
