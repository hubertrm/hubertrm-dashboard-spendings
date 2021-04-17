package be.hubertrm.dashboard.record.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "record")
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    private Long id;
    private LocalDate payDate;
    private float amount;
    private long categoryId;
    private long sourceId;
    private String comments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "pay_date", nullable = false)
    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate spendingDate) {
        this.payDate = spendingDate;
    }

    @Column(name = "amount", nullable = false)
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Column(name = "category_id", nullable = false)
    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "source_id", nullable = false)
    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "comments", nullable = true)
    public String getComments() {
        return comments;
    }

    public void setComments(String note) {
        this.comments = note;
    }
}
