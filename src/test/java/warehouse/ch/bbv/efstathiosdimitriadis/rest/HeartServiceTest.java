package warehouse.ch.bbv.efstathiosdimitriadis.rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.bbv.efstathiosdimitriadis.rest.HeartBean;
import ch.bbv.efstathiosdimitriadis.rest.HeartbeatService;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HeartServiceTest {
	private static Dispatcher dispatcher;
	@Mock
	HeartBean heartBeanMock;

	@InjectMocks
	HeartbeatService heartbeatService;

	@BeforeEach
	public void globalSetup() {
		Mockito.doCallRealMethod().when(heartBeanMock).getHeartRate();
		dispatcher = MockDispatcherFactory.createDispatcher();
		dispatcher.getRegistry().addSingletonResource(heartbeatService);
	}

	@Test
	public void helloTest() throws Exception {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = MockHttpRequest.get("/heartbeat");
		dispatcher.invoke(request, response);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
}