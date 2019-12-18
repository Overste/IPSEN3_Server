package nl.ipsen3server.controlllers;


import nl.ipsen3server.dao.AuthenticationDAO;

import nl.ipsen3server.models.Permission;
import nl.ipsen3server.models.Response;

/**
*
* @author Anthony Scheeres
*  
* 
*
*/
public class AuthenticationController {

	 /**
	  *
	  * @author Anthony Scheeres
	 * @throws Exception 
	  *  
	  * 
	  *
	  */
	public String handleGiveRead(String u, String token) throws Exception {
		AccountController accountController = new AccountController();
		TokenController tokkenController = new TokenController();
		int employeeId = Integer.parseInt(tokkenController.tokenToUserId(token));
		if (!hasSuperPermission(employeeId)) {
			return Response.fail.toString();
		}
		
		
		accountController.giveRead2(u);
		return Response.success.toString();
		}


	 /**
	  *
	  * @author Anthony Scheeres
	 * @return 
	 * @throws Exception 
	  *  
	  * 
	  *
	  */
	public String handleGiveWrite(String u,String token) throws Exception {
		LoggingController loggingController = new LoggingController();
		AccountController accountController = new AccountController();
		TokenController tokkenController = new TokenController();
		int employeeId = Integer.parseInt(tokkenController.tokenToUserId(token));
		if (!hasSuperPermission(employeeId)) {
			return Response.fail.toString();
		}
		
		
		accountController.giveWrite2(u);
		return Response.success.toString();
	}




	 /**
	  *
	  * @author Anthony Scheeres 
	  *
	  */
	public boolean hasSuperPermission(int employeeId) {
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		if (authenticationDAO.hasEnumHandler(employeeId, Permission.WRITE.toString()) && authenticationDAO.hasEnumHandler(employeeId, Permission.READ.toString()) && authenticationDAO.hasEnumHandler(employeeId, Permission.DELETE.toString())){
			return true;
		}
		return false;
	}
	 /**
	  *
	  * @author Anthony Scheeres 
	  *
	  */
	public boolean hasReadPermission(int employeeId) {
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		return authenticationDAO.hasEnumHandler(employeeId, Permission.READ.toString());
		
	}



	 /**
	  *
	  * @author Anthony Scheeres
	 * @throws Exception 
	  *
	  */
	public String handleGiveDelete(String u,String token) throws Exception {
		LoggingController loggingController = new LoggingController();
		AccountController accountController = new AccountController();
		TokenController tokkenController = new TokenController();
		int employeeId = Integer.parseInt(tokkenController.tokenToUserId(token));
		if (!hasSuperPermission(employeeId)) {
			return Response.fail.toString();
		}
		
		
		accountController.giveDelete2(u);
		return Response.success.toString();
	}



/**
*
* @author Anthony Scheeres
 *  
* 
*
*/
public boolean validate(String token, String permission) {
	TokenController tokenController = new TokenController();
	AuthenticationDAO authenticationDAO = new AuthenticationDAO();
	int employeeId = Integer.parseInt(tokenController.tokenToUserId(token));
	return authenticationDAO.hasEnumHandler(employeeId, permission);
}


	
	/**
	*
	* @author Jesse Poleij
	*
	*
	*/
	public void userIDtoUsername(String userID) {
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();

		String resultset = authenticationDAO.userIDtoUsername(userID);

		System.out.println(resultset);


	}

	/**
	 * takes in a userId and a permission and checks the database to see if this user has this permission
	 *
	 * @author AnthonySchuijlenburg
	 *
	 * @param userID The id of the user of which it's permissions need to be checked
	 * @param permission The permission it need te checked against
	 * @return true or false depending on the users rights
	 */
	public boolean hasPermission(int userID, String permission) {
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		return authenticationDAO.hasEnumHandler(userID, permission);
	}

}