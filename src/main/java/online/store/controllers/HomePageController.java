package online.store.controllers;

import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.store.model.wrappers.ProductsWrapper;
import online.store.services.ProductsService;

//class is flagged as a @RestController meaning it is ready for use by Spring MVC to handle web requests
@RestController
public class HomePageController {

	private final ProductsService productService;

	// Dependency inyection by constructor
	public HomePageController(ProductsService productService) {
		this.productService = productService;
	}

	// loads the category names from the ProductService and format it as a
	// comma-separated string
	@GetMapping("/categories")
	public String getProductCategories() {
		return productService.getAllSupportedCategories().stream().collect(Collectors.joining(","));
	}

	@GetMapping("/deals_of_the_day/{number_of_products}")
	public ProductsWrapper getDealsOfTheDay(@PathVariable(name = "number_of_products") int numberOfProducts) {
		return new ProductsWrapper(productService.getDealsOfTheDay(numberOfProducts));
	}

	@GetMapping("/products")
	public ProductsWrapper getProductsFromCategory(@RequestParam(name = "category", required = false) String category) {

		if (category != null && !category.isEmpty()) {
			return new ProductsWrapper(productService.getProductsByCategory(category));

		}
		return new ProductsWrapper(productService.getAllProducts());

	}

}
