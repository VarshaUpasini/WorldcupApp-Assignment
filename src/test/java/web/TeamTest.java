package web;

import frontend.pageobjects.Teams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class TeamTest extends BaseTest {

  private final Logger logger = LoggerFactory.getLogger(TeamTest.class);
  Teams teams;

  @Test
  public void testCreateTeam() {
    teams = new Teams(driver);
    teams.verifyPageElements();
    teams.createTeam();
    logger.info("Team created successfully");
  }

  @Test
  public void testCreateExistingTeam() throws Exception {
    teams = new Teams(driver);
    teams.verifyPageElements();
    teams.createExistingTeam();
    logger.info("Existing team cannot be created");
    logger.info("Test verified successfully");
  }

  @Test
  public void testViewTeamList() {
    teams = new Teams(driver);
    teams.verifyPageElements();
    teams.verifyTeamTable();
    logger.info("Team list table contents verified successfully");
  }
}
