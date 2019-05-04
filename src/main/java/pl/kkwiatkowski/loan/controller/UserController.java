package pl.kkwiatkowski.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kkwiatkowski.loan.dto.UserDetails;
import pl.kkwiatkowski.loan.service.UserServiceInterf;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceInterf userService;

    @RequestMapping(value = "/getUserById/{userId}", method = RequestMethod.GET)
    public UserDetails getUserDetails(@PathVariable("userId") Integer userId) {
        return userService.getUserDetails(userId);
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public UserDetails createUser(@RequestBody UserDetails request) {
        return userService.createUser(request);
    }

    @RequestMapping(value = "/getUserByName/{userName}", method = RequestMethod.GET)
    public List<UserDetails> getUserByName(@PathVariable("userName") String userName) {
        return userService.getUsersByName(userName); // TODO - atr. powt
    }

    @RequestMapping(value = "/addUsers", method = RequestMethod.POST)
    public List<UserDetails> addUsers(@RequestBody List<UserDetails> detailsList) {
        return userService.addUsers(detailsList);
    }
}
