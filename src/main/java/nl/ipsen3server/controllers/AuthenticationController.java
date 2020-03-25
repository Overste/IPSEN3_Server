package nl.ipsen3server.controllers;

import nl.ipsen3server.dao.AuthenticationDAO;

import nl.ipsen3server.dao.UserDAO;
import nl.ipsen3server.models.Permission;
import nl.ipsen3server.models.ResponseR;

import javax.ws.rs.core.Response;

/**

* @author Anthony Scheeres
*/
public class AuthenticationController {

	public Response getUserRole(String token) {
		TokenController tokenController = new TokenController();
		int user_id = Integer.parseInt(tokenController.tokenToUserId(token));
		UserDAO userDAO = new UserDAO();
		String data = userDAO.getUserRole(user_id);

		if (data.contains("SUPERUSER")) {
			return Response.ok("SUPERUSER").build();
		} else if (data.contains("EMPLOYEE")) {
			return Response.ok("EMPLOYEE").build();
		} else if (data.contains("USER")) {
			return Response.ok("USER").build();
		} else {
			return Response.ok("UNCLASSIFIED").build();
		}

	}

	/**
	 * @author Anthony Scheeres
	 */
	public String handleGiveRead(String u, String token) throws Exception {
		AccountController accountController = new AccountController();
		TokenController tokkenController = new TokenController();
		int employeeId = Integer.parseInt(tokkenController.tokenToUserId(token));
		if (!hasSuperPermission(employeeId)) {
			return ResponseR.fail.toString();
		}

		accountController.giveRead2(u);
		return ResponseR.success.toString();
	}

	/**
	 * @author Anthony Scheeres
	 */
	public boolean hasSuperPermission(int employeeId) {
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		return authenticationDAO.checkUserPermission(
			employeeId,
			Permission.WRITE.toString()
		) && authenticationDAO.checkUserPermission(employeeId, Permission.READ.toString())
		  && authenticationDAO.checkUserPermission(employeeId, Permission.DELETE.toString());
	}

	/**
	 * @author Anthony Scheeres
	 */
	public boolean hasReadPermission(int employeeId) {
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		return authenticationDAO.checkUserPermission(employeeId, Permission.READ.toString());
	}

	/**
	 * @author Anthony Scheeres
	 */
	public boolean validate(String token, String permission) {
		TokenController tokenController = new TokenController();
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		int employeeId = Integer.parseInt(tokenController.tokenToUserId(token));
		return authenticationDAO.checkUserPermission(employeeId, permission);
	}

	/**
	 * takes in a userId and a permission and checks the database to see if this user has this permission
	 *
	 * @param userID     The id of the user of which it's permissions need to be checked
	 * @param permission The permission it need te checked against
	 * @return true or false depending on the users rights
	 * @author AnthonySchuijlenburg
	 */
	boolean hasPermission(int userID, String permission) {
		AuthenticationDAO authenticationDAO = new AuthenticationDAO();
		return authenticationDAO.checkUserPermission(userID, permission);
	}
}