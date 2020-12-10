package com.project.covid19.controller;

import com.project.covid19.entity.CovidStatus;
import com.project.covid19.util.Util;
import com.project.covid19.entity.Marker;
import com.project.covid19.service.CrawlingService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log
@RestController
@RequestMapping("/craw")
@CrossOrigin(origins = "http://kainTime.iptime.org:8080", allowedHeaders = "*")
public class CrawlingContainer {

    @Autowired
    CrawlingService service;

    @GetMapping("/{type}")
    public ResponseEntity<List<Marker>> getMarkersList(@PathVariable String type){
        log.info("getMarkersInfoList() : type - " + type);
        List<Marker> list = null;
        try {

            if(!Util.isEmptyString(type.trim())){
                list = service.getMarkersList(type);
                return new ResponseEntity<List<Marker>>(list, HttpStatus.OK);
            }

        } catch (Exception e){
            log.info(ExceptionUtils.getStackTrace(e));
        }

        return new ResponseEntity<List<Marker>>(list, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/status")
    public ResponseEntity<CovidStatus> getCovidStatus(){

        CovidStatus status = service.getCovidStatus();

        if(status != null){
            return new ResponseEntity<CovidStatus>(status, HttpStatus.OK);
        }

        return new ResponseEntity<CovidStatus>(status, HttpStatus.BAD_REQUEST);
    }
}
