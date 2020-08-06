package wooteco.subway.maps.map.domain.fare.distance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.subway.maps.map.domain.fare.distance.DistanceCharger.OVER_FIFTY;
import static wooteco.subway.maps.map.domain.fare.distance.DistanceCharger.TEN_TO_FIFTY;

class DistanceChargerTest {

    private static Stream<Arguments> discountChargerProvier() {
        return Stream.of(
                Arguments.arguments(11, Arrays.asList(TEN_TO_FIFTY).toArray(new DistanceCharger[0])),
                Arguments.arguments(50, Arrays.asList(TEN_TO_FIFTY).toArray(new DistanceCharger[0])),
                Arguments.arguments(51, Arrays.asList(TEN_TO_FIFTY, OVER_FIFTY).toArray(new DistanceCharger[0]))
        );
    }

    @DisplayName("알맞는 과금 정책 찾기")
    @ParameterizedTest
    @MethodSource(value = "discountChargerProvier")
    void findByDistance(int distance, DistanceCharger... expect) {
        //given
        List<DistanceCharger> distanceChargers = DistanceCharger.findByDistance(distance);

        //then
        assertThat(distanceChargers).contains(expect);
    }

    @DisplayName("과금 정책에 따른 과금 적용")
    @Test
    void charge() {
        //given

        //when

        //then
    }
}