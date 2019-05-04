package pl.kkwiatkowski.loan.dto;

import pl.kkwiatkowski.loan.dao.Loan;

import java.time.LocalDateTime;

public abstract class EnhancedLoan extends Loan {

    @Override
    public LocalDateTime getLastExtendDate() { // TODO - Przesłanianie
        return super.getLastExtendDate() != null ? super.getLastExtendDate() : this.getLoanIssuedDate();
    }

}
