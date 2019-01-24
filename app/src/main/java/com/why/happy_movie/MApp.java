package com.why.happy_movie;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.why.happy_movie.dao.DaoMaster;
import com.why.happy_movie.dao.DaoSession;
import com.why.happy_movie.dao.UserBeanDao;


/**
 * 佛曰： 永无BUG 盘他！
 */
public class MApp extends Application {


    /** 主线程ID */
    private static int mMainThreadId = -1;
    /** 主线程ID */
    private static Thread mMainThread;
    /** 主线程Handler */
    private static Handler mMainThreadHandler;
    /** 主线程Looper */
    private static Looper mMainLooper;

    /**
     * context 全局唯一的上下文
     */
    private static Context context;
    public static SharedPreferences sharedPreferences;
    public static UserBeanDao userBeanDao;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoSession daoSession = DaoMaster.newDevSession(this, UserBeanDao.TABLENAME);
        userBeanDao = daoSession.getUserBeanDao();

        context=this;
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        sharedPreferences = getSharedPreferences("sp",MODE_PRIVATE);

        Fresco.initialize(this);
    }

    public static SharedPreferences getShare(){
        return sharedPreferences;
    }

    /**
     * @author: 康海涛 QQ2541849981
     * @describe: 获取全局Application的上下文
     * @return 全局唯一的上下文
     */
    public static Context getContext() {
        return context;
    }

    /** 获取主线程ID */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /** 获取主线程 */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /** 获取主线程的handler */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /** 获取主线程的looper */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }
}
