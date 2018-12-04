package pl.kkwiatkowski.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kkwiatkowski.loan.dto.ApplyLoanRequest;
import pl.kkwiatkowski.loan.dto.Loan;
import pl.kkwiatkowski.loan.impl.LoanService;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @RequestMapping(value = "/apply_for_loan", method = RequestMethod.POST)
    public Loan applyForLoan(@RequestBody ApplyLoanRequest request) {
        return loanService.applyForLoan(request);
    }

    @RequestMapping(value = "/extend_loan/{loanId}", method = RequestMethod.POST)
    public Loan extendLoan(@PathVariable("loanId") Integer loanId) {
        return loanService.extendLoan(loanId);
    }
}
