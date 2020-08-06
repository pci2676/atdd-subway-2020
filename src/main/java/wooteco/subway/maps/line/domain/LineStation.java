package wooteco.subway.maps.line.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public class LineStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long stationId;
    private Long preStationId;
    private Integer distance;
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;

    public LineStation() {
    }

    public LineStation(Long stationId, Long preStationId, Integer distance, Integer duration) {
        this.stationId = stationId;
        this.preStationId = preStationId;
        this.distance = distance;
        this.duration = duration;
    }

    public boolean isSame(LineStation newLineStation) {
        return Objects.equals(this.stationId, newLineStation.stationId);
    }

    public void placeLine(Line line) {
        this.line = line;
    }

    public Long getStationId() {
        return stationId;
    }

    public Long getPreStationId() {
        return preStationId;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getExtraCharge() {
        return line.getExtraCharge();
    }

    public void updatePreStationTo(Long newPreStationId) {
        this.preStationId = newPreStationId;
    }
}
