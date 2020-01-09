package nl.ipsen3server.dao;

import nl.ipsen3server.controlllers.MailController;
import nl.ipsen3server.controlllers.UserController;
import nl.ipsen3server.models.AccountModel;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
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
        String query = String.format("SELECT user_id, token FROM %s;", tableName);
        return databaseUtilities.connectThisDatabase(databaseModel, query);
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
            System.out.println(permission1);
            if(permission1.contains("t")) {
               System.out.println("return true");
            	return true;
            }
            
          
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return false;
    }


    /**
     * @author Anthony Scheeres
     */
    public void changeToken(String token, int id) {
        String query = String.format("UPDATE %s SET token = ? WHERE user_id = ?;", tableName);
        ArrayList data = new ArrayList(Arrays.asList(token, Integer.toString(id)));
        PreparedStatmentDatabaseUtilities preparedStatementDatabaseUtilities = new PreparedStatmentDatabaseUtilities();
        try {
            preparedStatementDatabaseUtilities.connectToDatabase(databaseModel, query, "UPDATE", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @author Anthony Scheeres
     */
    public String showUsers() throws Exception {
        String query = String.format("SELECT * FROM %s ORDER BY username;", tableName);
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();
        String json = databaseUtilities.connectToDatabase(databaseModel, query, "SELECT");
        return json;
    }


    /**
     * @author Anthony Scheeres
     */
    public String showOneUserPermission(int user_id) {
        PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();
        String result = null;
        String query = String.format("SELECT * FROM %s WHERE user_id = ?;", tableName);
        ArrayList data = new ArrayList(Arrays.asList(user_id));
        try {
            result = preparedStatmentDatabaseUtilities.connectToDatabase(databaseModel, query, "SELECT", data);
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
        long id = userController.createUserId2(e1.get("user_id"));
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
                "DELETE FROM application_users\r\n" +
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
    public void removeUserModel(String u) {
        PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();
        String deletequery =
                String.format("DELETE FROM %s WHERE username = ?;", tableName);
        ArrayList<String> usernameArray = new ArrayList<>(Arrays.asList(u));

        try {
            preparedStatmentDatabaseUtilities.connectToDatabase(databaseModel, deletequery, "DELETE", usernameArray);
        } catch (Exception e) {
        }
    }


    public boolean updateUserRole(long id, String role) {
        boolean succes = false;

        PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();
        String updateQuery =
                String.format("UPDATE %s\r\n" +
                        "SET user_role=?::user_role WHERE user_id=?", tableName);

        List<String> variables = new ArrayList<>();
        variables.add(role);
        variables.add(String.valueOf(id));

        try {
            preparedStatmentDatabaseUtilities.connectDatabaseJson(databaseModel, updateQuery, variables, true);
            succes = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return succes;
    }

}