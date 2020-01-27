package nl.ipsen3server.dao;

import java.util.logging.Level;
import nl.ipsen3server.controlllers.LoggerController;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.ExperimentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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



    public String showExperimentPhase(List[] phase){
        String query = String.format("UPDATE %s SET experiment_phase = ? WHERE experiment_id = ?)", tableName);
        ArrayList<String> data = new ArrayList<String>();
        data.add(phase[0].toString());
        data.add(phase[1].toString());

        return connectToDatabase(query, "UPDATE", data);
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
        Enum status = model.getStatus();

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

        //TODO Make the values above align with model

        ArrayList<String> createProject = new ArrayList<>();
        createProject.add(String.format("%d", id));
        createProject.add(model.getName());
        createProject.add(model.getExperimentleaders());
        createProject.add(model.getDescription());
        createProject.add(model.getOrganisations());
        createProject.add(model.getBusinessOwners());
        createProject.add(String.format("%s", model.getStatussen()));
        createProject.add(model.getFasens());
        createProject.add(model.getInovationCost());
        createProject.add(model.getMoneySource());

        try {
            dbUtilities.connectToDatabase(databaseModel, query, "INSERT", createProject);
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
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
}
