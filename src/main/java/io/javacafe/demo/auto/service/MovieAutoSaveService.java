package io.javacafe.demo.auto.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.stereotype.Service;

import io.javacafe.demo.auto.util.ElasticUtil;
import io.javacafe.demo.auto.vo.MovieAuto;
import lombok.extern.slf4j.Slf4j;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


@Slf4j
@Service
public class MovieAutoSaveService {

	
	public void save(List<MovieAuto> list) throws IOException {
        
        Client client = ElasticUtil.getEsClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        
        for (MovieAuto vo : list) { 
            IndexRequestBuilder requsetBuilder = client.prepareIndex("movie_auto", "_doc", vo.getPK())
            		.setSource(makeContentBuilder(vo));
            
            bulkRequest.add(requsetBuilder);
        }
        
        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            log.error("[Bulk처리 실패] " + bulkResponse.buildFailureMessage());
        }
        log.info("ElasticSearch 데이터 {}건 입력", list.size());		
	}

	
	private XContentBuilder makeContentBuilder(MovieAuto vo) throws IOException {

        XContentBuilder builder = jsonBuilder()
                .startObject()	                
	                .field("item", vo.getItem())
	                .field("uniqueId", vo.getUniqueId()) 
	                .field("movieCd", vo.getMovieCd())
	                .field("movieNm", vo.getMovieNm()) 
	                .field("prdtYear", vo.getPrdtYear()) 
                .endObject();
        
        return builder;
   }
	
	
}



