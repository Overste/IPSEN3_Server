package nl.ipsen3server.controlllers;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.util.PSQLException;

import nl.ipsen3server.dao.DatabaseUtilities;
import nl.ipsen3server.dao.LoggingDAO;
import nl.ipsen3server.dao.PreparedStatmentDatabaseUtilities;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.LogModel;

import javax.lang.model.type.ArrayType;

public class LoggingController {

    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());


    /**
     * @author Anthony Schuijlenburg
     * @param experimentId The id of the experiment from which the logs need to be retrieved
     * @param token The token from the user requesting the log
     * @return Returns the logs
     */
    public String showlogs(int experimentId, String token){
        AuthenticationController authenticationController = new AuthenticationController();
        int userId = authenticationController.tokenToUserId(token);
        if(authenticationController.hasPermission(userId, "READ")){
            LoggingDAO loggingDAO = new LoggingDAO();
            return loggingDAO.showLogs(experimentId);
        }
        return "Not sufficient rights";
    }


    /**
     * @author Anthony Schuijlenburg
     * @param logModel A model of the log file that needs to be uploaded
     * @param token The token from the user trying to access
     */
    public void createLog(LogModel logModel, String token) {
        AuthenticationController authenticationController = new AuthenticationController();
        int userId = authenticationController.tokenToUserId(token);
        logModel.setByUserId(userId);
        logModel.setTimestamp();
        if(authenticationController.hasPermission(userId, "WRITE")){
            LoggingDAO loggingDAO = new LoggingDAO();
            loggingDAO.CreateLog(logModel);
        }
    }
}