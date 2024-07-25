package com.app.employeePortal.category.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.candidate.entity.CandidateDetails;
import com.app.employeePortal.candidate.repository.CandidateDetailsRepository;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.category.entity.Hour;
import com.app.employeePortal.category.entity.Invoice;
import com.app.employeePortal.category.mapper.HourCalculateMapper;
import com.app.employeePortal.category.mapper.HourMapper;
import com.app.employeePortal.category.mapper.HourTaskMapper;
import com.app.employeePortal.category.mapper.InvoiceMapper;
import com.app.employeePortal.category.repository.HourRepository;
import com.app.employeePortal.category.repository.InvoiceRepository;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.project.Entity.ProjectDetails;
import com.app.employeePortal.project.Repository.ProjectRepository;
import com.app.employeePortal.recruitment.entity.OpportunityRecruitDetails;
import com.app.employeePortal.recruitment.entity.RecruitProfileLinkDetails;
import com.app.employeePortal.recruitment.repository.RecruitmentOpportunityDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProfileDetailsRepository;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class HourServiceImpl implements HourService {

	@Autowired
	HourRepository hourRepository;
	@Autowired
	RecruitmentOpportunityDetailsRepository recruitmentOpportunityDetailsRepository;
	@Autowired
	RecruitmentProfileDetailsRepository recruitProfileDetailsRepository;
	@Autowired
	CandidateDetailsRepository candidateDetailsRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	CandidateService candidateService;
	@Autowired
	EmployeeService employeeService;

	@Override
	public HourMapper saveHour(HourMapper hourMapper) {
		
		HourMapper hourId = new HourMapper();
		
		Date plannerStartDate = null;
		try {
			plannerStartDate = Utility.removeTime(Utility.getDateFromISOString(hourMapper.getPlannerStartDate()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Hour hour = hourRepository.findByPlannerStartDateAndProjectNameAndTaskIdAndCandidateId(plannerStartDate,
				hourMapper.getProjectName(), hourMapper.getTaskId(), hourMapper.getCandidateId());

		if (null != hour) {
			hour.setCreationDate(Utility.removeTime(new Date()));
			System.out.println(
					"Utility.removeTime(new Date())==============if==============" + Utility.removeTime(new Date()));
			hour.setEndTime(hourMapper.getEndTime());
			hour.setOrgId(hourMapper.getOrgId());
			hour.setProjectName(hourMapper.getProjectName());
			hour.setStartTime(hourMapper.getStartTime());
			hour.setUserId(hourMapper.getUserId());
			hour.setCustomerId(hourMapper.getCustomerId());
			hour.setCandidateId(hourMapper.getCandidateId());
			hour.setAproveInd(false);
			hour.setAproveUnit(hourMapper.getAproveUnit());
			hour.setDocumentId(hourMapper.getDocumentId());
			hour.setTaskId(hourMapper.getTaskId());
			TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(hourMapper.getTaskId());
			if (null != taskDetails) {
				hour.setProjectManager(taskDetails.getAssigned_to());
			}

			// hour.setProjectManager(hourMapper.getProjectManager());
			hour.setImageId(hourMapper.getImageId());
			hour.setNote(hourMapper.getNote());
			hour.setCompleteUnit(hourMapper.getCompleteUnit());
			try {
				hour.setStartDate(Utility.getDateFromISOString(hourMapper.getStartDate()));
				hour.setEndDate(Utility.getDateFromISOString(hourMapper.getEndDate()));
				hour.setPlannerStartDate(Utility.getDateFromISOString(hourMapper.getPlannerStartDate()));
				float diff = (Utility.getDateFromISOString(hourMapper.getEndDate()).getTime()
						- Utility.getDateFromISOString(hourMapper.getStartDate()).getTime());
				long hour1 = (long) (diff / (60 * 60 * 1000f));

				float min = (diff - (hour1 * 60 * 1000 * 60)) / (60 * 1000);

				float hour2 = hour1 + (min / 100);

				System.out.println("hour2===========if=============" + hour2);
				hour.setHour(hour2);
			} catch (Exception e) {
				e.printStackTrace();
			}

				hourId = getHourByHourId( hourRepository.save(hour).getHourId());
		} else {
			Hour dbhour = new Hour();

			dbhour.setCreationDate(Utility.removeTime(new Date()));
			System.out.println(
					"Utility.removeTime(new Date())==============else==============" + Utility.removeTime(new Date()));
			dbhour.setEndTime(hourMapper.getEndTime());
			dbhour.setHourId(hourMapper.getHourId());
			dbhour.setOrgId(hourMapper.getOrgId());
			dbhour.setProjectName(hourMapper.getProjectName());
			dbhour.setStartTime(hourMapper.getStartTime());
			dbhour.setUserId(hourMapper.getUserId());
			dbhour.setCustomerId(hourMapper.getCustomerId());
			dbhour.setCandidateId(hourMapper.getCandidateId());
			dbhour.setAproveInd(false);
			dbhour.setAproveUnit(hourMapper.getAproveUnit());
			dbhour.setDocumentId(hourMapper.getDocumentId());
			dbhour.setTaskId(hourMapper.getTaskId());

			TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(hourMapper.getTaskId());
			if (null != taskDetails) {
				dbhour.setProjectManager(taskDetails.getAssigned_to());
			}

			dbhour.setImageId(hourMapper.getImageId());
			dbhour.setNote(hourMapper.getNote());
			dbhour.setCompleteUnit(hourMapper.getCompleteUnit());

			try {
				dbhour.setStartDate(Utility.getDateFromISOString(hourMapper.getStartDate()));
				dbhour.setEndDate(Utility.getDateFromISOString(hourMapper.getEndDate()));
				dbhour.setPlannerStartDate(Utility.getDateFromISOString(hourMapper.getPlannerStartDate()));
				float diff = (Utility.getDateFromISOString(hourMapper.getEndDate()).getTime()
						- Utility.getDateFromISOString(hourMapper.getStartDate()).getTime());
				long hour1 = (long) (diff / (60 * 60 * 1000f));

				float min = (diff - (hour1 * 60 * 1000 * 60)) / (60 * 1000);

				float hour2 = hour1 + (min / 100);

				System.out.println("hour2===========else=============" + hour2);
				dbhour.setHour(hour2);
			} catch (Exception e) {
				e.printStackTrace();
			}

			hourId = getHourByHourId( hourRepository.save(dbhour).getHourId());
		}

		return hourId;
	}

	@Override
	public HourMapper getHourByHourId(String hourId) {
		Hour hour = hourRepository.findByHourId(hourId);
		HourMapper hourMapper = new HourMapper();

		if (null != hour) {
			hourMapper.setCreationDate(Utility.getISOFromDate(hour.getCreationDate()));
			System.out.println("hr.getCreationDate()=========" + hour.getCreationDate());
			hourMapper.setEndTime(hour.getEndTime());
			hourMapper.setOrgId(hour.getOrgId());

			hourMapper.setProjectId(hour.getProjectName());
			ProjectDetails projectDetails = projectRepository.getById(hour.getProjectName());
			if (null != projectDetails) {
				hourMapper.setProjectName(projectDetails.getProjectName());
			}

			hourMapper.setStartTime(hour.getStartTime());
			hourMapper.setUserId(hour.getUserId());
			hourMapper.setCustomerId(hour.getCustomerId());
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(hour.getCustomerId());
			if (null != customer) {
				hourMapper.setCustomerName(customer.getName());
			}

			hourMapper.setCandidateId(hour.getCandidateId());
			hourMapper.setHour(hour.getHour());
			hourMapper.setStartDate(Utility.getISOFromDate(hour.getStartDate()));
			hourMapper.setEndDate(Utility.getISOFromDate(hour.getEndDate()));
			hourMapper.setPlannerStartDate(Utility.getISOFromDate(hour.getPlannerStartDate()));
			hourMapper.setHourId(hour.getHourId());
		}

		return hourMapper;
	}

	@Override
	public List<HourCalculateMapper> getSalesBillableCandidateList(String userId, String startDate, String endDate) {

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = 0;
		Date temp_date = start_date;
		while (end_date.after(temp_date)) {
			count++;
			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
		}
		if (count == 0) {
			count++;
		}

		List<HourCalculateMapper> resultMapper = new ArrayList<>();

		List<OpportunityRecruitDetails> recruitmentDetails = recruitmentOpportunityDetailsRepository
				.getRecruitmentDetailsListByUserId(userId);
		if (null != recruitmentDetails && !recruitmentDetails.isEmpty()) {
			List<String> recruitmetIds = new ArrayList<>();
			for (OpportunityRecruitDetails recruitment : recruitmentDetails) {
				recruitmetIds.add(recruitment.getRecruitment_id());
			}

			List<RecruitProfileLinkDetails> profileDetails = recruitProfileDetailsRepository
					.getProfileDetailByRecruitmentIdInAndonBoardInd(recruitmetIds);
			if (null != profileDetails && !profileDetails.isEmpty()) {
				for (RecruitProfileLinkDetails profileDetail : profileDetails) {
					HourCalculateMapper hourCalculateMapper = new HourCalculateMapper();

					hourCalculateMapper.setBillableCurency(profileDetail.getOnboardCurrency());
					hourCalculateMapper.setCandidateId(profileDetail.getCandidateId());
					hourCalculateMapper.setOrgId(profileDetail.getOrgId());
					hourCalculateMapper.setBillingAmount(profileDetail.getFinalBilling());

					hourCalculateMapper.setCustomerId(profileDetail.getCustomerId());
					Customer customer = customerRepository
							.getCustomerDetailsByCustomerId(profileDetail.getCustomerId());
					if (null != customer) {
						hourCalculateMapper.setCustomerName(customer.getName());
					}

					System.out
							.println("profileDetail.getCandidateId()]]]]]]]]]]]]]]]" + profileDetail.getCandidateId());

					CandidateDetails candidateDetails = candidateDetailsRepository
							.getByCandidateId(profileDetail.getCandidateId());
					if (null != candidateDetails) {
						String middleName = " ";
						String lastName = "";

						if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

							lastName = candidateDetails.getLastName();
						}

						if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

							middleName = candidateDetails.getMiddleName();
							hourCalculateMapper.setCandidateName(
									candidateDetails.getFirstName() + " " + middleName + " " + lastName);
						} else {

							hourCalculateMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
						}
					}

					hourCalculateMapper.setHour(profileDetail.getBillableHour());
					if (!StringUtils.isEmpty(profileDetail.getProjectName())) {
						hourCalculateMapper.setProjectId(profileDetail.getProjectName());
						ProjectDetails projectDetails = projectRepository.getById(profileDetail.getProjectName());
						if (null != projectDetails) {
							hourCalculateMapper.setProjectName(projectDetails.getProjectName());
						}
					}
					hourCalculateMapper.setCreationDate(Utility.getISOFromDate(profileDetail.getCreation_date()));
					String str1 = Float.toString(profileDetail.getBillableHour());
					String arr1[] = str1.split("\\.");
					float hours1 = count * Float.parseFloat(arr1[0]);
					long h1 = (long) ((count * Float.parseFloat(arr1[1])) / 60);
					float mins1 = (count * Float.parseFloat(arr1[1])) - (h1 * 60);
					float totalBillableHour = hours1 + h1 + (mins1 / 100);
					float totalBillableAmount = (profileDetail.getFinalBilling() * (hours1 + h1))
							+ (mins1 * (profileDetail.getFinalBilling() / 60));
					hourCalculateMapper.setActualBillableHour(totalBillableHour);
					hourCalculateMapper.setActualBillableAmount(totalBillableAmount);

					List<Hour> hour = hourRepository.getByCandidateIdAndProjectNameAndCreationDate(
							profileDetail.getCandidateId(), profileDetail.getProjectName(), start_date, end_date);
					if (null != hour && !hour.isEmpty()) {
						float totalhour = 0;
						float hours = 0;
						float mins = 0;

						for (Hour hr : hour) {
							String str = Float.toString(hr.getHour());
							String arr[] = str.split("\\.");
							hours = hours + Float.parseFloat(arr[0]);
							mins = mins + Float.parseFloat(arr[1]);
						}
						long h = (long) (mins / 60);
						float min = mins - (h * 60);
						totalhour = hours + h + (min / 100);
						float totalBillableAmount2 = (profileDetail.getFinalBilling() * (hours + h))
								+ (mins * (profileDetail.getFinalBilling() / 60));
						hourCalculateMapper.setFinalBillableHour(totalhour);
						hourCalculateMapper.setFinalBillableAmount(totalBillableAmount2);
					}

					resultMapper.add(hourCalculateMapper);
				}
			}
		}

		return resultMapper;
	}

	@Override
	public List<HourCalculateMapper> getManagementBillableCandidateList(String orgId, String startDate,
			String endDate) {

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = 0;
		Date temp_date = start_date;
		while (end_date.after(temp_date)) {
			count++;
			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
		}
		if (count == 0) {
			count++;
		}

		List<HourCalculateMapper> resultMapper = new ArrayList<>();

		List<RecruitProfileLinkDetails> profileDetails = recruitProfileDetailsRepository
				.getProfileDetailByPOrgIdAndonBoardInd(orgId);
		if (null != profileDetails && !profileDetails.isEmpty()) {
			for (RecruitProfileLinkDetails profileDetail : profileDetails) {
				HourCalculateMapper hourCalculateMapper = new HourCalculateMapper();

				hourCalculateMapper.setBillableCurency(profileDetail.getOnboardCurrency());
				hourCalculateMapper.setCandidateId(profileDetail.getCandidateId());
				hourCalculateMapper.setOrgId(profileDetail.getOrgId());
				hourCalculateMapper.setBillingAmount(profileDetail.getFinalBilling());

				hourCalculateMapper.setCustomerId(profileDetail.getCustomerId());
				Customer customer = customerRepository.getCustomerDetailsByCustomerId(profileDetail.getCustomerId());
				if (null != customer) {
					hourCalculateMapper.setCustomerName(customer.getName());
				}

				CandidateDetails candidateDetails = candidateDetailsRepository
						.getByCandidateId(profileDetail.getCandidateId());
				if (null != candidateDetails) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

						lastName = candidateDetails.getLastName();
					}

					if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

						middleName = candidateDetails.getMiddleName();
						hourCalculateMapper
								.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						hourCalculateMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
					}
				}

				hourCalculateMapper.setHour(profileDetail.getBillableHour());
				System.out.println("profileId==========" + profileDetail.getProfile_id());
				if (!StringUtils.isEmpty(profileDetail.getProjectName())) {
					hourCalculateMapper.setProjectId(profileDetail.getProjectName());
					ProjectDetails projectDetails = projectRepository.getById(profileDetail.getProjectName());
					if (null != projectDetails) {
						hourCalculateMapper.setProjectName(projectDetails.getProjectName());
					}
				}

				hourCalculateMapper.setCreationDate(Utility.getISOFromDate(profileDetail.getCreation_date()));
				String str1 = Float.toString(profileDetail.getBillableHour());
				String arr1[] = str1.split("\\.");
				float hours1 = count * Float.parseFloat(arr1[0]);
				long h1 = (long) ((count * Float.parseFloat(arr1[1])) / 60);
				float mins1 = (count * Float.parseFloat(arr1[1])) - (h1 * 60);
				float totalBillableHour = hours1 + h1 + (mins1 / 100);
				float totalBillableAmount = (profileDetail.getFinalBilling() * (hours1 + h1))
						+ (mins1 * (profileDetail.getFinalBilling() / 60));
				hourCalculateMapper.setActualBillableHour(totalBillableHour);
				hourCalculateMapper.setActualBillableAmount(totalBillableAmount);

				List<Hour> hour = hourRepository.getByCandidateIdAndProjectNameAndCreationDate(
						profileDetail.getCandidateId(), profileDetail.getProjectName(), start_date, end_date);
				if (null != hour && !hour.isEmpty()) {
					float totalhour = 0;
					float hours = 0;
					float mins = 0;

					for (Hour hr : hour) {
						String str = Float.toString(hr.getHour());
						String arr[] = str.split("\\.");
						hours = hours + Float.parseFloat(arr[0]);
						mins = mins + Float.parseFloat(arr[1]);
					}
					long h = (long) (mins / 60);
					float min = mins - (h * 60);
					totalhour = hours + h + (min / 100);
					float totalBillableAmount2 = (profileDetail.getFinalBilling() * (hours + h))
							+ (mins * (profileDetail.getFinalBilling() / 60));
					hourCalculateMapper.setFinalBillableHour(totalhour);
					hourCalculateMapper.setFinalBillableAmount(totalBillableAmount2);
				}
				
				String str4 = Float.toString(hourCalculateMapper.getActualBillableHour());
				String arr3[] = str4.split("\\.");
				float hours2 = Float.parseFloat(arr3[0]);
				float mins2 = Float.parseFloat(arr3[1]);
				
				String str5 = Float.toString(hourCalculateMapper.getFinalBillableHour());
				String arr4[] = str5.split("\\.");
				float hours3 = Float.parseFloat(arr4[0]);
				float mins3 = Float.parseFloat(arr4[1]);
				
				long h = (long)(((hours2*60)+mins2) - ((hours3*60)+mins3));
				
				long h2 = (long) (h / 60);
				float min = h - (h2 * 60);
				float totalhour1 = h2 + (min / 100);
				hourCalculateMapper.setDeviationBillableHour(totalhour1);
				
				float deviationAmount = ( hourCalculateMapper.getActualBillableAmount() - hourCalculateMapper.getFinalBillableAmount());
				hourCalculateMapper.setDeviationBillableAmount(deviationAmount);
				
				resultMapper.add(hourCalculateMapper);
			}
		}

		return resultMapper;
	}

	@Override
	public List<HourCalculateMapper> getRecruiterBillableCandidateList(String userId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = 0;
		Date temp_date = start_date;
		while (end_date.after(temp_date)) {
			count++;
			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
		}
		if (count == 0) {
			count++;
		}

		List<HourCalculateMapper> resultMapper = new ArrayList<>();

		List<RecruitProfileLinkDetails> profileDetails = recruitProfileDetailsRepository
				.getProfileDetailByPUserIdAndonBoardInd(userId);
		if (null != profileDetails && !profileDetails.isEmpty()) {
			for (RecruitProfileLinkDetails profileDetail : profileDetails) {
				HourCalculateMapper hourCalculateMapper = new HourCalculateMapper();

				hourCalculateMapper.setBillableCurency(profileDetail.getOnboardCurrency());
				hourCalculateMapper.setCandidateId(profileDetail.getCandidateId());
				hourCalculateMapper.setOrgId(profileDetail.getOrgId());
				hourCalculateMapper.setBillingAmount(profileDetail.getFinalBilling());

				hourCalculateMapper.setCustomerId(profileDetail.getCustomerId());
				Customer customer = customerRepository.getCustomerDetailsByCustomerId(profileDetail.getCustomerId());
				if (null != customer) {
					hourCalculateMapper.setCustomerName(customer.getName());
				}

				CandidateDetails candidateDetails = candidateDetailsRepository
						.getByCandidateId(profileDetail.getCandidateId());
				if (null != candidateDetails) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

						lastName = candidateDetails.getLastName();
					}

					if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

						middleName = candidateDetails.getMiddleName();
						hourCalculateMapper
								.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						hourCalculateMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
					}
				}

				hourCalculateMapper.setHour(profileDetail.getBillableHour());
				if (!StringUtils.isEmpty(profileDetail.getProjectName())) {
					hourCalculateMapper.setProjectId(profileDetail.getProjectName());
					ProjectDetails projectDetails = projectRepository.getById(profileDetail.getProjectName());
					if (null != projectDetails) {
						hourCalculateMapper.setProjectName(projectDetails.getProjectName());
					}
				}
				hourCalculateMapper.setCreationDate(Utility.getISOFromDate(profileDetail.getCreation_date()));
				String str1 = Float.toString(profileDetail.getBillableHour());
				String arr1[] = str1.split("\\.");
				float hours1 = count * Float.parseFloat(arr1[0]);
				long h1 = (long) ((count * Float.parseFloat(arr1[1])) / 60);
				float mins1 = (count * Float.parseFloat(arr1[1])) - (h1 * 60);
				float totalBillableHour = hours1 + h1 + (mins1 / 100);
				float totalBillableAmount = (profileDetail.getFinalBilling() * (hours1 + h1))
						+ (mins1 * (profileDetail.getFinalBilling() / 60));
				hourCalculateMapper.setActualBillableHour(totalBillableHour);
				hourCalculateMapper.setActualBillableAmount(totalBillableAmount);

				List<Hour> hour = hourRepository.getByCandidateIdAndProjectNameAndCreationDate(
						profileDetail.getCandidateId(), profileDetail.getProjectName(), start_date, end_date);
				if (null != hour && !hour.isEmpty()) {
					float totalhour = 0;
					float hours = 0;
					float mins = 0;

					for (Hour hr : hour) {
						String str = Float.toString(hr.getHour());
						String arr[] = str.split("\\.");
						hours = hours + Float.parseFloat(arr[0]);
						mins = mins + Float.parseFloat(arr[1]);
					}
					long h = (long) (mins / 60);
					float min = mins - (h * 60);
					totalhour = hours + h + (min / 100);
					float totalBillableAmount2 = (profileDetail.getFinalBilling() * (hours + h))
							+ (mins * (profileDetail.getFinalBilling() / 60));
					hourCalculateMapper.setFinalBillableHour(totalhour);
					hourCalculateMapper.setFinalBillableAmount(totalBillableAmount2);
				}

				resultMapper.add(hourCalculateMapper);
			}
		}

		return resultMapper;
	}

	@Override
	public List<HourMapper> getAllHourListByUserId(String userId, String startDate, String endDate) {

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<HourMapper> resultList = new ArrayList<>();

		List<Hour> hour = hourRepository.getByUserIdAndCreationDate(userId, start_date, end_date);
		if (null != hour && !hour.isEmpty()) {
			for (Hour hr : hour) {

				HourMapper hourMapper = new HourMapper();

				hourMapper.setCandidateId(hr.getCandidateId());
				hourMapper.setCreationDate(Utility.getISOFromDate(hr.getCreationDate()));
				System.out.println("hr.getCreationDate()=========" + hr.getCreationDate());
				hourMapper.setEndTime(hr.getEndTime());
				hourMapper.setHour(hr.getHour());
				hourMapper.setHourId(hr.getHourId());
				hourMapper.setOrgId(hr.getOrgId());

				hourMapper.setProjectId(hr.getProjectName());
				ProjectDetails projectDetails = projectRepository.getById(hr.getProjectName());
				if (null != projectDetails) {
					hourMapper.setProjectName(projectDetails.getProjectName());
				}
				hourMapper.setStartTime(hr.getStartTime());
				hourMapper.setUserId(hr.getUserId());
				hourMapper.setCustomerId(hr.getCustomerId());

				Customer customer = customerRepository.getCustomerDetailsByCustomerId(hr.getCustomerId());
				if (null != customer) {
					hourMapper.setCustomerName(customer.getName());
				}

				hourMapper.setStartDate(Utility.getISOFromDate(hr.getStartDate()));
				hourMapper.setEndDate(Utility.getISOFromDate(hr.getEndDate()));

				List<RecruitProfileLinkDetails> profileDetails = recruitProfileDetailsRepository
						.getProfileDetailsByCandidateIdAndProjectName(hr.getCandidateId(), hr.getProjectName());
				if (null != profileDetails && !profileDetails.isEmpty()) {
					for (RecruitProfileLinkDetails profileDetail : profileDetails) {
						hourMapper.setBillingAmount(profileDetail.getFinalBilling());

						String str1 = Float.toString(hr.getHour());
						String arr1[] = str1.split("\\.");
						float hours1 = Float.parseFloat(arr1[0]);
						float mins1 = (Float.parseFloat(arr1[1]));

						float totalBillableAmount = (profileDetail.getFinalBilling() * hours1)
								+ (mins1 * (profileDetail.getFinalBilling() / 60));
						hourMapper.setFinalBillableAmount(totalBillableAmount);
					}
				}

				resultList.add(hourMapper);
			}
		}
		return resultList;
	}

	@Override
	public List<HourMapper> getAllHourListByCandidateIdAndDateRangeForWebsite(String candidateId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<HourMapper> resultList = new ArrayList<>();

		List<Hour> hour = hourRepository.getByCandidateIdAndCreationDate(candidateId, start_date, end_date);
		if (null != hour && !hour.isEmpty()) {
			for (Hour hr : hour) {

				HourMapper hourMapper = new HourMapper();

				hourMapper.setCandidateId(hr.getCandidateId());
				hourMapper.setCreationDate(Utility.getISOFromDate(hr.getCreationDate()));
				hourMapper.setEndTime(hr.getEndTime());
				hourMapper.setHour(hr.getHour());
				hourMapper.setHourId(hr.getHourId());
				System.out.println("hr.getHourId()========" + hr.getHourId());
				hourMapper.setOrgId(hr.getOrgId());
				hourMapper.setProjectId(hr.getProjectName());
				System.out.println("id========" + hr.getProjectName());
				ProjectDetails projectDetails = projectRepository.getById(hr.getProjectName());
				if (null != projectDetails) {
					hourMapper.setProjectName(projectDetails.getProjectName());
				}
				hourMapper.setStartTime(hr.getStartTime());
				hourMapper.setUserId(hr.getUserId());

				hourMapper.setAproveInd(hr.isAproveInd());
				hourMapper.setAproveUnit(hr.getAproveUnit());
				hourMapper.setDocumentId(hr.getDocumentId());
				hourMapper.setTaskId(hr.getTaskId());

				TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(hr.getTaskId());
				if (null != taskDetails) {
					hourMapper.setTaskName(taskDetails.getTask_name());
				}

				hourMapper.setProjectManager(hr.getProjectManager());
				hourMapper.setImageId(hr.getImageId());
				hourMapper.setNote(hr.getNote());
				hourMapper.setRemark(hr.getRemark());
				hourMapper.setCompleteUnit(hr.getCompleteUnit());

				hourMapper.setCustomerId(hr.getCustomerId());
				Customer customer = customerRepository.getCustomerDetailsByCustomerId(hr.getCustomerId());
				if (null != customer) {
					hourMapper.setCustomerName(customer.getName());
				}

				hourMapper.setStartDate(Utility.getISOFromDate(hr.getStartDate()));
				hourMapper.setEndDate(Utility.getISOFromDate(hr.getEndDate()));

				List<RecruitProfileLinkDetails> profileDetails = recruitProfileDetailsRepository
						.getProfileDetailsByCandidateIdAndProjectName(candidateId, hr.getProjectName());
				if (null != profileDetails && !profileDetails.isEmpty()) {
					for (RecruitProfileLinkDetails profileDetail : profileDetails) {
						hourMapper.setBillingAmount(profileDetail.getFinalBilling());

						String str1 = Float.toString(hr.getHour());
						String arr1[] = str1.split("\\.");
						float hours1 = Float.parseFloat(arr1[0]);
						float mins1 = (Float.parseFloat(arr1[1]));

						float totalBillableAmount = (profileDetail.getFinalBilling() * hours1)
								+ (mins1 * (profileDetail.getFinalBilling() / 60));
						hourMapper.setFinalBillableAmount(totalBillableAmount);
					}
				}

				resultList.add(hourMapper);
			}
		}
		return resultList;
	}

	@Override
	public List<HourMapper> getAllHourListByCandidateIdForWebsite(String candidateId) {
		List<HourMapper> resultList = new ArrayList<>();

		List<Hour> hour = hourRepository.getByCandidateId(candidateId);
		if (null != hour && !hour.isEmpty()) {
			for (Hour hr : hour) {

				HourMapper hourMapper = new HourMapper();

				hourMapper.setCandidateId(hr.getCandidateId());
				hourMapper.setCreationDate(Utility.getISOFromDate(hr.getCreationDate()));
				hourMapper.setEndTime(hr.getEndTime());
				hourMapper.setHour(hr.getHour());
				hourMapper.setHourId(hr.getHourId());
				hourMapper.setOrgId(hr.getOrgId());
				hourMapper.setProjectId(hr.getProjectName());
				ProjectDetails projectDetails = projectRepository.getById(hr.getProjectName());
				if (null != projectDetails) {
					hourMapper.setProjectName(projectDetails.getProjectName());
				}
				hourMapper.setStartTime(hr.getStartTime());
				hourMapper.setUserId(hr.getUserId());

				hourMapper.setAproveInd(hr.isAproveInd());
				hourMapper.setAproveUnit(hr.getAproveUnit());
				hourMapper.setDocumentId(hr.getDocumentId());
				hourMapper.setTaskId(hr.getTaskId());

				TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(hr.getTaskId());
				if (null != taskDetails) {
					hourMapper.setTaskName(taskDetails.getTask_name());
				}

				hourMapper.setProjectManager(hr.getProjectManager());
				hourMapper.setImageId(hr.getImageId());
				hourMapper.setNote(hr.getNote());
				hourMapper.setRemark(hr.getRemark());
				hourMapper.setCompleteUnit(hr.getCompleteUnit());

				hourMapper.setCustomerId(hr.getCustomerId());
				Customer customer = customerRepository.getCustomerDetailsByCustomerId(hr.getCustomerId());
				if (null != customer) {
					hourMapper.setCustomerName(customer.getName());
				}

				hourMapper.setStartDate(Utility.getISOFromDate(hr.getStartDate()));
				hourMapper.setEndDate(Utility.getISOFromDate(hr.getEndDate()));

				List<RecruitProfileLinkDetails> profileDetails = recruitProfileDetailsRepository
						.getProfileDetailsByCandidateIdAndProjectName(candidateId, hr.getProjectName());
				if (null != profileDetails && !profileDetails.isEmpty()) {
					for (RecruitProfileLinkDetails profileDetail : profileDetails) {
						hourMapper.setBillingAmount(profileDetail.getFinalBilling());

						String str1 = Float.toString(hr.getHour());
						String arr1[] = str1.split("\\.");
						float hours1 = Float.parseFloat(arr1[0]);
						float mins1 = (Float.parseFloat(arr1[1]));
						float totalBillableAmount = (profileDetail.getFinalBilling() * hours1)
								+ (mins1 * (profileDetail.getFinalBilling() / 60));
						hourMapper.setFinalBillableAmount(totalBillableAmount);
					}
				}

				resultList.add(hourMapper);
			}
		}
		return resultList;
	}

	@Override
	public List<HourTaskMapper> getAllHourListByProjectManagerId(String userId, String taskId) {
		List<Hour> hour = hourRepository.getByProjectManagerAndTaskId(userId, taskId);
		if (null != hour && !hour.isEmpty()) {
			return hour.stream().map(hr -> {
				HourTaskMapper hourMapper = new HourTaskMapper();
				hourMapper.setCandidateId(hr.getCandidateId());

				CandidateDetails candidateDetails = candidateDetailsRepository.getByCandidateId(hr.getCandidateId());
				if (null != candidateDetails) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

						lastName = candidateDetails.getLastName();
					}

					if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

						middleName = candidateDetails.getMiddleName();
						hourMapper
								.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						hourMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
					}
				}

				hourMapper.setCreationDate(Utility.getISOFromDate(hr.getCreationDate()));
				hourMapper.setHourId(hr.getHourId());
				hourMapper.setOrgId(hr.getOrgId());
				hourMapper.setProjectId(hr.getProjectName());
				ProjectDetails projectDetails = projectRepository.getById(hr.getProjectName());
				if (null != projectDetails) {
					hourMapper.setProjectName(projectDetails.getProjectName());
				}
				hourMapper.setUserId(hr.getUserId());

				hourMapper.setAproveInd(hr.isAproveInd());
				hourMapper.setAproveUnit(hr.getAproveUnit());
				hourMapper.setDocumentId(hr.getDocumentId());
				hourMapper.setTaskId(hr.getTaskId());

				TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(hr.getTaskId());
				if (null != taskDetails) {
					hourMapper.setTaskName(taskDetails.getTask_name());
				}

				hourMapper.setProjectManager(hr.getProjectManager());
				hourMapper.setImageId(hr.getImageId());
				hourMapper.setNote(hr.getNote());
				hourMapper.setRemark(hr.getRemark());
				hourMapper.setCompleteUnit(hr.getCompleteUnit());

				hourMapper.setCustomerId(hr.getCustomerId());
				Customer customer = customerRepository.getCustomerDetailsByCustomerId(hr.getCustomerId());
				if (null != customer) {
					hourMapper.setCustomerName(customer.getName());
				}

				hourMapper.setStartDate(Utility.getISOFromDate(hr.getStartDate()));
				hourMapper.setPlannerStartDate(Utility.getISOFromDate(hr.getPlannerStartDate()));
				hourMapper.setEndDate(Utility.getISOFromDate(hr.getEndDate()));
				return hourMapper;
			}).collect(Collectors.toList());

		}
		return null;
	}

	@Override
	public HourTaskMapper approveHourByHourId(HourTaskMapper hourTaskMapper) {
		HourTaskMapper hourMapper = new HourTaskMapper();
		Hour hour = hourRepository.getByHourId(hourTaskMapper.getHourId());
		if (null != hour) {

			hour.setAproveInd(hourTaskMapper.isAproveInd());
			hour.setAproveUnit(hourTaskMapper.getAproveUnit());
			hour.setRemark(hourTaskMapper.getRemark());
			hourRepository.save(hour);

			hourMapper = getHourDetailByHourIdForCandidate(hourTaskMapper.getHourId());
		}
		return hourMapper;
	}

	@Override
	public HourTaskMapper getHourDetailByHourIdForCandidate(String hourId) {
		HourTaskMapper hourMapper = new HourTaskMapper();
		Hour hr = hourRepository.getByHourId(hourId);
		if (null != hr) {

			hourMapper.setCandidateId(hr.getCandidateId());

			CandidateDetails candidateDetails = candidateDetailsRepository.getByCandidateId(hr.getCandidateId());
			if (null != candidateDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

					lastName = candidateDetails.getLastName();
				}

				if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

					middleName = candidateDetails.getMiddleName();
					hourMapper.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					hourMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
				}
			}

			hourMapper.setCreationDate(Utility.getISOFromDate(hr.getCreationDate()));
			hourMapper.setHourId(hr.getHourId());
			hourMapper.setOrgId(hr.getOrgId());
			hourMapper.setProjectId(hr.getProjectName());
			ProjectDetails projectDetails = projectRepository.getById(hr.getProjectName());
			if (null != projectDetails) {
				hourMapper.setProjectName(projectDetails.getProjectName());
			}
			hourMapper.setUserId(hr.getUserId());

			hourMapper.setAproveInd(hr.isAproveInd());
			hourMapper.setAproveUnit(hr.getAproveUnit());
			hourMapper.setDocumentId(hr.getDocumentId());
			hourMapper.setTaskId(hr.getTaskId());

			TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(hr.getTaskId());
			if (null != taskDetails) {
				hourMapper.setTaskName(taskDetails.getTask_name());
			}

			hourMapper.setProjectManager(hr.getProjectManager());
			hourMapper.setImageId(hr.getImageId());
			hourMapper.setNote(hr.getNote());
			hourMapper.setRemark(hr.getRemark());
			hourMapper.setCompleteUnit(hr.getCompleteUnit());

			hourMapper.setCustomerId(hr.getCustomerId());
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(hr.getCustomerId());
			if (null != customer) {
				hourMapper.setCustomerName(customer.getName());
			}

			hourMapper.setStartDate(Utility.getISOFromDate(hr.getStartDate()));
			hourMapper.setPlannerStartDate(Utility.getISOFromDate(hr.getPlannerStartDate()));
			hourMapper.setEndDate(Utility.getISOFromDate(hr.getEndDate()));
		}
		return hourMapper;
	}

	@Override
	public List<HourTaskMapper> getHourDetailsByHourIdForCandidate(String hourId) {
		List<HourTaskMapper> hourMapperList = new ArrayList<>();
		;
		Hour hr = hourRepository.getByHourId(hourId);
		if (null != hr) {
			HourTaskMapper hourMapper = new HourTaskMapper();
			hourMapper.setCandidateId(hr.getCandidateId());

			CandidateDetails candidateDetails = candidateDetailsRepository.getByCandidateId(hr.getCandidateId());
			if (null != candidateDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

					lastName = candidateDetails.getLastName();
				}

				if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

					middleName = candidateDetails.getMiddleName();
					hourMapper.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					hourMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
				}
			}

			hourMapper.setCreationDate(Utility.getISOFromDate(hr.getCreationDate()));
			hourMapper.setHourId(hr.getHourId());
			hourMapper.setOrgId(hr.getOrgId());
			hourMapper.setProjectId(hr.getProjectName());
			ProjectDetails projectDetails = projectRepository.getById(hr.getProjectName());
			if (null != projectDetails) {
				hourMapper.setProjectName(projectDetails.getProjectName());
			}
			hourMapper.setUserId(hr.getUserId());

			hourMapper.setAproveInd(hr.isAproveInd());
			hourMapper.setAproveUnit(hr.getAproveUnit());
			hourMapper.setDocumentId(hr.getDocumentId());
			hourMapper.setTaskId(hr.getTaskId());

			TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(hr.getTaskId());
			if (null != taskDetails) {
				hourMapper.setTaskName(taskDetails.getTask_name());
			}

			hourMapper.setProjectManager(hr.getProjectManager());
			hourMapper.setImageId(hr.getImageId());
			hourMapper.setNote(hr.getNote());
			hourMapper.setRemark(hr.getRemark());
			hourMapper.setCompleteUnit(hr.getCompleteUnit());

			hourMapper.setCustomerId(hr.getCustomerId());
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(hr.getCustomerId());
			if (null != customer) {
				hourMapper.setCustomerName(customer.getName());
			}

			hourMapper.setStartDate(Utility.getISOFromDate(hr.getStartDate()));
			hourMapper.setPlannerStartDate(Utility.getISOFromDate(hr.getPlannerStartDate()));
			hourMapper.setEndDate(Utility.getISOFromDate(hr.getEndDate()));

			hourMapperList.add(hourMapper);
		}
		return hourMapperList;
	}

	@Override
	public int getTotalCompletedUnitByCandidateIdAndDateRange(String candidateId, String startDate, String endDate) {

		int total = 0;

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Hour> hr = hourRepository.getByCandidateIdAndCreationDate(candidateId, start_date, end_date);
		if (null != hr && !hr.isEmpty()) {
			for (Hour hour : hr) {
				if (null != hour.getAproveUnit() && !hour.getAproveUnit().isEmpty()) {
					total = total + Integer.parseInt(hour.getAproveUnit());
				}
			}
		}

		return total;
	}

	@Override
	public List<HourTaskMapper> getAllHourListByCandidateIdAndTaskId(String candidateId, String taskId) {
		List<Hour> hour = hourRepository.getByCandidateIdAndTaskId(candidateId, taskId);
		if (null != hour && !hour.isEmpty()) {
			return hour.stream().map(hr -> {
				HourTaskMapper hourMapper = new HourTaskMapper();
				hourMapper.setCandidateId(hr.getCandidateId());

				CandidateDetails candidateDetails = candidateDetailsRepository.getByCandidateId(hr.getCandidateId());
				if (null != candidateDetails) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

						lastName = candidateDetails.getLastName();
					}

					if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

						middleName = candidateDetails.getMiddleName();
						hourMapper
								.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						hourMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
					}
				}

				hourMapper.setCreationDate(Utility.getISOFromDate(hr.getCreationDate()));
				hourMapper.setHourId(hr.getHourId());
				hourMapper.setOrgId(hr.getOrgId());
				hourMapper.setProjectId(hr.getProjectName());
				ProjectDetails projectDetails = projectRepository.getById(hr.getProjectName());
				if (null != projectDetails) {
					hourMapper.setProjectName(projectDetails.getProjectName());
				}
				hourMapper.setUserId(hr.getUserId());

				hourMapper.setAproveInd(hr.isAproveInd());
				hourMapper.setAproveUnit(hr.getAproveUnit());
				hourMapper.setDocumentId(hr.getDocumentId());
				hourMapper.setTaskId(hr.getTaskId());

				TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(hr.getTaskId());
				if (null != taskDetails) {
					hourMapper.setTaskName(taskDetails.getTask_name());
				}

				hourMapper.setProjectManager(hr.getProjectManager());
				hourMapper.setImageId(hr.getImageId());
				hourMapper.setNote(hr.getNote());
				hourMapper.setRemark(hr.getRemark());
				hourMapper.setCompleteUnit(hr.getCompleteUnit());

				hourMapper.setCustomerId(hr.getCustomerId());
				Customer customer = customerRepository.getCustomerDetailsByCustomerId(hr.getCustomerId());
				if (null != customer) {
					hourMapper.setCustomerName(customer.getName());
				}

				hourMapper.setStartDate(Utility.getISOFromDate(hr.getStartDate()));
				hourMapper.setPlannerStartDate(Utility.getISOFromDate(hr.getPlannerStartDate()));
				hourMapper.setEndDate(Utility.getISOFromDate(hr.getEndDate()));
				return hourMapper;
			}).collect(Collectors.toList());

		}
		return null;
	}

	@Override
	public List<HourTaskMapper> getAllHourListByCandidateIdAndDateRange(String candidateId, String startDate,
			String endDate) {

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Hour> hour = hourRepository.getByCandidateIdAndCreationDate(candidateId, start_date, end_date);
		if (null != hour && !hour.isEmpty()) {
			return hour.stream().map(hr -> {
				HourTaskMapper hourMapper = new HourTaskMapper();
				hourMapper.setCandidateId(hr.getCandidateId());

				CandidateDetails candidateDetails = candidateDetailsRepository.getByCandidateId(hr.getCandidateId());
				if (null != candidateDetails) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

						lastName = candidateDetails.getLastName();
					}

					if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

						middleName = candidateDetails.getMiddleName();
						hourMapper
								.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						hourMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
					}
				}

				hourMapper.setCreationDate(Utility.getISOFromDate(hr.getCreationDate()));
				hourMapper.setHourId(hr.getHourId());
				hourMapper.setOrgId(hr.getOrgId());
				hourMapper.setProjectId(hr.getProjectName());
				ProjectDetails projectDetails = projectRepository.getById(hr.getProjectName());
				if (null != projectDetails) {
					hourMapper.setProjectName(projectDetails.getProjectName());
				}
				hourMapper.setUserId(hr.getUserId());

				hourMapper.setAproveInd(hr.isAproveInd());
				hourMapper.setAproveUnit(hr.getAproveUnit());
				hourMapper.setDocumentId(hr.getDocumentId());
				hourMapper.setTaskId(hr.getTaskId());

				TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(hr.getTaskId());
				if (null != taskDetails) {
					hourMapper.setTaskName(taskDetails.getTask_name());
				}

				hourMapper.setProjectManager(hr.getProjectManager());
				hourMapper.setImageId(hr.getImageId());
				hourMapper.setNote(hr.getNote());
				hourMapper.setRemark(hr.getRemark());
				hourMapper.setCompleteUnit(hr.getCompleteUnit());

				hourMapper.setCustomerId(hr.getCustomerId());
				Customer customer = customerRepository.getCustomerDetailsByCustomerId(hr.getCustomerId());
				if (null != customer) {
					hourMapper.setCustomerName(customer.getName());
				}

				hourMapper.setStartDate(Utility.getISOFromDate(hr.getStartDate()));
				hourMapper.setPlannerStartDate(Utility.getISOFromDate(hr.getPlannerStartDate()));
				hourMapper.setEndDate(Utility.getISOFromDate(hr.getEndDate()));
				return hourMapper;
			}).collect(Collectors.toList());

		}
		return null;
	}

	@Override
	public List<HourCalculateMapper> getCandidateTotalBillings(String candidateId, String projectId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = 0;
		Date temp_date = start_date;
		while (end_date.after(temp_date)) {
			count++;
			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
		}
		if (count == 0) {
			count++;
		}
		List<HourCalculateMapper> resultList = new ArrayList<>();

		RecruitProfileLinkDetails profileDetail = recruitProfileDetailsRepository
				.getProfileDetailByCandidateIdAndProjectName(candidateId, projectId);
		if (null != profileDetail) {
			HourCalculateMapper hourCalculateMapper = new HourCalculateMapper();
			hourCalculateMapper.setBillableCurency(profileDetail.getOnboardCurrency());
			hourCalculateMapper.setCandidateId(profileDetail.getCandidateId());
			hourCalculateMapper.setOrgId(profileDetail.getOrgId());
			hourCalculateMapper.setBillingAmount(profileDetail.getFinalBilling());

			hourCalculateMapper.setCustomerId(profileDetail.getCustomerId());
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(profileDetail.getCustomerId());
			if (null != customer) {
				hourCalculateMapper.setCustomerName(customer.getName());
			}

			CandidateDetails candidateDetails = candidateDetailsRepository
					.getByCandidateId(profileDetail.getCandidateId());
			if (null != candidateDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

					lastName = candidateDetails.getLastName();
				}

				if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

					middleName = candidateDetails.getMiddleName();
					hourCalculateMapper
							.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					hourCalculateMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
				}
			}

			hourCalculateMapper.setHour(profileDetail.getBillableHour());
			hourCalculateMapper.setProjectId(profileDetail.getProjectName());
			ProjectDetails projectDetails = projectRepository.getById(profileDetail.getProjectName());
			if (null != projectDetails) {
				hourCalculateMapper.setProjectName(projectDetails.getProjectName());
			}
			hourCalculateMapper.setCreationDate(Utility.getISOFromDate(profileDetail.getCreation_date()));
			String str1 = Float.toString(profileDetail.getBillableHour());
			String arr1[] = str1.split("\\.");
			float hours1 = count * Float.parseFloat(arr1[0]);
			long h1 = (long) ((count * Float.parseFloat(arr1[1])) / 60);
			float mins1 = (count * Float.parseFloat(arr1[1])) - (h1 * 60);
			float totalBillableHour = hours1 + h1 + (mins1 / 100);
			float totalBillableAmount = (profileDetail.getFinalBilling() * (hours1 + h1))
					+ (mins1 * (profileDetail.getFinalBilling() / 60));
			hourCalculateMapper.setActualBillableHour(totalBillableHour);
			hourCalculateMapper.setActualBillableAmount(totalBillableAmount);

			List<Hour> hour = hourRepository.getByCandidateIdAndProjectNameAndCreationDate(
					profileDetail.getCandidateId(), profileDetail.getProjectName(), start_date, end_date);
			if (null != hour && !hour.isEmpty()) {
				float totalhour = 0;
				float hours = 0;
				float mins = 0;

				for (Hour hr : hour) {
					String str = Float.toString(hr.getHour());
					String arr[] = str.split("\\.");
					hours = hours + Float.parseFloat(arr[0]);
					mins = mins + Float.parseFloat(arr[1]);
				}
				long h = (long) (mins / 60);
				float min = mins - (h * 60);
				totalhour = hours + h + (min / 100);
				float totalBillableAmount2 = (profileDetail.getFinalBilling() * (hours + h))
						+ (mins * (profileDetail.getFinalBilling() / 60));
				hourCalculateMapper.setFinalBillableHour(totalhour);
				hourCalculateMapper.setFinalBillableAmount(totalBillableAmount2);
			}
			resultList.add(hourCalculateMapper);
		}

		return resultList;
	}

	@Override
	public List<HourCalculateMapper> getCandidatesTotalBillingsForInvoice(String customerId, String projectId,
			String month, String year) {

		List<String> arr2 = new ArrayList<>();
		arr2.add("Jan");
		arr2.add("Feb");
		arr2.add("Mar");
		arr2.add("Apr");
		arr2.add("May");
		arr2.add("Jun");
		arr2.add("Jul");
		arr2.add("Aug");
		arr2.add("Sep");
		arr2.add("Oct");
		arr2.add("Nov");
		arr2.add("Dec");

		System.out.println("arr.size===========================" + arr2.size());

		int monthNo = arr2.indexOf(month) + 1;
		int year1 = Integer.parseInt(year);

		YearMonth yearMonth = YearMonth.of(year1, monthNo); // 2015-01. January of 2015.
		LocalDate startDateOfMonth = yearMonth.atDay(1); // 2015-01-01
		LocalDate endDateOfMonth = yearMonth.atEndOfMonth();//  2015-01-31

		Date start_date = null;
		Date end_date = null;

		try {
			start_date = Utility.getUtilDateByLocalDate(startDateOfMonth);
			end_date = Utility.getUtilDateByLocalDate(endDateOfMonth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = 0;
		Date temp_date = start_date;
		while (end_date.after(temp_date)) {
			count++;
			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
		}
		if (count == 0) {
			count++;
		}
		List<HourCalculateMapper> resultMapper = new ArrayList<>();

		System.out.println("start_date===========================" + start_date);
		System.out.println("end_date===========================" + end_date);
		System.out.println("month===========================" + month);
		System.out.println("year===========================" + year);
		System.out.println("startDateOfMonth===========================" + startDateOfMonth);
		System.out.println("endDateOfMonth===========================" + endDateOfMonth);

		List<Invoice> invoice = invoiceRepository.getCandidateByProjectIdandCustomerId(projectId, customerId, month,
				year);

		List<RecruitProfileLinkDetails> profileDetails = recruitProfileDetailsRepository
				.getProfileDetailByCustomerIdAndProjectName(customerId, projectId);
		if (null != profileDetails && !profileDetails.isEmpty()) {
			System.out.println("profileDetails size===========================" + profileDetails.size());
			for (RecruitProfileLinkDetails profileDetail : profileDetails) {
				System.out.println(
						"profileDetail.getCandidateId()===========================" + profileDetail.getCandidateId());
				if (null != invoice && !invoice.isEmpty()) {
					boolean flag = false;
					System.out.println("invoice size===========================" + invoice.size());
					for (Invoice invoice1 : invoice) {
						System.out.println(
								"invoice1.getCandidateId()===========================" + invoice1.getCandidateId());
						if (invoice1.getCandidateId().equalsIgnoreCase(profileDetail.getCandidateId())) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						HourCalculateMapper hourCalculateMapper = new HourCalculateMapper();
						hourCalculateMapper.setBillableCurency(profileDetail.getOnboardCurrency());
						hourCalculateMapper.setCandidateId(profileDetail.getCandidateId());
						hourCalculateMapper.setOrgId(profileDetail.getOrgId());
						hourCalculateMapper.setBillingAmount(profileDetail.getFinalBilling());

						hourCalculateMapper.setCustomerId(profileDetail.getCustomerId());
						Customer customer = customerRepository
								.getCustomerDetailsByCustomerId(profileDetail.getCustomerId());
						if (null != customer) {
							hourCalculateMapper.setCustomerName(customer.getName());
						}

						CandidateDetails candidateDetails = candidateDetailsRepository
								.getByCandidateId(profileDetail.getCandidateId());
						if (null != candidateDetails) {
							String middleName = " ";
							String lastName = "";

							if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

								lastName = candidateDetails.getLastName();
							}

							if (candidateDetails.getMiddleName() != null
									&& candidateDetails.getMiddleName().length() > 0) {

								middleName = candidateDetails.getMiddleName();
								hourCalculateMapper.setCandidateName(
										candidateDetails.getFirstName() + " " + middleName + " " + lastName);
							} else {

								hourCalculateMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
							}
						}

						hourCalculateMapper.setHour(profileDetail.getBillableHour());
						hourCalculateMapper.setProjectId(profileDetail.getProjectName());
						ProjectDetails projectDetails = projectRepository.getById(profileDetail.getProjectName());
						if (null != projectDetails) {
							hourCalculateMapper.setProjectName(projectDetails.getProjectName());
						}
						hourCalculateMapper.setCreationDate(Utility.getISOFromDate(profileDetail.getCreation_date()));
						hourCalculateMapper.setMonth(month);
						hourCalculateMapper.setYear(year);

						String str1 = Float.toString(profileDetail.getBillableHour());
						String arr1[] = str1.split("\\.");
						float hours1 = count * Float.parseFloat(arr1[0]);
						long h1 = (long) ((count * Float.parseFloat(arr1[1])) / 60);
						float mins1 = (count * Float.parseFloat(arr1[1])) - (h1 * 60);
						float totalBillableHour = hours1 + h1 + (mins1 / 100);
						float totalBillableAmount = (profileDetail.getFinalBilling() * (hours1 + h1))
								+ (mins1 * (profileDetail.getFinalBilling() / 60));
						hourCalculateMapper.setActualBillableHour(totalBillableHour);
						hourCalculateMapper.setActualBillableAmount(totalBillableAmount);

						List<Hour> hour = hourRepository.getByCandidateIdAndProjectNameAndCreationDate(
								profileDetail.getCandidateId(), profileDetail.getProjectName(), start_date, end_date);
						if (null != hour && !hour.isEmpty()) {
							float totalhour = 0;
							float hours = 0;
							float mins = 0;

							for (Hour hr : hour) {
								String str = Float.toString(hr.getHour());
								String arr[] = str.split("\\.");
								hours = hours + Float.parseFloat(arr[0]);
								mins = mins + Float.parseFloat(arr[1]);
							}
							long h = (long) (mins / 60);
							float min = mins - (h * 60);
							totalhour = hours + h + (min / 100);
							float totalBillableAmount2 = (profileDetail.getFinalBilling() * (hours + h))
									+ (mins * (profileDetail.getFinalBilling() / 60));
							hourCalculateMapper.setFinalBillableHour(totalhour);
							hourCalculateMapper.setFinalBillableAmount(totalBillableAmount2);
						}
						resultMapper.add(hourCalculateMapper);
					}
					System.out.println("resultMapper.size()===========================" + resultMapper.size());

				} else {
					System.out.println("in else===========================");
					HourCalculateMapper hourCalculateMapper = new HourCalculateMapper();
					hourCalculateMapper.setBillableCurency(profileDetail.getOnboardCurrency());
					hourCalculateMapper.setCandidateId(profileDetail.getCandidateId());
					hourCalculateMapper.setOrgId(profileDetail.getOrgId());
					hourCalculateMapper.setBillingAmount(profileDetail.getFinalBilling());

					hourCalculateMapper.setCustomerId(profileDetail.getCustomerId());
					Customer customer = customerRepository
							.getCustomerDetailsByCustomerId(profileDetail.getCustomerId());
					if (null != customer) {
						hourCalculateMapper.setCustomerName(customer.getName());
					}

					CandidateDetails candidateDetails = candidateDetailsRepository
							.getByCandidateId(profileDetail.getCandidateId());
					if (null != candidateDetails) {
						String middleName = " ";
						String lastName = "";

						if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

							lastName = candidateDetails.getLastName();
						}

						if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

							middleName = candidateDetails.getMiddleName();
							hourCalculateMapper.setCandidateName(
									candidateDetails.getFirstName() + " " + middleName + " " + lastName);
						} else {

							hourCalculateMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
						}
					}

					hourCalculateMapper.setHour(profileDetail.getBillableHour());
					hourCalculateMapper.setProjectId(profileDetail.getProjectName());
					ProjectDetails projectDetails = projectRepository.getById(profileDetail.getProjectName());
					if (null != projectDetails) {
						hourCalculateMapper.setProjectName(projectDetails.getProjectName());
					}
					hourCalculateMapper.setCreationDate(Utility.getISOFromDate(profileDetail.getCreation_date()));
					hourCalculateMapper.setMonth(month);
					hourCalculateMapper.setYear(year);

					String str1 = Float.toString(profileDetail.getBillableHour());
					String arr1[] = str1.split("\\.");
					float hours1 = count * Float.parseFloat(arr1[0]);
					long h1 = (long) ((count * Float.parseFloat(arr1[1])) / 60);
					float mins1 = (count * Float.parseFloat(arr1[1])) - (h1 * 60);
					float totalBillableHour = hours1 + h1 + (mins1 / 100);
					float totalBillableAmount = (profileDetail.getFinalBilling() * (hours1 + h1))
							+ (mins1 * (profileDetail.getFinalBilling() / 60));
					hourCalculateMapper.setActualBillableHour(totalBillableHour);
					hourCalculateMapper.setActualBillableAmount(totalBillableAmount);

					List<Hour> hour = hourRepository.getByCandidateIdAndProjectNameAndCreationDate(
							profileDetail.getCandidateId(), profileDetail.getProjectName(), start_date, end_date);
					if (null != hour && !hour.isEmpty()) {
						float totalhour = 0;
						float hours = 0;
						float mins = 0;

						for (Hour hr : hour) {
							String str = Float.toString(hr.getHour());
							String arr[] = str.split("\\.");
							hours = hours + Float.parseFloat(arr[0]);
							mins = mins + Float.parseFloat(arr[1]);
						}
						long h = (long) (mins / 60);
						float min = mins - (h * 60);
						totalhour = hours + h + (min / 100);
						float totalBillableAmount2 = (profileDetail.getFinalBilling() * (hours + h))
								+ (mins * (profileDetail.getFinalBilling() / 60));
						hourCalculateMapper.setFinalBillableHour(totalhour);
						hourCalculateMapper.setFinalBillableAmount(totalBillableAmount2);
					}
					resultMapper.add(hourCalculateMapper);
				}
			}
		}

		return resultMapper;
	}

	@Override
	public List<InvoiceMapper> createInvoice(List<InvoiceMapper> invoiceMapper) {
		List<InvoiceMapper> invoiceId = new ArrayList<>();

		if (null != invoiceMapper && !invoiceMapper.isEmpty()) {
			for (InvoiceMapper invoiceMapper1 : invoiceMapper) {
				Invoice invoice = new Invoice();

				invoice.setActualBillableAmount(invoiceMapper1.getActualBillableAmount());
				invoice.setActualBillableHour(invoiceMapper1.getActualBillableHour());
				invoice.setBillableCurency(invoiceMapper1.getBillableCurency());
				invoice.setBillingAmount(invoiceMapper1.getBillingAmount());
				invoice.setCandidateId(invoiceMapper1.getCandidateId());
				invoice.setCreationDate(new Date());
				invoice.setCustomerId(invoiceMapper1.getCustomerId());
				invoice.setHour(invoiceMapper1.getHour());
				invoice.setMonth(invoiceMapper1.getMonth());
				invoice.setYear(invoiceMapper1.getYear());
				invoice.setOrgId(invoiceMapper1.getOrgId());
				invoice.setProjectedBillableAmount(invoiceMapper1.getProjectedBillableAmount());
				invoice.setProjectedBillableHour(invoiceMapper1.getProjectedBillableHour());
				invoice.setProjectId(invoiceMapper1.getProjectId());
				invoice.setUserId(invoiceMapper1.getUserId());

				invoiceRepository.save(invoice);

				invoiceId.add(getInvoiceByInvoiceId(invoice.getInvoiceId()));

			}
		}

		return invoiceId;
	}

	@Override
	public InvoiceMapper getInvoiceByInvoiceId(String invoiceId) {

		InvoiceMapper invoiceMapper = new InvoiceMapper();

		Invoice invoice = invoiceRepository.getById(invoiceId);
		if (null != invoice) {

			invoiceMapper.setInvoiceId(invoice.getInvoiceId());
			invoiceMapper.setActualBillableAmount(invoice.getActualBillableAmount());
			invoiceMapper.setActualBillableHour(invoice.getActualBillableHour());
			invoiceMapper.setBillableCurency(invoice.getBillableCurency());
			invoiceMapper.setBillingAmount(invoice.getBillingAmount());
			invoiceMapper.setCandidateId(invoice.getCandidateId());
			invoiceMapper.setCandidateName(candidateService.getCandidateFullName(invoice.getCandidateId()));
			invoiceMapper.setCustomerId(invoice.getCustomerId());
			invoiceMapper.setMonth(invoice.getMonth());
			invoiceMapper.setYear(invoice.getYear());
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(invoice.getCustomerId());
			if (null != customer) {
				invoiceMapper.setCustomerName(customer.getName());
			}
			invoiceMapper.setHour(invoice.getHour());
			try {
//				invoiceMapper.setInvoiceEndDate(Utility.getISOFromDate(invoice.getInvoiceEndDate()));
//				invoiceMapper.setInvoiceStartDate(Utility.getISOFromDate(invoice.getInvoiceStartDate()));
				invoiceMapper.setCreationDate(Utility.getISOFromDate(invoice.getCreationDate()));
			} catch (Exception e) {
				e.printStackTrace();
			}

			invoiceMapper.setOrgId(invoice.getOrgId());
			invoiceMapper.setProjectedBillableAmount(invoice.getProjectedBillableAmount());
			invoiceMapper.setProjectedBillableHour(invoice.getProjectedBillableHour());
			invoiceMapper.setProjectId(invoice.getProjectId());
			ProjectDetails projectDetails = projectRepository.getById(invoice.getProjectId());
			if (null != projectDetails) {
				invoiceMapper.setProjectName(projectDetails.getProjectName());
			}
			invoiceMapper.setUserId(invoice.getUserId());
			invoiceMapper.setCreatorName(employeeService.getEmployeeFullName(invoice.getUserId()));

		}

		return invoiceMapper;
	}

	@Override
	public List<InvoiceMapper> getInvoiceByOrgId(String orgId) {
		List<InvoiceMapper> resultList = new ArrayList<>();

		List<Invoice> invoice = invoiceRepository.findByOrgId(orgId);
		if (null != invoice && !invoice.isEmpty()) {
			for (Invoice invoice2 : invoice) {
				InvoiceMapper invoiceMapper = getInvoiceByInvoiceId(invoice2.getInvoiceId());
				resultList.add(invoiceMapper);
			}
		}
		return resultList;
	}

	@Override
	public List<InvoiceMapper> getInvoiceByUserId(String userId) {
		List<InvoiceMapper> resultList = new ArrayList<>();

		List<Invoice> invoice = invoiceRepository.findByUserId(userId);
		if (null != invoice && !invoice.isEmpty()) {
			for (Invoice invoice2 : invoice) {
				InvoiceMapper invoiceMapper = getInvoiceByInvoiceId(invoice2.getInvoiceId());
				resultList.add(invoiceMapper);
			}
		}
		return resultList;
	}

	@Override
	public List<HourCalculateMapper> getBillableCandidateListByName(String name, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = 0;
		Date temp_date = start_date;
		while (end_date.after(temp_date)) {
			count++;
			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
		}
		if (count == 0) {
			count++;
		}
				
		List<HourCalculateMapper> resultMapper = new ArrayList<>();
		
		List<CandidateDetails> list = candidateDetailsRepository
				.findByFullNameContainingAndLiveInd(name, true);
		if(null!=list && !list.isEmpty()) {
			for(CandidateDetails candidateDetails : list) {

		List<RecruitProfileLinkDetails> profileDetails = recruitProfileDetailsRepository
				.getProfileDetailByCandidateIdAndonBoardInd(candidateDetails.getCandidateId());
		if (null != profileDetails && !profileDetails.isEmpty()) {
			for (RecruitProfileLinkDetails profileDetail : profileDetails) {
				HourCalculateMapper hourCalculateMapper = new HourCalculateMapper();

				hourCalculateMapper.setBillableCurency(profileDetail.getOnboardCurrency());
				hourCalculateMapper.setCandidateId(profileDetail.getCandidateId());
				hourCalculateMapper.setOrgId(profileDetail.getOrgId());
				hourCalculateMapper.setBillingAmount(profileDetail.getFinalBilling());

				hourCalculateMapper.setCustomerId(profileDetail.getCustomerId());
				Customer customer = customerRepository.getCustomerDetailsByCustomerId(profileDetail.getCustomerId());
				if (null != customer) {
					hourCalculateMapper.setCustomerName(customer.getName());
				}

//				CandidateDetails candidateDetails = candidateDetailsRepository
//						.getByCandidateId(profileDetail.getCandidateId());
				if (null != candidateDetails) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

						lastName = candidateDetails.getLastName();
					}

					if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

						middleName = candidateDetails.getMiddleName();
						hourCalculateMapper
								.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						hourCalculateMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
					}
				}

				hourCalculateMapper.setHour(profileDetail.getBillableHour());
				if (!StringUtils.isEmpty(profileDetail.getProjectName())) {
					hourCalculateMapper.setProjectId(profileDetail.getProjectName());
					ProjectDetails projectDetails = projectRepository.getById(profileDetail.getProjectName());
					if (null != projectDetails) {
						hourCalculateMapper.setProjectName(projectDetails.getProjectName());
					}
				}
				hourCalculateMapper.setCreationDate(Utility.getISOFromDate(profileDetail.getCreation_date()));
				String str1 = Float.toString(profileDetail.getBillableHour());
				String arr1[] = str1.split("\\.");
				float hours1 = count * Float.parseFloat(arr1[0]);
				long h1 = (long) ((count * Float.parseFloat(arr1[1])) / 60);
				float mins1 = (count * Float.parseFloat(arr1[1])) - (h1 * 60);
				float totalBillableHour = hours1 + h1 + (mins1 / 100);
				float totalBillableAmount = (profileDetail.getFinalBilling() * (hours1 + h1))
						+ (mins1 * (profileDetail.getFinalBilling() / 60));
				hourCalculateMapper.setActualBillableHour(totalBillableHour);
				hourCalculateMapper.setActualBillableAmount(totalBillableAmount);

				List<Hour> hour = hourRepository.getByCandidateIdAndProjectNameAndCreationDate(
						profileDetail.getCandidateId(), profileDetail.getProjectName(), start_date, end_date);
				if (null != hour && !hour.isEmpty()) {
					float totalhour = 0;
					float hours = 0;
					float mins = 0;

					for (Hour hr : hour) {
						String str = Float.toString(hr.getHour());
						String arr[] = str.split("\\.");
						hours = hours + Float.parseFloat(arr[0]);
						mins = mins + Float.parseFloat(arr[1]);
					}
					long h = (long) (mins / 60);
					float min = mins - (h * 60);
					totalhour = hours + h + (min / 100);
					float totalBillableAmount2 = (profileDetail.getFinalBilling() * (hours + h))
							+ (mins * (profileDetail.getFinalBilling() / 60));
					hourCalculateMapper.setFinalBillableHour(totalhour);
					hourCalculateMapper.setFinalBillableAmount(totalBillableAmount2);
				}

				resultMapper.add(hourCalculateMapper);
			}
		}
			}
		}
		return resultMapper;
	}

}
