package frontend.pageobjects;

import static config.WorldcupConfig.HOME_PAGE_TEXT;
import static config.WorldcupConfig.HOME_PAGE_TITLE;

import config.WorldcupConfig;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

@Data
public class Home extends BasePage {

  @FindBy(xpath = "//a[contains(text(),'World Cup')]")
  private WebElement worldcup;

  @FindBy(xpath = "//a[contains(text(),'Home')]")
  private WebElement home;

  @FindBy(xpath = "//a[contains(text(),'Players')]")
  private WebElement players;

  @FindBy(xpath = "//a[contains(text(),'Teams')]")
  private WebElement teams;

  @FindBy(xpath = "//h1[contains(text(),'This is the main page')]")
  private WebElement homePageText;

  public Home(WebDriver webdriver) {
    super(webdriver);
  }

  public void goTo() {
    driver.get(WorldcupConfig.url);
  }

  public void verifyHome() {
    Assert.assertEquals(HOME_PAGE_TITLE, driver.getTitle());
    Assert.assertEquals(HOME_PAGE_TEXT, homePageText.getText());
    players.click();
    Assert.assertEquals(HOME_PAGE_TITLE, driver.getTitle());
    teams.click();
    Assert.assertEquals(HOME_PAGE_TITLE, driver.getTitle());
  }
}
