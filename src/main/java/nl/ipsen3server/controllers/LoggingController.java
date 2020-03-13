package nl.ipsen3server.controllers;

import nl.ipsen3server.dao.LoggingDAO;
import nl.ipsen3server.models.LogModel;

public class LoggingController {

    private LoggingDAO loggingDAO = new LoggingDAO();
    private AuthenticationController authenticationController = new AuthenticationController();

    /**
     * @author Anthony Schuijlenburg
     * @param experimentId The id of the experiment from which the logs need to be retrieved
     * @param token The token from the user requesting the log
     * @return Returns the logs
     */
    public String showlogs(int experimentId, String token){
        int userId= 0;
        if(this.authenticationController.hasPermission(userId, "READ")){
            return this.loggingDAO.showLogs(experimentId);
        }
        return "Not sufficient rights";
    }

    /**
     * @author Anthony Schuijlenburg
     * @param logModel A model of the log file that needs to be uploaded
     * @param token The token from the user trying to access
     */
    public void createLog(LogModel logModel, String token) {
        TokenController tokenController = new TokenController();
		int userId = Integer.parseInt(tokenController.tokenToUserId(token));
        logModel.setByUserId(userId);
        logModel.setTimestamp();
        if(this.authenticationController.hasPermission(userId, "WRITE")){
            this.loggingDAO.CreateLog(logModel);
        }
    }
}