package com.project.covid19.serviceImpl;

import com.project.covid19.Util.CrawlingUtil;
import com.project.covid19.constants.CrawlingConstants;
import com.project.covid19.entity.AreaCount;
import com.project.covid19.entity.CrawlingBoard;
import com.project.covid19.entity.Marker;
import com.project.covid19.generator.abstractClass.MarkerGenerator;
import com.project.covid19.repository.AreaCountRepository;
import com.project.covid19.repository.MarkerRepository;
import com.project.covid19.service.CrawlingService;
import lombok.extern.java.Log;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log
@Service
public class CrawlingServieImpl implements CrawlingService {

    @Autowired
    MarkerRepository repo;

    @Autowired
    AreaCountRepository countrepo;

    @Override
    public List<Marker> getMarkersList(String type) throws Exception {
        log.info("getMarkersList() : type - " + type);
        List<Marker> list = null;

        MarkerGenerator generator = CrawlingUtil.getMarkerGenerator(type);

        if(generator != null){
            AreaCount count = countrepo.findByAreaName(type);
            long webcount = CrawlingUtil.getCountFromWeb("https://www.seoul.go.kr/coronaV/coronaStatus.do", type);

            if(count == null || count.getCount() < webcount){
                if(count == null){
                    count = new AreaCount();
                    count.setAreaName(type);
                    count.setCount(webcount);
                    count.setLastUpdateDate(new Date());
                }else if(count != null){
                    count.setCount(webcount);
                    count.setLastUpdateDate(new Date());
                }

                ChromeDriver driver = CrawlingUtil.intSelenium();
                CrawlingUtil.seleniumConnectUrl(driver, "https://www.seoul.go.kr/coronaV/coronaStatus.do");
                CrawlingUtil.clickByClass(driver,"btn-tab2");
                CrawlingUtil.selectAera(driver,"select#route-searchArea", "select#route-searchArea>option[value="+type+"]");
                List<CrawlingBoard> data = CrawlingUtil.crawlingData(driver);
                CrawlingUtil.driverClose(driver);

                list = generator.getMarkers(data);

                if(list != null && list.size() > 0){
                    repo.deleteByAddrDepTwo(type);
                    repo.saveAll(list);
                }

            }else{
                list = repo.findByAddrDepTwo(type);
            }

            if(list == null || list.size() > 0){
                countrepo.save(count);
            }
        }

        return list;
    }
}
