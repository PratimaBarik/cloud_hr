package com.app.employeePortal.leads.generator;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class LeadsInfoGenerator implements IdentifierGenerator {
	

	public int generateId() {
		Random random = new Random();
		return random.nextInt(100);
	}

	public String generateAnotherId() {

		return RandomStringUtils.randomNumeric(9);
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// TODO Auto-generated method stub
		
		Calendar calendar = Calendar.getInstance();
		return "LIG" + this.generateId() + this.generateAnotherId()
				+ calendar.get(Calendar.DATE) + calendar.get(Calendar.YEAR);
	}

	

}
