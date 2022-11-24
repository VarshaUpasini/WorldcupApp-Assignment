package backend;

import static backend.TeamTest.teamName;
import io.restassured.response.Response;
import org.testng.Assert;

public class TeamAssert {
  public void verifyCreateTeamWithValidData(Response response, String team){
    PlayerResponse playerResponse = response.getBody().as(PlayerResponse.class);
    response.then().assertThat().statusCode(200);
    Assert.assertNotNull(playerResponse.getPlayerId());
    Assert.assertEquals(teamName, playerResponse.getTeamName());
  }

  public void verifyCreateTeamWithExistingData(Response response, String team){
    PlayerInvalidResponse playerInvalidResponse = response.getBody()
        .as(PlayerInvalidResponse.class);
    response.then().assertThat().statusCode(400);
    Assert.assertEquals("Bad Request", playerInvalidResponse.getError());
    Assert.assertEquals("java.lang.IllegalStateException", playerInvalidResponse.getException());
    Assert.assertEquals(teamName+" already exists", playerInvalidResponse.getMessage());
    Assert.assertEquals("/teams/" , playerInvalidResponse.getPath());
  }
}
