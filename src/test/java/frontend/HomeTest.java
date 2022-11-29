package frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {

  private static final Logger logger = LoggerFactory.getLogger(HomeTest.class);

  /* test to verify the contenets of the home page */
  @Test
  public void testVerifyHomeContents() {
    home.verifyHome();
    logger.info("Home page verified successfully");
  }
}
