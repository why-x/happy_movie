package com.why.happy_movie.presenter;



import com.why.happy_movie.core.Interfacea;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.NetWorkManager;

import io.reactivex.Observable;

/**
 * 修改密码
 */

public class UpdatePwdPresenter extends BasePresenter {

    public UpdatePwdPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        Interfacea iRequest = NetWorkManager.getInstance().create(Interfacea.class);
        return iRequest.updatepwd((int)args[0],(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }
}

