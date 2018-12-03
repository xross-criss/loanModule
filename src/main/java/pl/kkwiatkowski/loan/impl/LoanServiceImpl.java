package pl.kkwiatkowski.loan.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kkwiatkowski.loan.dto.LoanRequest;
import pl.kkwiatkowski.loan.dto.LoanResponse;

@Service
@Slf4j
public class LoanServiceImpl {

    public LoanResponse applyForLoan(LoanRequest request) {
        checkLoanTerms(request);
        return processLoan(request);
    }

    private LoanResponse processLoan(LoanRequest request) {
        return null;
    }

    private void checkLoanTerms(LoanRequest request) {

    }
}