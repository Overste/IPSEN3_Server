
package nl.ipsen3server.controlllers;


import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import nl.ipsen3server.dao.AuthenticationDAO;
import nl.ipsen3server.dao.UserDAO;
import nl.ipsen3server.models.DataModel;
import nl.ipsen3server.models.Response;
import nl.ipsen3server.models.UserModel;

/**
 * @author Anthony Scheeres
 */
public class UserController {
    private AuthenticationDAO authenticationDAO = new AuthenticationDAO();
    private TokenController tokenController = new TokenController();
    private UserDAO userDAO = new UserDAO();


    /**
     * @throws Exception
     * @author Anthony Scheeres
     */
    public String handleShowUsers(String token) throws Exception {
        int employeeId = Integer.parseInt(tokenController.tokenToUserId(token));
        AuthenticationController authenticationController = new AuthenticationController();
        if (!authenticationController.hasReadPermission(employeeId)) {
            return Response.fail.toString();
        }
        //"success");
        return userDAO.showUsers();

    }


    /**
     * @author Anthony Scheeres
     */
    private long createUserId(List<UserModel> list) {
        long id = 1;
        for (UserModel databaseModel : list) {
            if (id <= databaseModel.getId()) {
                id = databaseModel.getId() + 1;
            }
        }
        return id;
    }


    /**
     * @author Anthony Scheeres
     */
    public long createUserId2(List<String> list) {
        if (list == null) {
            return 1;
        }
        long id = 1;
        for (String databaseModel : list) {
            if (id <= Integer.valueOf(databaseModel)) {
                id = Integer.valueOf(databaseModel) + 1;
            }
        }
        return id;
    }


    /**
     * @author Anthony Scheeres
     */
    private UserModel createJUser(@NotNull String username,
                                  @NotNull String password,
                                  @Pattern(regexp = "^[0-9]*$") long id,
                                  List<Enum> rechten) {
        return new UserModel(username, password, id,
                rechten, null);
    }


    /**
     * @author Anthony Scheeres
     */
    public boolean checkIfUsernameExist(List<String> list, String username) {
        for (String name : list) {
            if (name.equals(username)) {

                return true;
            }
        }
        return false;
    }


    /**
     * @param id
     * @param userRole
     * @return
     * @author Valerie Timmerman
     */
    public String updateUserRole(long id, String userRole) {
        if (userDAO.updateUserRole(id, userRole)) {
            return Response.success.toString();
        } else {
            return Response.fail.toString();
        }
    }


    /**
     * @author Anthony Scheeres
     */
    public String giveResourceByPermission(boolean hasPermissionRead) throws Exception {
        //"handleShowUsers " + hasPermissionRead);
        if (hasPermissionRead) {
            //"show users");

            return userDAO.showUsers();
        }
        //"don't show users");
        return Response.fail.toString();
    }


    /**
     * @author Anthony Scheeres
     */
    public UserModel createNewUserModel(@NotNull String username, @NotNull String password) {
        ApplicationController applicationController = new ApplicationController();
        UserModel userModel = createJUser(username, password, createUserId(DataModel.getApplicationModel().getUsers()), new ArrayList<Enum>());
        applicationController.addUser(userModel, DataModel.getApplicationModel());
        return userModel;
    }


    /**
     * @author Anthony Scheeres
     */
    public int getUserById(long id, List<UserModel> list) {

        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).getId() == id) {
                return index;
            }

        }
        return 0;
    }

}
