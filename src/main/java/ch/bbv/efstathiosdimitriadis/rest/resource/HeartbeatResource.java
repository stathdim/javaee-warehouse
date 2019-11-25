package ch.bbv.efstathiosdimitriadis.rest.resource;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.bbv.efstathiosdimitriadis.rest.utils.Complex;
import ch.bbv.efstathiosdimitriadis.rest.utils.HeartRate;
import ch.bbv.efstathiosdimitriadis.rest.utils.Simple;
import ch.bbv.efstathiosdimitriadis.rest.utils.SimpleHeartRate;

/**
 * Provides a simple heartbeat.
 *
 */
@Path("heartbeat")
public class HeartbeatResource {

	@Inject @Complex HeartRate heartBean;
    /**
     * Get the heartbeat.  Basically if you can hit this "service"
     * then the machine and process are up.
     * 
     * @return a HTTP 200 with a simple "OK" text response packet.
     * 
     */
    @Produces({ MediaType.TEXT_PLAIN })
    @GET
    public Response getHeartBeat() {
        return heartBean.getHeartRate();
    }
}