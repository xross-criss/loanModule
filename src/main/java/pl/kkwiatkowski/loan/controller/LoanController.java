package pl.kkwiatkowski.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kkwiatkowski.loan.dto.LoanRequest;
import pl.kkwiatkowski.loan.dto.LoanResponse;
import pl.kkwiatkowski.loan.impl.LoanServiceImpl;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanServiceImpl loanService;

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public LoanResponse getProspectById(@RequestBody LoanRequest request) {
        return loanService.applyForLoan(request);
    }
}
