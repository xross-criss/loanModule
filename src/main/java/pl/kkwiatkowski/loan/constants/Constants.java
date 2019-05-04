package pl.kkwiatkowski.loan.constants;


import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

public class Constants {

    public static final BigDecimal MIN_AMOUNT = BigDecimal.valueOf(500.0);
    public static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(50000.0);
    public static final Duration MIN_LOAN_DURATION = Duration.ofDays(60);
    public static final Duration MAX_LOAN_DURATION = Duration.ofDays(730);
    public static final LocalTime SYSTEM_OFF_PERIOD_START = LocalTime.of(0, 0);
    public static final LocalTime SYSTEM_OFF_PERIOD_END = LocalTime.of(6, 0);
    public static final BigDecimal INTEREST_PERCENTAGE = BigDecimal.valueOf(10 / 100);

}