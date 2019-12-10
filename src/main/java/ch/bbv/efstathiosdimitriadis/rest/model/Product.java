package ch.bbv.efstathiosdimitriadis.rest.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Entity
@ToString
@EqualsAndHashCode
@Value
@Table(name="products")
public class Product implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = -2106525495814801955L;
	@Getter
	@Column(name="product_name")
	private String name;
	@Getter
	@Column(name="category")
	private ProductCategory category;
	@Getter
	@Column(name="year")
	private int year;
	@Getter
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Id
	@Column(name="product_id")
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
