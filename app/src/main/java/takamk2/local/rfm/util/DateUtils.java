package takamk2.local.rfm.util;

import java.util.Calendar;

/**
 * Created by takamk2 on 17/02/10.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class DateUtils {

    private static final String LOGTAG = DateUtils.class.getSimpleName();

    public static Calendar getCalendarOfNow() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static long getTimestampOfNow() {
        return getCalendarOfNow().getTimeInMillis();
    }
}
