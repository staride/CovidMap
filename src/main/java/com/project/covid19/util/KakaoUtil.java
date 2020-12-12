package com.project.covid19.util;

import com.project.covid19.constants.KakaoConstants;
import com.project.covid19.entity.Marker;
import lombok.extern.java.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;

@Log
public class KakaoUtil {

    private static JSONParser parser = new JSONParser();

    public static void searchUsePlace(Marker marker) throws Exception{
        if(marker.getAddress() == null){
            String queryString = "?query="+ URLEncoder.encode(marker.getLocationName(), "UTF-8");
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();

            headers.add("Authorization", "KakaoAK " + KakaoConstants.API_KEY);
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

            URI url = URI.create(KakaoConstants.HOST+KakaoConstants.SEARCH_PLACE_KEYWORD_PATH_JSON+queryString);
            RequestEntity<String> rq = new RequestEntity<>(headers, HttpMethod.GET, url);
            ResponseEntity<String> re = restTemplate.exchange(rq, String.class);

            if(re.getBody() instanceof String){
                JSONObject document = (JSONObject) parser.parse((String)re.getBody());
                JSONArray datas = (JSONArray) document.get("documents");

                int datasize = datas.size();
                for(int i=0;i<datasize;i++){
                    JSONObject data = (JSONObject)datas.get(i);
                    String address = (String)data.get("address_name");
                    int index = address.indexOf(" ");
                    String city = address.substring(0, index).trim();

                    if(city.contains("서울")){
                        marker.setAddress(address);
                        int nextindex = address.indexOf(" ", index+1);
                        marker.setPositionX((String)data.get("y"));
                        marker.setPositionY((String)data.get("x"));
                        break;
                    }
                }
            }
        }
    }

    public static void searchUseAddress(Marker marker) throws Exception{
        if(marker.getAddress() != null){
            String queryString = "?query="+ URLEncoder.encode(marker.getAddress(), "UTF-8");
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();

            headers.add("Authorization", "KakaoAK " + KakaoConstants.API_KEY);
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

            URI url = URI.create(KakaoConstants.HOST+KakaoConstants.SEARCH_ADDRESS_PATH_JSON+queryString);
            RequestEntity<String> rq = new RequestEntity<>(headers, HttpMethod.GET, url);
            ResponseEntity<String> re = restTemplate.exchange(rq, String.class);

            if(re.getBody() instanceof String){
                JSONObject document = (JSONObject) parser.parse((String)re.getBody());
                JSONArray datas = (JSONArray) document.get("documents");

                int datasize = datas.size();
                for(int i=0;i<datasize;i++){
                    JSONObject data = (JSONObject)datas.get(i);
                    String address = (String)data.get("address_name");

                    if(address.contains("서울")){
                        marker.setPositionX((String)data.get("y"));
                        marker.setPositionY((String)data.get("x"));
                        break;
                    }
                }
            }
        }
    }
}
