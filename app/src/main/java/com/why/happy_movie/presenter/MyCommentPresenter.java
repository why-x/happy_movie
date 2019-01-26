package com.why.happy_movie.presenter;



import com.why.happy_movie.core.Interfacea;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.NetWorkManager;

import io.reactivex.Observable;

/**
 * 查询影片评论
 */

public class MyCommentPresenter extends BasePresenter {

    public MyCommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        Interfacea iRequest = NetWorkManager.getInstance().create(Interfacea.class);
        return iRequest.mycomment((int)args[0],(String)args[1],(int)args[2],(int)args[3],(int)args[4]);
    }

}

