package nl.ipsen3server.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import nl.ipsen3server.controlllers.LoggingController;
import nl.ipsen3server.models.LogModel;


/**
 * @author Anthony Scheeres, Anthony Schuijlenburg
 */
@Path("/log")
public class LogResource {

    private LoggingController loggingController = new LoggingController();


    /**
     * @author Anthony Scheeres, Anthony Schuijlenburg
     */
    @POST
    @Path("/upload/")
//    @Consumes(MediaType.APPLICATION_JSON)
    public void createLog() {
        LogModel logModel = new LogModel();
        loggingController.createLog(logModel);
    }

    /**
     * @author Anthony Scheeres, Anthony Schuijlenburg
     */
    @GET
    @Path("/{token}/download/{experimentId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String showlogs(@PathParam("experimentId") int id, @PathParam("token") String token) throws Exception {
        return loggingController.showlogs(id, token);
    }

}