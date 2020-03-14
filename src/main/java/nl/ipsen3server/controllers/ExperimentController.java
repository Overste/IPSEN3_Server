package nl.ipsen3server.controllers;

import nl.ipsen3server.dao.ExperimentDAO;
import nl.ipsen3server.models.BoxModel;
import nl.ipsen3server.models.ExperimentModel;
import nl.ipsen3server.models.Permission;
import nl.ipsen3server.models.Response;

/**
 * @author Anthony Schuijlenburg, Jesse Poleij
 */
public class ExperimentController {
    private AuthenticationController authenticationController = new AuthenticationController();
    private ExperimentDAO experimentDAO = new ExperimentDAO();
    private TokenController tokenController = new TokenController();

    /**
     * This method tries to fetch the userId that belongs to the given token. Then checks if that users has sufficient
     * rights to do that. After that it tries to delete the experiment.
     *
     * @param projectId The id of the project that needs to be deleted
     * @param token     The token of the user trying to delete a experiment
     * @return The status of the deleting attempt
     * @author Jesse Poleij, Anthony Schuijlenburg
     */
    public String deleteExperiment(int projectId, String token) {
        String userID = this.tokenController.tokenToUserId(token);
        if (userID == null) {
            return "Token is invalid";
        }
        if (this.authenticationController.hasPermission(Integer.parseInt(userID), Permission.DELETE.toString())) {
            return this.experimentDAO.deleteExperiment(projectId);
        } else {
            return "Not sufficient rights to delete this experiment";
        }
    }

    /**
     * This method tries to fetch the id and phase. Then uses the Token to checks if the user has the sufficient
     * rights to do that. After that it tries to update the experiment.
     *
     * @author CyrielvdRaaf
     *
     * @param token the token of the user trying to update an experiment.
     */
    public String showPhases(BoxModel phaseChange, String token){
        String userID = this.tokenController.tokenToUserId(token);
        if(this.authenticationController.hasPermission(Integer.parseInt(userID), Permission.READ.toString())){
            return this.experimentDAO.showExperimentPhase(phaseChange);
        }
        return "Not sufficient rights to change this experiment";
    }

    /**
     * Checks whether or not a user has_read rights to view experiments
     *
     * @param token the token of the user trying to access the experiments
     * @return all experiments in JSON format
     * @author Anthony Schuijlenburg
     */
    public String showExperiments(String token) {
        String userID = this.tokenController.tokenToUserId(token);
        if (this.authenticationController.hasPermission(Integer.parseInt(userID), Permission.READ.toString())) {
            return this.experimentDAO.showExperiments();
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
        String userID = this.tokenController.tokenToUserId(token);
        if (this.authenticationController.hasPermission(Integer.parseInt(userID), Permission.READ.toString())) {
            return this.experimentDAO.showExperiment(id);
        } else {
            return "Not sufficient rights to delete this experiment";
        }
    }

    /**
     * @author Cyriel van der Raaf, Jesse Poleij
     */
    public String handleCreateProject(ExperimentModel project, String token){
        this.experimentDAO.uploadExperiment(project);
        return Response.fail.toString();
    }

    /**
     * @author Jesse Poleij
     */
    public String handleUpdate(ExperimentModel project, String token){
        this.experimentDAO.updateExperiment(project);
        return Response.fail.toString();
    }
}
