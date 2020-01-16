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
import nl.ipsen3server.models.Response;
import nl.ipsen3server.models.UserModel;
import nl.ipsen3server.models.AccountModel;
import nl.ipsen3server.models.Permission;


/**
* @author Anthony Scheeres
*/
@Path("/user")
public class UserResource {
	private UserController 	userController  = new 	UserController ();
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
	public String showUser(@PathParam("token") String token, @PathParam("id") int id){
		UserDAO userDatabase = new UserDAO();
		return userDatabase.showOneUserPermission(id);
	}


	/**
	 * @author Anthony Scheeres
	 */
	@POST
	@Path("/createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUserModel(UserModel u ) throws Exception  {
		return accountController.handleCreateUserModel2(u);
	}


	/**
	 * @author Anthony Scheeres
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLogin(UserModel u) throws Exception  {
		return accountController.checkLogin(u);
	}


	/**
	 * @author Jesse Poleij
	 */
	@POST
	@Path("/{token}/removeUser")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response removeUserModel(String u)  {
		System.out.println("Username " +  u);
		return accountController.handleRemoveUser(u);
	}


	/**
	 * @author Anthony Scheeres
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{token}/token")
	public String validateToken(@PathParam("token") String token) throws Exception{
		return accountController.validateToken(token);
	}


	/**
	 * @author Valerie Timmerman
	 */
	@PUT
	@Path("/{token}/{id}/{user_role}/updateUserRole")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUserRole(@PathParam("id") long id, @PathParam("user_role") String userRole) {
		return userController.updateUserRole(id, userRole);
	}


//----------------------------------------------------------------------------------------------------------------------
//	These functions are not used
//----------------------------------------------------------------------------------------------------------------------


	/**
	 * @author Anthony Scheeres
	 */
	@POST
	@Path("/{token}/giveRead")
	@Consumes(MediaType.APPLICATION_JSON)
	public String giveRead(@PathParam("token") String token, AccountModel u) throws Exception  {
		return authenticationController.handleGiveRead(u.getUsername(), token);
	}


	/**
	 * @author Anthony Scheeres
	 */
	@POST
	@Path("/{token}/giveWrite")
	@Consumes(MediaType.APPLICATION_JSON)
	public String giveWrite(@PathParam("token") String token,AccountModel u) throws Exception  {
		return authenticationController.handleGiveWrite(u.getUsername(), token);
	}


	/**
	* @author Anthony Scheeres
	 * @throws Exception
	*/
	@POST
	@Path("/{token}/giveDelete")
	@Consumes(MediaType.APPLICATION_JSON)
	public String giveDelete(@PathParam("token") String token,AccountModel u) throws Exception  {
		TokenController tokenController = new TokenController();

		return authenticationController.handleGiveDelete(u.getUsername(), token);
	}


	/**
	* @author Anthony Scheeres
	*/
	@POST
	@Path("/{token}/hasRead")
	public Object hasRead(@PathParam("token") String token)  {
		return authenticationController.validate(token,Permission.READ.toString());
	}


	/**
	* @author Anthony Scheeres
	*/
	@POST
	@Path("/{token}/hasWrite")
	public Object hasWrite(@PathParam("token") String token)  {
		return authenticationController.validate(token, Permission.WRITE.toString());

	}


	/**
	* @author Anthony Scheeres
	*/
	@POST
	@Path("/{token}/hasDelete")
	public boolean hasDelete(@PathParam("token") String token) {
		return authenticationController.validate(token, Permission.DELETE.toString());

	}


	/**
	* @author Anthony Scheeres
	*/
	@POST
	@Path("/{token}/hasAdmin")
	public boolean hasAdmin(@PathParam("token") String token) {
		return authenticationController.hasAdmin(token);

	}
}	