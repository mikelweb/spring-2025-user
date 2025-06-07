package edu.uoc.epcsd.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalItemStatus;

@SpringBootTest
class DigitalItemUnitTest {

    @Test
    public void henCreatingDigitalItem_thenStatusShouldBeAvailable() {
        // Given
        final Long ID = 1L;
        final Long DIGITALSESSIONID = 13L;
        final String DESCRIPTION = "Description descr descr descr";
        final Long LAT = 5224L;
        final Long LON = 3423L;
        final String LINK = "https://www.hgfcdyhgbkjnk.com/jkbcefjkcbefjkbcv";
        // When
        DigitalItem digitalItem = DigitalItem.builder()
                .id(ID)
                .digitalSessionId(DIGITALSESSIONID)
                .description(DESCRIPTION)
                .lat(LAT)
                .lon(LON)
                .link(LINK)
                .build();
        // Then
        assertThat(digitalItem.getStatus()).isEqualTo(DigitalItemStatus.AVAILABLE); }
}