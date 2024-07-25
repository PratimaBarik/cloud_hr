package com.app.employeePortal.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.api.dto.RazorPayDTO;
import com.app.employeePortal.api.entity.Payment;
import com.app.employeePortal.api.repository.StripePaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@RestController
@CrossOrigin(maxAge = 3600)
public class RazorpayController {

	@Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;
    
    @Autowired
    StripePaymentRepository stripePaymentRepository;
    
	@RequestMapping("/api/v1/razorpay/order")
	public ResponseEntity<String> postToRazorpay(@RequestBody RazorPayDTO razorPayDTO) throws RazorpayException {
		System.out.println("hello .........");
		String razorPayOrderid = null;

		try {

			RazorpayClient client = new RazorpayClient(razorpayKey, razorpaySecret);

			JSONObject orderRequest = new JSONObject();

			try {
				orderRequest.put("amount", razorPayDTO.getRazorPayAmount());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // amount in the smallest currency unit

			try {
				orderRequest.put("currency", razorPayDTO.getCurrency());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				orderRequest.put("receipt", razorPayDTO.getOrderId());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Order order = client.Orders.create(orderRequest);

			System.out.println("response..." + order.get("id"));
			razorPayOrderid = order.get("id");

		} catch (RazorpayException e) {

			// Handle Exception

			System.out.println(e.getMessage());

		}
		// send response to frontend
		
		return ResponseEntity.ok(razorPayOrderid) ;

	}

	@PostMapping("/api/v1/razorpay/verify")
	public Map<String, String> verifyPayment(@RequestBody RazorPayDTO razorPayDTO) throws RazorpayException {
		String razorpayPaymentId = razorPayDTO.getRazorpayPaymentId();
		String razorpayOrderId = razorPayDTO.getRazorpayOrderId();
		String razorpaySignature = razorPayDTO.getRazorpaySignature();
		System.out.println("key,,,,,,    "+razorpayPaymentId);
		System.out.println("key,,,,,,    "+razorpayOrderId);
		System.out.println("key,,,,,,    "+razorpaySignature);
		RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");

		String secret = razorpaySecret;

		JSONObject options = new JSONObject();
		try {
			options.put("razorpay_order_id", razorpayOrderId);
			options.put("razorpay_payment_id", razorpayPaymentId);
			options.put("razorpay_signature", razorpaySignature);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		boolean status =  Utils.verifyPaymentSignature(options, secret);
		// Implement your logic to verify the payment signature here
		Map<String, String> response = new HashMap<>();

		if (status) {
			response.put("status", "success");
			Payment payment = new Payment();
			payment.setPaymentStatus("success");
			payment.setPaymentType("razorpay");
			payment.setUpiPaymentId(razorpayPaymentId);
			payment.setSignature(razorpaySignature);
			stripePaymentRepository.save(payment);
			
		} else {
			response.put("status", "failure");
			Payment payment = new Payment();
			payment.setPaymentStatus("failure");
			payment.setPaymentType("razorpay");
			payment.setUpiPaymentId(razorpayPaymentId);
			payment.setSignature(razorpaySignature);
			stripePaymentRepository.save(payment);
		}

		// Map<String, String> response = new HashMap<>();
		// response.put("status", "success");
		return response;
	}

}
