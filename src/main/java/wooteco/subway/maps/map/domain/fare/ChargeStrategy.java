package wooteco.subway.maps.map.domain.fare;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;

import java.util.Map;

public interface ChargeStrategy {
    boolean fulfill(SubwayPath subwayPath, Map<Long, Station> stations);

    int apply(SubwayPath subwayPath, Map<Long, Station> stations, int fare);
}
