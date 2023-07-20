package com.banque.rest;

import com.banque.entity.CompteEntity;
import com.banque.entity.OperationEntity;
import com.banque.web.http.Virement;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestBody;

public interface IServiceBanque {

    public Integer authentifier(String unLogin, String unMdp) throws Exception;

    public CompteEntity[] selectCompte(Integer unUtilisateurId) throws Exception;

    public OperationEntity[] selectOperation(Integer unUtilisateurId, Integer unCompteId, Date dateDeb, Date dateFin, Boolean creditDebit) throws Exception;
    
    public CompteEntity[] doVirement(@RequestBody Virement virement ) throws Exception;
}
