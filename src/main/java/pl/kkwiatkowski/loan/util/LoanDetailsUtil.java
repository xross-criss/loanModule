package pl.kkwiatkowski.loan.util;

import pl.kkwiatkowski.loan.dao.Loan;
import pl.kkwiatkowski.loan.dto.LoanDetailsResponse;
import pl.kkwiatkowski.loan.dto.LoanResponse;
import pl.kkwiatkowski.loan.dto.UserDetails;

import java.util.Optional;

public class LoanDetailsUtil {

    private LoanDetailsUtil() {
    }

    public static LoanDetailsResponse convertToLoanDetails(LoanResponse loan, UserDetails user) {
        LoanDetailsResponse loanDetailsResponse = new LoanDetailsResponse();

        Loan.convert(loan, loanDetailsResponse);
        loanDetailsResponse.setUser(user);

        return loanDetailsResponse;
    }

    public static LoanResponse convertToLoanResponse(Optional<Loan> loan) {
        return convertToLoanResponse(loan.get());
    }

    public static LoanResponse convertToLoanResponse(Loan loan) {
        LoanResponse loanResponse = new LoanResponse();

        Loan.convert(loan, loanResponse);
        loanResponse.setUserId(loan.getUserId());

        return loanResponse;
    }

}
