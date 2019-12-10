package ch.bbv.efstathiosdimitriadis.rest.dao;

import java.util.List;
import java.util.Optional;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.resource.beans.ProductFilterBean;

public interface ProductDAO {

	Optional<Product> getById(String id);

	List<Product> getAll(ProductFilterBean filter);

	Optional<Product> getByName(String name);

	Optional<Product> add(Product product);

	Optional<Product> remove(String id);

	Optional<Product> update(String id, Product update);

}