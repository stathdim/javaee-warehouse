package ch.bbv.efstathiosdimitriadis.rest.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.resource.beans.ProductFilterBean;

public class ProductRelationalDAO implements ProductDAO {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Product> add(Product product) {

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(product);
			entityManager.getTransaction().commit();
		} catch (EntityExistsException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(product);
	}

	@Override
	public Optional<Product> remove(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Product> update(String id, Product update) {
		// TODO Auto-generated method stub
		return null;
	}

}
