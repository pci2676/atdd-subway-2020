package wooteco.subway.maps.line.dto;

import wooteco.subway.maps.line.domain.Line;

import java.time.LocalTime;

public class LineRequest {
    private String name;
    private String color;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer intervalTime;
    private Integer extraCharge;

    public LineRequest() {
    }

    public LineRequest(String name, String color, LocalTime startTime, LocalTime endTime, Integer intervalTime) {
        this.name = name;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
    }

    public LineRequest(final String name, final String color, final LocalTime startTime, final LocalTime endTime, final Integer intervalTime, final Integer extraCharge) {
        this.name = name;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
        this.extraCharge = extraCharge;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public Integer getExtraCharge() {
        return extraCharge;
    }

    public Line toLine() {
        return new Line(name, color, startTime, endTime, intervalTime, extraCharge);
    }
}
