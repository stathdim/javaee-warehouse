package warehouse.ch.bbv.efstathiosdimitriadis.rest;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ch.bbv.efstathiosdimitriadis.rest.HeartbeatService;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;

public class HeartServiceTest {
	private static Dispatcher dispatcher;
	private static HeartbeatService heartbeatService;
	
	@BeforeAll 
	public static void globalSetup() {
		dispatcher = MockDispatcherFactory.createDispatcher();
		heartbeatService = new HeartbeatService();
		dispatcher.getRegistry().addSingletonResource(heartbeatService);
	}

	@Test 
	public void helloTest() throws Exception {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = MockHttpRequest.get("/v1/heartbeat");
		dispatcher.invoke(request, response);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
}