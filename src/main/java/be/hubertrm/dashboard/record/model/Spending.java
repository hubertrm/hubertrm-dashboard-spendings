package be.hubertrm.dashboard.record.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "spending")
@AllArgsConstructor
@NoArgsConstructor
public class Spending {

    private Long id;
    private Timestamp spendingDate;
    private Float amount;
    private Long categoryId;
    private Long accountId;
    private String note;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="ISEQ$$_73202")
    @SequenceGenerator(name="ISEQ$$_73202", sequenceName="ISEQ$$_73202", allocationSize=1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "spending_date", nullable = false)
    public Timestamp getSpendingDate() {
        return spendingDate;
    }

    public void setSpendingDate(Timestamp spendingDate) {
        this.spendingDate = spendingDate;
    }

    @Column(name = "amount", nullable = false)
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Column(name = "category_id", nullable = false)
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "account_id", nullable = false)
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Column(name = "note", nullable = true)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Spending{" +
                "id=" + id +
                ", spendingDate=" + spendingDate +
                ", amount=" + amount +
                ", categoryId=" + categoryId +
                ", accountId=" + accountId +
                ", note='" + note + '\'' +
                '}';
    }
}
