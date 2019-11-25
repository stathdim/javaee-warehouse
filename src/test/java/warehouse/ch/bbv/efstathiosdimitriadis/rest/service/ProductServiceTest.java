package warehouse.ch.bbv.efstathiosdimitriadis.rest.service;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.bbv.efstathiosdimitriadis.rest.database.ProductsDatabase;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.model.ProductCategory;
import ch.bbv.efstathiosdimitriadis.rest.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	/*
	 * The purpose of this testing class is not to have correct or well designed
	 * tests but to get familiar with the TDD approach and the JUnit API
	 */

	String testingUUID;
	@Mock
	ProductsDatabase mockDb;

	@InjectMocks
	ProductService productService;

	@BeforeEach
	void setup() {
		testingUUID = UUID.randomUUID().toString();
	}

	@Test
	void getByIdReturns200IfFound() {
		Product mockProduct = new Product("", ProductCategory.GLASSWARE);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).getById(any());

		Response resp = productService.getById(testingUUID);

		assertEquals(Status.OK.getStatusCode(), resp.getStatus());
	}

	@Test
	void getByIdReturnsProduct() {
		Product mockProduct = new Product("Dell G3", ProductCategory.LAPTOP);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).getById(any());

		Response resp = productService.getById(testingUUID);
		Product returnedProduct = (Product) resp.getEntity();

		assertEquals(mockProduct, returnedProduct);
	}

	@Test
	void getByIdDoesNotCreateNewObject() {
		Product mockProduct = new Product("", ProductCategory.CLOTHES);
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
	void getAllReturns200() {
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product("a", ProductCategory.CAR));
		mockProducts.add(new Product("b", ProductCategory.FOOD));

		Mockito.doReturn(mockProducts).when(mockDb).getAll();
		Response resp = productService.getAll();
		assertEquals(Status.OK.getStatusCode(), resp.getStatus());
	}

	@Test
	void getAllReturnsProducts() {
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product("a", ProductCategory.CAR));
		mockProducts.add(new Product("b", ProductCategory.FOOD));

		Mockito.doReturn(mockProducts).when(mockDb).getAll();
		Response resp = productService.getAll();
		List<Product> returnedProducts = (List<Product>) resp.getEntity(); // This probably should be replaced
		// with a JSON unmarshaller
		assertEquals(mockProducts.get(0), returnedProducts.get(0));
		assertEquals(mockProducts.get(1), returnedProducts.get(1));
	}

	@Test
	void getAllReturns204IfNoProducts() {
		Mockito.doReturn(new ArrayList<Product>()).when(mockDb).getAll();
		Response resp = productService.getAll();
		assertEquals(Status.NO_CONTENT.getStatusCode(), resp.getStatus());
	}

	@Test
	void getByNameReturns200IfFound() {
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product("a", ProductCategory.FURNITURE));
		Mockito.doReturn(mockProducts).when(mockDb).getByName(any());

		Response resp = productService.getByName("");
		assertEquals(Status.OK.getStatusCode(), resp.getStatus());
	}

	@Test
	void getByNameReturnsProducts() {
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product("a", ProductCategory.CAR));
		mockProducts.add(new Product("b", ProductCategory.FOOD));
		Mockito.doReturn(mockProducts).when(mockDb).getByName(any());
		Response resp = productService.getByName("a");
		List<Product> returnedProducts = (List<Product>) resp.getEntity(); // This probably should be replaced
		// with a JSON unmarshaller
		assertEquals(mockProducts.get(0), returnedProducts.get(0));
		assertEquals(mockProducts.get(1), returnedProducts.get(1));
	}

	@Test
	void getByNameReturns404IfNoProducts() {
		Mockito.doReturn(new ArrayList<Product>()).when(mockDb).getByName(any());
		Response resp = productService.getByName("");
		assertEquals(Status.NOT_FOUND.getStatusCode(), resp.getStatus());
	}

	@Test
	void addReturnsCreatedIfSuccess() {
		Product mockProduct = new Product("1", ProductCategory.FURNITURE);
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).add(any());
		Response resp = productService.add(mockProduct);

		assertEquals(Status.CREATED.getStatusCode(), resp.getStatus());
	}

	@Test
	void addReturns400IfNotCreated() {
		Mockito.doReturn(Optional.empty()).when(mockDb).add(any());
		Response resp = productService.add(new Product("", ProductCategory.GLASSWARE));
		assertEquals(Status.BAD_REQUEST.getStatusCode(), resp.getStatus());
	}

	@Test
	void addReturnsCorrectURIFormat() {
		Product mockProduct = new Product("1", ProductCategory.GLASSWARE);
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
		Mockito.doReturn(Optional.of(new Product("", ProductCategory.GLASSWARE))).when(mockDb).remove(any());
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
	void removeReturn400IfIdNotUUID() {
		Response resp = productService.remove("kgaspmv");
		assertEquals(Status.BAD_REQUEST.getStatusCode(), resp.getStatus());
	}
}
