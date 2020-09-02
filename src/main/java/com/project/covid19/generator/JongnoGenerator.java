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

@Log
public class JongnoGenerator extends MarkerGenerator {

    public JongnoGenerator(String type) {
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

                if(!Util.isEmptyString(locationName) && !locationName.contains("동선 공개하지 않음")){



                    if(!CrawlingUtil.isDuplicationMarker(result, locationName)){

                        Marker marker = new Marker();
                        marker.setConfirmDate(board.getConfirmDate());
                        marker.setLocationName(locationName);

                        if(locationName.contains("(") && locationName.contains(")")){
                            String addrDepThree = locationName.substring(locationName.indexOf("(") + 1, locationName.indexOf(")"));
                            locationName = locationName.substring(0, locationName.indexOf("("));

                            if(addrDepThree != null && !addrDepThree.contains("지점")){
                                marker.setAddress("서울시 " + type + " " +addrDepThree);
                                marker.setAddrDepOne("서울시");
                                marker.setAddrDepTwo(type);
                                marker.setAddrDepThree(addrDepThree);
                                KakaoUtil.searchUseAddress(marker);
                            }else{
                                KakaoUtil.searchUsePlace(marker);
                            }
                        }

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
