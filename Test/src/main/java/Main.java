import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    public static void main(String[] args) {
        // Set the path to ChromeDriver (replace with your actual path)
        System.setProperty("webdriver.chrome.driver", "C:/Users/bones/eclipse-workspace/chromedriver-win64/chromedriver.exe");

        // Create a new ChromeDriver instance
        WebDriver driver = new ChromeDriver();

        // Navigate to a website
        driver.get("https://www.youtube.com");
        //driver.findElement(null)
        // Perform actions (e.g., find an element and interact with it)

        // Close the browser
        //driver.quit();
    }
}