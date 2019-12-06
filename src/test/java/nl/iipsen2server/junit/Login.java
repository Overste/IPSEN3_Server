package test.java.nl.iipsen2server.junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.nl.iipsen2server.controlllers.AccountController;

class Login {

	
	 /**
     * @author Anthony Scheeres
     */
	@Test
	void testCompareLoginCredentials() {
		AccountController accountController  = new AccountController ();
		String username = "Anthony";
		String username2 = "Anthony";
		String password = "passw0rd"; 
		String password2 = "passw0rd";
		assertEquals(true, accountController.checkCredentials(username, username2, password, password2));
	}

}
