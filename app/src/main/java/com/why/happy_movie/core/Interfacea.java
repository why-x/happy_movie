package com.why.happy_movie.core;

import com.why.happy_movie.bean.LoginBean;
import com.why.happy_movie.bean.MovieDBean;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.YingYuanBean;

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
    Observable<Result<List<MovieListBean>>> findHotMovieList(@Header("userId") int userId,
                                                             @Header("sessionId")String sessionId,
                                                             @Query("page")int page,
                                                             @Query("count")int count);


    /**
     * 正在热映
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/findReleaseMovieList")
    Observable<Result<List<MovieListBean>>> findReleaseMovieList(@Header("userId") int userId,
                                                             @Header("sessionId")String sessionId,
                                                             @Query("page")int page,
                                                             @Query("count")int count);

    /**
     * 即将上映
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/findComingSoonMovieList")
    Observable<Result<List<MovieListBean>>> findComingSoonMovieList(@Header("userId") int userId,
                                                                 @Header("sessionId")String sessionId,
                                                                 @Query("page")int page,
                                                                 @Query("count")int count);


    /**
     * 电影详情
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movie/v1/findMoviesById")
    Observable<Result<MovieDBean>> findMoviesById(@Header("userId") int userId,
                                                  @Header("sessionId")String sessionId,
                                                  @Query("movieId")int movieId);



    @GET("cinema/v1/findRecommendCinemas")
    Observable<Result<List<YingYuanBean>>> findRecommendCinemas(@Header("userId") int userId,
                                                                @Header("sessionId")String sessionId,
                                                                @Query("page")int page,
                                                                @Query("count")int count);


    @GET("cinema/v1/findNearbyCinemas")
    Observable<Result<List<YingYuanBean>>> findNearbyCinemas(@Header("userId") int userId,
                                                                @Header("sessionId")String sessionId,
                                                             @Query("longitude")String longitude,
                                                             @Query("latitude")String latitude,
                                                                @Query("page")int page,
                                                                @Query("count")int count);



}
