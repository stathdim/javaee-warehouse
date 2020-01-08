package ch.bbv.efstathiosdimitriadis.rest.dao;

import java.util.List;
import java.util.Optional;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.resource.beans.ProductFilterBean;

@Singleton
@Lock(LockType.WRITE)
@RelationalDb
public class ProductRelationalDAO implements ProductDAO {
	private static Logger logger = Logger.getLogger(ProductDAO.class.getName());

	private final EntityManager entityManager;

	public ProductRelationalDAO() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("warehouse-persistence");
		entityManager = emf.createEntityManager();
	}

	@Override
	public Optional<Product> getById(String id) {
		entityManager.getTransaction().begin();
		Product retrieved = entityManager.find(Product.class, id);
		entityManager.getTransaction().commit();

		if (retrieved == null)
			return Optional.empty();
		return Optional.of(retrieved);

	}

	@Override
	public List<Product> getAll(ProductFilterBean filter) {
		// TODO: Implement filtering
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
		criteria.from(Product.class);
		List<Product> data = entityManager.createQuery(criteria).getResultList();
		return data;
	}

	@Override
	public Optional<Product> getByName(String name) {
		try {
			TypedQuery<Product> query = entityManager.createQuery("SELECT p from Product p WHERE p.name = :name", Product.class)
					.setParameter("name", name);
			Product result = query.getSingleResult();
			return Optional.ofNullable(result);
		} catch (NoResultException e) {
			return Optional.empty();
		}
		
	}

	@Override
	public Optional<Product> add(Product product) {

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(product);
			entityManager.getTransaction().commit();
		} catch (EntityExistsException e) {
			handleExceptionDuringOngoingTransaction(e);
			return Optional.empty();
		}
		return Optional.of(product);
	}

	@Override
	public Optional<Product> remove(String id) {
		try {
			Product forRemoval = entityManager.find(Product.class, id);
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			if (forRemoval == null)
				return Optional.empty();
			entityManager.remove(forRemoval);
			entityManager.flush();
			transaction.commit();
			return Optional.of(forRemoval);
		} catch (IllegalArgumentException e) {
			logger.error("User tried to remove product using illegal argument: " + id);
			handleExceptionDuringOngoingTransaction(e);
			return Optional.empty();
		} catch (Exception e) {
			handleExceptionDuringOngoingTransaction(e);
			return Optional.empty();
		}
	}

	@Override
	public Optional<Product> update(String id, Product update) {
		if (update == null) return Optional.empty();
		try {
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			Product original = entityManager.find(Product.class, id);
			
			if (original == null) return Optional.empty();
			entityManager.remove(original);
			entityManager.persist(update);
			transaction.commit();
			return Optional.of(update);
			
		} catch (Exception e) {
			logger.error("Update product " + id + " failed with error " + e.getMessage());
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			return Optional.empty();
		}
		
	}
	
	private void handleExceptionDuringOngoingTransaction(Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage());
		entityManager.getTransaction().rollback();
	}

}
