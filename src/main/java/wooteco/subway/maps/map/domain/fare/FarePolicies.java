package wooteco.subway.maps.map.domain.fare;

import wooteco.subway.maps.map.domain.SubwayPath;

import java.util.List;
import java.util.Objects;

public class FarePolicies {
    private final List<FarePolicy> farePolicies;

    public FarePolicies(final List<FarePolicy> farePolicies) {
        this.farePolicies = farePolicies;
    }

    public int calculateFare(SubwayPath subwayPath) {
        int fare = 0;
        for (FarePolicy farePolicy : farePolicies) {
            fare = fare + farePolicy.getCharge(subwayPath);
        }
        return fare;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FarePolicies that = (FarePolicies) o;
        return Objects.equals(farePolicies, that.farePolicies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(farePolicies);
    }
}
