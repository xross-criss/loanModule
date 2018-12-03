package pl.kkwiatkowski.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.kkwiatkowski.loan.dto.LoanRequest;
import pl.kkwiatkowski.loan.dto.LoanResponse;
import pl.kkwiatkowski.loan.impl.LoanServiceImpl;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanServiceImpl loanService;

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public LoanResponse getProspectById(@PathVariable("prospectId") LoanRequest request) {
        return loanService.applyForLoan(request);
    }
}
