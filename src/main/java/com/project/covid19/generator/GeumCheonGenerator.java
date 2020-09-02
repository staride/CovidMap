package com.project.covid19.generator;

import com.project.covid19.entity.CrawlingBoard;
import com.project.covid19.entity.Marker;
import com.project.covid19.generator.abstractClass.MarkerGenerator;

import java.util.List;

public class GeumCheonGenerator extends MarkerGenerator {
    public GeumCheonGenerator(String type) {
        super(type);
    }

    @Override
    public List<Marker> getMarkers(List<CrawlingBoard> list) throws Exception {
        return null;
    }
}
