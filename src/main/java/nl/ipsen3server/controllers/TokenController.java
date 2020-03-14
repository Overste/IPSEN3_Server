
package nl.ipsen3server.controllers;

import java.util.logging.Level;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import nl.ipsen3server.dao.UserDAO;
import nl.ipsen3server.models.Response;
public class TokenController {

	private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());
	/**
	* @author Anthony Scheeres
	* get tokens from database
	*/
	private HashMap<String, List<String>> getTokens() throws Exception {
		UserDAO userDAO = new UserDAO();
		return userDAO.getTokens();
	}

	private boolean isStringEmty(String token){
			  return token.length()!=0;	
		 }
	 
	/**
	*
	* @author Anthony Scheeres
	*
	*
	*looks if token exist in hashmap
	*/
	private String findValideTokenInHashmap(HashMap<String, List<String>> hashmap, String token) {
		String result = null ;

		for (int index = 0; index <hashmap.get("token").size(); index++) {
			String myToken =  hashmap.get("token").get(index);
			if (isStringEmty(myToken)) {
				if (myToken.equals(token)) {
				   result = hashmap.get("user_id").get(index);
				}
			}
		}
		return result;
	}

	/**
	*
	* @author Anthony Scheeres
	*
	*/
	public String tokenToUserId(String token) {
		HashMap < String, List < String >> hashmap = null;
		try {
			hashmap = getTokens();return findValideTokenInHashmap( hashmap, token);
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error occur", e);
		}
		return Response.fail.toString();
	}
}
