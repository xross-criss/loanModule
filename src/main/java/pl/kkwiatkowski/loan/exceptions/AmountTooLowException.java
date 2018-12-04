package pl.kkwiatkowski.loan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Amount is too low!")
public class AmountTooLowException extends RuntimeException{
}
