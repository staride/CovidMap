package com.project.covid19.generator.abstractClass;

import com.project.covid19.entity.CrawlingBoard;
import com.project.covid19.entity.Marker;

import java.util.List;

public abstract class MarkerGenerator {
    protected String type;

    public MarkerGenerator(String type){
        this.type = type;
    }

    public abstract List<Marker> getMarkers(List<CrawlingBoard> list) throws Exception;
}
