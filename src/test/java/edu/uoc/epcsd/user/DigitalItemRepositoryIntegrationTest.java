package edu.uoc.epcsd.user;
import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalItemStatus;
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.repository.DigitalItemRepository;
import edu.uoc.epcsd.user.domain.repository.DigitalSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DigitalItemRepositoryIntegrationTestConfig.class)

public class DigitalItemRepositoryIntegrationTest {

    @Autowired
    private DigitalItemRepository digitalItemRepository;
    @Autowired
    private DigitalSessionRepository digitalSessionRepository;

    @Test
    void whenFindBySession_thenReturnDigitalItems() {

        // Given
        // Digital session
        final Long DS_ID = 1L;
        final String DS_DESCRIPTION = "Description descr descr";
        final String DS_LOCATION = "Location locat locat locat";
        final String DS_LINK = "https://www.link.com";
        final Long DS_USERID = 1L;

        // Digital item
        final Long DI_ID = 1L;
        final Long DI_DIGITALSESSIONID = 1L;
        final String DI_DESCRIPTION = "Description descr descr";
        final Long DI_LAT = 6568743L;
        final Long DI_LON = 98894L;
        final String DI_LINK = "https://www.link.com";
        final DigitalItemStatus DI_STATUS = DigitalItemStatus.AVAILABLE;

        // When
        // Create digital session
        DigitalSession digitalSession =
            DigitalSession.builder().
                id(DS_ID).
                description(DS_DESCRIPTION).
                location(DS_LOCATION).
                link(DS_LINK).
                userId(DS_USERID).
                build();
        digitalSessionRepository.createDigitalSession(digitalSession);

        // Create digital item
        DigitalItem digitalItem =
            DigitalItem.builder().
                id(DI_ID).
                digitalSessionId(DI_DIGITALSESSIONID).
                description(DI_DESCRIPTION).
                lat(DI_LAT).
                lon(DI_LON).
                link(DI_LINK).
                status(DI_STATUS).
                build();
        digitalItemRepository.createDigitalItem(digitalItem);

        // Get first digital item (since there's only one)
        DigitalItem fromDb = digitalItemRepository.findDigitalItemBySession(DI_DIGITALSESSIONID).get(0);

        // Then
        assertThat(fromDb).isEqualTo(digitalItem);
    }
}