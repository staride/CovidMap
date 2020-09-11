package com.project.covid19.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "boardNo")
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String contents;

    @Column(length = 200, nullable = false)
    private String writer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(length = 50, nullable = false, columnDefinition = "timestamp default now()", name = "regdate")
    private Date regDate;

}
