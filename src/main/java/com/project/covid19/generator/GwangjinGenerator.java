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
public class GwangjinGenerator extends MarkerGenerator {
    public GwangjinGenerator(String type) {
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
                if(!Util.isEmptyString(locationName) && !locationName.contains("자택") && !locationName.contains("보건소") && !locationName.contains("*") &&
                  !details[1].contains("자가격리") && !details[0].equals("-") && !details[0].contains("ATM")
                  && !details[0].contains("타구")&& !details[0].equals("식당") && !details[0].equals("집") && !details[0].equals("직장")){

                    String addrDepThree = null;

                    if(locationName.contains("(") && locationName.contains(")")){
                        addrDepThree = locationName.substring(locationName.indexOf("(") + 1, locationName.indexOf(")"));
                        locationName = locationName.substring(0, locationName.indexOf("("));
                    }

                    if(!CrawlingUtil.isDuplicationMarker(result, locationName)){

                        Marker marker = new Marker();

                        if(addrDepThree != null){
                            marker.setLocationName(locationName);
                            marker.setAddress("서울시 " + this.type + " " + addrDepThree);
                            marker.setAddrDepOne("서울시");
                            marker.setAddrDepTwo(this.type);
                            marker.setAddrDepThree(addrDepThree);
                            KakaoUtil.searchUseAddress(marker);
                        }else{
                            marker.setLocationName(locationName);
                            KakaoUtil.searchUsePlace(marker);
                        }

                        marker.setConfirmDate(board.getConfirmDate());
                        marker.setDescription(details[1] + " " + details[2]);

                        if(!Util.isEmptyString(marker.getPositionX()) && !Util.isEmptyString(marker.getPositionY())){
                            result.add(marker);
                        }
                    }
                }
            }
        }

        return result;
    }
}
