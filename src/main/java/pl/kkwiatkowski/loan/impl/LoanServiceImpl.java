package pl.kkwiatkowski.loan.impl;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.stereotype.Service;
import pl.kkwiatkowski.loan.constants.Constants;
import pl.kkwiatkowski.loan.dto.LoanRequest;
import pl.kkwiatkowski.loan.dto.LoanResponse;

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
                .loanActionDate(requestDate)
                .loanAmount(request.getIssuedAmount())
                .loanTerm(requestDate.plus(request.getIssuedDuration()))
                .repaymentAmount(request.getIssuedAmount().multiply(Constants.interestPercentage))
                .build();
    }

    private void checkLoanTerms(LoanRequest request, DateTime requestDate) {
        DateTime startPeriod = DateTime.now().withTime(Constants.systemOffStartPeriodHours.getHours(), Constants.systemOffStartPeriodMinutes.getMinutes(), 0, 0);
        DateTime endPeriod = DateTime.now().withTime(Constants.systemOffEndPeriodHours.getHours(), Constants.systemOffEndPeriodMinutes.getMinutes(), 0, 0);

        if (startPeriod.isAfter(requestDate) && endPeriod.isBefore(requestDate) && request.getIssuedAmount().equals(Constants.maxAmount)) {
            throw new LoanCannotBeIssuedException();
        }
        if (request.getIssuedAmount().compareTo(Constants.minAmount) == -1) {
            throw new AmountTooLowException();
        }
        if (request.getIssuedAmount().compareTo(Constants.maxAmount) == 1) {
            throw new AmountTooBigException();
        }
        if (request.getIssuedDuration().getStandardDays() < Constants.minLoanDuration.getStandardDays()) {
            throw new LoanDurationTooShortException();
        }
        if (request.getIssuedDuration().getStandardDays() > Constants.maxLoanDuration.getStandardDays()) {
            throw new LoanDurationTooLongException();
        }

    }
}