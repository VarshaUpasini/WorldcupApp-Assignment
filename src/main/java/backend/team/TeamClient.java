package backend.team;

import static config.WorldcupConfig.BASE_URI;
import static io.restassured.RestAssured.given;

import backend.Error;
import org.apache.http.HttpStatus;

public class TeamClient {

  public static Team createTeam(String randomTeamName) {
    return given().log().all()
        .param("teamName", randomTeamName)
        .baseUri(BASE_URI)
        .when().post("/teams")
        .then().log().all()
        .statusCode(HttpStatus.SC_OK)
        .extract().as(Team.class);
  }

  public static Error createTeamWithExistingData(String randomTeamName) {
    return given().log().all()
        .param("teamName", randomTeamName)
        .baseUri(BASE_URI)
        .when().post("/teams")
        .then().log().all()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .extract().as(Error.class);
  }
}
