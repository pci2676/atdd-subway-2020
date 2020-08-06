package wooteco.subway.maps.map.dto;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.map.domain.fare.FarePolicy;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;
import wooteco.subway.members.member.domain.LoginMember;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PathResponseAssembler {
    public static PathResponse assemble(SubwayPath subwayPath, Map<Long, Station> stations, LoginMember loginMember) {
        List<StationResponse> stationResponses = subwayPath.extractStationId().stream()
                .map(it -> StationResponse.of(stations.get(it)))
                .collect(Collectors.toList());

        int distance = subwayPath.calculateDistance();
        int fare = FarePolicy.findBy(subwayPath)
                .calculateFare(subwayPath);

        if (loginMember != null) {
            fare = loginMember.discountFare(fare);
        }

        return new PathResponse(stationResponses, subwayPath.calculateDuration(), distance, fare);
    }
}
