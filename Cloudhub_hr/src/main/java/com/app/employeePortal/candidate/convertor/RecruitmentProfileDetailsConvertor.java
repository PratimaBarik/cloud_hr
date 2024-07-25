package com.app.employeePortal.candidate.convertor;

import com.app.employeePortal.recruitment.entity.RecruitProfileLinkDetails;

public class RecruitmentProfileDetailsConvertor{
	
	
	public static RecruitProfileLinkDetails convertToDatabaseColumn(Object[] attribute) {
		 try {
			 
			 System.out.println("attribute[1]======"+attribute[0]);
			return new RecruitProfileLinkDetails((String) attribute[0]);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Object[] convertToEntityAttribute(RecruitProfileLinkDetails dbData) {
		 return new Object[]{dbData.getCreation_date(), dbData.getCandidateId()};
	}
}
