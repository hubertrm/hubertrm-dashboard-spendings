package be.hubertrm.dashboard.record.comparator;

import java.util.Comparator;

/**
 * {@code DateCompare} class represents properties and behaviors of DateCompare objects
 * in the record management system.
 * <br>
 *      Its purpose his to compare {@link Datable} objects
 * <br>
 * <p> ON: April 05, 2021</p>
 *
 * @version 1.0
 * @author hubertrm
 */
public class DateCompare implements Comparator<Datable> {

    @Override
    public int compare(Datable datableCompared, Datable datableComparing) {
        return datableCompared.getDate().compareTo(datableComparing.getDate());
    }
}
