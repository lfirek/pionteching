package pl.ing.pionteching.transaction.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(TransactionResource.class)
public class TransactionResourceTest {

    @Test
    public void testReportEndpointWithExampleData() {
        JsonPath requestJson = new JsonPath(
                new File("src/test/resources/examples/transaction/example001_request.json"));
        JsonPath responseJson = new JsonPath(
                new File("src/test/resources/examples/transaction/example001_response.json"));

        given()
                .header("Content-type", "application/json")
                .and()
                .body(requestJson.prettify())
                .when()
                .post("/report")
                .then()
                .statusCode(200)
                .body("", equalTo(responseJson.getList("")));
    }

    @Test
    public void testReportEndpointWithExampleDataUpperLimit() {
        JsonPath requestJson = new JsonPath(
                new File("src/test/resources/examples/transaction/example001_request.json"));
        JsonPath responseJson = new JsonPath(
                new File("src/test/resources/examples/transaction/example001_response.json"));

        given()
                .header("Content-type", "application/json")
                .and()
                .body(requestJson.prettify())
                .when()
                .post("/report")
                .then()
                .statusCode(200)
                .body("", equalTo(responseJson.getList("")));
    }

}