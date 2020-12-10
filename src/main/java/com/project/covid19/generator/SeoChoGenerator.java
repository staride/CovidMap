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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class SeoChoGenerator extends MarkerGenerator {

    public SeoChoGenerator(String type) {
        super(type);
    }

    @Override
    public List<Marker> getMarkers(List<CrawlingBoard> list) throws Exception {

        ArrayList<Marker> result = new ArrayList<>();

        for(CrawlingBoard board : list){
            String[] datas = board.getData().split(CrawlingConstants.CRAW_DATA_SEPARATOR);
            int len = datas.length;
            for(int i=0;i<len;i++){
                String data = datas[i];
                if(!Util.isEmptyString(data) && Pattern.matches("^.*(\\D*[(]+.{2,10}[)]+).*$", data) && !data.contains("추정")
                   && !data.contains("접촉자") && !data.contains("접촉자") && !data.contains("확진") && !data.contains("자택")
                   && !data.contains("사무실")){
                    Matcher matcher = Pattern.compile("(\\D*[(]+.{2,10}[)]+)").matcher(data);
                    String locationName = null;
                    if(matcher.find()){
                        locationName = matcher.group(0);
                    }

                    String addrDepThree = locationName.substring(locationName.indexOf("(") + 1, locationName.indexOf(")"));
                    locationName = locationName.substring(0, locationName.indexOf("("));
                    locationName = locationName.replaceAll("ｏ", "");

                    if(locationName != null && !CrawlingUtil.isDuplicationMarker(result, locationName)){

                        Marker marker = new Marker();
                        marker.setConfirmDate(board.getConfirmDate());
                        marker.setLocationName(locationName);
                        marker.setAddrDepOne("서울시");
                        marker.setAddrDepTwo(type);
                        marker.setAddrDepThree(addrDepThree);
                        marker.setAddress("서울시 " + type + " " + addrDepThree);
                        if(i != len-1 && !datas[i+1].equals("<등록 : 서초구청>")){
                            marker.setDescription(datas[i+1]);
                        }

                        KakaoUtil.searchUseAddress(marker);
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
