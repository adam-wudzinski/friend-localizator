package com.project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Adas on 2017-04-25.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        if (userRepository.findByEmail(user.getEmail()) != null) {
            userRepository.save(user);
        }
    }

    public List<UserDTO> findUsers(String name) {
        if (name == null) {
            name = "";
        }
        return userRepository
                    .findByLastnameIgnoreCaseContaining(name)
                    .stream()
                    .map(UserDTO::new)
                    .collect(Collectors.toList());
    }

}
