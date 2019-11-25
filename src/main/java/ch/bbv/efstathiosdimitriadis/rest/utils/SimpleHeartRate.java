package ch.bbv.efstathiosdimitriadis.rest.utils;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

@RequestScoped
@Simple
public class SimpleHeartRate implements HeartRate {
	
	@Override
	public Response getHeartRate() {
		return Response.ok("OK").build();
		}
}
