package com.project.covid19.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "boardNo")
@ToString
@Entity
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

    @JsonFormat(pattern = "yyyy-mm-dd HH:MM:ss")
    @Column(length = 50, nullable = false, columnDefinition = "timestamp default now()", name = "regdate")
    private Date regDate;

}
