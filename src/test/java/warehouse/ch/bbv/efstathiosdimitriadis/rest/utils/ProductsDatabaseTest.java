package warehouse.ch.bbv.efstathiosdimitriadis.rest.utils;

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

import ch.bbv.efstathiosdimitriadis.rest.database.ProductsDatabase;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;

public class ProductsDatabaseTest {
	ProductsDatabase database;
	
	@BeforeEach
	public void setup() {
		database = new ProductsDatabase();
	}
	
	@Test 
	void temp(TestReporter testReporter) {
		List<Product> products = database.getAll();
		testReporter.publishEntry(products.size() + "");
		products.stream()
		.map(p -> p.toString())
		.forEach(testReporter::publishEntry);
	}

	@Test
	void getProductByNameReturnsCorrectProduct() {
		// we know that there is a tire Product in our dummy service
		// this is data duplication of course so later we will change this
		String name = "tire";
		List<Product> products = database.getByName(name);
		assertEquals(name, products.get(0).getName());
	}

	@Test
	void getProductByNameReturnsEmptyListIfNotExist() {
		String name = "Excalibur";
		List<Product> products = database.getByName(name);
		assertEquals(0, products.size());
	}

	@Test
	void getProductByIdReturnsCorrectProduct(TestReporter reporter) {
//		 To get a product by it's id we must know it's id beforehand 
//		since UUIDs are globally unique
		Product namedProduct = database.getByName("tire").get(0);
		Optional<Product> productOptional = database.getById(namedProduct.getId());
		assertTrue(productOptional.isPresent());
		Product product = productOptional.get();
		assertEquals(Product.class, product.getClass());
		assertEquals(namedProduct.getId(), product.getId());
	}

	@Test
	void getProductByNotExistingIdReturnsEmptyOptional() {
		String randomUUID = UUID.randomUUID().toString();
		Optional<Product> product = database.getById(randomUUID);
		assertFalse(product.isPresent());
	}

	@Test
	void getProductByInvalidIdReturnsEmptyOptional() {
		Optional<Product> product = database.getById("notUUID");
		assertFalse(product.isPresent());
	}

	@Test
	void getAllProducts() {
		List<Product> products = database.getAll();
		if (products == null)
			fail();
	}

	@Test
	void createProductReturnsTheProduct() {
		Product product = new Product("Pigma Micron 03", "pen");
		Optional<Product> createdProduct = database.add(product);

		assertTrue(createdProduct.isPresent());
		assertEquals(product, createdProduct.get());
	}

	@Test
	void createProductStoresProducts() {
		Product product = new Product("Pigma Micron 03", "pen");
		database.add(product);

		Optional<Product> storedProduct = database.getById(product.getId());
		assertTrue(storedProduct.isPresent());
		assertEquals(product, storedProduct.get());
	}

	@Test
	void createNullProductReturnsEmptyOptional() {
		Optional<Product> createdProduct = database.add(null);
		assertFalse(createdProduct.isPresent());
	}

	@Test
	void removeProductReturnsDeletedCopy() {
		Product productForRemoval = new Product("Pigma Micron 03", "pen");
		database.add(productForRemoval);

		Optional<Product> removedProduct = database.remove(productForRemoval.getId());
		assertEquals(productForRemoval, removedProduct.get());
	}

	@Test
	void removeProductReturnsEmptyOptionalIfNotFound() {
		Product productForRemoval = new Product("Pigma Micron 08", "pen");
		Optional<Product> removedProduct = database.remove(productForRemoval.getId());
		assertFalse(removedProduct.isPresent());
	}
	
	@Test
	void removeProductReturnsEmptyOptionalIfNull() {
		Optional<Product> removedProduct = database.remove(null);
		assertFalse(removedProduct.isPresent());
	}
	
	@Test
	void updateProductUpdatesTheProduct() {
		Product product = new Product("Ferrari LaFerrari", "car");
		database.add(product);
		
	}
}
