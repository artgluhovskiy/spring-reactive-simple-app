package org.art.web.rss.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "/hello.do", method = RequestMethod.GET)
    public String hello() {
        return "Hello";
    }
}
