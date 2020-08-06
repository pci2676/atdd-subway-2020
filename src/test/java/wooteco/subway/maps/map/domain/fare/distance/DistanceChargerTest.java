package wooteco.subway.maps.map.domain.fare.distance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.subway.maps.map.domain.fare.distance.DistanceCharger.OVER_FIFTY;
import static wooteco.subway.maps.map.domain.fare.distance.DistanceCharger.TEN_TO_FIFTY;

class DistanceChargerTest {

    private static Stream<Arguments> discountChargerProvier() {
        return Stream.of(
                Arguments.arguments(11, new DistanceChargers(Arrays.asList(TEN_TO_FIFTY))),
                Arguments.arguments(50, new DistanceChargers(Arrays.asList(TEN_TO_FIFTY))),
                Arguments.arguments(51, new DistanceChargers(Arrays.asList(TEN_TO_FIFTY, OVER_FIFTY)))
        );
    }

    @DisplayName("알맞는 과금 정책 찾기")
    @ParameterizedTest
    @MethodSource(value = "discountChargerProvier")
    void findByDistance(int distance, DistanceChargers expect) {
        //given
        DistanceChargers distanceChargers = DistanceCharger.findByDistance(distance);

        //then
        assertThat(distanceChargers).isEqualTo(expect);
    }

    @DisplayName("과금 정책에 따른 추가 과금 구하기")
    @ParameterizedTest
    @CsvSource(value = {
            "TEN_TO_FIFTY,11,0",
            "TEN_TO_FIFTY,15,100",
            "TEN_TO_FIFTY,20,200",
            "OVER_FIFTY,51,0",
            "OVER_FIFTY,58,100",
            "OVER_FIFTY,66,200"}
    )
    void charge(DistanceCharger distanceCharger, int distance, int expectCharge) {
        int charge = distanceCharger.charge(distance);

        assertThat(charge).isEqualTo(expectCharge);
    }

    @DisplayName("거리에 따른 전체 과금의 합 구하기")
    @ParameterizedTest
    @CsvSource(value = {"11,0", "15,100", "50,800", "58,900"})
    void charge(int distance, int expectFare) {
        //given
        DistanceChargers distanceChargers = DistanceCharger.findByDistance(distance);

        //when
        int fare = distanceChargers.calculateCharge(distance);

        //then
        assertThat(fare).isEqualTo(expectFare);
    }
}