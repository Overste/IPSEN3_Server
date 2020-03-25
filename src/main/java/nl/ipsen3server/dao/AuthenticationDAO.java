package nl.ipsen3server.dao;

import java.util.logging.Level;
import nl.ipsen3server.controllers.LoggerController;
import nl.ipsen3server.models.AccountModel;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.ResponseR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class made specificly for checking user permissions
 */
public class AuthenticationDAO {
    private String tableName = "application_users";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    private PreparedStatementDatabaseUtilities preparedStatementDatabaseUtilities = new PreparedStatementDatabaseUtilities();
    private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());

    /**
     * @author Anthony Scheeres
     */
    private void givePermission(AccountModel u, Enum e) throws Exception {
        PreparedStatementDatabaseUtilities databaseController = new PreparedStatementDatabaseUtilities();
        List<String> list = new ArrayList<String>();
        String query2 = String.format("UPDATE application_users SET permission = array_append(permission,'%s') WHERE username = ?;", e);
        list.add(u.getUsername());
        databaseController.connectDatabaseJson(databaseModel, query2, list, false);
    }

    /**
     * takes in a userId and a permission and checks the database to see if this user has this permission
     *
     * @param userId     The id of the user of which it's permissions need to be checked
     * @param permission The permission it need te checked against
     * @return true or false depending on the users rights
     * @author AnthonySchuijlenburg
     */
    public boolean checkUserPermission(int userId, String permission) {
        permission = permission.toLowerCase();
        String query = String.format("SELECT has_%s FROM %s WHERE user_id =?;", permission, tableName);
        ArrayList data = new ArrayList(Arrays.asList(Integer.toString(userId)));
        return preparedStatementDatabaseUtilities.connectToDatabase(databaseModel, query, "SELECT", data).contains("true");
    }

    /**
     * @author Anthony Scheeres
     */
    public boolean checkForSuperUser(int userId){
        String query = String.format("SELECT user_role FROM %s WHERE user_id = ?", tableName);
        ArrayList data = new ArrayList(Arrays.asList(Integer.toString(userId)));

        return preparedStatementDatabaseUtilities.connectToDatabase(databaseModel, query, "SELECT", data).contains("SUPERUSER");
    }

    /**
     * @author Anthony Scheeres
     */
    public String userIDtoUsername(String userID) {
        PreparedStatementDatabaseUtilities preparedStatementDatabaseUtilities = new PreparedStatementDatabaseUtilities();
        ArrayList data = new ArrayList(Arrays.asList(userID));
        String query = String.format("SELECT username, user_id FROM %s WHERE user_id = ?", tableName);
        try {
            return preparedStatementDatabaseUtilities.connectToDatabase(databaseModel, query, "SELECT" ,data);
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
        return ResponseR.fail.toString();
    }
}
  
