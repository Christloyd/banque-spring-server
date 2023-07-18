package com.banque.rest;

import com.banque.entity.CompteEntity;
import com.banque.entity.OperationEntity;
import java.util.Date;

public interface IServiceBanque {

    public Integer authentifier(String unLogin, String unMdp) throws Exception;

    public CompteEntity[] selectCompte(Integer unUtilisateurId) throws Exception;

    public OperationEntity[] selectOperation(Integer unUtilisateurId, Integer unCompteId, Date dateDeb, Date dateFin, Boolean creditDebit) throws Exception;
}
