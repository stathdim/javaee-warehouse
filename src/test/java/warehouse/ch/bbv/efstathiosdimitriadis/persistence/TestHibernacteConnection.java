package warehouse.ch.bbv.efstathiosdimitriadis.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.model.ProductCategory;

public class TestHibernacteConnection {

	@Test
	void testConnection() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("warehouse-persistence");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	@Test
	void testPersistence() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("warehouse-persistence");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.getTransaction().commit();
		Product[] stored = {new Product("Kia Picanto", ProductCategory.CAR, 2019), new Product("Uni V7 Red", ProductCategory.STATIONERY, 2018)};
		em.persist(stored[0]);
		em.persist(stored[1]);
		Product retrieved = em.find(Product.class, stored[0].getId());
		
		assertEquals(stored[0], retrieved);
		
		em.close();
		emf.close();
	}
	
	
}
