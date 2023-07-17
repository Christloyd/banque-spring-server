/**
 * Copyright :  <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.banque.entity.IOperationEntity;
import com.banque.entity.IUtilisateurEntity;
import com.banque.service.IOperationService;
import com.banque.web.bean.HistoriqueBean;
import com.banque.web.bean.validator.HistoriqueBeanValidator;

/**
 * Controller pour l'historique <br/>
 */
@Controller
@SessionAttributes(names = { "utilisateur" })
public class HistoriqueController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IOperationService service;

	/**
	 * Constructeur de l'objet.
	 */
	public HistoriqueController() {
		super();
	}

	@RequestMapping(value = "/historique.smvc", method = RequestMethod.GET)
	public String showHistorique(
			@RequestParam("inNumeroCompte") String inNumeroCompte, Model model,
			ModelMap modelMap) {
		this.LOG.debug("--> Passage dans showHistorique");
		HistoriqueBean historiqueBean = new HistoriqueBean(inNumeroCompte);
		historiqueBean.setCredit(Boolean.TRUE);
		historiqueBean.setDebit(Boolean.TRUE);
		model.addAttribute("historiqueBean", historiqueBean);
		// c'est l'annotation qui fait le lien avec la session
		IUtilisateurEntity utilisateur = (IUtilisateurEntity) modelMap
				.get("utilisateur");
		if (utilisateur == null) {
			this.LOG.error("Erreur : utilisateur non connecte");
			return "index";
		}
		try {
			List<IOperationEntity> listeOperations = this.service.selectAll(
					utilisateur.getId(), historiqueBean.getCptId());
			model.addAttribute("listeOperations", listeOperations);
		} catch (Exception e) {
			this.LOG.error("Erreur:", e);
		}
		return "comptes/historique";
	}

	/**
	 * Soumet le formulaire.
	 *
	 * @param historiqueBean
	 *            le bean qui represente le formulaire
	 * @param modelMap
	 *            le model map
	 * @param bindingResult
	 *            result binding
	 * @return la ou aller
	 */
	@RequestMapping(value = "/dohistorique.smvc", method = RequestMethod.POST)
	public String doHistorique(
			@ModelAttribute("historiqueBean") HistoriqueBean historiqueBean,
			ModelMap modelMap, BindingResult bindingResult) {
		this.LOG.debug("--> Passage dans dohistorique");
		// c'est l'annotation qui fait le lien avec la session
		IUtilisateurEntity utilisateur = (IUtilisateurEntity) modelMap
				.get("utilisateur");
		if (utilisateur == null) {
			this.LOG.error("Erreur : utilisateur non connecte");
			return "index";
		}

		HistoriqueBeanValidator validator = new HistoriqueBeanValidator();
		validator.validate(historiqueBean, bindingResult);

		if (!bindingResult.hasErrors()) {
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.setLenient(false);
				Date dateDebut = null;
				if (historiqueBean.getDateDebut() != null) {
					dateDebut = sdf.parse(historiqueBean.getDateDebut());
				}
				Date dateFin = null;
				if (historiqueBean.getDateFin() != null) {
					dateFin = sdf.parse(historiqueBean.getDateFin());
				}
				if ((dateDebut != null) && (dateFin == null)) {
					dateFin = new Date(System.currentTimeMillis());
				}
				if ((dateDebut == null) && (dateFin != null)) {
					dateDebut = new Date(System.currentTimeMillis());
				}

				List<IOperationEntity> listeOperations = this.service
						.selectCritere(utilisateur.getId(),
								historiqueBean.getCptId(), dateDebut, dateFin,
								(historiqueBean.getCredit() != null)
								&& historiqueBean.getCredit()
								.booleanValue(),
								(historiqueBean.getDebit() != null)
								&& historiqueBean.getDebit()
								.booleanValue());

				modelMap.addAttribute("listeOperations", listeOperations);
			} catch (Exception e) {
				bindingResult.addError(new ObjectError("globalError", null,
						new Object[] { e.getMessage() }, "error.technical"));
				this.LOG.error("Erreur:", e);
			}
		}
		return "comptes/historique";
	}
}
