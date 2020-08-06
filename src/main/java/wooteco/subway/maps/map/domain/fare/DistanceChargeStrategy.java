package wooteco.subway.maps.map.domain.fare;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.map.domain.fare.distance.DistanceCharger;
import wooteco.subway.maps.map.domain.fare.distance.DistanceChargers;
import wooteco.subway.maps.station.domain.Station;

import java.util.Map;

public class DistanceChargeStrategy implements ChargeStrategy {

    private static final int DEFAULT_DISTANCE = 10;

    @Override
    public boolean fulfill(final SubwayPath subwayPath, final Map<Long, Station> stations) {
        int distance = subwayPath.calculateDistance();
        return distance > DEFAULT_DISTANCE;
    }

    @Override
    public int apply(final SubwayPath subwayPath, final Map<Long, Station> stations) {
        int distance = subwayPath.calculateDistance();
        DistanceChargers distanceChargers = DistanceCharger.findByDistance(distance);
        return distanceChargers.calculateCharge(distance);
    }
}
