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
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import reusable.ReusableMethods;

public class TC_UploadVideoWrongFileType {
	public static void uvwf(ExtentReports report){
		
		//This test case is to check that the user is not able to upload files that aren't video file formats

		WebDriver driver = new ChromeDriver();	
		Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));//initializing and setting the explicit wait time to 5 seconds
		TakesScreenshot scrShot =((TakesScreenshot)driver);//screenshot initialization
		ExtentTest test = report.createTest("User shouldn't be able to upload non video files");//Initializing the test case for the report
		
		try {
			

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
		wait.until(d -> driver.findElement(By.cssSelector("#upload-icon > tp-yt-iron-icon")).isDisplayed());
		WebElement uploadButton = driver.findElement(By.cssSelector("#upload-icon > tp-yt-iron-icon"));
		uploadButton.click();
		
		WebElement selectFileButton = driver.findElement(By.name("Filedata"));
		File video1 = new File("target/catvideo.txt");//instead of manually entering the absolute path, having the file saved to the variable and calling getAbsolutePath() so anyone that downloads the repository can run it without changing the filepath
		selectFileButton.sendKeys(video1.getAbsolutePath());
		
		//Test step is passed if the correct error message is displayed for the text file
		ExtentTest txtStep = test.createNode("Attempting to upload txt file");
		try {
			WebElement errorMessage = driver.findElement(By.cssSelector("#content > div > yt-formatted-string"));
			if (errorMessage.getText().contains("Invalid file format. Learn more")) {
				txtStep.pass("The correct error message is displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			}
			else {
				txtStep.fail("Correct error message is not displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			}
		}
		catch (Exception n){
			txtStep.fail("Correct error message is not displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			n.printStackTrace();
		}
		
		//Test step is passed if the correct error message is displayed for the jpg file
		ExtentTest jpgStep = test.createNode("Attempting to upload jpg file");
		
		driver.navigate().refresh();//refreshes the page

		wait.until(d -> driver.findElement(By.cssSelector("#upload-icon > tp-yt-iron-icon")).isDisplayed());
		WebElement uploadButton2 = driver.findElement(By.cssSelector("#upload-icon > tp-yt-iron-icon"));//new variable needs to be created for the same button to avoid stale error after refresh
		uploadButton2.click();
		
		File video2 = new File("target/catvideo.jpg");
		WebElement selectFileButton2 = driver.findElement(By.name("Filedata"));
		selectFileButton2.sendKeys(video2.getAbsolutePath());
		
		try {
			WebElement errorMessage = driver.findElement(By.cssSelector("#content > div > yt-formatted-string"));
			if (errorMessage.getText().contains("Invalid file format. Learn more")) {
				jpgStep.pass("The correct error message is displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			}
			else {
				jpgStep.fail("Correct error message is not displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			}
		}
		catch (Exception n){
			jpgStep.fail("Correct error message is not displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			n.printStackTrace();
		}
		
		//Test step is passed if the correct error message is displayed for the xlsx file
		ExtentTest xlsxStep = test.createNode("Attempting to upload xlsx file");
		
		driver.navigate().refresh();//refreshes the page

		wait.until(d -> driver.findElement(By.cssSelector("#upload-icon > tp-yt-iron-icon")).isDisplayed());
		WebElement uploadButton3 = driver.findElement(By.cssSelector("#upload-icon > tp-yt-iron-icon"));
		uploadButton3.click();
		
		File video3 = new File("target/catvideo.xlsx");
		WebElement selectFileButton3 = driver.findElement(By.name("Filedata"));
		selectFileButton3.sendKeys(video3.getAbsolutePath());
		
		try {
			WebElement errorMessage = driver.findElement(By.cssSelector("#content > div > yt-formatted-string"));
			if (errorMessage.getText().contains("Invalid file format. Learn more")) {
				xlsxStep.pass("The correct error message is displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			}
			else {
				xlsxStep.fail("Correct error message is not displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			}
		}
		catch (Exception n){
			xlsxStep.fail("Correct error message is not displayed", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			n.printStackTrace();
		}
		
		//Checking the video list that a new video row was not created
		WebElement closeButton = driver.findElement(By.id("close-button"));
		closeButton.click();
		
		WebElement contentButton = driver.findElement(By.cssSelector("#menu-paper-icon-item-1 > div.nav-item-text.style-scope.ytcp-navigation-drawer"));
		contentButton.click();
		
		boolean videoTabPassed = true;
		wait.until(d -> driver.findElement(By.cssSelector("#video-list-shorts-tab > div > ytcp-ve > span")).isDisplayed());
		
		if(driver.getPageSource().contains("No content available")==false) {//checking for No content available because that is only displayed when there are no uploads
			test.fail("A video row was created for the file", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
			videoTabPassed = false; 
		}
		
		WebElement shortsTab = driver.findElement(By.cssSelector("#video-list-shorts-tab > div > ytcp-ve > span"));
		shortsTab.click();
		
		Thread.sleep(1000);//1 sec wait for tab transition
				
		if(driver.getPageSource().contains("No content available")==false) {
			test.fail("A shorts row was created for the file", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
		}
		else if(videoTabPassed == true) {
			test.pass("The file was not uploaded", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
		}
		
		//Try catch to fail the test case and give a different fail message if the test fails before the final step and not within the steps
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
