package user;

import com.project.user.User;
import com.project.user.UserRepository;
import com.project.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Adas on 2017-05-28.
 */
public class UserServiceTest {

    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;
    @Mock
    private User otherUser;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveUser_callsSavesWhenEmailIsNotInDb(){
        when(userRepository.findByEmail(any())).thenReturn(null);

        service.saveUser(user);

        verify(userRepository).save(user);
    }

    @Test
    public void saveUser_notCallsSavesWhenEmailIsInDb(){
        when(userRepository.findByEmail(any())).thenReturn(otherUser);

        service.saveUser(user);

        verify(userRepository,never()).save(user);
    }

    @Test
    public void findUser_callsUserRepository(){
        service.findUsers("");

        verify(userRepository).findBySurnameIgnoreCaseContaining("");
    }

    @Test
    public void friendUsers_friendUsersIfThenExist(){
        when(userRepository.findOne(1l)).thenReturn(otherUser);
        user.friends = new HashSet<>();

        service.friendUsers(1l, user);

        verify(otherUser).friendWith(user);
        verify(user).friendWith(otherUser);
        verify(userRepository).save(user);
        verify(userRepository).save(otherUser);
    }

    @Test
    public void friendUsers_notFriendUSersIfOtherNotExist(){
        when(userRepository.findOne(1l)).thenReturn(null);
        user.friends = new HashSet<>();

        service.friendUsers(1l, user);

        verify(otherUser,never()).friendWith(user);
        verify(user,never()).friendWith(otherUser);
        verify(userRepository,never()).save(user);
        verify(userRepository,never()).save(otherUser);
    }

    @Test
    public void findByEmail_callsRepository(){
        service.findByEmail("email");

        verify(userRepository).findByEmail("email");
    }

    @Test
    public void unfriendUsers_unfriendUsersIfTheyExist(){
        when(userRepository.findOne(1l)).thenReturn(otherUser);
        user.friends = new HashSet<>();

        service.unfriendUsers(user, 1l);

        verify(otherUser).unfriendWith(user);
        verify(user).unfriendWith(otherUser);
        verify(userRepository).save(user);
        verify(userRepository).save(otherUser);
    }

    @Test
    public void unfriendUsers_doesntUnfriendUsersIfTheyExist(){
        when(userRepository.findOne(1l)).thenReturn(null);
        user.friends = new HashSet<>();

        service.unfriendUsers(user, 1l);

        verify(otherUser,never()).unfriendWith(user);
        verify(user,never()).unfriendWith(otherUser);
        verify(userRepository,never()).save(user);
        verify(userRepository,never()).save(otherUser);
    }


    @Test
    public void findUsersNotFriendedWith_callsRepository(){
        service.findUsersNotFriendedWith(user, "name");

        verify(userRepository).findBySurnameIgnoreCaseContaining("name");
    }
}
