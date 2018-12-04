package pl.kkwiatkowski.loan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Loan {
    private String loanId;
    private BigDecimal loanAmount;
    private DateTime loanTerm;
    private DateTime loanIssuedDate;
    private DateTime lastExtendDate;
}
