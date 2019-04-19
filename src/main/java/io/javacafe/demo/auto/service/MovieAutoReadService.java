package io.javacafe.demo.auto.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.StopWatch;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import io.javacafe.demo.auto.util.ElasticUtil;
import io.javacafe.demo.auto.vo.MovieAuto;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MovieAutoReadService {

	
	public List<MovieAuto> getAutoComplete(String keyword) {
		StopWatch stopWatch = new StopWatch();
        stopWatch.start();

		// ES 템플릿 선택
		String tplName = "tpl_movie_auto";
		String indexName = "movie_auto";
		int size = 20;
		
		// ES 템플릿 파라메터 생성
		Map<String, Object> params = new HashMap<>();
		params.put("size", size);
		params.put("keyword", keyword);
		
		
		// ES 객체 생성
        Client client = ElasticUtil.getEsClient();

        
        // ES 호출
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(indexName);
        
        SearchTemplateRequestBuilder templateBuilder = new SearchTemplateRequestBuilder(client)
                .setScript(tplName)         
                .setScriptType(ScriptType.STORED)     
                .setScriptParams(params)     
                .setRequest(searchRequest);
        
        SearchResponse response = templateBuilder
        		.get()
        		.getResponse();     
                
        
        // 결과 리턴
        List<MovieAuto> result = getAcResult(keyword, response);
        
        log.info("[자동완성 결과] ★★★ 처리시간 : {} ms ★★★  KEYWORD: {}, STATUS: {}, 전체: {}ea, 조회: {}ea",
        		stopWatch.getTime(), keyword, 
        		response.status().getStatus(), response.getHits().getTotalHits(), response.getHits().getHits().length);

        return result;	
	}

	
	private List<MovieAuto> getAcResult(String keyword, SearchResponse response) {
        if (response.getHits().getHits().length <= 0) {
            return Collections.emptyList();
        }
        
    	// 결과 객체를 생성한다.
    	List<MovieAuto> list = new ArrayList<>();        
        for (SearchHit h : response.getHits().getHits()) {
        	list.add(parse(keyword, h));
        }
        
        return list;
	}

	
	private MovieAuto parse(String keyword, SearchHit h) {

    	// 필드정보를 구한다.
    	String item = (String)h.getSourceAsMap().get("item");
    	String uniqueId = (String)h.getSourceAsMap().get("uniqueId");
    	String movieCd = (String)h.getSourceAsMap().get("movieCd");
    	String movieNm = (String)h.getSourceAsMap().get("movieNm");
    	String prdtYear = (String)h.getSourceAsMap().get("prdtYear");

    	// 스코어를 구한다.
    	Double score = Double.parseDouble(Float.toString(h.getScore()));
    	
    	// 객체 리턴
    	MovieAuto vo = new MovieAuto(item, uniqueId, movieNm, movieCd, prdtYear);
    	vo.setScore(score);
    	vo.setTerm(keyword);
    	
    	return vo;
	}
	
	
	
}







