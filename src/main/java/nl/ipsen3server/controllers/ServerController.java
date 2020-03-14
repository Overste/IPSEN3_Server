package nl.ipsen3server.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.DatabaseModel;
import nl.ipsen3server.models.MailModel;
import nl.ipsen3server.models.RestApiModel;
import nl.ipsen3server.models.ServerModel;

/**
*
* @author Anthony Scheeres
*
*/
public class ServerController {
	/**
	 *
	 * @author Anthony Scheeres
	 *
	 */
	private ServerModel createServer(List<RestApiModel> api, List<DatabaseModel> databases, @NotNull long id){
		return new ServerModel(api, databases, null, id);
	}

	/**
	 *
	 * @author Anthony Scheeres
	 *
	 */
	public ServerModel createNewServer() {
		ApplicationController applicationController = new ApplicationController();
		ServerModel server = createServer(new ArrayList<>(), new ArrayList<>(),createServerId(new ArrayList<>()));
		applicationController.add(server, DataModel.getApplicationModel());
		return server;
	}
	
	/**
	 *
	 * @author Anthony Scheeres
	 *
	 */
	 public void addDatabase(DatabaseModel database , ServerModel server) {
		server.getDatabase().add(database);
	 }
	 
	 /**
	 *
	 * @author Anthony Scheeres
	 *
	 */
	 private long createServerId(List<ServerModel> list){
		 long id = 1;
		 for (ServerModel databaseModel : list) {
			 if (id <= databaseModel.getId()) {
				 id = databaseModel.getId()+1;
			 }
		 }
		 return id;
	 }
	 
	 public void addMail(MailModel mail, ServerModel server) {
		 server.setMail(mail);
	 }
	 
	/**
	* @author Anthony Scheeres
	*/
	public int getServerById(List<ServerModel> list, long id) {
		for(int index=0; index< list.size(); index++){
			if (list.get(index).getId() == id) {
				return index;
			}
		}
		return 0;
	}

	 /**
	  * @author Anthony Scheeres
	  */
	 public void addRestApi(RestApiModel raoi , ServerModel serverModel) {
			serverModel.getRestApi().add(raoi);
	 }
}
