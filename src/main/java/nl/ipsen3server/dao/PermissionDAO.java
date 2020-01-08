
package nl.ipsen3server.dao;

import java.util.logging.Level;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.Permission;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class PermissionDAO {
    String tableName = "application_users";
    DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();


    /**
     * @author Anthony Scheeres
     * @throws Exception 
     */
    public void giveRead(String username) throws Exception {
    	   Enum permission = Permission.READ;
           givePermission(username, permission);
    }


    /**
     * @author Anthony Scheeres
     * @throws Exception 
     */
    public void giveWrite(String username) throws Exception {
        Enum permission = Permission.WRITE;
        givePermission(username, permission);
    }
    
    
    /**
     * @author Anthony Scheeres
     * @throws Exception 
     */
    public void giveDelete(String username) throws Exception {
    	   Enum permission = Permission.DELETE;
           givePermission(username, permission);
    }


    /**
     * @author Anthony Scheeres
     */
    private void givePermission(String username, Enum e) throws Exception {
        PreparedStatmentDatabaseUtilities databaseController = new PreparedStatmentDatabaseUtilities();
        ArrayList data = new ArrayList(Arrays.asList(username));
        String query = String.format("UPDATE %s SET %s = true WHERE username = ?;", tableName, "has_"+e.toString().toLowerCase(),e);
        preparedStatmentDatabaseUtilities.connectToDatabase(databaseModel, query, "UPDATE", data);
    }
}
