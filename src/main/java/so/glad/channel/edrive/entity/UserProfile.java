package so.glad.channel.edrive.entity;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Cartoon
 * 2015/3/26.
 */
@XmlRootElement(name = "userExt")
public class UserProfile extends Root{

    private String loginName;

    private String headPortraitUrl;

    private String nickname;

    private String gender;

    private String birthday;

    private String realname;

    private String mobile;

    private String email;

    private String idNumber;

    private String safeMobile;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSafeMobile() {
        return safeMobile;
    }

    public void setSafeMobile(String safeMobile) {
        this.safeMobile = safeMobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile)) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equal(loginName, that.loginName) &&
                Objects.equal(headPortraitUrl, that.headPortraitUrl) &&
                Objects.equal(nickname, that.nickname) &&
                Objects.equal(gender, that.gender) &&
                Objects.equal(birthday, that.birthday) &&
                Objects.equal(realname, that.realname) &&
                Objects.equal(mobile, that.mobile) &&
                Objects.equal(email, that.email) &&
                Objects.equal(idNumber, that.idNumber) &&
                Objects.equal(safeMobile, that.safeMobile);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(loginName, headPortraitUrl, nickname, gender, birthday, realname, mobile, email, idNumber, safeMobile);
    }
}
