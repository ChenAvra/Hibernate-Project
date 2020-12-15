package hib;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class HistoryId implements Serializable {
    private long userid;
    private long mid;
    private Timestamp viewtime;


    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }


    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }


    public Timestamp getViewtime() {
        return viewtime;
    }

    public void setViewtime(Timestamp viewtime) {
        this.viewtime = viewtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryId historyId = (HistoryId) o;
        return userid == historyId.userid &&
                mid == historyId.mid &&
                Objects.equals(viewtime, historyId.viewtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, mid, viewtime);
    }
}
