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


import ch.bbv.efstathiosdimitriadis.rest.database.ProductsDatabase;
import ch.bbv.efstathiosdimitriadis.rest.model.ProductCategory;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;

public class ProductsDatabaseTest {
	ProductsDatabase database;
	
	@BeforeEach
	public void setup() {
		database = new ProductsDatabase();
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
	void getProductByIdReturnsCorrectProduct() {
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
		Product product = new Product("Pigma Micron 03", ProductCategory.STATIONERY);
		Optional<Product> createdProduct = database.add(product);

		assertTrue(createdProduct.isPresent());
		assertEquals(product, createdProduct.get());
	}

	@Test
	void createProductStoresProducts() {
		Product product = new Product("Pigma Micron 03", ProductCategory.STATIONERY);
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
		Product productForRemoval = new Product("Pigma Micron 03", ProductCategory.STATIONERY);
		database.add(productForRemoval);

		Optional<Product> removedProduct = database.remove(productForRemoval.getId());
		assertEquals(productForRemoval, removedProduct.get());
	}

	@Test
	void removeProductReturnsEmptyOptionalIfNotFound() {
		Product productForRemoval = new Product("Pigma Micron 08", ProductCategory.STATIONERY);
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
		Product product = new Product("Ferrari LaFerrari", ProductCategory.CAR);
		database.add(product);
//		Right now we dont have any fields than can be modified in Product
//		We consider that there can be products with the same name in different categories
//		we should either make the Category field a List (maybe an enum of predefined categories)
//		or add another field to make the update action meaningful 
//		The best solution is the first as Product-Category is a Many-to-Many relationship
//		Here we must also take into consideration the operation of retrieving all items of a category
//		To do this we need to JOIN the products with the Categories field
 	}
}
