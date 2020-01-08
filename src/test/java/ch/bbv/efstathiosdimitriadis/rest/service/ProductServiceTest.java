package ch.bbv.efstathiosdimitriadis.rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import ch.bbv.efstathiosdimitriadis.rest.dao.ProductInMemoryDAO;
import ch.bbv.efstathiosdimitriadis.rest.dao.ProductRelationalDAO;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.model.ProductCategory;
import ch.bbv.efstathiosdimitriadis.rest.resource.beans.ProductFilterBean;
import ch.bbv.efstathiosdimitriadis.rest.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	/*
	 * The purpose of this testing class is not to have correct or well designed
	 * tests but to get familiar with the TDD approach and the JUnit API
	 */

	String testingUUID;
	@Mock 
	ProductRelationalDAO mockDb;

	@InjectMocks
	ProductService productService;


	@BeforeEach
	void setup() {
		testingUUID = UUID.randomUUID().toString();
	}

	@Test 
	void getByIdReturns200IfFound() {
		Product mockProduct = new Product("", ProductCategory.GLASSWARE, 2017);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).getById(any());

		Response resp = productService.getById(testingUUID);

		assertEquals(Status.OK.getStatusCode(), resp.getStatus());
	}

	@Test
	void getByIdReturnsProduct() {
		Product mockProduct = new Product("Dell G3", ProductCategory.LAPTOP, 2017);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).getById(any());

		Response resp = productService.getById(testingUUID);
		Product returnedProduct = (Product) resp.getEntity();

		assertEquals(mockProduct, returnedProduct);
	}

	@Test
	void getByIdDoesNotCreateNewObject() {
		Product mockProduct = new Product("", ProductCategory.CLOTHES, 2017);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).getById(any());
		Response resp = productService.getById(testingUUID);
		Product returnedProduct = (Product) resp.getEntity();

		assertEquals(mockProduct.getId(), returnedProduct.getId());
	}

	@Test
	void getByIdReturns404IfProductNotFound() {
		Mockito.doReturn(Optional.empty()).when(mockDb).getById(any());

		Response resp = productService.getById(testingUUID);
		assertEquals(Status.NOT_FOUND.getStatusCode(), resp.getStatus());
	}

	@Test
	void getByIdReturns400IfIdInvalid() {
		Response resp = productService.getById("abc");
		assertEquals(Status.BAD_REQUEST.getStatusCode(), resp.getStatus());
	}
	
	@Test
	void getAllAcceptsFilterAsArgument() {
		ProductFilterBean filterBean = new ProductFilterBean();
		Response resp = productService.getAll(filterBean);
	}

	@Test
	void getAllReturns200() {
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product("a", ProductCategory.CAR, 2017));
		mockProducts.add(new Product("b", ProductCategory.FOOD, 2017));

		Mockito.doReturn(mockProducts).when(mockDb).getAll(any());
		ProductFilterBean filterBean = new ProductFilterBean();
		Response resp = productService.getAll(filterBean);
		assertEquals(Status.OK.getStatusCode(), resp.getStatus());
	}

	@Test
	void getAllReturnsProducts() {
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product("a", ProductCategory.CAR, 2017));
		mockProducts.add(new Product("b", ProductCategory.FOOD, 2017));

		Mockito.doReturn(mockProducts).when(mockDb).getAll(any());
		ProductFilterBean filterBean = new ProductFilterBean();
		Response resp = productService.getAll(filterBean);
		@SuppressWarnings("unchecked")
		List<Product> returnedProducts = (List<Product>) resp.getEntity(); // This probably should be replaced
		// with a JSON unmarshaller
		assertEquals(mockProducts.get(0), returnedProducts.get(0));
		assertEquals(mockProducts.get(1), returnedProducts.get(1));
	}

	@Test
	void getAllReturns204IfNoProducts() {
		Mockito.doReturn(new ArrayList<Product>()).when(mockDb).getAll(any());
		ProductFilterBean filterBean = new ProductFilterBean();
		Response resp = productService.getAll(filterBean);
		assertEquals(Status.NO_CONTENT.getStatusCode(), resp.getStatus());
	}

	@Test
	void getByNameReturns200IfFound() {
		Optional<Product> mockProduct = Optional.of(new Product("a", ProductCategory.CAR, 2017));
		Mockito.doReturn(mockProduct).when(mockDb).getByName(any());

		Response resp = productService.getByName("");
		assertEquals(Status.OK.getStatusCode(), resp.getStatus());
	}

	@Test
	void getByNameReturnsProduct() {
		Optional<Product> mockProduct = Optional.of(new Product("a", ProductCategory.CAR, 2017));
		Mockito.doReturn(mockProduct).when(mockDb).getByName(any());
		Response resp = productService.getByName("a");
		Product returnedProduct = (Product) resp.getEntity(); // This probably should be replaced
		// with a JSON unmarshaller
		assertEquals(mockProduct.get(), returnedProduct);
	}

	@Test
	void getByNameReturns404IfNoProducts() {
		Mockito.doReturn(Optional.empty()).when(mockDb).getByName(any());
		Response resp = productService.getByName("");
		assertEquals(Status.NOT_FOUND.getStatusCode(), resp.getStatus());
	}

	@Test
	void addReturnsCreatedIfSuccess() {
		Product mockProduct = new Product("1", ProductCategory.FURNITURE, 2017);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).add(any());
		Response resp = productService.add(mockProduct);

		assertEquals(Status.CREATED.getStatusCode(), resp.getStatus());
	}

	@Test
	void addReturns400IfNotCreated() {
		Mockito.doReturn(Optional.empty()).when(mockDb).add(any());
		Response resp = productService.add(new Product("", ProductCategory.GLASSWARE, 2017));
		assertEquals(Status.BAD_REQUEST.getStatusCode(), resp.getStatus());
	}

	@Test
	void addReturnsCorrectURIFormat() {
		Product mockProduct = new Product("1", ProductCategory.GLASSWARE, 2017);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).add(any());

		Response resp = productService.add(mockProduct);

		assertNotEquals(null, resp.getHeaderString("Location"));
		String[] productURI = resp.getHeaderString("Location").split("/");
		assertEquals("products", productURI[0]);
