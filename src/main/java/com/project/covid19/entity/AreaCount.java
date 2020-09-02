package com.project.covid19.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "areaNo")
@ToString
@Entity
@DynamicInsert
@Table(name = "area_count")
public class AreaCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_no")
    private Long areaNo;

    @Column(length = 50, nullable = false)
    private String areaName;

    @Column(nullable = false)
    private long count;

    @JsonFormat(pattern = "yyyy-mm-dd HH:MM:ss")
    @Column(nullable = false, columnDefinition = "timestamp default now()", name = "lastupdate")
    private Date lastUpdateDate;

}
