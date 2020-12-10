package com.project.covid19.controller;

import com.project.covid19.util.ValidatedUtil;
import com.project.covid19.entity.Board;
import com.project.covid19.service.BoardService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "http://kainTime.iptime.org:8080", allowedHeaders = "*")
public class BoardController {

    @Autowired
    BoardService service;

    @GetMapping("")
    public ResponseEntity<List<Board>> getBoardList(){

        log.info("getBoardList()");

        List<Board> list = service.getBoardList();
        int size = list.size();

        return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> writeBoard(@RequestBody @Validated Board board){

        log.info("writeBoard(): data - " + board);

        String message = null;

        if(ValidatedUtil.validateRegisterBoardInfo(board)){
            if(service.writeBoard(board)){
                message = "Success";
            }else{
                message = "fail";
            }
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }

        message = "fail";
        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity<Board> readByBoardNo(@PathVariable Long boardNo){
        log.info("readByBoardNo() : data - " + boardNo);
        Board board = null;

        if(boardNo != null && boardNo > 0){
            board = service.readByBoardNo(boardNo);

            if(board != null){
                return new ResponseEntity<Board>(board, HttpStatus.OK);
            }else{
                return new ResponseEntity<Board>(board, HttpStatus.NO_CONTENT);
            }
        }

        return new ResponseEntity<Board>(board, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{boardNo}")
    public ResponseEntity<String> removeBoard(@PathVariable Long boardNo){
        log.info("removeBoard() : data - " + boardNo);
        String message = null;

        if(boardNo != null && boardNo > 0){
            if(service.removeBoard(boardNo)){
                message = "Success";
                return new ResponseEntity<String>(message, HttpStatus.OK);
            }
        }

        message = "fail";
        return new ResponseEntity<String>(message, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{boardNo}")
    public ResponseEntity<String> modifyBoard(@PathVariable Long boardNo, @RequestBody @Validated Board board){
        log.info("modifyBoard() : boardNo - " + boardNo);
        log.info("modifyBoard() : Board - " + board);

        String message = null;

        if(ValidatedUtil.validateModifyBoardInfo(board) && boardNo != null && boardNo > 0){
            board.setBoardNo(boardNo);

            if(service.modifyBoard(board)){
                message = "Success";
                return new ResponseEntity<String>(message, HttpStatus.OK);
            }
        }

        message = "fail";
        return new ResponseEntity<String>(message, HttpStatus.NO_CONTENT);
    }
}
