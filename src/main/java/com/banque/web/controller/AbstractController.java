/**
 * Copyright :  <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.web.controller;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

/**
 * Controller de base. <br/>
 */
@Controller
public abstract class AbstractController implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Log LOG = LogFactory.getLog(this.getClass());

	/**
	 * Constructeur de l'objet.
	 */
	protected AbstractController() {
		super();
	}

}
