package com.project.covid19.crawler;

import com.project.covid19.Util.CrawlingUtil;
import com.project.covid19.constants.CrawTypeConstants;
import com.project.covid19.entity.Marker;
import lombok.extern.java.Log;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Log
public class JungCrawler extends Crawler{
    public JungCrawler(String type) {
        super(type);
    }

    // 구현 미완성
    @Override
    public List<Marker> start() {

        ArrayList<Marker> list = new ArrayList<Marker>();

        for (String url : this.urls) {
            Document pagedoc = CrawlingUtil.connectUrl(url);

            Elements pageel = pagedoc.select("ul.pagination>li.paginate_button>a");
            int len = pageel.size();

            for(int i=1;i<=len;i++){
                Document datadoc = CrawlingUtil.connectUrl(url + "&page=" + i);

                Elements datas = datadoc.select("dd.welfare_bottom");
                int datalen = datas.size();

                for(int j=0;j<datalen;j++){
                    Element data_el = datas.get(j);
                    String text = data_el.text();

                    if(!text.contains("없습니다")){
                        Elements m_datas = data_el.select("div.sub_table1>table>tbody>tr");
                        int m_datalen = m_datas.size();

                        for(int k=0;k<m_datalen;k++){
                            Element m_data = m_datas.get(k);
                            Elements childs = m_data.select("td");
                            if(childs.size() == 5){
                                String loc_info = childs.get(2).text();
                                if(!loc_info.contains("비공개")){
                                    String addrDepThree = loc_info.replaceAll("<br>", "").trim();
                                    String locationName = null;

                                    if(loc_info.contains("(")){
                                        locationName = loc_info.substring(0, loc_info.indexOf("(") - 1);
                                    }else{
                                        break;
                                    }

                                    String des_str = childs.get(3).text();
                                    String confirmdate =  des_str.replaceAll("<br>", "").trim();
                                    confirmdate = confirmdate.substring(confirmdate.indexOf(".") + 1, confirmdate.indexOf(".("));

                                    if(addrDepThree.contains(",")){
                                        addrDepThree = addrDepThree.substring(addrDepThree.indexOf("(") + 1, addrDepThree.indexOf(",") - 1);
                                    }

                                    String finalAddrDepThree = addrDepThree;
                                    Marker marker = list.parallelStream().filter(item -> finalAddrDepThree.equals(item.getAddrDepThree())).findFirst().orElse(null);

                                    if(marker == null){
                                        marker = new Marker();
                                        marker.setConfirmDate(confirmdate);
                                        marker.setLocationName(locationName);
                                        marker.setAddrDepOne("서울시");
                                        marker.setAddrDepTwo(CrawTypeConstants.TYPE_JUNG);
                                        marker.setAddrDepThree(addrDepThree);
                                        marker.setAddress(marker.getAddrDepOne() + " " + marker.getAddrDepTwo() + " " + marker.getAddrDepThree());
                                        marker.setTimes(des_str);
                                        marker.setDescription(childs.get(4).text());

                                        list.add(marker);
                                        log.info(marker.toString());
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        return list;
    }
}
