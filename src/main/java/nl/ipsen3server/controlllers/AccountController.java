package nl.ipsen3server.controlllers;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.Response;
import nl.ipsen3server.models.RestApiModel;
import nl.ipsen3server.models.User;
import nl.ipsen3server.models.UserModel;
import nl.ipsen3server.models.ValidateEmailModel;
import nl.ipsen3server.dao.PermissionDAO;
import nl.ipsen3server.dao.PreparedStatmentDatabaseUtilities;
import nl.ipsen3server.dao.UserDAO;
import nl.ipsen3server.models.AccountModel;


/**
 * @author Anthony Scheeres
 */


public class AccountController {

	 private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());
private UserDAO userDatabase = new UserDAO();
private PermissionDAO permissionDatabase = new PermissionDAO();
String domain = "OM.NL";



/**
 * @return
 * @author Anthony Scheeres
 * @throws Exception 
 */
    public void giveRead2(String username) throws Exception {
        permissionDatabase.giveRead(username);
    }
    public String handleCheckLogin(UserModel u) {
    	try {
    		return checkLogin(u);
    	} catch (Exception e) {

			 LOGGER.log(Level.SEVERE, "Error occur", e);
    	}
    	return Response.fail.toString();
    }

    /**
     * @author Anthony Scheeres
     * @throws Exception 
     */
    public void giveWrite2(String user) throws Exception {
        permissionDatabase.giveWrite(user);
    }

    /**
     * @author Anthony Scheeres
     * @throws Exception 
     */
    public void giveDelete2(String user) throws Exception {
        permissionDatabase.giveDelete(user);
    }

    /**
     * @author Anthony Scheeres
     */
    private String createUserModel(UserModel userModel) {
        UserController r = new UserController();
        HashMap<String, List<String>> hashmap;
        String result = null;
        try {
			hashmap = userDatabase.getUsers();
		
        List<String> usernames = hashmap.get("username");
        
        if (r.checkIfUsernameExist(usernames, userModel.getUsername()) != true) {
        	  result =  userDatabase.insertHandlerUser(hashmap, userModel);
        }
        
        
        } catch (Exception e) {
			// TODO Auto-generated catch block
			 LOGGER.log(Level.SEVERE, "Error occur", e);
		}
        
        return result;
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean checkInputValide(UserModel u) {
    	
    	String email = u.getEmail(); 
    	String password = u.getPassword();
        MailController m = new MailController();
        boolean response = true;
        
        if (!m.isValidEmailAddress(email)) {
        	response= false;
        }

        if (password.length() == 0) {
        	response= false;
        }
        
        if (PreparedStatmentDatabaseUtilities.isNumeric(u.getUsername())){
        	response= false;
        }
        
        
        return response;
    }


    /**
     * @author Anthony Scheeres
     * @throws Exception 
     */
    public String handleCreateUserModel2(UserModel u) throws Exception {
    	String fail = Response.fail.toString();
        if (!checkInputValide(u)) {
            return fail;
        }
   
            String token = createUserModel(u);
            
         
					validateEmail(token, u.getEmail());
		
            
            if (!token.equals(null)) {   
                return token;
            }
            return fail;
      
    }


    /**
     * @author Anthony Scheeres
     */
    private void validateEmail(String token, String email) throws Exception {
    	String linkToServer = "http://%s:%s/user/%s/token";
    	String message = "Open de volgende link om uw email te valideren: ";
    	String link = message + linkToServer;
    	RestApiModel database =   DataModel.getApplicationModel().getServers().get(0).getRestApi().get(0);
    	String title = "Valideer u email!";
        MailController.sendMailOnDifferentThread(String.format(
               link,
                database.getHostName(),
                database.getPortNumber(),
                token
                ),
                "testlab",
                email,
                title);
    }

 /**
  *
  * @author Anthony Scheeres
 * @throws Exception 
  *  
  *
  */
 public String checkLogin(UserModel u) throws Exception {
  HashMap < String, List < String >> hashmap;
  String response = Response.fail.toString();
   hashmap = userDatabase.getUserInfo();
   List<String> users = hashmap.get(User.username.toString());
   String usernameFromClient = u.getUsername();
   String passwordFromClient = u.getPassword();
   for (int index = 0; index < users.size(); index++) {
	   String username = hashmap.get(User.username.toString()).get(index);
	   String passwordFromDatabase = hashmap.get(User.password.toString()).get(index); 
	   String token = hashmap.get(User.token.toString()).get(index);
	   String permission = hashmap.get("has_read").get(index);
	   String UserId = hashmap.get(User.user_id.toString()).get(index);
	   String responseToUser = GetLoginInformation(username, usernameFromClient, passwordFromDatabase,  passwordFromClient, permission, UserId, token);
	   if (!responseToUser.equals(response)) {
		   return responseToUser;
	   }
   }

  return response;
 }
 
 
 /**
  * @author Anthony Scheeres
  */
 private String GetLoginInformation(String username, String username2, String passwordFromDatabase,  String passwordFromClient, String permission, String UserId, String token){
	 String failtResponse = Response.fail.toString();
	 //"token : "+token + "permission :"+permission );
	  if (checkCredentials(username, username2, passwordFromDatabase,  passwordFromClient)) {
	    	boolean hasPermission = permission.length() ==0;
	    	if(hasPermission) {
	    
	    		return token;
	    	}
	    	
	   
	    	
	    	if (permission.contains("t") || token.equals(null)) {
	    		 String newToken =  askNewTokenForAccount(Integer.parseInt(UserId));		 
	    		 //"token : "+token + "new token :"+newToken );
	    		  return newToken;
	    	}
	     return token;
	    }
	  return failtResponse;
 }
 
 /**
  * @author Anthony Scheeres
  */
private String askNewTokenForAccount(int id) {
	  MailController mailController = new MailController();
	  UserDAO userDatabase = new UserDAO();
	  String newToken = mailController.generateToken();
	  userDatabase.changeToken(newToken, id);
	  return newToken;
}
 
 
 
/**
*
* @author Anthony Scheeres
*  
* 
*
*/
public boolean checkCredentials(String username,String username2, String password, String password2){
return username.equals(username2) && password.equals(password2);

}


    /**
     * @author Anthony Scheeres
     * @throws Exception 
     */
    public String validateToken(String token) throws Exception {
        MailController mailController = new MailController();
        HashMap<String, List<String>> data = mailController.getTokens();

   	 String response = Response.fail.toString();
       
        
        
        for (int i = 0; i < data.get(User.token.toString()).size(); i++) {
        	String email = data.get(User.email.toString()).get(i);
        	String tokenFromDatabase = data.get(User.token.toString()).get(i);
        	String username = data.get(User.username.toString()).get(i); //use username to uniquely identify a user 
        	String yourDomain = getDomeinNameFromMail(email.toUpperCase());
        	
        	ValidateEmailModel validateEmailModel = new ValidateEmailModel(email, tokenFromDatabase, username, yourDomain, token);
        	
         response = isTokenValid(validateEmailModel);
    }
        return response ;
        
        
    }
    
    
    public String isTokenValid(	ValidateEmailModel validateEmailModel) throws Exception {
    	
    	String email = validateEmailModel.getEmail(); 
    	String tokenFromDatabase = validateEmailModel.getTokenFromDatabase();
    	String username = validateEmailModel.getUsername(); 
    	String yourDomain = validateEmailModel.getYourDomain(); 
    	String token = validateEmailModel.getToken();
    	 String response = Response.fail.toString();

         String role = "UNCLASSIFIED";
    	 
    	 
    	if (email != null && tokenFromDatabase != null) {
            if (token.equals(tokenFromDatabase)) {
                if ( yourDomain.equals(domain)) {
                
                	//give read permissions
                	giveRead2(username);
                    role = "USER";
                    
                    
                    
                    response = Response.success.toString();
                } else response ="domein invalid, should be: " + domain.toLowerCase();
                
            }

            givePermissionToThisAccount(token, role);
            
        }   return response;
    }
 
    
    
    /**
     * @author Anthony Scheeres
     */
    public void givePermissionToThisAccount(String token, String role) {
    	UserDAO userDAO= new UserDAO();
		TokenController tokkenController = new TokenController();
		
		long employeeId = Long.parseLong(tokkenController.tokenToUserId(token));
    	userDAO.updateUserRole(employeeId, role);
    }
    
    /**
     * @author Anthony Scheeres
     */
    private String getDomeinNameFromMail(String email){
    	return email.split("@")[1];
    }
    

    /**
     * @author Jesse Poleij, Anthony Scheeres
     */
    public Response handleRemoveUser(String u) {
        if(userDatabase.removeUserModel(u)) {
            return Response.success;
        } else {
            return Response.fail;
        }
    }
}