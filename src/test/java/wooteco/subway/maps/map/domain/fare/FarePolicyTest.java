package wooteco.subway.maps.map.domain.fare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.subway.maps.map.domain.SubwayPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static wooteco.subway.maps.map.domain.fare.FarePolicy.DEFAULT_POLICY;
import static wooteco.subway.maps.map.domain.fare.FarePolicy.DISTANCE_POLICY;

@ExtendWith(MockitoExtension.class)
class FarePolicyTest {

    @Mock
    SubwayPath subwayPath = new SubwayPath(new ArrayList<>());

    @DisplayName("거리에 따른 가격 정책 리스트 찾기")
    @Test
    void findBy() {
        //given
        when(subwayPath.calculateDistance()).thenReturn(11);

        //when
        FarePolicies farePolicies = FarePolicy.findBy(subwayPath, new HashMap<>());

        //then
        assertThat(farePolicies).isEqualTo(new FarePolicies(Arrays.asList(DISTANCE_POLICY, DEFAULT_POLICY)));
    }

    @Test
    void getFate() {
        //given
        when(subwayPath.calculateDistance()).thenReturn(15);

        //when
        FarePolicies farePolicies = FarePolicy.findBy(subwayPath, new HashMap<>());
        int calculateFare = farePolicies.calculateFare(subwayPath, new HashMap<>());

        //then
        assertThat(calculateFare).isEqualTo(1350);
    }

}