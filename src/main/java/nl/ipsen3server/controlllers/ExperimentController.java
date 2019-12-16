<<<<<<< HEAD:src/main/java/nl/iipsen2server/controlllers/ExperimentController.java
package main.java.nl.iipsen2server.controlllers;

import main.java.nl.iipsen2server.dao.AuthenticationDAO;
import main.java.nl.iipsen2server.dao.ExperimentDAO;
import main.java.nl.iipsen2server.models.ExperimentModel;
import main.java.nl.iipsen2server.models.ExperimentModel2;
import main.java.nl.iipsen2server.models.Permission;
import main.java.nl.iipsen2server.models.Response;


/**
 * @author Anthony Schuijlenburg, Jesse Poleij
 */
public class ExperimentController {
    private AuthenticationController authenticationController = new AuthenticationController();
    private ExperimentDAO experimentDAO = new ExperimentDAO();
   private  AuthenticationDAO authenticationDAO = new AuthenticationDAO();
	private TokenController tokenController = new TokenController();

    /**
     * @author Jesse Poleij
     */
    public void deleteExperiment(ExperimentModel project, String token) {
        String userID = tokenController.tokenToUserId(token);
        if (authenticationController.hasPermission(Long.parseLong(userID), Permission.DELETE.toString())) {
            experimentDAO.deleteExperiment(project);
        }
    }


    /**
     * @author AnthonySchuijleburg
     */
    public String showExperiments() {
        ExperimentDAO experimentDAO = new ExperimentDAO();
        return experimentDAO.showExperiments();
    }


    /**
     * @author AnthonySchuijleburg
     */
    public String showSingleExperiment(int id) {
        ExperimentDAO experimentDAO = new ExperimentDAO();
        return experimentDAO.showExperiment(id);
    }



	/**
	* @author Anthony Scheeres
	*/
	public String handleShowExperiments(String token) {
		
		long employeeId = Long.parseLong(tokenController.tokenToUserId(token));
		boolean hasPermission = authenticationController.hasReadPermission(employeeId);
		
		
		if (!hasPermission ) {
			System.out.println("fail");
			return Response.fail.toString();
		}
		String json = showExperiments();
		return json;
		
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
//        experimentDAO.uploadProject(new ExperimentModel2());
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
=======
package nl.ipsen3server.controlllers;

import nl.ipsen3server.dao.ExperimentDAO;
import nl.ipsen3server.models.ExperimentModel;
import nl.ipsen3server.models.Permission;


/**
 * @author Anthony Schuijlenburg, Jesse Poleij
 */
public class ExperimentController {

    private AuthenticationController authenticationController = new AuthenticationController();
    private TokenController tokenController = new TokenController();
    private ExperimentDAO experimentDAO = new ExperimentDAO();


    /**
     * @author Jesse Poleij
     */
    public void deleteExperiment(ExperimentModel project, String token) {
        String userID = tokenController.tokenToUserId(token);
        if (authenticationController.hasPermission(Long.parseLong(userID), Permission.DELETE.toString())) {
            experimentDAO.deleteExperiment(project);
        }
    }


    /**
     * @author AnthonySchuijleburg
     */
    public String showExperiments() {
        ExperimentDAO experimentDAO = new ExperimentDAO();
        return experimentDAO.showExperiments();
    }


    /**
     * @author AnthonySchuijleburg
     */
    public String showSingleExperiment(int id) {
        ExperimentDAO experimentDAO = new ExperimentDAO();
        return experimentDAO.showExperiment(id);
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
//        experimentDAO.uploadProject(new ExperimentModel2());
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
>>>>>>> master:src/main/java/nl/ipsen3server/controlllers/ExperimentController.java
