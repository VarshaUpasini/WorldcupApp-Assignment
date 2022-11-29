package backend;

import backend.team.Team;
import backend.team.TeamClient;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Helper;

/**
 * TeamTest class is created to test the functionalities such as Create team , create team with
 * existing team name.
 */
public class TeamTest {
  private static final Logger logger = LoggerFactory.getLogger(TeamTest.class);
  public LinkedHashMap<String, String> queryparams = new LinkedHashMap<String, String>();
  public String randomTeamName = Helper.getRandomName();

  @Test(priority = 0, groups = {"P0"})
  public void testCreateTeamWithValidData() {
    Team createdTeam = TeamClient.createTeam(randomTeamName);
    assertCreateTeamWithValidData(createdTeam, randomTeamName);
    logger.info(randomTeamName + " created successfully");
    logger.info("Test verified successfully");
  }

  /**
   * test the response when existing team name is passed
   */
  @Test(priority = 1, groups = {"P1"})
  public void testCreateTeamWithExistingData() {
    Error error = TeamClient.createTeamWithExistingData(randomTeamName);
    assertCreateTeamWithExistingData(error, randomTeamName);
    logger.info(randomTeamName + " cannot be created");
    logger.info("Test verified successfully");
  }

  public void assertCreateTeamWithValidData(Team createdTeam, String teamName) {
    Assert.assertNotNull(createdTeam.getTeamId());
    Assert.assertEquals(teamName, createdTeam.getTeamName());
  }

  public void assertCreateTeamWithExistingData(Error error, String teamName) {
    Assert.assertEquals("Bad Request", error.getError());
    Assert.assertEquals("java.lang.IllegalStateException", error.getException());
    Assert.assertEquals(teamName + " already exists", error.getMessage());
    Assert.assertEquals("/teams/", error.getPath());
  }
}