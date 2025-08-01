package studiocontent;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
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
		
		try {

		//calling the method to login
		ReusableMethods.login(driver);
		
		//Navigating to the Studio Content page
		wait.until(d -> driver.findElement(By.id("img")).isDisplayed());
		WebElement profileButton = driver.findElement(By.id("img"));
		profileButton.click();
		
		wait.until(d -> driver.findElement(By.linkText("YouTube Studio")).isDisplayed());
		
		WebElement studioButton = driver.findElement(By.linkText("YouTube Studio"));
		studioButton.click();

		//Uploading the WebM file and filling the required fields
		wait.until(d -> driver.findElement(By.cssSelector("#upload-icon > tp-yt-iron-icon")));
		WebElement uploadButton = driver.findElement(By.cssSelector("#upload-icon > tp-yt-iron-icon"));
		uploadButton.click();
		
		WebElement selectFileButton = driver.findElement(By.name("Filedata"));
		File video1 = new File("UploadFiles/catvideowebm.webm");//instead of manually entering the absolute path, having the file saved to the variable and calling getAbsolutePath() so anyone that downloads the repository can run it without changing the filepath
		selectFileButton.sendKeys(video1.getAbsolutePath());
		
		wait.until(d -> driver.findElement(By.name("VIDEO_MADE_FOR_KIDS_NOT_MFK")).isDisplayed());
		WebElement kidsRadioButton = driver.findElement(By.name("VIDEO_MADE_FOR_KIDS_NOT_MFK"));//clicking on mandatory radio button option
		kidsRadioButton.click();
		
		WebElement nextButton = driver.findElement(By.cssSelector("#next-button > ytcp-button-shape > button > yt-touch-feedback-shape > div > div.yt-spec-touch-feedback-shape__fill"));
		nextButton.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#next-button > ytcp-button-shape > button > yt-touch-feedback-shape > div > div.yt-spec-touch-feedback-shape__fill")));
		nextButton.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#next-button > ytcp-button-shape > button > yt-touch-feedback-shape > div > div.yt-spec-touch-feedback-shape__fill")));
		nextButton.click();
		
		WebElement privateRadioButton = driver.findElement(By.id("private-radio-button"));
		privateRadioButton.click();
		
		WebElement saveButton = driver.findElement(By.id("done-button"));
		saveButton.click();
		
		while(driver.getPageSource().contains("Disabled while we prepare your video")){//loop because the Save button is disabled during processing
			Thread.sleep(500);
			saveButton.click();
			}
		
		//Navigating to the page to view uploaded videos
		wait.until(d -> driver.findElement(By.cssSelector("#close-button > ytcp-button-shape > button")).isDisplayed());
		WebElement closeButton = driver.findElement(By.cssSelector("#close-button > ytcp-button-shape > button"));
		closeButton.click();
		
		Thread.sleep(1200);//waiting because the video doesn't show up in the list when moving too quickly
		
		WebElement contentButton = driver.findElement(By.cssSelector("#menu-paper-icon-item-1 > div.nav-item-text.style-scope.ytcp-navigation-drawer"));
		contentButton.click();

		wait.until(d -> driver.findElement(By.cssSelector("#video-list-shorts-tab > div > ytcp-ve > span")).isDisplayed());
		WebElement shortsTab = driver.findElement(By.cssSelector("#video-list-shorts-tab > div > ytcp-ve > span"));
		shortsTab.click();
		
		Thread.sleep(1000);//1 sec wait for tab transition

		ExtentTest webmStep = test.createNode("WebM file should be uploaded");
		boolean webmPass = false;

		//Checking for the WebM video file
		try {
			//setting up a try/catch because sometimes the video does not appear in the list so quickly but will appear after a refresh
			try {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("video-title")));
			}catch(TimeoutException t){
				driver.navigate().refresh();
			}

			WebElement videoTitle = driver.findElement(By.id("video-title"));
			if(videoTitle.getText().contains("catvideowebm")) {
				webmStep.pass("The WebM video was uploaded", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
				webmPass = true;
			}
			else {
				webmStep.fail("Unable to find the WebM video upload title", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//failed the step and taking a screenshot
			}
			
			//Deleting the upload so that future tests are not affected
			
			//Setting up a hover action to be able to click on the kebab button
			Actions action = new Actions(driver);
			action.moveToElement(videoTitle).perform();

			WebElement kebabButton = driver.findElement(By.cssSelector("#hover-items > ytcp-icon-button > tp-yt-iron-icon"));
			action.moveToElement(kebabButton);
			action.click().perform();
			
			WebElement deleteButton = driver.findElement(By.cssSelector("#text-item-5 > ytcp-ve > tp-yt-paper-item-body > div > div > div > yt-formatted-string"));
			deleteButton.click();
			
			wait.until(d -> driver.findElement(By.id("confirm-checkbox")).isDisplayed());
			WebElement deleteCheckbox = driver.findElement(By.id("confirm-checkbox"));
			deleteCheckbox.click();
			
			WebElement finalDeleteButton = driver.findElement(By.id("confirm-button"));
			finalDeleteButton.click();
			Thread.sleep(1000);//Need to wait or else the delete won't process
		}
		catch (NoSuchElementException n){
			webmStep.fail("Unable to find the WebM video upload element", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//failed the step and taking a screenshot
			n.printStackTrace();
		}


		
		//Doing the same process for uploading an MP4 file and filling the required fields
		wait.until(ExpectedConditions.elementToBeClickable(By.id("create-icon")));
		WebElement createButton = driver.findElement(By.id("create-icon"));
		createButton.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#text-item-0 > ytcp-ve > tp-yt-paper-item-body > div > div > div")));
		WebElement uploadVideosButton = driver.findElement(By.cssSelector("#text-item-0 > ytcp-ve > tp-yt-paper-item-body > div > div > div"));
		uploadVideosButton.click();

		WebElement selectFileButton2 = driver.findElement(By.name("Filedata"));
		File video2 = new File("UploadFiles/catvideomp4.mp4");//instead of manually entering the absolute path, having the file saved to the variable and calling getAbsolutePath() so anyone that downloads the repository can run it without changing the filepath
		selectFileButton2.sendKeys(video2.getAbsolutePath());
		
		wait.until(d -> driver.findElement(By.name("VIDEO_MADE_FOR_KIDS_NOT_MFK")).isDisplayed());
		WebElement kidsRadioButton2 = driver.findElement(By.name("VIDEO_MADE_FOR_KIDS_NOT_MFK"));//clicking on mandatory radio button option
		kidsRadioButton2.click();
		
		WebElement nextButton2 = driver.findElement(By.cssSelector("#next-button > ytcp-button-shape > button > yt-touch-feedback-shape > div > div.yt-spec-touch-feedback-shape__fill"));
		nextButton2.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#next-button > ytcp-button-shape > button > yt-touch-feedback-shape > div > div.yt-spec-touch-feedback-shape__fill")));
		nextButton2.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#next-button > ytcp-button-shape > button > yt-touch-feedback-shape > div > div.yt-spec-touch-feedback-shape__fill")));
		driver.findElement(By.cssSelector("#next-button > ytcp-button-shape > button > yt-touch-feedback-shape > div > div.yt-spec-touch-feedback-shape__fill")).click();


		WebElement privateRadioButton2 = driver.findElement(By.id("private-radio-button"));
		privateRadioButton2.click();
		
		WebElement saveButton2 = driver.findElement(By.id("done-button"));
		saveButton2.click();
		
		while(driver.getPageSource().contains("Disabled while we prepare your video")){//loop because the Save button is disabled during processing
			Thread.sleep(500);
			saveButton2.click();
			}

		wait.until(d -> driver.findElement(By.cssSelector("#close-button > ytcp-button-shape > button")).isDisplayed());
		WebElement closeButton2 = driver.findElement(By.cssSelector("#close-button > ytcp-button-shape > button"));
		closeButton2.click();

		// Checking for the MP4 video file
		ExtentTest mp4Step = test.createNode("MP4 file should be uploaded");
		boolean mp4Pass = false;

		try {
			//setting up a try/catch because sometimes the video does not appear in the list so quickly but will appear after a refresh
			try {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("video-title")));
			}catch(TimeoutException t){
				driver.navigate().refresh();
			}

			WebElement videoTitle2 = driver.findElement(By.id("video-title"));
			
			if(videoTitle2.getText().contains("catvideomp4")) {
				mp4Step.pass("The MP4 video was uploaded", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
				mp4Pass = true;
			}
			else {
				mp4Step.fail("Unable to find the MP4 video upload title", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//failed the step and taking a screenshot
			}
			
			//Deleting the upload so that future tests are not affected
			
			//Setting up a hover action to be able to click on the kebab button
			Actions action = new Actions(driver);
			wait.until(d -> driver.findElement(By.id("video-title")).isDisplayed());
			action.moveToElement(videoTitle2).perform();

			WebElement kebabButton2 = driver.findElement(By.cssSelector("#hover-items > ytcp-icon-button > tp-yt-iron-icon"));
			action.moveToElement(kebabButton2);
			action.click().perform();
			
			WebElement deleteButton2 = driver.findElement(By.cssSelector("#text-item-5 > ytcp-ve > tp-yt-paper-item-body > div > div > div > yt-formatted-string"));
			deleteButton2.click();
			
			wait.until(d -> driver.findElement(By.id("confirm-checkbox")).isDisplayed());
			WebElement deleteCheckbox2 = driver.findElement(By.id("confirm-checkbox"));
			deleteCheckbox2.click();
			
			WebElement finalDeleteButton2 = driver.findElement(By.id("confirm-button"));
			finalDeleteButton2.click();
			Thread.sleep(1000);//Need to wait or else the delete won't process
		}
		catch (NoSuchElementException n){
			mp4Step.fail("Unable to find the MP4 video upload element", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());//failed the step and taking a screenshot
			n.printStackTrace();
		}
		
		
		
		//If both steps passed then the test case passes
		if (webmPass == true && mp4Pass == true)
		{
			test.pass("The video files were uploaded");
		}
		else if (webmPass == true && mp4Pass == false)
		{
			test.fail("The MP4 file was not uploaded", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
		}
		else if (webmPass == false && mp4Pass == true)
		{
			test.fail("The WebM file was not uploaded", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
		}
		else if (webmPass == false && mp4Pass == false)
		{
			test.fail("Both video files were not uploaded", MediaEntityBuilder.createScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64)).build());
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
