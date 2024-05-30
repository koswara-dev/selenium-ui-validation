package com.juaracoding;

import com.juaracoding.utils.OpenAiVision;
import com.juaracoding.utils.Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MainApp {
    public static void main(String[] args) throws Exception {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Setting headless mode
        options.addArguments("--disable-gpu");  // GPU hardware acceleration isn't useful in headless mode
        options.addArguments("--window-size=1366,768");  // Set the window size
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://dev.ptdika.com/staging.sociola/login");

        // ImageToText
        String actualImage = Utils.getScreenshot(driver, "loginpage");
        String responseText = OpenAiVision.extractTextFromImage(actualImage);
        System.out.println("Extracted Text: " + responseText);

        // ImageCompare
        String expectedImage = "src/main/resources/expectedImages/dikaloginpage_false.png";
        String response = OpenAiVision.compareImages(expectedImage, actualImage);
        System.out.println("Response: " + response);

        // Assertion
        boolean isSameImage = Boolean.parseBoolean(OpenAiVision.isSameImage(response));
        String testResult = isSameImage ? "Passes" : "Failed";
        double similarity = Double.parseDouble(OpenAiVision.similarity(response));
        System.out.printf("Test Result: %s with similarity: %.2f%%", testResult, similarity);

        delay(3);
        driver.quit();
    }

    public static void delay(long second) {
        try {
            Thread.sleep(1000*second);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}