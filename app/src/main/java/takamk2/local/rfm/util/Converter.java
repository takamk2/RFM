package takamk2.local.rfm.util;

import java.text.NumberFormat;
import java.util.Calendar;

/**
 * Created by takamk2 on 17/02/10.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class Converter {

    public static Calendar convertTimestampToCalendar(Long timestamp) {
        // TODO: Implement
        return null;
    }

    public static boolean convertIntToBoolean(int i) {
        return i == 1;
    }

    public static int convertBooleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public static String convertLongToCurrency(Long price) {
        if (price == null) {
            return "N/A";
        }

        return NumberFormat.getCurrencyInstance().format(price);
    }

    public static String convertTimestampToStringDate(Long timestamp) {
        Calendar cal = convertTimestampToCalendar(timestamp);
        if (cal == null) {
            return "N/A";
        }

        return String.format("%04d/%02d/%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DATE));
    }

    public static String convertTimestampToStringDateTime(Long timestamp) {
        Calendar cal = convertTimestampToCalendar(timestamp);
        if (cal == null) {
            return "N/A";
        }

        cal.get(Calendar.YEAR);
        String res = "";
        return res;
    }
}
