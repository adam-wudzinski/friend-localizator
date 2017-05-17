package com.project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value="/api", method = RequestMethod.GET)
    public UserDTO getCurrentUser() {
        return new UserDTO(getLoggedUser());
    }

    @RequestMapping(value="/api/users", method = RequestMethod.GET)
    public List<UserDTO> getUsers(@RequestParam(value = "search", required = false) String search ){
        return userService.findUsersNotFriendedWith(getLoggedUser(), search);
    }

    @RequestMapping(value = "/api/friends", method = RequestMethod.GET)
    public List<UserDTO> getFriends(){
        return userService.findUserFriends(getLoggedUser());
    }

    @RequestMapping(value="/api/friends", method = RequestMethod.POST)
    public List<UserDTO>  addFriend(@RequestParam(value = "id", required = true) Long id){
        return userService.friendUsers(id, getLoggedUser());
    }

    @RequestMapping(value = "/api/friends", method = RequestMethod.DELETE)
    public List<UserDTO> deleteFriend(@RequestParam(value = "id", required = true) Long id){
        return userService.unfriendUsers(getLoggedUser(), id);
    }

    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userService.findByEmail(email);

    }

}
