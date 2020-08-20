package com.project.covid19.security.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DetailErrorInfoApi {
    private String target;
    private String message;
}