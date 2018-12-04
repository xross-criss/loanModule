package pl.kkwiatkowski.loan.impl;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import pl.kkwiatkowski.loan.constants.Constants;
import pl.kkwiatkowski.loan.dto.Loan;
import pl.kkwiatkowski.loan.dto.LoanRequest;
import pl.kkwiatkowski.loan.dto.LoanResponse;
import pl.kkwiatkowski.loan.exceptions.*;

import java.util.UUID;

@Slf4j
@Service
public class LoanServiceImpl {

    public LoanResponse applyForLoan(LoanRequest request) {
        DateTime requestDate = DateTime.now();

        checkLoanTerms(request, requestDate);
        return processLoan(request, requestDate);
    }

    private LoanResponse processLoan(LoanRequest request, DateTime requestDate) {
        return LoanResponse.builder()
                .loanId(UUID.randomUUID().toString()) // to oczywiscie powinno byc spiete z baza
                .loanIssuedDate(requestDate)
                .loanAmount(request.getIssuedAmount())
                .loanTerm(requestDate.plus(request.getIssuedDuration()))
                .repaymentAmount(request.getIssuedAmount().multiply(Constants.INTEREST_PERCENTAGE))
                .build();
    }

    private void checkLoanTerms(LoanRequest request, DateTime requestDate) {
        DateTime startPeriod = DateTime.now().withTime(Constants.SYSTEM_OFF_START_PERIOD_HOURS.getHours(), Constants.SYSTEM_OFF_START_PERIOD_MINUTES.getMinutes(), 0, 0);
        DateTime endPeriod = DateTime.now().withTime(Constants.SYSTEM_OFF_END_PERIOD_HOURS.getHours(), Constants.SYSTEM_OFF_END_PERIOD_MINUTES.getMinutes(), 0, 0);

        if (startPeriod.isAfter(requestDate) && endPeriod.isBefore(requestDate) && request.getIssuedAmount().equals(Constants.MAX_AMOUNT)) {
            throw new LoanCannotBeIssuedException();
        }
        if (request.getIssuedAmount().compareTo(Constants.MIN_AMOUNT) < 0) {
            throw new AmountTooLowException();
        }
        if (request.getIssuedAmount().compareTo(Constants.MAX_AMOUNT) > 0) {
            throw new AmountTooBigException();
        }
        if (request.getIssuedDuration().getStandardDays() < Constants.MIN_LOAN_DURATION.getStandardDays()) {
            throw new LoanDurationTooShortException();
        }
        if (request.getIssuedDuration().getStandardDays() > Constants.MAX_LOAN_DURATION.getStandardDays()) {
            throw new LoanDurationTooLongException();
        }

    }

    public LoanResponse extendLoan(Loan request) {
        DateTime requestDate = DateTime.now();

        if (request.getLoanId() == null) {
            throw new LoanDoesntExistsException();
        }

        return extendLoanResponse(request, requestDate);
    }

    private LoanResponse extendLoanResponse(Loan request, DateTime requestDate) {
        return LoanResponse.builder()
                .loanId(request.getLoanId())
                .repaymentAmount(request.getLoanAmount())
                .loanTerm(request.getLoanTerm())
                .loanAmount(request.getLoanAmount())
                .loanIssuedDate(request.getLoanIssuedDate())
                .lastExtendDate(requestDate)
                .build();
    }
}