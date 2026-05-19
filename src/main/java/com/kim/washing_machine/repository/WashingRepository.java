package com.kim.washing_machine.repository;

import com.kim.washing_machine.entity.Washing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WashingRepository extends JpaRepository<Washing, Long> {

    @Modifying // 데이터 변경하는 명령어에 필요
    @Query(value = "ALTER TABLE washing AUTO_INCREMENT = 1", nativeQuery = true) // 직접 작성한 명령어를 직접 실행
    void resetAutoIncrement();

    List<Washing> findByPosition(int position);

    List<Washing> findByRoom(String room);

    // List<Washing>: Washing 객체만 있는 List
}
