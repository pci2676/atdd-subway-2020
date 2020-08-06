package wooteco.subway.maps.map.domain.fare;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FarePolicy {
    DISTANCE_POLICY(new DistanceChargeStrategy()),
    DEFAULT_POLICY(new DefaultChargeStrategy());

    private final ChargeStrategy chargeStrategy;

    FarePolicy(final ChargeStrategy chargeStrategy) {
        this.chargeStrategy = chargeStrategy;
    }

    public static FarePolicies findBy(SubwayPath subwayPath, Map<Long, Station> stations) {
        return Arrays.stream(values())
                .filter(farePolicy -> farePolicy.chargeStrategy.fulfill(subwayPath, stations))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FarePolicies::new));
    }

    public int getCharege(SubwayPath subwayPath, Map<Long, Station> stations) {
        return this.chargeStrategy.apply(subwayPath, stations);
    }
}
