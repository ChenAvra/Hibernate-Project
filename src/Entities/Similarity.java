package Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(SimilarityPK.class)
public class Similarity {
    private long mid1;
    private long mid2;
    private Double similarity;

    @Id
    @Column(name = "MID1", nullable = false, precision = 0)
    public long getMid1() {
        return mid1;
    }

    public void setMid1(long mid1) {
        this.mid1 = mid1;
    }

    @Id
    @Column(name = "MID2", nullable = false, precision = 0)
    public long getMid2() {
        return mid2;
    }

    public void setMid2(long mid2) {
        this.mid2 = mid2;
    }

    @Basic
    @Column(name = "SIMILARITY", nullable = true, precision = 0)
    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Similarity that = (Similarity) o;
        return mid1 == that.mid1 &&
                mid2 == that.mid2 &&
                Objects.equals(similarity, that.similarity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid1, mid2, similarity);
    }
}
