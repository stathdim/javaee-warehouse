package ch.bbv.efstathiosdimitriadis.rest.resource;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.resource.beans.ProductFilterBean;
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
	public Response getAllProducts(@BeanParam ProductFilterBean filterBean) throws Exception {
		Response productResponse = productService.getAll(filterBean);
		return productResponse;
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneProduct(@PathParam("id") String id) throws Exception {
		return productService.getById(id);
	}
	
	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProduct(@PathParam("id") String id) {
		return productService.remove(id);
	}
	
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProduct(@PathParam("id") String id, Product update) {
		return productService.update(id, update);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(Product product) {
		return productService.add(product);
	}
}
