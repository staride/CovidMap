package com.project.covid19.service;

import com.project.covid19.entity.Marker;

import java.util.List;

public interface CrawlingService {
    public List<Marker> getMarkersList(String type) throws Exception;
}
