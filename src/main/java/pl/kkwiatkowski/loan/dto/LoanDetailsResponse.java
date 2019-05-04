package pl.kkwiatkowski.loan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kkwiatkowski.loan.dao.Loan;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LoanDetailsResponse extends EnhancedLoan {

    private transient Integer userId;
    
    private UserDetails user;

}
