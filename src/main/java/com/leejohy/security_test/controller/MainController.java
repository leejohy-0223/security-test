package com.leejohy.security_test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping("/")
    public String handleHome() {
        return "Public page";
    }

    @RequestMapping("/private")
    public String handlePrivate() {
        return "Private page";
    }

    @RequestMapping("/admin")
    public String handleAdmin() {
        return "Admin page";
    }
}
