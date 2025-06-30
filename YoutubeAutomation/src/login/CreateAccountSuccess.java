package login;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreateAccountSuccess {

	public static void cas(){
		
		// Create a new ChromeDriver instance
		//WebDriver driver = new ChromeDriver();
			
		// Create a new ChromeDriver instance
		WebDriver driver = new ChromeDriver();
		// Open browser and navigate to Youtube
		driver.get("https://www.youtube.com");
		
		//driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50000));
		
		WebElement loginButton = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/div[2]/ytd-masthead/div[4]/div[3]/div[2]/ytd-button-renderer"));
		loginButton.click();
		
	}
	
}