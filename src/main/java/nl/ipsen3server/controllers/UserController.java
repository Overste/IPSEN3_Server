package nl.ipsen3server.controllers;

import java.util.List;

import nl.ipsen3server.dao.UserDAO;
import nl.ipsen3server.models.Response;
import nl.ipsen3server.models.ResponseModel;
import nl.ipsen3server.models.User;
import nl.ipsen3server.models.UserModel;

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
    public String handleShowUsers(String token) {
        int employeeId = Integer.parseInt(tokenController.tokenToUserId(token));
        if (!this.authenticationController.hasReadPermission(employeeId)) {
            return Response.fail.toString();
        }
        return this.userDAO.showUsers();
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
    public ResponseModel updateUserRole(long id, String userRole) {
        if (userDAO.updateUserRole(id, userRole)) {
            return new ResponseModel(Response.success.toString());
        } else {
            return new ResponseModel(Response.fail.toString());
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
