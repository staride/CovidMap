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
public class DongJakGenerator extends MarkerGenerator {

    public DongJakGenerator(String type) {
        super(type);
    }

    @Override
    public List<Marker> getMarkers(List<CrawlingBoard> list) throws Exception {
        ArrayList<Marker> result = new ArrayList<Marker>();

        for(CrawlingBoard board : list) {
            String path = board.getData();
            String[] datas = path.split("(([1-9]|1[0-2])월([ ])?([0-9]|1[0-9]|2[0-9]|3[0-1])+일)");

            for(String data : datas){
                if(!Util.isEmptyString(data) && !data.equals(";")){
                    data = data.substring(data.indexOf(")") + 1, data.length());
                    String[] subdatas = data.split(CrawlingConstants.CRAW_DATA_SEPARATOR);

                    for(String subdata : subdatas){
                        if(!Util.isEmptyString(subdata.trim()) && !subdata.contains("타지자체") && !subdata.contains("소재")
                           && !subdata.contains("비공개")&& !subdata.contains("자택") && Util.isContainsHour(subdata)
                           && !subdata.contains("보건소") && !Pattern.matches("^.*(주소.*([동]+)).*$", subdata)
                           && !subdata.contains("타자지체") && !subdata.contains("자녀집") && !subdata.contains("친척댁")
                           && !subdata.contains("자 택") && !subdata.contains("직장") && !Pattern.matches("^[-]?[ ]?동작구.*→[^ ].*$", subdata)
                           && !subdata.contains("타시도") && !subdata.contains("확인중") && !Pattern.matches("^.*([→]+.*[동]+).*", subdata)){
                            String[] subdatadetail = subdata.split("((1[0-9]|2[0-3])([:])+([0-5][0-9])+[~]+(1[0-9]|2[0-3])([:])+([0-5][0-9]))+");
                            String locationName = subdatadetail[0].replaceAll(type, "").trim();

                            if(!CrawlingUtil.isDuplicationMarker(result, locationName)){
                                Marker marker = new Marker();
                                marker.setLocationName(locationName);
                                marker.setConfirmDate(board.getConfirmDate());
                                marker.setDescription("");
                                KakaoUtil.searchUsePlace(marker);

                                if(!Util.isEmptyString(marker.getPositionX()) && !Util.isEmptyString(marker.getPositionY())){
                                    result.add(marker);
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
