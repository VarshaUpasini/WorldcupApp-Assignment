package frontend;

import frontend.pageobjects.Home;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

  public static WebDriver driver;
  public Home home;

  public WebDriver initializeDriver() throws IOException {
    //to fetch property value from properties file
    Properties prop = new Properties();
    FileInputStream fis = new FileInputStream(
        System.getProperty("user.dir") + "\\src\\main\\java\\config\\Global.properties");
    prop.load(fis);
    String browser = prop.getProperty("browser");
    switch (browser.toLowerCase()) {
      case "chrome":
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        break;
      case "edge":
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        break;
      case "firefox":
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        break;
      case "safari":
        WebDriverManager.safaridriver().setup();
        driver = new SafariDriver();
        break;
    }
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    return driver;
  }

  public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
    File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
    File file = new File(System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png");
    FileUtils.copyFile(source, file);
    System.out.println("********************************" + System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png");
    return (System.getProperty(("user.dir") + "\\reports\\" + testCaseName + ".png"));
  }

  @BeforeMethod(alwaysRun = true)
  public Home launchHome() throws IOException {
    driver = initializeDriver();
    home = new Home(driver);
    home.goTo();
    return home;
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws InterruptedException {
    Thread.sleep(2000);
    driver.quit();
  }
}
