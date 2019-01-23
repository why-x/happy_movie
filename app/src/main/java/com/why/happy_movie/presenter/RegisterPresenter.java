package com.why.happy_movie.presenter;


import com.why.happy_movie.bean.Result;
import com.why.happy_movie.core.Interfacea;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.NetWorkManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by YangYueXiang
 * on 2018/11/14
 */
public class RegisterPresenter extends BasePresenter{


    public RegisterPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        Interfacea iRequest = NetWorkManager.getInstance().create(Interfacea.class);
        Observable<Result> registerData = iRequest.getRegisterData((String) args[0], (String) args[1], (String) args[2], (String) args[3],
                (String) args[4], (String) args[5], (String) args[6]);
        return registerData;
    }
}
