package com.project.covid19.crawler;

import com.project.covid19.Util.CrawlingUtil;
import com.project.covid19.entity.Marker;
import lombok.extern.java.Log;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

@Log
public class SeongdongCrawler extends Crawler{
    public SeongdongCrawler(String type) {
        super(type);
    }

    @Override
    public List<Marker> start() {
        ArrayList<Marker> list = new ArrayList<Marker>();

        for(String url : urls){
            Document doc = CrawlingUtil.connectUrl(url);
            log.info(doc+"");
        }

        return list;
    }
}
