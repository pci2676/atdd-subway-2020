package wooteco.subway.maps.map.domain.fare.strategy;

import wooteco.subway.maps.map.domain.SubwayPath;

public interface ChargeStrategy {
    boolean fulfill(SubwayPath subwayPath);

    int apply(SubwayPath subwayPath);
}
