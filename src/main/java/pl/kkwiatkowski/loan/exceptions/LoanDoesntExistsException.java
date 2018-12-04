package pl.kkwiatkowski.loan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Loan doesnt exists or id not included in request!")
public class LoanDoesntExistsException extends RuntimeException {
}
