package wooteco.subway.maps.map.documentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;
import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.ui.MapController;
import wooteco.subway.maps.station.dto.StationResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

@WebMvcTest(controllers = {MapController.class})
public class PathDocumentation extends Documentation {

    @Autowired
    private MapController mapController;

    @MockBean
    private MapService mapService;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
    }

    @Test
    void getPathByType() {
        PathResponse pathResponse = new PathResponse(
                Arrays.asList(
                        new StationResponse(1L, "강남", LocalDateTime.now(), LocalDateTime.now()),
                        new StationResponse(2L, "선릉", LocalDateTime.now(), LocalDateTime.now())
                ),
                3,
                4,
                1250
        );

        when(mapService.findPath(any(), any(), any())).thenReturn(pathResponse);

        Map<String, Object> params = new HashMap<>();
        params.put("source", 1L);
        params.put("target", 2L);
        params.put("type", "DISTANCE");

        given().log().all().
                params(params).
                when().
                get("/paths").
                then().
                log().all().
                apply(
                        document(
                                "map/paths",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestParameters(
                                        parameterWithName("source").description("출발역 아이디"),
                                        parameterWithName("target").description("도착역 아이디"),
                                        parameterWithName("type").description("노선 조회 타입")
                                ),
                                responseBody(

                                ),
                                responseFields(
                                        fieldWithPath("stations.[]").type(JsonFieldType.ARRAY).description("노선 목록"),
                                        fieldWithPath("stations.[].id").type(JsonFieldType.NUMBER).description("노선 아이디"),
                                        fieldWithPath("stations.[].name").type(JsonFieldType.STRING).description("노선 이름"),
                                        fieldWithPath("duration").type(JsonFieldType.NUMBER).description("총 시간"),
                                        fieldWithPath("distance").type(JsonFieldType.NUMBER).description("총 시간"),
                                        fieldWithPath("fare").type(JsonFieldType.NUMBER).description("요금")
                                )
                        )
                ).
                extract();
    }

}
