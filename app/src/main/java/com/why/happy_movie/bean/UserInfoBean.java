package com.why.happy_movie.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author happy_movie
 * @date 2019/1/24 16:13
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
public  class UserInfoBean {
    /**
     * birthday : 320256000000
     * id : 3
     * lastLoginTime : 1532059192000
     * nickName : 你的益达
     * phone : 18600151568
     * sex : 1
     * headPic : http://172.17.8.100/images/head_pic/bwjy.jpg
     */

    private long birthday;
    private long id;
    private long lastLoginTime;
    private String nickName;
    private String phone;
    private int sex;
    private String headPic;

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

}