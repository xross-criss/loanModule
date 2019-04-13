package pl.kkwiatkowski.loan.service;

import pl.kkwiatkowski.loan.dto.UserDetails;

import java.util.List;

public interface UserServiceInterf {

    public UserDetails getUserDetails(Integer userId);

    public UserDetails createUser(UserDetails request);

    List<UserDetails> getUsersByName(String userName);

    List<UserDetails> addUsers(List<UserDetails> detailsList);
}
