package nl.ipsen3server.controlllers;


import nl.ipsen3server.dao.AuthenticationDAO;

import nl.ipsen3server.models.LogModel;
import nl.ipsen3server.models.Permission;
import nl.ipsen3server.models.Response;
import nl.ipsen3server.models.UserModel;

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
		long employeeId = Long.parseLong(tokkenController.tokenToUserId(token));
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
		long employeeId = Long.parseLong(tokkenController.tokenToUserId(token));
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
	public boolean hasSuperPermission(long employeeId) {
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		if (authenticationDAO.hasEnumHandeler(employeeId, Permission.WRITE.toString()) && authenticationDAO.hasEnumHandeler(employeeId, Permission.READ.toString()) && authenticationDAO.hasEnumHandeler(employeeId, Permission.DELETE.toString())){
			return true;
		}
		return false;
	}
	 /**
	  *
	  * @author Anthony Scheeres 
	  *
	  */
	public boolean hasReadPermission(long employeeId) {
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		return authenticationDAO.hasEnumHandeler(employeeId, Permission.READ.toString());
		
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
		long employeeId = Long.parseLong(tokkenController.tokenToUserId(token));
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
	long employeeId = Long.parseLong(tokenController.tokenToUserId(token));
	return authenticationDAO.hasEnumHandeler(employeeId, permission);
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
	 *
	 * @author Jesse Poleij
	 *
	 *
	 */
	public boolean hasPermission(Long userID, String permission) {
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		return authenticationDAO.hasEnumHandeler(userID, permission);
	}

}