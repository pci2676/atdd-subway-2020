package wooteco.subway.maps.map.domain.fare.distance;

import java.util.List;
import java.util.Objects;

public class DistanceChargers {
    private final List<DistanceCharger> distanceChargers;

    public DistanceChargers(final List<DistanceCharger> distanceChargers) {
        this.distanceChargers = distanceChargers;
    }

    public int calculateCharge(final int distance) {
        int charge = 0;
        for (DistanceCharger distanceCharger : distanceChargers) {
            charge = charge + distanceCharger.charge(distance);
        }
        return charge;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DistanceChargers that = (DistanceChargers) o;
        return Objects.equals(distanceChargers, that.distanceChargers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distanceChargers);
    }
}
