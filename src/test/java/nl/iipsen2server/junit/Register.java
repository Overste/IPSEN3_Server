package nl.iipsen2server.junit;


import nl.ipsen3server.controllers.AccountController;
import nl.ipsen3server.models.UserModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Register {

	@Test
	void testInOutputValidator() {
		AccountController accountController  = new 	AccountController ();
		assertEquals(false, accountController.checkInputValide(new UserModel()));
	}

}
