package com.project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Adas on 2017-04-18.
 */
@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody User user){
        if (user != null) {
            userService.saveUser(user);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value="/api/users", method = RequestMethod.GET)
    public List<UserDTO> getUsers(@RequestParam(value = "search", required = false) String search ){
        return userService.findUsers(search);
    }

}
