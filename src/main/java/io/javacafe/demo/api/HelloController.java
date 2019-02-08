package io.javacafe.demo.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
    	log.info("API가 호출되었습니다.");
    	
    	return "OK";
    }





}
