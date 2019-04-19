package io.javacafe.demo.auto.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import io.javacafe.demo.auto.util.ElasticUtil;
import io.javacafe.demo.auto.vo.MovieAuto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieReadService {

	
	public List<MovieAuto> loadDatas() {
	    StopWatch stopWatch = new StopWatch();
	    stopWatch.start();
	    
	    // 클라이언트 생성
	    Client client = ElasticUtil.getEsClient();
	            
	    // 전체데이터를 Scroll 방식으로 10000개씩 읽어온다.
	    SearchResponse response = client.prepareSearch("movie_search")
	            .setTypes("_doc")
	            .setScroll(new TimeValue(60000))
	            .setQuery(QueryBuilders.matchAllQuery())
	            .setSize(10000)
	            .get();
	    
	    List<MovieAuto> result = new ArrayList<>();
	    int i=1;
	    
	    do {
	        log.info("Scroll 로딩카운트 : {}", i);
	        
	        for (SearchHit h : response.getHits().getHits()) {
	        	MovieAuto vo = makeVO(h);
	        	if (StringUtils.isNotBlank(vo.getItem())) {
	        		result.add(makeVO(h));
	        	}
	        }
	        
	        response = client.prepareSearchScroll(response.getScrollId())
	                .setScroll(new TimeValue(60000))
	                .execute()
	                .actionGet();
	        
	        i++;
	        
	    } while(response.getHits().getHits().length != 0); 

	    long eTime = stopWatch.getTime();
	    log.info("전체 로딩시간 : {} ms, 로딩건수 : {} 건", eTime, result.size());
	    
	    return result;	
	}
	

	private MovieAuto makeVO(SearchHit h) {
		MovieAuto vo = new MovieAuto();
		vo.setItem((String)h.getSourceAsMap().get("movieNm"));
		
		vo.setUniqueId(h.getId());
		vo.setMovieNm((String)h.getSourceAsMap().get("movieNm"));
		vo.setMovieCd((String)h.getSourceAsMap().get("movieCd"));
		vo.setPrdtYear((String)h.getSourceAsMap().get("prdtYear"));
	    
	    return vo;
	}


}



