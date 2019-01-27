package com.why.happy_movie.core;

import com.why.happy_movie.bean.CimemaldListBean;
import com.why.happy_movie.bean.LoginBean;
import com.why.happy_movie.bean.MovieDBean;
import com.why.happy_movie.bean.MovieListBean;
import com.why.happy_movie.bean.MovieScheduleListBean;
import com.why.happy_movie.bean.MoviesDBean;
import com.why.happy_movie.bean.MyComment;
import com.why.happy_movie.bean.MyPay;
import com.why.happy_movie.bean.MyUpdate;
import com.why.happy_movie.bean.PayBean;
import com.why.happy_movie.bean.Result;
import com.why.happy_movie.bean.SearchCnimea;
import com.why.happy_movie.bean.SearchMovie;
import com.why.happy_movie.bean.TimeCnimea;
import com.why.happy_movie.bean.YingYuanBean;
import com.why.happy_movie.bean.YongHuBean;

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
    Observable<Result> getRegisterData(@Field("nickName") String nickName,
                                       @Field("phone") String phone,
                                       @Field("pwd") String pwd,
                                       @Field("pwd2") String pwd2,
                                       @Field("sex") String sex,
                                       @Field("birthday") String birthday,
                                       @Field("email") String email);

    /**
     * 登录
     *
     * @param phone
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result<LoginBean>> getLogin(@Field("phone") String phone,
                                           @Field("pwd") String pwd);


    /**
     * 查询热门电影列表
     */
    @GET("movie/v1/findHotMovieList")
    Observable<Result<List<MovieListBean>>> findHotMovieList(@Header("userId") int userId,
                                                             @Header("sessionId") String sessionId,
                                                             @Query("page") int page,
                                                             @Query("count") int count);


    /**
     * 正在热映
     *
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/findReleaseMovieList")
    Observable<Result<List<MovieListBean>>> findReleaseMovieList(@Header("userId") int userId,
                                                                 @Header("sessionId") String sessionId,
                                                                 @Query("page") int page,
                                                                 @Query("count") int count);

    /**
     * 即将上映
     *
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/findComingSoonMovieList")
    Observable<Result<List<MovieListBean>>> findComingSoonMovieList(@Header("userId") int userId,
                                                                    @Header("sessionId") String sessionId,
                                                                    @Query("page") int page,
                                                                    @Query("count") int count);


    /**
     * 根据id查询电影
     *
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movie/v1/findMoviesById")
    Observable<Result<MovieDBean>> findMoviesById(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Query("movieId") int movieId);


    /**
     * 推荐影院
     *
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("cinema/v1/findRecommendCinemas")
    Observable<Result<List<YingYuanBean>>> findRecommendCinemas(@Header("userId") int userId,
                                                                @Header("sessionId") String sessionId,
                                                                @Query("page") int page,
                                                                @Query("count") int count);

    /**
     * 附近影院
     *
     * @param userId
     * @param sessionId
     * @param longitude
     * @param latitude
     * @param page
     * @param count
     * @return
     */
    @GET("cinema/v1/findNearbyCinemas")
    Observable<Result<List<YingYuanBean>>> findNearbyCinemas(@Header("userId") int userId,
                                                             @Header("sessionId") String sessionId,
                                                             @Query("longitude") String longitude,
                                                             @Query("latitude") String latitude,
                                                             @Query("page") int page,
                                                             @Query("count") int count);


    /**
     * 电影详情
     *
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movie/v1/findMoviesDetail")
    Observable<Result<MoviesDBean>> findMoviesDetail(@Header("userId") int userId,
                                                     @Header("sessionId") String sessionId,
                                                     @Query("movieId") int movieId);


    /**
     * 根据用户ID查询用户信息
     *
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("user/v1/verify/getUserInfoByUserId")
    Observable<Result<YongHuBean>> getUserInfoByUserId(@Header("userId") int userId,
                                                       @Header("sessionId") String sessionId);


    /**
     * 上传头像
     *
     * @param userId
     * @param sessionId
     * @param body
     * @return
     */
    @POST("user/v1/verify/uploadHeadPic")
    Observable<Result> uploadHeadPic(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Body MultipartBody body);


    /**
     * 签到
     *
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("user/v1/verify/userSignIn")
    Observable<Result> userSignIn(@Header("userId") int userId,
                                  @Header("sessionId") String sessionId);


    /**
     * 根据影院ID查询该影院当前排期的电影列表
     *
     * @param cinemaId
     * @return
     */
    @GET("movie/v1/findMovieListByCinemaId")
    Observable<Result<List<CimemaldListBean>>> findMovieListByCinemaId(@Query("cinemaId") int cinemaId);


    /**
     * 根据电影ID和影院ID查询电影排期列表
     *
     * @param cinemaId
     * @param movieId
     * @return
     */
    @GET("movie/v1/findMovieScheduleList")
    Observable<Result<List<MovieScheduleListBean>>> findMovieScheduleList(@Query("cinemasId") int cinemaId,
                                                                          @Query("movieId") int movieId);

    /**
     * 查询用户关注的影院信息
     *
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("cinema/v1/verify/findCinemaPageList")
    Observable<Result<List<SearchCnimea>>> findCinemaPageList(@Header("userId") int userId,
                                                              @Header("sessionId") String sessionId,
                                                              @Query("page") int page,
                                                              @Query("count") int count);


    /**
     * 关注影院
     *
     * @param userId
     * @param sessionId
     * @param cinemaId
     * @return
     */
    @GET("cinema/v1/verify/followCinema")
    Observable<Result> followCinema(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Query("cinemaId") int cinemaId);


    @GET("cinema/v1/verify/cancelFollowCinema")
    Observable<Result> cancelFollowCinema(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Query("cinemaId") int cinemaId);

    /**
     * 查询用户关注的影片列表
     *
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/verify/findMoviePageList")
    Observable<Result<List<SearchMovie>>> findMoviePageList(@Header("userId") int userId,
                                                            @Header("sessionId") String sessionId,
                                                            @Query("page") int page,
                                                            @Query("count") int count);

    /**
     * 关注电影
     *
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movie/v1/verify/followMovie")
    Observable<Result> followMovie(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Query("movieId") int movieId);


    /**
     * 取消关注电影
     *
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movie/v1/verify/cancelFollowMovie")
    Observable<Result> cancelFollowMovie(@Header("userId") int userId,
                                         @Header("sessionId") String sessionId,
                                         @Query("movieId") int movieId);


    /**
     * 用户购票记录查询列表
     *
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @param status
     * @return
     */
    @GET("user/v1/verify/findUserBuyTicketRecordList")
    Observable<Result<List<MyPay>>> mypay(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Query("page") int page,
                                          @Query("count") int count,
                                          @Query("status") int status);

    /**
     * 意见反馈
     *
     * @param userId
     * @param sessionId
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("tool/v1/verify/recordFeedBack")
    Observable<Result> myidea(@Header("userId") int userId,
                              @Header("sessionId") String sessionId,
                              @Field("content") String content);


    /**
     * 根据电影ID查询当前排片该电影的影院列表
     * @param movieId
     * @return
     */
    @GET("movie/v1/findCinemasListByMovieId")
    Observable<Result<List<TimeCnimea>>> findCinemasListByMovieId(@Query("movieId") int movieId);

    /**
     * 查询新版本 未做p
     *
     * @param userId
     * @param sessionId
     * @param ak
     * @return
     */
    @GET("tool/v1/findNewVersion")
    Observable<Result> followMovie(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Query("ak") String ak);

    /**
     * 修改用户信息
     *
     * @param userId
     * @param sessionId
     * @param nickName
     * @param sex
     * @param email
     * @return
     */
    @FormUrlEncoded
    @POST("user/v1/verify/modifyUserInfo")
    Observable<Result<MyUpdate>> myupdate(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Field("nickName") String nickName,
                                          @Field("sex") int sex,
                                          @Field("email") String email);

    /**
     * 上传用户头像  未完善bean类
     *
     * @param userId
     * @param sessionId
     * @param image
     * @return
     */
    @FormUrlEncoded
    @POST("user/v1/verify/uploadHeadPic")
    Observable<Result> onhead(@Header("userId") int userId,
                              @Header("sessionId") String sessionId,
                              @Field("image") File image);

    /**
     * 修改密码
     * @param userId
     * @param sessionId
     * @param oldPwd
     * @param newPwd
     * @param newPwd2
     * @return
     */
    @FormUrlEncoded
    @POST("user/v1/verify/modifyUserPwd")
    Observable<Result> updatepwd(@Header("userId") int userId,
                                 @Header("sessionId") String sessionId,
                                 @Field("oldPwd") String oldPwd,
                                 @Field("newPwd") String newPwd,
                                 @Field("newPwd2") String newPwd2);


    /**
     * 查询影片评论
     * @param userId
     * @param sessionId
     * @param movieId
     * @param page
     * @param count
     * @return
     */
    /**
     * 电影评论列表
     * @param userId
     * @param sessionId
     * @param movieId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/findAllMovieComment")
    Observable<Result<List<MyComment>>> mycomment(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Query("movieId") int movieId,
                                 @Query("page") int page,
                                 @Query("count") int count);


    /**
     * 创建订单
     * @param userId
     * @param sessionId
     * @param scheduleId
     * @param amount
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("movie/v1/verify/buyMovieTicket")
    Observable<Result> buyMovieTicket(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Field("scheduleId")int scheduleId,
                                      @Field("amount")int amount ,
                                      @Field("sign")String sign);


    /**
     * 支付
     * @param userId
     * @param sessionId
     * @param payType
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("movie/v1/verify/pay")
    Observable<PayBean> pay(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Field("payType")int payType,
                                    @Field("orderId")String orderId);



}
