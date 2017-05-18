package com.project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Adas on 2017-04-25.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
        }
    }

    public List<UserDTO> findUsers(String name) {
        if (name == null) {
            name = "";
        }
        return userRepository
                    .findBySurnameIgnoreCaseContaining(name)
                    .stream()
                    .map(UserDTO::new)
                    .collect(Collectors.toList());
    }

    public List<UserDTO> friendUsers(Long id, User loggedUser) {
        User toFriendWith = userRepository.findOne(id);
        if (usersExists(loggedUser, toFriendWith)) {
            friendUsers(loggedUser, toFriendWith);
        }
        return loggedUser
                .friends
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserDTO> findUserFriends(User loggedUser) {
        Set<User> friends = loggedUser.friends;
        if (friends == null) {
            friends = new HashSet<>();
        }
        return friends
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserDTO> unfriendUsers(User loggedUser, Long id) {
        User userToUnfriend = userRepository.findOne(id);
        if (usersExists(userToUnfriend, loggedUser)) {
            unfriendUsers(loggedUser, userToUnfriend);
        }
        return loggedUser
                .friends
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    private void friendUsers(User firstUser, User secondUser) {
        secondUser.friendWith(firstUser);
        firstUser.friendWith(secondUser);
        userRepository.save(secondUser);
        userRepository.save(firstUser);
    }

    private void unfriendUsers(User firstUser, User secondUser) {
        secondUser.unfriendWith(firstUser);
        firstUser.unfriendWith(secondUser);
        userRepository.save(secondUser);
        userRepository.save(firstUser);
    }

    private boolean usersExists(User logged, User toFriendWith) {
        return logged != null && toFriendWith != null;
    }


    public List<UserDTO> findUsersNotFriendedWith(User loggedUser, String name) {
        if (name == null) {
            name = "";
        }
        return userRepository
                .findBySurnameIgnoreCaseContaining(name)
                .stream()
                .filter(user -> !isFriend(loggedUser, user))
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    private boolean isFriend(User loggedUser, User u) {
        return loggedUser.isFriendWith(u.getId())
                || u.getId() == loggedUser.getId();
    }

}
