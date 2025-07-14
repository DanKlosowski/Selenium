import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import login.*;
import studiocontent.TC_UploadVideo;
import studiocontent.TC_UploadVideoWrongFileType;

public class Main {

	public static void main(String[] args) {
		// Set the path to ChromeDriver (replace with your actual path)
		System.setProperty("webdriver.chrome.driver", "C:/Users/bones/eclipse-workspace/chromedriver-win64/chromedriver.exe");
		//setting up a date string to include in the report filename so that a new report is created instead of overwriting the old file
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
		LocalDateTime date = LocalDateTime.now();
		String dateString = date.format(formatter);
		
		//initializing ExtentReports and creating the report file
		ExtentReports report = new ExtentReports();
		ExtentSparkReporter reportLocation = new ExtentSparkReporter("target/TestCaseReport "+dateString+".html");
		report.attachReporter(reportLocation);
		
		//Test cases ghvghvgh
		//TC_CannotReuseEmail.cre(report);
		//TC_UserAbleToLogin.ual(report);
		//TC_UnableToLoginWrongEmail.ulwe(report);
		//TC_UnableToLoginWrongPassword.ulwp(report);
		TC_UploadVideo.uv(report);
		//TC_UploadVideoWrongFileType.uvwf(report);
		
	}

}