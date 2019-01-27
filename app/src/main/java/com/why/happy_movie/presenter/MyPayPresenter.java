package com.why.happy_movie.presenter;



import com.why.happy_movie.core.Interfacea;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.NetWorkManager;

import io.reactivex.Observable;

/**
 * 用户购票记录查询列表
 */

public class MyPayPresenter extends BasePresenter {

    public MyPayPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        Interfacea iRequest = NetWorkManager.getInstance().create(Interfacea.class);
        return iRequest.mypay((int)args[0],(String)args[1],(int) args[2],(int) args[3],(int)args[4]);
    }


}

