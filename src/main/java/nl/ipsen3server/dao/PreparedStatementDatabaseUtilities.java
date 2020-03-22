package nl.ipsen3server.dao;

import nl.ipsen3server.controllers.LoggerController;
import nl.ipsen3server.models.DatabaseModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreparedStatementDatabaseUtilities {
	 private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());

    /**
     * Call this method to execute a query. This method accepts a query type and calls for the desired method.
     *
     * @author AnthonySchuijlenburg
     *
     * @param databaseModel DatabaseModel
     * @param query STRING query ("SELECT * FROM tablename")
     * @param queryType string SELECT, UPDATE, INSERT, DELETE
     * @param data the data that needs to be filled into the prepared statement
     * @return returns a String(JSON) a from database
     */
    public String connectToDatabase(DatabaseModel databaseModel, String query, String queryType, ArrayList<String> data){
        String result = null;
        List<String> updateQueries = new ArrayList<>(Arrays.asList("INSERT", "UPDATE", "DELETE"));

        try {
            if(queryType.equals("SELECT")) {
                result = executeQuery(databaseModel, query, data);
            } else if (updateQueries.contains(queryType)){
                result = executeUpdate(databaseModel, query, data);
            }
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
        return result;
    }


    /**
     * Executes a SELECT statement and returns the result in a String format
     *
     * @author AnthonySchuijlenburg
     *
     * @param databaseModel DatabaseModel
     * @param query STRING query ("SELECT * FROM tablename")
     * @param data the data that needs to be filled into the prepared statement, needs to be all strings!
     * @return String result of entered query
     */
    private String executeQuery(DatabaseModel databaseModel, String query, ArrayList<String> data){
        String username = databaseModel.getUsername();
        String password = databaseModel.getPassword();
        int portNumber = databaseModel.getPortNumber();
        String databaseName = databaseModel.getDatabaseName();
        String hostName = databaseModel.getHostName();

        String url = createUrl(portNumber, databaseName, hostName);
        String resultsInJson = null;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // if data is empty the queries has NO WHERE
            if (!data.isEmpty()) {
                for(int i = 0; i < data.size(); i++){
                    if (isNumeric(data.get(i))) {
                        preparedStatement.setInt(i + 1, Integer.parseInt(data.get(i)));
                    } else {
                        preparedStatement.setString(i + 1, data.get(i));
                    }
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            JsonConverterUtilities jsonConverter = new JsonConverterUtilities();
            resultsInJson = jsonConverter.convertToJSON(resultSet).toString();

        } catch (SQLException e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
        return resultsInJson;
    }

    /**
     * Executes and update request (DELETE, UPDATE, INSERT), inserts it into the database
     *
     * @author AnthonySchuijlenburg
     *
     * @param databaseModel DatabaseModel
     * @param query STRING query ("SELECT * FROM tablename")
     * @param data the data that needs to be filled into the prepared statement, needs to be all strings!
     * @return Nothing
     */
    private String executeUpdate(DatabaseModel databaseModel, String query, ArrayList<String> data){
        String result = null;
        String username = databaseModel.getUsername();
        String password = databaseModel.getPassword();
        int portNumber = databaseModel.getPortNumber();
        String databaseName = databaseModel.getDatabaseName();
        String hostName = databaseModel.getHostName();

        String url = createUrl(portNumber, databaseName, hostName);

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for(int i = 0; i < data.size(); i++){
                if (isNumeric(data.get(i))) {
                    preparedStatement.setInt(i + 1, Integer.parseInt(data.get(i)));
                } else {
                    preparedStatement.setString(i + 1, data.get(i));
                }
            }

            int response = preparedStatement.executeUpdate();
            if(response == 1 || response == 2) {
                result = "Succes";
            }

        } catch (SQLException e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
        return result;
    }

    private String createUrl(int portNumber, String databaseName, String hostName) {
        return String.format("jdbc:postgresql://%s:%s/%s", hostName, portNumber, databaseName);
    }

    /**
     * @author Anthony Scheeres
     * @return
     */
    //use a database object to connect to database and perform a query
    public String connectDatabaseJson(
        DatabaseModel databaseModel,
        String query,
        List < String > values,
        boolean isUpdate
    ) throws Exception {
        return connectToDatabaseJson(
            databaseModel.getUsername(),
            databaseModel.getPassword(),
            databaseModel.getPortNumber(),
            databaseModel.getDatabaseName(),
            databaseModel.getHostName(),
            query,
            values,
            isUpdate
        );
    }

    /**
     * @author Anthony Scheeres
     */
    //use a database object to connect to database and perform a query
    public HashMap < String, List < String >> connectDatabaseHashmap(
        DatabaseModel databaseModel,
        String query,
        ArrayList<String> values
    ) {
        return connectToDatabaseHashmap(
            databaseModel.getUsername(),
            databaseModel.getPassword(),
            databaseModel.getPortNumber(),
            databaseModel.getDatabaseName(),
            databaseModel.getHostName(),
            query,
            values
        );
    }

    /**
     * @author Anthony Scheeres
     */
    private String connectToDatabaseJson(
        String username,
        String password, int portNumber,
        String databaseName,
        String hostName,
        String query,
        List<String> values,
        boolean isUpdate
    ) throws Exception {
        String result = null;
        DatabaseUtilities dataUtilities = new DatabaseUtilities();
        String url = dataUtilities.createUrl(portNumber, databaseName, hostName);
        // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within 
        // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
        try  {
            //"Java JDBC PostgreSQL: " + databaseName);
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int counter = 0;
            for (int index = 0; index < values.size(); index++) {
                counter = index + 1;
                if (isNumeric(values.get(index))) {
                    pstmt.setInt(counter, Integer.parseInt(values.get(index)));
                } else {
                    pstmt.setString(counter, values.get(index));
                }
            }

            if(isUpdate){
                pstmt.executeUpdate();
                result = "Update was succecfull";
            }else{
                ResultSet r = pstmt.executeQuery();
                JsonConverterUtilities jsonConverter = new JsonConverterUtilities();
                result= jsonConverter.convertToJSON(r).toString();
                connection.close();
                pstmt.close();
            }

        } catch (SQLException err) {
            //"Connection failure.");
            err.printStackTrace();
        }
        return result;
    }


    /**
     * @author Anthony Scheeres
     */
    private HashMap < String, List < String >> connectToDatabaseHashmap(
        String username,
        String password, int portNumber,
        String databaseName,
        String hostName,
        String query,
        ArrayList<String> values
    ) {
        HashMap<String, List<String>> result = null;
        DatabaseUtilities dUtilities = new DatabaseUtilities();
        String url = dUtilities.createUrl(portNumber, databaseName, hostName);
        // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within 
        // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            //"Java JDBC PostgreSQL: " + databaseName);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int counter = 0;

            if (!values.isEmpty()) {
                for (int index = 0; index < values.size(); index++) {
                    counter = index + 1;
                    if (isNumeric(values.get(index))) {
                        pstmt.setInt(counter, Integer.parseInt(values.get(index)));
                    } else {
                        pstmt.setString(counter, values.get(index));
                    }
                }
            }

            ResultSet r = pstmt.executeQuery();
            DatabaseUtilities g = new DatabaseUtilities();
            HashMap < String, List < String >> hashmap = g.getTableContents(r);
            connection.close();
            result = hashmap;
        } catch (SQLException err) {
            //"Connection failure.");
            err.printStackTrace();
        }
        return result;
    }


    /**
     * @author Anthony Scheeres
     */
    public static boolean isNumeric(String strNum) {
        try {
            double integer = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
}