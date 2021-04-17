package be.hubertrm.dashboard.record.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

/**
 * {@code Record} class represents properties and behaviors of record objects
 * in the record management system.
 * <br>
 *      Each record is composed of an id, a date, an amount a category, a source
 *      and comments
 * <br>
 * <p> ON: April 04, 2021</p>
 *
 * @version 1.0
 * @author hubertrm
 */
@Data
public class RecordDto implements Comparable<RecordDto>, Datable {

    private long id;
    private LocalDate payDate;
    private float amount;
    private CategoryDto categoryDto;
    private SourceDto sourceDto;
    private String comments;

    public RecordDto() {
        this(-1L, LocalDate.now(), 0L, new CategoryDto(), new SourceDto(), "");
    }

    public RecordDto(long id, LocalDate payDate, float amount, CategoryDto categoryDto, SourceDto sourceDto, String comments) {
        this.id = id;
        this.payDate = payDate;
        this.amount = amount;
        this.categoryDto = categoryDto;
        this.sourceDto = sourceDto;
        this.comments = comments;
    }

    /**
     * @param obj the object to be compared with
     * @return true if of the same type and payDates, amounts, categories and sources are equal
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof RecordDto
                && this.payDate == ((RecordDto) obj).getPayDate()
                && this.amount == ((RecordDto) obj).getAmount()
                && this.categoryDto.equals(((RecordDto) obj).getCategoryDto())
                && this.sourceDto.equals(((RecordDto) obj).getSourceDto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payDate, amount, categoryDto, sourceDto, comments);
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", payDate=" + payDate +
                ", amount=" + amount +
                (categoryDto != null ? ", category=" + categoryDto.getName() : "") +
                (sourceDto != null ? ", source=" + sourceDto.getName() : "") +
                ", comments='" + comments + '\'' +
                '}';
    }

    /**
     * @param recordDto the object to be compared to
     * @return -1 if compared object's payDate is prior to comparing object's payDate
     *          0 if they are the same
     *         +1 if compared object's payDate is subsequent to comparing object's payDate
     */
    @Override
    public int compareTo(RecordDto recordDto) {
        return this.payDate.compareTo(recordDto.getPayDate());
    }

    @Override
    public LocalDate getDate() {
        return getPayDate();
    }
}
