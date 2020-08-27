package com.project.covid19.repository;

import com.project.covid19.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface BoardRepository extends JpaRepository<Board, Long> {
    public Board findByBoardNo(long boardNo);
    public Board deleteByBoardNo(long boardNo);
}
