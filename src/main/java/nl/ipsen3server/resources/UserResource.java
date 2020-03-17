package nl.ipsen3server.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


import nl.ipsen3server.controllers.AccountController;
import nl.ipsen3server.controllers.AuthenticationController;
import nl.ipsen3server.controllers.UserController;
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
	@Path("/showAllUsers")
	@Produces(MediaType.TEXT_PLAIN)
	public String showUsers(@HeaderParam("token") String token) throws Exception {
		return userController.handleShowUsers(token);
	}


	/**
	 * @author Anthony Scheeres
	 */
	@GET
	@Path("/{id}/showSingleUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String showUser(@HeaderParam("token") String token, @PathParam("id") int id) {
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
		return accountController.handleCreateUserModel(u);
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
	@Path("/removeUser")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response removeUserModel(@HeaderParam("token") String token, String u) {
		if(authenticationController.getUserRole(token).equals("SUPERUSER")) {
			System.out.println("Username " + u);
			return accountController.handleRemoveUser(u);
		} else {
			return Response.fail;
		}
	}

	/**
	 * @return
	 * @throws Exception
	 * @author Anthony Scheeres
	 */
	@GET
	@Path("/token")
	public String validateToken(@HeaderParam("token") String token) {
		return accountController.handleValidateToken(token);
	}

	/**
	 * @author Valerie Timmerman
	 * Gets a users role in the application.
	 */
	@GET
	@Path("/getRole")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserRole(@HeaderParam("token") String token) {
		return authenticationController.getUserRole(token);
	}

	/**
	 * @author Valerie Timmerman
	 */
	@PUT
	@Path("/{id}/{user_role}/updateUserRole")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel updateUserRole(@HeaderParam("token") String token, @PathParam("id") long id,
										@PathParam("user_role") String userRole) {
		if(authenticationController.getUserRole(token).equals("SUPERUSER")) {
			return userController.updateUserRole(id, userRole);
		} else {
			return new ResponseModel(Response.fail.toString());
		}

	}

}