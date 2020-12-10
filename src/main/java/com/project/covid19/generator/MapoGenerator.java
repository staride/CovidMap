package com.project.covid19.generator;

import com.project.covid19.util.Util;
import com.project.covid19.constants.CrawlingConstants;
import com.project.covid19.entity.CrawlingBoard;
import com.project.covid19.entity.Marker;
import com.project.covid19.generator.abstractClass.MarkerGenerator;

import java.util.ArrayList;
import java.util.List;

public class MapoGenerator extends MarkerGenerator {
    public MapoGenerator(String type) {
        super(type);
    }

    @Override
    public List<Marker> getMarkers(List<CrawlingBoard> list) throws Exception {
        ArrayList<Marker> result = new ArrayList<Marker>();

        for(CrawlingBoard board : list) {
            String[] datas = board.getData().split(CrawlingConstants.CRAW_DATA_RAW_SEPARATOR);
            for (String data : datas) {
                String[] details = data.split(CrawlingConstants.CRAW_DATA_SEPARATOR);
                String locationName = details[0];

                if(!Util.isEmptyString(locationName) && !locationName.contains("비공개") && !locationName.equals("희망병원")
                   && !locationName.contains("보건소") && !locationName.contains("자택") && !locationName.contains("타지역")
                   && !locationName.contains("강북삼성병원") && !locationName.contains("세브란스병원")){
                    // 추후 구현 필요
                    Marker marker = new Marker();
                    marker.setLocationName(locationName);
                }
            }
        }


        return result;
    }
}
