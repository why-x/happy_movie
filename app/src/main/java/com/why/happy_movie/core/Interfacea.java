package com.why.happy_movie.core;

import com.why.happy_movie.bean.LoginBean;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.bean.Result;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * 佛曰： 永无BUG 盘他！
 */
public interface Interfacea {

    //注册
    @FormUrlEncoded
    @POST("user/v1/registerUser")
    Observable<Result> getRegisterData(@Field("nickName")String nickName,
                                       @Field("phone")String phone,
                                       @Field("pwd")String pwd,
                                       @Field("pwd2")String pwd2,
                                       @Field("sex")String sex,
                                       @Field("birthday")String birthday,
                                       @Field("email")String email);

    /**
     * 登录
     * @param phone
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result<LoginBean>> getLogin(@Field("phone")String phone,
                                           @Field("pwd")String pwd);


    /**
     * 查询热门电影列表
     */
    @GET("movie/v1/findHotMovieList")
    Observable<Result<List<MovieListBean>>> findHotMovieList(@Header("userId") String userId,
                                                             @Header("sessionId")String sessionId);






}
