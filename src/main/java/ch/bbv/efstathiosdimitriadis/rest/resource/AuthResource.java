package ch.bbv.efstathiosdimitriadis.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

import ch.bbv.efstathiosdimitriadis.rest.model.UserCredentials;

@Path("users")
public class AuthResource {
	private static Logger log = Logger.getLogger(AuthResource.class.getName());

	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginUser(UserCredentials credentials) {
		try {
			if (credentials.getUsername().equals("admin") && credentials.getPassword().equals("demo")) {
				log.info(credentials.getUsername() + " logged in.");
				return Response.ok().build();
			} else {
				log.info(credentials.getUsername() + " failed to login.");
				return Response.status(Status.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.status(Status.BAD_REQUEST).build();
		}
		
	}

}
