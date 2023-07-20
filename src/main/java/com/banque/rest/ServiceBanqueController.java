package com.banque.rest;

import com.banque.entity.CompteEntity;
import com.banque.entity.ICompteEntity;
import com.banque.entity.IOperationEntity;
import com.banque.entity.OperationEntity;
import com.banque.service.IAuthentificationService;
import com.banque.service.ICompteService;
import com.banque.service.IOperationService;
import com.banque.web.http.Login;
import com.banque.web.http.Virement;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/service-banque")
public class ServiceBanqueController {

    @Autowired
    @Qualifier("authentificationService")
    private IAuthentificationService serviceAuthentification;

    @Autowired
    @Qualifier("compteService")
    private ICompteService serviceCompte;

    @Autowired
    @Qualifier("operationService")
    private IOperationService serviceOperation;

    @PostMapping("/authentifier")
    public Integer authentifier(@RequestBody Login login) throws Exception {

        return serviceAuthentification.authentifier(login.getUnLogin() , login.getUnMdp()).getId();
    }

    @PostMapping("/selectCompte")
    public CompteEntity[] selectCompte(@RequestParam("unUtilisateurId") Integer unUtilisateurId) throws Exception {
        // Logique de sélection des comptes
        // ...
        List<ICompteEntity> comptes = serviceCompte.selectAll(unUtilisateurId);
        return comptes.toArray(new CompteEntity[0]);
    }
    
    @PostMapping("/selectCompteEtLibelle")
    public CompteEntity[] selectCompteEtLibelle(@RequestParam("unUtilisateurId") Integer unUtilisateurId, @RequestParam("unLibelle") String unLibelle) throws Exception {
        // Logique de sélection des comptes
        // ...
        List<ICompteEntity> comptes = serviceCompte.selectSearchCompte(unUtilisateurId, unLibelle);
        return comptes.toArray(new CompteEntity[0]);
    }
    
    
    @PostMapping("/selectOperation")
    public OperationEntity[] selectOperation(
        @RequestParam("unUtilisateurId") Integer unUtilisateurId,
        @RequestParam("unCompteId") Integer unCompteId,
        @RequestParam("dateDeb") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateDeb,
        @RequestParam("dateFin") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateFin,
        @RequestParam(value = "creditDebit", required = false) Boolean creditDebit
    ) throws Exception {
      
      
        List<IOperationEntity> operations;
        if (creditDebit == null) // si on envoie pas le creditDebit alors ca recupere tous, credit et debit
            operations = serviceOperation.selectCritere(unUtilisateurId, unCompteId, dateDeb, dateFin, true, true);
        else
            operations = serviceOperation.selectCritere(unUtilisateurId, unCompteId, dateDeb, dateFin, creditDebit, !creditDebit);

        return operations.toArray(new OperationEntity[0]);
    }
    
    @PostMapping("/virement")
    public OperationEntity[] doVirement(@RequestBody Virement virement ) throws Exception {
    	
    	List<IOperationEntity> operations;
    	
    	operations = serviceOperation.faireVirement(virement.getUnUtilisateurId(), virement.getUnCompteIdSrc(), virement.getUnCompteIdDst(), virement.getUnMontant());

    	return operations.toArray(new OperationEntity[0]);
    	
    }
    
}
