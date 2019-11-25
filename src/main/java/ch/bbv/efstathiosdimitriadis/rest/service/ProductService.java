package ch.bbv.efstathiosdimitriadis.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import ch.bbv.efstathiosdimitriadis.rest.database.ProductsDatabase;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;

public class ProductService {
	@Inject ProductsDatabase db;

	public Response getById(String id) {
		Optional<Product> product = db.getById(id);
		if (product.isPresent())
			return Response.ok().entity(product.get()).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}

	public List<Product> getAll() {
		return db.getAll();
	}
	
	public List<Product> getByName(String name) {
		return db.getByName(name);
	}

	public Optional<Product> add(Product product) {
		return db.add(product);
	}

	public Optional<Product> remove(String id) {
		return db.remove(id);
	}
	
}
