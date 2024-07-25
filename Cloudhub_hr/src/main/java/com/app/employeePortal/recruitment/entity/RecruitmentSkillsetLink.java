package com.app.employeePortal.recruitment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="recruitment_skillset_link")
public class RecruitmentSkillsetLink {
	
	 @Id
	    @GenericGenerator(name = "recruitment_skillset_link_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentSkillSetGenerator")
	    @GeneratedValue(generator = "recruitment_skillset_link_id")

	    @Column(name = "recruitment_skillset_link_id")
	    private String recruitment_skillset_link_id;

	    @Column(name = "opportunity_id")
	    private String opportunity_id;

	    @Column(name = "profile_id")
	    private String profile_id;

	    @Column(name = "stage_id")
	    private String stage_id;

	    @Column(name = "process_id")
	    private String process_id;

	    @Column(name = "recruitment_id")
	    private String recruitment_id;

	    @Column(name = "skill_name")
		private String skillName;

	    @Column(name = "creation_date")
	    private Date creation_date;


	    @Column(name = "live_ind")
	    private boolean live_ind;


		@Override
		public String toString() {
			return "RecruitmentSkillsetLink [recruitment_skillset_link_id=" + recruitment_skillset_link_id
					+ ", opportunity_id=" + opportunity_id + ", profile_id=" + profile_id + ", stage_id=" + stage_id
					+ ", process_id=" + process_id + ", recruitment_id=" + recruitment_id + ", skillName=" + skillName
					+ ", creation_date=" + creation_date + ", live_ind=" + live_ind + "]";
		}
	    
	    

}
