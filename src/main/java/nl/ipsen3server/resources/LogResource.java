package nl.ipsen3server.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import nl.ipsen3server.controllers.LoggingController;
import nl.ipsen3server.models.LogModel;

/**
 * @author Anthony Schuijlenburg
 */
@Path("/log")
public class LogResource {

    private LoggingController loggingController = new LoggingController();

    //TODO logmodel needs to be send over and not created on the spot
    /**
     * @author Anthony Schuijlenburg
     * @param token The token of the user trying to post a log
     */
    @POST
    @Path("/{token}/upload/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createLog(LogModel logModel, @PathParam("token") String token) {
        loggingController.createLog(logModel, token);
    }


    /**
     * @author Anthony Schuijlenburg
     * @param id The id of the experiment from which the logs need to be downloaded
     * @param token The token of the user requesting the logs
     * @return Returns the logs from the requested project
     */
    @GET
    @Path("/{token}/download/{experimentId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String showlogs(@PathParam("experimentId") int id, @PathParam("token") String token){
        return loggingController.showlogs(id, token);
    }
}