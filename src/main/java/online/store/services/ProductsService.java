package online.store.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import online.store.model.Product;
import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;

/**
 * @author Michael Pogrebinsky - www.topdeveloperacademy.com Integrates with the
 *         database API and handles products and categories business logic
 */
@Service
public class ProductsService {
	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;

	public ProductsService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
		this.productRepository = productRepository;
		this.productCategoryRepository = productCategoryRepository;
	}

	// load all the categories from the dataBase
	public List<String> getAllSupportedCategories() {
		return productCategoryRepository.findAll().stream().map(productCategory -> productCategory.getCategory())
				.collect(Collectors.toList());
	}

	// Show deals of the day, HomePage
	public List<Product> getDealsOfTheDay(int atMostNumberOfProducts) {
		return productRepository.findAtMostNumberOfProducts(atMostNumberOfProducts);
	}

	// Show Products for each Category on HomePage
	public List<Product> getProductsByCategory(String productCategory) {
		return productRepository.findByCategory(productCategory);
	}

	// Load all the Products from the Products table
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	
	//returns a product object given its product id
	public Product getProductById(long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException(String.format("Product with id %s doesn't exist", id)));
	}
}
