package tests;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.HttpResponseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import static utils.TestConstants.*;

public abstract class BaseAPITest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = 443;
        RestAssured.config = RestAssured.config()
                .httpClient(
                        HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 5000)
                                .setParam("http.socket.timeout", 5000)
                );
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    protected RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .header("accept", "application/json")
                .contentType(ContentType.JSON);
    }

    protected RequestSpecification getRequestSpecWithAuth() {
        return getRequestSpec()
                .header("api_key", API_KEY);
    }

    protected void validate404ExceptionAsResponse(Exception e) throws Exception {
        validateExceptionAsResponse(e, RESPONSE_STATUS_NOT_FOUND);
    }

    protected void validateExceptionAsResponse(Exception e, int expectedCode) throws Exception {
        if (e instanceof HttpResponseException) {
            HttpResponseException httpResponseException = (HttpResponseException) e;
            Assert.assertEquals(httpResponseException.getStatusCode(), expectedCode);
        } else {
            throw e;
        }
    }
}
