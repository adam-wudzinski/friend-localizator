package com.project.user;

import com.project.utils.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Users endpoints
 */
@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    /**
     * Endpoint - registers new user
     */
    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody User user){
        if (user != null) {
            userService.saveUser(user);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Endpoint - gets logged user
     */
    @RequestMapping(value="/api", method = RequestMethod.GET)
    public UserDTO getCurrentUser() {
        return new UserDTO(getLoggedUser());
    }

    /**
     * Endpoint - lists all users not friended with logged user, searched by search value
     */
    @RequestMapping(value="/api/users", method = RequestMethod.GET)
    public List<UserDTO> getUsers(@RequestParam(value = "search", required = false) String search ){
        return userService.findUsersNotFriendedWith(getLoggedUser(), search);
    }

    /**
     * Enpoint - lists all friends of logged user
     */
    @RequestMapping(value = "/api/friends", method = RequestMethod.GET)
    public List<UserDTO> getFriends(){
        return userService.findUserFriends(getLoggedUser());
    }

    /**
     * Endpoint - friends logged user with other user
     * @param id other user id
     * @return list of logged user friends
     */
    @RequestMapping(value="/api/friends", method = RequestMethod.POST)
    public List<UserDTO>  addFriend(@RequestParam(value = "id", required = true) Long id){
        return userService.friendUsers(id, getLoggedUser());
    }

    /**
     * Endpoint - unfriends logged user with other user
     * @param id other user id
     * @return list of logged user friends
     */
    @RequestMapping(value = "/api/friends", method = RequestMethod.DELETE)
    public List<UserDTO> deleteFriend(@RequestParam(value = "id", required = true) Long id){
        return userService.unfriendUsers(getLoggedUser(), id);
    }

    private User getLoggedUser() {
        String email = authService.getLoggedUserEmail();
        return userService.findByEmail(email);
    }

}
