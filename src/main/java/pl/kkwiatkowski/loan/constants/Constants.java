package pl.kkwiatkowski.loan.constants;

import org.joda.time.Duration;

import java.math.BigDecimal;

public class Constants {
    public static final BigDecimal minAmount = BigDecimal.valueOf(500.0);
    public static final BigDecimal maxAmount = BigDecimal.valueOf(50000.0);
    public static final Duration minLoanDuration = Duration.standardDays(60);
    public static final Duration maxLoanDuration = Duration.standardDays(730);
}