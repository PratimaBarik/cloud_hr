package com.app.employeePortal.api.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.api.dto.PaymentDTO;
import com.app.employeePortal.api.dto.StripePaymentViewDTO;
import com.app.employeePortal.api.entity.Payment;
import com.app.employeePortal.api.repository.StripePaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class StripeServiceImpl implements StripeService {
	@Autowired
	StripePaymentRepository paymentRepository;

	@Override
	public StripePaymentViewDTO connectToStripe(PaymentDTO paymentDTO) throws JSONException {

		PaymentIntent paymentIntent = null;
		String client_secret = null;
		String paymentId = null;

		// Stripe.apiKey =
		// "sk_test_51LAhLPKXR87JGQ2mZ3lnFLXmvB4s8cUUbt1YQx4s41sUyBpP4reLg6jZ6w9MxnavqreE1AGUEM7pQbGubmVPEnuf00zZXEWHtg";//FOR
		// UAT
		Stripe.apiKey = "sk_test_51LRswhSFbuOpicuJ3F8q6G1nrPfecPZqWyoP2ZPEAkXjiwmaSXymkqg2j3SsMK0nMOOURK7UvRWeRvYp21KMjy7200Cd0l13lU";// FOR
																																		// PROD

		// Long cartId=Long.parseLong(paymentDTO.getCartId());
		// Long orderAmount = calculateOrderAmount(cartId);

		try {
			PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
					.setAmount(paymentDTO.getAmount().longValue()).setCurrency(paymentDTO.getCurrency())
					.setAutomaticPaymentMethods(
							PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
					.build();

			paymentIntent = PaymentIntent.create(params);

			JSONObject object = new JSONObject(paymentIntent.toJson());
			client_secret = object.getString("client_secret");
			paymentId = object.getString("id");
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// paymentId save in cart table against cartid

//		  Cart cart=CartRepository
//		  Cart cart=cartRepository.findById(Long.parseLong(paymentDTO.getCartId())).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Cart not found with id :"+paymentDTO.getCartId()));
//		 System.out.println("after geting cart id from payment DTO"+cart);
		Payment payment = Payment.builder()
//		          .cart(cart)
				.amount(paymentDTO.getAmount()).paymentType("Stripe").StripePaymentId(paymentId)
				.StripePaymentInd(paymentDTO.isStripePaymentInd())
				// .paymentId(paymentId)
				.build();
		paymentRepository.save(payment);

		StripePaymentViewDTO responsePayment = new StripePaymentViewDTO();
//		responsePayment.setCartId(Long.parseLong(paymentDTO.getCartId()));
		responsePayment.setClientSecret(client_secret);
		responsePayment.setStripePaymentId(paymentId);
		// set currency, amount
		responsePayment.setCurrency(paymentDTO.getCurrency());
		responsePayment.setAmount(paymentDTO.getAmount());
		if (payment != null) {
			responsePayment.setPaymentId(payment.getPaymentId());
		}

		return responsePayment;
	}

	@Override
	public StripePaymentViewDTO confirmPaymentFromStripe(PaymentDTO paymentDTO) {
		Stripe.apiKey = "sk_test_51LRswhSFbuOpicuJ3F8q6G1nrPfecPZqWyoP2ZPEAkXjiwmaSXymkqg2j3SsMK0nMOOURK7UvRWeRvYp21KMjy7200Cd0l13lU";// FOR
																																		// UAT
		// Stripe.apiKey
		// ="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";//FOR
		// PROD

//			    Cart cart=cartRepository.findById(Long.parseLong(paymentDTO.getCartId())).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Cart not found with id :"+paymentDTO.getCartId()));
		// Payment payment=paymentRepository.findByCart(cart);

		StripePaymentViewDTO response = new StripePaymentViewDTO();
		if (paymentDTO.isStripePaymentInd() == true) {
//					 StoreCartResponse storeCartResponse= checkoutOperationService
//								.submitCheckoutForStripe(paymentDTO.getCartId().toString());
			response.setStatus(paymentDTO.getStatus());
//						response.setStorecartResponse(storeCartResponse);

			Payment payment = paymentRepository.findByPaymentId(paymentDTO.getPaymentId());
			payment.setStripePaymentInd(paymentDTO.isStripePaymentInd());
			paymentRepository.save(payment);

			return response;
		} else {

			// Optional<MerchantDetails> merchantDetails
			// =merchantDetailsRepository.findById(cart.getMerchantDetails().getId());
			// System.out.println("merchant details for stripe"+merchantDetails);

			StripePaymentViewDTO stripePaymentViewDTO = new StripePaymentViewDTO();
			stripePaymentViewDTO.setMessage("status failed");
			// stripePaymentViewDTO.setName(merchantDetails.get().getName());
			return stripePaymentViewDTO;

		}

	}

	@Override
	public StripePaymentViewDTO confirmPayment(PaymentDTO paymentDTO) {
		Stripe.apiKey = "sk_test_51LRswhSFbuOpicuJ3F8q6G1nrPfecPZqWyoP2ZPEAkXjiwmaSXymkqg2j3SsMK0nMOOURK7UvRWeRvYp21KMjy7200Cd0l13lU";// FOR
		// UAT
// Stripe.apiKey
// ="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";//FOR
// PROD

//Cart cart=cartRepository.findById(Long.parseLong(paymentDTO.getCartId())).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Cart not found with id :"+paymentDTO.getCartId()));
// Payment payment=paymentRepository.findByCart(cart);

		StripePaymentViewDTO response = new StripePaymentViewDTO();
		if (paymentDTO.isStripePaymentInd() == true) {
//StoreCartResponse storeCartResponse= checkoutOperationService
//.submitCheckoutForStripe(paymentDTO.getCartId().toString());
			response.setStatus(paymentDTO.getStatus());
//response.setStorecartResponse(storeCartResponse);

			Payment payment = paymentRepository.findByPaymentId(paymentDTO.getPaymentId());
			payment.setStripePaymentInd(paymentDTO.isStripePaymentInd());
			paymentRepository.save(payment);

			return response;
		} else {

// Optional<MerchantDetails> merchantDetails
// =merchantDetailsRepository.findById(cart.getMerchantDetails().getId());
// System.out.println("merchant details for stripe"+merchantDetails);

			StripePaymentViewDTO stripePaymentViewDTO = new StripePaymentViewDTO();
			stripePaymentViewDTO.setMessage("status failed");
// stripePaymentViewDTO.setName(merchantDetails.get().getName());
			return stripePaymentViewDTO;

		}

	}

}
