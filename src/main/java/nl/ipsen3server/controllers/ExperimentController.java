package nl.ipsen3server.controllers;

import nl.ipsen3server.dao.ExperimentDAO;
import nl.ipsen3server.models.BoxModel;
import nl.ipsen3server.models.ExperimentModel;
import nl.ipsen3server.models.Permission;
import nl.ipsen3server.models.ResponseR;

import javax.ws.rs.core.Response;

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
    public Response deleteExperiment(int projectId, String token) {
        String userID = this.tokenController.tokenToUserId(token);
        if (userID == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (this.authenticationController.hasPermission(Integer.parseInt(userID), Permission.DELETE.toString())) {
            return this.experimentDAO.deleteExperiment(projectId);
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
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
    public Response showPhases(BoxModel phaseChange, String token){
        String userID = this.tokenController.tokenToUserId(token);
        if(this.authenticationController.hasPermission(Integer.parseInt(userID), Permission.READ.toString())){
            return Response.ok(this.experimentDAO.showExperimentPhase(phaseChange)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Checks whether or not a user has_read rights to view experiments
     *
     * @param token the token of the user trying to access the experiments
     * @return all experiments in JSON format
     * @author Anthony Schuijlenburg
     */
    public Response showExperiments(String token) {
        String userID = this.tokenController.tokenToUserId(token);
        if (this.authenticationController.hasPermission(Integer.parseInt(userID), Permission.READ.toString())) {
            return Response.ok(this.experimentDAO.showExperiments()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    /**
     * Checks whether or not a user has_read rights to view experiments
     *
     * @param token the token of the user trying to access the experiments
     * @return all experiments in JSON format
     * @author AnthonySchuijlenburg
     */
    public Response showSingleExperiment(String token, int id) {
        String userID = this.tokenController.tokenToUserId(token);
        if (this.authenticationController.hasPermission(Integer.parseInt(userID), Permission.READ.toString())) {
            return Response.ok(this.experimentDAO.showExperiment(id)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    /**
     * @author Cyriel van der Raaf, Jesse Poleij
     */
    public Response handleCreateProject(ExperimentModel project, String token){ String userID = this.tokenController.tokenToUserId(token);
       if(this.authenticationController.hasPermission(Integer.parseInt(userID), Permission.WRITE.toString())) {
           if(this.experimentDAO.uploadExperiment(project) != null) {
               return Response.ok().build();
           } else {
               return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
           }
       } else {
           return Response.status(Response.Status.UNAUTHORIZED).build();
       }
    }

    /**
     * @author Jesse Poleij
     */
    public Response handleUpdate(ExperimentModel project, String token) {
        String userID = this.tokenController.tokenToUserId(token);
        if(this.authenticationController.hasPermission(Integer.parseInt(userID), Permission.DELETE.toString())) {
            if(this.experimentDAO.updateExperiment(project) == null) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
