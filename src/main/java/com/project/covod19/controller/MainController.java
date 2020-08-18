package com.project.covod19.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log
@Controller
public class MainController {

    @GetMapping("/")
    public String moveIndex(){
        log.info("moveIndex");
        return "index";
    }
}
