package wooteco.subway.maps.map.domain.fare;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.map.domain.fare.strategy.ChargeStrategy;
import wooteco.subway.maps.map.domain.fare.strategy.DefaultChargeStrategy;
import wooteco.subway.maps.map.domain.fare.strategy.DistanceChargeStrategy;
import wooteco.subway.maps.map.domain.fare.strategy.LineChargeStrategy;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum FarePolicy {
    DISTANCE_POLICY(new DistanceChargeStrategy()),
    LINE_POLICY(new LineChargeStrategy()),
    DEFAULT_POLICY(new DefaultChargeStrategy());

    private final ChargeStrategy chargeStrategy;

    FarePolicy(final ChargeStrategy chargeStrategy) {
        this.chargeStrategy = chargeStrategy;
    }

    public static FarePolicies findBy(SubwayPath subwayPath) {
        return Arrays.stream(values())
                .filter(farePolicy -> farePolicy.chargeStrategy.fulfill(subwayPath))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FarePolicies::new));
    }

    public int getCharge(SubwayPath subwayPath) {
        return this.chargeStrategy.apply(subwayPath);
    }
}
