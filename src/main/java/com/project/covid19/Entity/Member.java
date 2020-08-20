package com.project.covid19.Entity;

import lombok.*;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "userNo")
@ToString
@Entity
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

    @Column(length = 20, nullable = true)
    private String locationX;

    @Column(length = 20, nullable = true)
    private String locationY;
}
