package nl.ipsen3server.dao;

import java.util.logging.Level;
import nl.ipsen3server.controllers.LoggerController;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.BoxModel;
import nl.ipsen3server.models.ExperimentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;


/**
 * @author Jesse Poleij, Anthony Schuijlenburg, Cyriel van der Raaf
 */
public class ExperimentDAO{
    private String tableName = "experiments";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);



	 private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());
    /**
     * Deletes the experiment, is only called after permission checks!
     *
     * @author Jesse Poleij, AnthonySchuijlenburg
     *
     * @param experimentId The experimentId of the experiment that needs to be deleted
     * @return The status of the deleting attempt
     */
    public String deleteExperiment(int experimentId) {
        String query = String.format("DELETE FROM %s WHERE experiment_id = ?;", tableName, experimentId);
        ArrayList<String> data = new ArrayList<>(Arrays.asList(Integer.toString(experimentId)));

        try {
            connectToDatabase(query, "DELETE", data);
            return "succes";
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
            return "Was not able to connect to database";
        }
    }


    /**
     * Requests all experiments from the database, is only called after permission checks!
     *
     * @author AnthonySchuijlenburg
     *
     * @return a JSON of all the the experiments in the database
     */
    public String showExperiments() {
        String query = String.format("SELECT * FROM %s;", tableName);
        return connectToDatabase(query, "SELECT");
    }


    /**
     * Requests a single specific experiment from the database, is only called after permission checks!
     *
     * @author AnthonySchuijlenburg
     *
     * @param id the id of the experiment that needs to be showed
     * @return the
     */
    public String showExperiment(int id) {
        String query = String.format("SELECT * FROM %s WHERE experiment_id = ?;", tableName);
        ArrayList<String> data = new ArrayList<>(Arrays.asList(Integer.toString(id)));
        return connectToDatabase(query, "SELECT", data);
    }


    /**
     * Updates a single experiment phase value from the database.
     *
     * @author CyrielvdRaaf
     *
     * @param phaseChange
     * @return a query statement that updates the database.
     */
    public String showExperimentPhase(BoxModel phaseChange){
        String query = String.format("UPDATE %s SET experiment_phase = '%s' WHERE experiment_id = %s;", tableName, phaseChange.getPhase(), phaseChange.getId());

        return  connectToDatabase(query, "UPDATE");
    }

    /**
     * Makes a local reference point for talking with PreparedStatementDatabaseUtilities.
     *
     * @author AnthonySchuijlenburg
     *
     * @param query the query that needs to be executes
     * @param queryType the type of that Query (SELECT, INSERT, UPDATE, DELETE)
     * @param data Arraylist of strings with data that needs to be filled into the prepared statement
     * @return the resultSet of the query. Returns an empty string if the query type is not SELECT
     */
    private String connectToDatabase(String query, String queryType, ArrayList<String> data) {
        PreparedStatmentDatabaseUtilities preparedStatmentDatabaseUtilities = new PreparedStatmentDatabaseUtilities();
        String returnQuery = null;
        try {
            returnQuery = preparedStatmentDatabaseUtilities.connectToDatabase(databaseModel, query, queryType, data);
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
        return returnQuery;
    }


    /**
     * Makes a local reference point for talking with DatabaseUtilities.
     *
     * @author AnthonySchuijlenburg
     *
     * @param query the query that needs to be executes
     * @param queryType the type of that Query (SELECT, INSERT, UPDATE, DELETE)
     * @return the resultSet of the query. Returns an empty string if the query type is not SELECT
     */
    private String connectToDatabase(String query, String queryType) {
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();
        String returnQuery = null;
        try {
            returnQuery = databaseUtilities.connectToDatabase(databaseModel, query, queryType);
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
        return returnQuery;
    }


    /**
     * Uses a prepared statement to upload an experiment to the database
     *
     *
     *
     * @param model
     */
    public void uploadExperiment(ExperimentModel model) {
        PreparedStatmentDatabaseUtilities dbUtilities = new PreparedStatmentDatabaseUtilities();

        long id = model.getId();

        String query = String.format("INSERT INTO %s VALUES (" +
                "" + "?,"               // experiment_id
                + "?,"                  // experiment_name
                + "?,"                  // experiment_leader
                + "?,"                  // experiment_description
                + "?,"                  // organisation
                + "?,"                  // business_owner
                + "?" + "::experiment_status,"                 // experiment_status
                + "?" + "::experiment_phase,"                 // experiment_phase
                + "?,"                   // inovation_cost
                + "?"                   // money_source
                + ");", tableName);

        System.out.println(query);

        //TODO Make the values above align with model

        ArrayList<String> createProject = createExperimentList(id, model);

        try {
            dbUtilities.connectToDatabase(databaseModel, query, "INSERT", createProject);
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Create Project Error occur", e);
        }
    }


    /**
     * @author Anthony Scheeres
     */
    public String showAllExperimentHashmap() throws Exception {
        String query = String.format("SELECT * FROM %s;", tableName);
        DatabaseUtilities d = new DatabaseUtilities();
        return d.connectThisDatabase(databaseModel, query).toString();
    }


    /**
     * @author Anthony Scheeres
     */
    public String showAllExperimentJson() throws Exception {
        String query = String.format("SELECT * FROM %s;", tableName);
        DatabaseUtilities d = new DatabaseUtilities();
        return d.connectThisDatabase2(databaseModel, query);
    }

    /**
     * @author Jesse poleij
     * @param model
     */
    public void updateExperiment(ExperimentModel model) {
        PreparedStatmentDatabaseUtilities dbUtilities = new PreparedStatmentDatabaseUtilities();

        long id = model.getId();

        String query = String.format("UPDATE %s set " +
                "experiment_name =" + "?," +
                "experiment_leader =" + "?," +
                "experiment_description =" + "?," +
                "organisation =" + "?," +
                "business_owner =" + "?," +
                "experiment_status =" + "?" + "::experiment_status, " +
                "experiment_phase =" + "?" + "::experiment_phase, " +
                "inovation_cost =" + "?," +
                "money_source =" + "?" +
                "WHERE experiment_id =" + "?" // money_source
                + ";", tableName);

        //TODO Make the values above align with model

        ArrayList<String> updateProject = createExperimentList(id, model);

        try {
            dbUtilities.connectToDatabase(databaseModel, query, "UPDATE", updateProject);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Update project error occur", e);
        }
    }

    private ArrayList<String> createExperimentList(long id, ExperimentModel experimentModel) {

        ArrayList<String> experiment = new ArrayList<>();

        experiment.add(String.format("%d", id));
        experiment.add(experimentModel.getName());
        experiment.add(experimentModel.getExperimentleaders());
        experiment.add(experimentModel.getDescription());
        experiment.add(experimentModel.getOrganisations());
        experiment.add(experimentModel.getBusinessOwners());
        experiment.add(String.format("%s", experimentModel.getStatussen()));
        experiment.add(experimentModel.getFasens());
        experiment.add(experimentModel.getInovationCost());
        experiment.add(experimentModel.getMoneySource());

        return experiment;
    }

}
