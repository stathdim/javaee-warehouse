package ch.bbv.efstathiosdimitriadis.rest.model;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Product implements Serializable {
	/**
	 * 
	 */
	@XmlAttribute
	private static final long serialVersionUID = -2106525495814801955L;
	@XmlElement
	private String name;
	@XmlElement
	private String category;
	private String id;

	public Product() {
	}

	public Product(String name, String category) {
		this.name = name;
		this.category = category;
		id = UUID.randomUUID().toString();
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return name + " " + category;
	}

	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().isAssignableFrom(this.getClass()))
			return false;
		Product other = (Product) obj;
		if (getCategory().equals(other.getCategory()) && getName().equals(other.getName())
				&& getId().equals(other.getId()))
			return true;
		return false;
	}

}
