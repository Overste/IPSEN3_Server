package main.java.nl.iipsen2server.controlllers;

import main.java.nl.iipsen2server.dao.ExperimentDAO;
import main.java.nl.iipsen2server.models.ExperimentModel;
import main.java.nl.iipsen2server.models.Permission;


/**
 * @author Anthony Schuijlenburg, Jesse Poleij
 */
public class ExperimentController {

    private AuthenticationController authenticationController = new AuthenticationController();
    private TokenController tokenController = new TokenController();
    private ExperimentDAO experimentDAO = new ExperimentDAO();


/**
 * @author Jesse Poleij
 *
 */
    public void deleteExperiment(ExperimentModel project, String token) {
        String userID = tokenController.tokenToUserId(token);
        if(authenticationController.hasPermission(Long.parseLong(userID), Permission.DELETE.toString())){
            experimentDAO.deleteExperiment(project);
        }
    }

    /**
     * @author AnthonySchuijleburg
     */
    public String showExperiments(){
        ExperimentDAO experimentDAO = new ExperimentDAO();
        return experimentDAO.showExperiments();
    }

    /**
     * @author AnthonySchuijleburg
     */
    public String showSingleExperiment(int id){
        ExperimentDAO experimentDAO = new ExperimentDAO();
        return experimentDAO.showExperiment(id);
    }

      /**
      *@author Jesse Poleij
      */
    public void showOverview(String token) {
        String userID = tokenController.tokenToUserId(token);
         authenticationController.hasPermission(Long.parseLong(userID), Permission.READ.toString());
    }

}
