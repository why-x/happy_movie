package com.why.happy_movie.frag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.MApp;
import com.why.happy_movie.activity.LoginActivity;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.myactivity.MessageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author happy_movie
 * @date 2019/1/22 20:27
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class home_three extends Fragment implements View.OnClickListener {

    @BindView(R.id.my_head)
    SimpleDraweeView myHead;
    @BindView(R.id.my_sign)
    Button mySign;
    @BindView(R.id.my_message)
    ImageButton myMessage;
    @BindView(R.id.my_love)
    ImageButton myLove;
    @BindView(R.id.my_feedback)
    ImageButton myFeedback;
    @BindView(R.id.my_version)
    ImageButton myVersion;
    @BindView(R.id.my_logout)
    ImageButton myLogout;
    Unbinder unbinder;
    @BindView(R.id.my_name)
    TextView my_name;
    private SharedPreferences sp;
    boolean zai=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_frag_all, container, false);
        unbinder = ButterKnife.bind(this, view);
         zai = MApp.sharedPreferences.getBoolean("zai", false);

        List<UserBean> userBeans = MApp.userBeanDao.loadAll();
         if(zai){
             if(userBeans.size()>0){
                 String headPic = userBeans.get(0).getHeadPic();
                 myHead.setImageURI(headPic);
                 my_name.setText(userBeans.get(0).getNickName());
             }
         }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myMessage.setOnClickListener(this);
        my_name.setOnClickListener(this);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_message:
                Intent intentm=new Intent(getActivity(),MessageActivity.class);
                startActivity(intentm);
                break;
            case  R.id.my_name:
                Intent intentlo=new Intent(getActivity(),LoginActivity.class);
                startActivity(intentlo);
                break;
        }
    }
}
