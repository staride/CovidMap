package com.project.covid19.util;

import com.project.covid19.entity.Marker;

import com.project.covid19.entity.CrawlingBoard;

import lombok.extern.java.Log;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
//            options.addArguments("--headless");
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

    public static List<CrawlingBoard> crawlingData(ChromeDriver driver){
//        List<WebElement> pagenation = driver.findElementsByCssSelector("div#move-cont2>div#rsstableId>div.status-confirm>div#patients>div.cont-page-wrap>div#DataTables_Table_0_wrapper>div.dataTables_paginate>span>a");
//        ArrayList<CrawlingBoard> list = new ArrayList<CrawlingBoard>();
//
//        int pagelen = pagenation.size();
//        for(int k = 0;k<pagelen;k++){
//            try {
//                pagenation.get(k).click();
//            }catch (StaleElementReferenceException e){
//                pagenation = driver.findElementsByCssSelector("div#move-cont2>div#rsstableId>div.status-confirm>div#patients>div.cont-page-wrap>div#DataTables_Table_0_wrapper>div.dataTables_paginate>span>a");
//                pagenation.get(k).click();
//            }
//
//            List<WebElement> trs = driver.findElementsByCssSelector("table.route-datatable>tbody>tr");
//
//            int size = trs.size();
//
//            for(int i=0;i<size;i+=2){
//                List<WebElement> titletds = trs.get(i).findElements(new By.ByCssSelector("td"));
//                WebElement datatd = trs.get(i+1).findElement(new By.ByCssSelector("td"));
//
//                String confirmDate = titletds.get(3).getText();
//                String type = titletds.get(4).getText();
//                String status = titletds.get(5).findElement(new By.ByCssSelector("strong>span")).getText();
//                String data = "";
//
//                List<WebElement> tabletrs = datatd.findElements(new By.ByCssSelector("table>tbody>tr"));
//
//                if(tabletrs.size() == 0){
//                    List<WebElement> ps = datatd.findElements(new By.ByCssSelector("p"));
//                    int psize = ps.size();
//                    for(int z=0;z<psize;z++){
//                        String pdata = ps.get(z).getText();
//
//                        if(!pdata.contains("동선 없음") && !pdata.equals("확인중") && !pdata.equals("확인 중")) {
//                            data += CrawlingConstants.CRAW_DATA_SEPARATOR + pdata;
//
//                            if (z != psize - 1) {
//                                data += " ";
//                            }
//                        }
//                    }
//                }else{
//                    int tabletrssize = tabletrs.size();
//                    for(int j=0;j<tabletrssize;j++){
//                        WebElement tabletr = tabletrs.get(j);
//                        List<WebElement> tabletds = tabletr.findElements(new By.ByCssSelector("td"));
//
//                        if(!tabletds.get(2).getText().contains("비공")){
//                            data += tabletds.get(2).getText() + CrawlingConstants.CRAW_DATA_SEPARATOR + tabletds.get(3).getText() + CrawlingConstants.CRAW_DATA_SEPARATOR + tabletds.get(4).getText();
//                            if(j != tabletrssize-1){
//                                data += CrawlingConstants.CRAW_DATA_RAW_SEPARATOR;
//                            }
//                        }
//                    }
//                }
//
//                data = data.replaceAll("\\n", CrawlingConstants.CRAW_DATA_LINE_SEPARATOR);
//
//                if(!status.equals("퇴원") && !status.equals("사망") && (Util.isContainsDate(data) || Util.isContainsHour(data))){
//                    CrawlingBoard board = new CrawlingBoard();
//
//                    board.setConfirmDate(confirmDate);
//                    board.setType(type);
//                    board.setStatus(status);
//                    board.setData(data);
//
//                    list.add(board);
//                }
//            }
//        }

//        return list;
        return null;
    }

    public static void driverClose (ChromeDriver driver){
        if(driver != null){
            driver.close();
        }
    }

    public static boolean isDuplicationMarker(ArrayList<Marker> list, String locationName){
        return list.parallelStream().filter(m -> m.getLocationName().equals(locationName)).findFirst().orElse(null) != null;
    }
}
