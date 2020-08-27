package com.project.covid19.crawler;

import com.project.covid19.Util.CrawlingUtil;
import com.project.covid19.entity.Marker;

import java.util.List;

public abstract class Crawler {
    protected String[] urls;
    protected String type;

    public Crawler(String type){
        this.urls = CrawlingUtil.getUrl(type);
        this.type = type;
    }

    public abstract List<Marker> start();
}