//		Second part must be a valid UUID
		try {
			UUID.fromString(productURI[1]);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void removeReturns200IfDeleted() {
		Mockito.doReturn(Optional.of(new Product("", ProductCategory.GLASSWARE, 2017))).when(mockDb).remove(any());
		Response resp = productService.remove(testingUUID);
		assertEquals(Status.OK.getStatusCode(), resp.getStatus());
	}

	@Test
	void removeReturns404IfNotFound() {
		Mockito.doReturn(Optional.empty()).when(mockDb).remove(any());
		Response resp = productService.remove(testingUUID);
		assertEquals(Status.NOT_FOUND.getStatusCode(), resp.getStatus());
	}

	@Test
	void removeReturns400IfIdNotUUID() {
		Response resp = productService.remove("kgaspmv");
		assertEquals(Status.BAD_REQUEST.getStatusCode(), resp.getStatus());
	}

	@Test
	void updateReturns200IfUpdated() {
		Product mockProduct = new Product("The Fellowship of the Ring", ProductCategory.BOOK, 2017);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).update(any(), any());

		Response resp = productService.update("", new Product("The Fellowship of the Ring", ProductCategory.BOOK, 2017));
		assertEquals(Status.OK.getStatusCode(), resp.getStatus());
	}

	@Test
	void updateReturnsUpdatedProduct() {
		Product mockProduct = new Product("The Fellowship of the Ring", ProductCategory.BOOK, 2017);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).update(any(), any());
		Response resp = productService.update("", mockProduct);

		Product returnedProduct = (Product) resp.getEntity();
		assertEquals(mockProduct, returnedProduct);
	}

	@Test
	void updateReturns404IfNotFound() {
		Mockito.doReturn(Optional.empty()).when(mockDb).update(any(), any());
		Response resp = productService.update("", new Product("The Fellowship of the Ring", ProductCategory.BOOK, 2017));
		assertEquals(Status.NOT_FOUND.getStatusCode(), resp.getStatus());
	}

	@Test
	void updateReturnsNewProductURI() {
		Product mockProduct = new Product("The Fellowship of the Ring", ProductCategory.BOOK, 2017);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).update(any(), any());
		Response resp = productService.update("", mockProduct);
		String[] location = resp.getHeaderString("Location").split("/");

		assertEquals(mockProduct.getId(), location[1]);

	}
}
