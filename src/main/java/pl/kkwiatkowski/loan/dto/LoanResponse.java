package pl.kkwiatkowski.loan.dto;

import lombok.*;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@NoArgsConstructor
@ToString
@Builder
public class LoanResponse {
    private BigDecimal loanAmount;
    private BigDecimal repaymentAmount;
    private DateTime loanActionDate;
    private DateTime loanTerm;
}