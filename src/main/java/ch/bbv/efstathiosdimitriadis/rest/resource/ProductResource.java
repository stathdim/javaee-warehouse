package ch.bbv.efstathiosdimitriadis.rest.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.service.ProductService;


@Path("products")
public class ProductResource {
	private ProductService productService;
	
	public ProductResource() {
		// TODO Auto-generated constructor stub
	}
	
	@Inject
	public ProductResource(ProductService productService) {
		this.productService = productService;
	}



	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct() throws Exception {
		
		Response productResponse = productService.getAll();
		return productResponse;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(Product product) {
		return Response.ok(product).build();
	}
}
