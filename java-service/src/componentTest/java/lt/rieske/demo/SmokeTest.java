package lt.rieske.demo;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SmokeTest extends ComponentTestHarness {

    @Test
    void serviceStarts() {
        // @formatter:off
        given()
            .baseUri(adminUrl())
        .when()
            .get("/actuator/health")
        .then()
            .statusCode(200);
        // @formatter:on
    }

    @Test
    void prometheusMetricsEndpointIsExposed() {
        // @formatter:off
        given()
            .baseUri(adminUrl())
        .when()
            .get("/actuator/prometheus")
        .then()
            .statusCode(200);
        // @formatter:on
    }

}
