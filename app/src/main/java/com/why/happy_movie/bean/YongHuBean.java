package com.why.happy_movie.bean;

/**
 * @author happy_movie
 * @date 2019/1/25 8:41
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public class YongHuBean {


        /**
         * birthday : 320256000000
         * headPic : http://172.17.8.100/images/head_pic/20180720175142.png
         * id : 5
         * lastLoginTime : 1532133499000
         * nickName : 你的益达
         * phone : 18600151568
         * sex : 1
         */

        private long birthday;
        private String headPic;
        private int id;
        private long lastLoginTime;
        private String nickName;
        private String phone;
        private int sex;
        String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

}
