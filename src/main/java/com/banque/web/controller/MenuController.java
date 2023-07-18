/**
 * Copyright :  <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.banque.entity.IUtilisateurEntity;

/**
 * Controller qui va afficher le menu. <br/>
 */
@Controller
@RequestMapping(value = "/menu.smvc")
@SessionAttributes(names = { "utilisateur" })
public class MenuController extends AbstractController {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur.
	 */
	public MenuController() {
		super();
	}

	@GetMapping
	public String showMenu(ModelMap modelMap) {
		this.LOG.debug("--> Passage dans showMenu");
		// c'est l'annotation qui fait le lien avec la session
		IUtilisateurEntity utilisateur = (IUtilisateurEntity) modelMap
				.get("utilisateur");
		if (utilisateur == null) {
			this.LOG.error("Erreur : utilisateur non connecte");
			return "index";
		}

		return "menu";
	}
}
