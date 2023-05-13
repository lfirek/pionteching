package pl.ing.pionteching.onlinegame.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(OnlineGameResource.class)
public class OnlineGameResourceTest {

    @Test
    public void testCalculateEndpointWithExampleData() {
        JsonPath requestJson = new JsonPath(
                new File("src/test/resources/examples/onlinegame/example001_request.json"));
        JsonPath responseJson = new JsonPath(
                new File("src/test/resources/examples/onlinegame/example001_response.json"));

        given()
                .header("Content-type", "application/json")
                .and()
                .body(requestJson.prettify())
                .when()
                .post("/calculate")
                .then()
                .statusCode(200)
                .body("", equalTo(responseJson.getList("")));
    }

}