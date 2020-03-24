package nl.ipsen3server.dao;

import nl.ipsen3server.controllers.LoggerController;
import nl.ipsen3server.controllers.MailController;
import nl.ipsen3server.controllers.UserController;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private String tableName = "application_users";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    private PreparedStatementDatabaseUtilities preparedStatementDatabaseUtilities = new PreparedStatementDatabaseUtilities();
    private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());

    /**
     * @author Anthony Scheeres
     * get tokens from database
     */
    public HashMap<String, List<String>> getTokens() {
        String query = String.format("SELECT user_id, token FROM %s;", tableName);

        return this.preparedStatementDatabaseUtilities.connectDatabaseHashmap(
            databaseModel,
            query,
            new ArrayList<>()
        );
    }

    /**
     * @author Anthony Scheeres
     */
    public void changeToken(String token, int id) {
        String query = String.format("UPDATE %s SET token = ? WHERE user_id = ?;", tableName);
        ArrayList data = new ArrayList(Arrays.asList(token, Integer.toString(id)));

        try {
            this.preparedStatementDatabaseUtilities.connectToDatabase(databaseModel, query, "UPDATE", data);
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
    }

    /**
     * @author Anthony Scheeres
     */
    public String showUsers() {
        String query = String.format("SELECT * FROM %s ORDER BY username;", tableName);

        return this.preparedStatementDatabaseUtilities.connectToDatabase(
            databaseModel,
            query,
            "SELECT",
            new ArrayList<>()
        );
    }

    /**
     * @author Anthony Scheeres
     */
    public String showOneUserPermission(int user_id) {
        String result = null;
        String query = String.format("SELECT * FROM %s WHERE user_id = ?;", tableName);
        ArrayList data = new ArrayList(Arrays.asList(String.valueOf(user_id)));
        try {
            result = this.preparedStatementDatabaseUtilities.connectToDatabase(
                databaseModel,
                query,
                "SELECT",
                data
            );
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
        return result;
    }

    /**
     * @author Anthony Scheeres
     */
    public HashMap<String, List<String>> getUserInfo() {
        String query = String.format(
            "SELECT " +
            "user_id, " +
            "username, " +
            "password, " +
            "has_read, " +
            "has_write, " +
            "has_delete, " +
            "token FROM %s " +
            "ORDER BY " +
            "user_id;",
            tableName
        );
        return this.preparedStatementDatabaseUtilities.connectDatabaseHashmap(
            databaseModel,
            query,
            new ArrayList<>()
        );
    }

    /**
     * @author Anthony Scheeres
     */
    public HashMap<String, List<String>> getUsers() {
        String query = String.format("select user_id, username from %s;", tableName);

        return this.preparedStatementDatabaseUtilities.connectDatabaseHashmap(
            databaseModel,
            query,
            new ArrayList<>()
        );
    }

    /**
     * @author Anthony Scheeres
     */
    public String insertHandlerUser(HashMap<String, List<String>> e1, UserModel userModel) throws Exception {
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
        this.preparedStatementDatabaseUtilities.connectDatabaseJson(databaseModel, query2, variables, true);
        return token;
    }

    /**
     * @author Anthony Scheeres
     */
    public boolean removeUserModel(String u) {
        boolean succes = false;
        String deletequery = String.format("DELETE FROM %s WHERE username = ?;", tableName);
        ArrayList<String> usernameArray = new ArrayList<>(Arrays.asList(u));

        try {
            String result = this.preparedStatementDatabaseUtilities.connectToDatabase(
                databaseModel,
                deletequery,
                "DELETE",
                usernameArray
            );
            if(result != null) {
                succes = true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occur", e.getMessage());
        }
        return succes;
    }

    public boolean updateUserRole(long id, String role) {
        boolean succes = false;

        String updateQuery = String.format("UPDATE %s\r\n" +
                        "SET user_role=?::user_role WHERE user_id=?", tableName);

        List<String> variables = new ArrayList<>();
        variables.add(role);
        variables.add(String.valueOf(id));

        try {
            this.preparedStatementDatabaseUtilities.connectDatabaseJson(
                databaseModel,
                updateQuery,
                variables,
                true
            );
            succes = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occur", e.getMessage());
        }
        return succes;
    }

    public String getUserRole(int id) {
        String result = null;
        String getQuery = String.format("SELECT user_role FROM %s WHERE user_id=?", tableName);

        ArrayList<String> variables = new ArrayList<>();
        variables.add(String.valueOf(id));

        try {
            result = this.preparedStatementDatabaseUtilities.connectToDatabase(
                databaseModel,
                getQuery,
                "SELECT", variables
            );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occur", e.getMessage());
        }
        return result;
    }
}