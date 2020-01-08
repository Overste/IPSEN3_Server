package nl.ipsen3server.controlllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.util.PSQLException;

import nl.ipsen3server.dao.DatabaseUtilities;
import nl.ipsen3server.dao.PreparedStatmentDatabaseUtilities;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.LogModel;

public class LoggingController {


    private String tableName = "logs";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    SimpleDateFormat dateFormat;

	 private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());




    /**
     * @author Anthony Scheeres, Anthony Schuijlenburg
     */

    public String showlogs(int id) throws Exception {
        String query = String.format("SELECT title FROM %s WHERE project_id = ", tableName);
        query += id + ";";
        System.out.println(query);
        DatabaseUtilities d = new DatabaseUtilities();
        return d.connectThisDatabase2(databaseModel, query);
    }




    /**
     * @author Anthony Scheeres, Anthony Schuijlenburg
     */
    public void createLog(LogModel l, int project_id) {
        DatabaseUtilities d = new DatabaseUtilities();
        String query = String.format("select id from %s;", tableName);
        LogController r = new LogController();
        PreparedStatmentDatabaseUtilities f = new PreparedStatmentDatabaseUtilities();
        HashMap < String, List < String >> e1;
        try {
            e1 = d.connectThisDatabase(databaseModel, query);
            long id = r.createUserId2(e1.get("id"));
            String query2 = "INSERT INTO logs(title, id, project_id) VALUES (" +
                "?," +
                "?," +
                "?" +
                ");";
            System.out.println(query2);
            try {
                List <String> f2 = new ArrayList < > ();
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