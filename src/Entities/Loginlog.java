package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@IdClass(LoginlogPK.class)
public class Loginlog {
    private long userid;
    private Timestamp logintime;

    @Id
    @Column(name = "USERID", nullable = false, precision = 0)
    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Id
    @Column(name = "LOGINTIME", nullable = false)
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
        Loginlog loginlog = (Loginlog) o;
        return userid == loginlog.userid &&
                Objects.equals(logintime, loginlog.logintime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, logintime);
    }
}
