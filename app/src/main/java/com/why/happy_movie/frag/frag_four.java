package com.why.happy_movie.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bw.movie.R;
import com.why.happy_movie.activity.HomeActivity;
import com.why.happy_movie.activity.LoginActivity;

/**
 * @author happy_movie
 * @date 2019/1/22 20:27
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class frag_four extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_four,container,false);
        LinearLayout linearLayout = view.findViewById(R.id.tiao);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),HomeActivity .class);
                getActivity().finish();
                startActivity(intent);
            }
        });
        return view;
    }
}
