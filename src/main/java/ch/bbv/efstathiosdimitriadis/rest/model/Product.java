package ch.bbv.efstathiosdimitriadis.rest.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "products")
public class Product implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = -2106525495814801955L;
	@Column(name = "product_name")
	private String name;
	@Column(name = "category")
	private ProductCategory category;
	@Column(name = "year")
	private int year;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return year == product.year &&
				name.equals(product.name) &&
				category == product.category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, category, year);
	}

	@Override
	public String toString() {
		return "Product{" +
				"name='" + name + '\'' +
				", category=" + category +
				", year=" + year +
				", id='" + id + '\'' +
				'}';
	}
}
