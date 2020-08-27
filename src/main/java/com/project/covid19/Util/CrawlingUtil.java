package com.project.covid19.Util;

import com.project.covid19.constants.CrawTypeConstants;
import com.project.covid19.constants.CrawUrlConstants;
import com.project.covid19.crawler.*;
import lombok.extern.java.Log;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Log
public class CrawlingUtil {

    public static Document connectUrl(String url) {
        log.info("connectUrl(): " + url);
        Document document = null;

        try {
//            Connection.Response homepage = Jsoup.connect(url).timeout(1000 * 60 * 60).method(Connection.Method.GET)
//                    .userAgent("Mozila/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20100101 " +
//                            "Firefox/10.0 AppleWebKit/537.36 (KHTML, like Gecko) " +
//                            "Chrome/51.0.2704.103 Safari/537.36")
//                    .execute();



            // document = homepage.parse();
            document = Jsoup.connect(url).timeout(1000 * 60 * 60).get();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return document;
    }

    public static String[] getUrl(String type){

        String[] urls = null;

        switch(type){
            case CrawTypeConstants.TYPE_JONGNO : urls = CrawUrlConstants.TYPE_JONGNO; break;
            case CrawTypeConstants.TYPE_JUNG : urls = CrawUrlConstants.TYPE_JUNG; break;
            case CrawTypeConstants.TYPE_YONGSAN : urls = CrawUrlConstants.TYPE_YONGSAN; break;
            case CrawTypeConstants.TYPE_SEONGDONG : urls = CrawUrlConstants.TYPE_SEONGDONG; break;
            case CrawTypeConstants.TYPE_GWANGJIN : urls = CrawUrlConstants.TYPE_GWANGJIN; break;
            case CrawTypeConstants.TYPE_DONGDAEMUN : urls = CrawUrlConstants.TYPE_DONGDAEMUN; break;
            case CrawTypeConstants.TYPE_JUNGNANG : urls = CrawUrlConstants.TYPE_JUNGNANG; break;
            case CrawTypeConstants.TYPE_SEONGBUK : urls = CrawUrlConstants.TYPE_SEONGBUK; break;
            case CrawTypeConstants.TYPE_GANGBUK : urls = CrawUrlConstants.TYPE_GANGBUK; break;
            case CrawTypeConstants.TYPE_DOBONG : urls = CrawUrlConstants.TYPE_DOBONG; break;
            case CrawTypeConstants.TYPE_NOWON : urls = CrawUrlConstants.TYPE_NOWON; break;
            case CrawTypeConstants.TYPE_EUNPYEONG : urls = CrawUrlConstants.TYPE_EUNPYEONG; break;
            case CrawTypeConstants.TYPE_SEODAEMUN : urls = CrawUrlConstants.TYPE_SEODAEMUN; break;
            case CrawTypeConstants.TYPE_MAPO : urls = CrawUrlConstants.TYPE_MAPO; break;
            case CrawTypeConstants.TYPE_YANGCHEON : urls = CrawUrlConstants.TYPE_YANGCHEON; break;
            case CrawTypeConstants.TYPE_GANGSEO : urls = CrawUrlConstants.TYPE_GANGSEO; break;
            case CrawTypeConstants.TYPE_GURO : urls = CrawUrlConstants.TYPE_GURO; break;
            case CrawTypeConstants.TYPE_GEUMCHEON : urls = CrawUrlConstants.TYPE_GEUMCHEON; break;
            case CrawTypeConstants.TYPE_YEONGDEUNGPO : urls = CrawUrlConstants.TYPE_YEONGDEUNGPO; break;
            case CrawTypeConstants.TYPE_DONGJAK : urls = CrawUrlConstants.TYPE_DONGJAK; break;
            case CrawTypeConstants.TYPE_GWANAK : urls = CrawUrlConstants.TYPE_GWANAK; break;
            case CrawTypeConstants.TYPE_SEOCHO : urls = CrawUrlConstants.TYPE_SEOCHO; break;
            case CrawTypeConstants.TYPE_GANGNAM : urls = CrawUrlConstants.TYPE_GANGNAM; break;
            case CrawTypeConstants.TYPE_SONGPA : urls = CrawUrlConstants.TYPE_SONGPA; break;
            case CrawTypeConstants.TYPE_GANGDONG : urls = CrawUrlConstants.TYPE_GANGDONG; break;
            default: urls = null;
        }

        return urls;
    }

    public static Crawler getCawler(String type){

        Crawler crawler = null;

        switch(type){
            case CrawTypeConstants.TYPE_JONGNO : crawler = new JongnoCrawler(type); break;
            case CrawTypeConstants.TYPE_JUNG : crawler = new JungCrawler(type); break;
            case CrawTypeConstants.TYPE_YONGSAN : crawler = new YongSanCrawler(type); break;
            case CrawTypeConstants.TYPE_SEONGDONG : crawler = new SeongdongCrawler(type); break;
            case CrawTypeConstants.TYPE_GWANGJIN : crawler = new GwangjinCrawler(type); break;
            case CrawTypeConstants.TYPE_DONGDAEMUN : crawler = new DongDaeMunCrawler(type); break;
            case CrawTypeConstants.TYPE_JUNGNANG : crawler = new JungnagCrawler(type); break;
            case CrawTypeConstants.TYPE_SEONGBUK : crawler = new SeongBukCrawler(type); break;
            case CrawTypeConstants.TYPE_GANGBUK : crawler = new GangBukCrawler(type); break;
            case CrawTypeConstants.TYPE_DOBONG : crawler = new DoBongCrawler(type); break;
            case CrawTypeConstants.TYPE_NOWON : crawler = new NowonCrawler(type); break;
            case CrawTypeConstants.TYPE_EUNPYEONG : crawler = new EunpyeongCrawler(type); break;
            case CrawTypeConstants.TYPE_SEODAEMUN : crawler = new SeoDaeMunCrawler(type); break;
            case CrawTypeConstants.TYPE_MAPO : crawler = new MapoCrawler(type); break;
            case CrawTypeConstants.TYPE_YANGCHEON : crawler = new YangcheonCrawler(type); break;
            case CrawTypeConstants.TYPE_GANGSEO : crawler = new GangSeoCrawler(type); break;
            case CrawTypeConstants.TYPE_GURO : crawler = new GuroCrawler(type); break;
            case CrawTypeConstants.TYPE_GEUMCHEON : crawler = new GeumCheonCrawler(type); break;
            case CrawTypeConstants.TYPE_YEONGDEUNGPO : crawler = new YeongDeungPoCrawler(type); break;
            case CrawTypeConstants.TYPE_DONGJAK : crawler = new DongJakCrawler(type); break;
            case CrawTypeConstants.TYPE_GWANAK : crawler = new GwanakCrawler(type); break;
            case CrawTypeConstants.TYPE_SEOCHO : crawler = new SeoChoCrawler(type); break;
            case CrawTypeConstants.TYPE_GANGNAM : crawler = new GangNamCrawler(type); break;
            case CrawTypeConstants.TYPE_SONGPA : crawler = new SongPaCrawler(type); break;
            case CrawTypeConstants.TYPE_GANGDONG : crawler = new GangDongCrawler(type); break;
            default: crawler = null;
        }

        return crawler;
    }
}
