package com.app.employeePortal.leads.generator;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class LeadsDocumentLinkGenerator implements IdentifierGenerator{
	
	public int generateId() {
		Random random = new Random();
		return random.nextInt(100);
	}

	public String generateAnotherId() {

		return RandomStringUtils.randomNumeric(9);
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor si, Object o)
			throws HibernateException {


		Calendar calendar = Calendar.getInstance();
		return "LDLG" + this.generateId() + this.generateAnotherId()
				+ calendar.get(Calendar.DATE) + calendar.get(Calendar.YEAR);

	}

}
