package api.base;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static io.restassured.RestAssured.*;


public class APIBase {

    private static final Logger logger = LoggerFactory.getLogger(APIBase.class);

    protected String baseURL;

    public APIBase(String baseURL) {
        this.baseURL = baseURL;
        useRelaxedHTTPSValidation();
    }

    public Response get(String endpoint){

        Response response = given().contentType(ContentType.JSON)
                .when().log().all().get(baseURL+endpoint);

        logger.info("Response: " + response.asString());

        return response;
    }

    public Response post(String endpoint, Object body){

        Response response = given().contentType(ContentType.JSON)
                .body(body).when().log().all().post(baseURL+endpoint);

        logger.info("Response: " + response.asString());

        return response;

    }

    public Response put(String endpoint, Object body, String token){

        Response response = given().contentType("application/json").accept("application/json")
                .cookie("token",token).body(body).when().log().all().put(baseURL+endpoint);

        return  response;
    }

    public Response delete(String endpoint, String token){

        Response response = given().contentType(ContentType.JSON)
                .cookie("token",token).when().log().all().delete(baseURL+endpoint);

        return  response;
    }

    public Response uploadFile(String endpoint, File file){

        Response response = given().contentType("multipart/form-data")
                .accept(ContentType.JSON)
                .multiPart("file",file)
                .post(baseURL+endpoint);

        return response;
    }




}
