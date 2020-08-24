package com.project.covid19.serviceImpl;

import com.project.covid19.entity.Board;
import com.project.covid19.repository.BoardRepository;
import com.project.covid19.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository repo;

    @Override
    public boolean writeBoard(Board board) {
        return repo.save(board) != null ? true : false;
    }

    @Override
    public List<Board> getBoardList() {
        return repo.findAll();
    }

    @Override
    public Board readByBoardNo(Long boardNo) {
        return repo.findByBoardNo(boardNo);
    }

    @Override
    public boolean removeBoard(Long boardNo) {
        repo.deleteById(boardNo);
        return repo.findByBoardNo(boardNo) == null ? true : false;
    }

    @Override
    public boolean modifyBoard(Board board) {
        Board entity = repo.findByBoardNo(board.getBoardNo());

        entity.setTitle(board.getTitle());
        entity.setContents(board.getContents());

        return repo.save(entity) != null ? true : false;
    }
}
