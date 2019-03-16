package lt.rieske.demo;

import org.junit.Test;

import static io.restassured.RestAssured.when;

public class SmokeTest extends ComponentTestHarness {

    @Test
    public void serviceStarts() {
        // @formatter:off
        when()
            .get("/actuator/health")
        .then()
            .statusCode(200);
        // @formatter:on
    }

}
