package nl.iipsen2server.junit;


import nl.ipsen3server.controlllers.AccountController;
import nl.ipsen3server.models.User;
import nl.ipsen3server.models.UserModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Register {

	@Test
	void testInOutputValidator() {
		AccountController accountController  = new 	AccountController ();
		UserModel userModel = new UserModel();
		userModel.setEmail("info@anthonyscheeres.nl");
		userModel.setPassword("");

		assertEquals(false, accountController.checkInputValide(userModel));
	}

}
