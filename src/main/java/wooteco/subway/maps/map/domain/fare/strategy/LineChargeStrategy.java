package wooteco.subway.maps.map.domain.fare.strategy;

import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.map.domain.LineStationEdge;
import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;

import java.util.Map;

public class LineChargeStrategy implements ChargeStrategy {
    @Override
    public boolean fulfill(final SubwayPath subwayPath, final Map<Long, Station> stations) {
        return !subwayPath.getLineStationEdges().isEmpty();
    }

    @Override
    public int apply(final SubwayPath subwayPath, final Map<Long, Station> stations) {
        int maxExtraCharge = 0;
        for (LineStationEdge lineStationEdge : subwayPath.getLineStationEdges()) {
            LineStation lineStation = lineStationEdge.getLineStation();
            maxExtraCharge = Math.max(maxExtraCharge, lineStation.getExtraCharge());
        }
        return maxExtraCharge;
    }
}
