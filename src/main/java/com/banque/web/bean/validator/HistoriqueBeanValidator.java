/**
 * Copyright :  <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.web.bean.validator;

import java.text.SimpleDateFormat;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.banque.web.bean.HistoriqueBean;

/**
 * Validateur pour l'historique. <br/>
 */
public class HistoriqueBeanValidator implements Validator {
	@Override
	public boolean supports(Class<?> pArg0) {
		return HistoriqueBean.class.equals(pArg0);
	}

	@Override
	public void validate(Object aBean, Errors errors) {
		HistoriqueBean hb = (HistoriqueBean) aBean;
		if (hb.getCptId() == null) {
			errors.rejectValue("cptId", null, "error.cpt.id.empty");
		}

		String dateFormat = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);

		java.util.Date lDateDebut = null;
		if (hb.getDateDebut() != null) {
			try {
				lDateDebut = sdf.parse(hb.getDateDebut());
			} catch (Exception e) {
				errors.rejectValue("dateDebut", null,
						"error.date.debut.invalid");
			}
		}
		java.util.Date lDateFin = null;
		if (hb.getDateFin() != null) {
			try {
				lDateFin = sdf.parse(hb.getDateFin());
			} catch (Exception e) {
				errors.rejectValue("dateFin", null, "error.date.fin.invalid");
			}
		}
		if ((lDateFin != null) && (lDateDebut != null)) {
			if (lDateDebut.after(lDateFin)) {
				errors.rejectValue("dateFin", null, "error.dates.invalid");
			}
		}
	}

}
