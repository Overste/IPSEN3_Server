package nl.ipsen3server.dao;

import nl.ipsen3server.models.AccountModel;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class made specificly for checking user permissions
 */
public class AuthenticationDAO {

    String tableName = "application_users";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();

    /**
     * @author Anthony Scheeres
     */
    private void givePermission(AccountModel u, Enum e) throws Exception {
        PreparedStatmentDatabaseUtilities databaseController = new PreparedStatmentDatabaseUtilities();
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
        return preparedStatmentDatabaseUtilities.connectToDatabase(databaseModel, query, "SELECT", data).contains("true");
    }


    public boolean checkForSuperUser(int userId){
        String query = String.format("SELECT user_role FROM %s WHERE user_id = ?", tableName);
        ArrayList data = new ArrayList(Arrays.asList(Integer.toString(userId)));

        return preparedStatmentDatabaseUtilities.connectToDatabase(databaseModel, query, "SELECT", data).contains("SUPERUSER");
    }


    /**
     * @author Anthony Scheeres
     */
    public String userIDtoUsername(String userID) {
        PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();
        ArrayList data = new ArrayList(Arrays.asList(userID));
        String query = String.format("SELECT username, user_id FROM %s WHERE user_id = ?", tableName);
        try {
            return preparedStatmentDatabaseUtilities.connectToDatabase(databaseModel, query, "SELECT" ,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.fail.toString();
    }
}
  
