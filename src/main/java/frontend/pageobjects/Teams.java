package frontend.pageobjects;

import static config.WorldcupConfig.ALERT_MESSAGE;
import static config.WorldcupConfig.HOME_PAGE_TITLE;
import static config.WorldcupConfig.TEAM_NAME_PLACEHOLDER;
import static utils.DBConnection.executeQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Helper;

public class Teams extends BasePage {

  ResultSet resultSet;
  WebElement cellValue;

  @FindBy(xpath = "//h1[contains(text(),'Teams List')]")
  private WebElement teamsText;

  @FindBy(xpath = "//a[contains(text(),'Teams')]")
  private WebElement teams;

  @FindBy(css = "input[type='text']")
  private WebElement teamnameTextbox;

  @FindBy(css = "button[type='submit']")
  private WebElement addTeamButton;

  @FindBy(xpath = "//h1[contains(text(),'Team Info')]")
  private WebElement teamInfoText;

  @FindBy(css = "a[href='/teams']")
  private WebElement backToList;

  @FindBy(xpath = "//th[@scope='col']")
  List<WebElement> column;

  @FindBy(css = ".table")
  private WebElement teamListTable;

  public Teams(WebDriver webdriver) {
    super(webdriver);
  }

  public void verifyPageElements() {
    teams.click();
    Assert.assertEquals(HOME_PAGE_TITLE, driver.getTitle());
    Assert.assertEquals(TEAM_NAME_PLACEHOLDER, teamnameTextbox.getAttribute("placeholder"));
  }

  public void createTeam() {
    scrollEndOfPage(driver);
    teamnameTextbox.sendKeys(Helper.getRandomName());
    addTeamButton.click();
  }

  public void createExistingTeam() throws InterruptedException {
    scrollEndOfPage(driver);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    teamnameTextbox.clear();
    teamnameTextbox.sendKeys("Malta");
    addTeamButton.click();
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    Assert.assertEquals(ALERT_MESSAGE, alert.getText());
    alert.accept();
  }

  public void verifyTeamTable() {
    /*Verifying the column headers*/
    Assert.assertEquals(3, column.size());
    Assert.assertEquals("#", column.get(0).getText());
    Assert.assertEquals("Name", column.get(1).getText());
    Assert.assertEquals("View", column.get(2).getText());

    /*Verified if the count from database is same as displayed on the webpage*/
    try {
      List<WebElement> row = driver.findElements(By.xpath("//th[@scope='row']"));
      resultSet = executeQueries("select count(*) AS count from team");
      int countFromDB = 0;
      while (resultSet.next()) {
        countFromDB = resultSet.getInt("count");
      }
      Assert.assertEquals(countFromDB, row.size());

      /* Verifying if the table contains values */
      for (int i = 1; i < row.size(); i++) {
        for (int j = 1; j < column.size(); j++) {
          cellValue = teamListTable.findElement(By.xpath(
              "/html/body/app-root/ng-component/div/table/tbody/tr[" + (i + 1) + "]/td[" + (j)
                  + "]"));
          Assert.assertNotNull(cellValue);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
