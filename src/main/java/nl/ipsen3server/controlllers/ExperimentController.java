package nl.ipsen3server.controlllers;

import nl.ipsen3server.dao.AuthenticationDAO;
import nl.ipsen3server.dao.ExperimentDAO;
import nl.ipsen3server.models.ExperimentModel;
import nl.ipsen3server.models.Permission;
import nl.ipsen3server.models.Response;


/**
 * @author Anthony Schuijlenburg, Jesse Poleij
 */
public class ExperimentController {
    private AuthenticationController authenticationController = new AuthenticationController();
    private ExperimentDAO experimentDAO = new ExperimentDAO();
    private AuthenticationDAO authenticationDAO = new AuthenticationDAO();
    private TokenController tokenController = new TokenController();

    /**
     * This method tries to fetch the userId that belongs to the given token. Then checks if that users has sufficient
     * rights to do that. After that it tries to delete the experiment.
     *
     * @param projectId The id of the project that needs to be deleted
     * @param token     The token of the user trying to delete a experiment
     * @return The status of the deleting attempt
     * @author Jesse Poleij, AnthonySchuijlenburg
     */
    public String deleteExperiment(int projectId, String token) {
        String userID = tokenController.tokenToUserId(token);
        if (userID == null) {
            return "Token is invalid";
        }
        if (authenticationController.hasPermission(Integer.parseInt(userID), Permission.DELETE.toString())) {
            return experimentDAO.deleteExperiment(projectId);
        } else {
            return "Not sufficient rights to delete this experiment";
        }
    }


    public String showPhases(String phase, String id, String token){
        String userID = tokenController.tokenToUserId(token);
        if(authenticationController.hasPermission(Integer.parseInt(userID), Permission.READ.toString())){
            ExperimentDAO experimentDAO = new ExperimentDAO();
            return experimentDAO.showExperimentPhase(phase, id);
        } else{
            return "Not sufficient rights to delete this experiment";
        }
    }


    /**
     * Checks whether or not a user has_read rights to view experiments
     *
     * @param token the token of the user trying to access the experiments
     * @return all experiments in JSON format
     * @author AnthonySchuijlenburg
     */
    public String showExperiments(String token) {
        String userID = tokenController.tokenToUserId(token);
        if (authenticationController.hasPermission(Integer.parseInt(userID), Permission.READ.toString())) {
            ExperimentDAO experimentDAO = new ExperimentDAO();
            return experimentDAO.showExperiments();
        } else {
            return "Not sufficient rights to delete this experiment";
        }
    }


    /**
     * Checks whether or not a user has_read rights to view experiments
     *
     * @param token the token of the user trying to access the experiments
     * @return all experiments in JSON format
     * @author AnthonySchuijlenburg
     */
    public String showSingleExperiment(String token, int id) {
        String userID = tokenController.tokenToUserId(token);
        if (authenticationController.hasPermission(Integer.parseInt(userID), Permission.READ.toString())) {
            ExperimentDAO experimentDAO = new ExperimentDAO();
            return experimentDAO.showExperiment(id);
        } else {
            return "Not sufficient rights to delete this experiment";
        }
    }


//TODO CYRIEL NEEDS TO CLEAN THIS UP
//
//    /*
//     * @author Cyriel van der Raaf
//     */
//    public String handleCreateProject(ExperimentModel2 project, String token){
//        //validate user
//        TokenController tokenController = new TokenController();
//        AuthenticationController authenticationController = new AuthenticationController();
//        long employeeId = Long.parseLong(tokenController.tokenToUserId(token));
//
//        if (!authenticationController.hasSuperPermission(employeeId)) {
//            return Response.fail.toString();
//        }
//        //write model to db
//        ExperimentDAO experimentDAO = new ExperimentDAO();
//        experimentDAO.uploadExperiment(new ExperimentModel2());
//
//
//        return Response.fail.toString();
//    }
//
//
//    /**
//     *@author Cyriel van der Raaf
//     */
//    public String deleteCreateProject(ExperimentModel2 project, String token){
//        //validate user
//        TokenController tokenController = new TokenController();
//        AuthenticationController authenticationController = new AuthenticationController();
//        long employeeId = Long.parseLong(tokenController.tokenToUserId(token));
//
//        if (!authenticationController.hasSuperPermission(employeeId)) {
//            return Response.fail.toString();
//        }
//
//        //project delete model
//        ExperimentDAO experimentDAO = new ExperimentDAO();
//        experimentDAO.deleteExperiment(new ExperimentModel2());
//
//
//        return Response.fail.toString();
//    }
}
