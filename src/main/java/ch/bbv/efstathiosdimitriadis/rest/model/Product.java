package ch.bbv.efstathiosdimitriadis.rest.model;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@ToString
@EqualsAndHashCode
@Value
public class Product implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = -2106525495814801955L;
	@Getter
	private String name;
	@Getter
	private ProductCategory category;
	@Getter
	private int year;
	@Getter
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private String id;


	@JsonCreator
	public Product(@JsonProperty("name") String name, @JsonProperty("category") ProductCategory category, @JsonProperty("year") int year) {
		this.name = name;
		this.category = category;
		id = UUID.randomUUID().toString();
		this.year = year;
	}

	public Product modifyName(String newName) {
		return new Product(newName, category, year);
	}

	public Product modifyCategory(ProductCategory newCategory) {
		return new Product(name, newCategory, year);
	}
	
	public Product modifyYear(int newYear) {
		return new Product(name, category, newYear);
	}

}
