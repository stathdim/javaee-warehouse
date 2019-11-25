package ch.bbv.efstathiosdimitriadis.rest.utils;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

@Complex
@RequestScoped
public class ComplexHeartRate implements HeartRate{

	@Override
	public Response getHeartRate() {
		String resp = "Hello from the otter side!";
		return Response.ok().entity(resp).build();
	}
 
}
