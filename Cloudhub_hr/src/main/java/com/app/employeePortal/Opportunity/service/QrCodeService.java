package com.app.employeePortal.Opportunity.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.employeePortal.Opportunity.entity.QrCode;
import com.app.employeePortal.Opportunity.repository.QrCodeRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QrCodeService {
	
	@Autowired
	QrCodeRepository qrCodeRepository;

     public void generateAndSaveQRCode2( String text, byte[] qrCodeImageBytes)
             throws IOException, WriterException {
     
     // Save QR code image to the database
     QrCode qrCode = new QrCode();
     qrCode.setQrCodeImage(qrCodeImageBytes);
     qrCode.setText(text);
     qrCodeRepository.save(qrCode);
     }
     
     
     public byte[] generateAndSaveQRCode( String text)
             throws IOException, WriterException {
    	 QRCodeWriter qrCodeWriter = new QRCodeWriter();
         BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);

         // Convert BitMatrix to byte array
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
         byte[] qrCodeImageBytes = outputStream.toByteArray();
         return qrCodeImageBytes;

     }


	public byte[] getQrCodeByQrCodeId(long qrCodeId) {
		QrCode qrcode = qrCodeRepository.findById(qrCodeId).orElseThrow(() -> 
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Qr Code not found " + qrCodeId));
		return qrcode.getQrCodeImage();
	}
}
