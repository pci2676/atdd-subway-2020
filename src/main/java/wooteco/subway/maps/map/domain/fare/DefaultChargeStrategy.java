package wooteco.subway.maps.map.domain.fare;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;

import java.util.Map;

public class DefaultChargeStrategy implements ChargeStrategy {

    private static final int DEFAULT_CHARGE = 1250;

    @Override
    public boolean fulfill(final SubwayPath subwayPath, final Map<Long, Station> stations) {
        return true;
    }

    @Override
    public int apply(final SubwayPath subwayPath, final Map<Long, Station> stations) {
        return DEFAULT_CHARGE;
    }
}
