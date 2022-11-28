package setup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.Reporting;
import web.BaseTest;
import web.HomeTest;

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
    //test = extentReport.createTest(result.getMethod().getMethodName());
    extentTest.get().fail(result.getThrowable());
    //test.fail(result.getThrowable() + "Test Failed");

    //Attach screenshot to the report

    try {
      driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
          .get(result.getInstance());

    } catch (Exception e) {
      e.printStackTrace();
    }
    String filePath = null;
    try {
      filePath = getScreenshot(result.getMethod().getMethodName(),driver);
    } catch (IOException e) {
      e.printStackTrace();
    }
    test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
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
