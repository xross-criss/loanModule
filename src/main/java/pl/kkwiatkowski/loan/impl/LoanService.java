package pl.kkwiatkowski.loan.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkwiatkowski.loan.constants.Constants;
import pl.kkwiatkowski.loan.dao.Loan;
import pl.kkwiatkowski.loan.dto.ApplyLoanRequest;
import pl.kkwiatkowski.loan.dto.LoanDetailsResponse;
import pl.kkwiatkowski.loan.dto.LoanResponse;
import pl.kkwiatkowski.loan.dto.UserDetails;
import pl.kkwiatkowski.loan.exceptions.*;
import pl.kkwiatkowski.loan.repository.LoanRepository;
import pl.kkwiatkowski.loan.service.UserServiceInterf;
import pl.kkwiatkowski.loan.util.LoanDetailsUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.kkwiatkowski.loan.constants.Constants.SYSTEM_OFF_PERIOD_END;
import static pl.kkwiatkowski.loan.constants.Constants.SYSTEM_OFF_PERIOD_START;

@Slf4j
@Service
public class LoanService {

    public static final Integer DURATION_OF_EXTENSION = 30; //TODO - atrybut klasowy

    private final LoanRepository loanRepository;
    private UserServiceInterf userService;

    @Autowired
    public LoanService(LoanRepository loanRepository, UserServiceInterf userService) {
        this.loanRepository = loanRepository;
        this.userService = userService;
    }

    public LoanResponse applyForLoan(ApplyLoanRequest request) {
        LocalDateTime requestDate = LocalDateTime.now();

        checkLoanTerms(request, requestDate.toLocalTime());
        return processLoan(request, requestDate);
    }

    private LoanResponse processLoan(ApplyLoanRequest request, LocalDateTime requestDate) {
        Loan loan = saveLoan(request, requestDate);
        return LoanDetailsUtil.convertToLoanResponse(loan);
    }

    private Loan saveLoan(ApplyLoanRequest request, LocalDateTime requestDate) {
        return loanRepository.save(
                Loan.builder()
                        .loanIssuedDate(requestDate)
                        .loanAmount(request.getIssuedAmount())
                        .loanTerm(requestDate.plus(request.getIssuedDuration()))
                        .repaymentAmount(request.getIssuedAmount().multiply(Constants.INTEREST_PERCENTAGE))
                        .userId(request.getUserId())
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

    public LoanResponse extendLoan(Integer loanId) { //TODO - przeciążęnie I
        return extendLoan(loanId, DURATION_OF_EXTENSION);
    }

    public LoanResponse extendLoan(Integer loanId, Integer duration) { //TODO - przeciążęnie I
        LocalDateTime requestDate = LocalDateTime.now();
        Optional<Loan> loan = getLoanFromDB(loanId);
        if (!loan.isPresent()) {
            throw new LoanDoesntExistsException();
        }

        LocalDateTime extensionTerm = loan.get().getLoanTerm().plus(Duration.ofDays(duration));

        extendLoan(loan.get(), requestDate, extensionTerm);
        return LoanDetailsUtil.convertToLoanResponse(getLoanFromDB(loanId));
    }

    private Loan extendLoan(Loan request, LocalDateTime requestDate, LocalDateTime extensionTerm) {
        return loanRepository.save(Loan.builder()
                .loanId(request.getLoanId())
                .loanAmount(request.getLoanAmount())
                .repaymentAmount(request.getRepaymentAmount())
                .loanTerm(extensionTerm) // TODO - atrybut pochodny
                .loanIssuedDate(request.getLoanIssuedDate())
                .lastExtendDate(requestDate)
                .userId(request.getUserId())
                .build());
    }

    public LoanDetailsResponse getLoanDetails(Integer loanId) {
        Optional<Loan> loan = getLoanFromDB(loanId);


        LoanResponse loanResponse = LoanDetailsUtil.convertToLoanResponse(loan.get());

        UserDetails userDetails = userService.getUserDetails(loanResponse.getUserId());

        return LoanDetailsUtil.convertToLoanDetails(loanResponse, userDetails);
    }

    public List<LoanResponse> registerLoans(List<ApplyLoanRequest> request) {
        if (request == null) {
            throw new LoanListIsNullException();
        }
        return request.stream().map(this::applyForLoan).collect(Collectors.toList());
    }

    public Optional<Loan> getLoanFromDB(Integer loanId) {
        return loanRepository.findById(loanId);
    }

}
