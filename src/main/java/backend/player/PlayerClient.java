package backend.player;

import static config.WorldcupConfig.BASE_URI;
import static io.restassured.RestAssured.given;
import backend.Error;
import org.apache.http.HttpStatus;

public class PlayerClient {
  public static Player createPlayer(String team, String randomPlayerName) {
    return given().log().all()
        .param("playerName", randomPlayerName)
        .param("teamName", team)
        .baseUri(BASE_URI)
        .when().post("/players")
        .then().log().all()
        .statusCode(HttpStatus.SC_OK)
        .extract().as(Player.class);
  }

  public static Player[] viewAllPlayers() {
    return given().log().all()
        .baseUri(BASE_URI)
        .when().get("/players/")
        .then().log().all()
        .statusCode(HttpStatus.SC_OK)
        .extract().as(Player[].class);
  }

  public static Player fetchPlayerByIdWithValidData(int id) {
    return given().log().all()
        .baseUri(BASE_URI)
        .when().get("/players/" + id)
        .then().log().all()
        .statusCode(HttpStatus.SC_OK)
        .extract().as(Player.class);
  }

  public static Error fetchPlayerByIdWithInvalidData(int id) {
    return given().log().all()
        .baseUri(BASE_URI)
        .when().get("/players/" + id)
        .then().log().all()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .extract().as(Error.class);
  }
}
