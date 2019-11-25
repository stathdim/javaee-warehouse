package ch.bbv.efstathiosdimitriadis.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.model.ProductCategory;

@Path("products")
public class ProductResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct() {
		Product product = new Product("tire", ProductCategory.CAR_ACCESSORY);
		return Response.ok(product).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(Product product) {
		return Response.ok(product).build();
	}
}
