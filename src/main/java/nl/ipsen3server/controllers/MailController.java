package nl.ipsen3server.controllers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.List;

import nl.ipsen3server.dao.PreparedStatementDatabaseUtilities;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import nl.ipsen3server.dao.DatabaseUtilities;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.MailModel;
import nl.ipsen3server.models.ServerModel;
/**
 * @author Anthony Scheeres
 */
public class MailController {
	private String tableName = "application_users";
	private int tokenLength = 20;
	private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());
	private PreparedStatementDatabaseUtilities preparedStatementDatabaseUtilities = new PreparedStatementDatabaseUtilities();
	private MailModel createMailModel(String username, String password) {
	return new MailModel(username, password);
	}

	 /**
	  * @author Anthony Scheeres
	  */
	 public boolean isValidEmailAddress(String email) {
		EmailValidator emailValidator = new EmailValidator();
		return emailValidator.isValid(email, null);
	 }
	 
	private String findValideToken(HashMap<String, List<String>> e1) {
		String token = null;
		boolean tokenConfirmed = false;

		while (!tokenConfirmed) {
			token = getAlphaNumericString1(tokenLength);
			tokenConfirmed = !tokenExist(e1, token);
		}
		return token;
	}
	 
	 /**
	  * @author Anthony Scheeres
	  */
	 public HashMap<String, List<String>> getTokens() {
		String query = String.format("SELECT username, token, email FROM %s;", tableName);
		DatabaseUtilities databaseUtilities = new DatabaseUtilities();
		HashMap<String, List<String>> result = null;

		try {
			DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
//			result = databaseUtilities.connectThisDatabase(databaseModel, query);
			result = preparedStatementDatabaseUtilities.connectDatabaseHashmap(
					databaseModel,
					query,
					new ArrayList<>()
			);


		}
		catch (Exception e2) {
			LOGGER.log(Level.SEVERE, "Error occur", e2.getMessage());
		}
		return result;
	 }
	 
	 
	 /**
	  * @author Anthony Scheeres
	  */
	 public String generateToken() {
		HashMap<String, List<String>> hashmap = getTokens();
		return findValideToken(hashmap);
	 }

	 /**
	  * @author Anthony Scheeres
	  */
	 private boolean tokenExist(HashMap<String, List<String>> e1, String token) {
	 	List<String> oldToken = e1.get("token");

		for(String token2: oldToken) {
			if (token.equals(token2)) {
				return true;
			}
		}
		return false;
	}

	 /**
	  * @author Anthony Scheeres
	  */
	// function to generate a random string of length n 
	 private static String getAlphaNumericString1(int n) {
	     // chose a Character random from this String 
	     String AlphaNumericString =
			 "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
			 "0123456789" +
			 "abcdefghijklmnopqrstuvxyz";

	     // create StringBuffer size of AlphaNumericString 
	     StringBuilder sb = new StringBuilder(n); 

	     for (int i = 0; i < n; i++) {
	         // generate a random number between 
	         // 0 to AlphaNumericString variable length 
	         int index = (int)(AlphaNumericString.length() * Math.random());

	         // add Character one by one in end of sb 
	         sb.append(AlphaNumericString.charAt(index));
	     }
	     return sb.toString();
	}

	public void createNewMailModel(String username, String password, ServerModel s) {
		ServerController s2 = new ServerController();
		s2.addMail(createMailModel(username, password), s);
	}
}
