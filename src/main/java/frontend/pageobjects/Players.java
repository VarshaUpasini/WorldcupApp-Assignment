package frontend.pageobjects;

import static config.WorldcupConfig.HOME_PAGE_TITLE;
import static config.WorldcupConfig.PLAYER_NAME_PLACEHOLDER;
import static utils.DBConnection.executeQueries;

import config.WorldcupConfig;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.DBConnection;
import utils.Helper;

@Data
public class Players extends BasePage {

  ResultSet resultSet;
  WebElement cellValue;

  @FindBy(xpath = "//a[contains(text(),'Players')]")
  private WebElement players;

  @FindBy(xpath = "//h1[contains(text(),'Players List')]")
  private WebElement playersText;

  @FindBy(xpath = "//a[contains(text(),'Teams')]")
  private WebElement teams;

  @FindBy(css = "input[type='text']")
  private WebElement playernameTextbox;

  @FindBy(xpath = "//select")
  private WebElement teamDropdown;

  @FindBy(xpath = "//button[normalize-space()='Add Player']")
  private WebElement addPlayerButton;

  @FindBy(css = ".table")
  private WebElement playerListTable;

  @FindBy(xpath = "//th[@scope='col']")
  List<WebElement> column;

  @FindBy(xpath = "//h1[contains(text(),'Player Info')]")
  private WebElement playerInfoText;

  @FindBy(xpath = "//a[contains(text(),'Back to List')]")
  private WebElement backToList;

  public Players(WebDriver webdriver) {
    super(webdriver);
  }

  public void verifyPageElements() {
    players.click();
    Assert.assertEquals(HOME_PAGE_TITLE, driver.getTitle());
    Assert.assertEquals(PLAYER_NAME_PLACEHOLDER, playernameTextbox.getAttribute("placeholder"));
  }

  public void createPlayer() {
    String options;
    scrollEndOfPage(driver);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    wait.until(ExpectedConditions.visibilityOf(teamDropdown));
    Select select = new Select(teamDropdown);
    List<WebElement> teamNames = select.getOptions();
    int size = teamNames.size();
    for (int i = 1; i < size; i++) {
      playernameTextbox.clear();
      playernameTextbox.sendKeys(Helper.getRandomName());
      options = teamNames.get(i).getText();
      select.selectByVisibleText(options);
      addPlayerButton.click();
    }
  }

  public void verifyPlayersTable() {

    /*Verifying the column headers*/
    Assert.assertEquals(4, column.size());
    Assert.assertEquals("#", column.get(0).getText());
    Assert.assertEquals("Name", column.get(1).getText());
    Assert.assertEquals("Team", column.get(2).getText());
    Assert.assertEquals("Actions", column.get(3).getText());

    /*Verified if the count from database is same as displayed on the webpage*/
    try {
      List<WebElement> row = driver.findElements(By.xpath("//th[@scope='row']"));
      resultSet = executeQueries("select count(*) AS count from player");
      int countFromDB = 0;
      while (resultSet.next()) {
        countFromDB = resultSet.getInt("count");
      }
      Assert.assertEquals(countFromDB, row.size());

      /* Verifying if the table contains values */
      for (int i = 1; i < row.size(); i++) {
        for (int j = 1; j < column.size(); j++) {
          cellValue = playerListTable.findElement(By.xpath(
              "/html/body/app-root/ng-component/div/table/tbody/tr[" + (i + 1) + "]/td[" + (j)
                  + "]"));
          Assert.assertNotNull(cellValue);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * @param id player id is fetched from the dataprovider for each value from the data provider we
   *           fetch the row and click on the view link which leads to the player info page and then
   *           we veirfy the player details against the player database
   */
  public void verifyPlayerDetails(int id) {
    int playerId = 0;
    String playerName = null;
    String team = null;
    List<WebElement> entireRow = playerListTable.findElements(By.xpath("//tbody/tr[" + id + "]"));
    String idValue = driver.findElement(By.xpath("//th[normalize-space()='" + id + "']")).getText();
    Assert.assertEquals(Integer.parseInt(idValue), id);
    WebElement viewLink = driver.findElement(
        By.xpath("(//a[contains(text(),'View')])[" + id + "]"));
    viewLink.click();
    Assert.assertEquals(driver.getCurrentUrl(), WorldcupConfig.Player_URL + "/" + id);
    WebElement ids = driver.findElement(By.cssSelector(".col-2.border.border-primary"));
    WebElement name = driver.findElement(By.cssSelector(".col.border.border-secondary"));
    WebElement teamName = driver.findElement(By.cssSelector(".col.border.border-success"));
    String[] idText = ids.getText().split(":");
    String[] nameText = name.getText().split(":");
    String[] teamNameText = teamName.getText().split(":");
    try {
      resultSet = DBConnection.executeQueries(
          "select * from player natural join team where playerId = " + id);
      while (resultSet.next()) {
        playerId = resultSet.getInt("playerId");
        playerName = resultSet.getString("playerName");
        team = resultSet.getString("teamName");
      }
      Assert.assertEquals(playerId, Integer.parseInt(idText[1].trim()));
      Assert.assertEquals(playerName, nameText[1].trim());
      Assert.assertEquals(team, teamNameText[1].trim());
      backToList.click();
      Assert.assertEquals(driver.getCurrentUrl(), WorldcupConfig.Player_URL);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
