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
		em.getTransaction().begin();
		em.persist(testingProduct);
		em.getTransaction().commit();
	}

	@Test
	void addProductWorks() {
		Product product = new Product("Fiat Punto", ProductCategory.CAR, 1999);
		productDAO.add(product);

		Product retrieved = em.find(Product.class, product.getId());

		assertEquals(product, retrieved);

		em.getTransaction().begin();
		em.remove(retrieved);
		em.getTransaction().commit();
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
		em.getTransaction().begin();
		em.remove(testingProduct);
		em.getTransaction().commit();
		List<Product> retrieved = productDAO.getAll(new ProductFilterBean());
		assertEquals(0, retrieved.size());
	}

	@Test
	@Disabled
	void getAllSupportsFiltering() {
		fail("not implemented yet");
	}

	@Test
	void removeProductWorks(TestReporter reporter) {
		Product test = new Product("Fiat Punto", ProductCategory.CAR, 2015);
		em.getTransaction().begin();
		em.persist(test);
		em.getTransaction().commit();

		Optional<Product> removed = productDAO.remove(test.getId());
		assertTrue(removed.isPresent());
		assertEquals(removed.get().getId(), test.getId());
		
		em.getTransaction().begin();
		em.flush();
		em.clear();
		Product found = em.find(Product.class, test.getId());
		em.getTransaction().commit();
		assertTrue(found == null);

	}

	@AfterEach
	void teardown() {
		try {
			em.getTransaction().begin();
			em.remove(testingProduct);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
		}

	}

}
