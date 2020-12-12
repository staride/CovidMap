package com.project.covid19.util;

import lombok.extern.java.Log;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.nio.file.Paths;

@Log
public class CrawlingUtil {

    public static Document connectUrl(String url) {
        log.info("connectUrl(): " + url);
        Document document = null;

        try {
            Connection.Response homepage = Jsoup.connect(url).timeout(1000 * 60 * 60).method(Connection.Method.GET)
                    .userAgent("Mozila/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20100101 " +
                            "Firefox/10.0 AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/51.0.2704.103 Safari/537.36")
                    .execute();



             document = homepage.parse();
//            document = Jsoup.connect(url).timeout(1000 * 60 * 60).get();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return document;
    }

    public static ChromeDriver initSelenium(){
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/driver/chromedriver");

            System.setProperty("webdriver.chrome.driver", path.toString());

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-default-apps");
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");


            return new ChromeDriver(options);
    }

    public static void seleniumConnectUrl(ChromeDriver driver, String url){
        driver.get(url);
    }

    public static void clickByClass(ChromeDriver driver, String className){
        driver.findElementByClassName(className).click();
    }

    public static void selectAera(ChromeDriver driver, String selectSelector, String optionSelector){
        driver.findElementByCssSelector(selectSelector).click();
        driver.findElementByCssSelector(optionSelector).click();
        driver.findElementByCssSelector("button#route-searchButton").click();
    }

    public static void driverClose (ChromeDriver driver){
        if(driver != null){
            driver.close();
        }
    }
}
