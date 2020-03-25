package nl.ipsen3server.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import nl.ipsen3server.controllers.AccountController;
import nl.ipsen3server.controllers.AuthenticationController;
import nl.ipsen3server.controllers.UserController;
import nl.ipsen3server.models.*;

/**
* @author Anthony Scheeres, Valerie Timmerman
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
	public Response showUsers(@HeaderParam("token") String token) {
		return this.userController.handleShowUsers(token);
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
		return this.accountController.handleRemoveUser(u, token);
	}

	/**
	 * @return
	 * @throws Exception
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
	public Response getUserRole(@HeaderParam("token") String token) {
		return this.authenticationController.getUserRole(token);
	}

	/**
	 * @author Valerie Timmerman
	 * Updates the rol of a specific user if this user has superuser rights
	 */
	@PUT
	@Path("/{id}/{user_role}/updateUserRole")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUserRole(@HeaderParam("token") String token,
										@PathParam("id") long id,
										@PathParam("user_role") String userRole) {
		return this.userController.updateUserRole(id, userRole, token);

	}

	/**
	 * @author Valerie Timmerman
	 * Gets the users info for the profile page and returns it as a usermodel.
	 */
	@GET
	@Path("/getUserInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserInfo(@HeaderParam("token") String token) {
		return userController.getUserInfo(token);
	}

}