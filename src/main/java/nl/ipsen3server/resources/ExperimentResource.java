
package nl.ipsen3server.resources;

import nl.ipsen3server.controllers.ExperimentController;
import nl.ipsen3server.dao.ExperimentDAO;
import nl.ipsen3server.models.ExperimentModel;
import nl.ipsen3server.models.BoxModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author AnthonySchuijlenburg, AnthonyScheeres
 */
@Path("/experiment")
public class ExperimentResource {

    private ExperimentController experimentcontroller = new ExperimentController();

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
    @Path("/{token}/remove/{id}")
    public String deleteExperiment(@PathParam("token") String token, @PathParam("id") int id) {
        return experimentcontroller.deleteExperiment(id, token);
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
    @Path("/{token}/showAllExperiments")
    @Produces(MediaType.TEXT_PLAIN)
    public String showExperiments(@PathParam("token") String token){
        ExperimentController experimentController = new ExperimentController();
        return experimentController.showExperiments(token);
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
    @Path("/{token}/showPhaseOfExperiments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String showPhases(BoxModel phaseChange, @PathParam("token") String token){
        ExperimentController experimentController = new ExperimentController();
        return experimentController.showPhases(phaseChange, token);
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
    @Path("/{token}/showSingleExperiment/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String showSingleExperiment(@PathParam("token") String token, @PathParam("id") int id){
        ExperimentController experimentController = new ExperimentController();
        return experimentController.showSingleExperiment(token, id);
    }

    /**
     *@author Cyriel van der Raaf, Jesse Poleij
     */
    @POST
    @Path("/{token}/createProject")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createProject(ExperimentModel project, @PathParam("token") String token){
        return experimentcontroller.handleCreateProject(project, token);
    }

    /**
     *@author Jesse Poleij
     */
    @POST
    @Path("/{token}/updateProject")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteProject(ExperimentModel project, @PathParam("token") String token){
        return experimentcontroller.handleUpdate(project, token);
    }
}
