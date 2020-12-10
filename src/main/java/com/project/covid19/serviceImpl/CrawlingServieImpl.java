package com.project.covid19.serviceImpl;

import com.project.covid19.entity.CovidStatus;
import com.project.covid19.util.CrawlingUtil;
import com.project.covid19.entity.AreaCount;
import com.project.covid19.entity.CrawlingBoard;
import com.project.covid19.entity.Marker;
import com.project.covid19.repository.AreaCountRepository;
import com.project.covid19.repository.MarkerRepository;
import com.project.covid19.service.CrawlingService;
import lombok.extern.java.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log
@Service
public class CrawlingServieImpl implements CrawlingService {

    @Autowired
    MarkerRepository repo;

    @Autowired
    AreaCountRepository countrepo;

    private String acc = "p.number";
    private String diff = "p.diff";

    @Override
    public List<Marker> getMarkersList(String type){
//        log.info("getMarkersList() : type - " + type);
//        List<Marker> list = null;
//
//        MarkerGenerator generator = CrawlingUtil.getMarkerGenerator(type);
//
//        if(generator != null){
//            AreaCount count = countrepo.findByAreaName(type);
//            long webcount = CrawlingUtil.getCountFromWeb("https://www.seoul.go.kr/coronaV/coronaStatus.do", type);
//
//            if(count == null || count.getCount() < webcount){
//                if(count == null){
//                    count = new AreaCount();
//                    count.setAreaName(type);
//                    count.setCount(webcount);
//                    count.setLastUpdateDate(new Date());
//                }else if(count != null){
//                    count.setCount(webcount);
//                    count.setLastUpdateDate(new Date());
//                }
//
//                ChromeDriver driver = CrawlingUtil.intSelenium();
//                CrawlingUtil.seleniumConnectUrl(driver, "https://www.seoul.go.kr/coronaV/coronaStatus.do");
////                CrawlingUtil.clickByClass(driver,"btn-tab2");
//                CrawlingUtil.selectAera(driver,"select#status-searchArea", "select#status-searchArea>option[value="+type+"]");
//                List<CrawlingBoard> data = CrawlingUtil.crawlingData(driver);
//                CrawlingUtil.driverClose(driver);
//
//                list = generator.getMarkers(data);
//
//                if(list != null && list.size() > 0){
//                    repo.deleteByAddrDepTwo(type);
//                    repo.saveAll(list);
//                }
//
//            }else{
//                list = repo.findByAddrDepTwo(type);
//            }
//
//            if(list == null || list.size() > 0){
//                countrepo.save(count);
//            }
//        }
//
//        return list;
        return null;
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
