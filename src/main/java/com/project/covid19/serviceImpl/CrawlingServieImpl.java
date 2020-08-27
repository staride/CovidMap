package com.project.covid19.serviceImpl;

import com.project.covid19.Util.CrawlingUtil;
import com.project.covid19.crawler.Crawler;
import com.project.covid19.entity.Marker;
import com.project.covid19.repository.CrawlingRepository;
import com.project.covid19.service.CrawlingService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service
public class CrawlingServieImpl implements CrawlingService {

    @Autowired
    CrawlingRepository repo;

    @Override
    public List<Marker> getMarkersList(String type) {
        log.info("getMarkersList() : type - " + type);
        List<Marker> list = null;

        // 조건 추가 필요
        Crawler crawler = CrawlingUtil.getCawler(type);
        if(crawler != null){
            list = crawler.start();
            repo.deleteByAddrDepTwo(type);
            repo.saveAll(list);
            // list = repo.findByAddrDepTwo(type);
        }else{
            log.info("Crawler in null. type : " + type);
        }

        return list;
    }
}
