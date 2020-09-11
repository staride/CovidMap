package com.project.covid19.generator;

import com.project.covid19.Util.CrawlingUtil;
import com.project.covid19.Util.KakaoUtil;
import com.project.covid19.Util.Util;
import com.project.covid19.constants.CrawlingConstants;
import com.project.covid19.entity.CrawlingBoard;
import com.project.covid19.entity.Marker;
import com.project.covid19.generator.abstractClass.MarkerGenerator;

import java.util.ArrayList;
import java.util.List;

public class SeoDaeMunGenerator extends MarkerGenerator {
    public SeoDaeMunGenerator(String type) {
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

                String addrDepThree = null;

                if (locationName.contains("(") && locationName.contains(")")) {
                    addrDepThree = locationName.substring(locationName.indexOf("(") + 1, locationName.indexOf(")"));
                    locationName = locationName.substring(0, locationName.indexOf("("));
                }

                if (!CrawlingUtil.isDuplicationMarker(result, locationName)) {

                    Marker marker = new Marker();

                    if (addrDepThree != null) {
                        marker.setLocationName(locationName);
                        marker.setConfirmDate(board.getConfirmDate());
                        marker.setAddress("서울시 " + type + " " + addrDepThree);
                        marker.setAddrDepOne("서울시");
                        marker.setAddrDepTwo(type);
                        marker.setAddrDepThree(addrDepThree);
                        marker.setDescription(details[1] + " " + details[2]);
                        KakaoUtil.searchUseAddress(marker);
                    } else {
                        marker.setLocationName(type + locationName);
                        marker.setConfirmDate(board.getConfirmDate());
                        marker.setDescription(details[1] + " " + details[2]);
                        KakaoUtil.searchUsePlace(marker);
                        marker.setLocationName(locationName);
                    }

                    if(!Util.isEmptyString(marker.getPositionX()) && !Util.isEmptyString(marker.getPositionY())){
                        result.add(marker);
                    }
                }
            }
        }
        return result;
    }
}
