package com.project.covid19.service;

import com.project.covid19.entity.Board;

import java.util.List;

public interface BoardService {
    public boolean writeBoard(Board board);
    public List<Board> getBoardList();
    public Board readByBoardNo(Long boardNo);
    public boolean removeBoard(Long boardNo);
    public boolean modifyBoard(Board board);
}
