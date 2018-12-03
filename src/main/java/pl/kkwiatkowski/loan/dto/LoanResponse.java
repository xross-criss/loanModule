package pl.kkwiatkowski.loan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoanResponse {
    private BigDecimal loanAmount;
    private BigDecimal repaymentAmount;
    private DateTime loanActionDate;
    private DateTime loanTerm;
}



/*
    REST interface
define max/min amount and max/min term (days)
        no installments
        available operations are:
        apply_for_loan (term, amount)
        if application is not within amount/term range reject application
        if application is between 00:00 and 06:00 and max amount is asked then reject application
        issued loan has 10% of principal (not 10% per year)
        extend loan - (extension term is preconfigured. Upon extension the due date is changed, original due date + term)
        junit tests
        integration success path scenario test (loan issued)
        post the solution to a public either github or bitbucket repo
        no GUI
        no authorization
        no users
        SOLID etc

*/
