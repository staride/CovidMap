package com.project.covid19.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "markerNo")
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "markers")
public class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marker_no")
    private Long markerNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(length = 50, nullable = false, columnDefinition = "timestamp default now()", name = "regdate")
    private Date regDate;

    @Column(length = 50, nullable = true)
    private String confirmDate;

    @Column(length = 500, nullable = false)
    private String locationName;

    @Column(length = 500, nullable = false)
    private String address;

    @Column(length = 500, nullable = false)
    private String addressSecond;

    @Column(length = 500, nullable = false)
    private String disinfect;

    @Column(length = 20, nullable = true, name = "position_X")
    private String positionX;

    @Column(length = 20, nullable = true, name = "position_Y")
    private String positionY;
}
