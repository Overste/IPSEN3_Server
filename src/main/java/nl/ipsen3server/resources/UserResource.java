package nl.ipsen3server.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import nl.ipsen3server.controllers.AccountController;
import nl.ipsen3server.controllers.AuthenticationController;
import nl.ipsen3server.controllers.UserController;
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
	public String showUsers(@PathParam("token") String token) {
		return this.userController.handleShowUsers(token);
	}


	/**
	 * @author Anthony Scheeres
	 */
	@GET
	@Path("/{token}/{id}/showSingleUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String showUser(@PathParam("token") String token, @PathParam("id") int id) {
		return this.userController.showOneUserPermission(id);
	}

	/**
	 * @author Anthony Scheeres Thomas Warbout
	 */
	@POST
	@Path("/createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUserModel(UserModel u) { return this.accountController.handleCreateUserModel(u); }

	/**
	 * @author Anthony Scheeres
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLogin(UserModel u) {
		return this.accountController.handleCheckLogin(u);
	}


	/**
	 * @author Jesse Poleij
	 */
	@POST
	@Path("/{token}/removeUser")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response removeUserModel(String u) { return this.accountController.handleRemoveUser(u); }

	/**
	 * @author Anthony Scheeres
	 */
	@GET
	@Path("/{token}/token")
	public String validateToken(@PathParam("token") String token) {
		return this.accountController.handleValidateToken(token);
	}

	/**
	 * @author Valerie Timmerman
	 * Gets a users role in the application.
	 */
	@GET
	@Path("/{token}/getRole")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserRole(@PathParam("token") String token) {
		return this.authenticationController.getUserRole(token);
	}

	/**
	 * @author Valerie Timmerman
	 */
	@PUT
	@Path("/{token}/{id}/{user_role}/updateUserRole")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel updateUserRole(@PathParam("id") long id, @PathParam("user_role") String userRole) {
		return this.userController.updateUserRole(id, userRole);
	}
}