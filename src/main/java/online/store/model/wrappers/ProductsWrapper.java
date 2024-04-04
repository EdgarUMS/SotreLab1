package online.store.model.wrappers;

import java.util.Collections;
import java.util.List;

import online.store.model.Product;

public class ProductsWrapper {
	private List<Product> products = Collections.emptyList();

	public ProductsWrapper(List<Product> products) {
		this.products = Collections.unmodifiableList(products);
	}

	public List<Product> getProducts() {
		return products;
	}

}
