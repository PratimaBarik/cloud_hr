
package com.app.employeePortal.mileage.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.api.service.CurrencyExchangeService;
import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.mileage.entity.MileageDetails;
import com.app.employeePortal.mileage.entity.MileageInfo;
import com.app.employeePortal.mileage.entity.MileageNotesLink;
import com.app.employeePortal.mileage.entity.MileageRate;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.mileage.mapper.MileageRateMapper;
import com.app.employeePortal.mileage.repository.MileageInfoRepository;
import com.app.employeePortal.mileage.repository.MileageNotesLinkRepository;
import com.app.employeePortal.mileage.repository.MileageRateRepository;
import com.app.employeePortal.mileage.repository.MileageRepository;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.processApproval.service.ProcessApprovalService;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskInfo;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskInfoRepository;
import com.app.employeePortal.util.Utility;
import com.app.employeePortal.voucher.entity.VoucherDetails;
import com.app.employeePortal.voucher.entity.VoucherInfo;
import com.app.employeePortal.voucher.entity.VoucherMileageLink;
import com.app.employeePortal.voucher.mapper.VoucherMapper;
import com.app.employeePortal.voucher.repository.VoucherInfoRepository;
import com.app.employeePortal.voucher.repository.VoucherMileageRepository;
import com.app.employeePortal.voucher.repository.VoucherRepository;
import com.app.employeePortal.voucher.service.VoucherService;

@Service
public class MileageServiceImpl implements MileageService {

	@Autowired
	MileageInfoRepository mileageInfoRepository;

	@Autowired
	MileageRepository mileageRepository;
	@Autowired
	VoucherInfoRepository voucherInfoRepository;
	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	VoucherMileageRepository voucherMileageRepository;
	@Autowired
	TaskInfoRepository taskInfoRepository;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;

	@Autowired
	EmployeeTaskRepository employeeTaskRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	MileageRateRepository mileageRateRepository;
	@Autowired
	EmployeeService empService;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	CurrencyExchangeService currencyExchangeService;
	@Autowired
	EmailService emailService;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ProcessApprovalService processApprovalService;
	@Autowired
	VoucherService voucherService;
	@Autowired
	MileageNotesLinkRepository mileageNotesLinkRepository;
	@Autowired
	NotesRepository notesRepository;

	@Autowired
	AddressInfoRepository addressInfoRepository;

	@Autowired
	AddressRepository addressRepository;
	private static String[] mileage_heading = { "Voucher Id", "Voucher Date", "Submitted By", "Amount", "Status" };

