package warehouse.ch.bbv.efstathiosdimitriadis.rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.service.ProductService;

public class ProductServiceTest {
	/*
	 * The purpose of this testing class is not to have correct or well designed tests
	 * but to get familiar with the TDD approach and the JUnit API
	 */
	ProductService productService;
	
	@BeforeEach
	void setup() {
		productService = new ProductService();
	}
	
	
	@Test
	void getProductByNameReturnsCorrectProduct() {
		// we know that there is a tire Product in our dummy service
		// this is data duplication of course so later we will change this 
		String name = "tire";
		List<Product> products = productService.getByName(name);
		
		assertEquals(name, products.get(0).getName());
	}
	
	@Test
	void getProductByNameReturnsEmptyListIfNotExist() {
		String name = "Excalibur";
		List<Product> products = productService.getByName(name);
		
		assertEquals(0, products.size());
	}
	
	@Test
	void getProductByIdReturnsCorrectProduct(TestReporter reporter) {
//		 To get a product by it's id we must know it's id beforehand 
//		since UUIDs are globally unique
		Product namedProduct = productService.getByName("tire").get(0);
		Optional<Product> productOptional = productService.getById(namedProduct.getId());
		assertTrue(productOptional.isPresent());
		Product product = productOptional.get();
		assertEquals(Product.class, product.getClass());
		assertEquals(namedProduct.getId(), product.getId());
	}
	
	@Test
	void getProductByNotExistingIdReturnsEmptyOptional() {
		String randomUUID = UUID.randomUUID().toString();
		Optional<Product> product = productService.getById(randomUUID);
		assertFalse(product.isPresent());
	}
	
	@Test
	void getProductByInvalidIdReturnsEmptyOptional() {
		Optional<Product> product = productService.getById("notUUID");
		assertFalse(product.isPresent());
	}
	
	@Test
	void getAllProducts() {
		List<Product> products = productService.getAll();
		if (products == null)
			fail();
	}
	
	@Test
	void createProductReturnsTheProduct() {
		Product product = new Product("Pigma Micron 03", "pen");
		Optional<Product> createdProduct = productService.add(product);
		
		assertTrue(createdProduct.isPresent());
		assertEquals(product, createdProduct.get());
	}
	
	@Test
	void createProductStoresProducts() {
		Product product = new Product("Pigma Micron 03", "pen");
		productService.add(product);
		
		Optional<Product> storedProduct = productService.getById(product.getId());
		assertTrue(storedProduct.isPresent());
		assertEquals(product, storedProduct.get());
	}
	
	@Test
	void createNullProductReturnsEmptyOptional() {
		Optional<Product> createdProduct = productService.add(null);
		assertFalse(createdProduct.isPresent());
	}
}
