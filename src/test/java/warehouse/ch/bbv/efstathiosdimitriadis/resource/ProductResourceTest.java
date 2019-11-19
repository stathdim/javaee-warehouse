package warehouse.ch.bbv.efstathiosdimitriadis.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.bbv.efstathiosdimitriadis.rest.resource.ProductResource;

public class ProductResourceTest {
	private Dispatcher dispatcher;
	
	@BeforeEach
	public void setup() {
		dispatcher = MockDispatcherFactory.createDispatcher();
		dispatcher.getRegistry().addPerRequestResource(ProductResource.class);
	}
	
	@Test
	public void getProductCodeIsOk() throws URISyntaxException {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = MockHttpRequest.get("/products");
		dispatcher.invoke(request, response);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void getProductReturnsProductJSON() throws URISyntaxException {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = MockHttpRequest.get("/products");
		dispatcher.invoke(request, response);

	}
}
