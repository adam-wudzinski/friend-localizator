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
 * Created by Adas on 2017-05-17.
 */
@Service
public class LocalizationService {
    @Autowired
    private UserRepository userRepository;

    public User updateLocalization(User user, LocalizationDTO loc) {
        user.setLongitude(loc.getLongitude());
        user.setLatitude(loc.getLatitude());
        return userRepository.save(user);
    }

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
