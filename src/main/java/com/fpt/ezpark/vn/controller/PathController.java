package com.fpt.ezpark.vn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PathController {

    @RequestMapping("/")
    public String home() {
        return "redirect:/user";
    }
}
