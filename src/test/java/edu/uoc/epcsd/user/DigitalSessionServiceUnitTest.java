package edu.uoc.epcsd.user;
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.User;
import edu.uoc.epcsd.user.domain.repository.DigitalSessionRepository;
import edu.uoc.epcsd.user.domain.exception.UserNotFoundException;
import edu.uoc.epcsd.user.domain.repository.UserRepository;
import edu.uoc.epcsd.user.domain.service.DigitalSessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DigitalSessionServiceUnitTest {
    @InjectMocks
    private DigitalSessionServiceImpl digitalSessionService;
    @Mock
    private DigitalSessionRepository digitalSessionRepository;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); }
    @Test
    public void whenFindDigitalSessionByUser_withValidUserId_thenReturnDigitalSessionsList() {
        // Given
        final Long USERID = 5L;
        User user = new User();
        user.setId(USERID);

        final Long ID1 = 1L;
        final String DESCRIPTION1 = "Description desc desc desc";
        final String LOCATION1 = "Location locat locat locat";
        final String LINK1 = "https://www.gegegerterger.com";
        final Long USERID1 = USERID;

        final Long ID2 = 2L;
        final String DESCRIPTION2 = "Description desc desc desc";
        final String LOCATION2 = "Location locat locat locat";
        final String LINK2 = "https://www.gregerbrgnrmnrty.com";
        final Long USERID2 = USERID;

        DigitalSession digitalSession1 = new DigitalSession(ID1, DESCRIPTION1, LOCATION1, LINK1, USERID1);
        DigitalSession digitalSession2 = new DigitalSession(ID2, DESCRIPTION2, LOCATION2, LINK2, USERID2);
        List<DigitalSession> expectedSessions = Arrays.asList(digitalSession1, digitalSession2);

        when(
            userRepository.
            findUserById(USERID)).
            thenReturn(Optional.of(user)
        );

        when(
            digitalSessionRepository.
            findDigitalSessionByUser(USERID)).
            thenReturn(expectedSessions);

        // When
        List<DigitalSession> actualDigitalSession = digitalSessionService.findDigitalSessionByUser(USERID);
        // Then
        assertEquals(expectedSessions, actualDigitalSession);
        verify(userRepository, times(1)).findUserById(USERID);
        verify(digitalSessionRepository, times(1)).findDigitalSessionByUser(USERID);
    }

}