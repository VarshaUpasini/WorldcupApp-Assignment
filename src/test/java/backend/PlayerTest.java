package backend;

import static config.WorldcupConfig.Player_URL;
import static io.restassured.RestAssured.given;
import static org.slf4j.Logger.*;
import static org.slf4j.LoggerFactory.getLogger;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Helper;


public class PlayerTest extends PlayerAssert {

  public LinkedHashMap<String, String> queryparams = new LinkedHashMap<String, String>();
  public static final String playerName = Helper.generateRandomChar(8);
  private static final String LOG_FILE = System.getProperty("user.dir") + "\\src\\test\\resources\\log4j.properties";

  Logger logger = getLogger(PlayerTest.class);
  Properties properties = new Properties();

  @Test(dataProvider = "teamDetails", priority = 1)
  public void testCreatePlayerWithValidData(String team) {
    queryparams.put("playerName", playerName);
    queryparams.put("teamName", team);
    RequestSpecification requestSpec = given();
    requestSpec.queryParams(queryparams);
    Response response = given().log().all()
        .spec(requestSpec)
        .when().post(Player_URL)
        .then()
        .extract().response();
    response.asPrettyString();
    verifyCreatePlayerWithValidData(response, team);
  }

  @DataProvider(name = "teamDetails")
  public Object[][] teamDetails() {
    return new Object[][]{{"Germany"}, {"Italy"}, {"Japan"}, {"Russia"}, {"Malta"}, {"Spain"},
        {"Sweden"}};
  }

  @Test
  public void testViewAllPlayers() throws IOException {
    properties.load(new FileInputStream(LOG_FILE));
    PropertyConfigurator.configure(properties);
    RequestSpecification requestSpec = RestAssured.given();
    Response response = given().log().all()
        .spec(requestSpec)
        .when().get(Player_URL)
        .then().log().all()
        .extract().response();
    response.asPrettyString();
    verifyViewAllPlayers(response);
    logger.info("********************************All player view");
  }

  @Test(dataProvider = "playerId")
  public void testFetchPlayerByIdWithValidData(int id) {
    RequestSpecification requestSpec = RestAssured.given();
    Response response = given().log().all()
        .spec(requestSpec)
        .when().get(Player_URL + id)
        .then().log().all()
        .extract().response();
    response.asPrettyString();
    verifyFetchPlayerByIdWithValidData(response, id);
  }

  @DataProvider(name = "playerId")
  public Object[][] playerId() {
    return new Object[][]{{1}};
  }

  /**
   * @param id
   */
  @Test(dataProvider = "invalidPlayerId")
  public void testFetchPlayerByIdWithInvalidData(int id) {
    RequestSpecification requestSpec = RestAssured.given();
    Response response = given().log().all()
        .spec(requestSpec)
        .when().get(Player_URL + id)
        .then().log().all()
        .extract().response();
    response.asPrettyString();
    verifyFetchPlayerByIdWithInvalidData(response, id);
  }

  @DataProvider(name = "invalidPlayerId")
  public Object[][] invalidPlayerId() {
    int id = Integer.parseInt(Helper.getRandomNumber());
    return new Object[][]{{id}};
  }
}
