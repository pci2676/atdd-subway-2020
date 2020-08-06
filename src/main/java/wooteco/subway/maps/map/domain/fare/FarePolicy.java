package wooteco.subway.maps.map.domain.fare;

public enum FarePolicy {
    DISTANCE_POLICY(new DistanceFarePolicy());

    private final FareStrategy fareStrategy;

    FarePolicy(final FareStrategy fareStrategy) {
        this.fareStrategy = fareStrategy;
    }
}
