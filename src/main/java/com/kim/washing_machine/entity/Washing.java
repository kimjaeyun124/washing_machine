package com.kim.washing_machine.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // PROTECTED로 외부에서 Washing을 새로하여 코드를 안꼬이게 하기위해, DB 조회시 필요한 JPA생성
public class Washing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 자동으로 1씩 올려서 값을 보냄 = AUTO_INCREMENT
    private Long id;

    @Column(nullable = false, length = 3) // 제약을 주기 위해서 사용
    private String room;

    @Column(nullable = false)
    private int position;

    @Column(nullable = false)
    private boolean done;

    @Builder // Setter로 나중에 강제로 변경을 못하게 하기 위해서 사용
    public Washing(String room, int position) {
        this.room = room;
        this.position = position;
        this.done = false;
    }

    public void finishWashing() {
        this.done = true;
    }

}


