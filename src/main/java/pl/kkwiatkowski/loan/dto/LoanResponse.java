package pl.kkwiatkowski.loan.dto;

import lombok.*;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Builder
public class LoanResponse {
    private String loanId;
    private BigDecimal loanAmount;
    private BigDecimal repaymentAmount;
    private DateTime loanIssuedDate;
    private DateTime loanTerm;
    private DateTime lastExtendDate;
}