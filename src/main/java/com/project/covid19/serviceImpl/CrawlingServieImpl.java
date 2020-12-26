package com.project.covid19.serviceImpl;

import com.project.covid19.entity.CovidStatus;
import com.project.covid19.util.CrawlingUtil;
import com.project.covid19.entity.Marker;
import com.project.covid19.service.CrawlingService;
import com.project.covid19.util.KakaoUtil;
import lombok.extern.java.Log;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log
@Service
public class CrawlingServieImpl implements CrawlingService {

    private String acc = "p.number";
    private String diff = "p.diff";

    @Override
    public List<Marker> getMarkersList(){
        ArrayList<Marker> result = null;
        ChromeDriver driver = null;

        try{
            result = new ArrayList<Marker>();
            driver = CrawlingUtil.initSelenium();
            CrawlingUtil.seleniumConnectUrl(driver, "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=12&ncvContSeq=&contSeq=&board_id=&gubun=");

            List<WebElement> trs = driver.findElementsByCssSelector("div.data_table>table>tbody>tr");
            for(WebElement tr : trs){
                List<WebElement> tds = tr.findElements(By.cssSelector("td"));

                String data = tds.get(2).getText();

                if(!data.contains("소재")){
                    String address = data.substring(data.indexOf("(")+1, data.lastIndexOf(")"));
                    if(address.matches(".*\\d.*")){
                        Marker marker = new Marker();

                        marker.setAddressSecond(tds.get(0).getText());
                        marker.setLocationName(data.substring(0, data.indexOf("(")).trim());
                        marker.setAddress(address);
                        marker.setConfirmDate(tds.get(3).getText());
                        marker.setDisinfect(tds.get(4).getText());

                        KakaoUtil.searchUseAddress(marker);

                        if(marker.getPositionX() != null && marker.getPositionY() != null){
                            result.add(marker);
                        }
                    }
                }
            }

        }catch (Exception e){
            log.info(e.getMessage());
            e.printStackTrace();
            result = null;
        }finally {
            CrawlingUtil.driverClose(driver);
        }

        return result;
    }

    @Override
    public CovidStatus getCovidStatus(){

        ChromeDriver driver = null;
        CovidStatus status = null;

        try{
            driver = CrawlingUtil.initSelenium();

            CrawlingUtil.seleniumConnectUrl(driver, "https://coronaboard.kr/");
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

            WebElement board = driver.findElementByCssSelector("div.domestic");

            List<WebElement> datas = board.findElements(By.cssSelector("div"));

            status = new CovidStatus();

            status.setTotalAcc(datas.get(0).findElement(By.cssSelector(acc)).getText());
            status.setTotalDiff(datas.get(0).findElement(By.cssSelector(diff)).getText());

            status.setDeathAcc(datas.get(1).findElement(By.cssSelector(acc)).getText());
            status.setDeathDiff(datas.get(1).findElement(By.cssSelector(diff)).getText());

            status.setFreeAcc(datas.get(2).findElement(By.cssSelector(acc)).getText());
            status.setFreeDiff(datas.get(2).findElement(By.cssSelector(diff)).getText());

            status.setFatalityRate(datas.get(3).findElement(By.cssSelector(acc)).getText());

            status.setTotalInspectionAcc(datas.get(4).findElement(By.cssSelector(acc)).getText());
            status.setTotalInspectionDiff(datas.get(4).findElement(By.cssSelector(diff)).getText());

            status.setInspectionAcc(datas.get(5).findElement(By.cssSelector(acc)).getText());
            status.setInspectionDiff(datas.get(5).findElement(By.cssSelector(diff)).getText());

            status.setTotalNegativeAcc(datas.get(6).findElement(By.cssSelector(acc)).getText());
            status.setTotalNegativeDiff(datas.get(6).findElement(By.cssSelector(diff)).getText());

        }catch (Exception e){
            log.info(e.getMessage());
            status = null;
        }finally {
            CrawlingUtil.driverClose(driver);
        }

        return status;
    }
}
