package com.why.happy_movie.bean;

/**
 * @author happy_movie
 * @date 2019/1/28 11:40
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class WxLoginBean {


        /**
         * sessionId : 15486467930581959
         * userId : 1959
         * userInfo : {"headPic":"http://thirdwx.qlogo.cn/mmopen/vi_32/T7SClKgvYABicX6XIicq1icvWEuQenDCichcbnXVZib2nf1ibupK3wyRcSB8YIVthVKUlw19GUhylfR6aS1XfichPgqXw/132","id":1959,"lastLoginTime":1548646793000,"nickName":"ＯゞAゝda調_hDH","sex":1}
         */

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

        public static class UserInfoBean {
            /**
             * headPic : http://thirdwx.qlogo.cn/mmopen/vi_32/T7SClKgvYABicX6XIicq1icvWEuQenDCichcbnXVZib2nf1ibupK3wyRcSB8YIVthVKUlw19GUhylfR6aS1XfichPgqXw/132
             * id : 1959
             * lastLoginTime : 1548646793000
             * nickName : ＯゞAゝda調_hDH
             * sex : 1
             */

            private String headPic;
            private int id;
            private long lastLoginTime;
            private String nickName;
            private int sex;

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getLastLoginTime() {
                return lastLoginTime;
            }

            public void setLastLoginTime(long lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }
        }

}
