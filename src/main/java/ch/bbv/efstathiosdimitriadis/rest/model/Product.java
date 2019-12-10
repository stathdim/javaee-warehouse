package ch.bbv.efstathiosdimitriadis.rest.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "products")
public class Product implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = -2106525495814801955L;
	@Getter
	@Setter
	@Column(name = "product_name")
	private String name;
	@Getter
	@Setter
	@Column(name = "category")
	private ProductCategory category;
	@Getter
	@Setter
	@Column(name = "year")
	private int year;
	@Getter
	@EqualsAndHashCode.Exclude
	@Id
	@Column(name = "product_id")
	private String id;

	public Product() {

	}

	@JsonCreator
	public Product(@JsonProperty("name") String name, @JsonProperty("category") ProductCategory category,
			@JsonProperty("year") int year) {
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
