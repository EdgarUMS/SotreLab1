package online.store.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import online.store.exceptions.CreditCardValidationException;
import online.store.model.CheckoutRequest;
import online.store.model.Order;
import online.store.services.CreditCardValidationService;
import online.store.services.OrdersService;
import online.store.services.ProductsService;

@RestController
public class CheckoutController {
	
	private final OrdersService ordersService;
	private final ProductsService productsService;
	private final CreditCardValidationService creditCardValidationService;
	
	public CheckoutController(OrdersService ordersService, ProductsService productsService, CreditCardValidationService creditCardValidationService) {
		this.ordersService = ordersService;
		this.productsService = productsService;
		this.creditCardValidationService = creditCardValidationService;
	}
	
	
	//saves the orders in the database
	@PostMapping("/checkout")
	public ResponseEntity<String> checkout(@RequestBody CheckoutRequest checkoutRequest) {
		
		Set<Order> orders = new HashSet<>(checkoutRequest.getProducts().size());
		
		if(isNUllOrBlank(checkoutRequest.getFirstName())) {
			return new ResponseEntity<>("First name is missing",HttpStatus.BAD_REQUEST);
		}
		
		if(isNUllOrBlank(checkoutRequest.getLastName())) {
			return new ResponseEntity<>("Last name is missing",HttpStatus.BAD_REQUEST);
		}
		
		if(isNUllOrBlank(checkoutRequest.getCreditCard())) {
			return new ResponseEntity<>("First name is missing",HttpStatus.PAYMENT_REQUIRED);
		}
		
		creditCardValidationService.validate(checkoutRequest.getCreditCard());
		
		for (CheckoutRequest.ProductInfo productInfo : checkoutRequest.getProducts()) {
			Order order = new Order(
					checkoutRequest.getFirstName(),
					checkoutRequest.getLastName(),
					checkoutRequest.getEmail(),
					checkoutRequest.getShippingAddress(),
					productInfo.getQuantity(),
					productsService.getProductById(productInfo.getProductId()),
					checkoutRequest.getCreditCard() );
			
			orders.add(order);
		}
		
		ordersService.placeOrders(orders);
		return new ResponseEntity<>("success",HttpStatus.OK);
		
	}
	
	//validation
	private static boolean isNUllOrBlank(String input) {
		return input == null || input.isEmpty()|| input.trim().length() == 0;
	}
	
	//catches all credit card validation Exception
	@ExceptionHandler({CreditCardValidationException.class})
	public  ResponseEntity<String> handleCreditCardError(Exception ex){
		System.out.println(String.format("Request to /checkout path threw an exception %s", ex.getMessage()));
		
		return new ResponseEntity<>("Credit card is invald, please use another form of payment", HttpStatus.BAD_REQUEST);
	}
	
	
	

}
