/**
 * Copyright :  <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.banque.entity.ICompteEntity;
import com.banque.entity.IUtilisateurEntity;
import com.banque.service.ICompteService;
import com.banque.service.IOperationService;
import com.banque.web.bean.VirementBean;
import com.banque.web.bean.validator.VirementBeanValidator;

/**
 * Controller pour le virement. <br/>
 */
@Controller
@SessionAttributes(names = { "utilisateur" })
public class VirementController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ICompteService serviceCompte;
	@Autowired
	private IOperationService serviceOperation;

	/**
	 * Constructeur de l'objet.
	 */
	public VirementController() {
		super();
	}

	@RequestMapping(value = "/virement.smvc", method = RequestMethod.GET)
	public String showVirement(Model model, ModelMap modelMap) {
		this.LOG.debug("--> Passage dans showVirement");
		VirementBean virementBean = new VirementBean();
		model.addAttribute("virementBean", virementBean);
		// c'est l'annotation qui fait le lien avec la session
		IUtilisateurEntity utilisateur = (IUtilisateurEntity) modelMap
				.get("utilisateur");
		if (utilisateur == null) {
			this.LOG.error("Erreur : utilisateur non connecte");
			return "index";
		}
		try {
			List<ICompteEntity> listeCompte = this.serviceCompte
					.selectAll(utilisateur.getId());
			model.addAttribute("listeCompte", listeCompte);
		} catch (Exception e) {
			this.LOG.error("Erreur:", e);
		}
		return "comptes/virement";
	}

	/**
	 * Soumet le formulaire.
	 *
	 * @param historiqueBean
	 *            le bean qui represente le formulaire
	 * @param modelMap
	 *            le model map
	 * @param model
	 *            le model
	 * @param bindingResult
	 *            result binding
	 * @return la ou aller
	 */
	@RequestMapping(value = "/dovirement.smvc", method = RequestMethod.POST)
	public String doVirement(
			@ModelAttribute("virementBean") VirementBean virementBean,
			ModelMap modelMap, Model model, BindingResult bindingResult) {
		this.LOG.debug("--> Passage dans doVirement");
		// c'est l'annotation qui fait le lien avec la session
		IUtilisateurEntity utilisateur = (IUtilisateurEntity) modelMap
				.get("utilisateur");
		if (utilisateur == null) {
			this.LOG.error("Erreur : utilisateur non connecte");
			return "index";
		}

		try {
			// On replace la liste des comptes
			List<ICompteEntity> listeCompte = this.serviceCompte
					.selectAll(utilisateur.getId());
			model.addAttribute("listeCompte", listeCompte);
		} catch (Exception e) {
			this.LOG.error("Erreur:", e);
		}

		VirementBeanValidator validator = new VirementBeanValidator();
		validator.validate(virementBean, bindingResult);

		if (!bindingResult.hasErrors()) {
			try {
				this.serviceOperation.faireVirement(utilisateur.getId(),
						Integer.valueOf(virementBean.getCptSrcId()),
						Integer.valueOf(virementBean.getCptDstId()),
						Double.valueOf(virementBean.getMontant()));
				model.addAttribute("message", "virement.ok");
			} catch (Exception e) {
				bindingResult.addError(new ObjectError("globalError", null,
						new Object[] { e.getMessage() }, "error.technical"));
				this.LOG.error("Erreur:", e);
			}
		}
		return "comptes/virement";
	}
}
