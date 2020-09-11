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

public class YongSanGenerator extends MarkerGenerator {
    public YongSanGenerator(String type) {
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

                if(!Util.isEmptyString(locationName) && !locationName.contains("자택") && !locationName.contains("타구") && !locationName.contains("진료소")
                   && !locationName.contains("타시도")&& !locationName.equals("미공개") && !locationName.contains("인근") && !locationName.contains("방문")){
                    if(!CrawlingUtil.isDuplicationMarker(result, locationName)){
                        Marker marker = new Marker();
                        marker.setLocationName(locationName);
                        marker.setConfirmDate(board.getConfirmDate());

                        if(details.length == 3){
                            marker.setDescription(details[1] + " " + details[2]);
                        }else{
                            marker.setDescription(details[1]);
                        }
                        KakaoUtil.searchUsePlace(marker);

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
