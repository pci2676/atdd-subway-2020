package wooteco.subway.maps.map.domain.fare.distance;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum DistanceCharger {
    TEN_TO_FIFTY(distance -> distance > 10, 10, 50, 5, 100),
    OVER_FIFTY(distance -> distance > 50, 50, Integer.MAX_VALUE, 8, 100);

    private final Predicate<Integer> strategy;
    private final int startDistance;
    private final int boundary;
    private final int countUnit;
    private final int chargeUnit;

    DistanceCharger(final Predicate<Integer> strategy, final int startDistance, final int boundary, final int countUnit, final int chargeUnit) {
        this.strategy = strategy;
        this.startDistance = startDistance;
        this.countUnit = countUnit;
        this.boundary = boundary;
        this.chargeUnit = chargeUnit;
    }

    public static DistanceChargers findByDistance(int distance) {
        return Arrays.stream(values())
                .filter(charger -> charger.strategy.test(distance))
                .collect(Collectors.collectingAndThen(Collectors.toList(), DistanceChargers::new));
    }

    public int charge(int distance) {
        if (distance > this.boundary) {
            distance = this.boundary;
        }
        distance = distance - this.startDistance;
        int count = distance / countUnit;
        return count * chargeUnit;
    }
}
