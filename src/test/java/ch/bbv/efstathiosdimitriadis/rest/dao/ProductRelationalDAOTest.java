package ch.bbv.efstathiosdimitriadis.rest.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.model.ProductCategory;
import ch.bbv.efstathiosdimitriadis.rest.resource.beans.ProductFilterBean;

class ProductRelationalDAOTest {
	static private ProductDAO productDAO;
	static private EntityManager em;
	private Product testingProduct;

	@BeforeEach
	void setup() {
		productDAO = new ProductRelationalDAO();
		em = Persistence.createEntityManagerFactory("warehouse-persistence").createEntityManager();
		testingProduct = new Product("Coffee Mug", ProductCategory.GLASSWARE, 2014);
		persistTestingProduct(testingProduct);
	}

	@Test
	void addProductWorks() {
		Product product = new Product("Fiat Punto", ProductCategory.CAR, 1999);
		productDAO.add(product);

		Product retrieved = em.find(Product.class, product.getId());

		assertEquals(product, retrieved);

		deleteTestingProduct(retrieved);
	}

	@Test
	void getProductByIdReturnsOptionalProduct() {
		Optional<Product> retrieved = productDAO.getById(testingProduct.getId());
		assertTrue(retrieved.isPresent());
	}

	@Test
	void getProductByIdReturnsCorrectProduct() {
		Optional<Product> retrieved = productDAO.getById(testingProduct.getId());
		assertEquals(testingProduct, retrieved.get());
	}

	@Test
	void getProductByIdReturnsEmptyOptionalIfProductNotFound() {
		Optional<Product> retrieved = productDAO.getById(UUID.randomUUID().toString());
		assertFalse(retrieved.isPresent());
	}

	@Test
	void getAllProductsWorks() {
		List<Product> retrieved = productDAO.getAll(new ProductFilterBean());
		assertTrue(retrieved.size() > 0);
	}

	@Test
	void getAllReturnsEmptyListIfNoProducts() {
		deleteTestingProduct(testingProduct);
		List<Product> retrieved = productDAO.getAll(new ProductFilterBean());
		assertEquals(0, retrieved.size());
	}

	@Test
	void getAllSupportsFilterByCategory() {
		Product filteredOne = new Product("1", ProductCategory.CLOTHES, 1000);
		Product filteredTwo = new Product("2", ProductCategory.FOOD, 2000);
		persistTestingProduct(filteredOne);
		persistTestingProduct(filteredTwo);
		
		ProductFilterBean filter = new ProductFilterBean();
		filter.setCategory(ProductCategory.GLASSWARE.toString());
		
		List<Product> retrieved = productDAO.getAll(filter);
		assertEquals(1, retrieved.size());
		deleteTestingProduct(filteredOne);
		deleteTestingProduct(filteredTwo);
	}
	
	@Test
	void getAllSupportsFilterByYear() {
		Product filteredOne = new Product("1", ProductCategory.CLOTHES, 1000);
		Product filteredTwo = new Product("2", ProductCategory.FOOD, 2000);
		Product filteredThree = new Product("3", ProductCategory.TABLET, 1000);
		persistTestingProduct(filteredOne);
		persistTestingProduct(filteredTwo);
		persistTestingProduct(filteredThree);
		
		ProductFilterBean filter = new ProductFilterBean();
		filter.setYear(1000);
		
		List<Product> retrieved = productDAO.getAll(filter);
		assertEquals(2, retrieved.size());
		deleteTestingProduct(filteredOne);
		deleteTestingProduct(filteredTwo);
		deleteTestingProduct(filteredThree);
	}
	
	@Test
	void getAllSupportsFilterByYearAndCategory() {
		Product filteredOne = new Product("1", ProductCategory.CLOTHES, 1000);
		Product filteredTwo = new Product("2", ProductCategory.TABLET, 2000);
		Product filteredThree = new Product("3", ProductCategory.TABLET, 1000);
		Product filteredFour= new Product("4", ProductCategory.TABLET, 1000);
		persistTestingProduct(filteredOne);
		persistTestingProduct(filteredTwo);
		persistTestingProduct(filteredThree);
		persistTestingProduct(filteredFour);
		
		ProductFilterBean filter = new ProductFilterBean();
		filter.setYear(1000);
		filter.setCategory(ProductCategory.TABLET.toString());
		
		List<Product> retrieved = productDAO.getAll(filter);
		assertEquals(2, retrieved.size());
		deleteTestingProduct(filteredOne);
		deleteTestingProduct(filteredTwo);
		deleteTestingProduct(filteredThree);
		deleteTestingProduct(filteredFour);
	}
	
