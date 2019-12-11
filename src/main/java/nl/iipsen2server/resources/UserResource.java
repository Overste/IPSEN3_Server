package main.java.nl.iipsen2server.resources;



import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import main.java.nl.iipsen2server.controlllers.AccountController;
import main.java.nl.iipsen2server.controlllers.AuthenticationController;
import main.java.nl.iipsen2server.controlllers.TokenController;
import main.java.nl.iipsen2server.controlllers.UserController;
import main.java.nl.iipsen2server.dao.UserDAO;
import main.java.nl.iipsen2server.models.UserModel;
import main.java.nl.iipsen2server.models.AccountModel;
import main.java.nl.iipsen2server.models.Permission;


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
	 * @throws Exception 
	*/	
	@POST
	@Path("/{token}/giveRead")
	@Consumes(MediaType.APPLICATION_JSON)
	public String giveRead(@PathParam("token") String token, AccountModel u) throws Exception  {
		return authenticationController.handleGiveRead(u.getUsername(), token);
	}
	
	
	/**
	* @author Anthony Scheeres
	 * @throws Exception 
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
	public boolean hasDelete(@PathParam("token") String token)  {
		return authenticationController.validate(token, Permission.DELETE.toString());
		
	}

	
	/**
	* @author Jesse Poleij
	*/
	@POST
	@Path("/{token}/removeUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeUserModel(@PathParam("token") String employeeId, AccountModel u)  {
		accountController.handleRemoveUser(u, employeeId);
	}
	
	
	
	/**
	* @author Anthony Scheeres
	 * @throws Exception 
	*/
	@POST
	@Path("/createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUserModel(UserModel u ) throws Exception  {
		return accountController.handleCreateUserModel2(u);
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
	*
	* @author Anthony Scheeres
	 * @throws Exception 
	*
	*/
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLogin(UserModel u) throws Exception  {
		return accountController.checkLogin(u);
	}
	
	/**
	*
	* @author Anthony Scheeres
	*
	*/
	@GET
	@Path("/{token}/showAllUsers")
	@Produces(MediaType.TEXT_PLAIN)
	public String showUsers(@PathParam("token") String token) throws Exception {
		return userController.handleShowUsers(token);
	}
	
	/**
	*
	* @author Anthony Scheeres
	*
	*/
	@GET
	@Path("/{token}/{id}/showSingleUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String showUser(@PathParam("token") String token, @PathParam("id") int id){
		UserDAO userDatabase = new UserDAO();
		return userDatabase.showOneUserPermission(id);
	}
}	