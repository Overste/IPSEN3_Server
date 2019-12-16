<<<<<<< HEAD:src/main/java/nl/iipsen2server/dao/ExperimentDAO.java
package main.java.nl.iipsen2server.dao;

import main.java.nl.iipsen2server.models.DataModel;
import main.java.nl.iipsen2server.models.DatabaseModel;
import main.java.nl.iipsen2server.models.ExperimentModel;
import main.java.nl.iipsen2server.models.ExperimentModel2;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jesse Poleij, Anthony Schuijlenburg
 *
 */
public class ExperimentDAO {
    private String tableName = "experiments";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    private PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();


/**
 * @author Jesse Poleij
 */
    public void deleteExperiment(ExperimentModel project) {
        String query = String.format("DELETE FROM %s WHERE id = ?;", tableName);

        List< String > arrayList = new ArrayList<>();
        arrayList.add(Long.toString(project.getId()));
        try {
            preparedStatmentDatabaseUtilities.connectDatabaseJson(databaseModel, query, arrayList, false);
        } catch (Exception e) { e.printStackTrace(); }
    }


    /**
     * @author AnthonySchuijleburg
     */
    public String showExperiments(){
    	
    	
    	
        String query = String.format("SELECT * FROM %s;", tableName);
        
        String json = ConnectToDatabase(query);
        System.out.println(json);
        return json;
    }


    /**
     * @author AnthonySchuijleburg
     */
    public String showExperiment(int id){
        String query = String.format("SELECT * FROM %s WHERE experiment_id = %d;", tableName, id);
        return ConnectToDatabase(query);
    }


