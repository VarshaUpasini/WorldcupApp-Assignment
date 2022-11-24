package backend;

import static backend.PlayerTest.playerName;

import io.restassured.response.Response;
import org.testng.Assert;

public class PlayerAssert {

  public void verifyCreatePlayerWithValidData(Response response, String team){
    PlayerResponse playerResponse = response.getBody().as(PlayerResponse.class);
    response.then().assertThat().statusCode(200);
    Assert.assertNotNull(playerResponse.getPlayerId());
    Assert.assertEquals(playerName, playerResponse.getPlayerName());
    Assert.assertEquals(team, playerResponse.getTeamName());
  }

  public void verifyViewAllPlayers(Response response) {
    PlayerResponse[] playerResponse = response.getBody().as(PlayerResponse[].class);
    response.then().assertThat().statusCode(200);
    for (int i = 0; i < playerResponse.length; i++) {
      Assert.assertNotNull(playerResponse[i].getPlayerId());
      Assert.assertNotNull(playerResponse[i].getPlayerName());
      Assert.assertNotNull(playerResponse[i].getTeamName());
    }
  }

    public void verifyFetchPlayerByIdWithValidData(Response response, int id){
      PlayerResponse playerResponse = response.getBody().as(PlayerResponse.class);
      response.then().assertThat().statusCode(200);
      Assert.assertEquals(1, playerResponse.getPlayerId());
      Assert.assertEquals("Melvin", playerResponse.getPlayerName());
      Assert.assertEquals("Malta", playerResponse.getTeamName());
  }

  public void verifyFetchPlayerByIdWithInvalidData(Response response, int id){
    PlayerInvalidResponse playerInvalidResponse = response.getBody()
        .as(PlayerInvalidResponse.class);
    response.then().assertThat().statusCode(400);
    Assert.assertEquals("Bad Request", playerInvalidResponse.getError());
    Assert.assertEquals("java.lang.IllegalStateException", playerInvalidResponse.getException());
    Assert.assertEquals("Could not find player with id " + id, playerInvalidResponse.getMessage());
    Assert.assertEquals("/players/" + id, playerInvalidResponse.getPath());
  }
}
