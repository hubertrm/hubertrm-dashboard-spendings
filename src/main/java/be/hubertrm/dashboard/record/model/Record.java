package be.hubertrm.dashboard.record.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class Record {

    private long id;
    private Date spendingDate;
    private float amount;
    private String category;
    private String account;
    private String note;

    public Record(long id, Date spendingDate, float amount, String category, String account, String note) {
        this.id = id;
        this.spendingDate = spendingDate;
        this.amount = amount;
        this.category = category;
        this.account = account;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSpendingDate() {
        return spendingDate;
    }

    public void setSpendingDate(Date spendingDate) {
        this.spendingDate = spendingDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
