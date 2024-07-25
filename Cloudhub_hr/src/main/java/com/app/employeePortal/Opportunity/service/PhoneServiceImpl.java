package com.app.employeePortal.Opportunity.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.Opportunity.entity.Phone;
import com.app.employeePortal.Opportunity.entity.PhoneBrandCount;
import com.app.employeePortal.Opportunity.entity.PhoneDispatchCount;
import com.app.employeePortal.Opportunity.entity.QrCode;
import com.app.employeePortal.Opportunity.mapper.PhoneDetailsDTO;
import com.app.employeePortal.Opportunity.repository.OrderDetailsRepository;
import com.app.employeePortal.Opportunity.repository.PhoneBrandCountRepository;
import com.app.employeePortal.Opportunity.repository.PhoneDispatchCountRepository;
import com.app.employeePortal.Opportunity.repository.PhoneRepository;
import com.app.employeePortal.Opportunity.repository.QrCodeRepository;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.google.zxing.WriterException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class PhoneServiceImpl implements Phoneservice {
	@Autowired
	PhoneRepository phoneRepository;
	@Autowired
	PhoneDispatchCountRepository phoneDispatchCountRepository;
	@Autowired
	PhoneBrandCountRepository phoneBrandCountRepository;
	@Autowired
	QrCodeService qrCodeService;
	@Autowired
	QrCodeRepository qrCodeRepository;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	EmployeeRepository userRepository;
	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	@Override
	public String savePhoneDetails(PhoneDetailsDTO phoneDetailsDTO) {

		Phone phn = new Phone();
		setPropertyOnInput(phoneDetailsDTO, phn);
		byte[] qrCode = null;
		try {
			qrCode = qrCodeService.generateAndSaveQRCode(phoneDetailsDTO.getOrderPhoneId());
		} catch (IOException | WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Phone phonesUD = phoneRepository.save(phn);
		QrCode qr = new QrCode();
		qr.setQrCodeImage(qrCode);
		qr.setText(phoneDetailsDTO.getOrderPhoneId());
		qr.setPhone(phonesUD);
		qrCodeRepository.save(qr);
		// phn.setQrCode(qr);

		return phonesUD.getId();
	}

	private void setPropertyOnInput(PhoneDetailsDTO phoneDetailsDTO, Phone phn) {

		phn.setId(phoneDetailsDTO.getPhoneId());
		phn.setCompany(phoneDetailsDTO.getCompany());
		phn.setModel(phoneDetailsDTO.getModel());
		phn.setIMEI(phoneDetailsDTO.getIMEI());
		phn.setOrderPhoneId(phoneDetailsDTO.getOrderPhoneId());
		phn.setExcelId(phoneDetailsDTO.getExcelId());
		phn.setUserId(phoneDetailsDTO.getUserId());
		phn.setDistributorId(phoneDetailsDTO.getDistributorId());
		phn.setTask1(phoneDetailsDTO.getTask1());
		phn.setTask2(phoneDetailsDTO.getTask2());
		phn.setTask3(phoneDetailsDTO.getTask3());
		phn.setOS(phoneDetailsDTO.getOS());
		phn.setGB(phoneDetailsDTO.getGB());
		phn.setColor(phoneDetailsDTO.getColor());
		phn.setConditions(phoneDetailsDTO.getConditions());
		phn.setCreateAt(new Date());
		Phone phonesUD1 = phoneRepository.save(phn);

		List<Phone> list1 = phoneRepository.findByOpportunityIdAndActive(phonesUD1.getOpportunityId(), true);
		if (null != list1 && !list1.isEmpty()) {

			PhoneDispatchCount phoneDispatchCount = phoneDispatchCountRepository
					.findByOpportunityId(phonesUD1.getOpportunityId());
			if (null != phoneDispatchCount) {
				phoneDispatchCount.setTotalQuantity(list1.size());
				phoneDispatchCount.setRemainingQuantity(list1.size());
				phoneDispatchCount.setRepairRemainingQuantity(list1.size());
				phoneDispatchCountRepository.save(phoneDispatchCount);
			} else {
				PhoneDispatchCount phoneDispatchCount1 = new PhoneDispatchCount();
				phoneDispatchCount1.setOpportunityId(phonesUD1.getOpportunityId());
				phoneDispatchCount1.setTotalQuantity(list1.size());
				phoneDispatchCount1.setRemainingQuantity(list1.size());
				phoneDispatchCount1.setRepairRemainingQuantity(list1.size());
				phoneDispatchCountRepository.save(phoneDispatchCount1);
			}

			PhoneBrandCount phoneBrandCount = phoneBrandCountRepository
					.findByOpportunityIdAndCompany(phonesUD1.getOpportunityId(), phonesUD1.getCompany());
			System.out.println("phone.getOrderPhoneId....." + phonesUD1.getOrderPhoneId());
			System.out.println("phone.getCompany______" + phonesUD1.getCompany());
			if (null != phoneBrandCount) {

				System.out.println("phone.getOrderPhoneId..insideif..." + phonesUD1.getOpportunityId());
				System.out.println("phone.getCompany___insideif___" + phonesUD1.getCompany());
				int countPONumber = phoneBrandCountRepository.countBygetOpportunityIdAndCompany(phonesUD1.getOpportunityId(),
						phonesUD1.getCompany());
				phoneBrandCount.setTotalQuantity(phoneBrandCount.getTotalQuantity() + countPONumber);
				phoneBrandCount.setRemainingQuantity(phoneBrandCount.getRemainingQuantity() + countPONumber);
				phoneBrandCountRepository.save(phoneBrandCount);
			} else {
				int countPONumber = phoneBrandCountRepository.countBygetOpportunityIdAndCompany(phonesUD1.getOpportunityId(),
						phonesUD1.getCompany());
				PhoneBrandCount phoneBrandCount1 = new PhoneBrandCount();

				phoneBrandCount1.setOpportunityId(phonesUD1.getOpportunityId());
				phoneBrandCount1.setCompany(phonesUD1.getCompany());
				phoneBrandCount1.setTotalQuantity(countPONumber + 1);
				phoneBrandCount1.setRemainingQuantity(countPONumber + 1);
				phoneBrandCountRepository.save(phoneBrandCount1);
			}
		}
	}

}
