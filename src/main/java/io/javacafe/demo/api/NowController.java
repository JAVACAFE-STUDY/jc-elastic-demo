package io.javacafe.demo.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NowController {

    @RequestMapping("/hello")
    public String hello() {
    	return "OK";
    }





}
