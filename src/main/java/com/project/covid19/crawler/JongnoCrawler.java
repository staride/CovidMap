package com.project.covid19.crawler;

import com.project.covid19.Util.CrawlingUtil;
import com.project.covid19.Util.Util;
import com.project.covid19.constants.CrawTypeConstants;
import com.project.covid19.entity.Marker;
import lombok.extern.java.Log;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class JongnoCrawler extends Crawler{

    public JongnoCrawler(String type) {
        super(type);
    }

    @Override
    public List<Marker> start() {

        ArrayList<Marker> list = new ArrayList<Marker>();

        for(String url : this.urls){
            Document document = CrawlingUtil.connectUrl(url);

            Elements elements = document.select("tbody>tr");
            String rowspan = null;
            int size = elements.size();

            int totalrow = 0;
            int count = 0;
            String confirmDate = null;

            for(int i=0;i<size;i++){
                Element el = elements.get(i);
                if(el.select("td>ul.list-ping").size() ==0){
                    rowspan = el.child(0).attr("rowspan");

                    if(!rowspan.equals("")){
                        log.info("full data : " + el.text());
                        Elements td = el.select("td.bg-grey");
                        confirmDate = td.text().substring((td.text().indexOf("일 ") + 2), td.text().length()).replaceAll(" ", "");
                        confirmDate = confirmDate.substring(0, confirmDate.length()-1);

                        totalrow = Integer.valueOf(rowspan) - 2;
                        log.info("confirmDate : " + confirmDate);
                    }else{
                        if(!Util.isEmptyString(confirmDate)){
                            Elements childs = el.select("td");
                            int c_size = childs.size();
                            String txt = childs.get(0).text();

                            if(c_size == 5 && count++ <= totalrow && !txt.equals("대중교통") && !txt.contains("동선 공개하지 않음") && !childs.get(1).text().contains("상호명 비공개")){

                                String addrDepThree = childs.get(2).text();

                                Marker marker = list.parallelStream().filter(item -> addrDepThree.equals(item.getAddrDepThree())).findFirst().orElse(null);

                                if(marker == null){
                                    marker = new Marker();
                                    marker.setConfirmDate(confirmDate);
                                    marker.setLocationName(childs.get(1).text());

                                    marker.setAddrDepOne("서울시");
                                    marker.setAddrDepTwo(this.type);
                                    marker.setAddrDepThree(addrDepThree);
                                    marker.setAddress(marker.getAddrDepOne() + " " + marker.getAddrDepTwo() + " " + marker.getAddrDepThree());
                                    marker.setTimes(childs.get(3).text());
                                    marker.setDescription(childs.get(4).text());

                                    list.add(marker);
                                }

                                log.info(marker.toString());
                            }

                            if (count > totalrow) {
                                count = 0;
                                totalrow = 0;
                                confirmDate = null;
                            }
                        }
                    }
                }
            }
        }

        return list;
    }
}
