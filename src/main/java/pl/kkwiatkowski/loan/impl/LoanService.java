package pl.kkwiatkowski.loan.impl;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkwiatkowski.loan.constants.Constants;
import pl.kkwiatkowski.loan.dto.ApplyLoanRequest;
import pl.kkwiatkowski.loan.dto.Loan;
import pl.kkwiatkowski.loan.exceptions.*;
import pl.kkwiatkowski.loan.repository.LoanRepository;

@Slf4j
@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan applyForLoan(ApplyLoanRequest request) {
        DateTime requestDate = DateTime.now();

        checkLoanTerms(request, requestDate);
        return processLoan(request, requestDate);
    }

    private Loan processLoan(ApplyLoanRequest request, DateTime requestDate) {
        return loanRepository.save(
                Loan.builder()
                        .loanIssuedDate(requestDate)
                        .loanAmount(request.getIssuedAmount())
                        .loanTerm(requestDate.plus(request.getIssuedDuration()))
                        .repaymentAmount(request.getIssuedAmount().multiply(Constants.INTEREST_PERCENTAGE))
                        .build()
        );
    }

    private void checkLoanTerms(ApplyLoanRequest request, DateTime requestDate) {
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

    public Loan extendLoan(String loanId) {
        DateTime requestDate = DateTime.now();
        Loan loan = loanRepository.findOne(loanId);

        return extendLoanResponse(loan, requestDate);
    }

    private Loan extendLoanResponse(Loan request, DateTime requestDate) {

        return loanRepository.save(Loan.builder()
                .loanTerm(request.getLoanTerm().plus(Constants.DURATION_OF_EXTENSION))
                .lastExtendDate(requestDate)
                .build());
    }
}