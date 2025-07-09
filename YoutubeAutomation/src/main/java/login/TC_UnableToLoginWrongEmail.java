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


public class TC_UnableToLoginWrongEmail {
	public static void ulwe(ExtentReports report){
		
		//This test case is to check that a user is unable to login if the email is incorrect and an error message is displayed

		WebDriver driver = new ChromeDriver();	
		Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));//initializing and setting the explicit wait time to 5 seconds
		TakesScreenshot scrShot =((TakesScreenshot)driver);//screenshot initialization
		ExtentTest test = report.createTest("User should be able to login");//Initializing the test case for the report
		
		try {
			
		driver.get("https://www.youtube.com");
				
		//Clicking on the sign in button		
		WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"buttons\"]/ytd-button-renderer/yt-button-shape/a"));
		signInButton.click();
			
		ExtentTest loginStep = test.createNode("Login page");//Creating a test step on the login page
		try {
			WebElement email = driver.findElement(By.id("identifierId"));
			email.sendKeys("hjerfhuerbis@proton.me");//using invalid email and entering it into the email field
			loginStep.pass("Entered the invalid email on the login page", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//passing the step and taking a screenshot
			
		}
		catch (NoSuchElementException n){
			loginStep.fail("Login page step failed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//failed the step and taking a screenshot
			n.printStackTrace();
		}
		
		//Clicking on the Next button
		WebElement emailNextButton = driver.findElement(By.id("identifierNext"));
		emailNextButton.click();
		
		//doing an explicit wait on all 3 elements because otherwise it would not always grab the text from each 3 sections
		wait.until(d -> driver.findElement(By.id("headingText")).isDisplayed());
		wait.until(d -> driver.findElement(By.cssSelector("#yDmH0d > c-wiz > div > div.UXFQgc > div > div > div > form > span > section > div > div > div > div:nth-child(1)")).isDisplayed());
		wait.until(d -> driver.findElement(By.cssSelector("#yDmH0d > c-wiz > div > div.UXFQgc > div > div > div > form > span > section > div > div > div > div:nth-child(2)")).isDisplayed());
		

		WebElement errorHeader = driver.findElement(By.id("headingText"));
		WebElement errorMessage1 = driver.findElement(By.cssSelector("#yDmH0d > c-wiz > div > div.UXFQgc > div > div > div > form > span > section > div > div > div > div:nth-child(1)"));
		WebElement errorMessage2 = driver.findElement(By.cssSelector("#yDmH0d > c-wiz > div > div.UXFQgc > div > div > div > form > span > section > div > div > div > div:nth-child(2)"));

		
		//Checking if the correct error message is displayed
		if (errorHeader.getText().contains("Couldn’t sign you in") && errorMessage1.getText().contains("This browser or app may not be secure. Learn more") && errorMessage2.getText().contains("Try using a different browser. If you’re already using a supported browser, you can try again to sign in."))
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
		
		
		//Creating a second test case to test the Learn More link
		ExtentTest learnMoreTest = report.createTest("The Learn More link should take the user to the correct url");
		
		try {
		
		WebElement learnMoreLink = driver.findElement(By.cssSelector("#yDmH0d > c-wiz > div > div.UXFQgc > div > div > div > form > span > section > div > div > div > div:nth-child(1) > a"));
		learnMoreLink.click();
		
		
		//Switching to the new browser tab that was opened by the link
		Object[] windowHandles=driver.getWindowHandles().toArray();
		driver.switchTo().window((String) windowHandles[1]);
				
		
		//Checking if the url is correct
		if (driver.getCurrentUrl().contains("https://support.google.com/accounts/answer/7675428"))
		{
			//passed the test case and takes a screenshot
			learnMoreTest.pass("The Learn More link takes the user to the correct url", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
		}

		else
		{
			learnMoreTest.fail("The user was not taken to the correct url.", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
		}
		
		}catch(NoSuchElementException ne) {
			learnMoreTest.fail("\"No such element\" error before the final step", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			ne.printStackTrace();
		}catch(Exception e) {
			learnMoreTest.fail("The test case failed before the final step", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			e.printStackTrace();
		}
		
				
		report.flush();//writes the test information to the destination
		driver.quit();		
	}
}
