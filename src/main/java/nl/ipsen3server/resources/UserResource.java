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
	@Path("/showAllUsers")
	@Produces(MediaType.TEXT_PLAIN)
	public String showUsers(@HeaderParam("token") String token) throws Exception {
		return this.userController.handleShowUsers(token);
	}


	/**
	 * @author Anthony Scheeres
	 */
	@GET
	@Path("/{id}/showSingleUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String showUser(@HeaderParam("token") String token, @PathParam("id") int id) {
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
	@Path("/removeUser")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response removeUserModel(String u) { return this.accountController.handleRemoveUser(u); }

	/**
	 * @author Anthony Scheeres
	 */
	@GET
	@Path("/token")
	public String validateToken(@HeaderParam("token") String token) {
		return this.accountController.handleValidateToken(token);
	}

	/**
	 * @author Valerie Timmerman
	 * Gets a users role in the application.
	 */
	@GET
	@Path("/getRole")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserRole(@HeaderParam("token") String token) {
		return this.authenticationController.getUserRole(token);
	}

	/**
	 * @author Valerie Timmerman
	 */
	@PUT
	@Path("/{id}/{user_role}/updateUserRole")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel updateUserRole(@PathParam("id") long id, @PathParam("user_role") String userRole) {
		return this.userController.updateUserRole(id, userRole);
	}
}