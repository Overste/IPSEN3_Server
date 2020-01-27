package nl.ipsen3server.dao;

import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.LogModel;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class LoggingDAO {
    private String tableName = "experiment_log";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);


    /**
     * @author Anthony Schuijlenburg
     * @param experimentId The id of the experiment from which the logs need to be retrieved
     * @return Returns the logs
     */
    public String showLogs(int experimentId) {
        String query = String.format("SELECT * FROM %s WHERE experiment_id = ?;", tableName);
        ArrayList<String> data = new ArrayList<>(Arrays.asList(Integer.toString(experimentId)));
        return connectToDatabase (query, "SELECT", data);
    }


    /**
     * @author Anthony Schuijlenburg
     * @param logModel The model of the log that needs to be uploaded
     */
    public void CreateLog(LogModel logModel){
        int latestLogId = getLatestLogId();
        logModel.setLogId(latestLogId + 1);
        String query = String.format("INSERT INTO %s VALUES(?, ?, ?, ?, ?, ?)", tableName);
        ArrayList<String> data = new ArrayList<String>(Arrays.asList(
                String.valueOf(logModel.getLogId()),
                logModel.getTimestamp(),
                logModel.getTitle(),
                logModel.getDescription(),
                String.valueOf(logModel.getByUserId()),
                String.valueOf(logModel.getExperimentId())
        ));

        connectToDatabase(query, "INSERT", data);
    }


    /**
     * @author Anthony Schuijlenburg
     * @return the id of the latest log available
     */
    public int getLatestLogId(){
        String query = String.format("SELECT log_id FROM %s ORDER BY log_id", tableName);
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();
        String result = databaseUtilities.connectToDatabase(databaseModel, query, "SELECT");
        String[] results = result.split("");

        int logId = 0;

        for(int i=0; i < results.length; i++){
            try{
                int currentLog = Integer.parseInt(results[i]);
                if(currentLog > logId){
                    logId = currentLog;
                }
            }catch (Exception e){}
        }
        return logId;
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