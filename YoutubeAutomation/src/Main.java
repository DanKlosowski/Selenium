import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import login.CreateAccountSuccess;

public class Main {

	public static void main(String[] args) {
		// Set the path to ChromeDriver (replace with your actual path)
		System.setProperty("webdriver.chrome.driver", "C:/Users/bones/eclipse-workspace/chromedriver-win64/chromedriver.exe");

		
		CreateAccountSuccess.cas();
		
		
		// Close the browser
		// driver.quit();

	}

}