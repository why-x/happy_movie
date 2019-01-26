package com.why.happy_movie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.why.happy_movie.view.SeatTable;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class StatActivity extends AppCompatActivity {

    private TextView yingcheng;
    private TextView txt_address;
    private TextView moviename;
    private TextView txt_shijian;
    private TextView tct_ting;
    public SeatTable seatTableView;
    private double price;
    double zongprice=0;
    private TextView txtprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        yingcheng = findViewById(R.id.yingcheng);
        txt_address = findViewById(R.id.address);
        moviename = findViewById(R.id.moviename);
        txt_shijian = findViewById(R.id.shijian);
        tct_ting = findViewById(R.id.ting);
        txtprice = findViewById(R.id.price);
        ImageView  back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
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
                DecimalFormat df2 = new DecimalFormat("#0.00");
                String format = df2.format(zongprice);
                txtprice.setText(format+"");
            }

            @Override
            public void unCheck(int row, int column) {
                zongprice-=price;
                DecimalFormat df2 = new DecimalFormat("#0.00");
                String format = df2.format(zongprice);
                txtprice.setText(format+"");
            }


        });
        seatTableView.setData(10,15);
    }


}
