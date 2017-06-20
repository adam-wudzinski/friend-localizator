package com.project.localization;

import com.project.user.User;
import com.project.user.UserDTO;
import com.project.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Localization service class
 */
@Service
public class LocalizationService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Updates users localization
     * @param user
     * @param localization
     * @return user with updated localization
     */
    public User updateLocalization(User user, LocalizationDTO localization) {
        user.setLongitude(localization.getLongitude());
        user.setLatitude(localization.getLatitude());
        return userRepository.save(user);
    }

    /**
     * Enables sharing logged users localization with other user
     * @param loggedUser logged user
     * @param id other user id
     * @return list of users that logged users shares localizations with
     */
    public List<UserDTO> shareLocalization(User loggedUser, Long id) {
        User toShareLocalizationWith = userRepository.findOne(id);
        if (usersExists(loggedUser, toShareLocalizationWith)
                && loggedUser.isFriendWith(id)) {
            loggedUser.shareLocationWith(toShareLocalizationWith);
            loggedUser = userRepository.save(loggedUser);
        }

        return loggedUser.getShares().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());

    }

    /**
     * Disables sharing logged users localization with other user
     * @param loggedUser logged user
     * @param id other user id
     * @return list of users that logged users shares localizations with
     */
    public List<UserDTO> unshareLocalization(User loggedUser, Long id) {
        User toUnShareLocalizationWith = userRepository.findOne(id);
        if (usersExists(loggedUser, toUnShareLocalizationWith)){
            loggedUser.unshareLocationWith(toUnShareLocalizationWith);
            loggedUser = userRepository.save(loggedUser);
        }
        return loggedUser.getShares().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Finds all of logged user friends localizations
     * @param loggedUser
     * @return list of firends with thier localizations
     */
    public List<LocalizedUserDTO> findLocalizedFriends(User loggedUser) {
        User user = userRepository.findOne(loggedUser.getId());
        return user.friends.stream()
                .filter(x -> x.isSharingLocationWith(user))
                .map(LocalizedUserDTO::new)
                .collect(Collectors.toList());
    }


    private boolean usersExists(User first, User second) {
        return first != null && second != null;
    }
}
