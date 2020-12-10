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
import java.util.regex.Pattern;

@Log
public class GangDongGenerator extends MarkerGenerator {
    public GangDongGenerator(String type) {
        super(type);
    }

    @Override
    public List<Marker> getMarkers(List<CrawlingBoard> list) throws Exception {

        ArrayList<Marker> result = new ArrayList<>();
        int size = list.size();

        if(size > 0){

            for(CrawlingBoard board : list){
                String path = board.getData();

                String[] datas = path.split("(([1-9]|1[0-2])([.])+([ ])?([0-9]|1[0-9]|2[0-9]|3[0-1])+)");
                for(String data : datas){
                    if(!data.contains("자택") && Pattern.matches(".*((1[0-9]|2[0-3])([:])+([0-5][0-9])+[ ~ ]+(1[0-9]|2[0-3])([:])+([0-5][0-9]))+.*", data)){
                        // log.info("data : " + data);
//                        Matcher matcher = Pattern.compile("(([1-9]|1[0-2])([.])+([ ])?([0-9]|1[0-9]|2[0-9]|3[0-1])+)").matcher(data);
//
//                        if(matcher.find()){
//                            date = matcher.group(0);
//                        }

                        // log.info("after data : " + data);

                        String[] subdatas = data.split(CrawlingConstants.CRAW_DATA_SEPARATOR);

                        for(String subdata : subdatas){
                            if(!subdata.contains("공개 예정")){
                                subdata = subdata.replaceAll("((1[0-9]|2[0-3])([:])+([0-5][0-9])+[ ~ ]+(1[0-9]|2[0-3])([:])+([0-5][0-9]))+", "");

                                if(subdata.contains("→") && subdata.contains("(") ){
                                    int index = subdata.indexOf("(");
                                    String from =subdata.substring(0, index).trim();
                                    int start = (subdata.indexOf("→")+2);
                                    int end = subdata.indexOf("(", index + 1);
                                    String to = subdata.substring(start, end);

                                    if(!CrawlingUtil.isDuplicationMarker(result, from)){
                                        Marker marker = new Marker();
                                        marker.setConfirmDate(board.getConfirmDate());
                                        marker.setLocationName(from);
                                        KakaoUtil.searchUsePlace(marker);
                                        if(!Util.isEmptyString(marker.getPositionX()) && !Util.isEmptyString(marker.getPositionY())){
                                            result.add(marker);
                                        }
                                    }

                                    if(!CrawlingUtil.isDuplicationMarker(result, to)){
                                        Marker marker2 = new Marker();
                                        marker2.setConfirmDate(board.getConfirmDate());
                                        marker2.setLocationName(to);
                                        KakaoUtil.searchUsePlace(marker2);
                                        if(!Util.isEmptyString(marker2.getPositionX()) && !Util.isEmptyString(marker2.getPositionY())){
                                            result.add(marker2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
