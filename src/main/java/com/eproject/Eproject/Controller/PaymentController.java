package com.eproject.Eproject.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.eproject.Eproject.entity.Payment;
import com.eproject.Eproject.service.PaymentService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
	
	

	@Value("${stripe.webhook-secret}")
    private String endpointSecret;
	
	private final PaymentService paymentService;
	

	
	
	
	@PostMapping("/stripe/{orderId}")
	
	public ResponseEntity<String> createStripeSession(@PathVariable Long orderId)throws Exception{
		
		
		String url=paymentService.createStripeSession(orderId);
		
		
		return ResponseEntity.ok(url);
		
		
	}
	
	
	
	@PostMapping("/webhook")
	public ResponseEntity<String> handleWebhook(@RequestBody String payload,@RequestHeader("Stripe-Signature") String sigHeader){
			
		
		Event event;
		
		
		try {
			
		  event=Webhook.constructEvent(payload, sigHeader, endpointSecret);
		 
		}
		catch(Exception e) {
	
			return ResponseEntity.badRequest().body("Invalid signature");	
		}
	//	System.out.println("In sign of webhook");
		System.out.println("Webhook received: " + event.getType());
		
		
		try {
			
		
			if("checkout.session.completed".equals(event.getType())) {
			/*	System.out.println("webhook checkout session completed");
				
				Session completedSession=(Session) event.getDataObjectDeserializer().getObject().orElse(null);
				
				 if (completedSession == null || completedSession.getMetadata() == null) {
                     return ResponseEntity.ok("No metadata found");
                 }
				 System.out.println("webhook after checkout session completed");
				
				String orderIdCompleted=completedSession.getMetadata().get("orderId");
				
				 System.out.println("webhook after after checkout session completed");
				
				if(orderIdCompleted!=null) {
				
				paymentService.markPaymentSuccess(Long.parseLong(orderIdCompleted),completedSession.getPaymentIntent());
				}
				 System.out.println("webhook after after  aftercheckout session completed");
				
				*/
			
			
				 JsonObject json = JsonParser.parseString(payload).getAsJsonObject();
	                String sessionId = json
	                        .getAsJsonObject("data")
	                        .getAsJsonObject("object")
	                        .get("id")
	                        .getAsString();

	              //  System.out.println("Session ID: " + sessionId);

	                Session session = Session.retrieve(sessionId);

	                String orderId = session.getMetadata().get("orderId");

	                if (orderId != null) {

	                   System.out.println("Updating order: " + orderId);

	                    paymentService.markPaymentSuccess(
	                            Long.parseLong(orderId),
	                            session.getPaymentIntent()
	                    );
	                }
		                
			}
			
			else if("checkout.session.expired".equals(event.getType())) {
				
				
				JsonObject json=JsonParser.parseString(payload).getAsJsonObject();
				
			     String sessionId=json.getAsJsonObject("data").getAsJsonObject("object").get("id").getAsString();
			     
			     
			     Session session=Session.retrieve(sessionId);
			     
			     
				    String orderId=session.getMetadata().get("orderId");
				    
				    if (orderId!= null) {
				    
				    paymentService.markPaymentExpired(Long.parseLong(orderId));
		
				   
				    }
			
			}
			
			else if("payment_intent.payment_failed".equals(event.getType())) {
				
			JsonObject json=JsonParser.parseString(payload).getAsJsonObject();
			
			
			  JsonObject obj=json.getAsJsonObject("data").getAsJsonObject("object");
			  
			  String orderId=obj.getAsJsonObject("metadata").get("orderId").getAsString();
			  
	
			    
			     if (orderId!= null) {
			     paymentService.markPaymentFailed(Long.parseLong(orderId));
			     
			     }

				

				
			}
			
			return ResponseEntity.ok("Webhook Handled");
	
			
		}

      catch(Exception e) {
    	  
    	   return ResponseEntity.ok("Webhook Error");
      }
		
		
		
		

		
		
		
		
		
	}
	
	
	
	
	

	
	
	
	
	

}
