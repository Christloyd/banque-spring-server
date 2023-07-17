/**
 * Copyright :  <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.web.bean.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.banque.web.bean.VirementBean;

/**
 * Validateur pour virement. <br/>
 */
public class VirementBeanValidator implements Validator {
	@Override
	public boolean supports(Class<?> pArg0) {
		return VirementBean.class.equals(pArg0);
	}

	@Override
	public void validate(Object aBean, Errors errors) {
		VirementBean vb = (VirementBean) aBean;
		if (vb.getMontant() == null) {
			errors.rejectValue("montant", null, "error.montant.empty");
		}

		if (vb.getCptDstId() == null) {
			errors.rejectValue("cptDstId", null, "error.cptdstid.empty");
		}

		if (vb.getCptSrcId() == null) {
			errors.rejectValue("cptSrcId", null, "error.cptsrcid.empty");
		}

		double lcMntant = -1d;
		if (vb.getMontant() != null) {
			try {
				lcMntant = Double.parseDouble(vb.getMontant());
			} catch (Exception e) {
				errors.rejectValue("montant", null, "error.montant.notanumber");
			}
			if (lcMntant <= 0) {
				errors.rejectValue("montant", null, "error.montant.notpositive");
			}
		}

		if (vb.getCptSrcId() != null) {
			try {
				Integer.parseInt(vb.getCptSrcId());
			} catch (Exception e) {
				errors.rejectValue("cptSrcId", null,
						"error.cptsrcid.notanumber");
			}
		}
		if (vb.getCptDstId() != null) {
			try {
				Integer.parseInt(vb.getCptDstId());
			} catch (Exception e) {
				errors.rejectValue("cptDstId", null,
						"error.cptdstid.notanumber");
			}
		}
	}

}
