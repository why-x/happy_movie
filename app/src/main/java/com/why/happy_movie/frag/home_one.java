package com.why.happy_movie.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.why.happy_movie.adapter.CinemaFlowAdapter;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.RecyclerCoverFlow;

/**
 * @author happy_movie
 * @date 2019/1/22 20:27
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class home_one extends Fragment {

    List<Integer> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_one,container,false);

        RecyclerCoverFlow recyclerCoverFlow = view.findViewById(R.id.rcf_cinema_flow);
        list.add(R.drawable.mengchongguojiang);
        CinemaFlowAdapter cinemaFlowAdapter = new CinemaFlowAdapter(getContext(), list);
        recyclerCoverFlow.setAdapter(cinemaFlowAdapter);



        return view;
    }
}
