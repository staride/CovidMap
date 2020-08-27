package com.project.covid19.controller;

import com.project.covid19.Util.Util;
import com.project.covid19.entity.Marker;
import com.project.covid19.service.CrawlingService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log
@RestController
@RequestMapping("/craw")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class CrawlingContainer {

    @Autowired
    CrawlingService service;

    @GetMapping("/{type}")
    public ResponseEntity<List<Marker>> getMarkersList(@PathVariable String type){
        log.info("getMarkersInfoList() : type - " + type);
        List<Marker> list = null;

        if(!Util.isEmptyString(type)){
            list = service.getMarkersList(type);
            return new ResponseEntity<List<Marker>>(list, HttpStatus.OK);
        }

        return new ResponseEntity<List<Marker>>(list, HttpStatus.BAD_REQUEST);
    }
}
