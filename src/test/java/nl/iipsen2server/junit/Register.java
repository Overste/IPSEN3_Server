package test.java.nl.iipsen2server.junit;

import org.junit.Test;

import main.java.nl.iipsen2server.controlllers.AccountController;
import static org.junit.Assert.assertEquals;

class Register {

	@Test
	void testInOutputValidator() {
		AccountController accountController  = new 	AccountController ();
		String email = "info@anthonyscheeres.nl";
		String password = "";
		assertEquals(false, accountController.checkInputValide(email, password));
	}

}
