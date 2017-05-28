package localization;

import com.project.localization.LocalizationController;
import com.project.localization.LocalizationDTO;
import com.project.localization.LocalizationService;
import com.project.user.UserService;
import com.project.utils.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by Adas on 2017-05-28.
 */
public class LocalizationControllerTest {
    @InjectMocks
    private LocalizationController controller;
    @Mock
    private UserService userService;
    @Mock
    private LocalizationService localizationService;
    @Mock
    private AuthService authService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateLocalization_callsAuthServiceToDetermineLoggedUser(){
        controller.updateLocalization(new LocalizationDTO(10d,10d));

        verify(authService).getLoggedUserEmail();
    }

    @Test
    public void updateLocalization_callsLocalizationService(){
        LocalizationDTO localization = new LocalizationDTO(10d, 10d);
        controller.updateLocalization(localization);

        verify(localizationService).updateLocalization(anyObject(), eq(localization));
    }

    @Test
    public void getLocalizedFriends_callsAuthServiceToDetermineLoggedUser(){
        controller.getLocalizedFriends();

        verify(authService).getLoggedUserEmail();
    }

    @Test
    public void getLocalizedFriends_callsLocalizationService(){
        controller.getLocalizedFriends();

        verify(localizationService).findLocalizedFriends(anyObject());
    }

    @Test
    public void shareLocalization_callsAuthServiceToDetermineLoggedUser(){
        controller.shareLocalization(1l);

        verify(authService).getLoggedUserEmail();
    }

    @Test
    public void shareLocalization_callsLocalizationService(){
        controller.shareLocalization(1l);

        verify(localizationService).shareLocalization(anyObject(), eq(1l));
    }

    @Test
    public void unshareLocalization_callsAuthServiceToDetermineLoggedUser(){
        controller.unshareLocalization(1l);

        verify(authService).getLoggedUserEmail();
    }

    @Test
    public void unshareLocalization_callsLocalizationService(){
        controller.unshareLocalization(1l);

        verify(localizationService).unshareLocalization(anyObject(), eq(1l));
    }

}

