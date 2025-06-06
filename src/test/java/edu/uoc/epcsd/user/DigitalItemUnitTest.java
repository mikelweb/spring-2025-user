package edu.uoc.epcsd.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalItemStatus;

@SpringBootTest
class DigitalItemUnitTest {

    @Test
    public void newDigitalItemStatusAvailableUnitTest() {
        // Given
        Long id = 1L;
        Long digitalSessionId = 1L;
        String description = "Description descr descr descr";
        Long lat = 2L;
        Long lon = 3L;
        String link = "https://www.hgfcdyhgbkjnk.com/jkbcefjkcbefjkbcv";
        // When
        DigitalItem digitalItem = DigitalItem.builder() .id(id)
                .digitalSessionId(digitalSessionId)
                .description(description)
                .lat(lat)
                .lon(lon)
                .link(link)
                .build();
        // Then
        assertThat(digitalItem.getStatus()).isEqualTo(DigitalItemStatus.AVAILABLE); }
}