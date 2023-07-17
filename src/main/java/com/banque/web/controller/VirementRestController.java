package com.banque.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.banque.entity.ICompteEntity;
import com.banque.entity.IUtilisateurEntity;
import com.banque.service.ICompteService;
import com.banque.service.IOperationService;
import com.banque.web.bean.VirementBean;
import com.banque.web.bean.validator.VirementBeanValidator;

@RestController
@SessionAttributes(names = { "utilisateur" })
@RequestMapping("/api")
public class VirementRestController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8334378969664217935L;
	@Autowired
	private ICompteService serviceCompte;
	@Autowired
	private IOperationService serviceOperation;

	public VirementRestController() {
		super();
	}

	@GetMapping("/virement")
	public String showVirement(Model model, ModelMap modelMap) {
		this.LOG.debug("--> Passage dans showVirement");
		VirementBean virementBean = new VirementBean();
		model.addAttribute("virementBean", virementBean);
		IUtilisateurEntity utilisateur = (IUtilisateurEntity) modelMap.get("utilisateur");
		if (utilisateur == null) {
			this.LOG.error("Erreur : utilisateur non connecte");
			return "index";
		}
		try {
			List<ICompteEntity> listeCompte = this.serviceCompte.selectAll(utilisateur.getId());
			model.addAttribute("listeCompte", listeCompte);
		} catch (Exception e) {
			this.LOG.error("Erreur:", e);
		}
		return "comptes/virement";
	}

	@PostMapping("/dovirement")
	public String doVirement(@RequestBody VirementBean virementBean, ModelMap modelMap, Model model,
			BindingResult bindingResult) {
		this.LOG.debug("--> Passage dans doVirement");
		IUtilisateurEntity utilisateur = (IUtilisateurEntity) modelMap.get("utilisateur");
		if (utilisateur == null) {
			this.LOG.error("Erreur : utilisateur non connecte");
			return "index";
		}

		try {
			List<ICompteEntity> listeCompte = this.serviceCompte.selectAll(utilisateur.getId());
			model.addAttribute("listeCompte", listeCompte);
		} catch (Exception e) {
			this.LOG.error("Erreur:", e);
		}

		VirementBeanValidator validator = new VirementBeanValidator();
		validator.validate(virementBean, bindingResult);

		if (!bindingResult.hasErrors()) {
			try {
				this.serviceOperation.faireVirement(utilisateur.getId(), Integer.valueOf(virementBean.getCptSrcId()),
						Integer.valueOf(virementBean.getCptDstId()), Double.valueOf(virementBean.getMontant()));
				model.addAttribute("message", "virement.ok");
			} catch (Exception e) {
				bindingResult.addError(new ObjectError("globalError", null, new Object[] { e.getMessage() },
						"error.technical"));
				this.LOG.error("Erreur:", e);
			}
		}
		return "comptes/virement";
	}
}
