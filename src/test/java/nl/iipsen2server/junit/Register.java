package test.java.nl.iipsen2server.junit;


import main.java.nl.iipsen2server.controlllers.AccountController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Register {

	@Test
	void testInOutputValidator() {
		AccountController accountController  = new 	AccountController ();
		String email = "info@anthonyscheeres.nl";
		String password = "";
		assertEquals(false, accountController.checkInputValide(email, password));
	}

}
