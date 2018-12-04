package pl.kkwiatkowski.loan.constants;

import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.math.BigDecimal;

public class Constants {
    public static final BigDecimal MIN_AMOUNT = BigDecimal.valueOf(500.0);
    public static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(50000.0);
    public static final Duration MIN_LOAN_DURATION = Duration.standardDays(60);
    public static final Duration MAX_LOAN_DURATION = Duration.standardDays(730);
    public static final Hours SYSTEM_OFF_START_PERIOD_HOURS = Hours.parseHours("00");
    public static final Minutes SYSTEM_OFF_START_PERIOD_MINUTES = Minutes.parseMinutes("00");
    public static final Hours SYSTEM_OFF_END_PERIOD_HOURS = Hours.parseHours("06");
    public static final Minutes SYSTEM_OFF_END_PERIOD_MINUTES = Minutes.parseMinutes("00");
    public static final BigDecimal INTEREST_PERCENTAGE = BigDecimal.valueOf(10 / 100);
    public static final Duration DURATION_OF_EXTENSION = Duration.standardDays(30);
}