package warehouse.ch.bbv.efstathiosdimitriadis.rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.service.ProductService;

public class ProductServiceTest {
	ProductService productService;
	
	@BeforeEach
	void setup() {
		productService = new ProductService();
	}
	
	@Test
	void getProductByIdReturnsCorrectProduct() {
		Product product = productService.getById(2);
		assertEquals(Product.class, product.getClass());
		assertEquals(2, product.getId());
	}
	
	@Test
	void getProductByIdReturnsNullIfOutOfBounds() {
		Product product = productService.getById(15); // need to set to out of bounds programmatically
		assertEquals(null, product);
	}
	
	
}
