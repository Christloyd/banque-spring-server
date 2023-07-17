/**
 * Copyright :  <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.web.bean;

import java.io.Serializable;

/**
 * Login Bean. <br/>
 */
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;
	private String password;

	/**
	 * Constructeur de l'objet.
	 */
	public LoginBean() {
		super();
	}

	/**
	 * Recupere la valeur du login.
	 *
	 * @return la valeur du login.
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Recupere la valeur du password.
	 *
	 * @return la valeur du password.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Modifie la valeur du login.
	 *
	 * @param aValue
	 *            la valeur du login.
	 */
	public void setLogin(String aValue) {
		if ((aValue == null) || (aValue.trim().length() == 0)) {
			this.login = null;
		} else {
			this.login = aValue;
		}
	}

	/**
	 * Modifie la valeur du password.
	 *
	 * @param aValue
	 *            la valeur du password.
	 */
	public void setPassword(String aValue) {
		if ((aValue == null) || (aValue.trim().length() == 0)) {
			this.password = null;
		} else {
			this.password = aValue;
		}
	}

}
