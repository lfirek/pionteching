package pl.ing.pionteching.atm.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(AtmResource.class)
public class AtmResourceTest {

    @Test
    public void testWithExampleData() {
        JsonPath requestJson = new JsonPath(
                new File("src/test/resources/examples/atm/example001_request.json"));
        JsonPath responseJson = new JsonPath(
                new File("src/test/resources/examples/atm/example001_response.json"));

        given()
                .header("Content-type", "application/json")
                .and()
                .body(requestJson.prettify())
                .when()
                .post("/calculateOrder")
                .then()
                .statusCode(200)
                .body("", equalTo(responseJson.getList("")));

        requestJson = new JsonPath(
                new File("src/test/resources/examples/atm/example002_request.json"));
        responseJson = new JsonPath(
                new File("src/test/resources/examples/atm/example002_response.json"));

        given()
                .header("Content-type", "application/json")
                .and()
                .body(requestJson.prettify())
                .when()
                .post("/calculateOrder")
                .then()
                .statusCode(200)
                .body("", equalTo(responseJson.getList("")));
    }

}