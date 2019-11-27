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
     * @author Jesse Poleij
     */
    @GET
    @Path("/{token}/overview")
    @Produces(MediaType.TEXT_PLAIN)
    public void showOverview(@PathParam("token") String token){
        experimentcontroller.showOverview(token);
    }


	/**
	 * @author AnthonySchuijlenburg
	 */
    @GET
    @Path("/show")
    @Produces(MediaType.TEXT_PLAIN)
    public String showExperiments(){
        ExperimentDAO experimentDAO = new ExperimentDAO();
        return experimentDAO.showExperiments();
    }


    /**
     * @author AnthonySchuijlenburg
     */
    @GET
    @Path("/showSingle/{id}")
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
}
