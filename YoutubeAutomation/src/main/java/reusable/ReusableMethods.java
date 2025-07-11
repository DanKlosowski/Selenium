package reusable;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import reusable.Config;


public class ReusableMethods {

	//Method to take screenshots for my test cases
	/*public static void Screenshot (WebDriver driver, String filePath) throws IOException {
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File srcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File destFile=new File(filePath);
		Files.copy(srcFile, destFile);
		
	}*/
	
	//Method to login with the test account
	public static void login (WebDriver driver) {
		Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));//initializing and setting the explicit wait time to 5 seconds
		
		WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"buttons\"]/ytd-button-renderer/yt-button-shape/a"));
		signInButton.click();
		
		WebElement email = driver.findElement(By.id("identifierId"));
		email.sendKeys(Config.userEmail);//takes username from Config file and enters it into the email field
		
		WebElement emailNextButton = driver.findElement(By.id("identifierNext"));
		emailNextButton.click();
		
		wait.until(d -> driver.findElement(By.name("Passwd")).isDisplayed());
		
		WebElement password = driver.findElement(By.name("Passwd"));
		password.sendKeys(Config.userPassword);//takes password from Config file and enters it into the password field
		
		WebElement passwordNextButton = driver.findElement(By.id("passwordNext"));
		passwordNextButton.click();
	}
}