	@Override
	public VoucherMapper saveToMileageProcess(List<MileageMapper> mileageList, String userId, String orgId) {

		String voucherId = null;
		String exchangeCurrency = null;
		double voucher_amount = 0;
		EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeId(userId, true);
		if (null != details) {
			if (null != details.getCurrency()) {
				exchangeCurrency = details.getCurrency();
			} else {
				exchangeCurrency = "INR";
			}
		}
		if (null != mileageList && !mileageList.isEmpty()) {

			/* insert to voucher-info */

			VoucherInfo voucherInfo = new VoucherInfo();
			voucherInfo.setCreation_date(new Date());
			VoucherInfo info = voucherInfoRepository.save(voucherInfo);
			voucherId = info.getVoucher_id();

			for (MileageMapper mileageMapper : mileageList) {

				MileageInfo mileageInfo = new MileageInfo();
				mileageInfo.setCreation_date(new Date());

				MileageInfo mileageInfoo = mileageInfoRepository.save(mileageInfo);
				String mileageId = mileageInfoo.getMileage_id();

				if (null != mileageId) {

					MileageDetails mileageDetails = new MileageDetails();
					mileageDetails.setClient_name(mileageMapper.getClientName());
					mileageDetails.setCreation_date(new Date());
					mileageDetails.setDistances(mileageMapper.getDistances());
					mileageDetails.setFrom_location(mileageMapper.getFromLocation());
					mileageDetails.setMileage_id(mileageId);
					mileageDetails.setMileage_rate(mileageMapper.getMileageRate());
					mileageDetails.setOrganization_id(orgId);
					mileageDetails.setUser_id(userId);
					mileageDetails.setProject_name(mileageMapper.getProjectName());
					mileageDetails.setRemark(mileageMapper.getRemark());
					mileageDetails.setTo_location(mileageMapper.getToLocation());
					mileageDetails.setCurrency(mileageMapper.getCurrency());
					mileageDetails.setUnit(mileageMapper.getUnit());
					mileageDetails.setLive_ind(true);
					try {
						mileageDetails.setMileage_date(Utility.getDateFromISOString(mileageMapper.getMileageDate()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					/* for fromLocation */
					if (null != mileageMapper.getFromAddress()) {

						AddressMapper addressMapper = mileageMapper.getFromAddress();
						/* insert to address info & address deatils */

						AddressInfo addressInfo = new AddressInfo();
						addressInfo.setCreationDate(new Date());
						// addressInfo.setCreatorId(candidateMapperr.getUserId());
						AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

						String fromaddressId = addressInfoo.getId();

						if (null != fromaddressId) {

							AddressDetails addressDetails = new AddressDetails();
							addressDetails.setAddressId(fromaddressId);
							addressDetails.setAddressLine1(addressMapper.getAddress1());
							addressDetails.setAddressLine2(addressMapper.getAddress2());
							addressDetails.setAddressType(addressMapper.getAddressType());
							addressDetails.setCountry(addressMapper.getCountry());
							addressDetails.setCreationDate(new Date());
							addressDetails.setStreet(addressMapper.getStreet());
							addressDetails.setCity(addressMapper.getCity());
							addressDetails.setPostalCode(addressMapper.getPostalCode());
							addressDetails.setTown(addressMapper.getTown());
							addressDetails.setState(addressMapper.getState());
							addressDetails.setLatitude(addressMapper.getLatitude());
							addressDetails.setLongitude(addressMapper.getLongitude());
							addressDetails.setLiveInd(true);
							addressDetails.setHouseNo(addressMapper.getHouseNo());
							addressRepository.save(addressDetails);

							mileageDetails.setFrom_location(fromaddressId);

						}

					}
					/* for ToLocation */
					if (null != mileageMapper.getToAddress()) {

						AddressMapper addressMapper = mileageMapper.getToAddress();
						/* insert to address info & address deatils & customeraddressLink */

						AddressInfo addressInfo = new AddressInfo();
						addressInfo.setCreationDate(new Date());
						// addressInfo.setCreatorId(candidateMapperr.getUserId());
						AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

						String toaddressId = addressInfoo.getId();

						if (null != toaddressId) {

							AddressDetails addressDetails = new AddressDetails();
							addressDetails.setAddressId(toaddressId);
							addressDetails.setAddressLine1(addressMapper.getAddress1());
							addressDetails.setAddressLine2(addressMapper.getAddress2());
							addressDetails.setAddressType(addressMapper.getAddressType());
							addressDetails.setCountry(addressMapper.getCountry());
							addressDetails.setCreationDate(new Date());
							addressDetails.setStreet(addressMapper.getStreet());
							addressDetails.setCity(addressMapper.getCity());
							addressDetails.setPostalCode(addressMapper.getPostalCode());
							addressDetails.setTown(addressMapper.getTown());
							addressDetails.setState(addressMapper.getState());
							addressDetails.setLatitude(addressMapper.getLatitude());
							addressDetails.setLongitude(addressMapper.getLongitude());
							addressDetails.setLiveInd(true);
							addressDetails.setHouseNo(addressMapper.getHouseNo());
							addressRepository.save(addressDetails);

							mileageDetails.setTo_location(toaddressId);

						}
					}
					MileageDetails mileageDetailss = mileageRepository.save(mileageDetails);
					String mileageDetailsId = mileageDetailss.getMileage_details_id();
					EmployeeDetails details1 = employeeRepository.getEmployeeDetailsByEmployeeId(userId, true);
					if (null != details1) {
						Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(details1.getWorkplace(),details1.getOrgId());
						if (null != country1) {
							MileageRate rate = mileageRateRepository
									.getMileageDetailsByCountry(country1.getCountry_id());
							// voucher_amount =
							// voucher_amount+currencyExchangeService.getExchangePrice(mileageMapper.getCurrency(),
							// exchangeCurrency, mileageMapper.getDistances()*rate.getMileageRate(),
							// Utility.removeTime(new Date()));
							voucher_amount = voucher_amount + mileageMapper.getDistances() * rate.getMileageRate();

							System.out.println("koiaur hai...." + voucher_amount);
							System.out.println("koiaur hai...." + mileageMapper.getDistances() * rate.getMileageRate());
						}
					}
					/* insert to voucher-mileage-link */
					VoucherMileageLink voucherMileageLink = new VoucherMileageLink();
					voucherMileageLink.setMileage_id(mileageId);
					voucherMileageLink.setVoucher_id(voucherId);
					voucherMileageLink.setCreation_date(new Date());
					voucherMileageLink.setLive_ind(true);
					voucherMileageRepository.save(voucherMileageLink);

				}
			}

			/* insert to task info */
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setCreation_date(new Date());
			TaskInfo task = taskInfoRepository.save(taskInfo);

			String taskId = task.getTask_id();

			if (null != taskId) {

				/* insert to task details table */

				TaskDetails taskDetails = new TaskDetails();
				taskDetails.setTask_id(taskId);
				taskDetails.setCreation_date(new Date());
				taskDetails.setPriority("Medium");
				taskDetails.setTask_type("mileage");
				taskDetails.setTask_name("mileage");
				taskDetails.setTask_status("To Start");
				taskDetails.setUser_id(userId);
				taskDetails.setOrganization_id(orgId);
				taskDetails.setLiveInd(true);
				// taskDetails.setCompletion_ind(false);
				try {
					taskDetails.setStart_date(new Date());
					taskDetails.setEnd_date(new Date());

				} catch (Exception e) {
					e.printStackTrace();
				}

				taskDetailsRepository.save(taskDetails);

				processApprovalService.ProcessApprove(userId, "Mileage", taskDetails);

				/*
				 * sk /* insert to employee task link table
				 * 
				 * String hrId = empService.getHREmployeeId(orgId); String adminId =
				 * empService.getAdminIdByOrgId(orgId); String reportingManagerId = null;
				 * 
				 * if(null!=details) { if(!StringUtils.isEmpty(details.getReportingManager())) {
				 * reportingManagerId=details.getReportingManager(); }else
				 * if(StringUtils.isEmpty(details.getReportingManager())&&
				 * !StringUtils.isEmpty(hrId)) { reportingManagerId=hrId;
				 * 
				 * }else if(StringUtils.isEmpty(hrId)) { reportingManagerId=adminId; }
				 * 
				 * } EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
				 * employeeTaskLink.setTask_id(taskId); employeeTaskLink.setCreation_date(new
				 * Date()); employeeTaskLink.setLive_ind(true);
				 * employeeTaskLink.setEmployee_id(reportingManagerId);
				 * employeeTaskRepository.save(employeeTaskLink); sk
				 */

				/* insert to voucher details */
				VoucherDetails voucherDetails = new VoucherDetails();
				voucherDetails.setUser_id(userId);
				voucherDetails.setOrganization_id(orgId);
				voucherDetails.setStatus("Pending");
				voucherDetails.setAmount(voucher_amount);
				voucherDetails.setCreation_date(new Date());
				voucherDetails.setSubmitted_by(userId);
				voucherDetails.setCurrency(exchangeCurrency);
				voucherDetails.setVoucher_date(new Date());
				voucherDetails.setVoucher_type("Mileage");
				voucherDetails.setLive_ind(true);
				voucherDetails.setVoucher_id(voucherId);
				voucherDetails.setTask_id(taskId);
				voucherRepository.save(voucherDetails);
			}

			/* insert to Notification */
			/*
			 * sb NotificationDetails notification = new NotificationDetails();
			 * notification.setNotificationType("mileage"); notification.setUser_id(userId);
			 * //EmployeeDetails employee2
			 * =employeeRepository.getEmployeesByuserId(mileageMapper.getUserId());
			 * System.out.println("user name%%%%%%%%%%%%" +details.getFullName());
			 * notification.setAssignedBy(details.getFullName());
			 * notification.setAssignedTo(details.getReportingManager());
			 * notification.setNotificationDate(new Date());
			 * System.out.println("notification date%%%%%%%%%"+notification.
			 * getNotificationDate());
			 * System.out.println("notification id%%%%%%%%%"+notification.getNotificationId(
			 * )); notification.setMessage("" +details.getFirstName() +
			 * " requested approval for " + notification.getNotificationType());
			 * notification.setOrg_id(notification.getOrg_id());
			 * notification.setMessageReadInd(false);
			 * notificationRepository.save(notification);
			 * 
			 * EmployeeDetails details2 =
			 * employeeRepository.getEmployeeDetailsByEmployeeId(reportingManagerId,true);
			 * String myvar = "<div style=' display: block; margin-top: 100px; '>"+
			 * "    <div style='  text-align: center;'> </div>"+
			 * "    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"
			 * + "        <div class='box-2' style='  text-align: center;'>"+
			 * "            <h1 style='text-align: center; padding: 10px;'>Hello "+details2.
			 * getFirstName()+" </h1>"+
			 * "            <p style='text-align: center;'>fullName + \" requested approval for \" + notification.getNotificationType());"
			 * + "            </div>"+ "        </div>"+ "    </div>"+ "</div>";
			 * 
			 * String subject = "Mileage approve request....."; String from =
			 * "engage@tekorero.com"; String to = details2.getEmailId();
			 * 
			 * try { emailService.sendMail(from, to, subject, myvar); } catch (Exception e)
			 * {
			 * 
			 * e.printStackTrace(); } sb
			 */

		}
		VoucherMapper resultMapper = voucherService.getVoucherDetailsById(voucherId);
		return resultMapper;

	}

	@Override
	public MileageMapper getMileageRelatedDetails(String mileageId) {
		System.out.println("mileageId========" + mileageId);

		MileageDetails mileageDetails = mileageRepository.getMileageDetailsById(mileageId);

		MileageMapper mileageMapper = new MileageMapper();

		if (null != mileageDetails) {
			System.out.println("mileageId========" + mileageId + "!!" + mileageDetails.getMileage_id());
			mileageMapper.setMileageId(mileageId);
			mileageMapper.setClientName(mileageDetails.getClient_name());
			mileageMapper.setDistances(mileageDetails.getDistances());
			mileageMapper.setFromLocation(mileageDetails.getFrom_location());
			mileageMapper.setMileageDate(Utility.getISOFromDate(mileageDetails.getMileage_date()));
			mileageMapper.setMileageRate(mileageDetails.getMileage_rate());
			mileageMapper.setProjectName(mileageDetails.getProject_name());
			mileageMapper.setRemark(mileageDetails.getRemark());
			mileageMapper.setToLocation(mileageDetails.getTo_location());
			mileageMapper.setUserId(mileageDetails.getUser_id());
			mileageMapper.setOrganizationId(mileageDetails.getOrganization_id());
			mileageMapper.setCreationDate(Utility.getISOFromDate(mileageDetails.getCreation_date()));
			mileageMapper.setCurrency(mileageDetails.getCurrency());
			mileageMapper.setUnit(mileageDetails.getUnit());

		
		VoucherMileageLink voucherMileageLink = voucherMileageRepository.getByMileageId(mileageMapper.getMileageId());
		if (null != voucherMileageLink) {
			VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherMileageLink.getVoucher_id());
			if (null != voucherDetails) {
				mileageMapper.setStatus(voucherDetails.getStatus());
			}
		}
		AddressDetails fromaddress = addressRepository.getAddressDetailsByAddressId(mileageDetails.getFrom_location());

		AddressMapper fromAddressMapper = new AddressMapper();
		if (null != fromaddress) {

			fromAddressMapper.setAddress1(fromaddress.getAddressLine1());
			fromAddressMapper.setAddress2(fromaddress.getAddressLine2());
			fromAddressMapper.setAddressType(fromaddress.getAddressType());
			fromAddressMapper.setPostalCode(fromaddress.getPostalCode());
			fromAddressMapper.setStreet(fromaddress.getStreet());
			fromAddressMapper.setCity(fromaddress.getCity());
			fromAddressMapper.setTown(fromaddress.getTown());
			fromAddressMapper.setCountry(fromaddress.getCountry());
			fromAddressMapper.setLatitude(fromaddress.getLatitude());
			fromAddressMapper.setLongitude(fromaddress.getLongitude());
			fromAddressMapper.setState(fromaddress.getState());
			fromAddressMapper.setAddressId(fromaddress.getAddressId());
			fromAddressMapper.setHouseNo(fromaddress.getHouseNo());
			Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(fromaddress.getCountry(),mileageDetails.getOrganization_id());
			if (null != country) {
				fromAddressMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
				fromAddressMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
			}

			mileageMapper.setFromAddress(fromAddressMapper);
		}

		AddressDetails toaddress = addressRepository.getAddressDetailsByAddressId(mileageDetails.getTo_location());

		AddressMapper toAddressMapper = new AddressMapper();
		if (null != fromaddress) {

			toAddressMapper.setAddress1(toaddress.getAddressLine1());
			toAddressMapper.setAddress2(toaddress.getAddressLine2());
			toAddressMapper.setAddressType(toaddress.getAddressType());
			toAddressMapper.setPostalCode(toaddress.getPostalCode());
			toAddressMapper.setStreet(toaddress.getStreet());
			toAddressMapper.setCity(toaddress.getCity());
			toAddressMapper.setTown(toaddress.getTown());
			toAddressMapper.setCountry(toaddress.getCountry());
			toAddressMapper.setLatitude(toaddress.getLatitude());
			toAddressMapper.setLongitude(toaddress.getLongitude());
			toAddressMapper.setState(toaddress.getState());
			toAddressMapper.setAddressId(toaddress.getAddressId());
			toAddressMapper.setHouseNo(toaddress.getHouseNo());
			Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(toaddress.getCountry(),mileageDetails.getOrganization_id());
			if (null != country) {
				fromAddressMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
				fromAddressMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
			}

			mileageMapper.setToAddress(toAddressMapper);
		}
		}
		return mileageMapper;

	}

	@Override
	public List<MileageMapper> getMileageDetailsListByUserId(String userId) {
		List<MileageDetails> mileageList = mileageRepository.getMileageListByUserId(userId);
		List<MileageMapper> mapperList = new ArrayList<MileageMapper>();
		// for (MileageDetails mileageDetails : mileageList) {
		mileageList.stream().map(mileageDetails -> {
			MileageMapper mileageMapper = getMileageRelatedDetails(mileageDetails.getMileage_id());
			mapperList.add(mileageMapper);
			// }
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList;
	}

	@Override
	public List<MileageMapper> getMileageDetailsListByOrganizationId(String organizationId) {
		List<MileageDetails> mileageList = mileageRepository.getMileageListByOrganizationId(organizationId);
		List<MileageMapper> mapperList = new ArrayList<MileageMapper>();
		// for (MileageDetails mileageDetails : mileageList) {
		mileageList.stream().map(mileageDetails -> {
			MileageMapper mileageMapper = getMileageRelatedDetails(mileageDetails.getMileage_id());
			mapperList.add(mileageMapper);
			// }
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList;
	}

	@Override
	public List<MileageMapper> getMileageListByVoucherId(String voucherId) {

//		List<MileageMapper> mileageList = new ArrayList<MileageMapper>();
//		if(null!=voucherId) {
//			List<VoucherMileageLink> voucherMileagelist = voucherMileageRepository.getMileageListByVoucherId(voucherId);
//
//			if(null!=voucherMileagelist && !voucherMileagelist.isEmpty()) {
//				//for (VoucherMileageLink voucherMileageLink : voucherMileagelist) {
//				voucherMileagelist.stream().map(voucherMileageLink -> {
//					MileageMapper  mileageMapper = getMileageRelatedDetails(voucherMileageLink.getMileage_id());
//					System.out.println("mileageMapper============="+mileageMapper.toString());
//					mileageList.add(mileageMapper);
//					return mileageList;
//				}).collect(Collectors.toList());
//			}
//		}

//		return mileageList;
		return voucherMileageRepository.getMileageListByVoucherId(voucherId).stream()
				.map(li -> getMileageRelatedDetailsByMileageIdAndVoucherId(li.getMileage_id(), li.getVoucher_id()))
				.filter(li1 -> li1.getCreationDate() != null)
				.sorted((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate())).collect(Collectors.toList());
	}

	@Override
	public MileageMapper getMileageRelatedDetailsByMileageIdAndVoucherId(String mileageId, String voucherId) {
		System.out.println("mileageId========" + mileageId);

		MileageDetails mileageDetails = mileageRepository.getMileageDetailsById(mileageId);

		MileageMapper mileageMapper = new MileageMapper();

		if (null != mileageDetails) {
			System.out.println("mileageId========" + mileageId + "!!" + mileageDetails.getMileage_id());
			mileageMapper.setMileageId(mileageId);
			mileageMapper.setClientName(mileageDetails.getClient_name());
			mileageMapper.setDistances(mileageDetails.getDistances());
			mileageMapper.setFromLocation(mileageDetails.getFrom_location());
			mileageMapper.setMileageDate(Utility.getISOFromDate(mileageDetails.getMileage_date()));
			mileageMapper.setMileageRate(mileageDetails.getMileage_rate());
			mileageMapper.setProjectName(mileageDetails.getProject_name());
			mileageMapper.setRemark(mileageDetails.getRemark());
			mileageMapper.setToLocation(mileageDetails.getTo_location());
			mileageMapper.setUserId(mileageDetails.getUser_id());
			mileageMapper.setOrganizationId(mileageDetails.getOrganization_id());
			mileageMapper.setCreationDate(Utility.getISOFromDate(mileageDetails.getCreation_date()));
			mileageMapper.setCurrency(mileageDetails.getCurrency());
			mileageMapper.setUnit(mileageDetails.getUnit());

		}

		VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherId);
		if (null != voucherDetails) {
			mileageMapper.setStatus(voucherDetails.getStatus());
		}

		return mileageMapper;
	}

	@Override
	public MileageMapper updateMileageDetails(MileageMapper mileageMapper) {
		MileageMapper resultMapper = null;

		if (null != mileageMapper.getMileageId()) {

			MileageDetails mileageDetails = mileageRepository.getMileageDetailsById(mileageMapper.getMileageId());
			if (null != mileageDetails) {
				if (mileageMapper.getMileageRate() != 0)
					mileageDetails.setMileage_rate(mileageMapper.getMileageRate());
				if (null != mileageMapper.getClientName())
					mileageDetails.setClient_name(mileageMapper.getClientName());
				if (null != mileageMapper.getFromAddress()) {

					AddressDetails fromaddress = addressRepository
							.getAddressDetailsByAddressId(mileageDetails.getFrom_location());

					AddressMapper fromAddressMapper = mileageMapper.getFromAddress();
					if (null != fromaddress) {

						AddressDetails addressDetails = new AddressDetails();
//						addressDetails.setAddressId(mileageDetails.getFrom_location());
						addressDetails.setAddressLine1(fromAddressMapper.getAddress1());
						addressDetails.setAddressLine2(fromAddressMapper.getAddress2());
						addressDetails.setAddressType(fromAddressMapper.getAddressType());
						addressDetails.setCountry(fromAddressMapper.getCountry());
//						addressDetails.setCreationDate(new Date());
						addressDetails.setStreet(fromAddressMapper.getStreet());
						addressDetails.setCity(fromAddressMapper.getCity());
						addressDetails.setPostalCode(fromAddressMapper.getPostalCode());
						addressDetails.setTown(fromAddressMapper.getTown());
						addressDetails.setState(fromAddressMapper.getState());
						addressDetails.setLatitude(fromAddressMapper.getLatitude());
						addressDetails.setLongitude(fromAddressMapper.getLongitude());
						addressDetails.setLiveInd(true);
						addressDetails.setHouseNo(fromAddressMapper.getHouseNo());
						addressRepository.save(addressDetails);

					} else {
						AddressMapper addressMapper = mileageMapper.getFromAddress();
						/* insert to address info & address deatils */

						AddressInfo addressInfo = new AddressInfo();
						addressInfo.setCreationDate(new Date());
						// addressInfo.setCreatorId(candidateMapperr.getUserId());
						AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

						String fromaddressId = addressInfoo.getId();

						if (null != fromaddressId) {

							AddressDetails addressDetails = new AddressDetails();
							addressDetails.setAddressId(fromaddressId);
							addressDetails.setAddressLine1(addressMapper.getAddress1());
							addressDetails.setAddressLine2(addressMapper.getAddress2());
							addressDetails.setAddressType(addressMapper.getAddressType());
							addressDetails.setCountry(addressMapper.getCountry());
							addressDetails.setCreationDate(new Date());
							addressDetails.setStreet(addressMapper.getStreet());
							addressDetails.setCity(addressMapper.getCity());
							addressDetails.setPostalCode(addressMapper.getPostalCode());
							addressDetails.setTown(addressMapper.getTown());
							addressDetails.setState(addressMapper.getState());
							addressDetails.setLatitude(addressMapper.getLatitude());
							addressDetails.setLongitude(addressMapper.getLongitude());
							addressDetails.setLiveInd(true);
							addressDetails.setHouseNo(addressMapper.getHouseNo());
							addressRepository.save(addressDetails);

							mileageDetails.setFrom_location(fromaddressId);
						}
					}
				}

				if (null != mileageMapper.getProjectName())
					mileageDetails.setProject_name(mileageMapper.getProjectName());

				if (null != mileageMapper.getRemark())
					mileageDetails.setRemark(mileageMapper.getRemark());
				if (null != mileageMapper.getMileageDate())
					try {
						mileageDetails.setMileage_date(Utility.getDateFromISOString(mileageMapper.getMileageDate()));
					} catch (Exception e) {
						e.printStackTrace();
					}

				if (null != mileageMapper.getToAddress()) {
					AddressDetails toaddress = addressRepository
							.getAddressDetailsByAddressId(mileageDetails.getTo_location());

					AddressMapper toAddressMapper = mileageMapper.getToAddress();
					if (null != toaddress) {

						AddressDetails addressDetails = new AddressDetails();

						addressDetails.setAddressLine1(toAddressMapper.getAddress1());
						addressDetails.setAddressLine2(toAddressMapper.getAddress2());
						addressDetails.setAddressType(toAddressMapper.getAddressType());
						addressDetails.setCountry(toAddressMapper.getCountry());
						addressDetails.setStreet(toAddressMapper.getStreet());
						addressDetails.setCity(toAddressMapper.getCity());
						addressDetails.setPostalCode(toAddressMapper.getPostalCode());
						addressDetails.setTown(toAddressMapper.getTown());
						addressDetails.setState(toAddressMapper.getState());
						addressDetails.setLatitude(toAddressMapper.getLatitude());
						addressDetails.setLongitude(toAddressMapper.getLongitude());
						addressDetails.setLiveInd(true);
						addressDetails.setHouseNo(toAddressMapper.getHouseNo());
						addressRepository.save(addressDetails);

					} else {
						AddressMapper addressMapper = mileageMapper.getToAddress();
						/* insert to address info & address deatils & customeraddressLink */

						AddressInfo addressInfo = new AddressInfo();
						addressInfo.setCreationDate(new Date());
						// addressInfo.setCreatorId(candidateMapperr.getUserId());
						AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

						String toaddressId = addressInfoo.getId();

						if (null != toaddressId) {

							AddressDetails addressDetails = new AddressDetails();
							addressDetails.setAddressId(toaddressId);
							addressDetails.setAddressLine1(addressMapper.getAddress1());
							addressDetails.setAddressLine2(addressMapper.getAddress2());
							addressDetails.setAddressType(addressMapper.getAddressType());
							addressDetails.setCountry(addressMapper.getCountry());
							addressDetails.setCreationDate(new Date());
							addressDetails.setStreet(addressMapper.getStreet());
							addressDetails.setCity(addressMapper.getCity());
							addressDetails.setPostalCode(addressMapper.getPostalCode());
							addressDetails.setTown(addressMapper.getTown());
							addressDetails.setState(addressMapper.getState());
							addressDetails.setLatitude(addressMapper.getLatitude());
							addressDetails.setLongitude(addressMapper.getLongitude());
							addressDetails.setLiveInd(true);
							addressDetails.setHouseNo(addressMapper.getHouseNo());
							addressRepository.save(addressDetails);

							mileageDetails.setTo_location(toaddressId);

						}

					}

				}
				if (null != mileageMapper.getUserId())
					mileageDetails.setUser_id(mileageMapper.getUserId());
				if (null != mileageMapper.getUnit())
					mileageDetails.setUnit(mileageMapper.getUnit());

				if (0 != mileageMapper.getDistances()) {
					System.out.println("mileageMapper.getDistances()======" + mileageMapper.getDistances());
					EmployeeDetails details1 = employeeRepository
							.getEmployeeDetailsByEmployeeId(mileageDetails.getUser_id(), true);
					if (null != details1) {
						Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(details1.getWorkplace(),mileageDetails.getOrganization_id());
						if (null != country1) {
							MileageRate rate = mileageRateRepository
									.getMileageDetailsByCountry(country1.getCountry_id());
							if (null != rate) {
								System.out.println("rate.getMileageRate()======" + rate.getMileageRate());
								VoucherMileageLink voucherMileageLink = voucherMileageRepository
										.getByMileageId(mileageMapper.getMileageId());
								if (null != voucherMileageLink) {
									System.out.println("voucherMileageLink.getVoucher_id()======"
											+ voucherMileageLink.getVoucher_id());
									VoucherDetails voucherDetails = voucherRepository
											.getVoucherDetailsById(voucherMileageLink.getVoucher_id());
									if (null != voucherDetails) {
										System.out.println(
												"voucherDetails.getAmount()======" + voucherDetails.getAmount());
										double final_amount = 0;
										double voucher_amount = voucherDetails.getAmount()
												- (mileageDetails.getDistances() * rate.getMileageRate());
										System.out.println("voucher_amount======" + voucher_amount);
										final_amount = voucher_amount
												+ (mileageMapper.getDistances() * rate.getMileageRate());
										System.out.println("final_amount======" + final_amount);
										voucherDetails.setAmount(final_amount);
										voucherRepository.save(voucherDetails);
									}
								}
							}
						}
					}
				}
				if (mileageMapper.getDistances() != 0)
					mileageDetails.setDistances(mileageMapper.getDistances());
				mileageRepository.save(mileageDetails);
				resultMapper = getMileageRelatedDetails(mileageMapper.getMileageId());
			}
		}
		return resultMapper;
	}

	@Override
	public List<MileageMapper> getMileageListByUserIdWithDateRange(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		List<MileageMapper> resultList = new ArrayList<MileageMapper>();
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<MileageDetails> mileageList = mileageRepository.getMileageByUserIdWithDateRange(userId, start_date,
				end_date);

		if (null != mileageList && !mileageList.isEmpty()) {

			// for (MileageDetails mileageDetails : mileageList) {
			mileageList.forEach(mileageDetails -> {
				MileageMapper mileageMapper = new MileageMapper();
				mileageMapper.setClientName(mileageDetails.getClient_name());
				mileageMapper.setDistances(mileageDetails.getDistances());
				mileageMapper.setCreationDate(Utility.getISOFromDate(mileageDetails.getCreation_date()));
				mileageMapper.setFromLocation(mileageDetails.getFrom_location());
				mileageMapper.setMileageDate(Utility.getISOFromDate(mileageDetails.getMileage_date()));
				mileageMapper.setMileageId(mileageDetails.getMileage_id());
				mileageMapper.setMileageRate(mileageDetails.getMileage_rate());
				mileageMapper.setProjectName(mileageDetails.getProject_name());
				mileageMapper.setRemark(mileageDetails.getRemark());
				mileageMapper.setToLocation(mileageDetails.getTo_location());

				resultList.add(mileageMapper);
			});

		}
		return resultList;
	}

	@Override
	public List<MileageMapper> getMileageListByOrgIdWithDateRange(String orgId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		List<MileageMapper> resultList = new ArrayList<MileageMapper>();
		try {
			end_date = Utility.getDateAfterEndDate(Utility.getDateFromISOString(endDate));
			start_date = Utility.getDateFromISOString(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<MileageDetails> mileageList = mileageRepository.getMileagesByOrgIdWithDateRange(orgId, start_date,
				end_date);

		if (null != mileageList && !mileageList.isEmpty()) {

			// for (MileageDetails mileageDetails : mileageList) {
			mileageList.stream().map(mileageDetails -> {
				MileageMapper mileageMapper = new MileageMapper();
				mileageMapper.setClientName(mileageDetails.getClient_name());
				mileageMapper.setDistances(mileageDetails.getDistances());
				mileageMapper.setCreationDate(Utility.getISOFromDate(mileageDetails.getCreation_date()));
				mileageMapper.setFromLocation(mileageDetails.getFrom_location());
				mileageMapper.setMileageDate(Utility.getISOFromDate(mileageDetails.getMileage_date()));
				mileageMapper.setMileageId(mileageDetails.getMileage_id());
				mileageMapper.setMileageRate(mileageDetails.getMileage_rate());
				mileageMapper.setProjectName(mileageDetails.getProject_name());
				mileageMapper.setRemark(mileageDetails.getRemark());
				mileageMapper.setToLocation(mileageDetails.getTo_location());

				resultList.add(mileageMapper);
				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;

	}

	@Override
	public String saveMileageRate(List<MileageRateMapper> mileageRatelist, String userId, String orgId) {
		String mileageRateId = null;

		if (null != mileageRatelist && !mileageRatelist.isEmpty()) {

			List<MileageRate> ratee = mileageRateRepository.getMileageDetailByOrgId(orgId);
			if (ratee != null && !ratee.isEmpty()) {
				for (MileageRate mileageRate : ratee) {
					mileageRateRepository.delete(mileageRate);
				}
			}

			for (MileageRateMapper mileageRateMapper : mileageRatelist) {
				MileageRate rate = new MileageRate();
				rate.setMileageRate(mileageRateMapper.getMileageRate());
				rate.setCountry(mileageRateMapper.getCountry());
				rate.setUserId(userId);
				rate.setOrganizationId(orgId);
				rate.setCreationDate(new Date());
				rate.setLiveInd(true);
				rate.setUpdatedBy(userId);
				rate.setUpdationDate(new Date());
				MileageRate rate1 = mileageRateRepository.save(rate);
				mileageRateId = rate1.getMileageRateId();
			}

		}
		return mileageRateId;
	}

	@Override
	public String saveMileageRateByCountry(String country, double rate, String userId, String orgId) {
		String mileageRateId = null;
		MileageRate ratee = mileageRateRepository.findByCountryAndOrganizationId(country, orgId);
		if (null != ratee) {
			ratee.setMileageRate(rate);
			ratee.setCountry(country);
			ratee.setUserId(userId);
			ratee.setOrganizationId(orgId);
			ratee.setCreationDate(new Date());
			ratee.setLiveInd(true);
			ratee.setUpdatedBy(userId);
			ratee.setUpdationDate(new Date());
			MileageRate rate1 = mileageRateRepository.save(ratee);
			mileageRateId = rate1.getMileageRateId();

		} else {
			MileageRate rate2 = new MileageRate();
			rate2.setMileageRate(rate);
			rate2.setCountry(country);
			rate2.setUserId(userId);
			rate2.setOrganizationId(orgId);
			rate2.setCreationDate(new Date());
			rate2.setLiveInd(true);
			rate2.setUpdatedBy(userId);
			rate2.setUpdationDate(new Date());
			MileageRate rate10 = mileageRateRepository.save(rate2);
			mileageRateId = rate10.getMileageRateId();

		}

		return mileageRateId;
	}

	@Override
	public List<MileageRateMapper> getMileageRate(String orgId) {
		List<MileageRate> mileageRateList = mileageRateRepository.getMileageDetailByOrgId(orgId);

		List<MileageRateMapper> resultMapper = new ArrayList<MileageRateMapper>();

		for (MileageRate mileageRate : mileageRateList) {
			MileageRateMapper mileageRateMapper = new MileageRateMapper();
			mileageRateMapper.setMileageRate(mileageRate.getMileageRate());
			mileageRateMapper.setCreationDate(Utility.getISOFromDate(mileageRate.getCreationDate()));
			mileageRateMapper.setUserId(mileageRate.getUserId());
			mileageRateMapper.setOrganizationId(mileageRate.getOrganizationId());
			Country country2 = countryRepository.getByCountryId(mileageRate.getCountry());
			if (null != country2) {
				mileageRateMapper.setCountry(country2.getCountryName());
			}

			resultMapper.add(mileageRateMapper);
		}
		Collections.sort(resultMapper,
				(MileageRateMapper m1, MileageRateMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		List<MileageRate> list = mileageRateRepository.findByOrganizationId(orgId);
		if (null != list && !list.isEmpty()) {
			Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
			resultMapper.get(0).setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
		}

		return resultMapper;
	}

	@Override
	public String deleteMileage(String mileageId) {
		String message = null;
		MileageDetails mileageDetails = mileageRepository.getMileageDetailsById(mileageId);
		if (null != mileageDetails) {
			mileageDetails.setLive_ind(false);
			mileageRepository.save(mileageDetails);

			VoucherMileageLink voucherMileageLink = voucherMileageRepository
					.getByMileageId(mileageDetails.getMileage_id());
			if (null != voucherMileageLink) {
				voucherMileageLink.setLive_ind(false);
				voucherMileageRepository.save(voucherMileageLink);

				List<VoucherMileageLink> voucherMileageLink1 = voucherMileageRepository
						.getMileageListByVoucherId(voucherMileageLink.getVoucher_id());
				if (voucherMileageLink1.size() == 0) {
					VoucherDetails voucherDetails = voucherRepository
							.getVoucherDetailsById(voucherMileageLink.getVoucher_id());
					if (null != voucherDetails) {
						voucherDetails.setLive_ind(false);
						voucherRepository.save(voucherDetails);
						TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(voucherDetails.getTask_id());
						if (null != taskDetails) {
							taskDetails.setLiveInd(false);
							taskDetailsRepository.save(taskDetails);
						}
					}
				}
			}

			message = "Mileage Deleted Successfully";
		}

		return message;
	}

	@Override
	public String deleteVoucher(String voucherId) {
		String message = null;
		VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherId);
		if (null != voucherDetails) {
			voucherDetails.setLive_ind(false);
			voucherRepository.save(voucherDetails);

			List<VoucherMileageLink> voucherMileageLink1 = voucherMileageRepository
					.getMileageListByVoucherId(voucherDetails.getVoucher_id());
			if (null != voucherMileageLink1 && !voucherMileageLink1.isEmpty()) {
				for (VoucherMileageLink voucherMileageLink : voucherMileageLink1) {

					voucherMileageLink.setLive_ind(false);
					voucherMileageRepository.save(voucherMileageLink);

					MileageDetails mileageDetails = mileageRepository
							.getMileageDetailsById(voucherMileageLink.getMileage_id());
					if (null != mileageDetails) {
						mileageDetails.setLive_ind(false);
						mileageRepository.save(mileageDetails);
					}
				}
			}

			TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(voucherDetails.getTask_id());
			if (null != taskDetails) {
				taskDetails.setLiveInd(false);
				taskDetailsRepository.save(taskDetails);
			}
			message = "Voucher Deleted Successfully";
		}

		return message;
	}

	@Override
	public List<VoucherMapper> getMileageStatusListByUserId(String userId, String status, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creation_date").descending());
		List<VoucherMapper> resulList = new ArrayList<>();
		Page<VoucherDetails> list = voucherRepository.getVoucherListByUserIdAndVoucherTypeAndStatus(userId, "Mileage",
				status, paging);
		if (null != list && !list.isEmpty()) {
			resulList = list.stream().map(voucherDetails -> {
				VoucherMapper voucherMapper = voucherService.getVoucherDetailsById(voucherDetails.getVoucher_id());
				voucherMapper.setPageCount(list.getTotalPages());
				voucherMapper.setDataCount(list.getSize());
				voucherMapper.setListCount(list.getTotalElements());
				return voucherMapper;
			}).collect(Collectors.toList());
		}
		return resulList;
	}

	@Override
	public List<VoucherMapper> getMileageListByOrgIdWithDateRangeAndStatus(String organizationId, String startDate,
			String endDate, String status) {
		Date end_date = null;
		Date start_date = null;

		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<VoucherMapper> resulList = new ArrayList<>();
		List<VoucherDetails> list = voucherRepository
				.getVoucherListByOrganisationIdAndVoucherTypeAndStatusWithDateRange(organizationId, "Mileage", status,
						start_date, end_date);
		System.out.println("listsize---" + list.size());
		if (null != list && !list.isEmpty()) {
			list.stream().map(voucherDetails -> {
				System.out.println("voucherDetails.getVoucher_id()" + voucherDetails.getVoucher_id());
				VoucherMapper voucherMapper = voucherService.getVoucherDetailsById(voucherDetails.getVoucher_id());
				resulList.add(voucherMapper);
				return resulList;
			}).collect(Collectors.toList());

		}
		return resulList;
	}

	@Override
	public ByteArrayInputStream exportMileage(List<VoucherMapper> mileageList) {

		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("mileage");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < mileage_heading.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(mileage_heading[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != mileageList && !mileageList.isEmpty()) {
			for (VoucherMapper voucher : mileageList) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(voucher.getVoucherId());
				row.createCell(1).setCellValue(voucher.getVoucherDate());
				row.createCell(2).setCellValue(voucher.getSubmittedBy());
				row.createCell(3).setCellValue(voucher.getAmount());
				row.createCell(4).setCellValue(voucher.getStatus());
			}

		}
		// Resize all columns to fit the content size
		for (int i = 0; i < mileage_heading.length; i++) {
			sheet.autoSizeColumn(i);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(outputStream.toByteArray());

	}

	@Override
	public String saveMileageNotes(NotesMapper notesMapper) {

		String notesId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			notesId = note.getNotes_id();

			/* insert to Mileage-notes-link */

			MileageNotesLink mileageNotesLink = new MileageNotesLink();
			mileageNotesLink.setMileageId(notesMapper.getMileageId());
			mileageNotesLink.setNoteId(notesId);
			mileageNotesLink.setCreationDate(new Date());

			mileageNotesLinkRepository.save(mileageNotesLink);

		}
		return notesId;

	}

	@Override
	public List<NotesMapper> getNoteListByMileageId(String mileageId) {
		List<MileageNotesLink> leadsNotesLinkList = mileageNotesLinkRepository.getNotesIdByMileageId(mileageId);
		if (leadsNotesLinkList != null && !leadsNotesLinkList.isEmpty()) {
			return leadsNotesLinkList.stream().map(leadsNotesLink -> {
				NotesMapper notesMapper = getNotes(leadsNotesLink.getNoteId());
				return notesMapper;
			}).collect(Collectors.toList());
		}
		return null;
	}

	private NotesMapper getNotes(String id) {
		Notes notes = notesRepository.findByNoteId(id);
		NotesMapper notesMapper = new NotesMapper();
		if (null != notes) {
			notesMapper.setNotesId(notes.getNotes_id());
			notesMapper.setNotes(notes.getNotes());
			notesMapper.setCreationDate(Utility.getISOFromDate(notes.getCreation_date()));
			if (!StringUtils.isEmpty(notes.getUserId())) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeesByuserId(notes.getUserId());
				String fullName = "";
				String middleName = "";
				String lastName = "";
				if (null != employeeDetails.getMiddleName()) {

					middleName = employeeDetails.getMiddleName();
				}
				if (null != employeeDetails.getLastName()) {
					lastName = employeeDetails.getLastName();
				}
				fullName = employeeDetails.getFirstName() + " " + middleName + " " + lastName;
				notesMapper.setOwnerName(fullName);
			}
		}
		return notesMapper;

	}

//	@Override
//	public NotesMapper updateNoteDetails(String notesId, NotesMapper notesMapper) {
//
//		NotesMapper resultMapper = employeeService.updateNotes(notesMapper);
//
//		return resultMapper;
//	}

	@Override
	public void deleteMileageNotesById(String notesId) {
		MileageNotesLink notesList = mileageNotesLinkRepository.findByNoteId(notesId);
		if (null != notesList) {

			Notes notes = notesRepository.findByNoteId(notesId);
			if (null != notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}
	}

}
