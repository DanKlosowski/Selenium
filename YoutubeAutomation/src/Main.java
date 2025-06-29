import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

	public static void main(String[] args) {
		// Set the path to ChromeDriver (replace with your actual path)
		System.setProperty("webdriver.chrome.driver", "C:/Users/bones/eclipse-workspace/chromedriver-win64/chromedriver.exe");

		// Create a new ChromeDriver instance
		WebDriver driver = new ChromeDriver();

		// Open browser and navigate to Youtube
		driver.get("https://www.youtube.com");
		
		// Close the browser
		// driver.quit();

	}

}