	@Test
	void getAllReturnsEmptyListIfNothingMatchesFilter(TestReporter reporter) {
		ProductFilterBean filter = new ProductFilterBean();
		filter.setYear(1);
		List<Product> retrieved = productDAO.getAll(filter);
		assertTrue(retrieved.isEmpty());
	}

	@Test
	void removeProductWorks(TestReporter reporter) {
		
		Optional<Product> removed = productDAO.remove(testingProduct.getId());
		assertTrue(removed.isPresent());
		assertEquals(testingProduct.getId(), removed.get().getId());

		em.getTransaction().begin();
		em.flush();
		em.clear();
		Product found = em.find(Product.class, testingProduct.getId());
		em.getTransaction().commit();
		assertTrue(found == null);
	}

	@Test
	void removeProductsReturnEmptyOptionalIfProductNotInDB() {
		Product test = new Product("Simple", ProductCategory.BOOK, 2080);

		Optional<Product> removed = productDAO.remove(test.getId());
		assertFalse(removed.isPresent());
	}

	@Test
	void removeProductsReturnsEmptyOptionalIfProductDeleted() {
		deleteTestingProduct(testingProduct);

		Optional<Product> removedAgain = productDAO.remove(testingProduct.getId());

		assertFalse(removedAgain.isPresent());
	}

	@Test
	void updateProductWorks() {
		Product update = testingProduct.modifyYear(7000);
		Optional<Product> updated = productDAO.update(testingProduct.getId(), update);
		assertTrue(updated.isPresent());
		assertEquals(testingProduct.getCategory(), updated.get().getCategory());
		assertEquals(testingProduct.getName(), updated.get().getName());
		assertNotEquals(testingProduct.getYear(), updated.get().getYear());
		assertEquals(update.getYear(), updated.get().getYear());
	}
	
	@Test
	void updateReturnsEmptyOptionalIfNullPassed() {
		Optional<Product> updated = productDAO.update(testingProduct.getId(), null);
		assertFalse(updated.isPresent());
	}
	
	@Test
	void updateReturnsEmptyOptionalIfProductNotInDB() {
		Product notPersisted = new Product("Simple", ProductCategory.BOOK, 2080);
		Product notPersistedUpdate = notPersisted.modifyCategory(ProductCategory.STATIONERY);
		Optional<Product> updated = productDAO.update(notPersisted.getId(), notPersistedUpdate);
		assertFalse(updated.isPresent());
	}
	
	@Test
	void getByNameWorks() {
		Optional<Product> retrieved = productDAO.getByName(testingProduct.getName());
		
		assertTrue(retrieved.isPresent());
		assertEquals(testingProduct, retrieved.get());
	}
	
	@Test
	void getByNameReturnsEmptyOptionalIfNotFound() {
		String random = UUID.randomUUID().toString();
		Optional<Product> retrieved = productDAO.getByName(random);
		
		assertFalse(retrieved.isPresent());
	}
	
	@Test
	void getByNameReturnsEmptyOptionalIfNullName() {
		Optional<Product> retrieved = productDAO.getByName(null);
		
		assertFalse(retrieved.isPresent());
	}

	@AfterEach
	void teardown() {
		try {
			deleteTestingProduct(testingProduct);
			em.close();
		} catch (Exception e) {
		}
	}
	
	private void persistTestingProduct(Product toPersist) {
		em.getTransaction().begin();
		em.persist(toPersist);
		em.getTransaction().commit();
	}
	
	private void deleteTestingProduct(Product toDelete) {
		em.getTransaction().begin();
		em.remove(toDelete);
		em.getTransaction().commit();
	}
}
