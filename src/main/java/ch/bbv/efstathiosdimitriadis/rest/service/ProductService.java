package ch.bbv.efstathiosdimitriadis.rest.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriBuilder;

import org.apache.http.client.utils.URIBuilder;

import ch.bbv.efstathiosdimitriadis.rest.database.ProductsDatabase;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.resource.ProductResource;

public class ProductService {
	@Inject
	ProductsDatabase db;

	public Response getById(String id) {

		if (notUUID(id))
			return Response.status(Status.BAD_REQUEST).entity("Invalid product ID").build();

		Optional<Product> product = db.getById(id);
		if (product.isPresent())
			return Response.ok().entity(product.get()).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}

	public Response getAll() {
		List<Product> products = db.getAll();
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

	private boolean notUUID(String id) {
		try {
			UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			return true;
		}
		return false;
	}
}
