/**
 * Copyright :  <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.banque.entity.IUtilisateurEntity;
import com.banque.service.IAuthentificationService;
import com.banque.service.ex.MauvaisMotdepasseException;
import com.banque.service.ex.UtilisateurInconnuException;
import com.banque.web.bean.LoginBean;
import com.banque.web.bean.validator.LoginBeanValidator;

/**
 * Controller de login. <br/>
 */
@Controller
@SessionAttributes(names = { "utilisateur" })
public class LoginController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired(required = true)
	@Qualifier("authentificationService")
	private IAuthentificationService service;

	/**
	 * Constructeur de l'objet.
	 */
	public LoginController() {
		super();
	}

	/**
	 * S'affiche quand on appel l'URL en get. Place le bean dans le modele.
	 *
	 * @param model
	 *            le model
	 * @return la ou aller
	 */
	@RequestMapping(value = "/login.smvc", method = RequestMethod.GET)
	public String showLogin(Model model) {
		this.LOG.debug("--> Passage dans showLogin");
		model.addAttribute("loginBean", new LoginBean());
		return "login";
	}

	/**
	 * Soumet le formulaire.
	 *
	 * @param loginBean
	 *            le bean qui represente le formulaire
	 * @param modelMap
	 *            le model map
	 * @param bindingResult
	 *            result binding
	 * @return la ou aller
	 */
	@RequestMapping(value = "/dologin.smvc", method = RequestMethod.POST)
	public String doLogin(@ModelAttribute("loginBean") LoginBean loginBean,
			ModelMap modelMap, BindingResult bindingResult) {
		this.LOG.debug("--> Passage dans doLogin");
		LoginBeanValidator validator = new LoginBeanValidator();
		validator.validate(loginBean, bindingResult);

		if (!bindingResult.hasErrors()) {
			try {
				IUtilisateurEntity utilisateur = this.service.authentifier(
						loginBean.getLogin(), loginBean.getPassword());
				// Si tout va bien on sauvegarde l'entite dans la session
				// c'est l'annotation qui fait le lien avec la session
				modelMap.addAttribute("utilisateur", utilisateur);
			} catch (MauvaisMotdepasseException e) {
				bindingResult.addError(new ObjectError("globalError",
						"error.wrong.password"));
				this.LOG.error("Erreur:", e);
				return "login";
			} catch (UtilisateurInconnuException e) {
				bindingResult.addError(new ObjectError("globalError",
						"error.user.unknown"));
				this.LOG.error("Erreur:", e);
				return "login";
			} catch (Exception e) {
				bindingResult.addError(new ObjectError("globalError", null,
						new Object[] { e.getMessage() }, "error.technical"));
				this.LOG.error("Erreur:", e);
				return "login";
			}

			return "menu";
		}
		return "login";
	}

}
