package nl.ipsen3server.dao;

import nl.ipsen3server.models.DatabaseModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PreparedStatmentDatabaseUtilities {

  
    
    /**
    *
    * @author Anthony Scheeres
    * @return  
    *
    */
   //use a database object to connect to database and perform a query
   public String connectDatabaseJson(
   		DatabaseModel databaseModel, 
   		String query, 
   		List < String > values
   		) throws Exception {
	   
	   boolean isUpdate = false;
	   
	   return  connectDatabaseJson(databaseModel, query, values,isUpdate );
   }
   
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
=======
  
    
 

>>>>>>> Stashed changes
    public String connectToDatabase(DatabaseModel databaseModel, String query, String queryType, ArrayList<String> data){

        String result = null;
        List<String> updateQueries = new ArrayList<>(Arrays.asList("INSERT", "UPDATE", "DELETE"));

        try {
            if(queryType.equals("SELECT")) {
                result = executeQuery(databaseModel, query, data);
            } else if (updateQueries.contains(queryType)){
                executeUpdate(databaseModel, query, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

            for(int i = 0; i < data.size(); i++){
                if (isNumeric(data.get(i))) {
                    preparedStatement.setInt(i + 1, Integer.parseInt(data.get(i)));
                } else {
                    preparedStatement.setString(i + 1, data.get(i));
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            JsonConverterUtilities jsonConverter = new JsonConverterUtilities();
            resultsInJson = jsonConverter.convertToJSON(resultSet).toString();

        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(query);
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
     * @param data the data that needs to be filled into the prepared statement, needs to be all strings!
     * @return Nothing
     */
    private void executeUpdate(DatabaseModel databaseModel, String query, ArrayList<String> data){
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

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(query);
            e.printStackTrace();
        }
    }

    private String createUrl(int portNumber, String databaseName, String hostName) {
        return String.format("jdbc:postgresql://%s:%s/%s", hostName, portNumber, databaseName);
    }


  


    /**
     * @author Anthony Scheeres
     * @return
     */
    //use a database object to connect to database and perform a query
    public HashMap < String, List < String >> connectDatabaseHashmap(
    		DatabaseModel databaseModel, 
    		String query, 
    		List < String > values) 
    				throws Exception {

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
     * @return 
     * @throws Exception
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
        //     Class.forName("org.postgresql.Driver"); 

        try  {
            System.out.println("Java JDBC PostgreSQL: " + databaseName);
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
                ;
            }

        } catch (SQLException err) {
            System.out.println("Connection failure.");
            err.printStackTrace();
        }
        return result;
    }


    /**
     * @author Anthony Scheeres
     * @return 
     * @throws Exception
     */
    private HashMap < String, List < String >> connectToDatabaseHashmap(
            String username,
            String password, int portNumber,
            String databaseName,
            String hostName,
            String query,
            List<String> values
    ) throws Exception {
    	  HashMap<String, List<String>> result = null;
        DatabaseUtilities dUtilities = new DatabaseUtilities();
        String url = dUtilities.createUrl(portNumber, databaseName, hostName);
        // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within 
        // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
        //     Class.forName("org.postgresql.Driver"); 
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Java JDBC PostgreSQL: " + databaseName);
            PreparedStatement pstmt = connection.prepareStatement(query);
            int counter = 0;
            for (int index = 0; index < values.size(); index++) {
                counter = index + 1;
                System.out.println(values.get(index));
                if (isNumeric(values.get(index))) {
                    pstmt.setInt(counter, Integer.parseInt(values.get(index)));
                } else {
                    pstmt.setString(counter, values.get(index));
                }
            }
            System.out.println(pstmt);
            ResultSet r = pstmt.executeQuery();
            DatabaseUtilities g = new DatabaseUtilities();
            HashMap < String, List < String >> hashmap = g.getTableContents(r);
            connection.close();
            result = hashmap;
        } catch (SQLException err) {
            System.out.println("Connection failure.");
            err.printStackTrace();
        }
        return result;
    }


    /**
     * @author Anthony Scheeres
     * @return 
     * @throws Exception
     */
    private static boolean isNumeric(String strNum) {
        try {
            double integer = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
}