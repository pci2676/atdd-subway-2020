package wooteco.subway.maps.map.domain.fare;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;

import java.util.Map;

public class DistanceFarePolicy implements FareStrategy {

    private static final int DEFAULT_DISTANCE = 10;

    @Override
    public boolean fulfill(final SubwayPath subwayPath, final Map<Long, Station> stations) {
        int distance = subwayPath.calculateDistance();
        return distance > DEFAULT_DISTANCE;
    }

    @Override
    public int apply(final SubwayPath subwayPath, final Map<Long, Station> stations, final int fare) {
        int distance = subwayPath.calculateDistance();

        int count = 0;

        if (distance > 50) {
            count = distance / 8;
        } else {
            count = distance / 5;
        }
        return 0;
    }
}
