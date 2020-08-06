package wooteco.subway.maps.map.acceptance;

import com.google.common.collect.Lists;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.security.core.TokenResponse;
import wooteco.subway.common.acceptance.AcceptanceTest;
import wooteco.subway.maps.line.acceptance.step.LineAcceptanceStep;
import wooteco.subway.maps.line.dto.LineResponse;
import wooteco.subway.maps.station.acceptance.step.StationAcceptanceStep;
import wooteco.subway.maps.station.dto.StationResponse;
import wooteco.subway.members.member.acceptance.step.MemberAcceptanceStep;

import static wooteco.subway.maps.line.acceptance.step.LineStationAcceptanceStep.지하철_노선에_지하철역_등록되어_있음;
import static wooteco.subway.maps.map.acceptance.step.PathAcceptanceStep.거리_경로_조회_요청;
import static wooteco.subway.maps.map.acceptance.step.PathAcceptanceStep.로그인하고_거리_경로_조회_요청;
import static wooteco.subway.maps.map.acceptance.step.PathAcceptanceStep.적절한_경로를_응답;
import static wooteco.subway.maps.map.acceptance.step.PathAcceptanceStep.총_거리와_소요_시간과_요금을_함께_응답함;
import static wooteco.subway.members.member.acceptance.step.MemberAcceptanceStep.로그인_되어_있음;

@DisplayName("지하철 경로 조회")
public class PathAcceptanceTest extends AcceptanceTest {
    private Long 교대역;
    private Long 강남역;
    private Long 삼성역;
    private Long 양재역;
    private Long 남부터미널역;
    private Long 이호선;
    private Long 신분당선;
    private Long 삼호선;

    /**
     * 교대역    --- *2호선* ---   강남역    ---    삼성역
     * |                        |
     * *3호선*                   *신분당선*
     * |                        |
     * 남부터미널역  --- *3호선* ---   양재
     */
    @BeforeEach
    public void setUp() {
        super.setUp();

        // given
        교대역 = 지하철역_등록되어_있음("교대역");
        강남역 = 지하철역_등록되어_있음("강남역");
        양재역 = 지하철역_등록되어_있음("양재역");
        남부터미널역 = 지하철역_등록되어_있음("남부터미널역");
        삼성역 = 지하철역_등록되어_있음("삼성역");

        이호선 = 지하철_노선_등록되어_있음("2호선", "GREEN", 0);
        신분당선 = 지하철_노선_등록되어_있음("신분당선", "RED", 200);
        삼호선 = 지하철_노선_등록되어_있음("3호선", "ORANGE", 300);

        지하철_노선에_지하철역_등록되어_있음(이호선, null, 교대역, 0, 0);
        지하철_노선에_지하철역_등록되어_있음(이호선, 교대역, 강남역, 2, 2);
        지하철_노선에_지하철역_등록되어_있음(이호선, 강남역, 삼성역, 58, 2);

        지하철_노선에_지하철역_등록되어_있음(신분당선, null, 강남역, 0, 0);
        지하철_노선에_지하철역_등록되어_있음(신분당선, 강남역, 양재역, 2, 1);

        지하철_노선에_지하철역_등록되어_있음(삼호선, null, 교대역, 0, 0);
        지하철_노선에_지하철역_등록되어_있음(삼호선, 교대역, 남부터미널역, 1, 2);
        지하철_노선에_지하철역_등록되어_있음(삼호선, 남부터미널역, 양재역, 2, 2);
    }

    @DisplayName("두 역의 최단 거리 경로를 조회한다.")
    @Test
    void findPathByDistance() {
        //when
        ExtractableResponse<Response> response = 거리_경로_조회_요청("DISTANCE", 1L, 3L);

        //then
        적절한_경로를_응답(response, Lists.newArrayList(교대역, 남부터미널역, 양재역));

        총_거리와_소요_시간과_요금을_함께_응답함(response, 3, 4, 1550);
    }


    @DisplayName("두 역의 최소 시간 경로를 조회한다.")
    @Test
    void findPathByDuration() {
        //when
        ExtractableResponse<Response> response = 거리_경로_조회_요청("DURATION", 1L, 3L);
        //then
        적절한_경로를_응답(response, Lists.newArrayList(교대역, 강남역, 양재역));
        총_거리와_소요_시간과_요금을_함께_응답함(response, 4, 3, 1450);
    }

    @DisplayName("같은 호선에서 거리에 따른 과금만 적용된 경우.")
    @Test
    void findPathByDuration2() {
        //when
        ExtractableResponse<Response> response = 거리_경로_조회_요청("DURATION", 2L, 5L);
        //then
        적절한_경로를_응답(response, Lists.newArrayList(강남역, 삼성역));
        총_거리와_소요_시간과_요금을_함께_응답함(response, 58, 2, 2150);
    }

    @DisplayName("다른 호선으로 갈아타서 추가요금이 부과된 경우")
    @Test
    void findPathByDuration3() {
        //when
        ExtractableResponse<Response> response = 거리_경로_조회_요청("DISTANCE", 2L, 4L);
        //then
        적절한_경로를_응답(response, Lists.newArrayList(교대역, 강남역, 남부터미널역));
        총_거리와_소요_시간과_요금을_함께_응답함(response, 3, 4, 1550);
    }

    @DisplayName("로그인한 사용자의 경우 나이에 따라 요금 할인이 적용된다.")
    @Test
    void findPathByLogin() {
        // when
        ExtractableResponse<Response> createResponse = MemberAcceptanceStep.회원_생성을_요청("pci2676@gmail.com", "1234", 8);
        // then
        MemberAcceptanceStep.회원_생성됨(createResponse);

        TokenResponse tokenResponse = 로그인_되어_있음("pci2676@gmail.com", "1234");

        //when
        ExtractableResponse<Response> response = 로그인하고_거리_경로_조회_요청("DURATION", 1L, 2L, tokenResponse);

        //then
        적절한_경로를_응답(response, Lists.newArrayList(교대역, 강남역));
        총_거리와_소요_시간과_요금을_함께_응답함(response, 2, 2, 450);
    }

    private Long 지하철_노선_등록되어_있음(String name, String color, int extraCharge) {
        ExtractableResponse<Response> createLineResponse1 = LineAcceptanceStep.지하철_노선_등록되어_있음(name, color, extraCharge);
        return createLineResponse1.as(LineResponse.class).getId();
    }

    private Long 지하철역_등록되어_있음(String name) {
        ExtractableResponse<Response> createdStationResponse1 = StationAcceptanceStep.지하철역_등록되어_있음(name);
        return createdStationResponse1.as(StationResponse.class).getId();
    }
}
