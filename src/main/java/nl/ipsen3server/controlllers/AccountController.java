<<<<<<< HEAD:src/main/java/nl/iipsen2server/controlllers/AccountController.java
package main.java.nl.iipsen2server.controlllers;

import java.util.HashMap;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import main.java.nl.iipsen2server.models.DataModel;
import main.java.nl.iipsen2server.models.Permission;
import main.java.nl.iipsen2server.models.Response;
import main.java.nl.iipsen2server.models.RestApiModel;
import main.java.nl.iipsen2server.models.User;
import main.java.nl.iipsen2server.models.UserModel;
import main.java.nl.iipsen2server.dao.PermissionDAO;
import main.java.nl.iipsen2server.dao.UserDAO;
import main.java.nl.iipsen2server.models.AccountModel;


/**
 * @author Anthony Scheeres
 */


public class AccountController {
private UserDAO userDatabase = new UserDAO();
private PermissionDAO permissionDatabase = new PermissionDAO();




/**
 * @return
 * @author Anthony Scheeres
 * @throws Exception 
 */
    public void giveRead2(String username) throws Exception {
        permissionDatabase.giveRead(username);
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
			e.printStackTrace();
		}
        
        return result;
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean checkInputValide(String email, String password) {
        MailController m = new MailController();
        if (!m.isValidEmailAddress(email)) {
            return false;
        }

        if (password.length() == 0) {
            return false;
        }
        return true;
    }


    /**
     * @author Anthony Scheeres
     * @throws Exception 
     */
    public String handleCreateUserModel2(UserModel u) throws Exception {
    	String fail = Response.fail.toString();
        if (!checkInputValide(u.getEmail(), u.getPassword())) {
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
	 System.out.println("token : "+token + "permission :"+permission );
	  if (checkCredentials(username, username2, passwordFromDatabase,  passwordFromClient)) {
	    	boolean hasPermission = permission.length() ==0;
	    	if(hasPermission) {
	    
	    		return token;
	    	}
	    	
	   
	    	
	    	if (permission.contains("t") || token.equals(null)) {
	    		 String newToken =  askNewTokenForAccount(Integer.parseInt(UserId));		 
	    		 System.out.println("token : "+token + "new token :"+newToken );
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
  if (username.equals(username2) && password.equals(password2)) {
   return true;
  }
  return false;
 }

    /**
     * @author Anthony Scheeres
     * @throws Exception 
     */
    public String validateToken(String token) throws Exception {
        MailController mailController = new MailController();
        HashMap<String, List<String>> data = mailController.getTokens();
        String domain = "OM.NL";
        for (int i = 0; i < data.get(User.token.toString()).size(); i++) {
        	String email = data.get(User.email.toString()).get(i);
        	String tokenFromDatabase = data.get(User.token.toString()).get(i);
        	
        	
            if (email != null && tokenFromDatabase != null) {
                if (token.equals(tokenFromDatabase)) {
                	String yourDomain = getDomeinNameFromMail(email.toUpperCase());
                    if ( yourDomain.equals(domain)) {
                    	String accountModel = data.get(User.username.toString()).get(i); //use username to uniquely identify a user 
                        giveRead2(accountModel);
                        return Response.success.toString();
                    } else return "domein invalid, should be: " + domain;
                }
            }
        }
        return Response.fail.toString();
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
    public void handleRemoveUser(AccountModel u, String token) {
        userDatabase.removeUserModel(u);
    }
=======
package nl.ipsen3server.controlllers;

import java.util.HashMap;
import java.util.List;

import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.Response;
import nl.ipsen3server.models.RestApiModel;
import nl.ipsen3server.models.User;
import nl.ipsen3server.models.UserModel;
import nl.ipsen3server.dao.PermissionDAO;
import nl.ipsen3server.dao.UserDAO;
import nl.ipsen3server.models.AccountModel;


/**
 * @author Anthony Scheeres
 */


public class AccountController {
private UserDAO userDatabase = new UserDAO();
private PermissionDAO permissionDatabase = new PermissionDAO();


/**
*
* @author Anthony Scheeres

*
*
*/

/**
 * @return
 * @author Anthony Scheeres
 */
    public boolean giveRead2(String username) {
        return permissionDatabase.giveRead(username);
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean giveWrite2(String user) {
        return permissionDatabase.giveWrite(user);
    }

    /**
     * @author Anthony Scheeres
     */
    public boolean giveDelete2(String user) {
        return permissionDatabase.giveDelete(user);
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
			e.printStackTrace();
		}
        
        return result;
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean checkInputValide(String email, String password) {
        MailController m = new MailController();
        if (!m.isValidEmailAddress(email)) {
            return false;
        }

        if (password.length() == 0) {
            return false;
        }
        return true;
    }


    /**
     * @author Anthony Scheeres
     * @throws Exception 
     */
    public String handleCreateUserModel2(UserModel u) throws Exception {
    	String fail = Response.fail.toString();
        if (!checkInputValide(u.getEmail(), u.getPassword())) {
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
	 System.out.println("token : "+token + "permission :"+permission );
	  if (checkCredentials(username, username2, passwordFromDatabase,  passwordFromClient)) {
	    	boolean hasPermission = permission.length() ==0;
	    	if(hasPermission) {
	    
	    		return token;
	    	}
	    	
	   
	    	
	    	if (permission.contains("t") || token.equals(null)) {
	    		 String newToken =  askNewTokenForAccount(Integer.parseInt(UserId));		 
	    		 System.out.println("token : "+token + "new token :"+newToken );
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
  if (username.equals(username2) && password.equals(password2)) {
   return true;
  }
  return false;
 }

    /**
     * @author Anthony Scheeres
     */
    public String validateToken(String token) {
        MailController mailController = new MailController();
        HashMap<String, List<String>> data = mailController.getTokens();
        String domain = "OM.NL";
        for (int i = 0; i < data.get(User.token.toString()).size(); i++) {
        	String email = data.get(User.email.toString()).get(i);
        	String tokenFromDatabase = data.get(User.token.toString()).get(i);
        	
        	
            if (email != null && tokenFromDatabase != null) {
                if (token.equals(tokenFromDatabase)) {
                	String yourDomain = getDomeinNameFromMail(email.toUpperCase());
                    if ( yourDomain.equals(domain)) {
                    	String accountModel = data.get(User.username.toString()).get(i); //use username to uniquely identify a user 
                        giveRead2(accountModel);
                        return Response.success.toString();
                    } else return "domein invalid, should be: " + domain;
                }
            }
        }
        return Response.fail.toString();
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
    public void handleRemoveUser(AccountModel u, String token) {
        userDatabase.removeUserModel(u);
    }
>>>>>>> master:src/main/java/nl/ipsen3server/controlllers/AccountController.java
}