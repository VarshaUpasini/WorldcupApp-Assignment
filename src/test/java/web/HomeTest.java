package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest{
  private static final Logger logger = LoggerFactory.getLogger(HomeTest.class);

  @Test
  public void testVerifyHomeContents() {
    home.verifyHome();
    logger.info("Home page verified successfully");
  }
}
