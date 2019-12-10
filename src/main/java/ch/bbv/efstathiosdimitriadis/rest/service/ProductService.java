package ch.bbv.efstathiosdimitriadis.rest.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import ch.bbv.efstathiosdimitriadis.rest.dao.InMemory;
import ch.bbv.efstathiosdimitriadis.rest.dao.ProductDAO;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.resource.ProductResource;
import ch.bbv.efstathiosdimitriadis.rest.resource.beans.ProductFilterBean;

@RequestScoped
public class ProductService {
	@Inject
	@InMemory
	ProductDAO db;

	public ProductService() {
		// TODO Auto-generated constructor stub
	}

	public Response getById(String id) {
		if (notUUID(id))
			return Response.status(Status.BAD_REQUEST).entity("Invalid product ID").build();

		Optional<Product> product = db.getById(id);
		if (product.isPresent())
			return Response.ok().entity(product.get()).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}

	public Response getAll(ProductFilterBean filter) {
		List<Product> products = db.getAll(filter);
		if (products.size() == 0)
			return Response.noContent().build();
		GenericEntity<List<Product>> genericProducts = new GenericEntity<List<Product>>(products) {
		};
		return Response.ok(genericProducts).build();
	}

	public Response getByName(String name) {
		Optional<Product> product = db.getByName(name);
		if (!product.isPresent())
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(product.get()).build();
	}

	public Response add(Product product) {
		Optional<Product> created = db.add(product);
		if (!created.isPresent())
			return Response.status(Status.BAD_REQUEST).build();
		Product createdProduct = created.get();
		URI createdURI = UriBuilder.fromResource(ProductResource.class).path(createdProduct.getId()).build();
		return Response.created(createdURI).build();
	}

	public Response remove(String id) {
		if (notUUID(id))
			return Response.status(Status.BAD_REQUEST).entity("Invalid product ID").build();
		Optional<Product> removed = db.remove(id);
		if (!removed.isPresent())
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok().build();
	}

	public Response update(String id, Product update) {
		Optional<Product> updatedProduct = db.update(id, update);
		if (!updatedProduct.isPresent())
			return Response.status(Status.NOT_FOUND).build();
		URI createdURI = UriBuilder.fromResource(ProductResource.class).path(updatedProduct.get().getId()).build();
		return Response.ok(updatedProduct.get()).location(createdURI).build();
	}

	private static boolean notUUID(String id) {
		try {
			UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			return true;
		}
		return false;
	}

}
