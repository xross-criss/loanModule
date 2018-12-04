package pl.kkwiatkowski.loan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "You havent met with requirements for the loan!")
public class LoanCannotBeIssuedException extends RuntimeException {
}
