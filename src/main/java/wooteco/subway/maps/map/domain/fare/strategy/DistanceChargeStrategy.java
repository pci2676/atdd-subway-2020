package wooteco.subway.maps.map.domain.fare.strategy;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.map.domain.fare.strategy.distance.DistanceCharger;
import wooteco.subway.maps.map.domain.fare.strategy.distance.DistanceChargers;

public class DistanceChargeStrategy implements ChargeStrategy {

    private static final int DEFAULT_DISTANCE = 10;

    @Override
    public boolean fulfill(final SubwayPath subwayPath) {
        int distance = subwayPath.calculateDistance();
        return distance > DEFAULT_DISTANCE;
    }

    @Override
    public int apply(final SubwayPath subwayPath) {
        int distance = subwayPath.calculateDistance();
        DistanceChargers distanceChargers = DistanceCharger.findByDistance(distance);
        return distanceChargers.calculateCharge(distance);
    }
}
