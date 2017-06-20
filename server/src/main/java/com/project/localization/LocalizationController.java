package com.project.localization;

import com.project.user.User;
import com.project.user.UserDTO;
import com.project.user.UserService;
import com.project.utils.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Localizations endpoints
 */
@RestController
@RequestMapping("/api/localization")
public class LocalizationController {

    @Autowired
    private UserService userService;
    @Autowired
    private LocalizationService localizationService;
    @Autowired
    private AuthService authService;

    /**
     * Endpoint - updates logged user localization
     * @return user with updated localization
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<LocalizedUserDTO> updateLocalization(@RequestBody LocalizationDTO localization){
        User user = localizationService.updateLocalization(getLoggedUser(), localization);
        return new ResponseEntity<>(new LocalizedUserDTO(user), HttpStatus.OK);
    }

    /**
     * Endpoint - gets all localizations of logged user friends
     * @return user with updated localization
     */
    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public List<LocalizedUserDTO> getLocalizedFriends(){
        return localizationService.findLocalizedFriends(getLoggedUser());
    }

    /**
     * Endpoint - enables sharing logged users localization with other user
     * @param id other user id
     * @return list of users that logged users shares localizations with
     */
    @RequestMapping(value="/share", method = RequestMethod.POST)
    public List<UserDTO>  shareLocalization(@RequestParam(value = "id", required = true) Long id){
        return localizationService.shareLocalization(getLoggedUser(), id);
    }

    /**
     * Endpoint - disables sharing logged users localization with other user
     * @param id other user id
     * @return list of users that logged users shares localizations with
     */
    @RequestMapping(value="/share", method = RequestMethod.DELETE)
    public List<UserDTO>  unshareLocalization(@RequestParam(value = "id", required = true) Long id){
        return localizationService.unshareLocalization(getLoggedUser(), id);
    }

    private User getLoggedUser() {
        String email = authService.getLoggedUserEmail();
        return userService.findByEmail(email);

    }
}
