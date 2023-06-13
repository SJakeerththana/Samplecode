import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Assignment {
	private static final Logger LOGGER = Logger.getLogger(Assignment.class);
	public static ExtentTest extentTest;
	public static ExtentReports extentReport = new ExtentReports(System.getProperty("user.dir") +"/src/test/resources/reports/ExtentReportResults.html");
	WebDriver driver;

	@BeforeTest
	public void openBrowser() {
		System.setProperty("webdriver.gecko.driver", "driver//geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		LOGGER.info("Google Page Opened");
		extentTest=extentReport.startTest("Check Ebay Login functinality");
	}

	@Test
	public void signinEbay() {
		driver.findElement(By.xpath("//input[@class = 'gLFyf gsfi']")).sendKeys("ebay");
		driver.findElement(By.xpath("(//input[@class = 'gNO89b'])[2]")).click();
		driver.findElement(By.xpath("//*[text() = 'eBay: Electronics, Cars, Fashion, Collectibles, Coupons and More']"))
				.click();
        extentTest.log(LogStatus.PASS, "ebay opened successfully");
		driver.findElement(By.xpath("//*[text() = 'Sign in']")).click();
		driver.findElement(By.id("userid")).sendKeys("jakeerththana1994@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("1234@hello");
		driver.findElement(By.id("sgnBt")).click();
		extentTest.log(LogStatus.PASS, "Login Successfully");
		WebElement sucMsg = driver.findElement(By.xpath("//*[text() = 'Jakeerththana']"));
		System.out.println(sucMsg.isDisplayed());
		extentTest.log(LogStatus.PASS, "Verified UserName");
		driver.findElement(By.id("gh-ug")).click();
		driver.findElement(By.xpath("//*[text() = 'Sign out']")).click();
		extentTest.log(LogStatus.PASS, "Logout Successfully");
	}

	@AfterMethod(alwaysRun = true)
	public void endTest(ITestResult result) {
			extentReport.endTest(extentTest);
	}

	@AfterMethod
	public void afterMethod(Method method, ITestResult result) {
		LOGGER.info("Executed test case name:" + method.getName() + " Execution Results : " + result.toString());
	}

	@AfterTest
	public void closeBrowser() {
		driver.close();
		extentReport.flush();
	}

}
