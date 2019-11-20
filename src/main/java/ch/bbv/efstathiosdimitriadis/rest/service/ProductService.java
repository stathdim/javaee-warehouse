package ch.bbv.efstathiosdimitriadis.rest.service;

import java.util.ArrayList;
import java.util.List;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;

public class ProductService {
	private static List<Product> products;

	public ProductService() {
		if (products == null) {
			products = new ArrayList<>();
			products.add(new Product("tire", "car-accessory"));
			products.add(new Product("beans", "vegetable"));
			products.add(new Product("A wise man's fear", "book"));
		}
	}
	
	public Product getById(int id) {
		if (id >= products.size()) return null;
		return products.get(id);
	}

}
