package ch.bbv.efstathiosdimitriadis.rest.resource.beans;

import javax.ws.rs.QueryParam;



public class ProductFilterBean {
	private @QueryParam("year") int year;
	private @QueryParam("weight") double weight;
	private @QueryParam("category") String category;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
