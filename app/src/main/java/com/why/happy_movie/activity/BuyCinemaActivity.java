package com.why.happy_movie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.why.happy_movie.adapter.BuyCinemasAdapter;
import com.why.happy_movie.adapter.CinemasAdapter;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.TimeCnimea;
import com.why.happy_movie.bean.YingYuanBean;
import com.why.happy_movie.presenter.BuyCinemaPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

public class BuyCinemaActivity extends AppCompatActivity implements DataCall<Result<List<TimeCnimea>>> {

    private int movieId;
    private String name;
    private BuyCinemasAdapter cinemasAdapter;
    List<TimeCnimea> timeCnimeas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buycinema);
        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId", 0);
        name = intent.getStringExtra("name");
        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView  txt_name = findViewById(R.id.name);
        txt_name.setText(name);

        RecyclerView recyclerView = findViewById(R.id.list5);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cinemasAdapter = new BuyCinemasAdapter(this,timeCnimeas);
        cinemasAdapter.getLike(new BuyCinemasAdapter.Tiao() {
            @Override
            public void onSuccess(int i) {
                Intent intent1 = new Intent(BuyCinemaActivity.this, BuyMoieListActivity.class);
                intent1.putExtra("name",timeCnimeas.get(i).getName());
                intent1.putExtra("address",timeCnimeas.get(i).getAddress());
                intent1.putExtra("movieId",movieId);
                intent1.putExtra("cinemaid",timeCnimeas.get(i).getId());
                startActivity(intent1);
            }
        });
        recyclerView.setAdapter(cinemasAdapter);

        BuyCinemaPresenter buyCinemaPresenter = new BuyCinemaPresenter(this);
        buyCinemaPresenter.reqeust(movieId);

    }

    @Override
    public void success(Result<List<TimeCnimea>> data) {
        List<TimeCnimea> result = data.getResult();
        timeCnimeas.addAll(result);
        cinemasAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(ApiException e) {

    }
}
