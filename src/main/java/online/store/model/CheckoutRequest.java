package online.store.model;

import java.util.List;

//purchase requests
public class CheckoutRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String shippingAddress;
	private List<ProductInfo> products;
	
	private String creditCard;
	
	public CheckoutRequest(String firstName, String lastName, String email, String shippingAddress,
			List<ProductInfo> products, String creditCard) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.shippingAddress = shippingAddress;
		this.products = products;
		this.creditCard = creditCard;
	}
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public List<ProductInfo> getProducts() {
		return products;
	}

	public void setProducts(List<ProductInfo> products) {
		this.products = products;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	
	
	// ----------------------------------
	
	public static class ProductInfo {
		
		private long productId;
		private long quantity;
		
		
		public ProductInfo(long productId, long quantity) {
			super();
			this.productId = productId;
			this.quantity = quantity;
		}
		
		public long getProductId() {
			return productId;
		}
		public void setProductId(long productId) {
			this.productId = productId;
		}
		public long getQuantity() {
			return quantity;
		}
		public void setQuantity(long quantity) {
			this.quantity = quantity;
		}
	}
}
