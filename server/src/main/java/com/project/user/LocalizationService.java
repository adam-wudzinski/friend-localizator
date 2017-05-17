package com.project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
