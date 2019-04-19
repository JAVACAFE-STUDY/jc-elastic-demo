package io.javacafe.demo.auto.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.javacafe.demo.auto.service.MovieAutoReadService;
import io.javacafe.demo.auto.service.MovieAutoSaveService;
import io.javacafe.demo.auto.service.MovieReadService;
import io.javacafe.demo.auto.vo.MovieAuto;


@RestController
public class MovieAutoController {

	@Autowired
	private MovieReadService movieAutoService;
	@Autowired
	private MovieAutoSaveService movieAutoSaveService;
	@Autowired
	private MovieAutoReadService movieAutoReadService;
	
	
	
	
    @RequestMapping(value = "/api/movieAuto")
    public List<MovieAuto> movieAuto(String term) {
    	return movieAutoReadService.getAutoComplete(term);
    }
    
    

	
    @RequestMapping("/rebuild")
    public String rebuild() throws IOException {
    	List<MovieAuto> list = movieAutoService.loadDatas();
    	list.stream().forEach(o -> o.setPK(o.getUniqueId()));
    	
    	movieAutoSaveService.save(list);
    	
    	return "OK";
    }
    
    

}



