package studiocontent;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import reusable.Config;
import reusable.ReusableMethods;

public class TC_UploadVideo {
	public static void uv(ExtentReports report){
		
		//This test case is to check that the user is able to upload valid video file formats

		WebDriver driver = new ChromeDriver();	
		Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));//initializing and setting the explicit wait time to 5 seconds
		TakesScreenshot scrShot =((TakesScreenshot)driver);//screenshot initialization
		ExtentTest test = report.createTest("User should be able to upload video files");//Initializing the test case for the report
		
		//try {
			

		driver.get("https://www.youtube.com");
			
		//calling the method to login
		ReusableMethods.login(driver);
		
		//Navigating to the Studio Content page
		wait.until(d -> driver.findElement(By.id("img")).isDisplayed());
		WebElement profileButton = driver.findElement(By.id("img"));
		profileButton.click();
		
		wait.until(d -> driver.findElement(By.linkText("YouTube Studio")).isDisplayed());
		
		//WebElement studioButton = driver.findElement(By.id("primary-text-container"));
		WebElement studioButton = driver.findElement(By.linkText("YouTube Studio"));
		studioButton.click();
		
		
		//Starting the upload
		wait.until(d -> driver.findElement(By.cssSelector("#upload-icon > tp-yt-iron-icon")));
		WebElement uploadButton = driver.findElement(By.cssSelector("#upload-icon > tp-yt-iron-icon"));
		uploadButton.click();
		
		WebElement selectFileButton = driver.findElement(By.name("Filedata"));
		File video1 = new File("target/cat.webm");//instead of manually entering the absolute path, having the file saved to the variable and calling getAbsolutePath() so anyone that downloads the repository can run it without changing the filepath
		selectFileButton.sendKeys(video1.getAbsolutePath());
		
		wait.until(d -> driver.findElement(By.name("VIDEO_MADE_FOR_KIDS_NOT_MFK")).isDisplayed());
		WebElement kidsRadioButton = driver.findElement(By.name("VIDEO_MADE_FOR_KIDS_NOT_MFK"));//clicking on mandatory radio button option
		kidsRadioButton.click();
		
		WebElement nextButton = driver.findElement(By.cssSelector("#next-button > ytcp-button-shape > button > yt-touch-feedback-shape > div > div.yt-spec-touch-feedback-shape__fill"));
		nextButton.click();
		wait.until(d -> nextButton.isDisplayed());
		nextButton.click();
		wait.until(d -> nextButton.isDisplayed());
		nextButton.click();
		
		//wait.until(d -> driver.findElement(By.id("radioContainer")).isDisplayed());
		WebElement privateRadioButton = driver.findElement(By.id("private-radio-button"));
		privateRadioButton.click();
		
		WebElement saveButton = driver.findElement(By.id("done-button"));
		saveButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("done-button")));
		saveButton.click();
		
		ExtentTest loginStep = test.createNode("After login");
		try {

			loginStep.pass("Login successful", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//passing the step and taking a screenshot
			
		}
		catch (NoSuchElementException n){
			loginStep.fail("Login failed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//failed the step and taking a screenshot
			n.printStackTrace();
		}
			
		/*
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
		//driver.quit();	*/
	}
}
