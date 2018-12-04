package pl.kkwiatkowski.loan.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkwiatkowski.loan.constants.Constants;
import pl.kkwiatkowski.loan.dto.ApplyLoanRequest;
import pl.kkwiatkowski.loan.dto.Loan;
import pl.kkwiatkowski.loan.exceptions.*;
import pl.kkwiatkowski.loan.repository.LoanRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static pl.kkwiatkowski.loan.constants.Constants.SYSTEM_OFF_PERIOD_END;
import static pl.kkwiatkowski.loan.constants.Constants.SYSTEM_OFF_PERIOD_START;

@Slf4j
@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan applyForLoan(ApplyLoanRequest request) {
        LocalDateTime requestDate = LocalDateTime.now();

        checkLoanTerms(request, requestDate.toLocalTime());
        return processLoan(request, requestDate);
    }

    private Loan processLoan(ApplyLoanRequest request, LocalDateTime requestDate) {
        return loanRepository.save(
                Loan.builder()
                        .loanIssuedDate(requestDate)
                        .loanAmount(request.getIssuedAmount())
                        .loanTerm(requestDate.plus(request.getIssuedDuration()))
                        .repaymentAmount(request.getIssuedAmount().multiply(Constants.INTEREST_PERCENTAGE))
                        .build()
        );
    }

    private void checkLoanTerms(ApplyLoanRequest request, LocalTime requestTime) {
        if (SYSTEM_OFF_PERIOD_START.isAfter(requestTime) && SYSTEM_OFF_PERIOD_END.isBefore(requestTime) && request.getIssuedAmount().equals(Constants.MAX_AMOUNT)) {
            throw new LoanCannotBeIssuedException();
        }
        if (request.getIssuedAmount().compareTo(Constants.MIN_AMOUNT) < 0) {
            throw new AmountTooLowException();
        }
        if (request.getIssuedAmount().compareTo(Constants.MAX_AMOUNT) > 0) {
            throw new AmountTooBigException();
        }
        if (request.getIssuedDuration().toDays() < Constants.MIN_LOAN_DURATION.toDays()) {
            throw new LoanDurationTooShortException();
        }
        if (request.getIssuedDuration().toDays() > Constants.MAX_LOAN_DURATION.toDays()) {
            throw new LoanDurationTooLongException();
        }

    }

    public Loan extendLoan(Integer loanId) {
        LocalDateTime requestDate = LocalDateTime.now();
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (!loan.isPresent()) {
            throw new LoanDoesntExistsException();
        }
        return extendLoanResponse(loan.get(), requestDate);
    }

    private Loan extendLoanResponse(Loan request, LocalDateTime requestDate) {
        return loanRepository.save(Loan.builder()
                .loanTerm(request.getLoanTerm().plus(Constants.DURATION_OF_EXTENSION))
                .lastExtendDate(requestDate)
                .build());
    }
}