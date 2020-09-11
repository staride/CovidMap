package com.project.covid19.generator;

import com.project.covid19.Util.CrawlingUtil;
import com.project.covid19.Util.KakaoUtil;
import com.project.covid19.Util.Util;
import com.project.covid19.constants.CrawlingConstants;
import com.project.covid19.entity.CrawlingBoard;
import com.project.covid19.entity.Marker;
import com.project.covid19.generator.abstractClass.MarkerGenerator;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;


public class GangBukGenerator extends MarkerGenerator {
    public GangBukGenerator(String type) {
        super(type);
    }

    @Override
    public List<Marker> getMarkers(List<CrawlingBoard> list) throws Exception {

        ArrayList<Marker> result = new ArrayList<Marker>();

        for(CrawlingBoard board : list){
            String[] datas = board.getData().split(CrawlingConstants.CRAW_DATA_RAW_SEPARATOR);
            for(String data : datas){
                String[] details = data.split(CrawlingConstants.CRAW_DATA_SEPARATOR);
                String locationName = details[0];

                if(!CrawlingUtil.isDuplicationMarker(result, locationName)){
                    Marker marker = new Marker();
                    marker.setLocationName(locationName);
                    marker.setDescription(details[1] + " " + details[2]);
                    KakaoUtil.searchUsePlace(marker);

                    if(!Util.isEmptyString(marker.getPositionX()) && !Util.isEmptyString(marker.getPositionY())){
                        result.add(marker);
                    }
                }
            }
        }

        return result;
    }
}
