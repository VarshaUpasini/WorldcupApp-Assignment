package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporting {

  public static ExtentReports extent;
  public static ExtentSparkReporter spark;
  public static ExtentTest test;

  public static ExtentReports initializeExtentReport() {
    spark = new ExtentSparkReporter( "index.html");
    spark.config().setTheme(Theme.DARK);
    spark.config().setDocumentTitle("Extent Test Report");
    spark.config().setReportName("Automation Results");
    extent = new ExtentReports();
    extent.attachReporter(spark);
    extent.setSystemInfo("Tester","Varsha");
    return extent;
  }
}
