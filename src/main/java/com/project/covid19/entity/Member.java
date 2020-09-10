package com.project.covid19.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "userNo")
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    @Column(length = 50, nullable = false)
    private String id;

    @Column(length = 200, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String nickName;

    @Column(length = 13, nullable = false)
    private String phone;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20, nullable = true, name = "position_X")
    private String positionX = "";

    @Column(length = 20, nullable = true, name = "position_y")
    private String positionY = "";

    @Column(length = 4096, nullable = true)
    private String refreshToken;
}
