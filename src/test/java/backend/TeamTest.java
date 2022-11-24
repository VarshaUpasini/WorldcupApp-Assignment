package backend;

import static config.WorldcupConfig.Teams_URL;
import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.LinkedHashMap;
import org.testng.annotations.Test;
import utils.Helper;

public class TeamTest extends TeamAssert{
  public LinkedHashMap<String, String> queryparams = new LinkedHashMap<String, String>();
  public static final String teamName = Helper.generateRandomChar(8);
  /**
   *
   */
  @Test(priority = 0)
  public void testCreateTeamWithValidData() {
    queryparams.put("teamName", teamName);
    RequestSpecification requestSpec = RestAssured.given();
    requestSpec.queryParams(queryparams);
    Response response = given().log().all()
        .spec(requestSpec)
        .when().post(Teams_URL)
        .then().log().all()
        .extract().response();
    response.asPrettyString();
    verifyCreateTeamWithValidData(response,teamName);
  }

  @Test(priority = 1)
  public void testCreateTeamWithExistingData() {
    queryparams.put("teamName", teamName);
    RequestSpecification requestSpec = RestAssured.given();
    requestSpec.queryParams(queryparams);
    Response response = given().log().all()
        .spec(requestSpec)
        .when().post(Teams_URL)
        .then().log().all()
        .extract().response();
    response.asPrettyString();
    verifyCreateTeamWithExistingData(response,teamName);
  }
}
