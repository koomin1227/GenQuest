package com.example.gen_quest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @GetMapping("/menu_select")
    public String menu_select() {
        return "menu_select";
    }
}
