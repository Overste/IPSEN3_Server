package main.java.nl.iipsen2server.dao;

import main.java.nl.iipsen2server.controlllers.MailController;
import main.java.nl.iipsen2server.controlllers.UserController;
import main.java.nl.iipsen2server.models.AccountModel;
import main.java.nl.iipsen2server.models.DataModel;
import main.java.nl.iipsen2server.models.DatabaseModel;
import main.java.nl.iipsen2server.models.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserDAO {

    private String tableName = "application_users";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);


    /**
     * @author Anthony Scheeres
     * get tokens from database
     */
    public HashMap<String, List<String>> getTokens() throws Exception {
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();
        String query = String.format("select user_id, token from %s;", tableName);
        return databaseUtilities.connectThisDatabase(databaseModel, query);
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean hasEnumHandeler(long employeeId, String permission) {
        String query2 = String.format("select permission from %s where user_id=?;", tableName);
        UserDAO userDAO = new UserDAO();
        return userDAO.hasPermission(permission, Long.toString(employeeId), query2);
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean hasPermission(String permission, String u, String query2) {
        PreparedStatmentDatabaseUtilities dUtilities = new PreparedStatmentDatabaseUtilities();
        HashMap<String, List<String>> hashMap;
        List<String> array = new ArrayList<String>();
        array.add(u);

        try {
            hashMap = dUtilities.connectDatabaseHashmap(databaseModel, query2, array);
            List<String> permission1 = hashMap.get("has_" + permission.toLowerCase());
            
            
            boolean hasPermission = permission1.contains(null);
            if (hasPermission) {
                return false;
            }
            
            
            if(permission1.contains("t")) {
            	return true;
            }
            
          
            
        } catch (Exception e) {
        }
        return false;
    }


    /**
     * @author Anthony Scheeres
     */
    public void changeToken(String token, int id) {
        String query = String.format(""
                + "UPDATE %s "
                + "SET token = '%s' "
                + "WHERE user_id = %d;", tableName, token, id);
        System.out.println(query);
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();
        try {
            databaseUtilities.connectThisDatabase2(databaseModel, query);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * @author Anthony Scheeres
     */
    public String showUsers() throws Exception {
        String query = String.format(
                "SELECT username, " +
                        "has_read, " +
                        "has_write, " +
                        "has_delete, " +
                        "user_role FROM %s"
                        + " order by username;", tableName);
        DatabaseUtilities d = new DatabaseUtilities();
        return d.connectThisDatabase2(databaseModel, query);
    }


    /**
     * @author Anthony Scheeres
     */
    public String showOneUserPermission(int user_id) {
        PreparedStatmentDatabaseUtilities f = new PreparedStatmentDatabaseUtilities();
        String result = null;
        String query = String.format("SELECT username, " +
                "has_read, " +
                "has_write, " +
                "has_delete, " +
                "user_role, " +
                "FROM %s" +
                "WHERE user_id = %d;", tableName, user_id);
        List<String> usernameArray = new ArrayList<String>();
//        usernameArray.add(accountModel.getUsername());
        try {
            result = f.connectDatabaseJson(databaseModel, query, usernameArray, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @author Anthony Scheeres
     */
    public HashMap<String, List<String>> getUserInfo() throws Exception {
        DatabaseUtilities d = new DatabaseUtilities();
        String query = String.format(
                "SELECT user_id, " +
                        "username, " +
                        "password, " +
                        "has_read, " +
                        "has_write, " +
                        "has_delete, " +
                        "user_role, " +
                        "token FROM %s ORDER BY user_id;", tableName);
        return d.connectThisDatabase(databaseModel, query);
    }


    /**
     * @author Anthony Scheeres
     */
    public HashMap<String, List<String>> getUsers() throws Exception {
        DatabaseUtilities d = new DatabaseUtilities();
        String query = String.format("select user_id, username from %s;", tableName);
        return d.connectThisDatabase(databaseModel, query);
    }


    /**
     * @author Anthony Scheeres
     */
    public String insertHandlerUser(HashMap<String, List<String>> e1, UserModel userModel) throws Exception {
        PreparedStatmentDatabaseUtilities pUtilites = new PreparedStatmentDatabaseUtilities();
        MailController mailController = new MailController();
        UserController userController = new UserController();
        long id = userController.createUserId(e1.get("user_id"));
        String query2 = String.format("INSERT INTO %s (username, password, user_id, email, token) VALUES (" +
                "?," +
                "?," +
                "?," +
                "?," +
                "?" +
                ");", tableName);
        String token = mailController.generateToken();
        List<String> variables = new ArrayList<>();
        variables.add(userModel.getUsername());
        variables.add(userModel.getPassword());
        variables.add(String.format("%d", id));
        variables.add(userModel.getEmail());
        variables.add(token);
        pUtilites.connectDatabaseJson(databaseModel, query2, variables, false);
        return token;
    }


    /**
     * @author Anthony Scheeres
     */
    public void removeUserModel2(AccountModel u) {
        PreparedStatmentDatabaseUtilities f = new PreparedStatmentDatabaseUtilities();
        String query =
                "DELETE FROM app_user\r\n" +
                        "WHERE username = ? ;";
        List<String> f1 = new ArrayList<String>();
        f1.add(u.getUsername());
        try {
            f.connectDatabaseJson(databaseModel, query, f1, false);
        } catch (Exception e) {

        }
    }


    /**
     * @author Anthony Scheeres
     */
    public void removeUserModel(AccountModel u) {
        PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();
        String deletequery =
                String.format("DELETE FROM %s\r\n" +
                        "WHERE username = ?;", tableName);
        List<String> usernameArray = new ArrayList<String>();
        usernameArray.add(u.getUsername());
        try {
            preparedStatmentDatabaseUtilities.connectDatabaseJson(databaseModel, deletequery, usernameArray, false);
        } catch (Exception e) {
        }
    }
}