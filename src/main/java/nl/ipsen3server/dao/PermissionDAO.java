<<<<<<< HEAD:src/main/java/nl/iipsen2server/dao/PermissionDAO.java
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
=======
package nl.ipsen3server.dao;

import nl.ipsen3server.controlllers.AuthenticationController;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.Permission;

import java.util.ArrayList;
import java.util.List;

public class PermissionDAO {

    String tableName = "application_users";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    AuthenticationController autenticationController = new AuthenticationController();
    private UserDAO userDatabase = new UserDAO();


    /**
     * @return
     * @author Anthony Scheeres
     */
    public boolean giveRead(String username) {
        String query2 = String.format("select has_read from %s where username=?;", tableName);
        Enum permission = Permission.READ;
        if (!userDatabase.hasPermission(permission.toString(), username, query2)) {
            try {
                givePermission(username, permission);
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean giveWrite(String username) {
        String query2 = String.format("select has_write from %s where username=?;", tableName);
        Enum permission = Permission.WRITE;
        if (!userDatabase.hasPermission(permission.toString(), username, query2)) {
            try {
                givePermission(username, permission);
                return true;
            } catch (Exception e) {

            }

        }
        return false;
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean giveDelete(String username) {
        String query2 = String.format("select has_delete from app_user where username=?;", tableName);
        Enum permission = Permission.DELETE;
        if (!userDatabase.hasPermission(permission.toString(), username, query2)) {
            try {
                givePermission(username, permission);
                return true;
            } catch (Exception e) {
            }

        }
        return false;
    }


    /**
     * @author Anthony Scheeres
     */
    private void givePermission(String u, Enum e) throws Exception {
        PreparedStatmentDatabaseUtilities databaseController = new PreparedStatmentDatabaseUtilities();
        List<String> list = new ArrayList<String>();
        String query2 = String.format("UPDATE application_user SET %s = true WHERE username = ?;", "has_"+e.toString().toLowerCase(),e);
        list.add(u);
        databaseController.connectDatabaseJson(databaseModel, query2, list, false);
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean hasEnumHandeler(long employeeId, String permission) {
        String query2 = "select permission from app_user where user_id=?;";
        return userDatabase.hasPermission(permission, Long.toString(employeeId), query2);
    }
}
>>>>>>> master:src/main/java/nl/ipsen3server/dao/PermissionDAO.java
