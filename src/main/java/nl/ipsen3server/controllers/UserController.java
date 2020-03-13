
package nl.ipsen3server.controllers;

import java.util.List;

import nl.ipsen3server.dao.UserDAO;
import nl.ipsen3server.models.Response;
import nl.ipsen3server.models.ResponseModel;

/**
 * @author Anthony Scheeres
 */
public class UserController {

    private TokenController tokenController = new TokenController();
    private UserDAO userDAO = new UserDAO();
    private AuthenticationController authenticationController = new AuthenticationController();

    /**
     * @throws Exception
     * @author Anthony Scheeres
     */
    public String handleShowUsers(String token) throws Exception {
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
     * @param id
     * @param userRole
     * @return
     * @author Valerie Timmerman
     */
    public ResponseModel updateUserRole(long id, String userRole) {
        if (userDAO.updateUserRole(id, userRole)) {
            return new ResponseModel(Response.success.toString());
        } else {
            return new ResponseModel(Response.fail.toString());
        }
    }

}
