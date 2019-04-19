package io.javacafe.demo.auto.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieAuto {

	private String PK;
	
	private String item;
	
	private String uniqueId;
	private String movieNm;
	private String movieCd;
	private String prdtYear;
	
	private Double score;
	private String term;
	
	
	
	public MovieAuto() {}

	public MovieAuto(String item, String uniqueId, String movieNm, String movieCd, String prdtYear) {
		this.item = item;
		this.uniqueId = uniqueId;
		this.movieNm = movieNm;
		this.movieCd = movieCd;
		this.prdtYear = prdtYear;
	}



	

	
}
