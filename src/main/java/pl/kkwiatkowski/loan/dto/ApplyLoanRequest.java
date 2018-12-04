package pl.kkwiatkowski.loan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.Duration;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ApplyLoanRequest {
    private BigDecimal issuedAmount;
    private Duration issuedDuration;
}
