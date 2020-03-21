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

    public String createUrl(int portNumber, String databaseName, String hostName) {
        return String.format("jdbc:postgresql://%s:%s/%s", hostName, portNumber, databaseName);
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
}
