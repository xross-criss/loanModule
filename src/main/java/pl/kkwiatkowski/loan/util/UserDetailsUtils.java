package pl.kkwiatkowski.loan.util;

import pl.kkwiatkowski.loan.dao.User;
import pl.kkwiatkowski.loan.dto.UserDetails;

import java.util.Optional;

public class UserDetailsUtils {

    private UserDetailsUtils() {
    }


    public static UserDetails convertToDetails(Optional<User> userReq) {
        User user = userReq.get();

        return convertToDetails(user);
    }

    public static UserDetails convertToDetails(User user) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(user.getUserId());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setSecondName(user.getSecondName() != null ? user.getSecondName() : "");
        userDetails.setLastName(user.getLastName());
        userDetails.setPhoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : "");

        return userDetails;
    }
}
