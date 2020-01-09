package nl.ipsen3server.dao;

import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class LoggingDAO {
    private String tableName = "experiment_log";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);

    public String showLogs(int experimentId) {
        String query = String.format("SELECT * FROM %s WHERE experiment_id = ?;", tableName);
        ArrayList<String> data = new ArrayList<>(Arrays.asList(Integer.toString(experimentId)));
        return connectToDatabase (query, "SELECT", data);
    }

    private String connectToDatabase(String query, String queryType, ArrayList<String> data) {
        PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();
        String returnQuery = null;
        try {
            returnQuery = preparedStatmentDatabaseUtilities.connectToDatabase(databaseModel, query, queryType, data);
        } catch (Exception e) {
        }
        return returnQuery;
    }
}
