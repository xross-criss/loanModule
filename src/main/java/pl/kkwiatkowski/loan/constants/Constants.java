package pl.kkwiatkowski.loan.constants;

import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.math.BigDecimal;

public class Constants {
    public static final BigDecimal minAmount = BigDecimal.valueOf(500.0);
    public static final BigDecimal maxAmount = BigDecimal.valueOf(50000.0);
    public static final Duration minLoanDuration = Duration.standardDays(60);
    public static final Duration maxLoanDuration = Duration.standardDays(730);
    public static final Hours systemOffStartPeriodHours = Hours.parseHours("00");
    public static final Minutes systemOffStartPeriodMinutes= Minutes.parseMinutes("00");
    public static final Hours systemOffEndPeriodHours = Hours.parseHours("06");
    public static final Minutes systemOffEndPeriodMinutes = Minutes.parseMinutes("00");
    public static final BigDecimal interestPercentage = BigDecimal.valueOf(10/100);
}