package pl.kkwiatkowski.loan.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kkwiatkowski.loan.dao.User;
import pl.kkwiatkowski.loan.dto.UserDetails;
import pl.kkwiatkowski.loan.exceptions.UserListIsNullException;
import pl.kkwiatkowski.loan.repository.UserRepository;
import pl.kkwiatkowski.loan.service.UserServiceInterf;
import pl.kkwiatkowski.loan.util.UserDetailsUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements UserServiceInterf {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails getUserDetails(Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        return UserDetailsUtils.convertToDetails(user);
    }

    @Override
    public UserDetails createUser(UserDetails request) {
        return UserDetailsUtils.convertToDetails(saveUser(request));
    }

    private User saveUser(UserDetails request) {
        return userRepository.save(
                User.builder()
                        .firstName(request.getFirstName())
                        .secondName(request.getSecondName() != null ? request.getSecondName() : "")
                        .lastName(request.getLastName())
                        .phoneNumber(request.getPhoneNumber() != null ? request.getPhoneNumber() : "")
                        .build()
        );
    }

    @Override
    public List<UserDetails> getUsersByName(String userName) {
        List<User> users = userRepository.findAll();

        return users.stream()
                .filter(user ->
                        (user.getFirstName().equalsIgnoreCase(userName)) || (user.getSecondName().equalsIgnoreCase(userName)))
                .map(UserDetailsUtils::convertToDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDetails> addUsers(List<UserDetails> detailsList) {
        if (detailsList == null) {
            throw new UserListIsNullException();
        }

        return detailsList.stream().map(this::createUser).collect(Collectors.toList());
    }
}
