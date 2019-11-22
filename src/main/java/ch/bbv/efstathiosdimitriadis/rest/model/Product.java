package ch.bbv.efstathiosdimitriadis.rest.model;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@ToString
@EqualsAndHashCode
@Value
public class Product implements Serializable {
	/**
	 * 
	 */
	@XmlAttribute
	private static final long serialVersionUID = -2106525495814801955L;
	@XmlElement
	@Getter
	private String name;
	@XmlElement
	@Getter
	private String category;
	@XmlTransient
	@Getter
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private String id;

	public Product(String name, String category) {
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
