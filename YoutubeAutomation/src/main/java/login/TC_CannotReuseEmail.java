package login;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;

public class TC_CannotReuseEmail {

	public static void cre(ExtentReports report){
	
		//This test case is to test that a user should be unable to create a new Youtube account with an email that is already linked to a Youtube account through the typical method of account creation.
		//I'm not creating a test case for the creation of an account because the captcha required to create new accounts and emails prevent this
		
		WebDriver driver = new ChromeDriver();	
		Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));//initializing and setting the explicit wait time to 5 seconds
		TakesScreenshot scrShot =((TakesScreenshot)driver);//screenshot initialization
		ExtentTest test = report.createTest("Should not be able to reuse email when creating a new account");//Initializing the test case for the report
		
		try {
			
		//The following steps begins the account creation process
		driver.get("https://www.youtube.com");// Open browser and navigate to Youtube
				
		WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"buttons\"]/ytd-button-renderer/yt-button-shape/a"));
		signInButton.click();
		
		wait.until(d -> driver.findElement(By.cssSelector("#yDmH0d > c-wiz > div > div.JYXaTc > div > div.FO2vFd > div > div > div:nth-child(1) > div > button")).isDisplayed());
		
		WebElement createAccountButton = driver.findElement(By.cssSelector("#yDmH0d > c-wiz > div > div.JYXaTc > div > div.FO2vFd > div > div > div:nth-child(1) > div > button"));
		createAccountButton.click();
		
		//Proceeds through the account creation process steps until the email field is displayed.
		WebElement forMyPersonalUse = driver.findElement(By.className("VfPpkd-StrnGf-rymPhb-b9t22c"));
		forMyPersonalUse.click();
		
		WebElement firstName = driver.findElement(By.id("firstName"));
		firstName.sendKeys("Bob");
		
		WebElement lastName = driver.findElement(By.id("lastName"));
		lastName.sendKeys("Selman");
		
		WebElement nameNextButton = driver.findElement(By.id("collectNameNext"));
		nameNextButton.click();

		wait.until(d -> driver.findElement(By.id("day")).isDisplayed());//wait until the page loads
		
		WebElement day = driver.findElement(By.id("day"));
		day.sendKeys("15");
		
		WebElement year = driver.findElement(By.id("year"));
		year.sendKeys("2000");
		
		WebElement monthDropdown = driver.findElement(By.xpath("//*[@id=\"month\"]/div/div[1]/div"));
		monthDropdown.click();
		driver.findElement(By.xpath("//*[@id=\"month\"]/div/div[2]/ul/li[7]")).click();
				
		WebElement genderDropdown = driver.findElement(By.id("gender"));
		genderDropdown.click();
		driver.findElement(By.xpath("//*[@id=\"gender\"]/div/div[2]/ul/li[2]")).click();

		WebElement birthdayNextButton = driver.findElement(By.cssSelector("#birthdaygenderNext > div > button"));
		birthdayNextButton.click();
		
		wait.until(d -> driver.findElement(By.id("identifierId")).isDisplayed());
		
		//Creating a test step before the Next button is pressed
		ExtentTest emailStep = test.createNode("Before the Next button on the email page is pressed");
				
		try {
			//Entering the email address that is already being used by the test Youtube account and clicking on the next button
			WebElement email = driver.findElement(By.id("identifierId"));
	
			emailStep.pass("Email is entered", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//passing the step and taking a screenshot
			
			email.sendKeys("SelTest72@proton.me");
		}
		catch (NoSuchElementException n){
			emailStep.fail("Failed on the email page step", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//failed the step and taking a screenshot
			n.printStackTrace();
		}
		
		
		WebElement emailNextButton = driver.findElement(By.id("next"));
		emailNextButton.click();
		
		
		WebElement errorMessage = driver.findElement(By.id("c20"));
		wait.until(d -> errorMessage.isDisplayed());//wait until the element is interactable
		
		//Checking if the error message is displayed
		if (errorMessage.getText().contains("That username is taken. Try another."))
		{
			//passed the test case and takes a screenshot
			test.pass("The error message is displayed after the Next button is pressed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
		}

		else
		{
			test.fail("The error message is not displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
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
