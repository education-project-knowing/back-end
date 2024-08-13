package education.knowing.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class LocalDateTimeUtil {
    public static boolean checkTimeMoreThanMinutesAgo(LocalDateTime localDateTime, long minute){
        LocalDateTime currentTime = LocalDateTime.now();

        Duration duration = Duration.between(currentTime, localDateTime);
        long betweenMinute = duration.toMinutes();

        return betweenMinute < minute;
    }
}
