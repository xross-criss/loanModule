package pl.kkwiatkowski.loan.util;

import pl.kkwiatkowski.loan.dao.User;
import pl.kkwiatkowski.loan.dto.UserDetails;
import pl.kkwiatkowski.loan.mappers.CreditHistoryCategoryMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        userDetails.setCreditHistoryId(user.getCreditHistoryId());
        userDetails.setScore(user.getScore());
        userDetails.setScoreScale(user.getScoreScale());

        List<String> catList = Arrays.asList(user.getHistoryLoanCategories().split(","));
        userDetails.setHistoryLoanCategories(catList.stream().map(CreditHistoryCategoryMapper.HISTROY_CATEGORIES::get).collect(Collectors.toList()));

        return userDetails;
    }
}
