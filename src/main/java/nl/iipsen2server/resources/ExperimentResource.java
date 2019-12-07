package main.java.nl.iipsen2server.resources;

import main.java.nl.iipsen2server.controlllers.ExperimentController;
import main.java.nl.iipsen2server.dao.ExperimentDAO;
import main.java.nl.iipsen2server.models.ExperimentModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author AnthonySchuijlenburg, AnthonyScheeres
 */
@Path("/experiment")
public class ExperimentResource {

    private ExperimentController experimentcontroller = new ExperimentController();


    /**
     * @author Jesse Poleij
     */
    @POST
    @Path("/{token}/remove")
    public void deleteExperiment(@PathParam("token") String token, ExperimentModel experimentModel) {
        experimentcontroller.deleteExperiment(experimentModel, token);
    }


	/**
	 * @author AnthonySchuijlenburg
	 */
    @GET
    @Path("{token}/showAllExperiments")
    @Produces(MediaType.TEXT_PLAIN)
    public String showExperiments(@PathParam("token") String token){
    	
        ExperimentController experimentDAO = new ExperimentController();
        return experimentDAO.handleShowExperiments(token);
    }


    /**
     * @author AnthonySchuijlenburg
     */
    @GET
    @Path("/showSingleExperiment/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String showSingleExperiment(@PathParam("id") int id){
        ExperimentController experimentController = new ExperimentController();
        return experimentController.showSingleExperiment(id);
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
