package com.project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Users service class
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Saves User to database
     * @param user
     */
    public void saveUser(User user){
        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
        }
    }

    /**
     * Finds users by name
     * @param name
     * @return
     */
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

    /**
     * Makes logged user friend with other user
     * @param id other user id
     * @param loggedUser logged user object
     * @return list of logged users friends
     */
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

    /**
     * Find all friends of logged user
     * @param loggedUser logged user object
     * @return list of users friends
     */
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

    /**
     * Finds user by email
     * @param email
     * @return
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Unmake logged user friend with other user
     * @param loggedUser logged user object
     * @param id other user id
     * @return list of logged users friends
     */
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

    /**
     * Finds all people not friended with logged user, searched by name
     * @param loggedUser logged user object
     * @param name
     * @return
     */
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


    private boolean isFriend(User loggedUser, User u) {
        return loggedUser.isFriendWith(u.getId())
                || u.getId() == loggedUser.getId();
    }

}
