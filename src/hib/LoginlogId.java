package hib;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class LoginlogId implements Serializable {
    private long userid;
    private Timestamp logintime;


    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }


    public Timestamp getLogintime() {
        return logintime;
    }

    public void setLogintime(Timestamp logintime) {
        this.logintime = logintime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginlogId that = (LoginlogId) o;
        return userid == that.userid &&
                Objects.equals(logintime, that.logintime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, logintime);
    }
}
