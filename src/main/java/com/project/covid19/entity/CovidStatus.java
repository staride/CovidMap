package com.project.covid19.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CovidStatus {

    // 누적 확진자와 누적 확진자 전일대비
    private String totalAcc;
    private String totalDiff;

    // 누적 사망자와 사망자 전일대비
    private String deathAcc;
    private String deathDiff;

    // 누적 격리해제와 격리해제 전일대비
    private String freeAcc;
    private String freeDiff;

    // 치명률
    private String fatalityRate;

    // 누적 총검사자와 총검사자 전일대비
    private String totalInspectionAcc;
    private String totalInspectionDiff;

    // 누적 검사중과 검사중 전일대비
    private String inspectionAcc;
    private String inspectionDiff;

    // 누적 결과음성과 결과음성 전일대비
    private String totalNegativeAcc;
    private String totalNegativeDiff;
}
