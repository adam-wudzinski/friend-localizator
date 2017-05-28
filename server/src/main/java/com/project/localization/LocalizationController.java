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
 * Created by Adas on 2017-05-17.
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

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<LocalizedUserDTO> updateLocalization(@RequestBody LocalizationDTO localization){
        User user = localizationService.updateLocalization(getLoggedUser(), localization);
        return new ResponseEntity<>(new LocalizedUserDTO(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public List<LocalizedUserDTO> getLocalizedFriends(){
        return localizationService.findLocalizedFriends(getLoggedUser());
    }

    @RequestMapping(value="/share", method = RequestMethod.POST)
    public List<UserDTO>  shareLocalization(@RequestParam(value = "id", required = true) Long id){
        return localizationService.shareLocalization(getLoggedUser(), id);
    }

    @RequestMapping(value="/share", method = RequestMethod.DELETE)
    public List<UserDTO>  unshareLocalization(@RequestParam(value = "id", required = true) Long id){
        return localizationService.unshareLocalization(getLoggedUser(), id);
    }

    private User getLoggedUser() {
        String email = authService.getLoggedUserEmail();
        return userService.findByEmail(email);

    }
}
