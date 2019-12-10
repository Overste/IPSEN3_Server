package main.java.nl.iipsen2server.dao;

import main.java.nl.iipsen2server.controlllers.AuthenticationController;
import main.java.nl.iipsen2server.models.DataModel;
import main.java.nl.iipsen2server.models.DatabaseModel;
import main.java.nl.iipsen2server.models.Permission;

import java.util.ArrayList;
import java.util.List;

public class PermissionDAO {

    String tableName = "application_users";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    AuthenticationController autenticationController = new AuthenticationController();
    private UserDAO userDatabase = new UserDAO();


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
    private void givePermission(String u, Enum e) throws Exception {
        PreparedStatmentDatabaseUtilities databaseController = new PreparedStatmentDatabaseUtilities();
        List<String> list = new ArrayList<String>();
        String query2 = String.format("UPDATE application_users SET %s = true WHERE username = ?;", "has_"+e.toString().toLowerCase(),e);
        list.add(u);
        databaseController.connectDatabaseJson(databaseModel, query2, list, false);
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean hasEnumHandeler(long employeeId, String permission) {
        String query2 = "select permission from application_users where user_id=?;";
        return userDatabase.hasPermission(permission, Long.toString(employeeId), query2);
    }
}
