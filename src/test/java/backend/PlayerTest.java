package backend;

import backend.player.Player;
import backend.player.PlayerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Helper;


/**
 * PlayerTest class created to test the functionalities such as Create players , view all players ,
 * view players with valid id and view player with invalid id.
 */
public class PlayerTest {
  private final Logger logger = LoggerFactory.getLogger(PlayerTest.class);

  @DataProvider(name = "teamDetails")
  public Object[][] teamDetails() {
    return new Object[][]{{"Germany"}, {"Italy"}, {"Japan"}, {"Russia"}, {"Malta"}, {"Spain"},
        {"Sweden"}};
  }

  @DataProvider(name = "playerId")
  public Object[][] playerId() {
    return new Object[][]{{1}};
  }

  @DataProvider(name = "invalidPlayerId")
  public Object[][] invalidPlayerId() {
    int id = Integer.parseInt(Helper.getRandomNumber());
    return new Object[][]{{id}};
  }

  /**
   * @param team fetches the values from dataprovider teamDetails
   */
  @Test(dataProvider = "teamDetails", priority = 1, groups = {"P0"})
  public void testCreatePlayerWithValidData(String team) {
    String randomPlayerName = Helper.getRandomName();
    Player createdPlayer = PlayerClient.createPlayer(team, randomPlayerName);
    assertCreatedPlayer(createdPlayer, randomPlayerName, team);
    logger.info(randomPlayerName + "has been added to " + team + "team.");
    logger.info("Test verified successfully");
  }

  /**
   * test method to view all players
   */
  @Test(groups = {"P0"})
  public void testViewAllPlayers() {
    Player[] viewPlayer = PlayerClient.viewAllPlayers();
    assertViewAllPlayers(viewPlayer);
    logger.info("Test verified successfully");
  }

  /**
   * @param id fetches the value from data provider playerId
   */
  @Test(dataProvider = "playerId",groups = {"P0"})
  public void testFetchPlayerByIdWithValidData(int id) {
    Player fetchPlayerByIdWithValidData = PlayerClient.fetchPlayerByIdWithValidData(id);
    verifyFetchPlayerByIdWithValidData(fetchPlayerByIdWithValidData, id);
    logger.info("Test player fetched successfully");
  }

  /**
   * @param id fetches the value from data provider invalidPlayerId
   *           this test method will fail as we
   *           are expecting 400 as status code and error as Bad request instead on 500 internal
   *           server error
   */
  @Test(dataProvider = "invalidPlayerId",groups = {"P1"})
  public void testFetchPlayerByIdWithInvalidData(int id) {
    Error error = PlayerClient.fetchPlayerByIdWithInvalidData(id);
    verifyFetchPlayerByIdWithInvalidData(error, id);
    logger.info("Test verified successfully");
  }

  public void assertCreatedPlayer(Player player, String playerName, String team) {
    Assert.assertNotNull(player.getPlayerId());
    Assert.assertEquals(playerName, player.getPlayerName());
    Assert.assertEquals(team, player.getTeamName());
  }

  public void assertViewAllPlayers(Player[] viewPlayer) {
    for (int i = 0; i < viewPlayer.length; i++) {
      Assert.assertNotNull(viewPlayer[i].getPlayerId());
      Assert.assertNotNull(viewPlayer[i].getPlayerName());
      Assert.assertNotNull(viewPlayer[i].getTeamName());
    }
  }

  public void verifyFetchPlayerByIdWithValidData(Player fetchPlayerByIdWithValidData, int id) {
    Assert.assertEquals(1, fetchPlayerByIdWithValidData.getPlayerId());
    Assert.assertEquals("Melvin", fetchPlayerByIdWithValidData.getPlayerName());
    Assert.assertEquals("Malta", fetchPlayerByIdWithValidData.getTeamName());
  }

  public void verifyFetchPlayerByIdWithInvalidData(Error error, int id) {
    Assert.assertEquals("Bad Request", error.getError());
    Assert.assertEquals("java.lang.IllegalStateException", error.getException());
    Assert.assertEquals("Could not find player with id " + id, error.getMessage());
    Assert.assertEquals("/players/" + id, error.getPath());
  }
}
