package main.java.nl.iipsen2server.dao;

import main.java.nl.iipsen2server.models.DataModel;
import main.java.nl.iipsen2server.models.DatabaseModel;
import main.java.nl.iipsen2server.models.ExperimentModel;

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
        String query = "DELETE FROM experiments WHERE id = ?;";

        List< String > arrayList = new ArrayList<>();
        arrayList.add(Long.toString(project.getId()));
        try {
            preparedStatmentDatabaseUtilities.connectDatabaseJson(databaseModel, query, arrayList, false);
        } catch (Exception e) { }
    }


    /**
     * @author AnthonySchuijleburg
     */
    public String showExperiments(){
        String query = String.format("select id, project_name, experiment_leader, status from %s;", tableName);
        return ConnectToDatabase(query);
    }


    /**
     * @author AnthonySchuijleburg
     */
    public String showExperiment(int id){
        String query = String.format("SELECT * FROM %s WHERE id = %d;", tableName, id);
        return ConnectToDatabase(query);
    }


    /**
     * @author AnthonySchuijleburg
     */
    private String ConnectToDatabase(String url){
        DatabaseUtilities databaseUtilities = new DatabaseUtilities();
        String e1 = null;
        try {
            e1 = databaseUtilities.connectThisDatabase2(databaseModel, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return e1;
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
}
