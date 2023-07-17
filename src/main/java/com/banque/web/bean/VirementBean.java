/**
 * Copyright :  <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.web.bean;

import java.io.Serializable;

/**
 * Bean pour le virement. <br/>
 */
public class VirementBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cptSrcId;
	private String cptDstId;
	private String montant;

	/**
	 * Constructeur de l'objet.
	 */
	public VirementBean() {
		super();
	}

	/**
	 * Indique si il n'y a acune information dans le bean.
	 *
	 * @return true si tous les champs du beans sont null
	 */
	public boolean empty() {
		return (this.cptSrcId == null) && (null == this.cptDstId)
				&& (this.montant == null);
	}

	/**
	 * Recupere la propriete <i>cptSrcId</i>.
	 *
	 * @return the cptSrcId la valeur de la propriete.
	 */
	public String getCptSrcId() {
		return this.cptSrcId;
	}

	public Integer getCptSrcIdAsInt() {
		try {
			return Integer.valueOf(this.cptSrcId);
		} catch (Exception e) {
			return null;
		}
	}

	public Integer getCptDstIdInt() {
		try {
			return Integer.valueOf(this.cptDstId);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Fixe la propriete <i>cptSrcId</i>.
	 *
	 * @param pCptSrcId
	 *            la nouvelle valeur pour la propriete cptSrcId.
	 */
	public void setCptSrcId(String pCptSrcId) {
		if ((pCptSrcId == null) || (pCptSrcId.trim().length() == 0)) {
			this.cptSrcId = null;
		} else {
			this.cptSrcId = pCptSrcId;
		}
	}

	/**
	 * Recupere la propriete <i>cptDstId</i>.
	 *
	 * @return the cptDstId la valeur de la propriete.
	 */
	public String getCptDstId() {
		return this.cptDstId;
	}

	/**
	 * Fixe la propriete <i>cptDstId</i>.
	 *
	 * @param pCptDstId
	 *            la nouvelle valeur pour la propriete cptDstId.
	 */
	public void setCptDstId(String pCptDstId) {
		if ((pCptDstId == null) || (pCptDstId.trim().length() == 0)) {
			this.cptDstId = null;
		} else {
			this.cptDstId = pCptDstId;
		}
	}

	/**
	 * Recupere la propriete <i>montant</i>.
	 *
	 * @return the montant la valeur de la propriete.
	 */
	public String getMontant() {
		return this.montant;
	}

	/**
	 * Fixe la propriete <i>montant</i>.
	 *
	 * @param pMontant
	 *            la nouvelle valeur pour la propriete montant.
	 */
	public void setMontant(String pMontant) {
		if ((pMontant == null) || (pMontant.trim().length() == 0)) {
			this.montant = null;
		} else {
			this.montant = pMontant;
		}
	}

}
