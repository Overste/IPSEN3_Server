package nl.ipsen3server.dao;

import java.util.ArrayList;
import java.util.List;

import nl.ipsen3server.controlllers.AuthenticationController;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.Permission;
import nl.ipsen3server.models.Response;
import nl.ipsen3server.models.AccountModel;

import javax.xml.crypto.Data;

public class AuthenticationDAO {

	 String tableName = "application_users";
	 private DatabaseModel databaseModel = DataModel.getApplicationModel().getServers().get(0).getDatabase().get(0);
	 AuthenticationController autenticationController = new AuthenticationController();
	 private UserDAO userDAO = new UserDAO();
	 DatabaseUtilities databaseUtilities = new DatabaseUtilities();


	 /**
	  *
	  * @author Anthony Scheeres
	 * @return 
	  * 
	  *
	  */
	 public boolean giveRead2(AccountModel u) {
		  String query2 = "select permission from application_users where username=?;";
		  Enum permission = Permission.READ;
	 if (!userDAO.hasPermission(permission.toString(), u.getUsername(), query2)) {
		  try {
			givePermission(u, permission);
			 return true;
		} catch (Exception e) {
		}
	 }return false;
	 }


	 /**
	 *
	 * @author Anthony Scheeres
	 *  
	 * 
	 *
	 */
	 public boolean giveWrite2(AccountModel u) {
		  String query2 = "select permission from application_users where username=?;";
		  Enum permission = Permission.WRITE;
	 if (!userDAO.hasPermission(permission.toString(), u.getUsername(), query2)) {
		  try {
			  givePermission(u, permission);
			 return true;
		} catch (Exception e) {
		
		}
		  
	 }return false;
	 }

	 


	 /**
	  * @author Anthony Scheeres
	  */
	 public boolean giveDelete2(AccountModel accountModel) {
		  String query2 = "select permission from application_users where username=?;";
		  Enum permission = Permission.DELETE;
		  if (!userDAO.hasPermission(Permission.DELETE.toString(), accountModel.getUsername(), query2)) {
		 	  try {
		 		  givePermission(accountModel, permission);
		 		 return true;
		 	} catch (Exception e) {
		 	}
		 	  
		  }return false;
	 }







	 /**
	  *
	  * @author Anthony Scheeres
	  *
	  */
	 private void givePermission(AccountModel u, Enum e) throws Exception {
		  PreparedStatmentDatabaseUtilities databaseController = new PreparedStatmentDatabaseUtilities();
		  List < String > list = new ArrayList < String > ();
		  String query2 = String.format("UPDATE application_users SET permission = array_append(permission,'%s') WHERE username = ?;", e);
		  list.add(u.getUsername());
		  databaseController.connectDatabaseJson(databaseModel, query2, list, false);
	 }


	/**
	 * takes in a userId and a permission and checks the database to see if this user has this permission
	 *
	 * @author AnthonySchuijlenburg
	 *
	 * @param userId The id of the user of which it's permissions need to be checked
	 * @param permission The permission it need te checked against
	 * @return true or false depending on the users rights
	 */
	public boolean hasEnumHandler(int userId, String permission) {
		permission = permission.toLowerCase();
	  	String query = "SELECT has_"+ permission +" FROM application_users WHERE user_id = " + userId + ";";
	  	if (databaseUtilities.connectToDatabase(databaseModel, query, "SELECT").contains("true")){
			return true;
		}
	  	return false;
}
	
	
	 /**
	  *
	  * @author Anthony Scheeres
	  *  
	  */
	public String userIDtoUsername(String userID){
		PreparedStatmentDatabaseUtilities databaseController = new PreparedStatmentDatabaseUtilities();
		List < String > list = new ArrayList < String > ();
		String query = "select username, user_id from application_users where user_id = ?";
		list.add(userID);

		try {
			return databaseController.connectDatabaseJson(databaseModel, query, list, false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.fail.toString();
	}
}
  
