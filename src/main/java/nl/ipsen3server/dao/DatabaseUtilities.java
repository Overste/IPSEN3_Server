package nl.ipsen3server.dao;

import java.util.logging.Level;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import nl.ipsen3server.controllers.LoggerController;
import nl.ipsen3server.models.DatabaseModel;

public class DatabaseUtilities {
	 private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());

    /**
     * Call this method to execute a query. This method accepts a query type and calls for the desired method.
     *
     * @author AnthonySchuijlenburg
     *
     * @param databaseModel DatabaseModel
     * @param query STRING query ("SELECT * FROM tablename")
     * @param queryType string SELECT, UPDATE, INSERT, DELETE
     * @return returns a String(JSON) a from database
     */
    public String connectToDatabase(DatabaseModel databaseModel, String query, String queryType){

        String result = null;
        List<String> updateQueries = new ArrayList<>(Arrays.asList("INSERT", "UPDATE", "DELETE"));

        try {
            if(queryType.equals("SELECT")) {
                result = executeQuery(databaseModel, query);
            } else if (updateQueries.contains(queryType)){
                executeUpdate(databaseModel, query);
            }
        } catch (Exception exception) {
             LOGGER.log(Level.SEVERE, "Error occur", exception);
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
     * @return String result of entered query
     */
    public String executeQuery(DatabaseModel databaseModel, String query){
        String username = databaseModel.getUsername();
        String password = databaseModel.getPassword();
        int portNumber = databaseModel.getPortNumber();
        String databaseName = databaseModel.getDatabaseName();
        String hostName = databaseModel.getHostName();

        String url = createUrl(portNumber, databaseName, hostName);
        String resultsInJson = null;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            JsonConverterUtilities jsonConverter = new JsonConverterUtilities();
            resultsInJson = jsonConverter.convertToJSON(resultSet).toString();

        } catch (Exception e) {
            e.printStackTrace();
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
     */
    public void executeUpdate(DatabaseModel databaseModel, String query){
        String username = databaseModel.getUsername();
        String password = databaseModel.getPassword();
        int portNumber = databaseModel.getPortNumber();
        String databaseName = databaseModel.getDatabaseName();
        String hostName = databaseModel.getHostName();

        String url = createUrl(portNumber, databaseName, hostName);

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException e) {
            //query);
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
    }

    /**
     * @author Anthony Scheeres
     */
    // potenially returns all data from an table added an methode that returns all column values in an 2d array!!
    public HashMap < String, List < String >> getTableContents(ResultSet resultSet) {
        HashMap < String, List < String >> hashmap = new HashMap<>();
        List < List < String >> array = new ArrayList<>();
        List < String > singleArray;
        singleArray = getColumnNames(resultSet);
        //trying to fit a table in a variable using 2d array lists
        try {
            for (int i = 0; i < singleArray.size(); i++) {
                array.add(new ArrayList<>());
            }
            while (resultSet.next()) {
                for (int index = 0; index < singleArray.size(); index++) {
                    array.get(index).add((resultSet.getString(singleArray.get(index))));
                }
            }
            for (int index = 0; index < singleArray.size(); index++) {
                hashmap.put(singleArray.get(index), array.get(index));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return hashmap;
    }

    /**
     * @author Anthony Scheeres
     * @return DatabaseConnector
     */
    //use a database object to connect to database and perform a query
    public HashMap < String, List < String >> connectThisDatabase(DatabaseModel databaseModel, String query) {
        return connectToDatabase2(
            databaseModel.getUsername(),
            databaseModel.getPassword(),
            databaseModel.getPortNumber(),
            databaseModel.getDatabaseName(),
            databaseModel.getHostName(),
            query
        );
    }

    /**
     * @author Anthony Scheeres
     * @return Databaseconnection
     * @throws Exception SQLException
     */
    //use a database object to connect to database and perform a query
    public String connectThisDatabase2(DatabaseModel databaseModel, String query) throws Exception {
        return connectToDatabaseOld(
            databaseModel.getUsername(),
            databaseModel.getPassword(),
            databaseModel.getPortNumber(),
            databaseModel.getDatabaseName(),
            databaseModel.getHostName(),
            query
        );
    }

    public String createUrl(int portNumber, String databaseName, String hostName) {
        return String.format("jdbc:postgresql://%s:%s/%s", hostName, portNumber, databaseName);
    }

    /**
     * @author Anthony Scheeres
     * @throws Exception SQLException
     */
    public String connectToDatabaseOld(
        String username,
        String password,
        int portNumber,
        String databaseName,
        String hostName,
        String query
    ) throws Exception {
        String result = null;
        String url = createUrl(portNumber, databaseName, hostName);
        HashMap < String, List < String >> hashmap = new HashMap<>();
        // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within
        // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
        //     Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            //"Java JDBC PostgreSQL: " + databaseName);
            ResultSet resultSet = this.enterQuery(connection, query);
            JsonConverterUtilities jsonConverer = new JsonConverterUtilities();
            String json = jsonConverer.convertToJSON(resultSet).toString();
            connection.close();
            result = json;

        } catch (SQLException err) {
            //"Connection failure.");
            err.printStackTrace();
        }
        return result;
    }

    /**
     * @author Anthony Scheeres
     */
    private HashMap < String, List < String >> connectToDatabase2(
        String username,
        String password,
        int portNumber,
        String databaseName,
        String hostName,
        String query
    ) {
        HashMap<String, List<String>> result = null;
        String url = createUrl(portNumber, databaseName, hostName);
        HashMap < String, List < String >> e = new HashMap<>();
        // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within 
        // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
        //     Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            //"Java JDBC PostgreSQL: " + databaseName);
            ResultSet resultSet = this.enterQuery(connection, query);
            HashMap < String, List < String >> hashmap = getTableContents(resultSet);
            connection.close();
            result =  hashmap;
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return result;
    }

    /**
     * @author Anthony Scheeres
     */
    //connect to postgres database 
    private List < String > getColumnNames(ResultSet resultSet) {
        List < String > columnNames = new ArrayList<>();
        try {
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();
            for (int index = 1; index < numberOfColumns + 1; index++) {
                String columnName;
                columnName = rsMetaData.getColumnName(index);
                columnNames.add(columnName);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
        return columnNames;
    }

    /**
     * @author Anthony Scheeres
     */
    // this methode can be used to insert an query
    private ResultSet enterQuery(Connection connection, String query) {
        Statement statement;
        ResultSet result = null;
        //query);
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {

             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
            return result;
    }

    /**
     * @author Anthony Scheeres
     */
    // this methode can be used to insert an query
    public int enterUpdate(Connection connection, String query) {
        Statement statement;
        try {
            statement = connection.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
            return 0;
        }
    }
}
