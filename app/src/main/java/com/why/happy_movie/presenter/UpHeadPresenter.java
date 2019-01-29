package com.why.happy_movie.presenter;



import com.why.happy_movie.core.Interfacea;
import com.why.happy_movie.utils.DataCall;
import com.why.happy_movie.utils.NetWorkManager;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */

public class UpHeadPresenter extends BasePresenter {

    public UpHeadPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        Interfacea iRequest = NetWorkManager.getInstance().create(Interfacea.class);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        List<Object> list = (List<Object>) args[2];
        if (list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                File file = new File((String) list.get(i));
                builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("multipart/octet-stream"), file));
            }
        }

        return iRequest.uploadHeadPic((int)args[0],(String)args[1],builder.build());
    }


}

