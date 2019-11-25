package ch.bbv.efstathiosdimitriadis.rest.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Singleton;
import javax.ejb.Startup;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.model.ProductCategory;

@Singleton
@Startup
public class ProductsDatabase {
	private Map<String, Product> products;

	public ProductsDatabase() {
		products = new HashMap<>();
		Product product = new Product("tire", ProductCategory.CAR_ACCESSORY);
		products.put(product.getId(), product);
		product = new Product("beans", ProductCategory.VEGETABLE);
		products.put(product.getId(), product);
		product = new Product("A wise man's fear", ProductCategory.BOOK);
		products.put(product.getId(), product);
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
