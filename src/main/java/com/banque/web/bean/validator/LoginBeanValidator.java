/**
 * Copyright :  <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.web.bean.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.banque.web.bean.LoginBean;

/**
 * Login bean validator.
 */
public class LoginBeanValidator implements Validator {

	/**
	 * Constructeur de l'objet.
	 */
	public LoginBeanValidator() {
		super();
	}

	@Override
	public boolean supports(Class<?> pArg0) {
		return LoginBean.class.equals(pArg0);
	}

	@Override
	public void validate(Object aLoginBean, Errors errors) {
		LoginBean lb = (LoginBean) aLoginBean;
		if (lb.getLogin() == null) {
			errors.rejectValue("login", null, "error.user.empty");
		}
		if (lb.getPassword() == null) {
			errors.rejectValue("password", null, "error.password.empty");
		}
	}

}
