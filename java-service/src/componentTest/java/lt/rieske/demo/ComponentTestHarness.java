package lt.rieske.demo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

public abstract class ComponentTestHarness {

    private static final Logger log = LoggerFactory.getLogger(ComponentTestHarness.class);

    private static final String SERVICE_CONTAINER = "service";
    private static final int SERVICE_PORT = 8080;
    private static final int ADMIN_PORT = 8081;

    private static final DockerComposeContainer<?> environment =
            new DockerComposeContainer<>(new File("src/componentTest/resources/component-test.yml"))
                    .withLocalCompose(true)
                    .withLogConsumer(SERVICE_CONTAINER, new Slf4jLogConsumer(log).withPrefix(SERVICE_CONTAINER))
                    .withExposedService(SERVICE_CONTAINER, 1,SERVICE_PORT, Wait.forListeningPort())
                    .withExposedService(SERVICE_CONTAINER, 1, ADMIN_PORT,
                            Wait.forHttp("/actuator/health").forStatusCode(200));

    @BeforeAll
    static void setup() {
        environment.start();
        RestAssured.baseURI = serviceUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterAll
    static void teardown() {
        environment.stop();
    }

    private static String serviceUrl() {
        return String.format("http://%s:%d",
                environment.getServiceHost(SERVICE_CONTAINER, SERVICE_PORT),
                environment.getServicePort(SERVICE_CONTAINER, SERVICE_PORT));
    }

    protected String adminUrl() {
        return String.format("http://%s:%d",
                environment.getServiceHost(SERVICE_CONTAINER, ADMIN_PORT),
                environment.getServicePort(SERVICE_CONTAINER, ADMIN_PORT));
    }
}
