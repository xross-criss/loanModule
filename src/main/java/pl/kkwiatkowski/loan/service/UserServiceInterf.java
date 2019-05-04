package pl.kkwiatkowski.loan.service;

import pl.kkwiatkowski.loan.dto.UserDetails;

import java.util.List;

public interface UserServiceInterf {

    UserDetails getUserDetails(Integer userId);

    UserDetails createUser(UserDetails request);

    List<UserDetails> getUsersByName(String userName);

    List<UserDetails> addUsers(List<UserDetails> detailsList);

}
