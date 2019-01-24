package com.why.happy_movie.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author happy_movie
 * @date 2019/1/24 16:03
 * QQ:45198565
 * 佛曰：永无BUG 盘他！
 */
@Entity
public class UserBean {

    @Id
    long id;
    private String sessionId;
    private int userId;
    private long birthday;
    private long lastLoginTime;
    private String nickName;
    private String phone;
    private int sex;
    private String headPic;
    @Generated(hash = 662269575)
    public UserBean(long id, String sessionId, int userId, long birthday,
            long lastLoginTime, String nickName, String phone, int sex,
            String headPic) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
        this.birthday = birthday;
        this.lastLoginTime = lastLoginTime;
        this.nickName = nickName;
        this.phone = phone;
        this.sex = sex;
        this.headPic = headPic;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getSessionId() {
        return this.sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public long getBirthday() {
        return this.birthday;
    }
    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
    public long getLastLoginTime() {
        return this.lastLoginTime;
    }
    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getHeadPic() {
        return this.headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
}
