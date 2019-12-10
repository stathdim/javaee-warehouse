package warehouse.ch.bbv.efstathiosdimitriadis.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.model.ProductCategory;

public class TestHibernacteConnection {
	
	@Test 
	void testMsSql() {
		// this is a single use password so there is no danger in hardcoding for now
		// later I will secure the app-db connection
		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=warehouse;user=sa;password=sompa2019!";

        try {
            // Load SQL Server JDBC driver and establish connection.
            System.out.print("Connecting to SQL Server ... ");
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {
                System.out.println("Done.");
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            fail();
        }
	}

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
		
		Product[] stored = { new Product("Kia SPicantos", ProductCategory.CAR, 2019),
				new Product("Uni V7 BReds", ProductCategory.STATIONERY, 2018) };
		em.persist(stored[0]);
		em.persist(stored[1]);
		Product retrieved = em.find(Product.class, stored[0].getId());
		em.getTransaction().commit();
		assertEquals(stored[0], retrieved);
		
		em.close();
		emf.close();
	}

}
