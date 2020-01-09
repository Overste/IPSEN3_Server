package nl.ipsen3server.controlllers;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.ipsen3server.dao.LoggingDAO;
import org.postgresql.util.PSQLException;

import nl.ipsen3server.dao.DatabaseUtilities;
import nl.ipsen3server.dao.PreparedStatmentDatabaseUtilities;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.LogModel;

import javax.lang.model.type.ArrayType;

public class LoggingController {


    private String tableName = "experiment_log";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    private PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();

    private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());


    /**
     * @author Anthony Scheeres, Anthony Schuijlenburg
     */

    public String showlogs(int experimentId, String token) throws Exception {
        AuthenticationController authenticationController = new AuthenticationController();
        int userId = authenticationController.tokenToUserId(token);
        if(authenticationController.hasPermission(userId, "READ")){
            LoggingDAO loggingDAO = new LoggingDAO();
            return loggingDAO.showLogs(experimentId);
        }
        return "Not sufficient rights";
    }


    public void createLog(LogModel logModel) {
        String query = String.format("SELECT log_id FROM %s ORDER BY log_id", tableName);
        try{
            ArrayList<String> data = new ArrayList<String>();
            String result = preparedStatmentDatabaseUtilities.connectToDatabase(databaseModel, query, "SELECT", data);

            System.out.println(result);
        } catch (Exception e){

        }
    }


    /**
     * @author Anthony Scheeres, Anthony Schuijlenburg
     */
    public void createLogs(LogModel l, int project_id) {
        DatabaseUtilities d = new DatabaseUtilities();
        String query = String.format("select id from %s;", tableName);
        LogController logController = new LogController();
        PreparedStatmentDatabaseUtilities f = new PreparedStatmentDatabaseUtilities();
        HashMap<String, List<String>> e1;
        try {
            e1 = d.connectThisDatabase(databaseModel, query);
            long id = logController.createUserId2(e1.get("id"));
            String query2 = "INSERT INTO logs(title, id, project_id) VALUES (" +
                    "?," +
                    "?," +
                    "?" +
                    ");";
            System.out.println(query2);
            try {
                List<String> f2 = new ArrayList<>();
                f2.add(l.getTitle());
                f2.add(Long.toString(id));
                f2.add(Integer.toString(project_id));
                f.connectDatabaseJson(databaseModel, query2, f2, true);
            } catch (PSQLException e) {
                // TODO Auto-generated catch block
                LOGGER.log(Level.SEVERE, "Error occur", e);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.log(Level.SEVERE, "Error occur", e);
        }
    }
}