package com.iqabiloglu.spring_boot_security.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/index")
    public String getIndex( ) {
        return "Welcome to index page!";
    }


    @GetMapping("/dashboard")
    public String getDashboard( ) {
        return "Successful login to dashboard page!";
    }

    @GetMapping("/admin")
    public String getAdmin( ) {
        return "Successful login to admin page";
    }
}
