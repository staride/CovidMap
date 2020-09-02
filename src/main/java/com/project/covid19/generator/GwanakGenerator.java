package com.project.covid19.generator;

import com.project.covid19.entity.CrawlingBoard;
import com.project.covid19.entity.Marker;
import com.project.covid19.generator.abstractClass.MarkerGenerator;

import java.util.ArrayList;
import java.util.List;

public class GwanakGenerator extends MarkerGenerator {
    public GwanakGenerator(String type) {
        super(type);
    }

    @Override
    public List<Marker> getMarkers(List<CrawlingBoard> list) throws Exception {

        ArrayList<Marker> result = new ArrayList<Marker>();



        return result;
    }
}
