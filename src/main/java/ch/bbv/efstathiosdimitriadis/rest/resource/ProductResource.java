package ch.bbv.efstathiosdimitriadis.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("products")
public class ProductResource {

	@GET
	public Response getProduct() {
		return Response.ok().build();
	}
}
