package wooteco.subway.maps.map.domain.fare.strategy;

import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.map.domain.LineStationEdge;
import wooteco.subway.maps.map.domain.SubwayPath;

public class LineChargeStrategy implements ChargeStrategy {
    @Override
    public boolean fulfill(final SubwayPath subwayPath) {
        return !subwayPath.getLineStationEdges().isEmpty();
    }

    @Override
    public int apply(final SubwayPath subwayPath) {
        int maxExtraCharge = 0;
        for (LineStationEdge lineStationEdge : subwayPath.getLineStationEdges()) {
            LineStation lineStation = lineStationEdge.getLineStation();
            maxExtraCharge = Math.max(maxExtraCharge, lineStation.getExtraCharge());
        }
        return maxExtraCharge;
    }
}
