package login;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import reusable.*;

public class TC_UserAbleToLogin {
	public static void ual(ExtentReports report){
		
		//This test case is to check that a user is able to login with an existing account.

		
		WebDriver driver = new ChromeDriver();	
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));//implicit wait of 5 seconds for all actions
		TakesScreenshot scrShot =((TakesScreenshot)driver);//screenshot initialization
		ExtentTest test = report.createTest("User should be able to login");//Initializing the test case for the report
		
		try {
			

		driver.get("https://www.youtube.com");
			
		//calling the method to login
		ReusableMethods.login(driver);
		
		
		ExtentTest loginStep = test.createNode("After login");//Creating a test step immediately after login
		try {
			WebElement profileButton = driver.findElement(By.id("img"));

			loginStep.pass("Login successful", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//passing the step and taking a screenshot
			
			//clicking on the profile icon to open the menu that displays the account name
			profileButton.click();
		}
		catch (NoSuchElementException n){
			loginStep.fail("Login failed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//failed the step and taking a screenshot
			n.printStackTrace();
		}
		
		
		
		WebElement accountName = driver.findElement(By.id("account-name"));
				
		//Checking if the correct account name is displayed
		if (accountName.getText().contains(Config.userName))
		{
			//passed the test case and takes a screenshot
			test.pass("The user was able to login and the correct account name is displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
		}

		else
		{
			test.fail("The correct account name is not displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
		}
		//Try catch to fail the test case and give a different fail message if the test fails before the final step
		}catch(NoSuchElementException ne) {
			test.fail("\"No such element\" error before the final step", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			ne.printStackTrace();
		}catch(Exception e) {
			test.fail("The test case failed before the final step", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			e.printStackTrace();
		}
		
		report.flush();//writes the test information to the destination
		driver.quit();	
	}
}
