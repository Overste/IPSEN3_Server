package nl.ipsen3server.dao;

import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.ExperimentModel2;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jesse Poleij, Anthony Schuijlenburg, Cyriel van der Raaf
 */
public class ExperimentDAO {
    private String tableName = "experiments";
    private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
    private DatabaseUtilities databaseUtilities = new DatabaseUtilities();


    /**
     * Deletes the experiment, is only called after permission checks!
     *
     * @author Jesse Poleij, AnthonySchuijlenburg
     *
     * @param experimentId The experimentId of the experiment that needs to be deleted
     * @return The status of the deleting attempt
     */
    public String deleteExperiment(int experimentId) {
        String query = String.format("DELETE FROM %s WHERE experiment_id = %d;", tableName, experimentId);

        try {
            connectToDatabase(query, "DELETE");
            return "Delete was succesfull!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Was not able to connect to database";
        }
    }


    /**
     * Requests all experiments from the database
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
     * Requests a single specific experiment from the database
     *
     * @author AnthonySchuijlenburg
     *
     * @param id the id of the experiment that needs to be showed
     * @return the
     */
    public String showExperiment(int id) {
        String query = String.format("SELECT * FROM %s WHERE experiment_id = %d;", tableName, id);
        return connectToDatabase(query, "SELECT");
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
     * @author Cyriel van der Raaf
     * Gebruikt een prepared statement om waardes in het tabel projects te plaatsen.
     */
    public void uploadProject(ExperimentModel2 model) {
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

        List<String> project2 = new ArrayList<>();
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
     * @author Cyriel van der Raaf
     * Gebruikt een prepared statement om een project te verwijderen.
     */
    public void deleteExperiment(ExperimentModel2 projectModel) {
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();

        String query1 = String.format("DELETE FROM %s WHERE id='&d';", tableName, projectModel.getId());

        try {
            databaseUtilities.connectThisDatabase2(databaseModel, query1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
