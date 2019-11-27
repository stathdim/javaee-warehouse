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

	@Test
	void updateProductReturnsUpdatedOptionalProduct() {
		Product originalProduct = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY);
		database.add(originalProduct);
		Product updatedProduct = originalProduct.modifyCategory(ProductCategory.CAR);

		Optional<Product> returnedProduct = database.update(originalProduct.getId(), updatedProduct);

		assertTrue(returnedProduct.isPresent());
		assertEquals(updatedProduct, returnedProduct.get());
	}

	@Test
	void updateProductUpdatesTheProduct() {
		Product originalProduct = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY);
		database.add(originalProduct);
		Product updatedProduct = originalProduct.modifyCategory(ProductCategory.CAR);

		database.update(originalProduct.getId(), updatedProduct);

		Optional<Product> retrievedFromDb = database.getByName(updatedProduct.getName());

		assertTrue(retrievedFromDb.isPresent());
		assertEquals(updatedProduct, retrievedFromDb.get());
	}

	@Test
	void updateProductReturnsEmptyOptionalIfNullUpdate() {
		Product originalProduct = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY);
		database.add(originalProduct);

		Optional<Product> updated = database.update(originalProduct.getId(), null);
		assertFalse(updated.isPresent());
	}
	
	@Test
	void updateProductLeavesProductUnchangedIfNullUpdate() {
		Product originalProduct = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY);
		database.add(originalProduct);

		database.update(originalProduct.getId(), null);
		
		Optional<Product> retrievedFromDb = database.getByName(originalProduct.getName());
		assertTrue(retrievedFromDb.isPresent());
		assertEquals(originalProduct, retrievedFromDb.get());
	}
	
	@Test
	void updateProductReturnsEmptyOptionalIfIdNotExists() {
		Product originalProduct = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY);
		database.add(originalProduct);
		Product updatedProduct = originalProduct.modifyCategory(ProductCategory.CAR);

		Optional<Product> updated = database.update(UUID.randomUUID().toString(), updatedProduct);
		assertFalse(updated.isPresent());
	}
}
