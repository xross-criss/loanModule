package pl.kkwiatkowski.loan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Issued loan term is too large")
public class LoanDurationTooLongException extends RuntimeException {
}
