package com.project.covid19.generator;

import com.project.covid19.Util.Util;
import com.project.covid19.constants.CrawlingConstants;
import com.project.covid19.entity.CrawlingBoard;
import com.project.covid19.entity.Marker;
import com.project.covid19.generator.abstractClass.MarkerGenerator;

import java.util.ArrayList;
import java.util.List;

public class EunpyeongGenerator extends MarkerGenerator {
    public EunpyeongGenerator(String type) {
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

                if(!Util.isEmptyString(locationName) && !locationName.equals("해당 지역에서 확인 후 공개") && !locationName.contains("**")
                   && !locationName.equals("(접촉자 분류 완료)")){
//                    Marker marker = new Marker();
//                    marker.setLocationName(locationName);
//                    result.add(marker);
                    // 추후 구현 필요
                }
            }
        }

        return result;
    }
}
