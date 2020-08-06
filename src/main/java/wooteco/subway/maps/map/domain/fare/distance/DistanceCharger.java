package wooteco.subway.maps.map.domain.fare.distance;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum DistanceCharger {
    TEN_TO_FIFTY(distance -> distance > 10, 10, 5, 100),
    OVER_FIFTY(distance -> distance > 50, 50, 8, 100);

    private final Predicate<Integer> strategy;
    private final int boundary;
    private final int countUnit;
    private final int chargeUnit;

    DistanceCharger(final Predicate<Integer> strategy, final int boundary, final int countUnit, final int chargeUnit) {
        this.strategy = strategy;
        this.boundary = boundary;
        this.countUnit = countUnit;
        this.chargeUnit = chargeUnit;
    }

    public static List<DistanceCharger> findByDistance(int distance) {
        return Arrays.stream(values())
                .filter(charger -> charger.strategy.test(distance))
                .collect(Collectors.toList());
    }

    public int charge(int fare, int distance) {
        int count = distance / this.countUnit;
        return fare + count * this.chargeUnit;
    }
}
