package ch.bbv.efstathiosdimitriadis.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;

public class ProductService {
	private static Map<String, Product> products;

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

	public Optional<Product> getById(String id) {
		return Optional.ofNullable(products.get(id));
	}

	public List<Product> getAll() {
		if (products.isEmpty())
			return new ArrayList<Product>();
		return new ArrayList<Product>(products.values());
	}

	public List<Product> getByName(String name) {
		return products.values().stream().filter((p) -> name.equals(p.getName())).collect(Collectors.toList());
	}

	public Optional<Product> add(Product product) {
		if (product == null)
			return Optional.empty();

		products.put(product.getId(), product);
		return Optional.of(products.get(product.getId()));
	}

	public Optional<Product> remove(String id) {
		return Optional.ofNullable(products.remove(id));
	}
}
