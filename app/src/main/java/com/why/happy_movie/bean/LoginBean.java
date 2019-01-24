package com.why.happy_movie.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author happy_movie
 * @date 2019/1/23 19:18
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class LoginBean {


        /**
         * sessionId : 15320592619803
         * userId : 3
         * userInfo : {"birthday":320256000000,"id":3,"lastLoginTime":1532059192000,"nickName":"你的益达","phone":"18600151568","sex":1,"headPic":"http://172.17.8.100/images/head_pic/bwjy.jpg"}
         */

        long id;
        private String sessionId;
        private int userId;
        private UserInfoBean userInfo;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }
}
