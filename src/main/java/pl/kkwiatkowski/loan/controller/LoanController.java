package pl.kkwiatkowski.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kkwiatkowski.loan.dto.Loan;
import pl.kkwiatkowski.loan.dto.LoanRequest;
import pl.kkwiatkowski.loan.dto.LoanResponse;
import pl.kkwiatkowski.loan.impl.LoanServiceImpl;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanServiceImpl loanService;

    @RequestMapping(value = "/apply_for_loan", method = RequestMethod.POST)
    public LoanResponse appyForLoan(@RequestBody LoanRequest request) {
        return loanService.applyForLoan(request);
    }

    @RequestMapping(value = "/extend_loan", method = RequestMethod.POST)
    public LoanResponse extendLoan(@RequestBody Loan request) {
        return loanService.extendLoan(request);
    }
}
