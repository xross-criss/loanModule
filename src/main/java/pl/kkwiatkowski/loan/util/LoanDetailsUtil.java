package pl.kkwiatkowski.loan.util;

import pl.kkwiatkowski.loan.dao.Loan;
import pl.kkwiatkowski.loan.dto.LoanDetailsResponse;
import pl.kkwiatkowski.loan.dto.LoanResponse;
import pl.kkwiatkowski.loan.dto.UserDetails;

import java.time.LocalDateTime;
import java.util.Optional;

public class LoanDetailsUtil {
    private LoanDetailsUtil() {
    }

    public static LoanDetailsResponse convertToLoanDetails(LoanResponse loan, UserDetails user) {
        LoanDetailsResponse loanDetailsResponse = new LoanDetailsResponse();

        loanDetailsResponse.setLoanId(loan.getLoanId());
        loanDetailsResponse.setLoanAmount(loan.getLoanAmount());
        loanDetailsResponse.setRepaymentAmount(loan.getRepaymentAmount());
        loanDetailsResponse.setLoanTerm(loan.getLoanTerm());
        loanDetailsResponse.setLoanIssuedDate(loan.getLoanIssuedDate());
        loanDetailsResponse.setLastExtendDate(loan.getLastExtendDate() != null ? loan.getLastExtendDate() : loan.getLoanIssuedDate());
        loanDetailsResponse.setUser(user);

        return loanDetailsResponse;
    }

    public static LoanResponse convertToLoanResponse(Optional<Loan> loan) {
        return convertToLoanResponse(loan.get());
    }

    public static LoanResponse convertToLoanResponse(Loan loan) {
        LoanResponse loanResponse = new LoanResponse();

        loanResponse.setLoanId(loan.getLoanId());
        loanResponse.setLoanAmount(loan.getLoanAmount());
        loanResponse.setRepaymentAmount(loan.getRepaymentAmount());
        loanResponse.setLoanTerm(loan.getLoanTerm());
        loanResponse.setLoanIssuedDate(loan.getLoanIssuedDate());
        loanResponse.setLastExtendDate(loan.getLastExtendDate() != null ? loan.getLastExtendDate() : loan.getLoanIssuedDate());
        loanResponse.setUserId(loan.getUserId());

        return loanResponse;
    }
}
