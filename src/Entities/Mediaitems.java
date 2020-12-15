package Entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;


public class Mediaitems {
    private long mid;
    private String title;
    private Long prodYear;
    private Short titleLength;


    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Long getProdYear() {
        return prodYear;
    }

    public void setProdYear(Long prodYear) {
        this.prodYear = prodYear;
    }


    public Short getTitleLength() {
        return titleLength;
    }

    public void setTitleLength(Short titleLength) {
        this.titleLength = titleLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mediaitems that = (Mediaitems) o;
        return mid == that.mid &&
                Objects.equals(title, that.title) &&
                Objects.equals(prodYear, that.prodYear) &&
                Objects.equals(titleLength, that.titleLength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid, title, prodYear, titleLength);
    }
}
