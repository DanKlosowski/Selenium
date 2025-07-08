package login;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import reusable.Config;

public class TC_UnableToLoginWrongPassword {
	public static void ulwp(ExtentReports report){
		
		//This test case is to check that a user is unable to login if the password is incorrect and an error message is displayed

		WebDriver driver = new ChromeDriver();
		Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));//initializing and setting the explicit wait time to 5 seconds
		TakesScreenshot scrShot =((TakesScreenshot)driver);//screenshot initialization
		ExtentTest test = report.createTest("User should be able to login");//Initializing the test case for the report
		
		try {
		
		//Going through the login process
		driver.get("https://www.youtube.com");
				
		WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"buttons\"]/ytd-button-renderer/yt-button-shape/a"));
		signInButton.click();
		
		WebElement email = driver.findElement(By.id("identifierId"));
		email.sendKeys(Config.userEmail);
		
		WebElement emailNextButton = driver.findElement(By.id("identifierNext"));
		emailNextButton.click();
		
		wait.until(d -> driver.findElement(By.name("Passwd")).isDisplayed());
		
				
		
		ExtentTest passwordStep = test.createNode("Login page");//Creating a test step after entering the password
		try {
			WebElement password = driver.findElement(By.name("Passwd"));
			password.sendKeys("dfgwefewr");//enters invalid password
			
			passwordStep.pass("Entered the invalid password", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//passing the step and taking a screenshot
		}
		catch (NoSuchElementException n){
			passwordStep.fail("Password entry step failed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//failed the step and taking a screenshot
			n.printStackTrace();
		}
		
		WebElement passwordNextButton = driver.findElement(By.id("passwordNext"));
		passwordNextButton.click();
		
		wait.until(d -> driver.findElement(By.id("c0")).isDisplayed());//explicit wait for the error text to be displayed 
		
		WebElement errorMessage = driver.findElement(By.id("c0"));//gets the error message webelement
		
		//Checking if the correct error message is displayed
		if (errorMessage.getText().contains("Wrong password. Try again or click Forgot password to reset it."))
		{
			//passed the test case and takes a screenshot
			test.pass("The user was unable to login and the correct error message was displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
		}

		else
		{
			test.fail("The correct error message was not displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
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
