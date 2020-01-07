package ch.bbv.efstathiosdimitriadis.rest.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.bbv.efstathiosdimitriadis.rest.model.UserCredentials;

public class AuthResourceTest {
	private Dispatcher dispatcher;
	private final static String AUTH_URI = "/users/login";

	@BeforeEach
	public void setup() {
		dispatcher = MockDispatcherFactory.createDispatcher();
		dispatcher.getRegistry().addPerRequestResource(AuthResource.class);
	}

	@Test
	public void loginUserReturns200() throws JsonProcessingException, URISyntaxException {
		MockHttpResponse response = new MockHttpResponse();

		byte[] payload = getCredentialsBytes("admin", "demo");

		MockHttpRequest request = MockHttpRequest.post(AUTH_URI);
		request.content(payload);
		request.contentType(MediaType.APPLICATION_JSON);

		dispatcher.invoke(request, response);

		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void loginReturns401WhenCredentialsWrong() throws JsonProcessingException, URISyntaxException {
		MockHttpResponse response = new MockHttpResponse();

		byte[] payload = getCredentialsBytes("admin", "salamander");

		MockHttpRequest request = MockHttpRequest.post(AUTH_URI);
		request.content(payload);
		request.contentType(MediaType.APPLICATION_JSON);

		dispatcher.invoke(request, response);

		assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}

	@Test
	public void loginReturnsBadRequestWhenCredentialsAreMissing() throws URISyntaxException {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = MockHttpRequest.post(AUTH_URI);
		request.contentType(MediaType.APPLICATION_JSON);

		dispatcher.invoke(request, response);

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

	}

	@Test
	public void loginReturnsBadRequestWhenUsernameMissing() throws URISyntaxException, JsonProcessingException {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = MockHttpRequest.post(AUTH_URI);

		byte[] payload = getCredentialsBytes(null, "password");
		request.content(payload);
		request.contentType(MediaType.APPLICATION_JSON);

		dispatcher.invoke(request, response);

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

	}
	
	@Test
	public void loginReturnsBadRequestWhenPasswordMissing() throws URISyntaxException, JsonProcessingException {
		MockHttpResponse response = new MockHttpResponse();
		MockHttpRequest request = MockHttpRequest.post(AUTH_URI);

		byte[] payload = getCredentialsBytes("admin", null); // username used as testing parameter must be correct
		// otherwise the OR clause will fail at the first comparison and the missing password will not be triggered
		request.content(payload);
		request.contentType(MediaType.APPLICATION_JSON);

		dispatcher.invoke(request, response);

		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

	}


	private byte[] getCredentialsBytes(String username, String password) throws JsonProcessingException {
		UserCredentials credentials = new UserCredentials();
		credentials.setPassword(password);
		credentials.setUsername(username);

		ObjectMapper mapper = new ObjectMapper();
		String payload = mapper.writeValueAsString(credentials);
		return payload.getBytes();

	}
}
