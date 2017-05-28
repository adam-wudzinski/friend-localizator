package localization;

import com.project.localization.LocalizationDTO;
import com.project.localization.LocalizationService;
import com.project.user.User;
import com.project.user.UserRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Adas on 2017-05-28.
 */
public class LocalizationServiceTest {

    @InjectMocks
    private LocalizationService service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;
    @Mock
    private User otherUser;
    @Mock
    private LocalizationDTO localization;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateLocalization_callsUserRepository() {
        service.updateLocalization(user, localization);

        verify(userRepository).save(user);
    }

    @Test
    public void updateLocalization_updatesUserWithLoc() {
        when(localization.getLatitude()).thenReturn(5d);
        when(localization.getLongitude()).thenReturn(3d);

        service.updateLocalization(user, localization);

        verify(user).setLatitude(5d);
        verify(user).setLongitude(3d);
    }

    @Test
    public void shareLocalization_callsUserRepository(){
        when(userRepository.findOne(any())).thenReturn(otherUser);
        when(user.isFriendWith(any())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        service.shareLocalization(user, 1l);

        verify(userRepository).save(user);
    }

    @Test
    public void shareLocalization_callsShareLocationWithOnUser(){
        when(userRepository.findOne(any())).thenReturn(otherUser);
        when(user.isFriendWith(any())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        service.shareLocalization(user, 1l);

        verify(user).shareLocationWith(otherUser);
    }

    @Test
    public void shareLocalization_ifOtherUserNotExitThenNotShare(){
        when(userRepository.findOne(any())).thenReturn(null);
        when(user.getShares()).thenReturn(new HashSet<User>());

        service.shareLocalization(user, 1l);

        verify(userRepository, never()).save(user);
        verify(user, never()).shareLocationWith(otherUser);
    }

    @Test
    public void shareLocalization_otherUserExistButNotFriendsThenNotShare(){
        when(userRepository.findOne(any())).thenReturn(otherUser);
        when(user.isFriendWith(any())).thenReturn(false);
        when(user.getShares()).thenReturn(new HashSet<User>());

        service.shareLocalization(user, 1l);

        verify(userRepository, never()).save(user);
        verify(user, never()).shareLocationWith(otherUser);
    }

    @Test
    public void unshareLocalization_callsUserRepository() {
        service.unshareLocalization(user, 1l);

        verify(userRepository).findOne(1l);
    }

    @Test
    public void unshareLocalization_unshresIfUsersExists(){
        when(userRepository.findOne(any())).thenReturn(otherUser);
        when(user.getShares()).thenReturn(new HashSet<User>());
        when(userRepository.save(user)).thenReturn(user);

        service.unshareLocalization(user, 1l);

        verify(user).unshareLocationWith(otherUser);
        verify(userRepository).save(user);
    }

    @Test
    public void findLocalizedFriends_callsUserRepositoryWithUserId(){
        when(user.getId()).thenReturn(1l);
        when(userRepository.findOne(1l)).thenReturn(user);
        user.friends = new HashSet<>();

        service.findLocalizedFriends(user);

        verify(user).getId();
        verify(userRepository).findOne(1l);
    }
}
