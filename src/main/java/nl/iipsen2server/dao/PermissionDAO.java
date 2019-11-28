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
     * @return
     * @author Anthony Scheeres
     */
    public boolean giveRead(String username) {
        String query2 = String.format("select permission from %s where username=?;", tableName);
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
        String query2 = String.format("select permission from %s where username=?;", tableName);
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
        String query2 = "select permission from app_user where username=?;";
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
        String query2 = String.format("UPDATE app_user SET permission = array_append(permission,'%s') WHERE username = ?;", e);
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
