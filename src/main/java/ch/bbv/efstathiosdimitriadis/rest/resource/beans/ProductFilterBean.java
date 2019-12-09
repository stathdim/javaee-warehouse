package ch.bbv.efstathiosdimitriadis.rest.resource.beans;

import javax.ws.rs.QueryParam;

import lombok.Getter;
import lombok.Setter;

public class ProductFilterBean {
	@Getter
	@Setter
	private @QueryParam("year") int year;
	
	@Getter
	@Setter
	private @QueryParam("weight") double weight;
	
	@Getter
	@Setter
	private @QueryParam("category") String category;
}
