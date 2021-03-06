package nl.ipsen3server.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import nl.ipsen3server.models.ApplicationModel;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.LogModel;
import nl.ipsen3server.models.ServerModel;
import nl.ipsen3server.models.UserModel;

/**
 * @author Anthony Scheeres
 */
public class ApplicationController {
	
	/**
	 * @author Anthony Scheeres
	 * @return ApplicationModel
	 */	
	public ApplicationModel createNewApplicationModel(String name) {
		ApplicationModel applicationModel= createApplication(
			new ArrayList<>(),
			new ArrayList<>(),
			new ArrayList<>(),
			name,
			null
		);
		DataModel.setApplicationModel(applicationModel);
		return applicationModel;
	}

	/**
	 * @author Anthony Scheeres
	 * @param currentUser current active user
	 */
	private ApplicationModel createApplication(
		List<UserModel> users,
		List<LogModel> logs,
		@NotNull List<ServerModel> servers,
		String name,
		UserModel currentUser
	){
		return new  ApplicationModel(users, logs, servers, name, currentUser);
	}
	 
	 /**
	 * @author Anthony Scheeres
	 */
	public void addUser(UserModel u2, ApplicationModel app){
		app.getUsers().add(u2);
	 }
	
	/**
	 * @author Anthony Scheeres
	 */
	public void add(ServerModel s , ApplicationModel a){
		a.getServers().add(s);
	}	
}
