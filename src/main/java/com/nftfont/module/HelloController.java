package com.nftfont.module;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello/api")
    public String Hello(){
        return "hello";
    }
    @GetMapping("/")
    public String aa(){
        return "abc";
    }
}