    /**
     * @author AnthonySchuijleburg
     */
    private String ConnectToDatabase(String url){
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();
        String returnQuery = null;
        try {
            returnQuery = databaseUtilities.connectThisDatabase2(databaseModel, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnQuery;
    }


    /**
     * @author Anthony Scheeres
     */
    public String showAllExperimentHashmap() throws Exception {
        String query = String.format("select * from %s;", tableName);
        DatabaseUtilities d = new DatabaseUtilities();
        return d.connectThisDatabase(databaseModel, query).toString();
    }


    /**
     * @author Anthony Scheeres
     */
    public String showAllExperimentJson() throws Exception {
        String query = String.format("select * from %s;", tableName);
        DatabaseUtilities d = new DatabaseUtilities();
        return d.connectThisDatabase2(databaseModel, query);
    }


    /**
     *@author Cyriel van der Raaf
     *Gebruikt een prepared statement om waardes in het tabel projects te plaatsen.
     */
    public void uploadProject(ExperimentModel2 model){
        PreparedStatmentDatabaseUtilities dbUtilities = new PreparedStatmentDatabaseUtilities();

        long id = model.getId();
        Enum status = model.getStatus();

        String query = String.format("INSERT INTO %s (" +
                "" + "?,"               // experiment_id
                + "?,"                  // experiment_name
                + "?,"                  // experiment_leader
                + "?,"                  // experiment_description
                + "?,"                  // organisation
                + "?,"                  // business_owner
                + "?,"                  // experiment_status
                + "?,"                  // experiment_phase
                + "?"                   // inovation_cost
                + "?"                   // money_source
                + ");", tableName);

        //TODO Make the values above align with model

        List <String> project2 = new ArrayList<>();
        project2.add(String.format("%d", id));
        project2.add(model.getName());
        project2.add(model.getExperimentleaders());
        project2.add(model.getDescription());
        project2.add(model.getOrganisations());
        project2.add(model.getBusinessOwners());
        project2.add(String.format("%s", status));
        project2.add(model.getInovationCost());
        project2.add(model.getMoneySource());

        try {
            dbUtilities.connectDatabaseHashmap(databaseModel, query, project2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *@author Cyriel van der Raaf
     *Gebruikt een prepared statement om een project te verwijderen.
     */
    public void deleteExperiment(ExperimentModel2 projectModel){
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();

        String query1 = String.format("DELETE FROM %s WHERE id='&d';", tableName, projectModel.getId());

        try {
            databaseUtilities.connectThisDatabase2(databaseModel, query1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
=======
package nl.ipsen3server.dao;

import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.ExperimentModel;
import nl.ipsen3server.models.ExperimentModel2;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jesse Poleij, Anthony Schuijlenburg
 *
 */
public class ExperimentDAO {
    private String tableName = "experiments";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    private PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();


/**
 * @author Jesse Poleij
 */
    public void deleteExperiment(ExperimentModel project) {
        String query = String.format("DELETE FROM %s WHERE id = ?;", tableName);

        List< String > arrayList = new ArrayList<>();
        arrayList.add(Long.toString(project.getId()));
        try {
            preparedStatmentDatabaseUtilities.connectDatabaseJson(databaseModel, query, arrayList, false);
        } catch (Exception e) { e.printStackTrace(); }
    }


    /**
     * @author AnthonySchuijleburg
     */
    public String showExperiments(){
        String query = String.format("SELECT * FROM %s;", tableName);
        return ConnectToDatabase(query);
    }


    /**
     * @author AnthonySchuijleburg
     */
    public String showExperiment(int id){
        String query = String.format("SELECT * FROM %s WHERE experiment_id = %d;", tableName, id);
        return ConnectToDatabase(query);
    }


    /**
     * @author AnthonySchuijleburg
     */
    private String ConnectToDatabase(String url){
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();
        String returnQuery = null;
        try {
            returnQuery = databaseUtilities.connectThisDatabase2(databaseModel, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnQuery;
    }


    /**
     * @author Anthony Scheeres
     */
    public String showAllExperimentHashmap() throws Exception {
        String query = String.format("select * from %s;", tableName);
        DatabaseUtilities d = new DatabaseUtilities();
        return d.connectThisDatabase(databaseModel, query).toString();
    }


    /**
     * @author Anthony Scheeres
     */
    public String showAllExperimentJson() throws Exception {
        String query = String.format("select * from %s;", tableName);
        DatabaseUtilities d = new DatabaseUtilities();
        return d.connectThisDatabase2(databaseModel, query);
    }


    /**
     *@author Cyriel van der Raaf
     *Gebruikt een prepared statement om waardes in het tabel projects te plaatsen.
     */
    public void uploadProject(ExperimentModel2 model){
        PreparedStatmentDatabaseUtilities dbUtilities = new PreparedStatmentDatabaseUtilities();

        long id = model.getId();
        Enum status = model.getStatus();

        String query = String.format("INSERT INTO %s (" +
                "" + "?,"               // experiment_id
                + "?,"                  // experiment_name
                + "?,"                  // experiment_leader
                + "?,"                  // experiment_description
                + "?,"                  // organisation
                + "?,"                  // business_owner
                + "?,"                  // experiment_status
                + "?,"                  // experiment_phase
                + "?"                   // inovation_cost
                + "?"                   // money_source
                + ");", tableName);

        //TODO Make the values above align with model

        List <String> project2 = new ArrayList<>();
        project2.add(String.format("%d", id));
        project2.add(model.getName());
        project2.add(model.getExperimentleaders());
        project2.add(model.getDescription());
        project2.add(model.getOrganisations());
        project2.add(model.getBusinessOwners());
        project2.add(String.format("%s", status));
        project2.add(model.getInovationCost());
        project2.add(model.getMoneySource());

        try {
            dbUtilities.connectDatabaseHashmap(databaseModel, query, project2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *@author Cyriel van der Raaf
     *Gebruikt een prepared statement om een project te verwijderen.
     */
    public void deleteExperiment(ExperimentModel2 projectModel){
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();

        String query1 = String.format("DELETE FROM %s WHERE id='&d';", tableName, projectModel.getId());

        try {
            databaseUtilities.connectThisDatabase2(databaseModel, query1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
>>>>>>> master:src/main/java/nl/ipsen3server/dao/ExperimentDAO.java
