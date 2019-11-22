package ch.bbv.efstathiosdimitriadis.rest.model;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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
	private String category;
	@Getter
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private String id;

	@JsonCreator
	public Product(@JsonProperty("name") String name, @JsonProperty("category") String category) {
		this.name = name;
		this.category = category;
		id = UUID.randomUUID().toString();
	}

	public Product modifyName(String name) {
		return new Product(name, category);
	}

	public Product modifyCategory(String category) {
		return new Product(name, category);
	}

}
