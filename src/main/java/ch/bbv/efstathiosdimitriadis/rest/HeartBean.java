package ch.bbv.efstathiosdimitriadis.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

@RequestScoped
public class HeartBean {
	public Response getHeartRate() {
		return Response.ok("OK").build();
		}
}
