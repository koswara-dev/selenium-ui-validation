package com.juaracoding.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getScreenshot(WebDriver driver, String pageName) throws IOException {
        // Your code to interact with the page and take screenshots
        // Take screenshot and save it as file or use as bytes
        TakesScreenshot ts = (TakesScreenshot) driver;
        //byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);

        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String destination = System.getProperty("user.dir")+"/screenshots/"+pageName+"_"+dateName+".png";
        File destFile = new File(destination);
        FileHandler.copy(srcFile, destFile);
        return destination;
    }

}
