package pl.kkwiatkowski.loan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "User List is empty!")
public class UserListIsNullException extends RuntimeException{
}