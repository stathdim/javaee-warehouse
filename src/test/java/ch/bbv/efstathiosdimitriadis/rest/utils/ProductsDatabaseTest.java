package ch.bbv.efstathiosdimitriadis.rest.utils;

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

import ch.bbv.efstathiosdimitriadis.rest.model.ProductCategory;
import ch.bbv.efstathiosdimitriadis.rest.resource.beans.ProductFilterBean;
import ch.bbv.efstathiosdimitriadis.rest.dao.ProductDAO;
import ch.bbv.efstathiosdimitriadis.rest.dao.ProductInMemoryDAO;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;

public class ProductsDatabaseTest {
	ProductDAO database;

	@BeforeEach
	public void setup() {
		database = new ProductInMemoryDAO();
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
		Product expectedProduct = new Product("Pigma Micron 03", ProductCategory.STATIONERY, 2017);
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
	void getAllProductsHasFilterParameter() {
		ProductFilterBean filterBean = new ProductFilterBean();
		List<Product> products = database.getAll(filterBean);
	}
	
	@Test
	void getAllProducts() {
		ProductFilterBean filterBean = new ProductFilterBean();
		List<Product> products = database.getAll(filterBean);
		if (products == null)
			fail();
	}
	
	@Test
	void getAllProductsFiltersByCategory() {
		Product product = new Product("Pigma Micron 07 Black", ProductCategory.STATIONERY, 1411);
		database.add(product);
		
		ProductFilterBean filterBean = new ProductFilterBean();
		filterBean.setCategory(ProductCategory.STATIONERY.toString());
		List<Product> products = database.getAll(filterBean);
		if (products == null || products.size() == 0)
			fail();
		assertEquals(products.get(0).getCategory(), ProductCategory.STATIONERY);
	}
	
	@Test
	void getAllProductsFiltersByYear() {
		Product product = new Product("Pigma Micron 07 Black", ProductCategory.STATIONERY, 1987);
		database.add(product);
		product = new Product("Pigma Micron 07 Red", ProductCategory.STATIONERY, 1987);
		database.add(product);
		ProductFilterBean filterBean = new ProductFilterBean();
		filterBean.setYear(1987);
		List<Product> products = database.getAll(filterBean);
		if (products == null || products.size() == 0)
			fail();
		for (Product p: products) 
			assertEquals(p.getYear(), 1987);
	}
	
	@Test
	void GetAllProductsFiltersByYearAndCategory() {
		Product product = new Product("Pigma Micron 07 Black", ProductCategory.STATIONERY, 1987);
		database.add(product);
		product = new Product("Pigma Micron 09 Black", ProductCategory.STATIONERY, 2012);
		database.add(product);
		product = new Product("Spinach", ProductCategory.VEGETABLE, 1987);
		database.add(product);
		
		ProductFilterBean filterBean = new ProductFilterBean();
		filterBean.setYear(1987);
		filterBean.setCategory(ProductCategory.STATIONERY.toString());
		
		List<Product> products = database.getAll(filterBean);
		
		if (products == null || products.size() == 0)
			fail();
		for (Product p: products) {
			assertEquals(ProductCategory.STATIONERY, p.getCategory());
			assertEquals(1987, p.getYear());
		}
	}

	@Test
	void createProductReturnsTheProduct() {
		Product product = new Product("Pigma Micron 03", ProductCategory.STATIONERY, 1999);
		Optional<Product> createdProduct = database.add(product);

		assertTrue(createdProduct.isPresent());
		assertEquals(product, createdProduct.get());
	}

	@Test
	void createProductStoresProducts() {
		Product product = new Product("Pigma Micron 03", ProductCategory.STATIONERY, 1923);
		database.add(product);

		Optional<Product> storedProduct = database.getById(product.getId());
		assertTrue(storedProduct.isPresent());
		assertEquals(product, storedProduct.get());
	}

	@Test
	void createProductFailsIfNameExists() {
		Product product = new Product("Pigma Micron 03", ProductCategory.CLOTHES, 1742);
		database.add(product);
		product = new Product("Pigma Micron 03", ProductCategory.STATIONERY, 1654);
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
		Product productForRemoval = new Product("Pigma Micron 03", ProductCategory.STATIONERY, 4510);
		database.add(productForRemoval);

		Optional<Product> removedProduct = database.remove(productForRemoval.getId());
		assertEquals(productForRemoval, removedProduct.get());
	}

	@Test
	void removeProductReturnsEmptyOptionalIfNotFound() {
		Product productForRemoval = new Product("Pigma Micron 08", ProductCategory.STATIONERY, 2000);
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
		Product originalProduct = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY, 2018);
		database.add(originalProduct);
		Product updatedProduct = originalProduct.modifyCategory(ProductCategory.CAR);

		Optional<Product> returnedProduct = database.update(originalProduct.getId(), updatedProduct);

		assertTrue(returnedProduct.isPresent());
		assertEquals(updatedProduct, returnedProduct.get());
	}

	@Test
	void updateProductUpdatesTheProduct() {
		Product originalProduct = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY, 2019);
		database.add(originalProduct);
		Product updatedProduct = originalProduct.modifyCategory(ProductCategory.CAR);

		database.update(originalProduct.getId(), updatedProduct);

		Optional<Product> retrievedFromDb = database.getByName(updatedProduct.getName());

		assertTrue(retrievedFromDb.isPresent());
		assertEquals(updatedProduct, retrievedFromDb.get());
	}

	@Test
	void updateProductReturnsEmptyOptionalIfNullUpdate() {
		Product originalProduct = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY, 2017);
		database.add(originalProduct);

		Optional<Product> updated = database.update(originalProduct.getId(), null);
		assertFalse(updated.isPresent());
	}
	
	@Test
	void updateProductLeavesProductUnchangedIfNullUpdate() {
		Product originalProduct = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY, 2017);
		database.add(originalProduct);

		database.update(originalProduct.getId(), null);
		
		Optional<Product> retrievedFromDb = database.getByName(originalProduct.getName());
		assertTrue(retrievedFromDb.isPresent());
		assertEquals(originalProduct, retrievedFromDb.get());
	}
	
	@Test
	void updateProductReturnsEmptyOptionalIfIdNotExists() {
		Product originalProduct = new Product("Ferrari LaFerrari", ProductCategory.CAR_ACCESSORY, 2017);
		database.add(originalProduct);
		Product updatedProduct = originalProduct.modifyCategory(ProductCategory.CAR);

		Optional<Product> updated = database.update(UUID.randomUUID().toString(), updatedProduct);
		assertFalse(updated.isPresent());
	}
}
