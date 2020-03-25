package nl.ipsen3server.controllers;

import java.util.List;

import nl.ipsen3server.dao.UserDAO;
import nl.ipsen3server.models.*;

import javax.ws.rs.core.Response;

/**
 * @author Anthony Scheeres
 */
public class UserController {
    private TokenController tokenController = new TokenController();
    private UserDAO userDAO = new UserDAO();
    private AuthenticationController authenticationController = new AuthenticationController();

    /**
     * @author Anthony Scheeres
     */
    public Response handleShowUsers(String token) {
        int employeeId = Integer.parseInt(tokenController.tokenToUserId(token));
        if (!this.authenticationController.hasReadPermission(employeeId)) {
            return Response.status(Response.Status.valueOf("fail")).build();
        }
        return Response.ok(this.userDAO.showUsers()).build();
    }

    /**
     * @author Anthony Scheeres
     */
    public long createUserId(List<String> list) {
        if (list == null) {
            return 1;
        }
        long id = 1;
        for (String databaseModel : list) {
            if (id <= Integer.parseInt(databaseModel)) {
                id = Integer.parseInt(databaseModel) + 1;
            }
        }
        return id;
    }

    /**
     * @param id user id
     * @param userRole role type
     * @return ResponseModel
     * @author Valerie Timmerman
     */
    public Response updateUserRole(long id, String userRole) {
        if (userDAO.updateUserRole(id, userRole)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * @param id user
     * @author Thomas Warbout
     */
    public String showOneUserPermission(int id) {
        return this.userDAO.showOneUserPermission(id);
    }

    public javax.ws.rs.core.Response getUserInfo(String token) {
        int userId = Integer.parseInt(tokenController.tokenToUserId(token));
        String data = this.showOneUserPermission(userId);
        data = data.replaceAll("\\{", "")
                .replaceAll("}", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "");
        String[] datasets = data.split(",");
        UserModel userdata = new UserModel();
        for(String info: datasets) {
            if(info.contains("email")) {
                userdata.setEmail(cleanInfo(info));
            } else if(info.contains("username")) {
                userdata.setUsername(cleanInfo(info));
            }
        }
        return javax.ws.rs.core.Response.ok(userdata).build();
    }

    private String cleanInfo(String info) {
        info = info.replaceAll("\"", "");
        String[] infoArray = info.split(":");
        return infoArray[1];
    }

}
