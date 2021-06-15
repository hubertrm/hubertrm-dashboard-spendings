package be.hubertrm.dashboard.record.core.comparator;

import java.time.LocalDate;

/**
 * A class implements the {@link Datable} type-marker interface to indicate to the
 * {@link DateCompare DateCompare.compare(Datable d1, Datable d2)}
 * method that it is legal for that method to compare the instances of that class on a {@link java.time.LocalDate} object.
 */
public interface Datable {

    /**
     * Get the value of the date
     * @return the date of the {@link Datable} instance
     */
    LocalDate getDate();
}
