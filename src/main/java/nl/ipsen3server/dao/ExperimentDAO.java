package nl.ipsen3server.dao;

import java.util.logging.Level;
import nl.ipsen3server.controllers.LoggerController;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.BoxModel;
import nl.ipsen3server.models.ExperimentModel;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author Jesse Poleij, Anthony Schuijlenburg, Cyriel van der Raaf
 */
public class ExperimentDAO{
    private String tableName = "experiments";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    private PreparedStatementDatabaseUtilities preparedStatementDatabaseUtilities = new PreparedStatementDatabaseUtilities();
    private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());

    /**
     * Deletes the experiment, is only called after permission checks!
     *
     * @author Jesse Poleij, AnthonySchuijlenburg
     *
     * @param experimentId The experimentId of the experiment that needs to be deleted
     * @return The status of the deleting attempt
     */
    public Response deleteExperiment(int experimentId) {
        String query = String.format("DELETE FROM %s WHERE experiment_id = ?;", tableName, experimentId);
        ArrayList<String> data = new ArrayList<>(Arrays.asList(Integer.toString(experimentId)));

        try {
            connectToDatabase(query, "DELETE", data);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occur", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
     * @author Anthony Schuijlenburg
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
     * @author Cyriel vd Raaf
     *
     * @param phaseChange phase
     * @return a query statement that updates the database.
     */
    public String showExperimentPhase(BoxModel phaseChange){
        String query = String.format(
            "UPDATE %s SET experiment_phase = '%s' " +
            "WHERE experiment_id = %s;",
            tableName,
            phaseChange.getPhase(),
            phaseChange.getId()
        );

        return connectToDatabase(query, "UPDATE");
    }

    /**
     * Makes a local reference point for talking with PreparedStatementDatabaseUtilities.
     *
     * @author Anthony Schuijlenburg
     *
     * @param query the query that needs to be executes
     * @param queryType the type of that Query (SELECT, INSERT, UPDATE, DELETE)
     * @param data Arraylist of strings with data that needs to be filled into the prepared statement
     * @return the resultSet of the query. Returns an empty string if the query type is not SELECT
     */
    private String connectToDatabase(String query, String queryType, ArrayList<String> data) {
        String returnQuery = null;
        try {
            returnQuery = this.preparedStatementDatabaseUtilities.connectToDatabase(databaseModel, query, queryType, data);
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
        return returnQuery;
    }

    /**
     * Makes a local reference point for talking with DatabaseUtilities.
     *
     * @author Anthony Schuijlenburg
     *
     * @param query the query that needs to be executes
     * @param queryType the type of that Query (SELECT, INSERT, UPDATE, DELETE)
     * @return the resultSet of the query. Returns an empty string if the query type is not SELECT
     */

    private String connectToDatabase(String query, String queryType) {
        String returnQuery = null;
        try {
            returnQuery = this.preparedStatementDatabaseUtilities.connectToDatabase(
                databaseModel,
                query,
                queryType,
                new ArrayList<>()
            );
        } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error occur", e);
        }
        return returnQuery;
    }

    public int getExperimentsCount() {
        String query = String.format("SELECT MAX(experiment_id) FROM %s;", tableName);
        return Integer.parseInt(
            connectToDatabase(query, "SELECT")
                .replaceAll("[^0-9]", "")
        );
    }

    /**
     * Uses a prepared statement to upload an experiment to the database
     * @param model ExperimentModel Object
     */
    public String uploadExperiment(ExperimentModel model) {
        String result = null;
        String query = String.format("" +
            "INSERT INTO %s VALUES ("
                + "?,"                          // experiment_id
                + "?,"                          // experiment_name
                + "?,"                          // experiment_leader
                + "?,"                          // experiment_description
                + "?,"                          // organisation
                + "?,"                          // business_owner
                + "?" + "::experiment_status,"  // experiment_status
                + "?" + "::experiment_phase,"   // experiment_phase
                + "?,"                          // inovation_cost
                + "?"                           // money_source
            + ");",
            tableName
        );
        ArrayList<String> createProject = createExperimentList(model);

        try {
            result = this.preparedStatementDatabaseUtilities.connectToDatabase(
                databaseModel,
                query,
                "INSERT",
                createProject
            );
        } catch (Exception e) {
            e.printStackTrace();
             LOGGER.log(Level.SEVERE, "Create Project Error occur", e);
        }
        return result;
    }

    /**
     * @author Jesse poleij
     * @param model ExperimentModel Object
     */
    public String updateExperiment(ExperimentModel model) {
        long id = model.getId();
        String result = null;

        String query = String.format(
            "UPDATE %s set " +
            "experiment_name =" + "?," +
            "experiment_leader =" + "?," +
            "experiment_description =" + "?," +
            "organisation =" + "?," +
            "business_owner =" + "?," +
            "experiment_status =" + "?" + "::experiment_status, " +
            "experiment_phase =" + "?" + "::experiment_phase, " +
            "inovation_cost =" + "?," +
            "money_source =" + "?" +
            "WHERE experiment_id =" + "?" +
            ";",
            tableName
        );

        ArrayList<String> updateProject = updateExperimentList(id, model);

        try {
            result = this.preparedStatementDatabaseUtilities.connectToDatabase(
                databaseModel,
                query,
                "UPDATE",
                updateProject
            );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Update project error occur", e);
        }
        return result;
    }

    private ArrayList<String> updateExperimentList(long id, ExperimentModel experimentModel) {
        ArrayList<String> experiment = new ArrayList<>();
        experiment.add(experimentModel.getName());
        experiment.add(experimentModel.getExperimentleaders());
        experiment.add(experimentModel.getDescription());
        experiment.add(experimentModel.getOrganisations());
        experiment.add(experimentModel.getBusinessOwners());
        experiment.add(String.format("%s", experimentModel.getStatussen()));
        experiment.add(experimentModel.getFasens());
        experiment.add(experimentModel.getInovationCost());
        experiment.add(experimentModel.getMoneySource());
        experiment.add(String.format("%d", id));

        return experiment;
    }

    private ArrayList<String> createExperimentList(ExperimentModel experimentModel) {
        ArrayList<String> experiment = new ArrayList<>();
        experiment.add(String.format("%d", this.getExperimentsCount() + 1));
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
