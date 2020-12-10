package com.project.covid19.generator;

import com.project.covid19.util.CrawlingUtil;
import com.project.covid19.util.KakaoUtil;
import com.project.covid19.util.Util;
import com.project.covid19.constants.CrawlingConstants;
import com.project.covid19.entity.CrawlingBoard;
import com.project.covid19.entity.Marker;
import com.project.covid19.generator.abstractClass.MarkerGenerator;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Log
public class DongDaeMunGenerator extends MarkerGenerator {
    public DongDaeMunGenerator(String type) {
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

                if(!Util.isEmptyString(locationName) && !locationName.contains("타 구") && !locationName.contains("보건소") && !locationName.contains("자택")
                   && !locationName.contains("자택") && !locationName.contains("비공개") && !locationName.contains("카페")
                   && !locationName.contains("음식점") && !locationName.contains("타 시도") && !locationName.contains("오피스텔")
                   && !locationName.contains("배봉산") && !locationName.contains("역학조사") && !locationName.contains("체육동호회")
                   && !locationName.contains("약국") && !locationName.contains("금융기관") && !locationName.contains("선별진료소")
                   && !locationName.contains("직장") && !locationName.contains("마트") && !locationName.contains("도서관")
                   && !locationName.equals("-")){

                    if(locationName.contains("지하철 1호선") && locationName.contains("(") && locationName.contains(")")){
                        locationName = locationName.substring(locationName.indexOf("(") + 1, locationName.indexOf(")"));
                    }

                    if(locationName.contains("→")){
                        String[] locationNames = locationName.split("→");
                        for(String name : locationNames){
                            if(!CrawlingUtil.isDuplicationMarker(result, name)){
                                Marker marker = new Marker();
                                marker.setLocationName(name);
                                marker.setConfirmDate(board.getConfirmDate());
                                marker.setDescription(details[1]);
                                KakaoUtil.searchUsePlace(marker);
                                if(!Util.isEmptyString(marker.getPositionX()) && !Util.isEmptyString(marker.getPositionY())){
                                    result.add(marker);
                                }
                            }
                        }
                    }else{
                        if(!CrawlingUtil.isDuplicationMarker(result, locationName)){
                            Marker marker = new Marker();
                            marker.setLocationName(locationName);
                            marker.setConfirmDate(board.getConfirmDate());
                            marker.setDescription(details[1]);
                            KakaoUtil.searchUsePlace(marker);
                            if(!Util.isEmptyString(marker.getPositionX()) && !Util.isEmptyString(marker.getPositionY())){
                                result.add(marker);
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
}
