package nl.ipsen3server.resources;

import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


import nl.ipsen3server.controlllers.AccountController;
import nl.ipsen3server.controlllers.AuthenticationController;
import nl.ipsen3server.controlllers.LoggerController;
import nl.ipsen3server.controlllers.TokenController;
import nl.ipsen3server.controlllers.UserController;
import nl.ipsen3server.dao.UserDAO;
import nl.ipsen3server.models.*;


/**
* @author Anthony Scheeres
*/
@Path("/user")
public class UserResource {
	private UserController userController = new UserController();
	private AccountController accountController = new AccountController();
	private AuthenticationController authenticationController = new AuthenticationController();


	/**
	 * @author Anthony Scheeres
	 */
	@GET
	@Path("/{token}/showAllUsers")
	@Produces(MediaType.TEXT_PLAIN)
	public String showUsers(@PathParam("token") String token) throws Exception {
		return userController.handleShowUsers(token);
	}


	/**
	 * @author Anthony Scheeres
	 */
	@GET
	@Path("/{token}/{id}/showSingleUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String showUser(@PathParam("token") String token, @PathParam("id") int id) {
		UserDAO userDatabase = new UserDAO();
		return userDatabase.showOneUserPermission(id);
	}

	/**
	 * @author Anthony Scheeres
	 */
	@POST
	@Path("/createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUserModel(UserModel u) {
		return accountController.handleCreateUserModel2(u);
	}


	/**
	 * @author Anthony Scheeres
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLogin(UserModel u) {
		return accountController.handleCheckLogin(u);
	}


	/**
	 * @author Jesse Poleij
	 */
	@POST
	@Path("/{token}/removeUser")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response removeUserModel(String u) {
		System.out.println("Username " + u);
		return accountController.handleRemoveUser(u);
	}


	/**
	 * @return
	 * @throws Exception
	 * @author Anthony Scheeres
	 */
	@GET
	@Path("/{token}/token")
	public String validateToken(@PathParam("token") String token) {
		return accountController.handleValidateToken(token);
	}

	/**
	 * @author Valerie Timmerman
	 * Gets a users role in the application.
	 */
	@GET
	@Path("/{token}/getRole")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserRole(@PathParam("token") String token) {
		return authenticationController.getUserRole(token);
	}

	/**
	 * @author Valerie Timmerman
	 */
	@PUT
	@Path("/{token}/{id}/{user_role}/updateUserRole")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel updateUserRole(@PathParam("id") long id, @PathParam("user_role") String userRole) {
		return userController.updateUserRole(id, userRole);
	}

}