package lt.rieske.demo;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class SmokeTest extends ComponentTestHarness {

    @Test
    public void serviceStarts() {
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
    public void prometheusMetricsEndpointIsExposed() {
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
