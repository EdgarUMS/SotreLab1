package online.store.services;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import online.store.model.Order;
import online.store.repositories.OrderRepository;

//now is aoutodetectable by Sprig classpath scanning
@Service
public class OrdersService {
	
	private final OrderRepository orderRepository;
	private final long maxNumberOfItems;
	
	public OrdersService(OrderRepository orderRepository, @Value("${products.service.max-number.max-number-of-items:25}") long maxNumberOfItems) {
		this.orderRepository = orderRepository;
		this.maxNumberOfItems = maxNumberOfItems;
	}
	
	//Takes a collection of Orders and saves them in the database, inside the Orders table
	public void placeOrders(Iterable<Order> orders) {
		validateNumberOfItemsOrdered(orders);
		orderRepository.saveAll(orders);
	}
	
	private void validateNumberOfItemsOrdered(Iterable<Order> orders) {
		long totalNumberOfItems = 0;
		for (Order order: orders) {
			totalNumberOfItems += order.getQuantity();
		}
		
		if(totalNumberOfItems > maxNumberOfItems) {
			throw new IllegalStateException(String.format("Number of products %d exceeded the limit of %d", totalNumberOfItems, maxNumberOfItems));
		}
	}

}
