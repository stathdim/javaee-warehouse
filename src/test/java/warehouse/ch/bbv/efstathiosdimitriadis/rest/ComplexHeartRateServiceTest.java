package warehouse.ch.bbv.efstathiosdimitriadis.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.bbv.efstathiosdimitriadis.rest.resource.HeartbeatResource;
import ch.bbv.efstathiosdimitriadis.rest.utils.Complex;
import ch.bbv.efstathiosdimitriadis.rest.utils.ComplexHeartRate;
import ch.bbv.efstathiosdimitriadis.rest.utils.HeartRate;

@ExtendWith(MockitoExtension.class)
public class ComplexHeartRateServiceTest {
	private static Dispatcher dispatcher;
	@Mock
	ComplexHeartRate heartBeanMock;

	@InjectMocks
	HeartbeatResource heartbeatService;

	@BeforeEach
	public void globalSetup() {
		Mockito.doCallRealMethod().when(heartBeanMock).getHeartRate();
		dispatcher = MockDispatcherFactory.createDispatcher();
		dispatcher.getRegistry().addSingletonResource(heartbeatService);
	}

	@Test
	public void heartbeatRouteReturns200Code() throws Exception {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = MockHttpRequest.get("/heartbeat");
		dispatcher.invoke(request, response);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	void heartBeatComplexServiceReturnsCorrectMessage() throws Exception {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request =  MockHttpRequest.get("/heartbeat");
		dispatcher.invoke(request, response);
		assertEquals("Hello from the otter side!", response.getContentAsString());
	}
}
