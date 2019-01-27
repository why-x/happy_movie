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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.why.happy_movie.MApp;
import com.why.happy_movie.activity.IdeaActivity;
import com.why.happy_movie.activity.LoginActivity;
import com.why.happy_movie.activity.MyLoveActivity;
import com.why.happy_movie.activity.MyNewsActivity;
import com.why.happy_movie.activity.MyRccordActivity;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.UserBean;
import com.why.happy_movie.myactivity.MessageActivity;
import com.why.happy_movie.presenter.SignPresenter;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.exception.ApiException;

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
    LinearLayout myMessage;
    @BindView(R.id.my_love)
    ImageButton myLove;
    @BindView(R.id.my_feedback)
    ImageButton myFeedback;
    @BindView(R.id.my_version)
    ImageView myVersion;
    @BindView(R.id.my_logout)
    LinearLayout myLogout;
    Unbinder unbinder;
    @BindView(R.id.my_name)
    TextView my_name;
    boolean zai = false;
    @BindView(R.id.my_rccord)
    ImageButton myRccord;
    @BindView(R.id.my_news)
    ImageView myNews;
    private List<UserBean> userBeans;
    int userId = 1771;
    String sessionId = "15482908826721771";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_frag_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        zai = MApp.sharedPreferences.getBoolean("zai", false);

        userBeans = MApp.userBeanDao.loadAll();
        if (zai) {
            if (userBeans.size() > 0) {
                String headPic = userBeans.get(0).getHeadPic();
                myHead.setImageURI(headPic);
                my_name.setText(userBeans.get(0).getNickName());
                userId = userBeans.get(0).getUserId();
                sessionId = userBeans.get(0).getSessionId();
            }
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myMessage.setOnClickListener(this);
        my_name.setOnClickListener(this);
        myLogout.setOnClickListener(this);
        mySign.setOnClickListener(this);
        myLove.setOnClickListener(this);
        myFeedback.setOnClickListener(this);
        myRccord.setOnClickListener(this);
        myVersion.setOnClickListener(this);
        myNews.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_message:
                if (!zai) {
                    Toast.makeText(getContext(), "请先登录……", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intentm = new Intent(getActivity(), MessageActivity.class);
                startActivity(intentm);
                break;
            case R.id.my_name:
                if (zai) {
                    return;
                }
                Intent intentlo = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentlo);
                break;
            case R.id.my_logout:
                if (!zai) {
                    Toast.makeText(getContext(), "请先登录……", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor edit = MApp.sharedPreferences.edit();
                edit.putBoolean("zai", false);
                edit.commit();
                zai = false;
                Toast.makeText(getContext(), "退出登录……", Toast.LENGTH_SHORT).show();
                myHead.setImageURI("res:///" + R.drawable.my_head);
                my_name.setText("登录");
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.my_sign:
                if (!zai) {
                    Toast.makeText(getContext(), "请先登录……", Toast.LENGTH_SHORT).show();
                    return;
                }
                SignPresenter signPresenter = new SignPresenter(new Qian());
                signPresenter.reqeust(userId, sessionId);
                break;
            case R.id.my_love:
                if (!zai) {
                    Toast.makeText(getContext(), "请先登录……", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent1 = new Intent(getActivity(), MyLoveActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_feedback:
                Intent intent2 = new Intent(getActivity(), IdeaActivity.class);
                startActivity(intent2);
                break;
            case R.id.my_rccord:
                if (!zai) {
                    Toast.makeText(getContext(), "请先登录……", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent3 = new Intent(getActivity(), MyRccordActivity.class);
                startActivity(intent3);
                break;
            case R.id.my_version:
                if (!zai) {
                    Toast.makeText(getContext(), "请先登录……", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getActivity(), "当前版本，已是最新版本！", Toast.LENGTH_SHORT).show();
                break;

            case R.id.my_news:
                if (!zai) {
                    Toast.makeText(getContext(), "请先登录……", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent4=new Intent(getActivity(),MyNewsActivity.class);
                startActivity(intent4);
                break;


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        zai = MApp.sharedPreferences.getBoolean("zai", false);
        if (zai) {
            if (userBeans.size() > 0) {
                userBeans = MApp.userBeanDao.loadAll();
                String headPic = userBeans.get(0).getHeadPic();
                myHead.setImageURI(headPic);
                my_name.setText(userBeans.get(0).getNickName());
                userId = userBeans.get(0).getUserId();
                sessionId = userBeans.get(0).getSessionId();
            }
        }
    }

    class Qian implements DataCall<Result> {

        @Override
        public void success(Result data) {
            Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
