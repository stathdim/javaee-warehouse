package ch.bbv.efstathiosdimitriadis.rest.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product {
	String name;
	String category;
	String id;
	
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
	
	
}
