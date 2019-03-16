package lt.rieske.demo;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.ClassRule;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

@Slf4j
public abstract class ComponentTestHarness {

    private static final String SERVICE_CONTAINER = "service_1";
    private static final int SERVICE_PORT = 8080;
    private static final int ADMIN_PORT = 8081;

    @ClassRule
    public static DockerComposeContainer environment =
            new DockerComposeContainer(new File("component-test.yml"))
                    .withLogConsumer(SERVICE_CONTAINER, new Slf4jLogConsumer(log).withPrefix(SERVICE_CONTAINER))
                    .withExposedService(SERVICE_CONTAINER, SERVICE_PORT,
                            Wait.forListeningPort())
                    .withExposedService(SERVICE_CONTAINER, ADMIN_PORT,
                            Wait.forHttp("/actuator/health").forStatusCode(200));

    @Before
    public void setUpEnvironment() {
        RestAssured.baseURI = serviceUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected String serviceUrl() {
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
