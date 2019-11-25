package warehouse.ch.bbv.efstathiosdimitriadis.rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import ch.bbv.efstathiosdimitriadis.rest.database.ProductsDatabase;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;
import ch.bbv.efstathiosdimitriadis.rest.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	/*
	 * The purpose of this testing class is not to have correct or well designed
	 * tests but to get familiar with the TDD approach and the JUnit API
	 */
	@Mock
	ProductsDatabase mockDb;

	@InjectMocks
	ProductService productService;

	@Test
	void getProductByIdReturns200IfFound() {
		Product mockProduct = new Product("", "");
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).getById(any());

		Response resp = productService.getById("abc");

		assertEquals(Status.OK.getStatusCode(), resp.getStatus());
	}

	@Test
	void getProductByIdReturnsProduct() {
		Product mockProduct = new Product("Dell G3", "laptop");
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).getById(any());

		Response resp = productService.getById("");
		Product returnedProduct = (Product) resp.getEntity();

		assertEquals(mockProduct, returnedProduct);
	}

	@Test
	void getProductByIdDoesNotCreateNewObject() {
		Product mockProduct = new Product("", "");
		Mockito.doReturn(Optional.of(mockProduct)).when(mockDb).getById(any());
		Response resp = productService.getById("");
		Product returnedProduct = (Product) resp.getEntity();

		assertEquals(mockProduct.getId(), returnedProduct.getId());
	}
	
	

}
