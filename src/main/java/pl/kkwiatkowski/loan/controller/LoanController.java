package pl.kkwiatkowski.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kkwiatkowski.loan.dto.ApplyLoanRequest;
import pl.kkwiatkowski.loan.dao.Loan;
import pl.kkwiatkowski.loan.dto.LoanDetailsResponse;
import pl.kkwiatkowski.loan.dto.LoanResponse;
import pl.kkwiatkowski.loan.impl.LoanService;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @RequestMapping(value = "/apply_for_loan", method = RequestMethod.POST)
    public LoanResponse applyForLoan(@RequestBody ApplyLoanRequest request) {
        return loanService.applyForLoan(request);
    }

    @RequestMapping(value = "/extend_loan/{loanId}", method = RequestMethod.POST)
    public LoanResponse extendLoan(@PathVariable("loanId") Integer loanId) {
        return loanService.extendLoan(loanId);
    }

    @RequestMapping(value = "/extend_loan_days/{loanId}/days/{days}", method = RequestMethod.POST)
    public LoanResponse extendLoan(@PathVariable("loanId") Integer loanId, @PathVariable("days") Integer days) {
        return loanService.extendLoan(loanId, days);
    }

    @RequestMapping(value = "/get_loan_by_id/{loanId}", method = RequestMethod.GET)
    public LoanDetailsResponse loanDetails(@PathVariable("loanId") Integer loanId) {
        return loanService.getLoanDetails(loanId);
    }

    @RequestMapping(value = "/registerLoans", method = RequestMethod.POST)
    public List<LoanResponse> registerLoans(@RequestBody List<ApplyLoanRequest> request) {
        return loanService.registerLoans(request);
    }
}
