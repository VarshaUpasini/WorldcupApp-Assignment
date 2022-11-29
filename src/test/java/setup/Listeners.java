package setup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import frontend.BaseTest;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.Reporting;

public class Listeners extends BaseTest implements ITestListener {

  ExtentTest test;
  ExtentReports extentReport = Reporting.initializeExtentReport();
  ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

  @Override
  public void onTestStart(ITestResult result) {
    ITestListener.super.onTestStart(result);
    test = extentReport.createTest(result.getMethod().getMethodName());
    extentTest.set(test);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    ITestListener.super.onTestSuccess(result);
    test.pass("Test Passed");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    ITestListener.super.onTestFailure(result);
    extentTest.get().fail(result.getThrowable());

    //Attach screenshot to the report
    WebDriver driver = (WebDriver) result.getAttribute("driver");
    try {
      test.addScreenCaptureFromPath(getScreenshot(result.getMethod().getMethodName(), driver),
          result.getMethod().getMethodName());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    ITestListener.super.onTestSkipped(result);
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
  }

  @Override
  public void onTestFailedWithTimeout(ITestResult result) {
    ITestListener.super.onTestFailedWithTimeout(result);
  }

  @Override
  public void onStart(ITestContext context) {
    ITestListener.super.onStart(context);
  }

  @Override
  public void onFinish(ITestContext context) {
    ITestListener.super.onFinish(context);
    extentReport.flush();
  }
}