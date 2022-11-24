package backend;

import backend.team.Team;
import backend.team.TeamClient;
import java.util.LinkedHashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Helper;

/**
 * TeamTest class is created to test the functionalities such as Create team , create team with
 * existing team name.
 */
public class TeamTest {

  public LinkedHashMap<String, String> queryparams = new LinkedHashMap<String, String>();
  public String randomTeamName = Helper.getRandomName();

  @Test(priority = 0)
  public void testCreateTeamWithValidData() {
    Team createdTeam = TeamClient.createTeam(randomTeamName);
    assertCreateTeamWithValidData(createdTeam, randomTeamName);
  }

  /**
   * this test method will fail as we are expecting 400 as status code and error as Bad request
   * instead on 500 internal server error
   */
  @Test(priority = 1)
  public void testCreateTeamWithExistingData() {
    Error error = TeamClient.createTeamWithExistingData(randomTeamName);
    assertCreateTeamWithExistingData(error, randomTeamName);
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
