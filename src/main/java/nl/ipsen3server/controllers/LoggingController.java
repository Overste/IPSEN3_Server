package nl.ipsen3server.controllers;

import nl.ipsen3server.dao.LoggingDAO;
import nl.ipsen3server.models.LogModel;

import javax.ws.rs.core.Response;

public class LoggingController {

    private LoggingDAO loggingDAO = new LoggingDAO();
    private AuthenticationController authenticationController = new AuthenticationController();

    /**
     * @author Anthony Schuijlenburg
     * @param experimentId The id of the experiment from which the logs need to be retrieved
     * @param token The token from the user requesting the log
     * @return Returns the logs
     */
    public Response showlogs(int experimentId, String token){
        int userId= 0;
        if(this.authenticationController.hasPermission(userId, "READ")){
            return Response.ok(this.loggingDAO.showLogs(experimentId)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * @author Anthony Schuijlenburg
     * @param logModel A model of the log file that needs to be uploaded
     * @param token The token from the user trying to access
     */
    public Response createLog(LogModel logModel, String token) {
        TokenController tokenController = new TokenController();
		int userId = Integer.parseInt(tokenController.tokenToUserId(token));
        logModel.setByUserId(userId);
        logModel.setTimestamp();
        if(this.authenticationController.hasPermission(userId, "WRITE")){
            if(this.loggingDAO.CreateLog(logModel) != null) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}