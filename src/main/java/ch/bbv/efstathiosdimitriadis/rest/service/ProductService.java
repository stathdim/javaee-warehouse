package ch.bbv.efstathiosdimitriadis.rest.service;

import java.util.HashMap;

import java.util.Map;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;

public class ProductService {
	private static Map<Integer, Product> products;

	public ProductService() {
		if (products == null) {
			products = new HashMap<>();
			Product product = new Product("tire", "car-accessory");
			products.put(product.getId(), product);
			product = new Product("beans", "vegetable");
			products.put(product.getId(), product);
			product = new Product("A wise man's fear", "book");
			products.put(product.getId(), product);

		}
	}

	public Product getById(int id) {
		if (id >= products.size())
			return null;
		return products.get(id);
	}

}
