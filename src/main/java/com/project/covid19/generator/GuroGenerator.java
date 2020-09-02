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

public class GuroGenerator extends MarkerGenerator {
    public GuroGenerator(String type) {
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
                if(!Util.isEmptyString(locationName) && !locationName.equals("공공기관") && !locationName.equals("병원") && !locationName.equals("약국")
                   && !locationName.contains("타지역") && !locationName.contains("타구") && !locationName.contains("타 구")
                   && !locationName.equals("마트") && !locationName.equals("음식점") && !locationName.equals("카페")
                   && !locationName.equals("약국") && !locationName.equals("은행") && !locationName.equals("편의점")
                   && !locationName.equals("교회") && !locationName.equals("비비팜") && !locationName.equals("바바펌")
                   && !locationName.equals("빵집") && !locationName.equals("상점") && !locationName.contains("직장")
                   && !locationName.equals("정육점") && !locationName.contains("놀이터") && !locationName.equals("어린이집")
                   && !locationName.contains("버스운행") && !locationName.equals("식당")){

                    if(locationName.equals("영등포구 큰권능교")){
                        locationName += "회";
                    }

                    if(locationName.contains("‧")){
                        locationName = locationName.substring(0, locationName.indexOf("‧")) + ")";
                    }

                    String addrDepThree = null;

                    if(locationName.contains("(") && locationName.contains(")")){
                        addrDepThree = locationName.substring(locationName.indexOf("(") + 1, locationName.indexOf(")"));
                        locationName = locationName.substring(0, locationName.indexOf("("));
                    }

                    if(!CrawlingUtil.isDuplicationMarker(result, locationName)){

                        Marker marker = new Marker();
                        marker.setLocationName(locationName);

                        if(addrDepThree != null){
                            marker.setAddress("서울시 " + this.type + " " + addrDepThree);
                            marker.setAddrDepOne("서울시");
                            marker.setAddrDepTwo(this.type);
                            marker.setAddrDepThree(addrDepThree);
                            KakaoUtil.searchUseAddress(marker);
                        }else{
                            KakaoUtil.searchUsePlace(marker);
                        }

                        marker.setConfirmDate(board.getConfirmDate());
                        if(details.length == 3){
                            marker.setDescription(details[1] + " " + details[2]);
                        }else{
                            marker.setDescription(details[1]);
                        }

                        if(marker.getAddress() != null){
                            result.add(marker);
                        }
                    }
                }
            }
        }

        return result;
    }
}
