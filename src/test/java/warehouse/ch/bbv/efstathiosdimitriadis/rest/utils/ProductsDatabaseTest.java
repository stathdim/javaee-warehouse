package warehouse.ch.bbv.efstathiosdimitriadis.rest.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
		Optional<Product> product = database.getByName(name);
		assertEquals(name, product.get().getName());
	}

	@Test
	void getProductByNameReturnsEmptyOptionalIfNotExist() {
		String name = "Excalibur";
		Optional<Product> product = database.getByName(name);
		assertFalse(product.isPresent());
	}

	@Test
	void getProductByIdReturnsCorrectProduct() {
//		 To get a product by it's id we must know it's id beforehand 
//		since UUIDs are globally unique
		Product expectedProduct = new Product("Pigma Micron 03", ProductCategory.STATIONERY);
		database.add(expectedProduct);
		
		Optional<Product> productOptional = database.getById(expectedProduct.getId());
		assertTrue(productOptional.isPresent());
		Product product = productOptional.get();
		assertEquals(Product.class, product.getClass());
		assertEquals(expectedProduct.getId(), product.getId());
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
	void createProductFailsIfNameExists() {
		Product product = new Product("Pigma Micron 03", ProductCategory.CLOTHES);
		database.add(product);
		product = new Product("Pigma Micron 03", ProductCategory.STATIONERY);
		Optional<Product> created = database.add(product);
		assertFalse(created.isPresent());
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
	
	@Test @Disabled
	void updateProductUpdatesTheProduct() {
		Product product = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY);
		database.add(product);
//		Right now we dont have any fields than can be modified in Product
//		We consider that there can be products with the same name in different categories
//		we should either make the Category field a List (maybe an enum of predefined categories)
//		or add another field to make the update action meaningful 
//		The best solution is the first as Product-Category is a Many-to-Many relationship
//		Here we must also take into consideration the operation of retrieving all items of a category
//		To do this we need to JOIN the products with the Categories field
		Product updatedProduct = product.modifyCategory(ProductCategory.CAR);
		
//		database.update(product, updatedProduct);
		
		Optional<Product> retrievedFromDb = database.getByName(updatedProduct.getName());
		
		assertTrue(retrievedFromDb.isPresent());
		assertEquals(updatedProduct, retrievedFromDb.get());
 	}
}
