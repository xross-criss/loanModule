package pl.kkwiatkowski.loan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetails extends CreditHistoryDTO { //TODO - ekstensja
    private Integer userId;
    private String firstName;
    private String secondName;
    private String lastName;
    private String phoneNumber;

}
