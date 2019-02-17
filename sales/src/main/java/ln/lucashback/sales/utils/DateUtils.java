package ln.lucashback.sales.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDateTime getLocalDateTime(String dateAsString) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return dateTimeFormat.parse(dateAsString, LocalDateTime::from);
    }
}
