package web;

import frontend.pageobjects.Players;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PlayerTest extends BaseTest {
  private static final Logger logger = LoggerFactory.getLogger(PlayerTest.class);
  Players players;

  @DataProvider(name = "playerIdDetails")
  public Object[][] playerIdDetails() {
    return new Object[][]{{1}, {2}, {3}, {4}, {5}};
  }

  @Test
  public void testAddPlayer() {
    players = new Players(driver);
    players.verifyPageElements();
    players.createPlayer();
    logger.info("Players added to existing team successfully");
  }

  @Test
  public void testViewPlayerList() {
    players = new Players(driver);
    players.verifyPageElements();
    players.verifyPlayersTable();
    logger.info("Player list table contents verified successfully");
  }

  @Test(dataProvider = "playerIdDetails")
  public void testViewPlayerDetailsForTheIdProvided(int id) {
    players = new Players(driver);
    players.verifyPageElements();
    players.verifyPlayerDetails(id);
    logger.info("Player with " + id + " viewed successfully");
  }
}
