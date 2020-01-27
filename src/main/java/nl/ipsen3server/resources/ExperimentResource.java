
package nl.ipsen3server.resources;

import nl.ipsen3server.controlllers.ExperimentController;
import nl.ipsen3server.dao.ExperimentDAO;
import nl.ipsen3server.models.ExperimentModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author AnthonySchuijlenburg, AnthonyScheeres
 */
@Path("/experiment")
public class ExperimentResource {

    private ExperimentController experimentcontroller = new ExperimentController();


    /**
     * Catches the URL and tries to delete an experiment. Returns the status of this attempt.
     *
     * @author Jesse Poleij, AnthonySchuijlenburg
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
     * @author AnthonySchuijlenburg
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



    @POST
    @Path("/{token}/showPhaseOfExperiments")
    @Consumes(MediaType.TEXT_PLAIN)
    public String showPhases(List[] phase, @PathParam("token") String token){
        ExperimentController experimentController = new ExperimentController();
        System.out.println(phase);
        return experimentController.showPhases(phase, token);
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
     * @author Anthony Scheeres
     */
    @GET
    @Path("/showAllHashmap")
    @Produces(MediaType.TEXT_PLAIN)
    public String showAllExperiments() throws Exception {
        ExperimentDAO experimentDAO = new ExperimentDAO();
        return experimentDAO.showAllExperimentHashmap();
    }


    /**
     * @author Anthony Scheeres
     */
    @GET
    @Path("/showAllJson")
    @Produces(MediaType.TEXT_PLAIN)
    public String showAllExperimentsJson() throws Exception {
        ExperimentDAO experimentDAO = new ExperimentDAO();
        return experimentDAO.showAllExperimentJson();
    }


//TODO CYRIEL NEEDS TO CLEAN THIS UP
//
//    /**
//     *@author Cyriel van der Raaf
//     */
//    @POST
//    @Path("/{token}/createProject")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String openProject(ExperimentModel2 project, @PathParam("token") String token){
//        ExperimentController experimentController = new ExperimentController();
//        return projectsController.handleCreateProject(project, token);
//    }
//
//    /**
//     *@author Cyriel van der Raaf
//     */
//    @POST
//    @Path("/{token}/createProject")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String deleteProject(ExperimentModel2 project, @PathParam("token") String token){
//        ExperimentController experimentController = new ExperimentController();
//        return projectsController.deleteCreateProject(project, token);
//    }
}
