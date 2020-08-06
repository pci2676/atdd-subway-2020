package wooteco.subway.maps.map.domain.fare.strategy;

import wooteco.subway.maps.map.domain.SubwayPath;

public class DefaultChargeStrategy implements ChargeStrategy {

    private static final int DEFAULT_CHARGE = 1250;

    @Override
    public boolean fulfill(final SubwayPath subwayPath) {
        return true;
    }

    @Override
    public int apply(final SubwayPath subwayPath) {
        return DEFAULT_CHARGE;
    }
}
