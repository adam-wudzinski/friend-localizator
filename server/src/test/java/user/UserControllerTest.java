package user;

import com.project.user.User;
import com.project.user.UserController;
import com.project.user.UserRepository;
import com.project.user.UserService;
import com.project.utils.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Adas on 2017-05-28.
 */
public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private AuthService authService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void register_callsRegisterOnService(){
        User user = new User("test@test.pl","test");

        userController.register(user);

        verify(userService).saveUser(user);
    }

    @Test
    public void register_returnsNewUser(){
        User user = new User("test@test.pl","test");

        ResponseEntity<User> result = userController.register(user);

        assertEquals(result, new ResponseEntity<User>(user, HttpStatus.OK));
    }

    @Test
    public void getUsers_callsFindUsersNotFriendedWith(){
        String searchValue = "search";

        userController.getUsers(searchValue);

        verify(userService).findUsersNotFriendedWith(anyObject(), eq(searchValue));
    }

    @Test
    public void getUsers_callsAuthServiceToDetermineLoggedUser(){
        String searchValue = "search";

        userController.getUsers(searchValue);

        verify(userService).findUsersNotFriendedWith(anyObject(), eq(searchValue));
        verify(authService).getLoggedUserEmail();
    }

    @Test
    public void getFriends_callsAuthServiceToDetermineLoggedUser(){
        userController.getFriends();

        verify(authService).getLoggedUserEmail();
    }

    @Test
    public void getFriends_callsUserService(){
        userController.getFriends();

        verify(userService).findUserFriends(anyObject());
    }

    @Test
    public void addFriend_callsAuthServiceToDetermineLoggedUser(){
        userController.addFriend(1l);

        verify(authService).getLoggedUserEmail();
    }

    @Test
    public void addFriend_callsUserServiceWithProperParams(){
        userController.addFriend(13l);

        verify(userService).friendUsers(eq(13l), anyObject());
    }

    @Test
    public void deleteFriend_callsAuthServiceToDetermineLoggedUser(){
        userController.deleteFriend(1l);

        verify(authService).getLoggedUserEmail();
    }

    @Test
    public void deleteFriend_callsUserServiceWithProperParams(){
        userController.deleteFriend(13l);

        verify(userService).unfriendUsers(anyObject(), eq(13l));
    }
}
