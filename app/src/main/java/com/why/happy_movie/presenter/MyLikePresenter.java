package com.why.happy_movie.presenter;



import com.why.happy_movie.core.Interfacea;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.NetWorkManager;

import io.reactivex.Observable;

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */

public class MyLikePresenter extends BasePresenter {

    public MyLikePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        Interfacea iRequest = NetWorkManager.getInstance().create(Interfacea.class);
        return iRequest.followCinema((int)args[0],(String)args[1],(int)args[2]);
    }


}

