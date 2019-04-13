package pl.kkwiatkowski.loan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LoanDetailsResponse {
    
    private Integer loanId;
    private BigDecimal loanAmount;
    private BigDecimal repaymentAmount;
    private LocalDateTime loanTerm;
    private LocalDateTime loanIssuedDate;
    private LocalDateTime lastExtendDate;
    private UserDetails user; //TODO - ekstensja

}
