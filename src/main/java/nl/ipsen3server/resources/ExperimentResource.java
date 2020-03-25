
package nl.ipsen3server.resources;

import nl.ipsen3server.controllers.ExperimentController;
import nl.ipsen3server.models.ExperimentModel;
import nl.ipsen3server.models.BoxModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author AnthonySchuijlenburg, AnthonyScheeres
 */
@Path("/experiment")
public class ExperimentResource {
    private ExperimentController experimentController = new ExperimentController();

    /**
     * Catches the URL and tries to delete an experiment. Returns the status of this attempt.
     *
     * @author Jesse Poleij, Anthony Schuijlenburg
     *
     * @param id The id of the project that needs to be deleted
     * @param token The token of the user trying to delete a experiment
     * @return a string with the status of the DELETE REQUEST
     */
    @DELETE
    @Path("/remove/{id}")
    public Response deleteExperiment(@HeaderParam("token") String token, @PathParam("id") int id) {
        return this.experimentController.deleteExperiment(id, token);
    }

    /**
     * Shows all experiments to a user requesting it
     *
     * @author Anthony Schuijlenburg
     *
     * @param token the token of the user trying to access the experiments
     * @return a JSON of all experiments from the database
     */
    @GET
    @Path("/showAllExperiments")
    @Produces(MediaType.TEXT_PLAIN)
    public Response showExperiments(@HeaderParam("token") String token){
        return this.experimentController.showExperiments(token);
    }

    /**
     * gets values of boxModel.
     *
     * @author CyrielvdRaaf
     *
     * @param phaseChange
     * @param token the token of the user trying to acces the experiments
     * @return a JSON of experiment phase and id from the database
     */
    @POST
    @Path("/showPhaseOfExperiments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response showPhases(BoxModel phaseChange, @HeaderParam("token") String token){
        return this.experimentController.showPhases(phaseChange, token);
    }

    /**
     * Shows a single experiment to a user requesting it
     *
     * @author AnthonySchuijlenburg
     *
     * @param token the token of the user trying to access the experiments
     * @param id the id of the experiment requested
     * @return a JSON of a single experiment from the database
     */
    @GET
    @Path("/showSingleExperiment/{id}")
    public Response showSingleExperiment(@HeaderParam("token") String token, @PathParam("id") int id){
        return this.experimentController.showSingleExperiment(token, id);
    }

    /**
     *@author Cyriel van der Raaf, Jesse Poleij
     */
    @POST
    @Path("/createProject")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProject(ExperimentModel project, @HeaderParam("token") String token){
        return this.experimentController.handleCreateProject(project, token);
    }

    /**
     *@author Jesse Poleij
     */
    @POST
    @Path("/updateProject")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProject(ExperimentModel project, @HeaderParam("token") String token){
        return this.experimentController.handleUpdate(project, token);
    }
}
